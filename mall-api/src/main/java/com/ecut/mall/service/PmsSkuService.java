package com.ecut.mall.service;

import com.ecut.mall.beans.PmsSkuInfo;

import java.util.List;

public interface PmsSkuService {
    String saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuInfo(String skuId);

    List<PmsSkuInfo> getSkuListBySpu(String productId);

    List<PmsSkuInfo> getAllSku(String catalog3Id);
}
