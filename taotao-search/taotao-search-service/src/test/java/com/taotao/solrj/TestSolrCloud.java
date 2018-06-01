package com.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	@Test
	public void testSolrCloudAdDocument()throws Exception{
		CloudSolrServer cloudSolrServer=new CloudSolrServer("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		cloudSolrServer.setDefaultCollection("collection3");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品名称");
		document.addField("item_price", 100);
		
		cloudSolrServer.add(document);
		cloudSolrServer.commit();
	}
}
