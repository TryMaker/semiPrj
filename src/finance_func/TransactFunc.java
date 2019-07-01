package finance_func;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conn.DBcon;

public class TransactFunc {
	Connection con;
	public TransactFunc() throws Exception {
		con=DBcon.getConnection();
	}

	public FinanceGS initTotal(FinanceGS gs) throws Exception {
		
		int income=0;
		
		String sql1="SELECT TAMOUNT FROM PRO_TRANSACT "
				+ "WHERE TIO='수입'";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		while (rs1.next()) {
			income+=rs1.getInt("TAMOUNT");
		}
		rs1.close();
		ps1.close();
		
		int outcome=0;
		
		String sql2="SELECT TAMOUNT FROM PRO_TRANSACT "
				+ "WHERE TIO='지출'";
		PreparedStatement ps2=con.prepareStatement(sql2);
		ResultSet rs2=ps2.executeQuery();
		while (rs2.next()) {
			outcome+=rs2.getInt("TAMOUNT");
		}
		rs2.close();
		ps2.close();
		
		gs.setTotalmoney(income-outcome);
		
		return gs;
		
	}
	
	public FinanceGS initDayPay(FinanceGS gs) throws Exception {
		
		int dailypay=0;
		
		String sql1="SELECT TAMOUNT FROM PRO_TRANSACT WHERE TDATE BETWEEN TRUNC(SYSDATE, 'DAY') AND SYSDATE AND TIO='지출'";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		while (rs1.next()) {
			dailypay+=rs1.getInt("TAMOUNT");
		}
		
		gs.setDailypay(dailypay);
		
		return gs;
		
	}
	
	public FinanceGS initWeekPay(FinanceGS gs) throws Exception {
		
		int weeklypay=0;
		
		String sql1="SELECT TAMOUNT FROM PRO_TRANSACT WHERE TDATE BETWEEN NEXT_DAY(SYSDATE-7, '월요일') AND SYSDATE AND TIO='지출'";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		while (rs1.next()) {
			weeklypay+=rs1.getInt("TAMOUNT");
		}
		
		gs.setWeeklypay(weeklypay);
		
		return gs;
	}

	public FinanceGS initMonthPay(FinanceGS gs) throws Exception {
		
		int monthlypay=0;
		
		String sql1="SELECT TAMOUNT FROM PRO_TRANSACT WHERE TDATE BETWEEN TRUNC(SYSDATE, 'MONTH') AND SYSDATE AND TIO='지출'";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		while (rs1.next()) {
			monthlypay+=rs1.getInt("TAMOUNT");
		}
		
		gs.setMonthlypay(monthlypay);
		
		return gs;
		
	}
	
