package com.zk.function.ftp.entities;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 16:21
 * @description 下载文件对应的实体类
 */
public class DownloadFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName = "";      //文件的名称
    private byte[] byteDataArr = new byte[0];   //字节数组,存储2进制文件

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getByteDataArr() {
        return byteDataArr;
    }

    public void setByteDataArr(byte[] byteDataArr) {
        this.byteDataArr = byteDataArr;
    }

    @Override
    public String toString() {
        return "DownloadFileDto{" +
                "fileName='" + fileName + '\'' +
                ", byteDataArr=" + Arrays.toString(byteDataArr) +
                '}';
    }
}
