package com.zk.ssm_demo.user.controllers;

import com.zk.ssm_demo.user.entities.PaginationVO;
import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.user.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private Logger logger = Logger.getLogger(KendoController.class);

    @ModelAttribute("prepareUser")
    private User prepareUser() {
        return new User();
    }


    @RequestMapping("/toList.do")
    public String toList() {
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
    public void delete(@RequestBody User user) {   //前台传过来的是json格式的字符串!!
        String id = user.getId();
        Integer result = userService.deleteUserById(id);
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
