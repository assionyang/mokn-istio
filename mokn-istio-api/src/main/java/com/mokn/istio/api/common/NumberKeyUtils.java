package com.mokn.istio.api.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class NumberKeyUtils {

    /**
     * 时间序列
     * @return
     */
    public static String timeNumber(){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String number=format.format(new Date());
        return number+random(5);
    }

    /**
     * 生成随机数
     * @param length 长度
     * @return
     */
    public static String random(int length){

        StringBuilder lengthStr=new StringBuilder();
        for(int i=0;i<length;i++){
            lengthStr.append("9");
        }

        Random random=new Random();
        int rd=random.nextInt(Integer.parseInt(lengthStr.toString().trim()));
        StringBuilder randomStr=new StringBuilder(String.valueOf(rd));
        if(randomStr.length()<length){
            for(int i=0;i<(length-randomStr.length());i++){
                randomStr.insert(0,"0");
            }
        }

        return randomStr.toString().trim();
    }

    /**
     * 生成UUID
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().trim().replace("-","");
    }
}
