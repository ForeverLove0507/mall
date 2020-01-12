package com.ecut.mall.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ecut.mall.beans.PmsBaseCatalog1;
import com.ecut.mall.beans.PmsBaseCatalog2;
import com.ecut.mall.beans.PmsBaseCatalog3;
import com.ecut.mall.manage.mapper.PmsBaseCatalog1Mapper;
import com.ecut.mall.manage.mapper.PmsBaseCatalog2Mapper;
import com.ecut.mall.manage.mapper.PmsBaseCatalog3Mapper;
import com.ecut.mall.service.PmsBaseCatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PmsBaseCatalogImpl implements PmsBaseCatalogService {
    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1s=pmsBaseCatalog1Mapper.selectAll();
        return pmsBaseCatalog1s;
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String id) {
        PmsBaseCatalog2 pmsBaseCatalog2=new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(id);
        List<PmsBaseCatalog2> pmsBaseCatalog2s=pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
        return pmsBaseCatalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String id) {
        PmsBaseCatalog3 pmsBaseCatalog3=new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2_id(id);
        List<PmsBaseCatalog3> pmsBaseCatalog3s=pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
        return pmsBaseCatalog3s;
    }
}
