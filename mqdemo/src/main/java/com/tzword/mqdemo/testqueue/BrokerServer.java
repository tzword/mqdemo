package com.tzword.mqdemo.testqueue;

import jdk.nashorn.internal.ir.WhileNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer implements Runnable{
    public  static int SERVICE_PORT = 9999;

    private final Socket socket;

    public BrokerServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            while(true){
                String str = in.readLine();
                if (str == null){
                    continue;
                }
                System.out.println("接收到原始数据：" + str);
                if (str.equals("CONSUME")){
                    // 从消息队列中消费一条消息
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                }else {
                    // 其他情况表示生产消息放在消息队列中
                    Broker.produce(str);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while(true){
            BrokerServer brokerServer = new BrokerServer(server.accept());
            new Thread(brokerServer).start();
        }
    }
}
