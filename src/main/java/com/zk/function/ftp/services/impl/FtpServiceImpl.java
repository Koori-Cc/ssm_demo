package com.zk.function.ftp.services.impl;

import com.zk.function.ftp.daos.FtpDao;
import com.zk.function.ftp.entities.DownloadFile;
import com.zk.function.ftp.entities.FtpEntity;
import com.zk.function.ftp.services.FtpService;
import com.zk.function.common.entities.PaginationVO;
import com.zk.function.ftp.utils.FtpUtil;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/13 8:59
 * @description
 */

@Service("function_ftp_service")
public class FtpServiceImpl implements FtpService {

    @Autowired
    private FtpDao ftpDao;

    @Value("${ftp_username}")
    private String ftp_username;

    @Value("${ftp_password}")
    private String ftp_password;

    @Value("${ftp_timeout}")
    private Integer ftp_timeout;


    public void insert(FtpEntity entity) {
        String id = UUIDUtils.getUUID();   //生成UUID的主键
        entity.setId(id);
        ftpDao.insert(entity);
    }

    public void batchInsert(List<FtpEntity> list) {
        ftpDao.batchInsert(list);
    }

    public PaginationVO<FtpEntity> paging(PaginationVO<FtpEntity> vo, FtpEntity entity) {

        Long page = vo.getPage();
        Long pageSize = vo.getPageSize();

        Long startRow = (page - 1) * pageSize + 1;   //开始的行数
        Long endRow = pageSize * page;   //结束的行数
        vo.setStartRow(startRow);
        vo.setEndRow(endRow);

        Long totalCount = ftpDao.queryTotalCount(entity);
        List<FtpEntity> dataList = ftpDao.queryListByCondition(vo,entity);

        Long pageCount = totalCount / vo.getPageSize();
        if(totalCount % vo.getPageSize() > 0) {
            pageCount++;
        }

        vo.setTotalCount(totalCount);
        vo.setDataList(dataList);
        vo.setPageCount(pageCount);
        return vo;
    }

    public void update(FtpEntity entity) {
        ftpDao.update(entity);
    }

    public void delete(FtpEntity entity) {
        ftpDao.delete(entity);
    }

    public List<String> queryFileTypeList() {
        return ftpDao.queryFileTypeList();
    }

    public List<FtpEntity> queryByCondition(FtpEntity entity) {
        return ftpDao.queryByCondition(entity);
    }

    public List<DownloadFile> getDownloadFileList(List<FtpEntity> list) {
        //创建一个下载的文件集合
        List<DownloadFile> fileList = new ArrayList<DownloadFile>();
        for(FtpEntity entity : list) {

            //从配置文件读取相应的信息
            entity.setUsername(ftp_username);
            entity.setPassword(ftp_password);
            entity.setTimeout(ftp_timeout);

            DownloadFile file = new DownloadFile();
            //获取文件的字节数组
            byte[] arr = FtpUtil.getFileByteArr(entity);
            file.setByteDataArr(arr);
            file.setFileName(entity.getFilename());
            fileList.add(file);
        }
        return fileList;
    }
}
