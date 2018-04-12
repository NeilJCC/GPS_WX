package com.ntrcb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ntrcb.tran.UserManager;

/**
 * Servlet implementation class CardApply
 */
@WebServlet("/CardApply")
public class CardApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardApply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		//String pinyin = request.getParameter("pinyin");
		String id = request.getParameter("id");
		String jiguan = request.getParameter("jiguan");
		String bdate = request.getParameter("bdate");
		String edate = request.getParameter("edate");
		String phone = request.getParameter("tele");
		
		//System.out.println(name+"|"+pinyin+"|"+id+"|"+jiguan+"|"+bdate+"|"+edate+"|"+phone);
		
		UserManager usermng = new UserManager();
		
		
		try {
			boolean res;
			res = usermng.insertCard(name, id, jiguan, bdate, edate, phone);
			PrintWriter out = response.getWriter();  
			out.println(res);	
		} catch (SQLException e) {
			e.printStackTrace();
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
