package com.zk.function.ftp.utils;

import com.zk.function.ftp.entities.DownloadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author panbing@supcon.com
 * @create 2017/10/10 16:38
 * @description 压缩包的工具类
 */
public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    public static void downloadZipImg(HttpServletResponse response, List<DownloadFile> downloadFileList) throws Exception {
        String fileName = "压缩文件.zip";       //这里一定要带后缀
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));   //对文件名进行编码
        try {
            byte[] dataByteArr = zipFile(downloadFileList);   //这个方法在下面
            response.getOutputStream().write(dataByteArr);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("压缩zip数据出现异常", e);
            throw new RuntimeException("压缩zip包出现异常");
        }

    }

    public static byte[] zipFile(List<DownloadFile> downloadFileList) throws Exception {
        /**将字节写到一个字节输出流里*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(baos);

        try {
            /**创建zip file in memory */
            for (DownloadFile downloadFile : downloadFileList) {   //这个集合事先需要准备好
                //判断文件必须存在
                if(downloadFile.getByteDataArr() != null && downloadFile.getByteDataArr().length > 0) {
                    ZipEntry entry = new ZipEntry(downloadFile.getFileName());    //这个filename需要带后缀,比如 ".jpg"
                    entry.setSize(downloadFile.getByteDataArr().length);
                    out.putNextEntry(entry);
                    out.write(downloadFile.getByteDataArr());
                    out.closeEntry();
                }
            }
        } catch (IOException e) {
            logger.error("压缩zip数据出现异常", e);
            throw new RuntimeException("压缩zip包出现异常");
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return baos.toByteArray();
    }

}
