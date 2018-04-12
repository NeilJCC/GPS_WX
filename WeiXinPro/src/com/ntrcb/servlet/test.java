package com.ntrcb.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx7a2c354c327e99be&secret=ffd1b082dbfdc092b98a047ba1a4904d&js_code=001z6Raf0rlHjy1yXO9f04ITaf0z6Ral&grant_type=authorization_code";
        String jsonStr = "";
        String httpOrgCreateTestRtn = HttpClientUtil.doPost(url, jsonStr, "utf-8");
        
        System.out.println(httpOrgCreateTestRtn);
        
        String jsonResult="["+httpOrgCreateTestRtn+"]";
        JSONArray jsonArray = JSONArray.fromObject(jsonResult);
        
    	JSONObject jsonObject = jsonArray.getJSONObject(0);
    	System.out.println(jsonObject.get("openid"));
	}

}
