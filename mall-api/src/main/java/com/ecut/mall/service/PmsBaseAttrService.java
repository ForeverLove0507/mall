package com.ecut.mall.service;

import com.ecut.mall.beans.PmsBaseAttrInfo;
import com.ecut.mall.beans.PmsBaseAttrValue;

import java.util.List;

public interface PmsBaseAttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
}
