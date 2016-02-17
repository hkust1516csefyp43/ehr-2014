package sight;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class DatabaseController
{
	private static String url;// = JDBC.getURL();
	private static String accountName = JDBC.getaccountName();
	private static String password = JDBC.getpassword();
	private static boolean isFirst = true;
	private static Connection conn;

    /**
     * i.e. Empty constructor
     */
    public DatabaseController() {
    }

	public static void setIsFirst( boolean isFirst )
	{
		DatabaseController.isFirst = isFirst;
	}

	public static boolean isFirst()
	{
		return isFirst;
	}

	public static void setURL( String newURL )
	{
		url = newURL;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( url, accountName, password );
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static long generateID()
	{
		return Calendar.getInstance().getTimeInMillis() * 1000 + User.getCurrentUser().getid();
	}

	public static long addPatient( Patient p )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO Patient (FirstName, MiddleName, LastName, Photo, DOB, AgeYear, AgeMonth, AgeDay, "
					+ "Sex, Status, TelNo, Address, LastUpdate, SlumID, UserID, PatientID) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, p.getfirstName());
			stat.setString(2, p.getmiddleName());
			stat.setString(3, p.getlastName());
			//InputStream inputStream = new FileInputStream( p.getphoto() );
			//stat.setBlob(4, inputStream);
			stat.setNull(4, Types.BLOB);
			stat.setDate(5, p.getDOB());
			stat.setInt(6, p.getageYear());
			stat.setInt(7, p.getageMonth());
			stat.setInt(8, p.getageDay());
			stat.setString(9, ""+p.getsex() );
			stat.setString(10, p.getstatus());
			stat.setString(11, p.gettelNo());
			stat.setString(12, p.getaddress());
			//stat.setDate(13, p.getlastUpdate());
			stat.setTimestamp(13, new Timestamp ((new java.util.Date()).getTime()));
			stat.setInt(14, p.getslumID());
			stat.setInt(15, p.getuserID());
			long newID = generateID();
			stat.setLong(16, newID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
			return newID;
			/*ResultSet rs = executeSQL( "SELECT MAX(PatientID) AS PID FROM Patient" );
			rs.next();
			return rs.getInt("PID");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static long addPatientVisit( long patientID, int nurseID, int slumID )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO PatientVisit (VisitDateTime, TagNumber, IsTriage, IsConsulted, IsFinished, PatientID, NurseID, VisitID) "
					+ "VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			ResultSet rs = executeSQL( "SELECT COUNT(PV.VisitID) AS rowCount FROM PatientVisit PV, Patient P "
					+ "WHERE DATE(PV.visitDateTime) = DATE(NOW()) AND PV.PatientID = P.PatientID AND P.SlumID = "+slumID );
			rs.next();
			stat.setTimestamp(1, new Timestamp ((new java.util.Date()).getTime()));
			stat.setInt(2, rs.getInt("rowCount") + 1);
			stat.setBoolean(3, true);
			stat.setBoolean(4, false);
			stat.setBoolean(5, false);
			stat.setLong(6, patientID);
			stat.setInt(7, nurseID);
			long newID = generateID();
			stat.setLong(8, newID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
			return newID;
			/*rs = executeSQL( "SELECT MAX(VisitID) AS PVID FROM PatientVisit" );
			rs.next();
			return rs.getInt("PVID");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static long addTriageRecord( TriageRecord tr )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO TriageRecord (Temperature, TemperatureScale, SBP, DBP, PR, RR, Spo2, "
					+ "Weight, Height, LDT, VisitID, UserID, TriageID) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setFloat(1, tr.gettemperature());
			stat.setString(2, ""+tr.gettemperatureScale());
			stat.setInt(3, tr.getSBP());
			stat.setInt(4, tr.getDBP());
			stat.setInt(5, tr.getPR());
			stat.setInt(6, tr.getRR());
			stat.setFloat(7, tr.getspo2());
			stat.setFloat(8, tr.getweight());
			stat.setFloat(9, tr.getheight());
			if (tr.getLDT() != null)
				stat.setDate(10, tr.getLDT());
			else
				stat.setNull(10, Types.DATE);
			stat.setLong(11, tr.getvisitID());
			stat.setInt(12, tr.getuserID());
			long newID = generateID();
			stat.setLong(13, newID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
			return newID;
			/*ResultSet rs = executeSQL( "SELECT MAX(TriageID) AS TID FROM TriageRecord" );
			rs.next();
			return rs.getInt("TID");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static long addFemaleRecord( FemaleRecord fr )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO FemaleRecord (LMP, IsPregnant, Gestation, IsBreastFeeding, IsContraception, "
					+ "NumOfPregnancy, NumOfLiveBirth, NumOfMiscarriage, NumOfAbortion, NumOfStillBirth, "
					+ "OtherInfo, LastUpdate, PatientID, UserID, FemaleID) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			if (fr.getLMP() != null)
				stat.setDate(1, fr.getLMP());
			else
				stat.setNull(1, Types.DATE);
			stat.setBoolean(2, fr.isPregnant());
			stat.setInt(3, fr.getgestation());
			stat.setBoolean(4, fr.isBreastFeeding());
			stat.setBoolean(5, fr.isContraceptive());
			stat.setInt(6, fr.getnumOfPregnancy());
			stat.setInt(7, fr.getnumOfLiveBirth());
			stat.setInt(8, fr.getnumOfMiscarriage());
			stat.setInt(9, fr.getnumOfAbortion());
			stat.setInt(10, fr.getnumOfStillBirth());
			stat.setString(11, fr.getotherInfo());
			stat.setTimestamp(12, new Timestamp ((new java.util.Date()).getTime()));
			stat.setLong(13, fr.getpatientID());
			stat.setInt(14, fr.getuserID());
			long newID = generateID();
			stat.setLong(15, newID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
			return newID;
			/*ResultSet rs = executeSQL( "SELECT MAX(FemaleID) AS FID FROM FemaleRecord" );
			rs.next();
			return rs.getInt("FID");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static void addPatientHistory( PatientHistory ph )
	{
		if( ph == null )
			return;
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO PatientHistory (Description, EntryDateTime, StartDate, RemissionDate, IsUnderTreatment, HistoryType, Remarks, PatientID, VisitID, UserID, PatientHistoryID)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, ph.getdescription());
			stat.setTimestamp(2, new Timestamp ((new java.util.Date()).getTime()));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			if ( ph.getstartDateString() == null || ph.getstartDateString().trim().isEmpty() )
				stat.setNull(3, Types.DATE);
			else
			{				
				Date startDate = new Date( format.parse( ph.getstartDateString() ).getTime() );
				stat.setDate(3, startDate);
			}
			if ( ph.getremissionDateString() == null || ph.getremissionDateString().trim().isEmpty() )
				stat.setNull(4, Types.DATE);
			else
			{				
				Date remissionDate = new Date( format.parse( ph.getremissionDateString() ).getTime() );
				stat.setDate(4, remissionDate);
			}
			stat.setBoolean(5, ph.getisUnderTreatment());
			stat.setString(6, ph.gethistoryType());
			stat.setString(7, ph.getremarks());
			stat.setLong(8, ph.getpatientID());
			stat.setLong(9, ph.getvisitID());
			stat.setInt(10, ph.getuserID());
			stat.setLong(11, generateID());
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static long addPrescription( Prescription p )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO Prescription (Medicine, Dosage, UnitOfDosage, Route, Strength, Frequency, NumOfDays, Quantity, Remark, IsPrescripted, InventoryID, VisitID, DoctorID, PharmacistID, PrescriptionID)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, p.getmedicine());
			stat.setInt(2, p.getdosage());
			stat.setString(3, p.getunitOfDosage());
			stat.setString(4, p.getroute());
			stat.setString(5, p.getstrength());
			stat.setInt(6, p.getfrequency());
			stat.setInt(7, p.getnumOfDays());
			stat.setFloat(8, p.getquantity());
			stat.setString(9, p.getremark());
			stat.setBoolean(10, p.getisPrescripted());
			if( p.getinventoryID() == 0 )
				stat.setNull(11, Types.INTEGER);
			else
				stat.setInt(11, p.getinventoryID());
			stat.setLong(12, p.getvisitID());
			stat.setInt(13, p.getdoctorID());
			if( p.getpharmacistID() == 0 )
				stat.setNull(14, Types.INTEGER);
			else
				stat.setInt(14, p.getpharmacistID());
			long newID = generateID();
			stat.setLong(15, newID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();
			return newID;
			/*ResultSet rs = executeSQL( "SELECT MAX(PrescriptionID) AS PID FROM Prescription" );
			rs.next();
			return rs.getInt("PID");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static int addUser( User u )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO Role (TriageAccess, ConsultationAccess, PharmacyAccess, "
					+ "InventoryAccess, StatisticsAccess, SupervisorAccess, AdminAccess) "
					+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setBoolean(1, u.gettriageAccess());
			stat.setBoolean(2, u.getconsultationAccess());
			stat.setBoolean(3, u.getpharmacyAccess());
			stat.setBoolean(4, u.getinventoryAccess());
			stat.setBoolean(5, u.getstatisticsAccess());
			stat.setBoolean(6, u.getsupervisorAccess());
			stat.setBoolean(7, u.getadminAccess());
			stat.executeUpdate();
			backupSql(stat, "I");

			ResultSet rs = executeSQL( "SELECT MAX(RoleID) AS ID FROM Role" );
			rs.next();
			int roleID = rs.getInt("ID");
			rs.close();
			sql = "INSERT INTO User (Name, Password, RoleID) VALUES (?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setString(1, u.getname());
			stat.setString(2, u.getpassword());
			stat.setInt(3, roleID);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();

			rs = executeSQL( "SELECT MAX(UserID) AS ID FROM User" );
			rs.next();
			return rs.getInt("ID");
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static int addSlum( String slumName )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			/*ResultSet rs = executeSQL( "SELECT * FROM Slum WHERE Name='"+slumName+"'" );
			if( rs.next() )
			{
				updateSlum(slumName, slumName, true);
				return rs.getInt("SlumID");
			}*/
			PreparedStatement stat = conn.prepareStatement("INSERT INTO Slum (Name) VALUES (?)");
			stat.setString(1, slumName);
			stat.executeUpdate();
			backupSql(stat, "I");
			conn.commit();

			ResultSet rs = executeSQL( "SELECT MAX(SlumID) AS ID FROM Slum" );
			rs.next();
			return rs.getInt("ID");
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
			return -1;
		}
	}

	public static void addKeyword( String type, String keyword )
	{		
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "INSERT INTO Keyword (Type, Word) VALUES (?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, type);
			stat.setString(2, keyword);
			stat.executeUpdate();	
			backupSql(stat, "I");
			conn.commit();
		}
		catch( Exception e  )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updatePatient( Patient p )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Patient SET FirstName=?, MiddleName=?, LastName=?, Photo=?, DOB=?, AgeYear=?, AgeMonth=?, AgeDay=?, "
					+ "Sex=?, Status=?, TelNo=?, Address=?, LastUpdate=?, SlumID=?, UserID=? WHERE PatientID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, p.getfirstName());
			stat.setString(2, p.getmiddleName());
			stat.setString(3, p.getlastName());
			stat.setNull(4, Types.BLOB);
			stat.setDate(5, p.getDOB());
			stat.setInt(6, p.getageYear());
			stat.setInt(7, p.getageMonth());
			stat.setInt(8, p.getageDay());
			stat.setString(9, ""+p.getsex() );
			stat.setString(10, p.getstatus());
			stat.setString(11, p.gettelNo());
			stat.setString(12, p.getaddress());
			stat.setTimestamp(13, new Timestamp ((new java.util.Date()).getTime()));
			stat.setInt(14, p.getslumID());
			stat.setInt(15, p.getuserID());
			stat.setLong(16, p.getpatientID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e1 )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e1.printStackTrace();
		}
	}

	public static void updateTriageRecord( TriageRecord tr )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE TriageRecord SET Temperature=?, TemperatureScale=?, SBP=?, DBP=?, PR=?, RR=?, Spo2=?, "
					+ "Weight=?, Height=?, LDT=?, VisitID=?, UserID=? WHERE TriageID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setFloat(1, tr.gettemperature());
			stat.setString(2, ""+tr.gettemperatureScale());
			stat.setInt(3, tr.getSBP());
			stat.setInt(4, tr.getDBP());
			stat.setInt(5, tr.getPR());
			stat.setInt(6, tr.getRR());
			stat.setFloat(7, tr.getspo2());
			stat.setFloat(8, tr.getweight());
			stat.setFloat(9, tr.getheight());
			if (tr.getLDT() != null)
				stat.setDate(10, tr.getLDT());
			else
				stat.setNull(10, Types.DATE);
			stat.setLong(11, tr.getvisitID());
			stat.setInt(12, tr.getuserID());
			stat.setLong(13, tr.gettriageID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updateFemaleRecord( FemaleRecord fr )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE FemaleRecord SET LMP=?, IsPregnant=?, Gestation=?, IsBreastFeeding=?, IsContraception=?, "
					+ "NumOfPregnancy=?, NumOfLiveBirth=?, NumOfMiscarriage=?, NumOfAbortion=?, NumOfStillBirth=?, "
					+ "OtherInfo=?, LastUpdate=?, PatientID=?, UserID=? WHERE FemaleID = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			if (fr.getLMP() != null)
				stat.setDate(1, fr.getLMP());
			else
				stat.setNull(1, Types.DATE);
			stat.setBoolean(2, fr.isPregnant());
			stat.setInt(3, fr.getgestation());
			stat.setBoolean(4, fr.isBreastFeeding());
			stat.setBoolean(5, fr.isContraceptive());
			stat.setInt(6, fr.getnumOfPregnancy());
			stat.setInt(7, fr.getnumOfLiveBirth());
			stat.setInt(8, fr.getnumOfMiscarriage());
			stat.setInt(9, fr.getnumOfAbortion());
			stat.setInt(10, fr.getnumOfStillBirth());
			stat.setString(11, fr.getotherInfo());
			stat.setTimestamp(12, new Timestamp ((new java.util.Date()).getTime()));
			stat.setLong(13, fr.getpatientID());
			stat.setInt(14, fr.getuserID());
			stat.setLong(15, fr.getfemaleID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updatePatientVisit( long visitID, int userID, boolean isTriage, boolean isConsulted, boolean isFinished )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "";
			if( !isTriage && !isConsulted && !isFinished )
				sql = "UPDATE PatientVisit SET IsTriage=FALSE, IsConsulted=FALSE, IsFinished=FALSE, NurseID=? WHERE VisitID=?";
			else if( isTriage && !isConsulted && !isFinished )
				sql = "UPDATE PatientVisit SET IsTriage=TRUE, IsConsulted=FALSE, IsFinished=FALSE, NurseID=? WHERE VisitID=?";
			else if( isTriage && isConsulted && !isFinished )
				sql = "UPDATE PatientVisit SET IsTriage=TRUE, IsConsulted=TRUE, IsFinished=FALSE, DoctorID=? WHERE VisitID=?";
			else if( isTriage && isConsulted && isFinished )
				sql = "UPDATE PatientVisit SET IsTriage=TRUE, IsConsulted=TRUE, IsFinished=TRUE, PharmacistID=? WHERE VisitID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, userID);
			stat.setLong(2, visitID);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updatePatientHistory( PatientHistory ph )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE PatientHistory SET Description=?, EntryDateTime=?, StartDate=?, RemissionDate=?, IsUnderTreatment=?, "
					+ "HistoryType=?, Remarks=?, PatientID=?, VisitID=?, UserID=?, IsActive=? WHERE PatientHistoryID = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, ph.getdescription());
			stat.setTimestamp(2, new Timestamp ((new java.util.Date()).getTime()));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			if ( ph.getstartDateString() == null || ph.getstartDateString().trim().isEmpty() )
				stat.setNull(3, Types.DATE);
			else
			{				
				Date startDate = new Date( format.parse( ph.getstartDateString() ).getTime() );
				stat.setDate(3, startDate);
			}
			if ( ph.getremissionDateString() == null || ph.getremissionDateString().trim().isEmpty() )
				stat.setNull(4, Types.DATE);
			else
			{				
				Date remissionDate = new Date( format.parse( ph.getremissionDateString() ).getTime() );
				stat.setDate(4, remissionDate);
			}
			stat.setBoolean(5, ph.getisUnderTreatment());
			stat.setString(6, ph.gethistoryType());
			stat.setString(7, ph.getremarks());
			stat.setLong(8, ph.getpatientID());
			stat.setLong(9, ph.getvisitID());
			stat.setInt(10, ph.getuserID());
			stat.setBoolean(11, ph.getisActive());
			stat.setLong(12, ph.getpatientHistoryID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void removePatientHistory( long patientHistoryID )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE PatientHistory SET IsActive=? WHERE PatientHistoryID = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setBoolean(1, false);
			stat.setLong(2, patientHistoryID);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updatePrescription( Prescription p )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Prescription SET Medicine=?, Dosage=?, UnitOfDosage=?, Route=?, Strength=?, Frequency=?, NumOfDays=?, "
					+ "Quantity=?, Remark=?, IsPrescripted=?, VisitID=?, DoctorID=?, IsActive=? WHERE PrescriptionID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, p.getmedicine());
			stat.setInt(2, p.getdosage());
			stat.setString(3, p.getunitOfDosage());
			stat.setString(4, p.getroute());
			stat.setString(5, p.getstrength());
			stat.setInt(6, p.getfrequency());
			stat.setInt(7, p.getnumOfDays());
			stat.setFloat(8, p.getquantity());
			stat.setString(9, p.getremark());
			stat.setBoolean(10, p.getisPrescripted());
			stat.setLong(11, p.getvisitID());
			stat.setInt(12, p.getdoctorID());
			stat.setBoolean(13, p.getisActive());
			stat.setLong(14, p.getprescriptionID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updatePrescription( long prescriptionID, float quantity, String brand, String batchNo, boolean isPrescripted, int inventoryID, int pharmacistID )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Prescription SET Quantity=?, Brand=?, BatchNo=?, IsPrescripted=?, InventoryID=?, PharmacistID=? WHERE PrescriptionID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setFloat(1, quantity);
			stat.setString(2, brand);
			stat.setString(3, batchNo);
			stat.setBoolean(4, isPrescripted);
			stat.setInt(5, inventoryID);
			stat.setInt(6, pharmacistID);
			stat.setLong(7, prescriptionID);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static int updateInventory( String medicine, String brand, String batchNo, float quantity, boolean isActive )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Inventory I, Medicine M SET I.Quantity=I.Quantity-?, I.IsActive=? "
					+ "WHERE M.Name=? AND M.Brand=? AND M.MedicineID=I.MedicineID AND I.BatchNo=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setFloat(1, quantity);
			stat.setBoolean(2, isActive);
			stat.setString(3, medicine);
			stat.setString(4, brand);
			stat.setString(5, batchNo);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();

			ResultSet rs = executeSQL( "SELECT I.InventoryID FROM Inventory I, Medicine M "
					+ "WHERE M.Name='"+medicine+"' AND M.Brand='"+brand+"' AND M.MedicineID=I.MedicineID "
					+ "AND I.BatchNo='"+batchNo+"'" );
			rs.next();
			return rs.getInt("InventoryID");
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace(); return -1;
		}
	}

	public static void updateUserPassword( int userID, String encryptedPassword )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE User SET Password=? WHERE UserID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, encryptedPassword);
			stat.setInt(2, userID);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updateUser( User u )
	{
		try
		{
			boolean isActive = false;
			ResultSet rs = executeSQL("SELECT IsActive FROM User WHERE UserID="+u.getid());
			if(rs.next())
				isActive = rs.getBoolean("IsActive");

			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );

			String sql;
			if(isActive)
				sql = "UPDATE User U, Role R SET R.TriageAccess=?, R.ConsultationAccess=?, R.PharmacyAccess=?, "
					+ "R.InventoryAccess=?, R.StatisticsAccess=?, R.SupervisorAccess=?, R.AdminAccess=?, "
					+ "U.Name=?, U.Password=?, U.IsActive=?, U.LastActive=? WHERE U.RoleID=R.RoleID AND U.UserID=?";
			else
				sql = "UPDATE User U, Role R SET R.TriageAccess=?, R.ConsultationAccess=?, R.PharmacyAccess=?, "
					+ "R.InventoryAccess=?, R.StatisticsAccess=?, R.SupervisorAccess=?, R.AdminAccess=?, "
					+ "U.Name=?, U.Password=?, U.IsActive=? WHERE U.RoleID=R.RoleID AND U.UserID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setBoolean(1, u.gettriageAccess());
			stat.setBoolean(2, u.getconsultationAccess());
			stat.setBoolean(3, u.getpharmacyAccess());
			stat.setBoolean(4, u.getinventoryAccess());
			stat.setBoolean(5, u.getstatisticsAccess());
			stat.setBoolean(6, u.getsupervisorAccess());
			stat.setBoolean(7, u.getadminAccess());
			stat.setString(8, u.getname());
			stat.setString(9, u.getpassword());
			stat.setBoolean(10, u.getisActive());
			if(isActive)
			{
				stat.setTimestamp(11, new Timestamp ((new java.util.Date()).getTime()));
				stat.setInt(12, u.getid());
			}
			else
				stat.setInt(11, u.getid());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updateSlum( int slumID, String newSlumName, boolean isActive )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Slum SET Name=?, IsActive=? WHERE SlumID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, newSlumName);
			stat.setBoolean(2, isActive);
			stat.setInt(3, slumID);
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void removeKeyword( String type, String keyword )
	{		
		try
		{
			//Class.forName( "com.mysql.jdbc.Driver" );
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			Statement stat = conn.createStatement();
			String backupString = "DELETE FROM Keyword WHERE Type='"+type+"' AND Word='"+keyword+"'";
			stat.executeUpdate(backupString);
			conn.commit();
			backupSql(backupString, "D");
			/*String backupString = "DELETE FROM Keyword WHERE Type='"+type+"' AND Word='"+keyword+"'";
			backupString = backupString.replace("\"", "!@#");
			backupString = backupString.replace("\\'", "#@!");
			backupString = backupString.replace("'", "\"");
			stat.executeUpdate("INSERT INTO BackupSQL (Script, Type) VALUES ('"+backupString+"', 'D')");*/
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static void updateComment( Comment c )
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			//Connection conn = DriverManager.getConnection( url, accountName, password );
			String sql = "UPDATE Comment SET Response=?, ResponseUserID=?, ResponseDateTime=?, IsResponded=? WHERE CommentID=?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, c.getresponse());
			stat.setInt(2, c.getresponseUserID());
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
			if ( c.getresponseDateTimeString() == null || c.getresponseDateTimeString().trim().isEmpty() )
				stat.setNull(3, Types.DATE);
			else
			{				
				Date responseDate = new Date( format.parse( c.getresponseDateTimeString() ).getTime() );
				stat.setDate(3, responseDate);
			}
			stat.setBoolean(4, c.getisResponded());
			stat.setInt(5, c.getcommentID());
			stat.executeUpdate();
			backupSql(stat, "U");
			conn.commit();
		}
		catch( Exception e )
		{
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	public static ArrayList<Patient> getPatient( int slumID, String firstName, String lastName )
	{
		ArrayList<Patient> patientList = new ArrayList<Patient>();
		try
		{
			ResultSet rs = null;
			if( firstName.isEmpty() )
				rs = executeSQL( "SELECT PatientID FROM Patient WHERE SlumID = " + slumID + " AND LastName = '" + lastName + "'" );
			else if( lastName.isEmpty() )
				rs = executeSQL( "SELECT PatientID FROM Patient WHERE SlumID = " + slumID + " AND FirstName = '" + firstName + "'" );
			else
				rs = executeSQL( "SELECT PatientID FROM Patient WHERE SlumID = " + slumID + " AND FirstName = '" + firstName + "' AND LastName = '" + lastName + "'" );
			while( rs.next() )
				patientList.add( getPatient( rs.getLong("PatientID") ) );
			return patientList;
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return null;
	}

	public static Patient getPatient( long patientID )
	{
		Patient p = new Patient();
		try
		{
			ResultSet rs = executeSQL( "SELECT * FROM Patient WHERE PatientID = " + patientID );
			while( rs.next() )
			{
				p.setpatientID(patientID);
				p.setfirstName(rs.getString("FirstName"));
				p.setmiddleName(rs.getString("MiddleName"));
				p.setlastName(rs.getString("LastName"));
				p.setslumID(rs.getInt("SlumID"));
				//p.setphoto(rs.getBlob("Photo"));
				//TODO: Photo
				/*try
				{
					File image = new File("New_Patient_Photo.jpg");
					FileOutputStream fos = new FileOutputStream(image);
					byte[] buffer = new byte[1];
					InputStream is = rs.getBinaryStream("Photo");
					while (is.read(buffer) > 0)
						fos.write(buffer);
					fos.close();
					p.setphoto(image);
				}
				catch( IOException e )
				{
					e.printStackTrace();
				}*/
				p.setsex(rs.getString("Sex").charAt(0));
				p.setDOB(rs.getDate("DOB"));
				p.setageYear(rs.getInt("AgeYear"));
				p.setageMonth(rs.getInt("AgeMonth"));
				p.setageDay(rs.getInt("AgeDay"));
				p.setstatus(rs.getString("Status"));
				p.settelNo(rs.getString("TelNo"));
				p.setaddress(rs.getString("Address"));
				p.setlastUpdate(rs.getDate("LastUpdate"));
				p.setuserID(rs.getInt("UserID"));
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return p;
	}

	public static ArrayList<String> getPatientFirstNameList( int slumID )
	{
		ArrayList<String> returnList = new ArrayList<String>();
		try
		{
			ResultSet rs = executeSQL( "SELECT DISTINCT FirstName FROM Patient WHERE SlumID = " + slumID );
			while( rs.next() )
				returnList.add( rs.getString("FirstName") );
			return returnList;
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return null;
	}

	public static ArrayList<String> getPatientLastNameList( int slumID )
	{
		ArrayList<String> returnList = new ArrayList<String>();
		try
		{
			ResultSet rs = executeSQL( "SELECT DISTINCT LastName FROM Patient WHERE SlumID = " + slumID );
			while( rs.next() )
				returnList.add( rs.getString("LastName") );
			return returnList;
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return null;
	}

	//PatientVisit without any names
	public static PatientVisit getPatientVisit( long visitID )
	{
		PatientVisit pv = new PatientVisit();
		try
		{
			ResultSet rs = executeSQL( "SELECT *, DATE_FORMAT(VisitDateTime, '%d-%b-%Y %T') AS vdt FROM PatientVisit WHERE VisitID = " + visitID );
			while( rs.next() )
			{
				pv = new PatientVisit( rs.getLong("VisitID"), rs.getString("vdt"), rs.getInt("TagNumber"), rs.getLong("PatientID"), rs.getBoolean("IsTriage"), rs.getBoolean("IsConsulted"), rs.getBoolean("IsFinished"),  "", "", "", "", rs.getInt("NurseID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") );
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return pv;
	}

	public static int getTagNumber( long visitID )
	{
		int t = -1;
		try
		{
			ResultSet rs = executeSQL( "SELECT TagNumber FROM PatientVisit WHERE VisitID = " + visitID );
			if( rs.next() )
				t = rs.getInt("TagNumber");
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return t;
	}

	public static TriageRecord getTriageRecord( long visitID )
	{
		TriageRecord tr = new TriageRecord();
		try
		{
			ResultSet rs = executeSQL( "SELECT * FROM TriageRecord WHERE VisitID = " + visitID );
			while( rs.next() )
			{
				tr.settriageID(rs.getLong("TriageID"));
				tr.setvisitID(visitID);
				tr.settemperature(rs.getFloat("Temperature"));
				tr.settemperatureScale(rs.getString("TemperatureScale").charAt(0));
				tr.setSBP(rs.getInt("SBP"));
				tr.setDBP(rs.getInt("DBP"));
				tr.setPR(rs.getInt("PR"));
				tr.setRR(rs.getInt("RR"));
				tr.setSpo2(rs.getFloat("Spo2"));
				tr.setweight(rs.getFloat("Weight"));
				tr.setheight(rs.getFloat("Height"));
				tr.setLDT(rs.getDate("LDT"));
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return tr;
	}

	public static FemaleRecord getFemaleRecord( long patientID )
	{
		FemaleRecord fr = new FemaleRecord();
		try
		{
			ResultSet rs = executeSQL( "SELECT * FROM FemaleRecord WHERE PatientID = " + patientID );
			while( rs.next() )
			{
				fr.setfemaleID(rs.getLong("FemaleID"));
				fr.setLMP(rs.getDate("LMP"));
				fr.setisBreastFeeding(rs.getBoolean("IsBreastFeeding"));
				fr.setisContraceptive(rs.getBoolean("IsContraception"));
				fr.setnumOfPregnancy(rs.getInt("NumOfPregnancy"));
				fr.setnumOfLiveBirth(rs.getInt("NumOfLiveBirth"));
				fr.setisPregnant(rs.getBoolean("IsPregnant"));
				fr.setgestation(rs.getInt("Gestation"));
				fr.setnumOfMiscarriage(rs.getInt("NumOfMiscarriage"));
				fr.setnumOfAbortion(rs.getInt("NumOfAbortion"));
				fr.setnumOfStillBirth(rs.getInt("NumOfStillBirth"));
				fr.setlastUpdate(rs.getDate("LastUpdate"));
				fr.setpatientID(patientID);
				fr.setuserID(rs.getInt("UserID"));
				fr.setotherInfo(rs.getString("OtherInfo"));
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return fr;
	}

    /**
     * TODO can I use this to generate patient record?
     *
     * @param patientID
     * @param historyType
     * @return
     */
    public static ArrayList<PatientHistory> getPatientHistoryList( long patientID, String... historyType )
	{
		ArrayList<PatientHistory> patientHistoryList = new ArrayList<>();
		String scrSql = null;
		String sql = "SELECT *, DATE_FORMAT(EntryDateTime, '%d/%m/%Y') AS edt, DATE_FORMAT(StartDate, '%d/%m/%Y') AS ot, "
				+ "DATE_FORMAT(RemissionDate, '%d/%m/%Y') AS rdt FROM PatientHistory WHERE IsActive = TRUE AND PatientID = " + patientID;
		int typeCount = 0;
		if( historyType.length == 1 )
		{
			if( historyType[0].equals(PatientHistory.TYPE_SCR) )
				sql += " AND HistoryType = '" + PatientHistory.TYPE_SCR + "' AND IsUnderTreatment = 1";
			else
				sql += " AND HistoryType = '" + historyType[0] + "'";
		}
		else
		{
			for( int i = 0; i < historyType.length; i++ )
			{
				if( historyType.equals(PatientHistory.TYPE_SCR) )
					scrSql = "SELECT *, DATE_FORMAT(EntryDateTime, '%d/%m/%Y') AS edt, DATE_FORMAT(StartDate, '%d/%m/%Y') AS ot, "
							+ "DATE_FORMAT(RemissionDate, '%d/%m/%Y') AS rdt FROM PatientHistory WHERE IsActive = TRUE AND PatientID = " + patientID
							+ " AND HistoryType = '" + PatientHistory.TYPE_SCR + "' AND IsUnderTreatment = 1";
				else
				{
					if( typeCount == 0 )
						sql += " AND (HistoryType = '" + historyType[i] + "'";
					else
						sql += " OR HistoryType = '" + historyType[i] + "'";
					typeCount++;
				}
			}
			sql += ")";
		}
		sql += " ORDER BY StartDate DESC";//HistoryType ASC
		try
		{
			ResultSet rs = executeSQL( sql );
			while( rs.next() )
			{
				PatientHistory mh = new PatientHistory( rs.getLong("PatientHistoryID"), rs.getString("Description"), rs.getString("edt"), rs.getString("ot"), rs.getString("rdt"), rs.getBoolean("IsUnderTreatment"), rs.getString("HistoryType"), rs.getString("Remarks"), rs.getLong("PatientID"), rs.getLong("VisitID"), rs.getInt("UserID"), rs.getBoolean("IsActive") );
				patientHistoryList.add( mh );
			}
			if( scrSql != null )
			{
				rs = executeSQL( sql );
				while( rs.next() )
				{
					PatientHistory mh = new PatientHistory( rs.getLong("PatientHistoryID"), rs.getString("Description"), rs.getString("edt"), rs.getString("ot"), rs.getString("rdt"), rs.getBoolean("IsUnderTreatment"), rs.getString("HistoryType"), rs.getString("Remarks"), rs.getLong("PatientID"), rs.getLong("VisitID"), rs.getInt("UserID"), rs.getBoolean("IsActive") );
					patientHistoryList.add( mh );
				}
				rs.close();
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return patientHistoryList;
    }

    /**
     * TODO can I use this to generate patient record?
     * @param patientID
     * @param visitID
     * @param historyType
     * @return
     */
    public static ArrayList<PatientHistory> getPatientHistoryList( long patientID, long visitID, String... historyType )
	{
		ArrayList<PatientHistory> patientHistoryList = new ArrayList<>();
		String scrSql = null;
		String sql = "SELECT *, DATE_FORMAT(EntryDateTime, '%d/%m/%Y') AS edt, DATE_FORMAT(StartDate, '%d/%m/%Y') AS ot, "
				+ "DATE_FORMAT(RemissionDate, '%d/%m/%Y') AS rdt FROM PatientHistory "
				+ "WHERE IsActive = TRUE AND PatientID = " + patientID + " AND VisitID="+visitID;
		int typeCount = 0;
		if( historyType.length == 1 )
		{
			if( historyType[0].equals(PatientHistory.TYPE_SCR) )
				sql += " AND HistoryType = '" + PatientHistory.TYPE_SCR + "' AND IsUnderTreatment = 1";
			else
				sql += " AND HistoryType = '" + historyType[0] + "'";
		}
		else
		{
			for( int i = 0; i < historyType.length; i++ )
			{
				if( historyType.equals(PatientHistory.TYPE_SCR) )
					scrSql = "SELECT *, DATE_FORMAT(EntryDateTime, '%d/%m/%Y') AS edt, DATE_FORMAT(StartDate, '%d/%m/%Y') AS ot, "
							+ "DATE_FORMAT(RemissionDate, '%d/%m/%Y') AS rdt FROM PatientHistory WHERE IsActive = TRUE AND PatientID = " + patientID
							+ " AND HistoryType = '" + PatientHistory.TYPE_SCR + "' AND IsUnderTreatment = 1 AND VisitID"+visitID;
				else
				{
					if( typeCount == 0 )
						sql += " AND (HistoryType = '" + historyType[i] + "'";
					else
						sql += " OR HistoryType = '" + historyType[i] + "'";
					typeCount++;
				}
			}
			sql += ")";
		}
		sql += " ORDER BY StartDate DESC";//HistoryType ASC
		try
		{
			ResultSet rs = executeSQL( sql );
			while( rs.next() )
			{
				PatientHistory mh = new PatientHistory( rs.getLong("PatientHistoryID"), rs.getString("Description"), rs.getString("edt"), rs.getString("ot"), rs.getString("rdt"), rs.getBoolean("IsUnderTreatment"), rs.getString("HistoryType"), rs.getString("Remarks"), rs.getLong("PatientID"), rs.getLong("VisitID"), rs.getInt("UserID"), rs.getBoolean("IsActive") );
				patientHistoryList.add( mh );
			}
			if( scrSql != null )
			{
				rs = executeSQL( sql );
				while( rs.next() )
				{
					PatientHistory mh = new PatientHistory( rs.getLong("PatientHistoryID"), rs.getString("Description"), rs.getString("edt"), rs.getString("ot"), rs.getString("rdt"), rs.getBoolean("IsUnderTreatment"), rs.getString("HistoryType"), rs.getString("Remarks"), rs.getLong("PatientID"), rs.getLong("VisitID"), rs.getInt("UserID"), rs.getBoolean("IsActive") );
					patientHistoryList.add( mh );
				}
				rs.close();
			}
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return patientHistoryList;
	}

	public static ArrayList<PatientVisit> getPatientQueue( int slumID, boolean isTriage, boolean isConsulted, boolean isFinished )
	{
		ArrayList<PatientVisit> patientVisitList = new ArrayList<>();
		String sql = "";
		if( isTriage && !isConsulted && !isFinished )
			sql = "SELECT PV.VisitID, DATE_FORMAT(PV.VisitDateTime, '%T') AS vdt, "
				+ "PV.TagNumber, PV.PatientID, P.FirstName, P.MiddleName, P.LastName, "
				+ "U1.Name AS NurseName, PV.NurseID, PV.DoctorID, PV.PharmacistID, "
				+ "PV.IsTriage AS IsTriage, PV.IsConsulted AS IsConsulted, PV.IsFinished AS IsFinished "
				+ "FROM PatientVisit PV, Patient P, User U1 "
				+ "WHERE P.SlumID = " + slumID + " AND DATE(VisitDateTime) = DATE(NOW()) AND PV.PatientID = P.PatientID "
				+ "AND PV.NurseID = U1.UserID "
				+ "AND PV.IsTriage = TRUE AND PV.IsConsulted = FALSE AND IsFinished = FALSE";
		else if( isTriage && isConsulted && !isFinished )
			sql = "SELECT PV.VisitID, DATE_FORMAT(PV.VisitDateTime, '%T') AS vdt, "
				+ "PV.TagNumber, PV.PatientID, P.FirstName, P.MiddleName, P.LastName, "
				+ "U1.Name AS NurseName, U2.Name as DoctorName, PV.NurseID, PV.DoctorID, PV.PharmacistID, "
				+ "PV.IsTriage AS IsTriage, PV.IsConsulted AS IsConsulted, PV.IsFinished AS IsFinished "
				+ "FROM PatientVisit PV, Patient P, User U1, User U2 "
				+ "WHERE P.SlumID = " + slumID + " AND DATE(VisitDateTime) = DATE(NOW()) AND PV.PatientID = P.PatientID "
				+ "AND PV.NurseID = U1.UserID AND PV.DoctorID = U2.UserID "
				+ "AND PV.IsTriage = TRUE AND PV.IsConsulted = TRUE AND IsFinished = FALSE";
		else if( isTriage && isConsulted && isFinished )
			sql = "SELECT PV.VisitID, DATE_FORMAT(PV.VisitDateTime, '%T') AS vdt, "
				+ "PV.TagNumber, PV.PatientID, P.FirstName, P.MiddleName, P.LastName, "
				+ "U1.Name AS NurseName, U2.Name as DoctorName, U3.Name as PharmacistName, PV.NurseID, PV.DoctorID, PV.PharmacistID, "
				+ "PV.IsTriage AS IsTriage, PV.IsConsulted AS IsConsulted, PV.IsFinished AS IsFinished "
				+ "FROM PatientVisit PV, Patient P, User U1, User U2, User U3 "
				+ "WHERE P.SlumID = " + slumID + " AND DATE(VisitDateTime) = DATE(NOW()) AND PV.PatientID = P.PatientID "
				+ "AND PV.NurseID = U1.UserID AND PV.DoctorID = U2.UserID AND PV.PharmacistID = U3.UserID "
				+ "AND PV.IsTriage = TRUE AND PV.IsConsulted = TRUE AND IsFinished = TRUE";
		try
		{
			ResultSet rs = executeSQL( sql );
			while( rs.next() )
			{
				String first = rs.getString("FirstName");
				String patientName = rs.getString("LastName");
				if( !first.isEmpty() )
				{
					if(patientName.isEmpty())
						patientName += first;
					else
						patientName += (" " + first);
				}
				PatientVisit pv = null;
				if( isTriage && !isConsulted && !isFinished )
					pv = new PatientVisit( rs.getLong("VisitID"), rs.getString("vdt"), rs.getInt("TagNumber"), rs.getLong("PatientID"), rs.getBoolean("IsTriage"), rs.getBoolean("IsConsulted"), rs.getBoolean("IsFinished"), patientName, rs.getString("NurseName"), "", "", rs.getInt("NurseID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") );
				else if( isTriage && isConsulted && !isFinished )
					pv = new PatientVisit( rs.getLong("VisitID"), rs.getString("vdt"), rs.getInt("TagNumber"), rs.getLong("PatientID"), rs.getBoolean("IsTriage"), rs.getBoolean("IsConsulted"), rs.getBoolean("IsFinished"), patientName, rs.getString("NurseName"), rs.getString("DoctorName"), "", rs.getInt("NurseID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") );
				else if( isTriage && isConsulted && isFinished )
					pv = new PatientVisit( rs.getLong("VisitID"), rs.getString("vdt"), rs.getInt("TagNumber"), rs.getLong("PatientID"), rs.getBoolean("IsTriage"), rs.getBoolean("IsConsulted"), rs.getBoolean("IsFinished"), patientName, rs.getString("NurseName"), rs.getString("DoctorName"), rs.getString("PharmacistName"), rs.getInt("NurseID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") );
				patientVisitList.add( pv );
			}
			rs.close();
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return patientVisitList;
    }

    /**
     * TODO can I use this to generate patient record?
     * @param patientID
     * @param isToday
     * @return
     */
    public static ArrayList<PatientVisit> getPatientVisitList( long patientID, boolean isToday )
	{
		ArrayList<PatientVisit> patientVisitList = new ArrayList<>();
		String sql = "SELECT PV.VisitID, DATE_FORMAT(PV.VisitDateTime, '%d-%b-%Y') AS vdt, "
				+ "PV.TagNumber, PV.PatientID, P.FirstName, P.MiddleName, P.LastName, "
				+ "U1.Name AS NurseName, U2.Name as DoctorName, U3.Name as PharmacistName, PV.NurseID, PV.DoctorID, PV.PharmacistID, "
				+ "PV.IsTriage AS IsTriage, PV.IsConsulted AS IsConsulted, PV.IsFinished AS IsFinished "
				+ "FROM PatientVisit PV, Patient P, User U1, User U2, User U3 "
				+ "WHERE P.PatientID = " + patientID + " AND PV.PatientID = P.PatientID "
				+ "AND PV.NurseID = U1.UserID AND PV.DoctorID = U2.UserID AND PV.PharmacistID = U3.UserID "
				+ "AND PV.IsTriage = TRUE AND PV.IsConsulted = TRUE AND IsFinished = TRUE";
		if(isToday)
			sql += " AND DATE(VisitDateTime) = DATE(NOW())";
		sql += " ORDER BY PV.VisitDateTime DESC";
		try
		{
			ResultSet rs = executeSQL( sql );
			while( rs.next() )
			{
				String first = rs.getString("FirstName");
				String patientName = rs.getString("LastName");
				if( !first.isEmpty() )
				{
					if(patientName.isEmpty())
						patientName += first;
					else
						patientName += (" " + first);
				}
				patientVisitList.add( new PatientVisit( rs.getLong("VisitID"), rs.getString("vdt"), rs.getInt("TagNumber"), rs.getLong("PatientID"), rs.getBoolean("IsTriage"), rs.getBoolean("IsConsulted"), rs.getBoolean("IsFinished"), patientName, rs.getString("NurseName"), rs.getString("DoctorName"), rs.getString("PharmacistName"), rs.getInt("NurseID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") ) );
			}
			rs.close();
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return patientVisitList;
	}

	public static ArrayList<Prescription> getPrescriptionList( long visitID, boolean isPrescripted )
	{
		ArrayList<Prescription> prescriptionList = new ArrayList<>();
		try
		{
			ResultSet rs = executeSQL( "SELECT PR.PrescriptionID, PR.Medicine, PR.Dosage, PR.UnitOfDosage, PR.Route, PR.Frequency, PR.Strength, "
					+ "PR.NumOfDays, PR.Quantity, PR.Brand, PR.BatchNo, PR.Remark, PR.IsPrescripted, PR.InventoryID, "
					+ "PR.VisitID, PR.DoctorID, PR.PharmacistID, P.FirstName, U.Name AS UserName "
					+ "FROM Prescription PR, PatientVisit PV, Patient P, User U "
					+ "WHERE PR.VisitID = PV.VisitID AND PV.PatientID = P.PatientID "
					+ "AND PR.DoctorID = U.UserID AND PR.IsPrescripted = " + isPrescripted
					+ " AND PR.IsActive = TRUE AND PR.VisitID = " + visitID );
			//"DATE(visitDateTime) = DATE(NOW()) AND "
			while( rs.next() )
			{
				Prescription p = new Prescription( rs.getLong("PrescriptionID"), rs.getString("Medicine"), rs.getInt("Dosage")
					, rs.getString("UnitOfDosage"), rs.getString("Route"), rs.getString("Strength"), rs.getInt("Frequency"), rs.getInt("NumOfDays")
					, rs.getFloat("Quantity"), rs.getString("Brand"), rs.getString("BatchNo"), rs.getString("Remark"), rs.getBoolean("IsPrescripted")
					, rs.getInt("InventoryID"), rs.getLong("VisitID"), rs.getInt("DoctorID"), rs.getInt("PharmacistID") );
				p.setunitOfQuantity(DatabaseController.getMedicineUnit(p.getmedicine()));
				prescriptionList.add( p );
			}
			rs.close();
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return prescriptionList;
	}

	public static ArrayList<String> getMedicineList()
	{
		ArrayList<String> medicineList = new ArrayList<>();
		try
		{
			ResultSet rs = executeSQL( "SELECT DISTINCT Name FROM Medicine" );
			while( rs.next() )
				medicineList.add(rs.getString("Name"));
			rs.close();
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return medicineList;
	}

	public static String getMedicineType( String medicine )
	{
		String value = "";
		try
		{
			ResultSet rs = executeSQL( "SELECT Type FROM Medicine M WHERE Name='"+medicine+"'" );
			if( rs.next() )
				value = rs.getString("Type");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

	public static String getMedicineUnit( String medicine )
	{
		String value = "";
		try
		{
			ResultSet rs = executeSQL( "SELECT UnitOfQuantity FROM Medicine M WHERE Name='"+medicine+"'" );
			if( rs.next() )
				value = rs.getString("UnitOfQuantity");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

	public static String getMedicineStrength( String medicine )
	{
		String value = "";
		try
		{
			ResultSet rs = executeSQL( "SELECT Strength FROM Medicine M WHERE Name='"+medicine+"'" );
			if( rs.next() )
				value = rs.getString("Strength");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

	public static float getMedicineQuantity( String medicine, String strength )
	{
		int value = 0;
		try
		{
			String sql;
			if(strength.isEmpty())
				sql = "SELECT I.Quantity FROM Inventory I, Medicine M "
					+ "WHERE M.Name = '" + medicine + "' AND M.MedicineID = I.MedicineID AND I.IsActive = TRUE";
			else
				sql = "SELECT I.Quantity FROM Inventory I, Medicine M "
				+ "WHERE M.Name = '" + medicine + "' AND M.MedicineID = I.MedicineID AND M.Strength = '" + strength + "' AND I.IsActive = TRUE";
			ResultSet rs = executeSQL(sql);
			while( rs.next() )
				value += rs.getFloat("Quantity");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

	public static float getMedicineQuantity( String medicine, String strength, String brand )
	{
		int value = 0;
		try
		{
			ResultSet rs = executeSQL( "SELECT I.Quantity FROM Inventory I, Medicine M "
					+ "WHERE M.Name = '" + medicine + "' AND M.Brand = '" + brand + "' AND M.MedicineID = I.MedicineID AND I.IsActive = TRUE" );
			while( rs.next() )
				value += rs.getFloat("Quantity");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

	public static float getMedicineQuantity( String medicine, String strength, String brand, String batchNo )
	{
		int value = 0;
		try
		{
			ResultSet rs = executeSQL( "SELECT I.Quantity FROM Inventory I, Medicine M "
					+ "WHERE M.Name = '" + medicine + "' AND M.Brand = '" + brand + "' "
					+ "AND I.BatchNo = '" + batchNo + "' AND M.MedicineID = I.MedicineID AND I.IsActive = TRUE" );
			while( rs.next() )
				value += rs.getFloat("Quantity");
			rs.close();
			return value;
		}
		catch( SQLException e ) { e.printStackTrace(); return value; }
	}

//	public static ArrayList<String> getAllBrandList()
//	{
//		ArrayList<String> brandList = new ArrayList<>();
//		ResultSet rs = executeSQL("SELECT DISTINCT Brand FROM Medicine");
//		try
//		{
//			while (rs.next())
//				brandList.add(rs.getString("Brand"));
//		}
//		catch (SQLException e) { e.printStackTrace(); }
//		return brandList;
//	}

	public static ArrayList<String> getStrengthList( String medicine )
	{
		ArrayList<String> strengthList = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT DISTINCT Strength FROM Medicine WHERE Name='" + medicine + "'");
		try
		{
			while (rs.next())
				strengthList.add(rs.getString("Strength"));
		} catch (SQLException e) { e.printStackTrace(); }
		return strengthList;
	}

	public static ArrayList<String> getBrandList( String medicine, String strength )
	{
		ArrayList<String> brandList = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT DISTINCT Brand FROM Medicine WHERE Name='" + medicine + "' AND Strength='" + strength + "'");
		try
		{
			while (rs.next())
				brandList.add(rs.getString("Brand"));
		} catch (SQLException e) { e.printStackTrace(); }
		return brandList;
	}

	public static String getBatchNumber( String medicine, String strength, String brand )
	{
		ResultSet rs = executeSQL("SELECT I1.BatchNo FROM Medicine M1, Inventory I1 "
				+ "WHERE M1.Name='" + medicine + "' AND M1.Brand='" + brand + "' AND M1.MedicineID=I1.MedicineID "
				+ "AND M1.Strength='" + strength + "' AND I1.IsActive = TRUE AND I1.ExpiryDate = "
				+ "(SELECT MIN(ExpiryDate) FROM Medicine M2, Inventory I2 "
				+ "WHERE M2.Name='" + medicine + "' AND M2.Brand='" + brand + "' AND M2.MedicineID=I2.MedicineID "
				+ "AND M2.Strength='" + strength + "' AND I2.IsActive = TRUE)");
		try
		{
			while (rs.next())
				return rs.getString("BatchNo");
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ArrayList<String> getBatchNumberList( String medicine, String brand )
	{
		ArrayList<String> batchNumberList = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT I.BatchNo FROM Medicine M, Inventory I "
				+ "WHERE M.Name='" + medicine + "' AND M.Brand='" + brand + "' AND M.MedicineID=I.MedicineID AND I.IsActive = TRUE");
		try
		{
			while (rs.next())
				batchNumberList.add(rs.getString("BatchNo"));
		} catch (SQLException e) { e.printStackTrace(); }
		return batchNumberList;
	}

	public static ArrayList<String> getKeywordList( String keywordType )
	{
		ArrayList<String> keywordList = new ArrayList<>();
		ResultSet rs = executeSQL( "SELECT * FROM Keyword WHERE Type ='" + keywordType + "' ORDER BY Word" );
		try
		{
			while( rs.next() )
				keywordList.add( rs.getString("Word") );
		}
		catch( SQLException e ) { e.printStackTrace(); }
		return keywordList; 
	}

	public static ArrayList<ChartData<String, Number>> getChartData( String dataName, long patientID )
	{
		ArrayList<ChartData<String, Number>> chartData = new ArrayList<>();
		ResultSet rs;
		if( dataName.equals("Temperature") )
			rs = executeSQL("SELECT TR."+dataName+" AS Data, DATE_FORMAT(PV.VisitDateTime, '%d/%m/%Y') AS Time, TR.TemperatureScale AS Scale FROM TriageRecord TR, PatientVisit PV WHERE TR.VisitID = PV.VisitID AND PV.PatientID = " + patientID);
		else
			rs = executeSQL("SELECT TR."+dataName+" AS Data, DATE_FORMAT(PV.VisitDateTime, '%d/%m/%Y') AS Time FROM TriageRecord TR, PatientVisit PV WHERE TR.VisitID = PV.VisitID AND PV.PatientID = " + patientID);
			try {
				while(rs.next())
				{
					double data = rs.getFloat("Data");
					if( dataName.equals("Temperature") && rs.getString("Scale").equals("F") )
						data = (data-32.0) * 5 / 9;
                    chartData.add(new ChartData<String, Number>(rs.getString("Time"), new Double(data)));
                }
			} catch (SQLException e) { e.printStackTrace(); }
		return chartData;
	}

	public static User getUser( String loginID )
	{
		User u = null;
		ResultSet rs = executeSQL("SELECT * FROM User U, Role R WHERE U.IsActive = TRUE AND U.RoleID = R.RoleID AND U.Name='"+loginID+"'");
		try
		{
			if(rs.next())
			{
				u = new User(rs.getString("Name"), rs.getInt("RoleID"), rs.getBoolean("TriageAccess"), rs.getBoolean("ConsultationAccess"), rs.getBoolean("PharmacyAccess"), rs.getBoolean("InventoryAccess"), rs.getBoolean("StatisticsAccess"), rs.getBoolean("SupervisorAccess"), rs.getBoolean("AdminAccess"), rs.getBoolean("IsActive"));
				u.setid(rs.getInt("UserID"));
				u.setpassword(rs.getString("Password"));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return u;
	}

	public static long getUserLogIDIfUserLoggedIn( int userID, String macAddress )
	{
		ResultSet rs = executeSQL("SELECT UserLogID, MAC FROM UserLog WHERE LogoutTime IS NULL AND UserID="+userID);
		try
		{
			if(rs.next())
			{
				if(macAddress.equals(rs.getString("MAC")))
					return rs.getLong("UserLogID");
				return -1L;
			}
			else
				return -1L;
		} catch (SQLException e) { e.printStackTrace(); return -1L; }
	}

	public static long userLogin( int userID )
	{
		try
		{
			Statement stat = conn.createStatement();
			long newID = generateID();

			InetAddress ip;
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++)
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

			String backupString = "INSERT INTO UserLog (UserLogID, UserID, MAC) VALUES ("+newID+", "+userID+", '"+sb.toString()+"')";
			stat.executeUpdate(backupString);
			conn.commit();
			backupSql(backupString, "I");
			/*backupString = backupString.replace("\"", "!@#");
			backupString = backupString.replace("\\'", "#@!");
			backupString = backupString.replace("'", "\"");
			stat.executeUpdate("INSERT INTO BackupSQL (Script, Type) VALUES ('"+backupString+"', 'I')");*/
			return newID;
			/*ResultSet rs = executeSQL( "SELECT MAX(UserLogID) AS ID FROM UserLog" );
			rs.next();
			return rs.getInt("ID");*/
		} catch (SQLException | SocketException | UnknownHostException e) { e.printStackTrace(); return -1; }
	}

	public static boolean userLogout( long userLogID )
	{
		try
		{
			Statement stat = conn.createStatement();
			String backupString = "UPDATE UserLog SET LogoutTime=NOW() WHERE UserLogID="+userLogID;
			stat.executeUpdate(backupString);
			conn.commit();
			backupSql(backupString, "I");
			/*backupString = backupString.replace("\"", "!@#");
			backupString = backupString.replace("\\'", "#@!");
			backupString = backupString.replace("'", "\"");
			stat.executeUpdate("INSERT INTO BackupSQL (Script, Type) VALUES ('"+backupString+"', 'I')");*/
			
			return true;
		} catch (SQLException e) { e.printStackTrace(); return false; }
	}

	public static ArrayList<User> getUserList()
	{
		ArrayList<User> userList = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT * FROM User U, Role R WHERE U.RoleID = R.RoleID");
		try
		{
			while(rs.next())
			{
				User u = new User(rs.getString("Name"), rs.getInt("RoleID"), rs.getBoolean("TriageAccess"), rs.getBoolean("ConsultationAccess"), rs.getBoolean("PharmacyAccess"), rs.getBoolean("InventoryAccess"), rs.getBoolean("StatisticsAccess"), rs.getBoolean("SupervisorAccess"), rs.getBoolean("AdminAccess"), rs.getBoolean("IsActive"));
				u.setid(rs.getInt("UserID"));
				u.setpassword(rs.getString("Password"));
				userList.add( u );
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return userList;
	}

	public static String getSlum( int slumID )
	{
		ResultSet rs = executeSQL("SELECT Name FROM Slum WHERE SlumID="+slumID);
		try
		{
			if(rs.next())
				return rs.getString("Name");
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public static ArrayList<Slum> getSlumList(boolean isActive)
	{
		ArrayList<Slum> slumList = new ArrayList<>();
		ResultSet rs;
		if(isActive)
			rs = executeSQL("SELECT * FROM Slum WHERE IsActive=TRUE");
		else
			rs = executeSQL("SELECT * FROM Slum");
		try
		{
			while(rs.next())
				slumList.add(new Slum(rs.getInt("SlumID"), rs.getString("Name"), rs.getBoolean("IsActive")));
		} catch (SQLException e) { e.printStackTrace(); }
		return slumList;
	}

	public static ArrayList<PatientDailyRecord> getDailyRecord(Date date)
	{
		ArrayList<PatientDailyRecord> dailyRecord = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT P.LastName, P.MiddleName, P.FirstName, P.Sex, P.AgeYear, P.AgeMonth, P.AgeDay, "
				+ "TR.Height, TR.Weight, U.Name AS Nurse, PV.VisitID, P.PatientID "
				+ "FROM Patient P, TriageRecord TR, PatientVisit PV, User U "
				+ "WHERE PV.PatientID=P.PatientID AND PV.VisitID=TR.VisitID AND PV.NurseID=U.UserID AND "
				+ "DATE(PV.VisitDateTime) = DATE('"+date+"')");
		try
		{
			while(rs.next())
			{
				long patientID = rs.getLong("PatientID");
				long visitID = rs.getLong("VisitID");
				PatientDailyRecord r = new PatientDailyRecord();
				String first = rs.getString("FirstName");
				String patientName = rs.getString("LastName");
				if( !first.isEmpty() )
				{
					if(patientName.isEmpty())
						patientName += first;
					else
						patientName += (" " + first);
				}
				r.setpatientName(patientName);
				r.setsex(rs.getString("Sex"));
				if(rs.getInt("AgeYear")!=0)
					r.setage(rs.getInt("AgeYear")+"");
				else
					r.setage(rs.getInt("AgeMonth")+"m "+rs.getInt("AgeDay")+"d");
				r.setheight(rs.getFloat("Height"));
				r.setweight(rs.getFloat("Weight"));
				r.setnurse(rs.getString("Nurse"));
				ResultSet rs1 = executeSQL("SELECT COUNT(VisitID) AS VisitNumber FROM PatientVisit "
						+ "WHERE PatientID="+patientID);
				if(rs1.next())
					if( rs1.getInt("VisitNumber") > 1 )
						r.setisNew(false);
					else
						r.setisNew(true);
				rs1.close();
				rs1 = executeSQL("SELECT U.Name AS Doctor FROM PatientVisit PV, User U "
						+ "WHERE PV.DoctorID=U.UserID AND PV.VisitID="+visitID);
				if(rs1.next())
					r.setdoctor(rs1.getString("Doctor"));
				else
					r.setdoctor("");
				rs1.close();
				rs1 = executeSQL("SELECT U.Name AS Pharmacist FROM PatientVisit PV, User U "
						+ "WHERE PV.PharmacistID=U.UserID AND PV.VisitID="+visitID);
				if(rs1.next())
					r.setpharmacist(rs1.getString("Pharmacist"));
				else
					r.setpharmacist("");
				rs1.close();
				rs1 = executeSQL("SELECT Description FROM PatientHistory "
						+ "WHERE IsActive = TRUE AND HistoryType='"+PatientHistory.TYPE_CC+"' AND VisitID="+visitID);
				if(rs1.next())
					r.setchiefComplaint(rs1.getString("Description"));
				else
					r.setchiefComplaint("");
				rs1.close();
				rs1 = executeSQL("SELECT Description FROM PatientHistory "
						+ "WHERE IsActive = TRUE AND HistoryType='"+PatientHistory.TYPE_CD+"' AND VisitID="+visitID);
				if(rs1.next())
					r.setdiagnosis(rs1.getString("Description"));
				else
					r.setdiagnosis("");
				rs1.close();
				dailyRecord.add(r);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return dailyRecord;
	}

	public static ArrayList<Comment> getCommentList( long patientID )
	{
		ArrayList<Comment> commentList = new ArrayList<>();
		ResultSet rs = executeSQL("SELECT C.CommentID, C.Comment, C.Response, C.VisitID, C.CommentUserID, C.ResponseUserID, "
				+ "DATE_FORMAT(C.CommentDateTime, '%d-%b-%Y') AS CommentTime, "
				+ "DATE_FORMAT(C.ResponseDateTime, '%d-%b-%Y') AS ResponseTime, C.IsResponded, U.Name, "
				+ "DATE_FORMAT(PV.VisitDateTime, '%d-%b-%Y') AS VisitTime "
				+ "FROM Comment C, PatientVisit PV, User U WHERE C.VisitID=PV.VisitID AND C.IsResponded=FALSE "
				+ "AND C.CommentUserID = U.UserID AND PV.PatientID="+patientID
				+ " ORDER BY PV.VisitDateTime DESC");
		try
		{
			while(rs.next())
				commentList.add(new Comment(rs.getInt("CommentID"), rs.getString("Comment"), rs.getString("Response"), rs.getLong("VisitID"), rs.getInt("CommentUserID"), rs.getString("Name"), rs.getInt("ResponseUserID"), rs.getString("CommentTime"), rs.getString("ResponseTime"), rs.getString("VisitTime"), rs.getBoolean("IsResponded")));
		} catch (SQLException e) { e.printStackTrace(); }
		return commentList;
    }

    /**
     * execute whatever sql string you give
     *
     * @param sql string
     * @return A table of data representing a database result set, which is usually generated by executing a statement that queries the database.
     */
    public static ResultSet executeSQL(String sql){
        try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, accountName, password);
			Statement stat = conn.createStatement();
			return stat.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void backupSql(PreparedStatement stat, String type) throws SQLException
	{
		String backupString = stat.toString();
		int start = backupString.indexOf(":");
		backupString = backupString.substring(start+2, backupString.length());

		String sql = "INSERT INTO BackupSQL (Script, Type)"
				+ " VALUES (?,?)";
		PreparedStatement thisStat = conn.prepareStatement(sql);
		thisStat.setString(1, backupString);
		thisStat.setString(2, type);
		thisStat.executeUpdate();
		conn.commit();
		
		/*backupString = backupString.replace("\"", "!@");
		backupString = backupString.replace("\\'", "#$");
		backupString = backupString.replace("'", "\"");
		//Statement runStat = conn.createStatement();
		stat.executeUpdate("INSERT INTO BackupSQL (Script, Type) VALUES ('"+backupString+"', '"+type+"')");
		conn.commit();*/
	}

	public static void backupSql(String script, String type) throws SQLException
	{
		String sql = "INSERT INTO BackupSQL (Script, Type)"
				+ " VALUES (?,?)";
		PreparedStatement thisStat = conn.prepareStatement(sql);
		thisStat.setString(1, script);
		thisStat.setString(2, type);
		thisStat.executeUpdate();
		conn.commit();
	}

	public static boolean runBackupSql(PrintWriter writer)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection syncConnection = DriverManager.getConnection( JDBC.getSyncURL(), accountName, password );
			Statement syncStat = syncConnection.createStatement();
			syncConnection.setAutoCommit(false);
			ResultSet rs = executeSQL("SELECT * FROM BackupSQL");
			while(rs.next())
			{
				/*String sql = rs.getString("Script").replace("\"", "'");
				sql = sql.replace("#@!", "\\'");
				sql = sql.replace("!@#", "\"");
				syncStat.executeUpdate(sql);*/
				syncStat.executeUpdate(rs.getString("Script"));
			}
			syncConnection.commit();
			syncConnection.close();
			return true;
		}
		catch (Exception e1)
		{
			e1.printStackTrace(writer);
			try { conn.rollback(); } catch (SQLException e2) { e2.printStackTrace(writer); e2.printStackTrace(); }
			e1.printStackTrace();
			return false;
		}
	}

	public static ArrayList<String> getBackupSQL()
	{
		ArrayList<String> backupSQL = new ArrayList<String>();
		try
		{
			ResultSet rs = executeSQL("SELECT * FROM BackupSQL");
			while(rs.next())
			{
				String sql = rs.getString("Script").replace("\"", "'");
				sql += ";";
				backupSQL.add(sql);
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return backupSQL;
	}

	public static void runMobileServerStatement(String sql)
	{
		try
		{
			Statement stat = conn.createStatement();
			stat.executeUpdate( sql );
			conn.commit();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public static void runOfficeServerStatement(String sql)
	{
		try
		{
			Connection syncConnection = DriverManager.getConnection( JDBC.getSyncURL(), "slave_user", "1234" );
			Statement stat = syncConnection.createStatement();
			stat.executeQuery( sql );
			syncConnection.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public static String getMasterFileName(String sql)
	{
		try
		{
			Connection syncConnection = DriverManager.getConnection( JDBC.getSyncURL(), "slave_user", "1234" );
			Statement stat = syncConnection.createStatement();
			ResultSet rs = stat.executeQuery( sql );
			if(rs.next())
				return rs.getString("File");
			syncConnection.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return null;
	}

	public static int getMasterFilePosition(String sql)
	{
		try
		{
			Connection syncConnection = DriverManager.getConnection( JDBC.getSyncURL(), "slave_user", "1234" );
			Statement stat = syncConnection.createStatement();
			ResultSet rs = stat.executeQuery( sql );
			if(rs.next())
				return rs.getInt("Position");
			syncConnection.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return -1;
	}
}
