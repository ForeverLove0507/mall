package com.ecut.mall.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.PmsSearchInfo;
import com.ecut.mall.beans.PmsSearchParam;
import com.ecut.mall.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchController {
    @Reference
    SearchService searchService;

    @RequestMapping("list.html")
    public  String list(PmsSearchParam pmsSearchParam, ModelMap modelMap){
        // 调用搜索服务，返回搜索结果
        List<PmsSearchInfo> pmsSearchInfos = searchService.list(pmsSearchParam);
        modelMap.put("skuLsInfoList",pmsSearchInfos);
        return  "list";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

}
