package com.zk.function.ftp.services;

import com.zk.function.ftp.entities.DownloadFile;
import com.zk.function.ftp.entities.FtpEntity;
import com.zk.function.common.entities.PaginationVO;

import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/13 8:59
 * @description
 */
public interface FtpService {

    void insert(FtpEntity entity);

    void batchInsert(List<FtpEntity> list);

    PaginationVO<FtpEntity> paging(PaginationVO<FtpEntity> vo, FtpEntity entity);

    void update(FtpEntity entity);

    void delete(FtpEntity entity);

    List<String> queryFileTypeList();


    List<FtpEntity> queryByCondition(FtpEntity entity);


    List<DownloadFile> getDownloadFileList(List<FtpEntity> list);

}
