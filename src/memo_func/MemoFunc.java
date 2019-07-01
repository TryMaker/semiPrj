package memo_func;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conn.DBcon;

public class MemoFunc {
	Connection con;
	public MemoFunc() throws Exception {
		con=DBcon.getConnection();
	}
	
	public ArrayList searchMemo(int idx, String str) throws Exception { //검색 기능
		String sql1="SELECT MTITLE, MDATE FROM PRO_MEMO " //제목만
				+ "WHERE MTITLE LIKE '%"+str+"%' ORDER BY MDATE DESC"; //역순
		String sql2="SELECT MTITLE, MDATE FROM PRO_MEMO " //제목+내용
				+ "WHERE MTITLE LIKE '%"+str+"%' OR "
				+ "MMEMO LIKE '%"+str+"%' ORDER BY MDATE DESC";
		String[] key= {sql1, sql2}; //제목만 or 제목+내용 검색 선택
		PreparedStatement ps=con.prepareStatement(key[idx]);
		ResultSet rs=ps.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs.next()) {
			ArrayList temp=new ArrayList();
//			temp.add(rs.getString("MCODE"));
			temp.add(rs.getString("MTITLE"));
//			temp.add(rs.getString("MMEMO"));
			temp.add(rs.getString("MDATE"));
//			temp.add(rs.getString("MBYTE"));
			data.add(temp);
		}
		return data;
	}
	
	public MemoGS selectMemo(MemoGS gs) throws Exception {
		//jtable에서 클릭한 레코드의 정보를 memo 로 저장
		String date=gs.getDate();
		String sql="SELECT * FROM PRO_MEMO " //날짜 가져옴
				+ "WHERE MDATE=TO_DATE('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			gs.setTitle(rs.getString("MTITLE"));
			gs.setMemo(rs.getString("MMEMO"));
			gs.setDate(rs.getString("MDATE"));
//			gs.setMemory(Integer.parseInt(rs.getString("")));
		}
		rs.close();
		ps.close();
		
		return gs;
	}
	
	public ArrayList insertMemo(MemoGS gs) throws Exception {
		con.setAutoCommit(false);
		//DB에 INSERT 시키기
		String sql1="INSERT INTO PRO_MEMO(MCODE, MTITLE, MMEMO, MDATE, MBYTE)"
				+ "VALUES(SEQ_MEMO.nextval,?,?,SYSDATE,?)"; //새로 입력함
		
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, gs.getTitle());
		ps1.setString(2, gs.getMemo());
		ps1.setInt(3, gs.getMemory());
		
		int r=ps1.executeUpdate();
		
		if (r!=1) {
			con.rollback();
			System.out.println("롤백");
		}
		con.commit();
		ps1.close();
		con.setAutoCommit(true);
		
		String sql2="SELECT MTITLE, MDATE FROM PRO_MEMO "
				+ "ORDER BY MDATE DESC"; //테이블 새로고침
		PreparedStatement ps2=con.prepareStatement(sql2);
		ResultSet rs=ps2.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("MTITLE"));
			temp.add(rs.getString("MDATE"));
			data.add(temp);
		}
		return data;
	}

	public ArrayList modifyMemo(MemoGS gs, String date) throws Exception {
		
		String sql1="SELECT MCODE FROM PRO_MEMO " //날짜로 기본키 가져옴
				+ "WHERE MDATE=TO_DATE"
				+ "('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs=ps1.executeQuery();
		
		int code=0;
		while (rs.next()) {
			code=rs.getInt("MCODE");
		}
		ps1.close();
		
		String sql2="UPDATE PRO_MEMO  SET MTITLE=?, " //수정함
				+ "MMEMO=?, "
				+ "MDATE=SYSDATE, "
				+ "MBYTE=? "
				+ "WHERE MCODE='"+code+"'";
//				기본키가 아니라서 하나 새로 생기는건가?
//				+ "('"+data+"', 'yyyy-mm-dd hh24:mi:ss')";
		
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setString(1, gs.getTitle());
		ps2.setString(2, gs.getMemo());
		ps2.setInt(3, gs.getMemory());
		
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT MTITLE, MDATE FROM PRO_MEMO " //새로고침
				+ "ORDER BY MDATE DESC";
		PreparedStatement ps3=con.prepareStatement(sql3);
		ResultSet rs2=ps3.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs2.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs2.getString("MTITLE"));
			temp.add(rs2.getString("MDATE"));
			data.add(temp);
		}
		return data;
	}

	public ArrayList deleteMemo(MemoGS gs) throws Exception {
		String date=gs.getDate();
		String sql1="SELECT MCODE FROM PRO_MEMO " //날짜 가져옴
				+ "WHERE MDATE=TO_DATE"
				+ "('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		int code=0;
		while (rs1.next()) {
			code=rs1.getInt("MCODE");
		}
		ps1.close();
		
		String sql2="DELETE FROM PRO_MEMO WHERE MCODE=?"; //삭제함
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setInt(1, code);
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT MTITLE, MDATE FROM PRO_MEMO " //새로고침함
				+ "ORDER BY MDATE DESC";
		PreparedStatement ps3=con.prepareStatement(sql3);
		ResultSet rs2=ps3.executeQuery();
		
		ArrayList data=new ArrayList();
		while (rs2.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs2.getString("MTITLE"));
			temp.add(rs2.getString("MDATE"));
			data.add(temp);
		}
		return data;
	}

}





