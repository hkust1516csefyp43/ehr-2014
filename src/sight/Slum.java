package sight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

public class Slum
{
	private StringProperty name;
	private IntegerProperty id;
	private BooleanProperty isActive;

	public Slum()
	{
		this.id = new SimpleIntegerProperty(-1);
		this.name = new SimpleStringProperty("");
		this.isActive = new SimpleBooleanProperty(true);
	}

	public Slum(int id, String name, boolean isActive)
	{
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public int getid() { return id.get(); }
	public String getname() { return name.get(); }
	public boolean getisActive() { return isActive.get(); }

	public IntegerProperty idProperty() { return id; }
	public StringProperty nameProperty() { return name; }
	public BooleanProperty isActiveProperty() { return isActive; }

	public void setid(int id) { this.id.set(id); }
	public void setname(String name) { this.name.set(name); }
	public void setisActive(boolean isActive) { this.isActive.set(isActive); }

	@Override
	public String toString()
	{
		return name.get();
	}
}
