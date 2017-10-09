package com.zk.ssm_demo.user.controllers;

import com.zk.ssm_demo.permission.entities.Permission;
import com.zk.ssm_demo.permission.services.PermissionService;
import com.zk.ssm_demo.role.entities.Role;
import com.zk.ssm_demo.role.services.RoleService;
import com.zk.ssm_demo.user.entities.PaginationVO;
import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import com.zk.ssm_demo.utils.KeyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author panbing@supcon.com
 * @create 2017/9/13 9:19
 * @description
 */
@Controller("ssm_demo_user_kendoController")
@RequestMapping("/kendo")
public class KendoController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    private Logger logger = Logger.getLogger(KendoController.class);

    @ModelAttribute("prepareUser")
    private User prepareUser() {
        return new User();
    }


    @RequestMapping("/toList.do")
    public String toList(ModelMap modelMap, HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute(KeyUtils.SESSION_USER);
        //查询该用户所有的角色
        List<Role> roleList = roleService.queryRoleUserRelation(user.getId());
        //根据角色查询所有的权限
        List<Permission> permissionList = permissionService.queryPermissionListByRoleList(roleList);
        List<String> permissionCodeList = new ArrayList<String>();
        for(int i = 0; i < permissionList.size(); i++) {
            permissionCodeList.add(permissionList.get(i).getCode());
        }
        //将权限编码存储进作用域
        modelMap.put(KeyUtils.PERMISSION,permissionCodeList);
        return "ssm_demo/user/kendoList";
    }

    @RequestMapping(value="/list.do",method = RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestBody PaginationVO vo) {     //这里加上 @RequestBody 才能接收参数,转换json格式
        Long page = vo.getPage();
        vo.setPageNo(page);
        vo = userService.paging(vo);

        return vo;
    }

    @RequestMapping(value="/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(@RequestBody User user) {   //前台传过来的是json格式的字符串!!
        String id = user.getId();
        boolean result;
        try{
            userService.deleteUserById(id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @RequestMapping(value="/edit.do",method = RequestMethod.POST)
    @ResponseBody
    public void edit(@RequestBody User user) {
        Integer result = userService.updateUser(user);
    }

    @RequestMapping(value="/add.do",method = RequestMethod.POST)
    @ResponseBody
    public void add(@RequestBody User user) {          //返回值是void,也必须加 @ResponseBody ,不然会找不到路径
        userService.register(user);
    }


    @RequestMapping(value="/detail.do",method = RequestMethod.POST)
    @ResponseBody
    public Object detail(@RequestBody User user) {
        User u = userService.queryUserById(user.getId());
        return u;
    }

    @RequestMapping(value="/address.do",method = RequestMethod.POST)
    @ResponseBody
    public Object address() {
        List<User> list = userService.queryUserList();
        return list;
    }

    @RequestMapping(value="/addressList.do",method = RequestMethod.POST)
    @ResponseBody
    public Object addressList() {
        List<String> list = userService.queryAddress();
        return list;
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value="/upload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file) {    //返回值的类型一定要对应!!不能写Object,否则前台接收数据会出问题
        Map<String, Object> map = new HashMap<String, Object>();
        String filename = "";
        boolean result = false;
        if(!file.isEmpty()) {   //说明上传了文件
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            String path = bundle.getString("nginx.imgPath");
            File storePath = new File(path);
            if(!storePath.exists()) {
                storePath.mkdirs();
            }
            filename = file.getOriginalFilename();
            //按照一定的规则对文件进行重新命名,常用UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //取出原来的扩展名
            String extName = filename.substring(filename.lastIndexOf("."));
            //重新命名文件的名称
            filename = uuid + extName;
            //把文件保存到服务器
            File storeDir = new File(path, filename);
            try {
                //将文件上传到指定的目录
                file.transferTo(storeDir);
                result = true;
            } catch (IOException e) {
                logger.info("文件上传失败!");
                e.printStackTrace();
                result = false;
            }
        }
        map.put("success",result);
        map.put("filename",filename);
        return map;
    }



}
