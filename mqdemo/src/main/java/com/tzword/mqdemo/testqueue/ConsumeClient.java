package com.tzword.mqdemo.testqueue;

public class ConsumeClient {
    public static void main(String[] args) {
        try {
            MqClient client = new MqClient();
            String message = client.consume();
            System.out.println("获取的消息位：" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
