package com.zk.ssm_demo.user.services.impl;


import com.zk.function.eCharts.utils.ChatUtil;
import com.zk.ssm_demo.role.daos.RoleDao;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.user.daos.UserDao;
import com.zk.ssm_demo.user.entities.PaginationVO;
import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import com.zk.ssm_demo.userRoleRelation.daos.UserRoleRelationDao;
import com.zk.ssm_demo.userRoleRelation.entities.UserRoleRelation;
import com.zk.ssm_demo.utils.MD5Util;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Value("${regular_role_code}")
    private String regularRoleCode;

    public void register(User user) {
        String pwd = MD5Util.MD5(user.getPassword());
        user.setPassword(pwd);
        user.setId(UUIDUtils.getUUID());
        user.setCreateTime(new Date());
        userDao.insertUser(user);
        // 用户注册后自动分配一个普通用户的角色
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setId(UUIDUtils.getUUID());
        userRoleRelation.setUser_id(user.getId());
        Role role = roleDao.queryRoleByCode(regularRoleCode);
        userRoleRelation.setRole_id(role.getId());
        userRoleRelationDao.insert(userRoleRelation);
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
            Date createTime = u.getCreateTime();
            if(createTime != null) {
                String s_createTime = format.format(createTime);
                u.setS_createTime(s_createTime);
            }
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

    public String getEchartsList() {
        //柱状图
        List<User> users = userDao.ageCount();
        String userBar = ChatUtil.Bar(users, "title");
        return userBar;
    }

    public String showBar() {
        //柱状图
        List<User> users = userDao.ageCount();
        String userBar = ChatUtil.Bar(users, "柱状图");
        return userBar;
    }

    public String showPie() {
        //柱状图
        List<User> users = userDao.ageCount();
        String userPie = ChatUtil.pie(users, "饼图");
        return userPie;
    }

    public Integer importExcel(List<User> list) {
        for(User u : list) {
            u.setId(UUIDUtils.getUUID());
            u.setCreateTime(new Date());
            String pwd = MD5Util.MD5(u.getName());   //默认密码就是用户名
            u.setPassword(pwd);
        }
        return userDao.insertUserList(list);
    }
}
