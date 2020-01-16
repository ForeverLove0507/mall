package com.ecut.mall.service;

import com.ecut.mall.beans.PmsBaseSaleAttr;
import com.ecut.mall.beans.PmsProductImage;
import com.ecut.mall.beans.PmsProductInfo;
import com.ecut.mall.beans.PmsProductSaleAttr;

import java.util.List;

public interface PmsSpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    String saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(String spuId);
}
