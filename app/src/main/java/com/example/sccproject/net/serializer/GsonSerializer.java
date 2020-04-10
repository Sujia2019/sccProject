package com.example.sccproject.net.serializer;

import com.google.gson.Gson;

/**
 * Created by alienware on 2020/4/11.
 */

public class GsonSerializer extends Serializer {

    private Gson gson;
    @SuppressWarnings("unchecked")
    @Override
    public <T> byte[] serializer(T obj) {
        if (null==obj){
            System.out.println("Invalid Object");
        }
        String s = gson.toJson(obj);
        System.out.println("obj"+s);
        return s.getBytes();
    }

    @Override
    public <T> Object deserializer(byte[] bytes, Class<T> clazz) {
        T t = gson.fromJson(new String(bytes), clazz);
        System.out.println("xxx"+t);
        return t;
    }
}