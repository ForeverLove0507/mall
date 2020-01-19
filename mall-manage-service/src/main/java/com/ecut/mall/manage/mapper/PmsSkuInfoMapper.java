package com.ecut.mall.manage.mapper;

import com.ecut.mall.beans.PmsSkuInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuListBySpu(PmsSkuInfo pmsSkuInfo);
}
