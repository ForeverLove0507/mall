package com.ecut.mall.service;

import com.ecut.mall.beans.PmsBaseCatalog1;
import com.ecut.mall.beans.PmsBaseCatalog2;
import com.ecut.mall.beans.PmsBaseCatalog3;

import java.util.List;

public interface PmsBaseCatalogService {
    List<PmsBaseCatalog1> getCatalog1();
    List<PmsBaseCatalog2> getCatalog2(String id);
    List<PmsBaseCatalog3> getCatalog3(String id);
}
