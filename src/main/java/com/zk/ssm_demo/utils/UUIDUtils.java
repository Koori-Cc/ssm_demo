package com.zk.ssm_demo.utils;

import java.util.UUID;

/**
 * Created by Koori_Cc on 2017/8/29.
 */
public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
