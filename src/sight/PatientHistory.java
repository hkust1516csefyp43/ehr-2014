package sight;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

public class PatientHistory
{
	private long patientHistoryID;
	private StringProperty description;
	private StringProperty entryDateTimeString;
	private StringProperty startDateString;
	private StringProperty remissionDateString;
	private BooleanProperty isUnderTreatment;
	private StringProperty historyType;
	private StringProperty remarks;
	private LongProperty patientID;
	private LongProperty visitID;
	private IntegerProperty userID;
	private BooleanProperty isActive;
	private BooleanProperty isNAD;
	private boolean isChanged;

	//private ObjectProperty<java.sql.Date> entryDateTime;
	public static final String TYPE_GENERAL = "General", TYPE_CC = "CC", TYPE_PMH = "PMH", TYPE_SCR = "Screening", 
			TYPE_DH = "Drug", TYPE_ALE = "Allergy", TYPE_HPI = "HPI", TYPE_FH = "Family", TYPE_SH = "Social", 
			TYPE_ROS = "ROS", TYPE_RF = "RedFlag", TYPE_PE = "PE", TYPE_CD = "Diagnosis", TYPE_INV = "Investigation", 
			TYPE_ADV = "Advice", TYPE_FOL = "FollowUp";
	public static final String SCN_TO = "Tobacco Use", SCN_ETH = "Ethanol Use", SCN_DRUG = "Drug Use", 
			SCN_EX_TO = "Ex-Smoker", SCN_EX_DRUG = "Ex-Drug Use";
	public static final SimpleDateFormat PH_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

	public PatientHistory()
	{
		this.patientHistoryID = -1;
		this.description = new SimpleStringProperty();
		this.entryDateTimeString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.startDateString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.remissionDateString = new SimpleStringProperty();
		this.isUnderTreatment = new SimpleBooleanProperty( false );
		this.historyType = new SimpleStringProperty();
		this.remarks = new SimpleStringProperty();
		this.visitID = new SimpleLongProperty(-1);
		this.isActive = new SimpleBooleanProperty( true );
		this.isNAD = new SimpleBooleanProperty( false );
		this.isChanged = false;
		//Logger.getLogger(AdminPanelController.class.getdName()).log(Level.SEVERE, null, ex);
	}
	
	public PatientHistory( String description, String historyType, long visitID )
	{
		this.patientHistoryID = -1;
		this.description = new SimpleStringProperty( description );
		//this.entryDateTimeString = new SimpleStringProperty();
		this.entryDateTimeString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		//this.startDateString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.startDateString = new SimpleStringProperty();
		this.remissionDateString = new SimpleStringProperty();
		this.isUnderTreatment = new SimpleBooleanProperty( false );
		this.historyType = new SimpleStringProperty( historyType );
		this.remarks = new SimpleStringProperty( "" );
		this.visitID = new SimpleLongProperty(visitID);
		this.isActive = new SimpleBooleanProperty( true );
		this.isNAD = new SimpleBooleanProperty( false );
		this.isChanged = false;
		//Logger.getLogger(AdminPanelController.class.getdName()).log(Level.SEVERE, null, ex);
	}

	public PatientHistory( String description, String historyType, String remarks )
	{
		this.patientHistoryID = -1;
		this.description = new SimpleStringProperty( description );
		//this.entryDateTimeString = new SimpleStringProperty();
		this.entryDateTimeString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.startDateString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.remissionDateString = new SimpleStringProperty();
		this.isUnderTreatment = new SimpleBooleanProperty( false );
		this.historyType = new SimpleStringProperty( historyType );
		this.remarks = new SimpleStringProperty( remarks );
		this.visitID = new SimpleLongProperty(-1);
		this.isActive = new SimpleBooleanProperty( true );
		this.isNAD = new SimpleBooleanProperty( false );
		this.isChanged = false;
		//Logger.getLogger(AdminPanelController.class.getdName()).log(Level.SEVERE, null, ex);
	}

	public PatientHistory( String description, String historyType, long patientID, long visitID, int userID, boolean isUnderTreatment )
	{
		this.patientHistoryID = -1;
		this.description = new SimpleStringProperty( description );
		this.entryDateTimeString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.startDateString = new SimpleStringProperty( PH_DATE_FORMAT.format( new java.util.Date() ) );
		this.remissionDateString = new SimpleStringProperty();
		this.isUnderTreatment = new SimpleBooleanProperty( isUnderTreatment );
		this.historyType = new SimpleStringProperty( historyType );
		this.remarks = new SimpleStringProperty( "" );
		this.patientID = new SimpleLongProperty( patientID );
		this.visitID = new SimpleLongProperty( visitID );
		this.userID = new SimpleIntegerProperty( userID );
		this.isActive = new SimpleBooleanProperty( true );
		this.isNAD = new SimpleBooleanProperty( false );
		this.isChanged = false;
		//Logger.getLogger(AdminPanelController.class.getdName()).log(Level.SEVERE, null, ex);
	}

