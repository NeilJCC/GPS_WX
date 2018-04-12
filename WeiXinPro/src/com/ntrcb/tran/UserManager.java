package com.ntrcb.tran;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ntrcb.util.db2Util;
import com.ntrcb.util.db2SjxfUtil;
import com.ntrcb.util.oracleJfyUtil;
import com.ntrcb.util.renhangDbUtil;

public class UserManager {

	public boolean insertLoan(String name, String idnumber, String sex,
			String phone, String company, String district, String street,
			String purpose, String amount, String referee) throws SQLException {
		db2SjxfUtil dbUtil = new db2SjxfUtil();
		dbUtil.getConnection();

		String sql = "insert into iap.LOANAPPLY(NAME,IDNUMBER,SEX,PHONE,COMPANY,DISTRICT,STREET,PURPOSE,AMOUNT,REFEREE) values ('"
				+ name
				+ "','"
				+ idnumber
				+ "','"
				+ sex
				+ "','"
				+ phone
				+ "','"
				+ company
				+ "','"
				+ district
				+ "','"
				+ street
				+ "','"
				+ purpose
				+ "','" + amount + "','" + referee + "')";

		System.out.println(sql);
		if (name == null || idnumber == null || sex == null || phone == null
				|| company == null || district == null || street == null
				|| purpose == null || amount == null) {
			dbUtil.releaseConn();
			return false;
		}

		if (name.isEmpty() || idnumber.isEmpty() || sex.isEmpty()
				|| phone.isEmpty() || company.isEmpty() || district.isEmpty()
				|| street.isEmpty() || purpose.isEmpty() || amount.isEmpty()) {
			dbUtil.releaseConn();
			return false;
		}

		boolean result;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}

	public boolean insertCard(String name, String id, String jiguan,
			String bdate, String edate, String tele) throws SQLException {
		db2SjxfUtil dbUtil = new db2SjxfUtil();
		dbUtil.getConnection();

		String sql = "insert into iap.CREDITAPPLY(NAME,IDNUMBER,JIGUAN,BEGINDATE,ENDDATE,TELEPHONE) values ('"
				+ name
				+ "','"
				+ id
				+ "','"
				+ jiguan
				+ "','"
				+ bdate
				+ "','"
				+ edate + "','" + tele + "')";

		if (name == null || tele == null) {
			dbUtil.releaseConn();
			return false;
		}

		if (name.isEmpty() || tele.isEmpty()) {
			dbUtil.releaseConn();
			return false;
		}

		System.out.println(sql);

		boolean result;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}

