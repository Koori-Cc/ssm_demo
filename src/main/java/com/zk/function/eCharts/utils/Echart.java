package com.zk.function.eCharts.utils;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @description echar封装实体
 * @auther lipinxian
 * @create 2017-09-04 下午 6:17
 */
public class Echart {

    /**
     * 组装Echarts图的Option,各参数不能为null
     * @param XValue        X轴坐标
     * @param legendName    图例
     * @param mainTitle     主标题
     * @param subTitle      副标题
     * @return
     */
    public static Option getOption(String[] XValue, String[] legendName, String mainTitle , String subTitle){

        //echarts option
        Option option = new GsonOption();
        //title
        option.title(mainTitle,subTitle);
        //legend
        option.legend(legendName);
        //tooltip
        option.tooltip().trigger(Trigger.axis);
        option.tooltip().axisPointer().type(PointerType.shadow);
        //Toolbox
        Toolbox toolbox = new Toolbox();
        toolbox.show(false);
        //toolbox.feature(Tool.mark,new MagicType(Magic.line,Magic.bar),Tool.restore,Tool.saveAsImage);
        option.toolbox(toolbox);
        //calculable
        option.calculable(true);
        //xAxis 间隔
        CategoryAxis xaxis = new CategoryAxis();
        xaxis.type(AxisType.category);
        if(XValue.length>=288){
            xaxis.axisLabel().interval(25);
        }else if(XValue.length>=144&&XValue.length<288){
            xaxis.axisLabel().interval(15);
        }else if(XValue.length>=96&&XValue.length<144){
            xaxis.axisLabel().interval(0);
        }else if(XValue.length>=48&&XValue.length<96){
            xaxis.axisLabel().interval(6);
        }else if(XValue.length>=30&&XValue.length<48){
            xaxis.axisLabel().interval(0);
        }
        xaxis.axisLabel().formatter("function(value){if(value.length>10) return value.substring(0,10)+'...';else return value;}");
        xaxis.data(XValue);
        if( "按卡口统计TOP10".equals(mainTitle)){
            xaxis.axisLabel().rotate(45);
        }
        else if("按卡口统计详情".equals(mainTitle)){
            xaxis.axisLabel().rotate(45);
        }else{
            xaxis.axisLabel().rotate(0);}
        option.xAxis(xaxis);
        //默认颜色
        if(legendName.length==1){
            option.color("#FE8463");
        }
        //yAxis
        ValueAxis yaxis = new ValueAxis();
        yaxis.type(AxisType.value);
		/*//设置图标区间颜色
		AreaStyle areaStyle = new AreaStyle();
		Object color = new String[]{"#fe0000","#20B2AA","#008001","#ffff00","#fea500","#98cb00"};
		areaStyle.setColor(color);
		yaxis.splitArea().show(true);
		yaxis.splitArea().areaStyle(areaStyle);*/
        option.yAxis(yaxis);
        return option;
    }


    /**
     * 日统计，24小时X轴坐标，返回一数组
     * @param XValue            X轴坐标
     * @return
     */

    public static String[] xAxisHour(String[] XValue){
        //拼装X坐标信息
        List<String> XNames = new ArrayList<String>();
        for(int i=0;i<24;i++){
            int len = String.valueOf(i).length();
            if(len==1){
                XNames.add("0" + i + " 时");
            }else{
                XNames.add(i + " 时");
            }
        }
        XValue=new String[XNames.size()];
        for (int i = 0; i <XNames.size(); i++){
            XValue[i]=XNames.get(i);
        }
        return XValue;
    }

    /**
     * 月统计，以30天或31天为X轴坐标，返回一数组
     * @param XValue         X轴坐标
     * @param time           str年月字符串 如:2015-01
     * @return
     */

    public static String[] xAxisDay(String[] XValue, String time){
        //拼装X坐标信息
        int count = DateUtil.getDaysOfMonth(time);  //根据年月获取该月天数
        List<String> XNames = new ArrayList<String>();
        for(int i=1;i<count+1;i++){
            int len = String.valueOf(i).length();
            if(len==1){
                XNames.add("0" + i + " 日");
            }else{
                XNames.add(i + " 日");
            }
        }
        XValue=new String[XNames.size()];
        for (int i = 0; i <XNames.size(); i++){
            XValue[i]=XNames.get(i);
        }
        return XValue;
    }

    /**
     * 年统计，以12月为X轴坐标，返回一数组
     * @param XValue         X轴坐标
     * @return
     */

    public static String[] xAxisMonth(String[] XValue){
        //拼装X坐标信息
        List<String> XNames = new ArrayList<String>();
        for(int i=1;i<13;i++){
            int len = String.valueOf(i).length();
            if(len==1){
                XNames.add("0" + i + " 月");
            }else{
                XNames.add(i + " 月");
            }
        }
        XValue=new String[XNames.size()];
        for (int i = 0; i <XNames.size(); i++){
            XValue[i]=XNames.get(i);
        }
        return XValue;
    }
}
