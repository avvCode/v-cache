package com.vv.server.util.stringUtils;

public class StringUtils {
    /**
     * 将用户输入的指令参数切割
     * @param message
     * @return
     */
    public static String[] parse(String message){
        return message.trim().split(" ");
    }

}
