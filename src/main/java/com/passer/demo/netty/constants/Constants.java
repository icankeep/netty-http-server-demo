package com.passer.demo.netty.constants;

/**
 * @author passer
 * @time 2022/11/13 16:01
 */
public interface Constants {
    String MAGIC_CODE = "COCO";

    int MAGIC_CODE_LEN = MAGIC_CODE.length();

    int MIN_LEN = MAGIC_CODE.length() + 32;
}
