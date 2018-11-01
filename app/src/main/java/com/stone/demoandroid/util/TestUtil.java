package com.stone.demoandroid.util;

import com.stone.demoandroid.MainActivity;

import java.util.HashMap;

public class TestUtil {
    private static final String TAG = "TestUtil";

    public static void main(String args[]) {
        //Child ch =new Child();
        //Father ff =new Father();
        new TestUtil().printResult("9fil3dg11P0jAsf11j");
    }

    public void printResult(String str) {

        String[] strArray = str.split("[^0-9]");
        HashMap<Integer, Integer> map = new HashMap();
        int maxValue = 0;
        int maxCount = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (!"".equals(strArray[i])) {
                int key = Integer.valueOf(strArray[i]);
                if (map.containsKey(key)) {
                    System.out.println("key:" + key);
                    map.put(key, map.get(key)+1);

                } else {
                    map.put(key, 1);
                    System.out.println("again key:" + key);
                }

                if (maxCount < map.get(key)) {
                    maxCount = map.get(key);
                    maxValue = key;
                }


            }
        }

        System.out.println("String :" + str + "\tvalue:" + maxValue + "\t count:" + maxCount);
    }
}
