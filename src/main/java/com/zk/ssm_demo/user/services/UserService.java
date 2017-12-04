package com.zk.ssm_demo.user.services;

import com.zk.ssm_demo.user.entities.PaginationVO;
import com.zk.ssm_demo.user.entities.User;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface UserService {

    void register(User user);

    Integer importExcel(List<User> list);

    User login(User user);

    List<User> queryUserList();

    Integer changePwd(User user);

    PaginationVO paging(PaginationVO vo);

    Integer deleteUserById(String id);

    User queryUserById(String id);

    Integer updateUser(User user);

    List<String> queryAddress();

    String getEchartsList();

    String showBar();

    String showPie();

}
