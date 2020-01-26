package com.ecut.mall.list;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.PmsSearchInfo;
import com.ecut.mall.beans.PmsSkuInfo;
import com.ecut.mall.service.PmsSkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.engine.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallListServiceApplicationTests {
    @Reference
    PmsSkuService pmsSkuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws InvocationTargetException, IllegalAccessException, IOException {
        // 查询mysql数据
        List<PmsSkuInfo> pmsSkuInfoList = new ArrayList<>();
        pmsSkuInfoList=pmsSkuService.getAllSku("61");

        List<PmsSearchInfo> pmsSearchInfos = new ArrayList<>();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchInfo pmsSearchInfo = new PmsSearchInfo();
            BeanUtils.copyProperties(pmsSearchInfo, pmsSkuInfo);
            pmsSearchInfos.add(pmsSearchInfo);
        }
        System.out.println(pmsSearchInfos);

        for (PmsSearchInfo pmsSearchInfo : pmsSearchInfos) {
            String id = String.valueOf(pmsSearchInfo.getId());

            Index build = new Index.Builder(pmsSearchInfo).index("mall").type("pmsSkuInfo").id(id).build();
            System.out.println(build);
            DocumentResult execute = jestClient.execute(build);
            System.out.println(execute);

        }

    }

}

/*PUT mall
{
        "mappings": {
        "pmsSkuInfo":{
        "properties": {
        "id":{
        "type": "keyword"
        , "index": false
        },
        "price":{
        "type": "double"
        },
        "skuName":{
        "type": "text",
        "analyzer": "ik_max_word"
        },
        "skuDesc":{
        "type": "text",
        "analyzer": "ik_smart"
        },
        "catalog3Id":{
        "type": "keyword"
        },
        "hotScore":{
        "type":"double"
        },
        "skuDefaultImg":{
        "type": "keyword",
        "index": false
        },
        "skuAttrValueList":{
        "properties": {
        "attrId":{
        "type":"keyword"
        },
        "valueId":{
        "type":"keyword"
        }
        }
        }
        }
        }
        }
        }*/
