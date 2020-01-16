package com.ecut.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.PmsBaseAttrInfo;
import com.ecut.mall.beans.PmsBaseAttrValue;
import com.ecut.mall.service.PmsBaseAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class PmsAttrController {
    @Reference
    PmsBaseAttrService pmsBaseAttrService;

    // 显示商品属性信息
    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    // 获取某个属性的属性值
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }

    // 保存商品属性、属性值
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        String succuss=pmsBaseAttrService.saveAttrInfo(pmsBaseAttrInfo);
        return succuss;
    }

}
