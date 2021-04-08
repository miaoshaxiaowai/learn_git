package com.example.geek_java.week3;


import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Map;

public class Question1Client {

    private static final String URL="http://localhost:8888/api/mgmt/device/model/machine/list" +
            "?pageNum=1&pageSize=10&orderBy=name&orderByType=ascending";

    public static void main(String[] args) {
        System.out.println(get(URL,null,null));
    }

    private static String get(String url, Map<String,Object> params,Map<String,String> headers){
        String result=null;
        try {
            result=Request.Get(url).execute().returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
