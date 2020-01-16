package com.ecut.mall.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ecut.mall.beans.PmsSkuAttrValue;
import com.ecut.mall.beans.PmsSkuImage;
import com.ecut.mall.beans.PmsSkuInfo;
import com.ecut.mall.beans.PmsSkuSaleAttrValue;
import com.ecut.mall.manage.mapper.*;
import com.ecut.mall.service.PmsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class PmsSkuServiceImpl implements PmsSkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        // 保存Sku销售属性信息
        pmsSkuInfoMapper.insert(pmsSkuInfo);
        // 获得主键返回
        String skuId = pmsSkuInfo.getId();

        // 保存图片配置信息
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getPmsSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        // 销售属性和销售属性值
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getPmsSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getPmsSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }


        return "success";
    }
}