	public ArrayList searchTransact(int idx1, int idx2, String str) throws Exception {
		
		int num=-1;
		if (idx2==1) {
			num=Integer.parseInt(str);
		}
		
		String sql="SELECT TIO, TAMOUNT, TDATE FROM PRO_TRANSACT WHERE (TIO="; //기본 문장
		
		String sql1="'수입' OR TIO='지출') ";
		String sql2="'수입') ";
		String sql3="'지출') ";
		String[] key1={sql1, sql2, sql3};
		
		String sql4="AND TDESCRIBE LIKE '%"+str+"%'";
		String sql5="AND TAMOUNT="+num;
		String[] key2= {sql4, sql5};
		
		String sqle="ORDER BY TDATE DESC";
		
		PreparedStatement ps=con.prepareStatement(sql+key1[idx1]+key2[idx2]+sqle);
		ResultSet rs=ps.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("TIO"));
			temp.add(rs.getInt("TAMOUNT"));
			temp.add(rs.getString("TDATE"));
			data.add(temp);
		}
		ps.close();
		rs.close();
		
		return data;
		
	}

	public FinanceGS selectTransact(FinanceGS gs) throws Exception { //작업중
		
		String date=gs.getTdate();
		String sql="SELECT * FROM PRO_TRANSACT "
				+ "WHERE TDATE=TO_DATE('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			gs.setTio(rs.getString("TIO"));
			gs.setTamount(rs.getInt("TAMOUNT"));
			gs.setTdescribe(rs.getString("TDESCRIBE"));
			gs.setTdate(rs.getString("TDATE"));
		}
		rs.close();
		ps.close();
		//TIO VARCHAR, TAMOUNT NUMBER, TDESCRIBE VARCHAR, TDATE DATE
		
		return gs;
	}
	
	public ArrayList insertTransact(FinanceGS gs) throws Exception {
		con.setAutoCommit(false);
		//DB에 INSERT 시키기
		String sql1="INSERT INTO PRO_TRANSACT " + 
				"VALUES(SEQ_TRANSACT.nextval, ?, ?, ?, sysdate)";
		//tcode(기본키, 시퀀스), tio(문자열), tamount(숫자), tdescribe(문자열), tdate(자동)
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, gs.getTio());
		ps1.setInt(2, gs.getTamount());
		ps1.setString(3, gs.getTdescribe());
		
		int r=ps1.executeUpdate();
		
		if (r!=1) {
			con.rollback();
			System.out.println("롤백");
		}
		con.commit();
		ps1.close();
		con.setAutoCommit(true);
		
		String sql2="SELECT TIO, TAMOUNT, TDATE FROM PRO_TRANSACT "
				+ "ORDER BY TDATE DESC"; //테이블 새로고침
		PreparedStatement ps2=con.prepareStatement(sql2);
		ResultSet rs=ps2.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("TIO"));
			temp.add(rs.getInt("TAMOUNT"));
			temp.add(rs.getString("TDATE"));
			data.add(temp);
		}
		
		return data;
	}

	public ArrayList modifyTransact(FinanceGS gs, String date) throws Exception {
		
		String sql1="SELECT TCODE FROM PRO_TRANSACT WHERE TDATE=TO_DATE ('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		int code=0;
		while (rs1.next()) {
			code=rs1.getInt("TCODE");
		}
		ps1.close();
		rs1.close();
		
		String sql2="UPDATE PRO_TRANSACT SET "
				+ "TIO=?, "
				+ "TAMOUNT=?, "
				+ "TDESCRIBE=? "
				+ "WHERE TCODE='"+code+"'";
		
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setString(1, gs.getTio());
		ps2.setInt(2, gs.getTamount());
		ps2.setString(3, gs.getTdescribe());
		
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT TIO, TAMOUNT, TDATE FROM PRO_TRANSACT "
				+ "ORDER BY TDATE DESC";
		PreparedStatement ps3=con.prepareStatement(sql3);
		ResultSet rs2=ps3.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs2.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs2.getString("TIO"));
			temp.add(rs2.getInt("TAMOUNT"));
			temp.add(rs2.getString("TDATE"));
			data.add(temp);
		}
		ps3.close();
		rs2.close();
		
		return data;
	}

	public ArrayList deleteTransact(FinanceGS gs) throws Exception {
		String date=gs.getTdate();
		String sql1="SELECT TCODE FROM PRO_TRANSACT WHERE TDATE=TO_DATE ('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		int code=0;
		while (rs1.next()) {
			code=rs1.getInt("TCODE");
		}
		ps1.close();
		rs1.close();
		
		String sql2="DELETE FROM PRO_TRANSACT WHERE TCODE=?"; //삭제함
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setInt(1, code);
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT TIO, TAMOUNT, TDATE FROM PRO_TRANSACT "
				+ "ORDER BY TDATE DESC";
		PreparedStatement ps3=con.prepareStatement(sql3);
		ResultSet rs2=ps3.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs2.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs2.getString("TIO"));
			temp.add(rs2.getInt("TAMOUNT"));
			temp.add(rs2.getString("TDATE"));
			data.add(temp);
		}
		ps3.close();
		rs2.close();
		
		return data;
	}
	
}
