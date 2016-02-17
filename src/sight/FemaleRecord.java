package sight;

import java.sql.Date;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class FemaleRecord {
	
	private long femaleID;
	private Date LMP = null;
	private boolean isPregnant = false;
	private int gestation = 0;
	private boolean isBreastFeeding = false;
	private boolean isContraceptive = false;
	private int numOfPregnancy = 0;
	private int numOfLiveBirth = 0;
	private int numOfMiscarriage = 0;
	private int numOfAbortion = 0;
	private int numOfStillBirth = 0;
	private String otherInfo = "";
	private Date lastUpdate;
	private long patientID;
	private int userID;

	FemaleRecord()
	{
	}

	FemaleRecord(long patientID, Date LMP, boolean isPregnant, int gestation, boolean isBreastFeeding, boolean isContraceptive, int numOfPregnancy, int numOfLiveBirth, int numOfMiscarriage, int numOfAbortion, int numOfStillBirth, String otherInfo, int userID)
	{
		this.patientID = patientID;
		this.LMP = LMP;
		this.isPregnant = isPregnant;
		this.gestation = gestation;
		this.isBreastFeeding = isBreastFeeding;
		this.isContraceptive = isContraceptive;
		this.numOfPregnancy = numOfPregnancy;
		this.numOfLiveBirth = numOfLiveBirth;
		this.numOfMiscarriage = numOfMiscarriage;
		this.numOfAbortion = numOfAbortion;
		this.numOfStillBirth = numOfStillBirth;
		this.otherInfo = otherInfo;
		this.userID = userID;
	}

	public long getfemaleID() { return femaleID; }
	public Date getLMP() { return LMP; }
	public boolean isPregnant()	{ return isPregnant; }
	public int getgestation() { return gestation; }
	public boolean isBreastFeeding() { return isBreastFeeding; }
	public boolean isContraceptive() { return isContraceptive; }
	public int getnumOfPregnancy() { return numOfPregnancy; }
	public int getnumOfLiveBirth() { return numOfLiveBirth; }
	public int getnumOfMiscarriage() { return numOfMiscarriage; }
	public int getnumOfAbortion() { return numOfAbortion; }
	public int getnumOfStillBirth() { return numOfStillBirth; }
	public String getotherInfo() { return otherInfo; }
	public Date getlastUpdate() { return lastUpdate; }
	public long getpatientID() { return patientID; }
	public int getuserID() { return userID; }
	
	public void setfemaleID(long femaleID) { this.femaleID = femaleID; }
	public void setLMP(Date LMP) { this.LMP = LMP; }
	public void setisPregnant(boolean isPregnant) { this.isPregnant = isPregnant; }
	public void setgestation(int gestation) { this.gestation = gestation; }
	public void setisBreastFeeding(boolean isBreastFeeding) { this.isBreastFeeding = isBreastFeeding; }
	public void setisContraceptive(boolean isContraceptive) { this.isContraceptive = isContraceptive; }
	public void setnumOfPregnancy(int numOfPregnancy) { this.numOfPregnancy = numOfPregnancy; }
	public void setnumOfLiveBirth(int numOfLiveBirth) { this.numOfLiveBirth = numOfLiveBirth; }
	public void setnumOfMiscarriage(int numOfMiscarriage) { this.numOfMiscarriage = numOfMiscarriage; }
	public void setnumOfAbortion(int numOfAbortion) { this.numOfAbortion = numOfAbortion; }
	public void setnumOfStillBirth(int numOfStillBirth) { this.numOfStillBirth = numOfStillBirth; }
	public void setotherInfo(String otherInfo) { this.otherInfo = otherInfo; }
	public void setlastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
	public void setpatientID(long patientID) { this.patientID = patientID; }
	public void setuserID(int userID) { this.userID = userID; }
}
