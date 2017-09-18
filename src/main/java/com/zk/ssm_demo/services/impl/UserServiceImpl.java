package com.zk.ssm_demo.services.impl;


import com.zk.ssm_demo.daos.UserDao;
import com.zk.ssm_demo.entities.PaginationVO;
import com.zk.ssm_demo.entities.User;
import com.zk.ssm_demo.services.UserService;
import com.zk.ssm_demo.utils.MD5Util;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public void register(User user) {
        String pwd = MD5Util.MD5(user.getPassword());
        user.setPassword(pwd);
        user.setId(UUIDUtils.getUUID());
        user.setCreateTime(new Date());
        userDao.insertUser(user);
    }

    public User login(User user) {
        String pwd = MD5Util.MD5(user.getPassword());
        user.setPassword(pwd);
        return userDao.queryUser(user);
    }


    public List<User> queryUserList() {
        return userDao.queryUserList();
    }

    public Integer changePwd(User user) {
        String pwd = MD5Util.MD5(user.getPassword());
        user.setPassword(pwd);
        return userDao.updateUserPwd(user);
    }

    public PaginationVO paging(PaginationVO vo) {

        List<User> userList = userDao.queryUserListForPaging(vo);

        for(User u : userList) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s_createTime = format.format(u.getCreateTime());
            u.setS_createTime(s_createTime);
        }

        Long totalCount = userDao.queryTotalCount(vo);

        Long pageCount = totalCount / vo.getPageSize();
        if(totalCount % vo.getPageSize() > 0) {
            pageCount++;
        }

        vo.setTotalCount(totalCount);
        vo.setUserList(userList);
        vo.setPageCount(pageCount);
        return vo;
    }


    public Integer deleteUserById(String id) {
        User u = userDao.queryUserById(id);
        String photo = u.getPhoto();
        if(photo != null && !"".equals(photo)) {
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            String dir = bundle.getString("nginx.imgPath") + File.separator + photo;
            File file = new File(dir);
            file.delete();   //删除上传的图片
        }
        return userDao.deleteUserById(id);
    }

    public User queryUserById(String id) {
        return userDao.queryUserById(id);
    }

    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    public List<String> queryAddress() {
        return userDao.queryAddress();
    }

    public Integer importExcel(List<User> list) {
        for(User u : list) {
            u.setId(UUIDUtils.getUUID());
        }
        return userDao.insertUserList(list);
    }
}
