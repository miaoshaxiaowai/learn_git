package com.example.geek_java.week3.router;

import java.util.List;

public class RoundRobinHttpEndpointRouter implements HttpEndpointRouter{

    private static int order=0;

    @Override
    public String route(List<String> endpoints) {
        String url= order(endpoints);
        System.out.println("url:"+url);
        return url;
    }

    private static synchronized String order(List<String> endpoints){
        if(order==Integer.MAX_VALUE){
            order=0;
        }
        return endpoints.get(order++%endpoints.size());
    }
}
