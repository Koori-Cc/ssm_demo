package com.zk.function.eCharts.utils;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Toolbox;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.SeriesData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.MarkLine;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.ItemStyle;
import com.zk.ssm_demo.user.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 图表数据封装工具类
 * @auther lipinxian
 * @create 2017-09-04 下午 3:55
 */
public class ChatUtil {

    /**
     * 柱状图
     * @param barList
     * @param mainTitle
     * @return
     */
    public static String Bar(List<User> barList, String mainTitle){
        /*****准备数据*****/
        int size = barList.size();
        // X坐标信息
        String[] XNames = new String[size];
        // Y坐标信息
        Long[] YNames = new Long[size];
        //series数据类型，生成series data里面的itemStyle
        SeriesData[] seriesData = new SeriesData[size];
        //新建一个颜色数组，供柱状图条形颜色循环使用
        String[] color = new String[]{"#63B2ED","#76DA91","#F7CC7E","#F79683","#7CD5CF"};
        // series
        List<Series> series = new ArrayList<Series>(size);
        // line
        Line line = new Line();

       /*****封装数据****/
        for(int i = 0; i < barList.size();i++){
            XNames[i] = barList.get(i).getAge() + "";   //以年龄为横坐标
            YNames[i] = barList.get(i).getAgeCount();   //以数量为纵坐标
            ItemStyle itemStyle = new ItemStyle();
            itemStyle.normal().setColor(color[i%color.length]);
            seriesData[i] = new SeriesData(YNames[i], itemStyle);
        }
        line.name("数量");
        line.type(SeriesType.bar);
        line.data(seriesData);
        MarkLine ml = new MarkLine();
        ml.data(new com.github.abel533.echarts.data.Data().type(
        MarkType.average).name("平均值"));
        line.markLine(ml);
        series.add(line);
        String[] legendName = "".split(",");
        Option option = Echart.getOption(XNames, legendName, mainTitle,	"");
        //整体背景色
        // option.setBackgroundColor("#ffffff");
        // 控制 X,Y轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        option.grid().x(70);
        option.grid().x2(70);
        option.grid().y(40);  //设置横坐标名称和边界的距离
        option.grid().y2(115);
        // 加入series
        option.series(series);
        return GsonUtil.format(option);
    }


    /**
     * 饼图
     * @param barList
     * @param title
     * @return
     */
    public static String pie(List<User> barList, String title){
        String Name[] = null;
        com.github.abel533.echarts.data.Data data[] = null ;
        if(barList.size() > 5){// 简图只显示6项，除了前5，后面的统计合并到其他。
            Name = new String[6];     //模块的名称
            data = new com.github.abel533.echarts.data.Data[6];       //数据的内容
            Long Num[] = new Long[6];    //数据的数量
            Num[5] = (long) 0;
            for (int i = 0; i < barList.size(); i++) {
                if (i < 5) {
                    Name[i] = barList.get(i).getAge() + "";
                    Num[i] = barList.get(i).getAgeCount();
                    data[i] = new com.github.abel533.echarts.data.Data(Name[i],Num[i]);
                } else {
                    Name[5] = "其他";
                    Num[5] += barList.get(i).getAgeCount();
                    data[5] = new com.github.abel533.echarts.data.Data(Name[5], Num[5]);
                }
            }
        }else if(barList.size()==0){//表示无数据时，用暂无代替
            Name = new String[1];
            data = new com.github.abel533.echarts.data.Data[1];
            Object Num[] = new Object[1];
            Name[0] = "无";
            Num[0] = '-';
            data[0] = new com.github.abel533.echarts.data.Data(Name[0],Num[0]);
        }else{//表示少于5条数据，则实际几条则显示几条
            Name = new String[barList.size()];
            data = new com.github.abel533.echarts.data.Data[barList.size()];
            Long Num[] = new Long[barList.size()];
            for (int i = 0; i < barList.size(); i++) {
                Name[i] = barList.get(i).getAge() + "";
                Num[i] = barList.get(i).getAgeCount();
                //第一个参数必须是字符串,否则显示会出错;第二个参数是数量
                data[i] = new com.github.abel533.echarts.data.Data(Name[i],Num[i]);
            }
        }

        Option option = new GsonOption();
        //option.setBackgroundColor("#ffffff");
        //option.color("#63B2ED", "#76DA91", "#F7CC7E", "#F79683", "#7CD5CF");

        // title主标题，副标题
        option.title(title);
        // tooltip
        option.tooltip().trigger(Trigger.item);
        option.tooltip().axisPointer().type(PointerType.shadow);
        option.tooltip().formatter("{a} <br/>{b} : {c} ({d}%)");
        // legend副标题图例
        option.legend().data(Name);
        option.legend().orient(Orient.vertical);
        option.legend().x(X.left);
        option.legend().y(Y.bottom);
        // Toolbox工具箱
        Toolbox toolbox = new Toolbox();
        toolbox.show(false);
        // toolbox.feature(Tool.mark,Tool.restore,Tool.saveAsImage);
        option.toolbox(toolbox);
        // calculable
        option.calculable(true);  /////

        // series 饼图圆心
        Pie pie1 = new Pie("数量");
        pie1.center("55%", "50%");
        pie1.radius("56%");
        pie1.data(data);
        // pie1.itemStyle().normal().label().position("inner");
        pie1.itemStyle().normal().label().formatter("{a}{b}{c}{d}");
        option.series(pie1);
        return GsonUtil.format(option);
    }

}
