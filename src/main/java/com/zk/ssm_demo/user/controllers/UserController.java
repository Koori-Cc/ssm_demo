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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Koori_Cc on 2017/8/24.
 */

@RequestMapping("/user")
@Controller("ssm_demo_user_userController")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    private Logger logger = Logger.getLogger(UserController.class);

    @Value("${nginx.imgPath}")
    private String nginxImgPath;


    @RequestMapping("/toRegister.do")
    public String toRegister() {
        return "ssm_demo/user/register";
    }

    @RequestMapping("/register.do")
    public String register(User user, @RequestParam("myPhoto") MultipartFile file) {
        String filename = "";
        //判断是否上传了文件
        if (!file.isEmpty()) {   //这个方法返回的是boolean
            File storePath = new File(nginxImgPath);
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

            File storeDir = new File(nginxImgPath, filename);

            try {
                //将文件上传到指定的目录
                file.transferTo(storeDir);
                user.setPhoto(filename);    //上传成功后,将文件名保存
            } catch (IOException e) {
                logger.info("文件上传失败!");
                e.printStackTrace();
            }

        }
        userService.register(user);
        return "redirect:/toIndex.do";   // 注意这个路径是项目的根路径,不是相对路径
    }

    @RequestMapping("/toLogin.do")
    public String toLogin() {
        return "ssm_demo/user/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public Object login(User user, HttpSession session) {
        user = userService.login(user);
        boolean result;
        if (user == null) {
            result = false;
        } else {
            session.setAttribute(KeyUtils.SESSION_USER, user);
            result = true;
        }
        return result;
    }

    /**
     * @param request
     * @return
     * @description 注销用户
     */
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        /**
         * 1、session.removeAttribute()可以指定销毁session中的某个属性
         * 2、session.invalidate()是让当前浏览器的session销毁,也就是一个session被销毁，比如用户登录后注销就用这个，
         *    因为注销意味着该用户session中的所有属性均失效
         */
        //false代表：不创建session对象，只是从request中获取
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/toIndex.do";
    }

    /**
     * @author Koori_Cc
     * @param modelMap
     * @param request
     * @description 转到菜单页面
     * @return
     */
    @RequestMapping("/toMenu.do")
    public String toMenu(ModelMap modelMap,HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute(KeyUtils.SESSION_USER);
        //查询该用户所有的角色
        List<Role> roleList = roleService.queryRoleUserRelation(user.getId());
        //根据角色查询所有的权限(无重复,提高效率)
        List<Permission> permissionList = permissionService.queryPermissionListByRoleList(roleList);
        List<String> permissionCodeList = new ArrayList<String>();
        for(int i = 0; i < permissionList.size(); i++) {
            permissionCodeList.add(permissionList.get(i).getCode());
        }
        //将权限编码存储进作用域
        modelMap.put(KeyUtils.PERMISSION,permissionCodeList);
        return "ssm_demo/user/menu";
    }

    @RequestMapping("/toList.do")
    public String toList(Model model) {
        List<User> userList = userService.queryUserList();
        model.addAttribute("userList", userList);
        return "ssm_demo/user/list";
    }

    @RequestMapping("/toChangePwd.do")     //必须要加斜杠!!
    public String toChangePwd() {
        return "ssm_demo/user/changePwd";
    }

    @RequestMapping("/changePwd.do")
    @ResponseBody
    public Object changePwd(String id, String name, String o_password, String n_password) {

        User u = new User();
        u.setId(id);
        u.setName(name);
        u.setPassword(o_password);
        u = userService.login(u);
        if (u == null) {
            return false;
        } else {
            u.setPassword(n_password);
            userService.changePwd(u);
            return true;
        }

    }

    @RequestMapping("/toUserList.do")
    public String toUserList(ModelMap modelMap,HttpServletRequest request) {
        User user = (User) request.getSession(false).getAttribute(KeyUtils.SESSION_USER);
        //查询该用户所有的角色
        List<Role> roleList = roleService.queryRoleUserRelation(user.getId());
        //根据角色查询所有的权限(无重复,提高效率)
        List<Permission> permissionList = permissionService.queryPermissionListByRoleList(roleList);
        List<String> permissionCodeList = new ArrayList<String>();
        for(int i = 0; i < permissionList.size(); i++) {
            permissionCodeList.add(permissionList.get(i).getCode());
        }
        //将权限编码存储进作用域
        modelMap.put(KeyUtils.PERMISSION,permissionCodeList);
        return "ssm_demo/user/userList";
    }


    @RequestMapping("/paging.do")
    @ResponseBody
    public Object paging(PaginationVO vo) {
        return userService.paging(vo);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Object delete(String id) {
        boolean result;
        try{
            Integer count = userService.deleteUserById(id);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @RequestMapping("/toUpdate.do")
    public String toUpdate(String id, Model model) {
        User user = userService.queryUserById(id);
        model.addAttribute("user", user);
        return "ssm_demo/user/update";
    }

    @RequestMapping("/update.do")
    public String update(User user) {
        Integer count = userService.updateUser(user);
        logger.info("修改生效人数" + count);
        return "redirect:toUserList.do";
    }

    @RequestMapping("/export.do")
    public void export(PaginationVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        vo = userService.paging(vo);
        List<User> userList = vo.getUserList();

        HSSFWorkbook wb = new HSSFWorkbook();   //创建一个excel文件
        HSSFSheet sheet = wb.createSheet("用户列表");   //创建一个页,可以起名字
        HSSFRow row = sheet.createRow(0);  //在指定的页中创建一行,从0开始
        HSSFCell cell = row.createCell(0);//在指定的行中创建列,从0开始
        cell.setCellValue("姓名");//设置列中的值
        cell = row.createCell(1);
        cell.setCellValue("年龄");
        cell = row.createCell(2);
        cell.setCellValue("住址");
        for(int i = 0;i < userList.size();i++) {
            User u = userList.get(i);
            row = sheet.createRow(i+1);
            cell = row.createCell(0);
            cell.setCellValue(u.getName());
            cell = row.createCell(1);
            cell.setCellValue(u.getAge());
            cell = row.createCell(2);
            cell.setCellValue(u.getAddress());
        }

        //设置响应类型,返回一个excel文件
        response.setContentType("application/octet;charset=UTF-8");
        //获取用户浏览器的信息,获取请求头
        //String browser = request.getHeader("Role-Agent");
        String browser = request.getHeader("User-Agent");
        String filename = "";
        if(browser.toLowerCase().contains("firefox")) {  //火狐的处理方式
            filename = new String("用户列表-火狐".getBytes("UTF-8"),"ISO8859-1");
        }else { //IE的处理方式
            filename = URLEncoder.encode("用户列表-IE", "UTF-8");
        }
        //告诉浏览器激活下载窗口,attachment表示处理方式是下载
        response.addHeader("Content-Disposition", "attachment;filename="+filename+".xls");
        //文件传输用字节流
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.close();
    }

    @RequestMapping("/import.do")
    public String importExcel(MultipartFile file) throws Exception {
        if(!file.isEmpty()) {  //上传了文件
            //把excel文件转换成HSSFWorkbook对象
            InputStream is = file.getInputStream();   //先读到文件
            HSSFWorkbook wb = new HSSFWorkbook(is);   //使用有参构造,将文件转换成对象

            //获取页(从0开始)
            HSSFSheet sheet = wb.getSheetAt(0);

            List<User> userList = new ArrayList<User>();
            //获得的不是总的数量,而是索引值,实际有13行,进行遍历的时候别漏掉最后一行
            for(int i = 1;i<sheet.getLastRowNum()+1;i++) {
                User user = new User();

                HSSFRow row = sheet.getRow(i);   //索引从0开始
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                HSSFCell cell = row.getCell(0);
                String name = cell.getStringCellValue();
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                cell = row.getCell(1);
                String s_age = cell.getStringCellValue();
                Integer age = Integer.parseInt(s_age);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                cell = row.getCell(2);
                String address = cell.getStringCellValue();
                user.setName(name);
                user.setAge(age);
                user.setAddress(address);
                userList.add(user);
            }

            Integer result = userService.importExcel(userList);
            logger.info(result);
        }
        return "redirect:toUserList.do";
    }

    @RequestMapping("/toDetail.do")
    public ModelAndView toDetail(String id) {
        User user = userService.queryUserById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject(KeyUtils.DETAIL_USER,user);
        mv.setViewName("ssm_demo/user/detail");
        return mv;
    }


    @RequestMapping("/toChangeInfo.do")
    public String toChangeInfo() {
        return "ssm_demo/user/info";
    }



}
