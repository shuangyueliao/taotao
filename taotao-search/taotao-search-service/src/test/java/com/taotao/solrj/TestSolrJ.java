package com.taotao.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	@Test
	public void testAddDocument()throws Exception{
		SolrServer solrServer=new HttpSolrServer("http://127.0.0.1:8081/solr");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "123");
		document.addField("item_title", "测试商品3");
		document.addField("item_price", 1000);
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void deleteDocumentById()throws Exception{
		SolrServer solrServer=new HttpSolrServer("http://127.0.0.1:8081/solr");
		solrServer.deleteById("test001");
		solrServer.commit();
		
	}
	@Test
	public void deleteDocumentByQuery()throws Exception{
		SolrServer solrServer=new HttpSolrServer("http://127.0.0.1:8081/solr");
		solrServer.deleteByQuery("id:123");
		solrServer.commit();
		
	}
	@Test
	public void searchDocument()throws Exception{
		SolrServer solrServer=new HttpSolrServer("http://127.0.0.1:9000/solr");
		SolrQuery query=new SolrQuery();
		query.setQuery("手机");
		query.setStart(30);
		query.setRows(20);
		query.set("df", "item_keywords");
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<div>");
		query.setHighlightSimplePost("</div>");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocumentList=response.getResults();
		System.out.println("查询结果总记录灵敏："+solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle="";
			if(list!=null && list.size()>0){
				itemTitle=list.get(0);
			}else{
				itemTitle=(String) solrDocument.get("item_title");
			}
			
			
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println("========================================");
		}
		
	}
}
