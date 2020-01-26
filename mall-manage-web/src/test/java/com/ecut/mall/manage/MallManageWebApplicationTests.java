package com.ecut.mall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
public class MallManageWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {
        // 配置fdfs的全局链接地址
        String tracker=MallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(tracker);
        TrackerClient trackerClient=new TrackerClient();

        // 获得一个trackerServer的实例
        TrackerServer trackerServer=trackerClient.getConnection();
        // 通过tracker获得一个Storage连接客户端
        StorageClient storageClient=new StorageClient(trackerServer,null);

        String[] uploadInfos = storageClient.upload_file("c:/a.jpg", "jpg", null);
        for (String uploadInfo: uploadInfos) {
            System.out.println(uploadInfo);
        }
    }

}
