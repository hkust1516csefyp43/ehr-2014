package sight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class PatientDailyRecord
{
	private StringProperty patientName;
	private StringProperty sex;
	private StringProperty age;
	private BooleanProperty isNew;
	private DoubleProperty height;
	private DoubleProperty weight;
	private StringProperty chiefComplaint;
	private StringProperty diagnosis;
	private StringProperty nurse;
	private StringProperty doctor;
	private StringProperty pharmacist;

	public PatientDailyRecord()
	{
	}

	public PatientDailyRecord(String patientName, String sex, String age, boolean isNew, double height, double weight, String chiefComplaint, String diagnosis, String nurseName, String doctorName, String pharmacistName)
	{
		this.patientName = new SimpleStringProperty(patientName);
		this.sex = new SimpleStringProperty(sex);
		this.age = new SimpleStringProperty(age);
		this.isNew = new SimpleBooleanProperty(isNew);
		this.height = new SimpleDoubleProperty(height);
		this.weight = new SimpleDoubleProperty(weight);
		this.chiefComplaint = new SimpleStringProperty(chiefComplaint);
		this.diagnosis = new SimpleStringProperty(diagnosis);
		this.nurse = new SimpleStringProperty(nurseName);
		this.doctor = new SimpleStringProperty(doctorName);
		this.pharmacist = new SimpleStringProperty(pharmacistName);
	}

	public StringProperty patientNameProperty() { return patientName; }
	public StringProperty sexProperty() { return sex; }
	public StringProperty ageProperty() { return age; }
	public BooleanProperty isNewProperty() { return isNew; }
	public DoubleProperty heightProperty() { return height; }
	public DoubleProperty weightProperty() { return weight; }
	public StringProperty chiefComplaintProperty() { return chiefComplaint; }
	public StringProperty diagnosisProperty() { return diagnosis; }
	public StringProperty nurseProperty() { return nurse; }
	public StringProperty doctorProperty() { return doctor; }
	public StringProperty pharmacistProperty() { return pharmacist; }

	public String getpatientName() { return patientName.get(); }
	public String getsex() { return sex.get(); }
	public String getage() { return age.get(); }
	public boolean getisNew() { return isNew.get(); }
	public double getheight() { return height.get(); }
	public double getweight() { return weight.get(); }
	public String getchiefComplaint() { return chiefComplaint.get(); }
	public String getdiagnosis() { return diagnosis.get(); }
	public String getnurse() { return nurse.get(); }
	public String getdoctor() { return doctor.get(); }
	public String getpharmacist() { return pharmacist.get(); }

	public void setpatientName(String name) { this.patientName = new SimpleStringProperty(name); }
	public void setsex(String sex) { this.sex = new SimpleStringProperty(sex); }
	public void setage(String age) { this.age = new SimpleStringProperty(age); }
	public void setisNew(boolean isNew) { this.isNew = new SimpleBooleanProperty(isNew); }
	public void setheight(double height) { this.height = new SimpleDoubleProperty(height); }
	public void setweight(double weight) { this.weight = new SimpleDoubleProperty(weight); }
	public void setchiefComplaint(String chiefComplaint) { this.chiefComplaint = new SimpleStringProperty(chiefComplaint); }
	public void setdiagnosis(String diagnosis) { this.diagnosis = new SimpleStringProperty(diagnosis); }
	public void setnurse(String nurseName) { this.nurse = new SimpleStringProperty(nurseName); }
	public void setdoctor(String doctorName) { this.doctor = new SimpleStringProperty(doctorName); }
	public void setpharmacist(String pharmacistName) { this.pharmacist = new SimpleStringProperty(pharmacistName); }
}
