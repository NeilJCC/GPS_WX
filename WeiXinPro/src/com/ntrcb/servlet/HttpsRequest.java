package com.ntrcb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ntrcb.tran.UserManager;

@WebServlet("/HttpsRequest")
public class HttpsRequest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public HttpsRequest() {
		super();
	}

	public static String openidStr;
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getParameter("urlStr");
		System.out.println(url);

		String jsonStr = "";
		String httpOrgCreateTestRtn = HttpClientUtil.doPost(url, jsonStr,
				"utf-8");

		String jsonResult = "[" + httpOrgCreateTestRtn + "]";
		JSONArray jsonArray = JSONArray.fromObject(jsonResult);
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String openid = (String) jsonObject.get("openid");
		
		openidStr = openid;

		UserManager usermng = new UserManager();
		List<Map<String, Object>> res;
		res = usermng.queryUserExists(openid);
		System.out.println(res);
		System.out.println(Integer.parseInt(res.get(0).get("NUM").toString()));
		int num = Integer.parseInt(res.get(0).get("NUM").toString());
		
		if (num == 0) {
			try {
				usermng.insertUserInfo(openid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	public static String getOpenidStr() {
		return openidStr;
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
