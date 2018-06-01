package com.taotao.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	@Test
	public void testPageHelper()throws Exception{
		PageHelper.startPage(1, 10);
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example=new TbItemExample();
		//Criteria criteria=example.createCriteria();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem>pageInfo=new PageInfo<>(list);
		System.out.println("count:"+pageInfo.getTotal());
		System.out.println("countSize:"+pageInfo.getPages());
		System.out.println("返回记录数:"+list.size());
		
	}
}
