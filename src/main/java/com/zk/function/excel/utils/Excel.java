package com.zk.function.excel.utils;


import com.zk.function.excel.entities.User;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/11 10:23
 * @description
 */
public class Excel {
    /** 标题单元格格式 */
    private static WritableCellFormat titleFormat=null;
    /** 主题内容单元格格式 */
    private static WritableCellFormat bodyFormat=null;
    /** 注释单元格格式 */
    private static WritableCellFormat noteFormat=null;
    /** 浮点型数据的单元格格式 */
    private static WritableCellFormat floatFormat=null;
    /** 整型数据的单元格格式 */
    private static WritableCellFormat intFormat=null;
    /** 初始化数据 */
    private static boolean init=false;

    /** 私有构造方法，防止错误使用Excel类 */
    private Excel(){
    }

    /**
     * 初始化各单元格格式
     * @throws WriteException 初始化失败
     */
    private static void init() throws WriteException{
        WritableFont font1,font2,font3,font4;

        //Arial字体，9号，粗体，单元格黄色，田字边框，居中对齐
        font1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD, false);
        titleFormat = new WritableCellFormat (font1);
        titleFormat.setBackground(Colour.YELLOW);
        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        titleFormat.setAlignment(Alignment.CENTRE);

        //Arial字体，9号，粗体，单元格黄色，田字边框，左右居中对齐，垂直居中对齐，自动换行
        font2 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD, false);
        noteFormat = new WritableCellFormat (font2);
        noteFormat.setBackground(Colour.YELLOW);
        noteFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        noteFormat.setAlignment(Alignment.CENTRE);
        noteFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        noteFormat.setWrap(true);

        //Arial字体，9号，非粗体，单元格淡绿色，田字边框,居中
        font3 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false);
        bodyFormat = new WritableCellFormat (font3);
        bodyFormat.setBackground(Colour.LIGHT_GREEN);
        bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        bodyFormat.setAlignment(Alignment.CENTRE);

        //Arial字体，9号，非粗体，单元格淡绿色，田字边框
        font4 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false);
        floatFormat = new WritableCellFormat (font4,NumberFormats.FLOAT);
        floatFormat.setBackground(Colour.LIGHT_GREEN);
        floatFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        //Arial字体，9号，非粗体，单元格淡绿色，田字边框
        font4 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false);
        intFormat = new WritableCellFormat (font4,NumberFormats.INTEGER);
        intFormat.setBackground(Colour.LIGHT_GREEN);
        intFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        init = true;
    }

    public static void createRespDataExcelFile(List<User> dataList, File destFile) throws WriteException, IOException{
        if(init==false) init();
        int index,row;
        WritableSheet sheet=null;
        WritableWorkbook book=null;
        book = Workbook.createWorkbook(destFile);
        sheet = book.createSheet("货车", 0);
        //设置每一列的列宽
        sheet.setColumnView(0, 15);
        sheet.setColumnView(1, 10);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);

        //标题栏
        index=0; //0代表第0列
        sheet.addCell(new Label(index++,0,"编号",titleFormat));  // 0 代表第一行
        sheet.addCell(new Label(index++,0,"姓名",titleFormat));
        sheet.addCell(new Label(index++,0,"年龄",titleFormat));
        sheet.addCell(new Label(index++,0,"住址",titleFormat));

        //从第二行开始是数据信息
        row=1;
        for(User u : dataList){
            if(u == null) continue;
            index=0;
            sheet.addCell(new Number(index++,row,u.getNo(),bodyFormat));
            sheet.addCell(new Label(index++,row,u.getName(),bodyFormat));
            sheet.addCell(new Number(index++,row,u.getAge(),bodyFormat));
            sheet.addCell(new Label(index++,row,u.getAddress(),bodyFormat));
            row++;
        }
        book.write();
        if(book!=null) book.close();
    }

    public static List<User> readUserExcelFile(File file) throws IOException, BiffException {
        if(file == null)
            return null;
        int row,column;
        String temp = null;
        Workbook book = null;
        Sheet sheet = null;
        List<User> userList = new ArrayList<User>();
        book = Workbook.getWorkbook(file);
        //获取第一个表格
        sheet = book.getSheet(0);
        //从第二行开始
        row = 1;
        while(row < sheet.getRows()){
            //从第一列开始解析
            column = 0;
            User user = new User();

            //No
            temp = sheet.getCell(column++,row).getContents().trim();
            //正则判断是否是非负整数
            if(temp != null && !temp.equals("") && temp.matches("^\\d+$"))
                user.setNo(Integer.parseInt(temp));

            //name
            temp = sheet.getCell(column++,row).getContents().trim();
            if(temp != null && !temp.equals(""))
                user.setName(temp);

            //age
            temp=sheet.getCell(column++,row).getContents().trim();
            //正则判断是否是非负整数
            if(temp != null && !temp.equals("") && temp.matches("^\\d+$"))
                user.setAge(Integer.parseInt(temp));

            //address
            temp=sheet.getCell(column++,row).getContents().trim();
            if(temp != null && !temp.equals(""))
                user.setAddress(temp);

            userList.add(user);
            row++;
        }
        if(book != null) book.close();
        return userList;
    }
}