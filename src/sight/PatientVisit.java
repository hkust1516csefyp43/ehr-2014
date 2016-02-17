package sight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class PatientVisit
{
	private LongProperty visitID;
	private StringProperty visitDateTimeString;
	private IntegerProperty tagNumber;
	private BooleanProperty isTriage;
	private BooleanProperty isConsulted;
	private BooleanProperty isFinished;
	private LongProperty patientID;
	private StringProperty patientName;
	private StringProperty nurseName;
	private StringProperty doctorName;
	private StringProperty pharmacistName;
	private IntegerProperty nurseID;
	private IntegerProperty doctorID;
	private IntegerProperty pharmacistID;

	public PatientVisit()
	{
	}

	public PatientVisit(long visitID, String visitDateTimeString, int tagNumber, long patientID, boolean isTriage, boolean isConsulted, boolean isFinished, String patientName, String nurseName, String doctorName, String pharmacistName, int nurseID, int doctorID, int pharmacistID)
	{
		this.visitID = new SimpleLongProperty( visitID );
		this.visitDateTimeString = new SimpleStringProperty( visitDateTimeString );
		this.tagNumber = new SimpleIntegerProperty( tagNumber );
		this.patientID = new SimpleLongProperty( patientID );
		this.isTriage = new SimpleBooleanProperty(isTriage);
		this.isConsulted = new SimpleBooleanProperty(isConsulted);
		this.isFinished = new SimpleBooleanProperty(isFinished);
		this.patientName = new SimpleStringProperty( patientName );
		this.nurseName = new SimpleStringProperty( nurseName );
		this.doctorName = new SimpleStringProperty( doctorName );
		this.pharmacistName = new SimpleStringProperty( pharmacistName );
		this.nurseID = new SimpleIntegerProperty( nurseID );
		this.doctorID = new SimpleIntegerProperty( nurseID );
		this.pharmacistID = new SimpleIntegerProperty( pharmacistID );
	}

	public LongProperty visitIDProperty() { return visitID; }
	public StringProperty visitDateTimeStringProperty() { return visitDateTimeString; }
	public IntegerProperty tagNumberProperty() { return tagNumber; }
	public LongProperty patientIDProperty() { return patientID; }
	public BooleanProperty isTriageProperty() { return isTriage; }
	public BooleanProperty isConsultedProperty() { return isConsulted; }
	public BooleanProperty isFinishedProperty() { return isFinished; }
	public StringProperty patientNameProperty() { return patientName; }
	public StringProperty nurseNameProperty() { return nurseName; }
	public StringProperty doctorNameProperty() { return doctorName; }
	public StringProperty pharmacistNameProperty() { return pharmacistName; }
	public IntegerProperty nurseIDProperty() { return nurseID; }
	public IntegerProperty doctorIDProperty() { return doctorID; }
	public IntegerProperty pharmacistIDProperty() { return pharmacistID; }

	public long getvisitID() { return visitID.get();	}
	public String getvisitDateTimeString() { return visitDateTimeString.get(); }
	public int gettagNumber() { return tagNumber.get(); }
	public long getpatientID() { return patientID.get(); }
	public boolean getisTriage() { return isTriage.get(); }
	public boolean getisConsulted() { return isConsulted.get(); }
	public boolean getisFinished() { return isFinished.get(); }
	public String getpatientName() { return patientName.get(); }
	public String getnurseName() { return nurseName.get(); }
	public String getdoctorName() { return doctorName.get(); }
	public String getpharmacistName() { return pharmacistName.get(); }
	public int getnurseID() { return nurseID.get(); }
	public int getdoctorID() { return doctorID.get(); }
	public int getpharmacistID() { return pharmacistID.get(); }

	public void setvisitID(long visitID) { this.visitID = new SimpleLongProperty( visitID ); }
	public void setvisitDateTimeString(String visitDateTimeString) { this.visitDateTimeString = new SimpleStringProperty( visitDateTimeString ); }
	public void settagNumber(int tagNumber) { this.tagNumber = new SimpleIntegerProperty( tagNumber ); }
	public void setpatientID(long patientID) { this.patientID = new SimpleLongProperty( patientID ); }
	public void setisTriage(boolean isTriage) { this.isTriage = new SimpleBooleanProperty( isTriage ); }
	public void setisConsulted(boolean isConsulted) { this.isConsulted = new SimpleBooleanProperty( isConsulted ); }
	public void setisFinished(boolean isFinished) { this.isFinished = new SimpleBooleanProperty( isFinished ); }
	public void setpatientName(String name) { this.patientName = new SimpleStringProperty( name ); }
	public void setnurseName(String nurseName) { this.nurseName = new SimpleStringProperty( nurseName ); }
	public void setdoctorName(String doctorName) { this.doctorName = new SimpleStringProperty( doctorName ); }
	public void setpharmacistName(String pharmacistName) { this.pharmacistName = new SimpleStringProperty( pharmacistName ); }
	public void setnurseID(int nurseID) { this.nurseID = new SimpleIntegerProperty( nurseID ); }
	public void setdoctorID(int doctorID) { this.doctorID = new SimpleIntegerProperty( doctorID ); }
	public void setpharmacistID(int pharmacistID) { this.pharmacistID = new SimpleIntegerProperty( pharmacistID ); }
}
