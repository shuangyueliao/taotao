package com.taotao.service.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	List<SearchItem>getItemList();
	SearchItem getItemById(long itemId);
}