	public PatientHistory( long patientHistoryID, String description, String entryDateTimeString, String startDateString, String remissionDateString, boolean isUnderTreatment, String historyType, String remarks, long patientID, long visitID, int userID, boolean isActive )
	{
		this.patientHistoryID = patientHistoryID;
		this.description = new SimpleStringProperty( description );
		this.entryDateTimeString = new SimpleStringProperty( entryDateTimeString );
		this.startDateString = new SimpleStringProperty( startDateString );
		this.remissionDateString = new SimpleStringProperty( remissionDateString );
		this.isUnderTreatment = new SimpleBooleanProperty( isUnderTreatment );
		this.historyType = new SimpleStringProperty( historyType );
		this.remarks = new SimpleStringProperty( remarks );
		this.patientID = new SimpleLongProperty( patientID );
		this.visitID = new SimpleLongProperty( visitID );
		this.userID = new SimpleIntegerProperty( userID );
		this.isActive = new SimpleBooleanProperty( isActive );
		this.isNAD = new SimpleBooleanProperty( false );
		this.isChanged = false;
		//Logger.getLogger(AdminPanelController.class.getdName()).log(Level.SEVERE, null, ex);
	}

	@Override
	public String toString(){ return description.get(); }

	public StringProperty descriptionProperty() { return description; }
	public StringProperty entryDateTimeStringProperty() { return entryDateTimeString; }
	public StringProperty startDateStringProperty() { return startDateString; }
	public StringProperty remissionDateStringProperty() { return remissionDateString; }
	public BooleanProperty isUnderTreatmentProperty() { return isUnderTreatment; }
	public StringProperty historyTypeProperty() { return historyType; }
	public StringProperty remarksProperty() { return remarks; }
	public LongProperty patientIDProperty() { return patientID; }
	public LongProperty visitIDProperty() { return visitID; }
	public IntegerProperty userIDProperty() { return userID; }
	public BooleanProperty isActiveProperty() { return isActive; }
	public BooleanProperty isNADProperty() { return isNAD; }
	//public ObjectProperty<java.sql.Date> entryDateTimeProperty() { return entryDateTime; }

	public long getpatientHistoryID() { return patientHistoryID; }
	public String getdescription() { return description.get(); }
	public String getentryDateTimeString() { return entryDateTimeString.get(); }
	public String getstartDateString() { return startDateString.get(); }
	public String getremissionDateString() { return remissionDateString.get(); }
	public boolean getisUnderTreatment() { return isUnderTreatment.get(); }
	public String gethistoryType() { return historyType.get(); }
	public String getremarks() { return remarks.get(); }
	public long getpatientID() { return patientID.get(); }
	public long getvisitID() { return visitID.get(); }
	public int getuserID() { return userID.get(); }
	public boolean getisActive() { return isActive.get(); }
	public boolean getisNAD() { return isNAD.get(); }
	public boolean isChanged() { return isChanged; }
	//public java.sql.Date getentryDateTime() { return entryDateTime.get(); }

	public void setpatientHistoryID(long patientHistoryID) { this.patientHistoryID = patientHistoryID; isChanged = true; }
	public void setdescription(String description) { this.description.set(description); isChanged = true; }
	public void setentryDateTimeString(String entryDateTimeString) { this.entryDateTimeString = new SimpleStringProperty( entryDateTimeString ); isChanged = true; }
	public void setstartDateString(String startDateString) { this.startDateString = new SimpleStringProperty( startDateString ); isChanged = true; }
	public void setremissionDateString(String remissionDateString) { this.remissionDateString = new SimpleStringProperty( remissionDateString ); isChanged = true; }
	public void setisUnderTreatment(boolean isUnderTreatment) { this.isUnderTreatment = new SimpleBooleanProperty( isUnderTreatment ); isChanged = true; }
	public void sethistoryType(String historyType) { this.historyType = new SimpleStringProperty( historyType ); isChanged = true; }
	public void setremarks(String remarks) { this.remarks = new SimpleStringProperty( remarks ); isChanged = true; }
	public void setpatientID(long patientID) { this.patientID = new SimpleLongProperty( patientID ); isChanged = true; }
	public void setvisitID(long visitID) { this.visitID = new SimpleLongProperty( visitID ); isChanged = true; }
	public void setuserID(int userID) { this.userID = new SimpleIntegerProperty( userID ); isChanged = true; }
	public void setisActive(boolean isActive) { this.isActive = new SimpleBooleanProperty( isActive ); isChanged = true; }
	public void setisNAD(boolean isNAD) { this.isNAD = new SimpleBooleanProperty( isNAD ); isChanged = true; }
	public void isChanged(boolean isChanged) { this.isChanged = isChanged; }
	//public void setentryDateTime(java.sql.Date entryDateTime) { this.entryDateTime = new SimpleObjectProperty<java.sql.Date>( entryDateTime ); }

	public int compareTo(PatientHistory ph)
	{
		int value = this.getentryDateTimeString().compareTo(ph.getentryDateTimeString());
		if(value != 0)
			return value;
		else
			return this.historyType.get().compareTo(ph.gethistoryType());
	}
}
