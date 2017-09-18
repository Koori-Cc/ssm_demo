package com.zk.ssm_demo.daos;

import com.zk.ssm_demo.entities.PaginationVO;
import com.zk.ssm_demo.entities.User;

import java.util.List;

/**
 * Created by Koori_Cc on 2017/8/24.
 */
public interface UserDao {

    Integer insertUser(User user);

    Integer insertUserList(List<User> list);

    User queryUser(User user);

    List<User> queryUserList();

    Integer updateUserPwd(User user);

    Long queryTotalCount(PaginationVO vo);

    List<User> queryUserListForPaging(PaginationVO vo);

    Integer deleteUserById(String id);

    User queryUserById(String id);

    Integer updateUser(User user);

    List<String> queryAddress();

}
