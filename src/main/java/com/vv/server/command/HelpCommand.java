package com.vv.server.command;



import com.vv.server.domain.Config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class HelpCommand {
    public static final String HELP = "HELP";
    private static final String commandFilePath = Config.commandFilePath;
    private static final StringBuilder ALL_COMMAND  = new StringBuilder();
    public static final HashMap<String,String> commandFactory = new HashMap<>();
    private HelpCommand(){

    }
    public static void doScan(){
        BufferedReader bufferedReader = null;
        try {
            Properties properties = new Properties();
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(commandFilePath)));
            properties.load(bufferedReader);
            Set<String> strings = properties.stringPropertyNames();

            for (String string : strings) {
                String property = properties.getProperty(string);
                ALL_COMMAND.append(property).append("\n");
                commandFactory.put(string,property);
            }
//            System.out.println(ALL_COMMAND);
            System.out.println("指令文件加载成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String doCommand(String[] parseMessage){
        if(parseMessage.length > 1){
            String s = parseMessage[1].toUpperCase(Locale.ROOT);
            return commandFactory.get(s);
        }else{
            return ALL_COMMAND.toString();
        }
    }
}
