package com.ecut.mall.service;

import com.ecut.mall.beans.PmsSearchInfo;
import com.ecut.mall.beans.PmsSearchParam;

import java.util.List;

public interface SearchService {
    List<PmsSearchInfo> list(PmsSearchParam pmsSearchParam);

}
