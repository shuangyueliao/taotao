package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;

@Controller
public class OrderCartController {
	
	@Value("${CART_KEY}")
	private String CART_KEY;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user.getUsername());
		List<TbItem> cartList = getCartItemList(request);
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	private List<TbItem>getCartItemList(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
		if(StringUtils.isBlank(json)){
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
		
	}
	
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,Model model){
		TaotaoResult result = orderService.createOrder(orderInfo);
		model.addAttribute("orderId",result.getData().toString());
		model.addAttribute("payment",orderInfo.getPayment());
		DateTime dateTime=new DateTime();
		dateTime=dateTime.plusDays(3);
		model.addAttribute("data",dateTime.toString("yyyy-MM-dd"));
		return "success";
	}
}
