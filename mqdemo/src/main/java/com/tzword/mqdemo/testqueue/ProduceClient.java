package com.tzword.mqdemo.testqueue;

public class ProduceClient {
    public static void main(String[] args) {
        try {
            MqClient client = new MqClient();
            client.produce("Hello world");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
