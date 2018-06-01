package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class TestFastDFS {
	@Test
	public void uploadFile()throws Exception{
		ClientGlobal.init("F:/workspace/taotao-manager-web/src/main/resources/resource/client.conf");
		TrackerClient trackerClient=new TrackerClient();
		TrackerServer trackerServer=trackerClient.getConnection();
		StorageServer storageServer=null;
		StorageClient storageClient=new StorageClient(trackerServer,storageServer);
		String[] strings = storageClient.upload_file("C:/Users/John/Pictures/201705161547241211.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	@Test
	public void testFastDfsClient()throws Exception{
		FastDFSClient fastDFSClient=new FastDFSClient("F:/workspace/taotao-manager-web/src/main/resources/resource/client.conf");
		String string = fastDFSClient.uploadFile("C:/Users/John/Pictures/QQ图片20180319091212.jpg");
		System.out.println(string);
		
	}
}
