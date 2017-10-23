package com.zk.function.ftp.utils;


import com.zk.function.ftp.entities.FtpEntity;
import com.zk.ssm_demo.utils.UUIDUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/10/11 10:23
 * @description
 */
public class ExcelUtil {

    //日志工具类
    private static Logger logger  = LoggerFactory.getLogger(ExcelUtil.class);

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static List<FtpEntity> readExcel(MultipartFile file) throws IOException{
        //检查文件
        checkFile(file);

        //获得Workbook工作薄对象(可以根据后缀判断excel的版本,从而采用不同的api,防止版本不兼容出错)
        Workbook workbook = getWorkBook(file);

        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<FtpEntity> list = new ArrayList<FtpEntity>();

        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){

                //获得当前sheet工作表(可以有多个工作簿)
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行(第一行为标题行)
                for(int rowNum = firstRowNum + 1 ; rowNum <= lastRowNum ; rowNum++){
                    FtpEntity entity = new FtpEntity();
                    //创建一个id
                    entity.setId(UUIDUtils.getUUID());
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();

                    //循环当前行
                    for(int cellNum = firstCellNum ; cellNum < lastCellNum ; cellNum++){
                        Cell cell = row.getCell(cellNum);
                        switch(cellNum) {
                            case  0:
                                entity.setIp(getCellValue(cell));
                                break;
                            case  1:
                                entity.setPort(Integer.parseInt(getCellValue(cell)));
                                break;
                            case  2:
                                entity.setPrePath(getCellValue(cell));
                                break;
                            case  3:
                                entity.setFilename(getCellValue(cell));
                                break;
                            case  4:
                                entity.setFileType(getCellValue(cell));
                                break;
                        }
                    }
                    list.add(entity);
                }
            }
            //关闭工作簿(注意jar的版本,低版本没有这个方法)
            workbook.close();
        }
        return list;
    }

    //校验文件是否存在,类型是否正确
    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            logger.error("文件未上传");
            throw new FileNotFoundException("文件未上传！");
        }
        if(file.isEmpty()) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }

    //获取对应版本的工作簿
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    //获取对应类型的单元格的值
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}