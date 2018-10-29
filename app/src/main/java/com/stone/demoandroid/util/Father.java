package com.stone.demoandroid.util;

public class Father {
    static Child cc =new Child();

    static{
        System.out.println("father static");
    }
    {
        System.out.println("father");
    }

    public  void fff(){
        System.out.println("father function");

    }
    public Father(){

    }
}
