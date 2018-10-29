package com.stone.demoandroid.util;

public class Child extends Father {

    static{
        System.out.println("child static");
    }
    {
        System.out.println("child");
    }

    @Override
    public void fff() {
        super.fff();
        System.out.println("child function");
    }
}
