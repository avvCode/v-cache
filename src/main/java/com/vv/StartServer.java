package com.vv;



import com.vv.server.command.HelpCommand;
import com.vv.server.dispatch.DispatchCommand;
import com.vv.server.dispatch.DoCommand;
import com.vv.server.domain.Config;
import com.vv.server.repository.Repository;
import com.vv.server.util.ioUtils.IoUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class StartServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Config.port);
            DoCommand.doScan();
            HelpCommand.doScan();
            Repository.readFromFile();
            System.out.println("服务启动，等待客户端连接");
            while (true){
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Repository.writeToFile();
        }
    }


}
class Handler extends Thread{
    private Socket socket;
    public Handler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("客户端" +Thread.currentThread().getName()+"连接上");
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        try {
            while (true){
                if(socket != null){
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String message = IoUtils.readLine(bufferedReader);

                    System.out.println(LocalDateTime.now() + " " + Thread.currentThread() + " " + message);

                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String result = DispatchCommand.dispatchCommand(message);
                    bufferedWriter.write(result);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("客户端" + Thread.currentThread().getName() + "关闭");
        }finally {
                try {
                    if(bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if(bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if(socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
}

