package com.vv.server.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class LinkedListCommand {
    private static final HashMap<String,LinkedList<String>> LINKED_LIST_HASH_MAP = new HashMap<>();

    public static String rPop(String[]parseMessage){
        String key = parseMessage[1];
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.get(key);
        String result = linkedList.pollLast();
        return result;
    }
    public static String lPop(String[]parseMessage){
        String key = parseMessage[1];
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.get(key);
        String result = linkedList.pollFirst();
        return result;
    }
    public static String rPush(String[]parseMessage){
        String key = parseMessage[1];
        int successNum = 0;
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.computeIfAbsent(key, k -> new LinkedList<>());
        for (int i = 2; i < parseMessage.length; i++) {
            linkedList.addLast(parseMessage[i]);
            successNum++;
        }
        return successNum+"";
    }
    public static String lPush(String[]parseMessage){
        String key = parseMessage[1];
        int successNum = 0;
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.computeIfAbsent(key, k -> new LinkedList<>());
        for (int i = 2; i < parseMessage.length; i++) {
            linkedList.addFirst(parseMessage[i]);
            successNum++;
        }
        return successNum+"";
    }
    public static String lDel(String[]parseMessage){
        String key = parseMessage[1];
        LINKED_LIST_HASH_MAP.remove(key);
        return "1";
    }
    public static String len(String[]parseMessage){
        String key = parseMessage[1];
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.get(key);
        if(linkedList == null){
            return "0";
        }
        return linkedList.size()+"";
    }
    public static String range(String[]parseMessage){
        String key = parseMessage[1];
        int start = Integer.parseInt(parseMessage[2]);
        int end = Integer.parseInt(parseMessage[3]);
        StringBuilder sb = new StringBuilder();
        LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.get(key);
        int size = linkedList.size();
        for(int i = 0 ; i  < size; i++){
            sb.append(linkedList.get(i)).append(" ");
        }
        return sb.toString();
    }

    public static String storeStr(){
        StringBuilder stringBuilder = new StringBuilder();

        Set<String> keySet = LINKED_LIST_HASH_MAP.keySet();
        for (String s : keySet) {
            stringBuilder.append("lPush ")
                         .append(s)
                         .append(" ");
            LinkedList<String> linkedList = LINKED_LIST_HASH_MAP.get(s);
            for (String s1 : linkedList) {
                stringBuilder.append(s1).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }

    public static void main(String[] args) {
        rPush(new String[]{"rPush","name","1"});
        rPush(new String[]{"rPush","name","2"});
        rPush(new String[]{"rPush","name","3"});
        rPush(new String[]{"rPush","age","4"});
        rPush(new String[]{"rPush","age","5"});
        rPush(new String[]{"rPush","age","6"});
        System.out.println("range(new String[]{\"range\",\"name\",\"0\",\"2\"}) = " + range(new String[]{"range", "name", "0", "2"}));
        System.out.println(storeStr().trim());
    }
}
