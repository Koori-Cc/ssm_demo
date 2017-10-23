package com.zk.function.ftp.utils;

import com.zk.function.ftp.entities.FtpEntity;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 16:24
 * @description 通过ftp协议连接的工具类
 */
public class FtpUtil {

    private static Logger log = LoggerFactory.getLogger(FtpUtil.class);

    public static byte[] getFileByteArr(FtpEntity entity) {
        byte[] bytearr = null;         //定义一个空的数组
        FTPClient ftpclient = new FTPClient();    //定义ftp客户端
        ftpclient.setConnectTimeout(entity.getTimeout()); // 设置超时，如果超过就判定超时了(单位毫秒)
        InputStream inputStream = null;   //定义输入流
        try {
            ftpclient.connect(entity.getIp(), entity.getPort());
            // 登录FTP服务器
            if (!ftpclient.login(entity.getUsername(), entity.getPassword())) {
                log.debug("登录FTP服务器失败");
            }

            int reply = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.debug("FTP服务器没有响应");
            }

            ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpclient.enterLocalPassiveMode();

            ftpclient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            //获取路径前缀
            String prePath = entity.getPrePath();
            //获取文件名
            String filename = entity.getFilename();

            //由文件的前缀和文件名组成一个uri路径
            String uriPath = prePath + filename;

            //把文件路径重新编码,避免接收不到文件
            //uriPath不是全路径url 如 ftp://127.0.0.1:21/ftp/1.jpg ,而是uri /ftp/1.jpg
            inputStream = ftpclient.retrieveFileStream(new String(uriPath.getBytes("UTF-8"), "ISO-8859-1"));

            if (null == inputStream) {
                log.error("文件流转换失败，");
            }
            bytearr = encodeIOToByteArr(inputStream);   //方法在下面
        } catch (SocketException se) {
            log.debug("连接主机:Socket异常;");
        } catch (IOException ioe) {
            log.debug("连接主机:失败;");
        } catch (NullPointerException ne) {
            log.debug("inputStream转byte[]失败,inputStream可能为null;");
        } catch (Exception e) {
            log.error("ftp获取图片失败。");
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (ftpclient.isConnected()) {
                    ftpclient.disconnect();
                }
            } catch (IOException e) {
                log.debug("关闭inputStream和ftpclient失败");
            }
        }
        return bytearr;   //将读到的文件转换成字节流返回
    }

    public static byte[] encodeIOToByteArr(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = null;
        byte[] b = new byte[1024];
        try {
            while ((len = is.read(b, 0, b.length)) != -1) {
                bos.write(b, 0, len);
            }
            buffer = bos.toByteArray();
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                log.debug("关闭bos失败");
            }
        }
        return buffer;
    }
}
