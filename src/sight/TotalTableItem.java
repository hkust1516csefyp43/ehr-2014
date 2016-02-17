package sight;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TotalTableItem
{
	private StringProperty patientSex;
	private IntegerProperty ageAbove18;
	private IntegerProperty ageAbove18New;
	private IntegerProperty ageBelow18;
	private IntegerProperty ageBelow18New;
	private IntegerProperty totalNew;
	private IntegerProperty total;
	
	TotalTableItem(String sex, int ageAbove18, int ageAbove18New, int ageBelow18, int ageBelow18New, int totalNew, int total)
	{
		this.patientSex = new SimpleStringProperty(sex);
		this.ageAbove18 = new SimpleIntegerProperty(ageAbove18);
		this.ageAbove18New = new SimpleIntegerProperty(ageAbove18New);
		this.ageBelow18 = new SimpleIntegerProperty(ageBelow18);
		this.ageBelow18New = new SimpleIntegerProperty(ageBelow18New);
		this.totalNew = new SimpleIntegerProperty(totalNew);
		this.total = new SimpleIntegerProperty(total);
	}

	public StringProperty patientSexProperty() { return patientSex; }
	public IntegerProperty ageAbove18Property() { return ageAbove18; }
	public IntegerProperty ageAbove18NewProperty() { return ageAbove18New; }
	public IntegerProperty ageBelow18Property() { return ageBelow18; }
	public IntegerProperty ageBelow18NewProperty() { return ageBelow18New; }
	public IntegerProperty totalNewProperty() { return totalNew; }
	public IntegerProperty totalProperty() { return total; }

	public void setpatientSex(String sex) { this.patientSex = new SimpleStringProperty( sex ); }
	public void setageAbove18(int num) { this.ageAbove18 = new SimpleIntegerProperty( num ); }
	public void setageAbove18New(int num) { this.ageAbove18New = new SimpleIntegerProperty( num ); }
	public void setageBelow18(int num) { this.ageBelow18 = new SimpleIntegerProperty( num ); }
	public void setageBelow18New(int num) { this.ageBelow18New = new SimpleIntegerProperty( num ); }
	public void settotalNew(int num) { this.totalNew = new SimpleIntegerProperty( num ); }
	public void settotal(int num) { this.total = new SimpleIntegerProperty( num ); }

	public String getpatientSex() { return patientSex.get(); }
	public int getageAbove18() { return ageAbove18.get(); }
	public int getageAbove18New() { return ageAbove18New.get(); }
	public int getageBelow18() { return ageBelow18.get(); }
	public int getageBelow18New() { return ageBelow18New.get(); }
	public int gettotalNew() { return totalNew.get(); }
	public int gettotal() { return total.get(); }
}
