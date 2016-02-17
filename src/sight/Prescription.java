package sight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
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

public class Prescription
{
	private LongProperty prescriptionID;
	private StringProperty medicine;
	private StringProperty type;
	private IntegerProperty dosage;
	private StringProperty unitOfDosage;
	private StringProperty dosageString;
	private StringProperty route;
	private StringProperty strength;
	private IntegerProperty frequency;
	private IntegerProperty numOfDays;
	private FloatProperty quantity;
	private StringProperty unitOfQuantity;
	private StringProperty brand;
	private StringProperty batchNo;
	private StringProperty remark;
	private BooleanProperty isPrescripted;
	private IntegerProperty inventoryID;
	private LongProperty visitID;
	private IntegerProperty doctorID;	
	private IntegerProperty pharmacistID;
	private BooleanProperty isActive;
	private BooleanProperty isSelected;
	private IntegerProperty numOfTablets;

	public Prescription( String medicine )
	{
		this.prescriptionID = new SimpleLongProperty(-1);
		this.medicine = new SimpleStringProperty(medicine);
		this.type = new SimpleStringProperty("");
		this.dosage = new SimpleIntegerProperty(0);
		this.unitOfDosage = new SimpleStringProperty("");
		this.dosageString = new SimpleStringProperty("");
		this.route = new SimpleStringProperty("");
		this.strength = new SimpleStringProperty("");
		this.frequency = new SimpleIntegerProperty(0);
		this.numOfDays = new SimpleIntegerProperty(0);
		this.quantity = new SimpleFloatProperty(0);
		this.unitOfQuantity = new SimpleStringProperty("");
		this.brand = new SimpleStringProperty("");
		this.batchNo = new SimpleStringProperty("");
		this.remark = new SimpleStringProperty("");
		this.isPrescripted = new SimpleBooleanProperty(false);
		this.inventoryID = new SimpleIntegerProperty(0);
		this.visitID = new SimpleLongProperty(0);
		this.doctorID = new SimpleIntegerProperty(0);
		this.pharmacistID = new SimpleIntegerProperty(0);
		this.isActive = new SimpleBooleanProperty(true);
		this.isSelected = new SimpleBooleanProperty( false );
		this.numOfTablets = new SimpleIntegerProperty(0);
	}

	public Prescription( long prescriptionID, String medicine, int dosage, String unitOfDosage, String route, String strength, int frequency, int numOfDays, float quantity, String brand, String batchNo, String remark, boolean isPrescripted, int inventoryID, long visitID, int doctorID, int pharmacistID )
	{
		this.prescriptionID = new SimpleLongProperty(prescriptionID);
		this.medicine = new SimpleStringProperty(medicine);
		//this.type = new SimpleStringProperty(type);
		this.dosage = new SimpleIntegerProperty(dosage);
		this.unitOfDosage = new SimpleStringProperty("");
		this.dosageString = new SimpleStringProperty(dosage+unitOfDosage);
		this.route = new SimpleStringProperty(route);
		this.strength = new SimpleStringProperty(strength);
		this.frequency = new SimpleIntegerProperty(frequency);
		this.numOfDays = new SimpleIntegerProperty(numOfDays);
		this.quantity = new SimpleFloatProperty(quantity);
		this.unitOfQuantity = new SimpleStringProperty("");
		this.brand = new SimpleStringProperty(brand);
		this.batchNo = new SimpleStringProperty(batchNo);
		this.remark = new SimpleStringProperty(remark);
		this.isPrescripted = new SimpleBooleanProperty(isPrescripted);
		this.inventoryID = new SimpleIntegerProperty(inventoryID);
		this.visitID = new SimpleLongProperty(visitID);
		this.doctorID = new SimpleIntegerProperty(doctorID);
		this.pharmacistID = new SimpleIntegerProperty(pharmacistID);
		this.isActive = new SimpleBooleanProperty(true);
		this.isSelected = new SimpleBooleanProperty( false );
		this.numOfTablets = new SimpleIntegerProperty(0);
	}

	public LongProperty prescriptionIDProperty() { return prescriptionID; }
	public StringProperty medicineProperty() { return medicine; }
	public StringProperty typeProperty() { return type; }
	public IntegerProperty dosageProperty() { return dosage; }
	public StringProperty unitOfDosageProperty() { return unitOfDosage; }
	public StringProperty dosageStringProperty() { return dosageString; }
	public StringProperty routeProperty() { return route; }
	public StringProperty strengthProperty() { return strength; }
	public IntegerProperty frequencyProperty() { return frequency; }
	public IntegerProperty numOfDaysProperty() { return numOfDays; }
	public FloatProperty quantityProperty() { return quantity; }
	public StringProperty unitOfQuantityProperty() { return unitOfQuantity; }
	public StringProperty brandProperty() { return brand; }
	public StringProperty batchNoProperty() { return batchNo; }
	public StringProperty remarkProperty() { return remark; }
	public BooleanProperty isPrescriptedProperty() { return isPrescripted; }
	public IntegerProperty inventoryIDProperty() { return inventoryID; }
	public LongProperty visitIDProperty() { return visitID; }
	public IntegerProperty doctorIDProperty() { return doctorID; }
	public IntegerProperty pharmacistIDProperty() { return pharmacistID; }
	public BooleanProperty isActiveProperty() { return isActive; }
	public BooleanProperty isSelectedProperty() { return isSelected; }
	public IntegerProperty numOfTabletsProperty() { return numOfTablets; }

