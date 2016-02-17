package sight;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class Patient
{
	public static final String STATUS_SINGLE = "Single";
	public static final String STATUS_MARRIED = "Married";
	public static final String STATUS_DIVORCED = "Divorced";
	public static final String STATUS_WIDOWED = "Widowed";
	private long patientID;
	private File photo;
	private Date DOB;
	//private long ageYear;
	//private long ageMonth = 0;
	//private char sex;
	private String status;
	private String telNo;
	private String address;
	private Date lastUpdate;
	private int slumID;
	private int userID;

	private StringProperty firstName;
	private StringProperty middleName;
	private StringProperty lastName;
	private IntegerProperty ageYear;
	private IntegerProperty ageMonth;
	private IntegerProperty ageDay;
	private StringProperty sex;

	public Patient()
	{
		this.patientID = 0;
		this.firstName = new SimpleStringProperty("");
		this.middleName = new SimpleStringProperty("");
		this.lastName = new SimpleStringProperty("");
		this.ageYear = new SimpleIntegerProperty(0);
		this.ageMonth = new SimpleIntegerProperty(0);
		this.ageDay = new SimpleIntegerProperty(0);
		this.sex = new SimpleStringProperty("M");
		this.status = "";
		this.address = "";
		this.telNo = "";
	}

	public Patient(long patientID, String firstName, String middleName, String lastName, int ageYear, int ageMonth, int ageDay, char sex, String status, String address, String telNo)
	{
		this.patientID = patientID;
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.ageYear = new SimpleIntegerProperty(ageYear);
		this.ageMonth = new SimpleIntegerProperty(ageMonth);
		this.ageDay = new SimpleIntegerProperty(ageDay);
		this.sex = new SimpleStringProperty(sex+"");
		this.status = status;
		this.address = address;
		this.telNo = telNo;
	}

	public Patient(long patientID, String firstName, String middleName, String lastName, File photo, Date DOB, int ageYear, int ageMonth, int ageDay, char sex, String status, String telNo, String address, int slumID, int userID)
	{
		this.patientID = patientID;
		this.firstName = new SimpleStringProperty(firstName);
		this.middleName = new SimpleStringProperty(middleName);
		this.lastName = new SimpleStringProperty(lastName);
		this.slumID = slumID;
		this.photo = photo;
		this.DOB = DOB;
		//long age = DOB.toLocalDate().until(LocalDate.now(), ChronoUnit.YEARS);
		this.ageYear = new SimpleIntegerProperty(ageYear);
		this.ageMonth = new SimpleIntegerProperty(ageMonth);
		this.ageDay = new SimpleIntegerProperty(ageDay);
		this.sex = new SimpleStringProperty(sex+"");
		this.status = status;
		this.telNo = telNo;
		this.address = address;
		this.userID = userID;
	}

	public StringProperty firstNameProperty() { return firstName; }
	public StringProperty middleNameProperty() { return middleName; }
	public StringProperty lastNameProperty() { return lastName; }
	public IntegerProperty ageYearProperty() { return ageYear; }
	public IntegerProperty ageMonthProperty() { return ageMonth; }
	public IntegerProperty ageDayProperty() { return ageDay; }
	public StringProperty sexProperty() { return sex; }

	public long getpatientID() { return patientID; }
	public String getfirstName() { return firstName.get(); }
	public String getmiddleName() { return middleName.get(); }
	public String getlastName() { return lastName.get(); }
	public int getslumID() { return slumID; }
	public File getphoto() { return photo; }
	public Date getDOB() { return DOB; }
	public int getageYear() { return ageYear.get(); }
	public int getageMonth() { return ageMonth.get(); }
	public int getageDay() { return ageDay.get(); }
	public char getsex() { return sex.get().charAt(0); }
	public String getstatus() { return status; }
	public String gettelNo() { return telNo; }
	public String getaddress() { return address; }
	public Date getlastUpdate() { return lastUpdate; }
	public int getuserID() { return userID; }
	
	public void setpatientID(long patientID) { this.patientID = patientID; }
	public void setfirstName(String firstName) { this.firstName = new SimpleStringProperty(firstName); }
	public void setmiddleName(String middleName) { this.middleName = new SimpleStringProperty(middleName); }
	public void setlastName(String lastName) { this.lastName = new SimpleStringProperty(lastName); }
	public void setslumID(int slumID) { this.slumID = slumID; }
	public void setphoto(File photo) { this.photo = photo; }
	public void setDOB(Date DOB) { this.DOB = DOB; }
	public void setageYear(int ageYear) { this.ageYear = new SimpleIntegerProperty(ageYear); }
	public void setageMonth(int ageMonth) { this.ageMonth = new SimpleIntegerProperty(ageMonth); }
	public void setageDay(int ageDay) { this.ageDay = new SimpleIntegerProperty(ageDay); }
	public void setage(Date dob)
	{
		this.ageYear = new SimpleIntegerProperty((int)(dob.toLocalDate().until(LocalDate.now(), ChronoUnit.YEARS)));
		if( this.ageYear.get() == 0 )
		{
			this.ageMonth = new SimpleIntegerProperty((int)(dob.toLocalDate().until(LocalDate.now(), ChronoUnit.MONTHS)));
			Calendar c = Calendar.getInstance();
			c.setTime(dob);
			c.add(Calendar.MONTH, ageMonth.get());
			Date dobForDay = new java.sql.Date(c.getTimeInMillis());
			int days = (int)(dobForDay.toLocalDate().until(LocalDate.now(), ChronoUnit.DAYS));
			this.ageDay = new SimpleIntegerProperty(days);
		}
	}
	public void setsex(char sex) { this.sex = new SimpleStringProperty(sex+""); }
	public void setstatus(String status) { this.status = status; }
	public void settelNo(String telNo) { this.telNo = telNo; }
	public void setaddress(String address) { this.address = address; }
	public void setlastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
	public void setuserID(int userID) { this.userID = userID; }
}
