package finance_func;

public class FinanceGS {
	
	//���ø���Ʈ
	int wishRank;
	String wishName;
	String wishCost; //���ڿ��ص� ��� ������?
	String wishDescribe;
	
	public int getWishRank() {
		return wishRank;
	}
	public void setWishRank(int wishRank) {
		this.wishRank = wishRank;
	}
	public String getWishName() {
		return wishName;
	}
	public void setWishName(String wishName) {
		this.wishName = wishName;
	}
	public String getWishCost() {
		return wishCost;
	}
	public void setWishCost(String wishCost) {
		this.wishCost = wishCost;
	}
	public String getWishDescribe() {
		return wishDescribe;
	}
	public void setWishDescribe(String wishDescribe) {
		this.wishDescribe = wishDescribe;
	}
	
	//�ŷ����� (���ٰ� ������ ���ּ�)
	int tcode;
	String tio;
	int tamount;
	String tdescribe;
	String tdate;
	
	int row; //transact modify

	public int getTcode() {
		return tcode;
	}
	public void setTcode(int tcode) {
		this.tcode = tcode;
	}
	public String getTio() {
		return tio;
	}
	public void setTio(String tio) {
		this.tio = tio;
	}
	public int getTamount() {
		return tamount;
	}
	public void setTamount(int tamount) {
		this.tamount = tamount;
	}
	public String getTdescribe() {
		return tdescribe;
	}
	public void setTdescribe(String tdescribe) {
		this.tdescribe = tdescribe;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
	int totalmoney;
	int dailypay;
	int weeklypay;
	int monthlypay;

	public int getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(int totalmoney) {
		this.totalmoney = totalmoney;
	}
	public int getDailypay() {
		return dailypay;
	}
	public void setDailypay(int dailypay) {
		this.dailypay = dailypay;
	}
	public int getWeeklypay() {
		return weeklypay;
	}
	public void setWeeklypay(int weeklypay) {
		this.weeklypay = weeklypay;
	}
	public int getMonthlypay() {
		return monthlypay;
	}
	public void setMonthlypay(int monthlypay) {
		this.monthlypay = monthlypay;
	}
	
}
