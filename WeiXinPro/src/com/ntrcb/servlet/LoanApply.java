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
 * Servlet implementation class LoanApply
 */
@WebServlet("/LoanApply")
public class LoanApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanApply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String sex = request.getParameter("sex");
		String tele = request.getParameter("tele");
		String company = request.getParameter("company");
		String district = request.getParameter("district");
		String street = request.getParameter("street");
		String purpose = request.getParameter("purpose");
		String amount = request.getParameter("amount");
		String referee = request.getParameter("referee");
		
		UserManager usermng = new UserManager();
		
		
		try {
			boolean res;
			res = usermng.insertLoan(name, id, sex, tele, company, district, street, purpose, amount, referee);
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
