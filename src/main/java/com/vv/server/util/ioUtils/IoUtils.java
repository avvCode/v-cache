package com.vv.server.util.ioUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class IoUtils {
    public static String readInputStream(Socket socket) throws IOException {
        byte[] buf = new byte[10240];
        int readLen = 0;
        StringBuilder sb = new StringBuilder();
        while ((readLen = socket.getInputStream().read(buf,0,readLen)) != -1){
            sb.append(new String(buf,0,readLen));
        }
        return sb.toString();
    }

    public static void writeOutputString(Socket socket, String message) throws IOException {
        socket.getOutputStream().write(message.getBytes());
        socket.getOutputStream().flush();
        socket.shutdownOutput();
    }
    //按行读取字节流
    public static String readLine(BufferedReader reader) throws IOException {
        char a1 = 'a';// 上一次存放的字符
        char a2 = 'a';// 本次存放的字符
        int data = -1;
        StringBuilder sb = new StringBuilder();
        while ((data = reader.read()) != -1) {
            a2 = (char) data;
            sb.append(a2);// 将本次的字符存放到StringBuilder里面
            if (a1 == 13 && a2 == 10) {
                break;
            }
            a1 = a2;// 将本次的赋值给a1
        }
        return sb.toString().trim();
    }
}

