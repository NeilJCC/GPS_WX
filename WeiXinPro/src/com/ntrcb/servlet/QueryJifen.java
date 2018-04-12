package com.ntrcb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntrcb.tran.UserManager;
import com.ntrcb.util.JsonUtil;

@WebServlet("/QueryJifen")
public class QueryJifen extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public QueryJifen() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings("unused")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		String id = request.getParameter("id");
		String openid = HttpsRequest.getOpenidStr();
		System.out.print("openid=");
		System.out.println(openid);

		try {
			UserManager usermng = new UserManager();
			boolean res1;
			res1 = usermng.insertUserId(openid, id);
//			PrintWriter out1 = response.getWriter();
//			out1.println(res1);
			
			UserManager usermng1 = new UserManager();
			List<Map<String, Object>> res2;
			res2 = usermng1.queryUserJifen(id);

			if (res2.size() == 0) {
				PrintWriter out2 = response.getWriter();
				out2.println("empty");
			}

			if (res2 == null) {
				PrintWriter out2 = response.getWriter();
				out2.println("false");
			}

			if (res2 != null && res2.size() != 0) {

				JsonUtil js = new JsonUtil();

				PrintWriter out2 = response.getWriter();
				System.out.println(js.ListToJson(res2.get(0)));
				out2.println(js.MapToJson(res2.get(0)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
