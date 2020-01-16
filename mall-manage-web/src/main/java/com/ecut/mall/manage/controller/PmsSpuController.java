package com.ecut.mall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.PmsBaseSaleAttr;
import com.ecut.mall.beans.PmsProductImage;
import com.ecut.mall.beans.PmsProductInfo;
import com.ecut.mall.beans.PmsProductSaleAttr;
import com.ecut.mall.manage.constant.Constant01;
import com.ecut.mall.manage.utils.FastDFSUtil;
import com.ecut.mall.service.PmsSpuService;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
public class PmsSpuController {
    @Reference
    PmsSpuService pmsSpuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = pmsSpuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsSpuService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsSpuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        String success=pmsSpuService.saveSpuInfo(pmsProductInfo);
        return  success;
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        // 将图片或者音视频上传到分布式文件存储系统
        // 将图片的存储路径返回给页面
        String imgUrl =FastDFSUtil.UpLoadImg(multipartFile);
        return imgUrl;
    }

    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId) {
        List<PmsProductImage> pmsProductImages = pmsSpuService.spuImageList(spuId);
        return pmsProductImages;
    }

}
