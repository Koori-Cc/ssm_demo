package com.zk.function.base64;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;
import java.io.*;

/**
 * @author panbing@supcon.com
 * @create 2017/11/6 15:23
 * @description
 */
public class Base64Util {

    //测试代码
    public static void main(String[] args) {
        String imgFile = "D:\\[work]\\temp\\ftp\\jpg\\3.jpg";//待处理的图片
        String imgbese = getImgStr(imgFile);

        System.out.println(imgbese.length());

        String imgFilePath = "D:\\[work]\\33.jpg";//新生成的图片
        generateImage(imgbese, imgFilePath);
    }

    /**
     * 将图片转换成Base64编码
     * @param in 传过来的流对象
     * @description 从图片服务器获取编码后的图片数据,网络传输用这种
     */
    public static String getImgStr(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = in.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);    //将数据写到输出流
        }
        byte[] data = outStream.toByteArray();  //将输出流转换成字节数组
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @description 从本地的地址获取编码后的图片数据
     */
    public static String getImgStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];   //available() 获取本地的数据没有问题,但是获取网络的数据可能会获取不完整
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr 图片数据
     * @param imgFilePath 保存图片全路径地址
     * @return
     */
    public static boolean generateImage(String imgStr, String imgFilePath) {
        //
        if (imgStr == null) //图像数据为空
            return false;
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }






}