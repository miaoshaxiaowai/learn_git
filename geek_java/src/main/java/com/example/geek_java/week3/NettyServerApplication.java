package com.example.geek_java.week3;


import com.example.geek_java.week3.inbound.HttpInboundServer;

import java.util.Arrays;

public class NettyServerApplication {
    public final static String GATEWAY_NAME="NIOGateway";
    public final static String GATEWAY_VERSION="3.0.0";

    public static void main(String[] args) {
        String proxyPort=System.getProperty("proxyPort","8888");

        // 这是之前的单个后端url的例子
        //String proxyServers = System.getProperty("proxyServer","http://localhost:8088");
//          //  http://localhost:8888/api/hello  ==> gateway API
//          //  http://localhost:8088/api/hello  ==> backend service

        //多个后端url走随机路由
        String proxyServers = System.getProperty("proxyServers","http://127.0.0.1:8081," +
                "http://127.0.0.1:8082");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME+" "+GATEWAY_VERSION+" starting...");
        HttpInboundServer server=new HttpInboundServer(port,
                Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME+" "+GATEWAY_VERSION+" started at http://localhost:" + port + " for server:" + server.toString());

        try{
            server.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
