package com.vv.server.dispatch;



import com.vv.server.domain.Config;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoCommand {
    private static final String scanPath = Config.commandScanPath;
    //使用哈希来存储 方法名肯定是唯一，key则是类
    private static HashMap<String,Class<?>> methodNameAndClass = new HashMap<>();

    private static HashMap<String,String> methodNames = new HashMap<>();
    /**
     * 递归遍历repository包下的数据结构，将所有方法弄出来
     */
    public static void doScan(){
        //利用类加载器将文件读取出来
        URL resource = DoCommand.class.getClassLoader().getResource(scanPath);
        try {
            if(resource != null && resource.toString().startsWith("file")){
//                System.out.println(resource);
                String filePath = URLDecoder.decode(resource.getFile(), "utf-8");
//                System.out.println("filePath = " + filePath);
                File dir = new File(filePath);
                // 遍历包下的所有文件 /Users/zyz19/Desktop/后端学习之旅/workspace/kvResp/out/production/kvResp/server/repository/Construct
                List<File> fileList = fetchFileList(dir);
                for (File f : fileList) {
//                    System.out.println(f.getName());
                    String classPath = getClassPath(f);
                    if (classPath == null) {
                        continue;
                    }
                    //获取到该包下及其子包下所有的类的方法
                    Class<?> clazz = Class.forName(classPath);
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        String methodName = method.getName();
                        if(!methodName.equalsIgnoreCase("doCommand")){
                            methodNames.put(methodName.toLowerCase(),methodName);
                            methodNameAndClass.put(method.getName().toLowerCase(),clazz);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("指令文件加载成功！");
    }

    /**
     * 递归获取包下所有的文件
     * @param dir 路径
     */
    private static List<File> fetchFileList(File dir) {
        List<File> fileList = new ArrayList<>();
        fetchFileList(dir, fileList);
        return fileList;
    }

    /**
     * 递归方法
     * @param dir 路径
     */
    private static void fetchFileList(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                fetchFileList(f, fileList);
            }
        } else {
            fileList.add(dir);
        }
    }

    private static String getClassPath(File f) {
        String fileName = f.getAbsolutePath();
        if (fileName.endsWith(".class")) {
            String nosuffixFileName = fileName.substring(7 + fileName.lastIndexOf("kvResp"), fileName.indexOf(".class"));
            // 将路径中的“/”替换成类路径中的“.”
            return nosuffixFileName.replaceAll("\\\\", ".");
        }
        return null;
    }

    public static Object doCommand(String [] parseMessage){
        Object result = null;
        String methodName = parseMessage[0].toLowerCase();
        try {
            Class<?> aClass = methodNameAndClass.get(methodName);
            String actualName = methodNames.get(methodName);
            Method method = aClass.getMethod(actualName,String[].class);
            result = method.invoke(aClass, (Object) parseMessage);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            result = "指令错误";
        }
        return result;
    }
}
