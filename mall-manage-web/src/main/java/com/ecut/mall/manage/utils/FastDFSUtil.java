package com.ecut.mall.manage.utils;

import com.ecut.mall.manage.constant.URLConstant;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FastDFSUtil {
    // 上传图片到服务器，并返回图片地址
    public static String UpLoadImg(MultipartFile multipartFile){
        String imgUrl= URLConstant.URL_VMware;
        // 将图片上传到分布式文件系统
        // 配置fdfs的全局链接地址
        String tracker= FastDFSUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrackerClient trackerClient=new TrackerClient();

        // 获得一个trackerServer的实例
        TrackerServer trackerServer= null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过tracker获得一个Storage连接客户端
        StorageClient storageClient=new StorageClient(trackerServer,null);

        // 上传图片文件并得到图片地址
        try {
            byte[] bytes = multipartFile.getBytes();
            String originalFilename = multipartFile.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i + 1);
            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);
            for(String uploadInfo:uploadInfos){
                imgUrl+="/"+uploadInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(imgUrl);
        return imgUrl;
    }
}
