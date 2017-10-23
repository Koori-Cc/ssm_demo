package com.zk.function.ftp.daos;

import com.zk.function.ftp.entities.FtpEntity;
import com.zk.function.common.entities.PaginationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/13 8:58
 * @description
 */
public interface FtpDao {

    void insert(FtpEntity entity);

    void batchInsert(List<FtpEntity> list);

    Long queryTotalCount(FtpEntity entity);

    List<FtpEntity> queryListByCondition(@Param("vo") PaginationVO<FtpEntity> vo, @Param("entity") FtpEntity entity);

    void update(FtpEntity entity);

    void delete(FtpEntity entity);

    List<String> queryFileTypeList();

    List<FtpEntity> queryByCondition(FtpEntity entity);

}