	public long getprescriptionID() { return prescriptionID.get(); }
	public String getmedicine() { return medicine.get(); }
	public String gettype() { return type.get(); }
	public int getdosage() { return dosage.get(); }
	public String getunitOfDosage() { return unitOfDosage.get(); }
	public String getdosageString() { return dosage.get()+unitOfDosage.get(); }
	public String getroute() { return route.get(); }
	public String getstrength() { return strength.get(); }
	public int getfrequency() { return frequency.get(); }
	public int getnumOfDays() { return numOfDays.get(); }
	public float getquantity() { return quantity.get(); }
	public String getunitOfQuantity() { return unitOfQuantity.get(); }
	public String getbrand() { return brand.get(); }
	public String getbatchNo() { return batchNo.get(); }
	public String getremark() { return remark.get(); }
	public boolean getisPrescripted() { return isPrescripted.get(); }
	public int getinventoryID() { return inventoryID.get(); }
	public long getvisitID() { return visitID.get(); }
	public int getdoctorID() { return doctorID.get(); }
	public int getpharmacistID() { return pharmacistID.get(); }
	public boolean getisActive() { return isActive.get(); }
	public boolean getisSelected() { return isSelected.get(); }
	public int getnumOfTablets() { return numOfTablets.get(); }

	public void setprescriptionID(long prescriptionID) { this.prescriptionID = new SimpleLongProperty( prescriptionID ); }
	public void setmedicine(String medicine) { this.medicine.set(medicine); }
	public void settype(String type) { this.type.set(type); }
	public void setdosage(int dosage) { this.dosage = new SimpleIntegerProperty( dosage ); this.dosageString = new SimpleStringProperty(dosage+unitOfDosage.get()); }
	public void setunitOfDosage(String unitOfDosage) { this.unitOfDosage = new SimpleStringProperty( unitOfDosage ); this.dosageString = new SimpleStringProperty(dosage.get()+unitOfDosage); }
	public void setdosageString(String dosageString) { this.dosageString = new SimpleStringProperty( dosageString ); }
	public void setroute(String route) { this.route = new SimpleStringProperty( route ); }
	public void setstrength(String strength) { this.strength = new SimpleStringProperty( strength ); }
	public void setfrequency(int frequency) { this.frequency = new SimpleIntegerProperty( frequency ); }
	public void setnumOfDays(int numOfDays) { this.numOfDays = new SimpleIntegerProperty( numOfDays ); }
	public void setquantity(float quantity) { this.quantity = new SimpleFloatProperty( quantity ); }
	public void setunitOfQuantity(String unitOfQuantity) { this.unitOfQuantity = new SimpleStringProperty( unitOfQuantity ); }
	public void setbrand(String brand) { this.brand = new SimpleStringProperty( brand ); }
	public void setbatchNo(String batchNo) { this.batchNo = new SimpleStringProperty( batchNo ); }
	public void setremark(String remark) { this.remark = new SimpleStringProperty( remark ); }
	public void setisPrescripted(boolean isPrescripted) { this.isPrescripted = new SimpleBooleanProperty( isPrescripted ); }
	public void setinventoryID(int inventoryID) { this.inventoryID = new SimpleIntegerProperty( inventoryID ); }
	public void setvisitID(long visitID) { this.visitID = new SimpleLongProperty( visitID ); }
	public void setdoctorID(int doctorID) { this.doctorID = new SimpleIntegerProperty( doctorID ); }
	public void setpharmacistID(int pharmacistID) { this.pharmacistID = new SimpleIntegerProperty( pharmacistID ); }
	public void setisActive(boolean isActive) { this.isActive = new SimpleBooleanProperty( isActive ); }
	public void setisSelected(boolean isSelected) { this.isSelected = new SimpleBooleanProperty( isSelected ); }
	public void setnumOfTablets(int numOfTablets) { this.numOfTablets = new SimpleIntegerProperty( numOfTablets ); }

	public int getLiquidStrengthMg()
	{
		String numString = strength.get().substring(0, strength.get().indexOf('m'));
		return Integer.parseInt(numString);
	}

	public int getLiquidStrengthMl()
	{
		String numString = strength.get().substring(strength.get().indexOf('/') + 1, strength.get().lastIndexOf('m'));
		return Integer.parseInt(numString);
	}

	public static int getSolidStrengthMgPerTablet( String strength )
	{
		String numString;
		if(strength.contains("Iu"))
			numString = strength.substring(0, strength.indexOf('I'));
		else if(strength.contains("unit"))
			numString = strength.substring(0, strength.indexOf('u'));
		else
			numString = strength.substring(0, strength.indexOf('m'));
		return Integer.parseInt(numString);
	}
}
