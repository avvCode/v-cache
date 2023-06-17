package com.vv.server.repository;



import com.vv.server.command.HashCommand;
import com.vv.server.command.LinkedListCommand;
import com.vv.server.domain.Config;
import com.vv.server.util.stringUtils.StringUtils;

import java.io.*;

public class Repository {
    private static final String STORE_PATH = Config.storePath;

    public static void writeToFile(){
        BufferedWriter bufferedWriter = null;
        try {
            String hashStoreStr = HashCommand.storeStr();
            String linkedListStr =  LinkedListCommand.storeStr();
            bufferedWriter = new BufferedWriter(new FileWriter(STORE_PATH));
            bufferedWriter.write(hashStoreStr);
            bufferedWriter.write(linkedListStr);
            System.out.println("保存数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFromFile(){
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(new FileReader(STORE_PATH));
            String command = bufferedReader.readLine();
            HashCommand.mSet(StringUtils.parse(command));

//            do{
//                command = bufferedReader.readLine();
//                System.out.println(command);
//                LinkedListCommand.rPush(StringUtils.parse(command));
//            }while (command != null);
            System.out.println("读取数据成功！");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
