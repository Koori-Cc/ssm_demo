package com.zk.function.excel.main;

import com.zk.function.excel.entities.User;
import com.zk.function.excel.utils.Excel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author panbing@supcon.com
 * @create 2017/10/12 16:09
 * @description
 */
public class TestExcel {

    public static void main(String args[]) {

        //测试创建excel文件
//        createExcel();

        //测试读取excel文件
        readExcel();

    }


    public static void createExcel() {
        List<User> userList = new ArrayList<User>();
        for(int i = 1 ; i <= 20 ; i ++) {
            User u = new User();
            u.setNo(i);
            u.setName(i + "");
            u.setAge(i);
            u.setAddress(i + "");
            userList.add(u);
        }
        File file = new File("D:/[work]/temp/file/user.xls");
        try {
            Excel.createRespDataExcelFile(userList,file);
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readExcel() {
        File file = new File("D:/[work]/temp/file/user.xls");
        try {
            List<User> userList = Excel.readUserExcelFile(file);
            for (int i = 0; userList != null && i < userList.size() ; i ++) {
                System.out.println(userList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

}
