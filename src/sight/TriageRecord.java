package sight;

import java.sql.Date;
import java.util.Calendar;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class TriageRecord {

	private long triageID;
	private float temperature;
	private char temperatureScale;
	private int SBP;
	private int DBP;
	private int PR;
	private int RR;
	private float spo2;
	private float weight;
	private float height;
	private Date LDT;
	private long visitID;
	private int userID;

	public TriageRecord()
	{
		triageID = -1;
		temperature = 0.0F;
		temperatureScale = 'C';
		SBP = 0;
		DBP = 0;
		PR = 0;
		RR = 0;
		spo2 = 0;
		weight = 0;
		height = 0;
		LDT = new Date(Calendar.getInstance().getTimeInMillis());
		visitID = -1;
		userID = -1;
	}

	public TriageRecord(long triageID, float temperature, char temperatureScale, int SBP, int DBP, int PR, int RR, float Spo2, float weight, float height, Date LDT, long visitID, int userID)
	{
		this.triageID = triageID;
		this.temperature = temperature;
		this.temperatureScale = temperatureScale;
		this.SBP = SBP;
		this.DBP = DBP;
		this.PR = PR;
		this.RR = RR;
		this.spo2 = Spo2;
		this.weight = weight;
		this.height = height;
		this.LDT = LDT;
		this.visitID = visitID;
		this.userID = userID;
	}

	public long gettriageID() { return triageID; }
	public float gettemperature() { return temperature; }
	public char gettemperatureScale() { return temperatureScale; }
	public int getSBP() { return SBP; }
	public int getDBP() { return DBP; }
	public int getPR() { return PR; }
	public int getRR() { return RR; }
	public float getspo2() { return spo2; }
	public float getweight() { return weight; }
	public float getheight() { return height; }
	public Date getLDT() { return LDT; }
	public long getvisitID() { return visitID; }
	public int getuserID() { return userID; }
	
	public void settriageID(long triageID) { this.triageID = triageID; }
	public void settemperature(float temperature) { this.temperature = temperature; }
	public void settemperatureScale(char temperatureScale) { this.temperatureScale = temperatureScale; }
	public void setSBP(int SBP) { this.SBP = SBP; }
	public void setDBP(int DBP) { this.DBP = DBP; }
	public void setPR(int PR) { this.PR = PR; }
	public void setRR(int RR) { this.RR = RR; }
	public void setSpo2(float Spo2) { this.spo2 = Spo2; }
	public void setweight(float weight) { this.weight = weight; }
	public void setheight(float height) { this.height = height; }
	public void setLDT(Date LDT) { this.LDT = LDT; }
	public void setvisitID(long visitID) { this.visitID = visitID; }
	public void setuserID(int userID) { this.userID = userID; }
}
