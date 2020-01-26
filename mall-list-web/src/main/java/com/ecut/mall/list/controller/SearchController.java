package com.ecut.mall.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.*;
import com.ecut.mall.service.PmsBaseAttrService;
import com.ecut.mall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    @Reference
    SearchService searchService;
    @Reference
    PmsBaseAttrService pmsBaseAttrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap) {
        // 调用搜索服务，返回搜索结果
        List<PmsSearchInfo> pmsSearchInfos = searchService.list(pmsSearchParam);
        modelMap.put("skuLsInfoList", pmsSearchInfos);

        // 抽取返回的商品中包含平台属性集合
        Set<String> valueIdSet = new HashSet<>();
        for (PmsSearchInfo pmsSearchInfo : pmsSearchInfos) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                valueIdSet.add(valueId);

            }
        }
        // 根据valueId将属性列表查询出来
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrService.getAttrValueListByValueId(valueIdSet);
        modelMap.put("attrList", pmsBaseAttrInfos);

        // 对平台属性集合进一步处理，删除选中的属性值所在的属性组
        String[] delValueIds = pmsSearchParam.getValueId();
        if (delValueIds!=null){
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                    String valueId = pmsBaseAttrValue.getId();
                    for (String delValueId : delValueIds) {
                        if (delValueId.equals(valueId)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }


        // 封装请求地址参数
        String urlParam = getUrlParam(pmsSearchParam);
        modelMap.put("urlParam", urlParam);
        return "list";
    }

    private String getUrlParam(PmsSearchParam pmsSearchParam) {
        String urlParam = "";
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueIds = pmsSearchParam.getValueId();

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;

        }
        if (valueIds != null) {
            for (String valueId : valueIds) {
                urlParam = urlParam + "&valueId=" + valueId;
            }
        }
        return urlParam;
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
