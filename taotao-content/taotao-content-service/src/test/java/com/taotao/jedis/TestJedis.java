package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	@Test
	public void testJedis()throws Exception{
		Jedis jedis=new Jedis("127.0.0.1",6379);
		jedis.set("jedis-key", "1234");
		String result = jedis.get("jedis-key");
		System.out.println(result);
		jedis.close();
	}
	
	@Test
	public void testJedisPool()throws Exception{
		JedisPool jedisPool=new JedisPool("127.0.0.1",6379);
		Jedis jedis=jedisPool.getResource();
		String result=jedis.get("jedis-key");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster()throws Exception{
		Set<HostAndPort>nodes=new HashSet<>();
		nodes.add(new HostAndPort("127.0.0.1", 7001));
		nodes.add(new HostAndPort("127.0.0.1", 7002));
		nodes.add(new HostAndPort("127.0.0.1", 7003));
		nodes.add(new HostAndPort("127.0.0.1", 7004));
		nodes.add(new HostAndPort("127.0.0.1", 7005));
		nodes.add(new HostAndPort("127.0.0.1", 7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("cluster-test", "hello jedis cluster");
		String string = jedisCluster.get("cluster-test");
		System.out.println(string);
		jedisCluster.close();
	}
}
