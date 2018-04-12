package com.ntrcb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntrcb.tran.UserManager;
import com.ntrcb.util.JsonUtil;

/**
 * Servlet implementation class LoanQuery
 */
@WebServlet("/LoanQuery")
public class LoanQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type","text/html;charset=UTF-8");
		
		//String idtype = request.getParameter("idtype");
		String id = request.getParameter("id");
		
		//String idtype =new  String(request.getParameter("idtype").getBytes("ISO-8859-1"),"utf-8");
		
		String idtype = request.getParameter("idtype");
		
		System.out.println(id);
		System.out.println(idtype);
		
		UserManager usermng = new UserManager();
		JsonUtil js = new JsonUtil();		
		
		List<Map<String, Object>> res;
		res = usermng.queryLoanResult(idtype, id);
		
		if (res.size() == 0) {
			PrintWriter out = response.getWriter();  
			out.println("empty");
		}
		
		if (res == null) {
			PrintWriter out = response.getWriter();  
			out.println("false");
		}
		
		if (res != null && res.size() != 0) {
			System.out.println(js.ListToJson(res.get(0)));
			//PrintWriter out = response.getWriter();  
			//out.println(js.ListToJson(res.get(0)));
			
			List<Map<String, Object>> res2;
			List<Map<String, Object>> res3;
			String jddm = (String) res.get(0).get("PHASENO");
			String jdmc = (String) res.get(0).get("PHASENAME");
			res2 = usermng.queryReturn(jddm, jdmc);
			res3 = usermng.queryDays(jddm);
			System.out.println(js.ListToJson(res2.get(0)));
//			res.get(0).toString().concat(res2.get(0).toString());
			res.get(0).putAll(res2.get(0));
			res.get(0).putAll(res3.get(0));
			System.out.println(js.ListToJson(res.get(0)));
			PrintWriter out = response.getWriter();  
			out.println(js.MapToJson(res.get(0)));
//			out.println(js.ListToJson(res.get(0)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
