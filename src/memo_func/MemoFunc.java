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
	
	public ArrayList searchMemo(int idx, String str) throws Exception { //�˻� ���
		String sql1="SELECT MTITLE, MDATE FROM PRO_MEMO " //����
				+ "WHERE MTITLE LIKE '%"+str+"%' ORDER BY MDATE DESC"; //����
		String sql2="SELECT MTITLE, MDATE FROM PRO_MEMO " //����+����
				+ "WHERE MTITLE LIKE '%"+str+"%' OR "
				+ "MMEMO LIKE '%"+str+"%' ORDER BY MDATE DESC";
		String[] key= {sql1, sql2}; //���� or ����+���� �˻� ����
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
		//jtable���� Ŭ���� ���ڵ��� ������ memo �� ����
		String date=gs.getDate();
		String sql="SELECT * FROM PRO_MEMO " //��¥ ������
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
		//DB�� INSERT ��Ű��
		String sql1="INSERT INTO PRO_MEMO(MCODE, MTITLE, MMEMO, MDATE, MBYTE)"
				+ "VALUES(SEQ_MEMO.nextval,?,?,SYSDATE,?)"; //���� �Է���
		
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1, gs.getTitle());
		ps1.setString(2, gs.getMemo());
		ps1.setInt(3, gs.getMemory());
		
		int r=ps1.executeUpdate();
		
		if (r!=1) {
			con.rollback();
			System.out.println("�ѹ�");
		}
		con.commit();
		ps1.close();
		con.setAutoCommit(true);
		
		String sql2="SELECT MTITLE, MDATE FROM PRO_MEMO "
				+ "ORDER BY MDATE DESC"; //���̺� ���ΰ�ħ
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
		
		String sql1="SELECT MCODE FROM PRO_MEMO " //��¥�� �⺻Ű ������
				+ "WHERE MDATE=TO_DATE"
				+ "('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs=ps1.executeQuery();
		
		int code=0;
		while (rs.next()) {
			code=rs.getInt("MCODE");
		}
		ps1.close();
		
		String sql2="UPDATE PRO_MEMO  SET MTITLE=?, " //������
				+ "MMEMO=?, "
				+ "MDATE=SYSDATE, "
				+ "MBYTE=? "
				+ "WHERE MCODE='"+code+"'";
//				�⺻Ű�� �ƴ϶� �ϳ� ���� ����°ǰ�?
//				+ "('"+data+"', 'yyyy-mm-dd hh24:mi:ss')";
		
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setString(1, gs.getTitle());
		ps2.setString(2, gs.getMemo());
		ps2.setInt(3, gs.getMemory());
		
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT MTITLE, MDATE FROM PRO_MEMO " //���ΰ�ħ
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
		String sql1="SELECT MCODE FROM PRO_MEMO " //��¥ ������
				+ "WHERE MDATE=TO_DATE"
				+ "('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		PreparedStatement ps1=con.prepareStatement(sql1);
		ResultSet rs1=ps1.executeQuery();
		
		int code=0;
		while (rs1.next()) {
			code=rs1.getInt("MCODE");
		}
		ps1.close();
		
		String sql2="DELETE FROM PRO_MEMO WHERE MCODE=?"; //������
		PreparedStatement ps2=con.prepareStatement(sql2);
		ps2.setInt(1, code);
		ps2.executeUpdate();
		ps2.close();
		
		String sql3="SELECT MTITLE, MDATE FROM PRO_MEMO " //���ΰ�ħ��
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





