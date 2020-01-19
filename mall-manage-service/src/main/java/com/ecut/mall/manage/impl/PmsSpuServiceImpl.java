package com.ecut.mall.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ecut.mall.beans.*;
import com.ecut.mall.manage.mapper.*;
import com.ecut.mall.service.PmsSpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PmsSpuServiceImpl implements PmsSpuService {
    @Autowired
    PmsSpuMapper pmsSpuMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    PmsSpuSaleAttrMapper pmsSpuSaleAttrMapper;
    @Autowired
    PmsSpuImageMapper pmsSpuImageMapper;
    @Autowired
    PmsSpuSaleAttrValueMapper pmsSpuSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo=new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsSpuMapper.select(pmsProductInfo);
        return pmsProductInfos;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }

    // 显示商品销售属性
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr=new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsSpuSaleAttrMapper.select(pmsProductSaleAttr);

        // 查询商品销售属性值
        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue=new PmsProductSaleAttrValue();
            // 这里的销售属性id指的是平台规定的销售属性id，不是prodectSaleAttr中的主键id
            pmsProductSaleAttrValue.setProductId(productSaleAttr.getProductId());
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> select = pmsSpuSaleAttrValueMapper.select(pmsProductSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(select);
        }
        return pmsProductSaleAttrs;
    }

    @Override
    public String saveSpuInfo(PmsProductInfo pmsProductInfo) {
        // 保存商品信息
        pmsSpuMapper.insertSelective(pmsProductInfo);

        // 拿到生成的商品主键
        String productId=pmsProductInfo.getId();

        // 保存商品图片信息
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for(PmsProductImage pmsProductImage:spuImageList){
            pmsProductImage.setProductId(productId);
            pmsSpuImageMapper.insertSelective(pmsProductImage);
        }

        // 保存销售属性、保存销售属性值
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr:spuSaleAttrList){
            pmsProductSaleAttr.setProductId(productId);
            pmsSpuSaleAttrMapper.insertSelective(pmsProductSaleAttr);
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(pmsProductSaleAttr.getProductId());
                pmsSpuSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }
        return "success";
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage pmsProductImage=new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        List<PmsProductImage> pmsProductImages = pmsSpuImageMapper.select(pmsProductImage);
        return pmsProductImages;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsSpuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId, skuId);
        return pmsProductSaleAttrs;
    }
}
