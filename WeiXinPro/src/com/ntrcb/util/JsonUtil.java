package com.ntrcb.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public String BeanToJson(Object obj){
		JSONObject json = JSONObject.fromObject(obj);  
		return json.toString();
	}
	
	public String ListToJson(Object obj){
		JSONArray json=JSONArray.fromObject(obj); 
		return json.toString();
	}
	
	public String MapToJson(Object obj){
		JSONObject json = JSONObject.fromObject(obj);  
		return json.toString();
	} 
	
	public Object JsonToBean(String json,Object obj){
		JSONObject jsonObj = JSONObject.fromObject(json);
		obj = (Object) JSONObject.toBean(jsonObj, Object.class);
		return obj;
	} 
	
}
