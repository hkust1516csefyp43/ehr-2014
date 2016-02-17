package sight;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
*
* @author Stephen, Alton, Michelle
* @version 1.0
* @date 22/05/2015
* 
*/

public class User
{
	private static User uniqueInstance = new User();
	private int id;
	private StringProperty name;
	private String password;
	private IntegerProperty roleID;
	private BooleanProperty triageAccess;
	private BooleanProperty consultationAccess;
	private BooleanProperty pharmacyAccess;
	private BooleanProperty inventoryAccess;
	private BooleanProperty statisticsAccess;
	private BooleanProperty supervisorAccess;
	private BooleanProperty adminAccess;
	private BooleanProperty isActive;
	public static final String DEFAULT_DECRYPTED_PASSWORD = "sight121";

	private User()
	{
		this.id = -1;
		this.name = new SimpleStringProperty("");
		this.roleID = new SimpleIntegerProperty(0);
		this.triageAccess = new SimpleBooleanProperty(false);
		this.consultationAccess = new SimpleBooleanProperty(false);
		this.pharmacyAccess = new SimpleBooleanProperty(false);
		this.inventoryAccess = new SimpleBooleanProperty(false);
		this.statisticsAccess = new SimpleBooleanProperty(false);
		this.supervisorAccess = new SimpleBooleanProperty(false);
		this.adminAccess = new SimpleBooleanProperty(false);
		this.isActive = new SimpleBooleanProperty(true);
	}

	public User( String password )
	{
		this.id = -1;
		this.password = password;
		this.name = new SimpleStringProperty("");
		this.roleID = new SimpleIntegerProperty(0);
		this.triageAccess = new SimpleBooleanProperty(false);
		this.consultationAccess = new SimpleBooleanProperty(false);
		this.pharmacyAccess = new SimpleBooleanProperty(false);
		this.inventoryAccess = new SimpleBooleanProperty(false);
		this.statisticsAccess = new SimpleBooleanProperty(false);
		this.supervisorAccess = new SimpleBooleanProperty(false);
		this.adminAccess = new SimpleBooleanProperty(false);
		this.isActive = new SimpleBooleanProperty(true);
	}

	public User( String name, int roleID, boolean triageAccess, boolean consultationAccess, boolean pharmacyAccess, boolean inventoryAccess, boolean statisticsAccess, boolean supervisorAccess, boolean adminAccess, boolean isActive )
	{
		this.name = new SimpleStringProperty(name);
		this.roleID = new SimpleIntegerProperty(roleID);
		this.triageAccess = new SimpleBooleanProperty(triageAccess);
		this.consultationAccess = new SimpleBooleanProperty(consultationAccess);
		this.pharmacyAccess = new SimpleBooleanProperty(pharmacyAccess);
		this.inventoryAccess = new SimpleBooleanProperty(inventoryAccess);
		this.statisticsAccess = new SimpleBooleanProperty(statisticsAccess);
		this.supervisorAccess = new SimpleBooleanProperty(supervisorAccess);
		this.adminAccess = new SimpleBooleanProperty(adminAccess);
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public static User getCurrentUser(){ return uniqueInstance; }

	public int getid() { return id; }
	public String getname() { return name.get(); }
	public String getpassword() { return password; }
	public int getroleID() { return roleID.get(); }
	public boolean gettriageAccess() { return triageAccess.get(); }
	public boolean getconsultationAccess() { return consultationAccess.get(); }
	public boolean getpharmacyAccess() { return pharmacyAccess.get(); }
	public boolean getinventoryAccess() { return inventoryAccess.get(); }
	public boolean getstatisticsAccess() { return statisticsAccess.get(); }
	public boolean getsupervisorAccess() { return supervisorAccess.get(); }
	public boolean getadminAccess() { return adminAccess.get(); }
	public boolean getisActive() { return isActive.get(); }

	public StringProperty nameProperty() { return name; }
	public IntegerProperty roleIDProperty() { return roleID; }
	public BooleanProperty triageAccessProperty() { return triageAccess; }
	public BooleanProperty consultationAccessProperty() { return consultationAccess; }
	public BooleanProperty pharmacyAccessProperty() { return pharmacyAccess; }
	public BooleanProperty inventoryAccessProperty() { return inventoryAccess; }
	public BooleanProperty statisticsAccessProperty() { return statisticsAccess; }
	public BooleanProperty supervisorAccessProperty() { return supervisorAccess; }
	public BooleanProperty adminAccessProperty() { return adminAccess; }
	public BooleanProperty isActiveProperty() { return isActive; }

	public void setid(int id) { this.id = id; }
	public void setname(String name) { this.name.set(name); }
	public void setpassword(String password) { this.password = password; }
	public void setroleID(int roleID) { this.roleID.set(roleID); }
	public void settriageAccess(boolean access) { triageAccess.set(access); }
	public void setconsultationAccess(boolean access) { consultationAccess.set(access); }
	public void setpharmacyAccess(boolean access) { pharmacyAccess.set(access); }
	public void setinventoryAccess(boolean access) { inventoryAccess.set(access); }
	public void setstatisticsAccess(boolean access) { statisticsAccess.set(access); }
	public void setsupervisorAccess(boolean access) { supervisorAccess.set(access); }
	public void setadminAccess(boolean access) { adminAccess.set(access); }
	public void setisActive(boolean isActive) { this.isActive.set(isActive); }

	public static String getEncryptedPassword( String password ) 
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
		
			byte by[] = md.digest();
		
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < by.length; i++)
				sb.append(Integer.toString((by[i] & 0xff) + 0x100, 16).substring(1));
			return sb.toString();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
