package com.zk.function.ftp.controllers;

import com.zk.function.common.entities.PaginationVO;
import com.zk.function.ftp.entities.DownloadFile;
import com.zk.function.ftp.entities.FtpEntity;
import com.zk.function.ftp.services.FtpService;
import com.zk.function.ftp.utils.ExcelUtil;
import com.zk.function.ftp.utils.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/11 9:16
 * @description
 */
@Controller("function_ftp_controller")
@RequestMapping("/function/ftp")
public class FtpController {

    private Logger logger = LoggerFactory.getLogger(FtpController.class);

    @Autowired
    private FtpService ftpService;

    @Value("${ftp_username}")
    private String ftp_username;   //登录ftp的用户名

    @Value("${ftp_password}")
    private String ftp_password;   //登录ftp的密码

    @Value("${ftp_timeout}")
    private String ftp_timeout;   //ftp的超时时限



    @RequestMapping("/toList.do")
    public String toList() {
        return "function/ftp/list";
    }

    //----------------------------获取分页数据 开始-----------------------------------------------
    @RequestMapping(value="/list.do",method= RequestMethod.POST)
    @ResponseBody
    public PaginationVO<FtpEntity> list(FtpEntity ftpEntity,PaginationVO<FtpEntity> paginationVO) {
        String flag = ftpEntity.getC_filename_flag();
        if("0".equals(flag)) {      //精确查找
            ftpEntity.setC_jq_filename(ftpEntity.getFilename());
        }else if("1".equals(flag)) {   //模糊查找
            ftpEntity.setC_filename(ftpEntity.getFilename());
        }
        return ftpService.paging(paginationVO,ftpEntity);
    }

    @InitBinder("ftpEntity")     //必须是实体类的驼峰命名形式,否则无法绑定数据
    public void entityBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("ftpEntity.");
    }

    @InitBinder("paginationVO")
    public void voBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("paginationVO.");
    }
    //-------------------------------获取分页数据 结束---------------------------------------------------

    @RequestMapping(value="/fileType.do",method= RequestMethod.POST)
    @ResponseBody
    public List<String> fileType() {
        return ftpService.queryFileTypeList();
    }

    @RequestMapping(value="/insert.do",method= RequestMethod.POST)
    @ResponseBody
    public void insert(@RequestBody FtpEntity entity) {
        ftpService.insert(entity);
    }

    @RequestMapping(value="/batchInsert.do",method= RequestMethod.POST)
    @ResponseBody
    public void batchInsert() {

    }

    @RequestMapping(value="/update.do",method= RequestMethod.POST)
    @ResponseBody
    public void update(@RequestBody FtpEntity entity) {
        ftpService.update(entity);
    }

    @RequestMapping(value="/delete.do",method= RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestBody FtpEntity entity) {
        ftpService.delete(entity);
    }


    @RequestMapping("/toImportExcel.do")
    public String toImportExcel() {
        return "function/ftp/importExcel";
    }

    @RequestMapping("/importExcel.do")
    public String importExcel(MultipartFile file) {
        try {
            List<FtpEntity> list = ExcelUtil.readExcel(file);
            ftpService.batchInsert(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用重定向,不要用转发!否则刷新页面会重复插入数据
        return "redirect:/function/ftp/toList.do";
    }


    @RequestMapping("/dowmloadFile.do")  //同步请求,激活浏览器的下载框,下载文件
    public void dowmloadFile(FtpEntity entity, HttpServletResponse response) {
        String flag = entity.getC_filename_flag();
        if("0".equals(flag)) {      //精确查找
            entity.setC_jq_filename(entity.getFilename());
        }else if("1".equals(flag)) {   //模糊查找
            entity.setC_filename(entity.getFilename());
        }
        List<FtpEntity> list =  ftpService.queryByCondition(entity);
        List<DownloadFile> fileList = ftpService.getDownloadFileList(list);

        try {
            ZipUtil.downloadZipImg(response,fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
