package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.Content;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		// 通过 httpClient 调用服务层接口
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		// 将字符串转化为 TaotaoResult
		TaotaoResult taotaoResult = TaotaoResult.formatToList(result, Content.class);
		// 去内容列表
		List<Content> list = (List<Content>) taotaoResult.getData();
		// 创建一个 jsp 页面需要的 pojo
		List<Map> resultList = new ArrayList<>();
		// 遍历
		for(Content content : list){
			Map map = new HashMap();
			map.put("src", content.getPic());
			map.put("height", 240);
			map.put("width", 670);
			map.put("srcB", content.getPic2());
			map.put("heightB",240);
			map.put("widthB", 550);
			map.put("href", content.getUrl());
			map.put("alt", content.getSubTitle());
			resultList.add(map);
		}
		return JsonUtils.objectToJson(resultList);
	}

}
