package com.ecut.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.PmsSkuInfo;
import com.ecut.mall.service.PmsSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class PmsSkuController {
    @Reference
    PmsSkuService pmsSkuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        String success=pmsSkuService.saveSkuInfo(pmsSkuInfo);
        return success;
    }
}
