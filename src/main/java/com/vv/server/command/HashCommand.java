package com.vv.server.command;

import java.util.HashMap;
import java.util.Set;

public class HashCommand {

    private static final HashMap<String,String> HASH_MAP = new HashMap<>();

    public static String get(String[] parseMessage) throws Exception {
        if(parseMessage.length == 2){
            return HASH_MAP.get(parseMessage[1]);
        }else {
            throw new Exception("指令错误");
        }
    }
    public static String mGet(String[] parseMessage){
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < parseMessage.length; i++) {
            sb.append(i).append(")").append(HASH_MAP.get(parseMessage[i])).append("\n");
        }
        return sb.toString();
    }

    public static String set(String[] parseMessage){
        HASH_MAP.put(parseMessage[1],parseMessage[2]);
        return "1";
    }

    public static String mSet(String[]parseMessage){
        int successNum = 0;
        int i = 1;
        while (i < parseMessage.length){
            HASH_MAP.put(parseMessage[i],parseMessage[i+1]);
            successNum++;
            i+=2;
        }
        return successNum+"";
    }

    public static String del(String [] parseMessage){
        int successNum = 0;
        for (int i = 1; i < parseMessage.length; i++) {
            if(HASH_MAP.containsKey(parseMessage[i])){
                HASH_MAP.remove(parseMessage[i]);
                successNum++;
            }
        }
        return successNum+"";
    }
    public static String storeStr(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mset ");
        Set<String> keySet = HASH_MAP.keySet();
        for (String s : keySet) {
            stringBuilder.append(s).append(" ").append(HASH_MAP.get(s)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    public static void main(String[] args) {
        set(new String[]{"set","name","zs"});
        set(new String[]{"set","name1","zs"});
        set(new String[]{"set","name2","zs"});
        set(new String[]{"set","name3","zs"});
        mSet(new String[]{"mset","name4","zs","name5","ls"});
        System.out.println(storeStr());
    }
}
