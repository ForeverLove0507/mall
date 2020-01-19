package com.ecut.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ecut.mall.beans.PmsProductSaleAttr;
import com.ecut.mall.beans.PmsSkuAttrValue;
import com.ecut.mall.beans.PmsSkuInfo;
import com.ecut.mall.beans.PmsSkuSaleAttrValue;
import com.ecut.mall.service.PmsSkuService;
import com.ecut.mall.service.PmsSpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin
public class ItemController {
    @Reference
    PmsSkuService pmsSkuService;
    @Reference
    PmsSpuService pmsSpuService;

    @RequestMapping("/{skuId}.html")
    public String getSkuInfo(@PathVariable("skuId") String skuId, ModelMap map){
        // 查询商品sku和图片信息
        PmsSkuInfo pmsSkuInfo=pmsSkuService.getSkuInfo(skuId);
        map.addAttribute("skuInfo",pmsSkuInfo);

        // 查询商品销售属性并且标记当前sku
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsSpuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), pmsSkuInfo.getId());
        map.addAttribute("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        // 将sku销售属性与产品id对应关系存入hash表中，然后放在页面，方便用户在不同的销售属性来回切换，减少一次与数据库交互
        HashMap<String,String> skuAttrValueHash=new HashMap<>();
        List<PmsSkuInfo> skuList = pmsSkuService.getSkuListBySpu(pmsSkuInfo.getProductId());
        for (PmsSkuInfo skuInfo:skuList){
            String k="";
            String v=skuInfo.getId();
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValue = skuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue skuSaleAttrValue : pmsSkuSaleAttrValue) {
                String saleAttrValueId = skuSaleAttrValue.getSaleAttrValueId();
                k+=saleAttrValueId+"|";
            }
            skuAttrValueHash.put(k,v);
        }
        // 将Hash表放到页面
        String skuSaleAttrValueJsonStr= JSON.toJSONString(skuAttrValueHash);
        map.put("skuSaleAttrValueJsonStr",skuSaleAttrValueJsonStr);

        return "item";
    }


}
