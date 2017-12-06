package com.zk.function.ztree.controllers;

import com.zk.function.ztree.entities.Ztree;
import com.zk.function.ztree.entities.ZtreeEntity;
import com.zk.function.ztree.services.ZtreeService;
import com.zk.function.ztree.utils.ObjectToEntity;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/12/5 9:06
 * @description
 */
@Controller("function_ztree_controller")
@RequestMapping("/function/zTree")
public class ZtreeController {

    private Logger logger = LoggerFactory.getLogger(ZtreeController.class);

    @Autowired
    private ZtreeService ztreeService;

    @RequestMapping("/toList.do")
    public String toList(ModelMap map) {
        List<Ztree> list = ztreeService.getAllList();
        for(int i = 0;i < list.size();i++) {
            list.get(i).setOpen(true);
        }
        JSONArray json = JSONArray.fromObject(list);
        map.put("dataList",json);
        return "function/ztree/list";
    }




}
