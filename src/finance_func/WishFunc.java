package finance_func;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conn.DBcon;

public class WishFunc {
	Connection con;
	public WishFunc() throws Exception {
		con=DBcon.getConnection();
	}
	
	public FinanceGS initializeWish(FinanceGS gs) throws Exception {
		
		int rank=gs.getWishRank();
		
		String sql1="SELECT * FROM PRO_WISH "
				+ "WHERE WRANK="+rank;
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		while (rs1.next()) {
			gs.setWishName(rs1.getString("WNAME"));
			gs.setWishCost(rs1.getString("WCOST"));
			gs.setWishDescribe(rs1.getString("WDESCRIBE"));
		}
		rs1.close();
		ps1.close();
		
		return gs;
	}
	
	public FinanceGS updateWish(FinanceGS gs) throws Exception {
		
//		int rank=gs.getWishRank();
		String sql1="UPDATE PRO_WISH SET "
				+ "WNAME=?, "
				+ "WCOST=?, "
				+ "WDESCRIBE=? "
				+ "WHERE WRANK=?";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, gs.getWishName());
		ps1.setString(2, gs.getWishCost());
		ps1.setString(3, gs.getWishDescribe());
		ps1.setInt(4, gs.getWishRank());
		
		ps1.executeUpdate();
		ps1.close();
		
		String sql2="SELECT WNAME, WCOST, WDESCRIBE "
				+ "FROM PRO_WISH "
				+ "WHERE WRANK=?";
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setInt(1, gs.getWishRank());
		ResultSet rs1=ps2.executeQuery();
//		ArrayList data=new ArrayList();
		while (rs1.next()) {
//			gs.setWishRank(rs1.getInt("WRANK"));
			gs.setWishName(rs1.getString("WNAME"));
			gs.setWishCost(rs1.getString("WCOST"));
			gs.setWishDescribe(rs1.getString("WDESCRIBE"));
		}
		rs1.close();
		ps2.close();
		return gs;
	}

}
