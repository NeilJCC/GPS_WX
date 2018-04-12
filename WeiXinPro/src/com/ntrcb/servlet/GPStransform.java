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

@WebServlet("/GPStransform")
public class GPStransform extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GPStransform() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("id");
		double longitude = Double.parseDouble(request.getParameter("longitude").toString());
		double latitude = Double.parseDouble(request.getParameter("latitude").toString());
		double []bd_tmp = GPSFormatUtil.gcj02_To_Bd09(latitude, longitude);
		double []bd = {bd_tmp[1],bd_tmp[0]};
		double []sg_tmp = GPSFormatUtil.gcj02_To_Gps84(latitude, longitude);
		double []sg = GPSFormatUtil.map84ToSogou(sg_tmp[1], sg_tmp[0]);
		UserManager userManager = new UserManager();
		try {
			boolean res = userManager.insertGPS(longitude, latitude, bd[0], bd[1], sg[0], sg[1], id);
			PrintWriter out = response.getWriter();  
			out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