	public List<Map<String, Object>> queryLoanResult(String idtype, String id) {
		if (idtype == null || id == null) {
			return null;
		}

		if (idtype.isEmpty() || id.isEmpty()) {
			return null;
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		db2Util db2 = new db2Util();
		String sql = "select a.objectno,a.flowname,"
				+ "case when "
				+ "phaseno='0010' then '50' "
				+ "when  "
				+ "phaseno='0020' then '100' "
				+ "when "
				+ "phaseno='0060' then '150' "
				+ "when "
				+ "phaseno='0070' then '200' "
				+ "when "
				+ "phaseno='0080' then '250' "
				+ "when "
				+ "phaseno='0120' then '300' "
				+ "when "
				+ "phaseno='0130' then '350' "
				+ "when "
				+ "phaseno='0140' then '450' "
				+ "when "
				+ "phaseno='0150' then '500' "
				+ "when "
				+ "phaseno='0160' then '780' "
				+ "when "
				+ "phaseno='0170' then '850' "
				+ "when "
				+ "phaseno='0180' then '400' "
				+ "when "
				+ "phaseno='0190' then '700' "
				+ "when "
				+ "phaseno='0200' then '750' "
				+ "when "
				+ "phaseno='0360' then '800' "
				+ "when "
				+ "phaseno='0390' then '300' "
				+ "when "
				+ "phaseno='0510' then '900' "
				+ "when "
				+ "phaseno='0754' then '600' "
				+ "when "
				+ "phaseno='0755' then '630' "
				+ "when "
				+ "phaseno='0756' then '660' "
				+ "when "
				+ "phaseno='3000' then '100' "
				+ "end "
				+ "as phaseno,"
				+ "a.PHASENAME,b.VOUCHTYPE,b.CUSTOMERNAME,decimal(b.BUSINESSSUM/10000,15,0) "
				+ "as BUSINESSSUM,"
				+ "c.certtype,c.CERTID,"
				+ "a.orgid,a.orgname,a.userid,a.username,a.inputdate "
				+ "from CMIS.FLOW_OBJECT a "
				+ "left join "
				+ "CMIS.business_apply b "
				+ "on a.OBJECTNO = b.SERIALNO "
				+ "left join "
				+ "("
				+ "select '身份证' as certtype,customerid,certid as certid from CMIS.IND_INFO "
				+ "union "
				+ "select '组织机构代码证' as certtype,customerid,corpid as certid from CMIS.ent_info"
				+ ")"
				+ "c "
				+ "on b.CUSTOMERID=c.CUSTOMERID "
				+ "where a.objecttype like 'Credit%' and a.FLOWNO like '%Apply%' and a.OBJECTNO like 'BA%' "
				+ "and b.flag5 <> '1070' " + "and c.certtype='" + idtype + "'"
				+ " and c.certid='" + id + "' " + "fetch first 1  row only";
		Connection conn = db2.getConnection();
		try {
			list = db2.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> queryReturn(String jddm, String jdmc) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		db2Util db2 = new db2Util();
		String sql = "select * from iap.XDSXSP_PARA where jddm='" + jddm
				+ "' and jdmc='" + jdmc + "'";
		Connection conn = db2.getConnection();
		try {
			list = db2.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> queryDays(String jddm) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		db2Util db2 = new db2Util();
		String sql = "select sum(YJHS) as LASTDAYS from iap.XDSXSP_PARA where jddm>'"
				+ jddm + "'";
		Connection conn = db2.getConnection();
		try {
			list = db2.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> queryUserExists(String openid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		oracleJfyUtil dbUtil = new oracleJfyUtil();
		dbUtil.getConnection();

		String sql = "select count(*) NUM from jfy.xcx_user where openid='"
				+ openid + "'";

		System.out.println(sql);

		try {
			list = dbUtil.findModeResult(sql, null);
			System.out.println(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	public boolean insertUserInfo(String openid) throws SQLException {
		oracleJfyUtil dbUtil = new oracleJfyUtil();
		dbUtil.getConnection();

		String sql = "insert into jfy.xcx_user(openid) values('" + openid
				+ "')";

		System.out.println(sql);

		boolean result = false;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}

	public boolean insertUserId(String openid, String id) throws SQLException {
		oracleJfyUtil dbUtil = new oracleJfyUtil();
		dbUtil.getConnection();

		String sql = "update jfy.xcx_user set IDNO='" + id + "' where OPENID='"
				+ openid + "'";

		boolean result = false;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}

	public List<Map<String, Object>> queryUserJifen(String id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		oracleJfyUtil dbUtil = new oracleJfyUtil();
		dbUtil.getConnection();

		String sql = "select a.POINTSBALANCE from jfy.POINTSACCOUNTINFO a left join jfy.CUSTOMERS b on a.CUSTOMERSID=b.CUSTOMERSID where b.IDNO='"
				+ id + "' and b.IDTYPE='A'";

		System.out.println(sql);

		try {
			list = dbUtil.findModeResult(sql, null);
			System.out.println(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;
	}

	public boolean insertComplaint(String name, String unit, String content)
			throws SQLException {
		db2SjxfUtil dbUtil = new db2SjxfUtil();
		dbUtil.getConnection();
		String sql = "insert into iap.COMPLAINT(name,company,content) values('"
				+ name + "','" + unit + "','" + content + "')";
		boolean result = false;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}

	public boolean insertUpvote(String name, String unit, String content)
			throws SQLException {
		db2SjxfUtil dbUtil = new db2SjxfUtil();
		dbUtil.getConnection();
		String sql = "insert into iap.UPVOTE(name,company,content) values('"
				+ name + "','" + unit + "','" + content + "')";
		boolean result = false;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}
	
	public boolean insertGPS(double gdLongitude,double gdLatitude,double bdLongitude,double bdLatitude,double sgLongitude,double sgLatitude,String bankCode) 
			throws SQLException {
		renhangDbUtil dbUtil = new renhangDbUtil();
		dbUtil.getConnection();
		String sql = "UPDATE bank SET bank_baidu_longitude=" + bdLongitude +",bank_baidu_latitude=" + bdLatitude 
				+",bank_sougou_longitude=" + sgLongitude + ",bank_sougou_latitude=" +sgLatitude 
				+",bank_gaode_longitude=" + gdLongitude + ",bank_gaode_latitude=" + gdLatitude + " WHERE bank_code=\'" + bankCode + "\'";
		boolean result = false;
		result = dbUtil.updateByPreparedStatement1(sql);
		dbUtil.releaseConn();
		return result;
	}
	
	public List<Map<String, Object>> bankInfoReturn(String city, String area, String banktype) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		renhangDbUtil dbUtil = new renhangDbUtil();
		Connection conn = dbUtil.getConnection();		
		String sql = "select bank_code,bank_name,bank_address from bank where bank_city_name=\'"+ city 
				+ "\' and (bank_area_name like \'%" + area + "%\' or bank_address like \'%" + area + "%\') and bank_type=\'" + banktype + "\'";
		try {
			list = dbUtil.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbUtil.releaseConn();
		return list;
	}
	
	public List<Map<String, Object>> queryBankType(String city) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		renhangDbUtil dbUtil = new renhangDbUtil();
		Connection conn = dbUtil.getConnection();	
		String sql = "SELECT distinct bank_type FROM bank WHERE bank_city_name=\'" + city +"\'";
		try {
			list = dbUtil.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbUtil.releaseConn();
		return list;
	}
	
	public List<Map<String, Object>> queryBankCode(String code) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		renhangDbUtil dbUtil = new renhangDbUtil();
		Connection conn = dbUtil.getConnection();	
		String sql = "select bank_name,bank_code,bank_address FROM bank WHERE bank_code=\'" + code + "\'";
		try {
			list = dbUtil.findModeResult(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbUtil.releaseConn();
		return list;
	}
}
