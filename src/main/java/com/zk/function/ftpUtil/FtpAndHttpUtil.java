package com.zk.function.ftpUtil;

import com.zk.function.base64.Base64Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;

/**
 * @author panbing@supcon.com
 * @create 2017/11/6 16:09
 * @description
 */
public class FtpAndHttpUtil {

    private static Logger log = LoggerFactory.getLogger(FtpAndHttpUtil.class);

    // 从服务器获取输入流,编码成base64的字符串
    public static String getHttpBase64Str(String httpUrl, int timeOut) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(timeOut);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            }

            String data = null;

            //base64转码
            if(inputStream != null) {
                data = Base64Util.getImgStr(inputStream);
            }

            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFtpBase64Str(String ftpStr, int timeout) {
        if (StringUtils.isEmpty(ftpStr)) {
            return null;
        }

        // 1：user 2：pass 3：ip 4：port 5：filepath/filename
        String[] strParseFtpUrl = new String[5];
        try {
            // ftp路径格式ftp://user:pass@ip:port/filepath/filename
            String[] url1s = ftpStr.split("@");

            // 前半部分:ftp://user:pass
            String[] urls4 = url1s[0].split("//");
            String[] urls5 = urls4[1].split(":");
            strParseFtpUrl[0] = urls5[0];// user
            strParseFtpUrl[1] = urls5[1];// pass

            // 后半部分:ip:port/filepath/filename 端口可能没有
            int startOfcolon = url1s[1].indexOf("/");
            // 前部分ip:port 后部分filepath/filename
            String strIpport = url1s[1].substring(0, startOfcolon);
            strParseFtpUrl[4] = url1s[1].substring(startOfcolon + 1);
            // 判断是否有端口号
            int startOfIpPort = strIpport.indexOf(":");
            if (startOfIpPort < 0) {
                // ip
                strParseFtpUrl[2] = strIpport;
                strParseFtpUrl[3] = "2050";
            } else {
                // ip:port
                String[] urls2 = strIpport.split(":");
                strParseFtpUrl[2] = urls2[0];
                strParseFtpUrl[3] = urls2[1];
            }
        } catch (Exception e) {
            log.error("ftp路径不正确，" + ftpStr);
            return null;
        }

        //设置ftp的连接客户端
        FTPClient ftpclient = new FTPClient();

        //设置编码方式
        ftpclient.setControlEncoding("GBK");

//		ftpclient.setConnectTimeout(timeout);
//		ftpclient.setDataTimeout(timeout);

        InputStream inputStream = null;
        try {

            //连接ftp客户端
            ftpclient.connect(strParseFtpUrl[2], Integer.parseInt(strParseFtpUrl[3]));

            // 登录FTP服务器
            if (!ftpclient.login(strParseFtpUrl[0], strParseFtpUrl[1])) {
                log.info("登录FTP服务器失败");
                return null;
            }

            int reply = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.debug("FTP服务器没有响应");
                return null;
            }

//			ftpclient.enterRemotePassiveMode();

            ftpclient.setBufferSize(2048);   //设置缓冲区

            ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpclient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            ftpclient.enterLocalPassiveMode();

            inputStream = ftpclient.retrieveFileStream(strParseFtpUrl[4]);

            if (null == inputStream) {
                log.error("文件流转换失败，" + ftpStr);
                return null;
            }

            return Base64Util.getImgStr(inputStream);

        } catch (SocketException se) {
            log.debug("连接主机:" + strParseFtpUrl[2] + "Socket异常;" + se.toString());
            return null;
        } catch (IOException ioe) {
            log.debug("连接主机:" + strParseFtpUrl[2] + "失败;" + ioe.toString());
            return null;
        } catch (NullPointerException ne) {
            log.debug("inputStream转byte[]失败,inputStream可能为null;"
                    + ne.toString());
            return null;
        } catch (Exception e) {
            log.error("ftp获取图片失败。");
            return null;
        } finally {
           try {
               if (inputStream != null) {
                    inputStream.close();
                    ftpclient.completePendingCommand();
                }
                if (ftpclient.isConnected()) {
                    ftpclient.disconnect();
                }
            } catch (IOException e) {
                log.debug("关闭输入流和ftpclient失败");
            }
        }
    }


}
