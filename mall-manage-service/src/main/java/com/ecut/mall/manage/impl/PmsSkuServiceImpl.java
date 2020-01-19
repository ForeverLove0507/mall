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
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        pmsSkuInfoMapper.insert(pmsSkuInfo);
        // 获得主键返回
        String skuId = pmsSkuInfo.getId();

        // 保存图片配置信息
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        // 销售属性和销售属性值
        List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }


        return "success";
    }

    @Override
    public PmsSkuInfo getSkuInfo(String skuId) {
        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        //链接缓存

        //查询缓存,如果缓存中没有，则查询数据库

        /*// 查询skuInfo
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);

        // 查询skuImage并临时存到skuInfo中
        PmsSkuImage pmsSkuImage=new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImageSelect = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(pmsSkuImageSelect);

        // 查询skuSaleAttrValue并临时存到skuInfo中
        PmsSkuSaleAttrValue pmsSkuSaleAttrValue=new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValue.setSkuId(skuId);
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueSelect = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
        pmsSkuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueSelect);*/

        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuListBySpu(String productId) {
        PmsSkuInfo pmsSkuInfo=new PmsSkuInfo();
        pmsSkuInfo.setProductId(productId);
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuListBySpu(pmsSkuInfo);
        return pmsSkuInfos;
    }
}
