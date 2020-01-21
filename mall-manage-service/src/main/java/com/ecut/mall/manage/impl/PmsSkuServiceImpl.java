package com.ecut.mall.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ecut.mall.beans.PmsSkuAttrValue;
import com.ecut.mall.beans.PmsSkuImage;
import com.ecut.mall.beans.PmsSkuInfo;
import com.ecut.mall.beans.PmsSkuSaleAttrValue;
import com.ecut.mall.manage.constant.RedisConstant;
import com.ecut.mall.manage.mapper.*;
import com.ecut.mall.service.PmsSkuService;
import com.ecut.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.UUID;

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
    @Autowired
    RedisUtil redisUtil;

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
        try {
            PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
            // 链接缓存
            Jedis jedis = redisUtil.getJedis();
            String skuInfoKey = RedisConstant.SKUKEY_PREFIX + skuId + RedisConstant.SKUKEY_SUFFIX;
            String skuInfoKeyLock = RedisConstant.SKUKEY_PREFIX + skuId + RedisConstant.SKUKEY_SUFFIX_LOCK;
            // 查询缓存
            String skuInfoJson = jedis.get(skuInfoKey);
            if (StringUtils.isNotBlank(skuInfoJson)) {
                System.out.println("查询缓存");
                pmsSkuInfo = JSON.parseObject(skuInfoJson, PmsSkuInfo.class);
            } else {
                // 缓存中没有，查询数据库
                // 设置分布式锁
                // value应设置不同，原因：。。。
                String token = UUID.randomUUID().toString();
                String OK = jedis.set(skuInfoKeyLock, token, "nx", "px", 1000 * 5);
                if ("OK".equals(OK)) {
                    // 获得分布式锁
                    System.out.println("获得分布式锁");
                    pmsSkuInfo = getSkuInfoByIdFromDB(skuId);
                    // 如果数据库中不存在，防止缓存穿透
                    if (pmsSkuInfo == null) {
                        jedis.setex(skuInfoKey, RedisConstant.SKUKEY_TIMEOUT, "empty");
                        return null;
                    } else {
                        String skuInfoJsonNew = JSON.toJSONString(pmsSkuInfo);
                        // 真实项目中设置不同的过期时间，防止缓存雪崩
                        System.out.println("设置不同的过期时间，防止缓存雪崩");
                        String setex = jedis.setex(skuInfoKey, RedisConstant.SKUKEY_TIMEOUT, skuInfoJsonNew);
                        jedis.close();
                    }
                    // 访问完Mysql后，回来删锁
                    // 确认删除的是自己的锁
                    String skuLockToken = jedis.get(skuInfoKeyLock);
                    if (StringUtils.isNotBlank(skuLockToken)&&token.equals(skuLockToken)){
                        jedis.del(skuInfoKeyLock);
                    }
                    return pmsSkuInfo;
                } else {
                    // 未获得分布式锁，开始自旋
                    System.out.println("未获得分布式锁，开始自旋");
                    jedis.close();
                    return getSkuInfo(skuId);
                }
            }
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        }
        return getSkuInfoByIdFromDB(skuId);
    }

    public PmsSkuInfo getSkuInfoByIdFromDB(String skuId) {
        // 查询skuInfo
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);

        // 查询skuImage并临时存到skuInfo中
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImageSelect = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(pmsSkuImageSelect);

        // 查询skuSaleAttrValue并临时存到skuInfo中
        PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
        pmsSkuSaleAttrValue.setSkuId(skuId);
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueSelect = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
        pmsSkuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueSelect);
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuListBySpu(String productId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setProductId(productId);
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuListBySpu(pmsSkuInfo);
        return pmsSkuInfos;
    }
}
