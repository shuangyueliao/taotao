package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaotaoResult checkData(String data, int type) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type==1){
			criteria.andUsernameEqualTo(data);
		}else if(type==2){
			criteria.andPhoneEqualTo(data);
		}else if(type==3){
			criteria.andEmailEqualTo(data);
		}else{
			return TaotaoResult.build(400, "非法数据");
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}
	@Override
	public TaotaoResult register(TbUser user) {
		if(StringUtils.isBlank(user.getUsername())){
			return TaotaoResult.build(400, "用户不能为空");
		}
		TaotaoResult taotaoResult = checkData(user.getUsername(),1);
		if(!(boolean)taotaoResult.getData()){
			return TaotaoResult.build(400, "用户名重复");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return TaotaoResult.build(400, "密码不能为空");
		}
		
		if(StringUtils.isNotBlank(user.getPhone())){
			taotaoResult = checkData(user.getPhone(),2);
			if(!(boolean)taotaoResult.getData()){
				return TaotaoResult.build(400, "电话号码重复");
			}
		}
		if(StringUtils.isNotBlank(user.getEmail())){
			taotaoResult = checkData(user.getEmail(),3);
			if(!(boolean)taotaoResult.getData()){
				return TaotaoResult.build(400, "email重复");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		userMapper.insert(user);
		
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult login(String username, String password) {
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list==null || list.size()==0){
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		TbUser user = list.get(0);
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		user.setPassword(null);
		String token=UUID.randomUUID().toString();
		jedisClient.set(USER_SESSION+":"+token,JsonUtils.objectToJson(user));
		jedisClient.expire(USER_SESSION+":"+token, SESSION_EXPIRE);
		return TaotaoResult.ok(token);
	}
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json=jedisClient.get(USER_SESSION+":"+token);
		if(StringUtils.isBlank(json)){
			return TaotaoResult.build(400, "用户登录已经过期");
		}
		jedisClient.expire(USER_SESSION+":"+token, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		
		return TaotaoResult.ok(user);
	}

}
