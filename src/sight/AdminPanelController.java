package sight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
*
* @author Michelle, Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class AdminPanelController implements Initializable
{
	//User Management
	@FXML private TitledPane tpUserManagement;
	@FXML private TableView<User> tvUserManagementTable;
	@FXML private TableColumn<User, String> tbUserName;
	@FXML private TableColumn<User, Boolean> tbTriageAccess;
	@FXML private TableColumn<User, Boolean> tbConsultationAccess;
	@FXML private TableColumn<User, Boolean> tbPharmacyAccess;
	@FXML private TableColumn<User, Boolean> tbInventoryAccess;
	@FXML private TableColumn<User, Boolean> tbStatisticsAccess;
	@FXML private TableColumn<User, Boolean> tbSupervisorAccess;
	@FXML private TableColumn<User, Boolean> tbAdminAccess;
	@FXML private TableColumn<User, Boolean> tbUserIsActive;

	//Keyword Management
	@FXML private TitledPane tpKeywordManagement;
	@FXML private ComboBox<String> cbKeywordType;
	@FXML private TextField tfKeyword;
	@FXML private ListView<String> lvKeywordList;

	//Slum Management
	@FXML private TitledPane tpSlumManagement;
	@FXML private TextField tfSlum;
	//@FXML private ListView<String> lvSlumList;
	@FXML private TableView<Slum> tvSlumManagementTable;
	@FXML private TableColumn<Slum, String> tbSlumName;
	@FXML private TableColumn<Slum, Boolean> tbSlumIsActive;

	//Single Day Patient Record
	@FXML private TitledPane tpDailyReport;
	@FXML private DatePicker dpDate;
	@FXML private TableView<PatientDailyRecord> tvDailyReportTable;
	@FXML private TableColumn<PatientDailyRecord, String> tbPatientName;
	@FXML private TableColumn<PatientDailyRecord, String> tbSex;
	@FXML private TableColumn<PatientDailyRecord, String> tbAge;
	@FXML private TableColumn<PatientDailyRecord, Boolean> tbNew;
	@FXML private TableColumn<PatientDailyRecord, Double> tbHeight;
	@FXML private TableColumn<PatientDailyRecord, Double> tbWeight;
	@FXML private TableColumn<PatientDailyRecord, String> tbChiefComplaint;
	@FXML private TableColumn<PatientDailyRecord, String> tbDiagnosis;
	@FXML private TableColumn<PatientDailyRecord, String> tbNurse;
	@FXML private TableColumn<PatientDailyRecord, String> tbDoctor;
	@FXML private TableColumn<PatientDailyRecord, String> tbPharmacist;

	@FXML private TableView<TotalTableItem> tvTotalTable;
	@FXML private TableColumn<TotalTableItem, String> tbPatientSex;
	@FXML private TableColumn<TotalTableItem, Integer> tbAgeAbove18;
	@FXML private TableColumn<TotalTableItem, Integer> tbAgeAbove18New;
	@FXML private TableColumn<TotalTableItem, Integer> tbAgeBelow18;
	@FXML private TableColumn<TotalTableItem, Integer> tbAgeBelow18New;
	@FXML private TableColumn<TotalTableItem, Integer> tbTotalNew;
	@FXML private TableColumn<TotalTableItem, Integer> tbTotal;

	private int slumID = -1; 

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		initUserManagementPane();
		initKeywordManagementPane();
		initSlumManagementPane();
		initDatabaseManagementPane();
		initSingleDayPatientRecordPane();
	}

	@FXML
	private void btnResetUserPasswordPressed(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset User Password");
		alert.setContentText("Confirm to reset the password of this user to DEFAULT?");
		Optional<ButtonType> result = alert.showAndWait();
		if( (result.isPresent()) && (result.get() == ButtonType.OK) )
		{
			User u = tvUserManagementTable.getSelectionModel().getSelectedItem();
			if( u == null )
				return;
			u.setpassword(User.getEncryptedPassword(User.DEFAULT_DECRYPTED_PASSWORD));
		}
	}

	@FXML
	private void btnResetUserTablePressed(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset User Table");
		alert.setContentText("All changes will be discarded.\nConfirm to reset?");
		Optional<ButtonType> result = alert.showAndWait();
		if( (result.isPresent()) && (result.get() == ButtonType.OK) )
			fillUserTable();
	}

	@FXML
	private void btnAddUserPressed(ActionEvent event)
	{
		ObservableList<User> currentUserList = tvUserManagementTable.getItems();
		currentUserList.add( new User(User.getEncryptedPassword(User.DEFAULT_DECRYPTED_PASSWORD)) );
		tvUserManagementTable.setItems( currentUserList );
	}

	@FXML
	private void btnDeactivateUserPressed(ActionEvent event)
	{
		User u = tvUserManagementTable.getSelectionModel().getSelectedItem();
		if( u == null )
			return;
		else if( u.getid() == -1 )
			tvUserManagementTable.getItems().remove(u);
		else if( u.getid() != -1 )
		{
			u.setisActive(false);
			return;
		}
		/*Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Deactivate User", "Confirm to Deactivate the user?", AlertType.CONFIRMATION);
		if( (result.isPresent()) && (result.get() == ButtonType.OK) )
			tvUserManagementTable.getItems().remove(u);*/
	}

	@FXML
	private void btnConfirmUserManagementPressed(ActionEvent event)
	{
		ObservableList<User> currentUserList = tvUserManagementTable.getItems();
		for( User u : currentUserList )
			if( u.getname().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Warning!", "Please fill the user name.", AlertType.WARNING);
				return;
			}
			//else if( !u.gettriageAccess() && !u.getconsultationAccess() && ! u.getpharmacyAccess() && !u.getinventoryAccess() && !u.getstatisticsAccess() && !u.getstatisticsAccess() )
		for( User u : currentUserList )
			if( u.getid() == -1 )
			{
				int userID = DatabaseController.addUser(u);
				u.setid(userID);
			}
			else
				DatabaseController.updateUser(u);
		promptMessage("One-2-One Medical System", "Successful!", "Changes successfully submitted.", AlertType.INFORMATION);
	}
  
	@FXML
	private void btnAddKeywordPressed(ActionEvent event)
	{
		String keyword = tfKeyword.getText();
		if(keyword.trim().isEmpty())
			return;
		String type = "";
		switch(cbKeywordType.getValue())
		{
			case "General":
				type = PatientHistory.TYPE_GENERAL;
				break;
			case "Chief Complaint":
				type = PatientHistory.TYPE_CC;
				break;
			case "Drug History":
				type = PatientHistory.TYPE_DH;
				break;
			case "Allergy":
				type = PatientHistory.TYPE_ALE;
				break;
			case "Family History":
				type = PatientHistory.TYPE_FH;
				break;
			case "Social History":
				type = PatientHistory.TYPE_SH;
				break;
			case "Clinical Diagnosis":
				type = PatientHistory.TYPE_CD;
				break;
			case "Route":
				type = "Route";
				break;
		}
		ObservableList<String> curKeywordList = lvKeywordList.getItems();
		if(curKeywordList.contains(keyword))
			promptMessage("One-2-One Medical System", "Duplicate Keyword!", "The keyword exists in the list already.", AlertType.INFORMATION);
		else
		{
			DatabaseController.addKeyword(type, keyword); 
			curKeywordList.add(keyword);
			lvKeywordList.setItems(curKeywordList);
			lvKeywordList.setVisible(false);
			lvKeywordList.setVisible(true);
			tfKeyword.setText("");
			promptMessage("One-2-One Medical System", "Successful!", "The keyword is added.", AlertType.INFORMATION);
		}
	}
	
	@FXML
	private void btnDeleteKeywordPressed(ActionEvent event)
	{
		String keyword = tfKeyword.getText();
		String type = "";
		switch(cbKeywordType.getValue())
		{
			case "Chief Complaint":
				type = PatientHistory.TYPE_CC;
				break;
			case "Drug History":
				type = PatientHistory.TYPE_DH;
				break;
			case "Allergy":
				type = PatientHistory.TYPE_ALE;
				break;
			case "Family History":
				type = PatientHistory.TYPE_FH;
				break;
			case "Social History":
				type = PatientHistory.TYPE_SH;
				break;
			case "Clinical Diagnosis":
				type = PatientHistory.TYPE_CD;
				break;
			case "Route":
				type = "Route";
				break;
		}
		ObservableList<String> tempList = lvKeywordList.getItems();
		if(keyword.trim().isEmpty())
		{
			keyword = lvKeywordList.getSelectionModel().getSelectedItem();
			if(keyword == null)
				return;
		}
		if(!tempList.contains(keyword))
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("CANNOT FOUND");
			alert.setContentText("The keyword does not exist.");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setContentText("Confirm to remove the keyword?");
			Optional<ButtonType> result = alert.showAndWait();
			if( (result.isPresent()) && (result.get() == ButtonType.OK) )
			{
				DatabaseController.removeKeyword(type, keyword); 
				tempList.remove(keyword);
				lvKeywordList.setItems(tempList);
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("SUCCESS");
				alert.setContentText("The keyword is removed.");
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	private void btnUploadKeywordPressed (ActionEvent event)//TODO: Can handle upload file with different keyword types
	{
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Keyword File (Text File ONLY)");
		File file = fileChooser.showOpenDialog(stage);
		if(file == null)
			return;

		String type = "";
		switch(cbKeywordType.getValue())
		{
			case "Chief Complaint":
				type = PatientHistory.TYPE_CC;
				break;
			case "Drug History":
				type = PatientHistory.TYPE_DH;
				break;
			case "Allergy":
				type = PatientHistory.TYPE_ALE;
				break;
			case "Family History":
				type = PatientHistory.TYPE_FH;
				break;
			case "Social History":
				type = PatientHistory.TYPE_SH;
				break;
			case "Clinical Diagnosis":
				type = PatientHistory.TYPE_CD;
				break;
			case "Route":
				type = "Route";
				break;
		}
		long newKeywordCount = 0;
		try{
			Scanner scanner = new Scanner(file);
			ObservableList<String> curKeywordList = lvKeywordList.getItems();
			while(scanner.hasNextLine())
			{
				String keyword = scanner.nextLine();
				if(keyword.trim().isEmpty())
					continue;
				boolean exist = false;				
				for( String k : curKeywordList )
					if (keyword.equals(k))
					{
						exist = true;
						break;
					}
				if(!exist)
				{
					DatabaseController.addKeyword( type, keyword );
					curKeywordList.add(keyword);
					newKeywordCount++;
				}
			};
			lvKeywordList.setItems(curKeywordList);
			java.util.Collections.sort
			(
				lvKeywordList.getItems(),
				new java.util.Comparator<String>()
				{
					@Override
					public int compare(String s1, String s2){ return s1.compareToIgnoreCase(s2); }
				}
			);
			scanner.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SUCCESS");
			alert.setContentText(newKeywordCount+" keyword(s) is/are added.");
			alert.showAndWait();
		}
		catch (FileNotFoundException e) {
		e.printStackTrace();  
		}	
	}

	@FXML
	private void btnResetSlumTablePressed(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset User Table");
		alert.setContentText("All changes will be discarded.\nConfirm to reset?");
		Optional<ButtonType> result = alert.showAndWait();
		if( (result.isPresent()) && (result.get() == ButtonType.OK) )
			fillSlumTable();
	}

	@FXML
	private void btnAddSlumPressed(ActionEvent event)
	{
		ObservableList<Slum> currentSlumList = tvSlumManagementTable.getItems();
		currentSlumList.add( new Slum() );
		tvSlumManagementTable.setItems( currentSlumList );
	}

	@FXML
	private void btnDeactivateSlumPressed(ActionEvent event)
	{
		Slum s = tvSlumManagementTable.getSelectionModel().getSelectedItem();
		if( s == null )
			return;
		else if( s.getid() == -1 )
			tvSlumManagementTable.getItems().remove(s);
		else if( s.getid() != -1 )
		{
			s.setisActive(false);
			return;
		}
		/*Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Deactivate User", "Confirm to Deactivate the user?", AlertType.CONFIRMATION);
		if( (result.isPresent()) && (result.get() == ButtonType.OK) )
			tvSlumManagementTable.getItems().remove(s);*/
	}

	@FXML
	private void btnConfirmSlumManagementPressed(ActionEvent event)
	{
		ObservableList<Slum> currentSlumList = tvSlumManagementTable.getItems();
		for( Slum s : currentSlumList )
			if( s.getname().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Warning!", "Please fill the slum name.", AlertType.WARNING);
				return;
			}
			//else if( !u.gettriageAccess() && !u.getconsultationAccess() && ! u.getpharmacyAccess() && !u.getinventoryAccess() && !u.getstatisticsAccess() && !u.getstatisticsAccess() )
		for( Slum s : currentSlumList )
			if( s.getid() == -1 )
			{
				int slumID = DatabaseController.addSlum(s.getname());
				s.setid(slumID);
			}
			else
				DatabaseController.updateSlum(s.getid(), s.getname(), s.getisActive());
		promptMessage("One-2-One Medical System", "Successful!", "Changes successfully submitted.", AlertType.INFORMATION);
	}

	@FXML
	private void btnExportDailyReportPressed(ActionEvent event)
	{
		if(dpDate.getValue() == null)
			return;
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export Daily Report (Excel)");
		fileChooser.setInitialFileName("Daily_Report_"+dpDate.getValue());
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel Documents (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(stage);
		if(file == null)
			return;
		stage.close();

		/*new Thread(){
			@Override
			public void run()
			{
				final ProgressBar progressBar = new ProgressBar();
				progressBar.setProgress(-1);
				progressBar.setMinSize(200, 15);
				//progressBar.progressProperty().bind(task.progressProperty());
				final ProgressIndicator progressIndicator = new ProgressIndicator();
				//progressIndicator.progressProperty().bind(task.progressProperty());
				//progressIndicator.setProgress(0.0);
				final HBox hbox = new HBox();
		        hbox.setSpacing(5);
		        hbox.setAlignment(Pos.CENTER);
		        hbox.getChildren().addAll(progressBar, progressIndicator);
				final Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
				final Stage progressStage = new Stage();
				progressStage.setScene(new Scene(hbox));
				progressStage.setTitle("Loading...");
				progressStage.getIcons().setAll(image);
				progressStage.sizeToScene();
				progressStage.setResizable(false);
				progressStage.setMinHeight(30);
				progressStage.setMinWidth(250);
				progressStage.show();
			}
		}.start();*/
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("One-2-One Medical System");
		alert.setHeaderText("Loading...");
		alert.show();

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet dailyReportSheet = workbook.createSheet("Daily Report");
		XSSFSheet totalDataSheet = workbook.createSheet("Total Data");

		//long totalColumn = tvDailyReportTable.getColumns().size();
		String curSlumName = DatabaseController.getSlum(slumID);
		int rowNum = 0;
		Row row;

		CellStyle headingStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		//font.setColor(HSSFColor.BLACK.index);
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		headingStyle.setFont(font);
		headingStyle.setAlignment(CellStyle.ALIGN_CENTER);

		row = dailyReportSheet.createRow(rowNum++);
		row.createCell(0).setCellValue("Patient Records");
		row.getCell(0).setCellStyle(headingStyle);
		dailyReportSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));

		row = dailyReportSheet.createRow(rowNum++);
		row.createCell(0).setCellValue(curSlumName+" Community Medical Clinic");
		row.getCell(0).setCellStyle(headingStyle);
		dailyReportSheet.addMergedRegion(new CellRangeAddress(1,1,0,10));

		CellStyle dateStyle = workbook.createCellStyle();
		font = workbook.createFont();
		//font.setColor(HSSFColor.BLACK.index);
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		dateStyle.setFont(font);
		dateStyle.setAlignment(CellStyle.ALIGN_RIGHT);

		row = dailyReportSheet.createRow(rowNum++);
		row.createCell(0).setCellValue("Date: "+dpDate.getValue());
		row.getCell(0).setCellStyle(dateStyle);
		dailyReportSheet.addMergedRegion(new CellRangeAddress(2,2,0,10));

		CellStyle normalStyle = workbook.createCellStyle();
		font = workbook.createFont();
		//font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		normalStyle.setFont(font);

		row = dailyReportSheet.createRow(rowNum++);
		//row.setRowStyle(normalStyle);
		row.createCell(0).setCellValue("Patient");
		row.getCell(0).setCellStyle(normalStyle);
		row.createCell(1).setCellValue("Sex");
		row.getCell(1).setCellStyle(normalStyle);
		row.createCell(2).setCellValue("Age");
		row.getCell(2).setCellStyle(normalStyle);
		row.createCell(3).setCellValue("New");
		row.getCell(3).setCellStyle(normalStyle);
		row.createCell(4).setCellValue("Height");
		row.getCell(4).setCellStyle(normalStyle);
		row.createCell(5).setCellValue("Weight");
		row.getCell(5).setCellStyle(normalStyle);
		row.createCell(6).setCellValue("Chief Complaint");
		row.getCell(6).setCellStyle(normalStyle);
		row.createCell(7).setCellValue("Clinical Diagnosis");
		row.getCell(7).setCellStyle(normalStyle);
		row.createCell(8).setCellValue("Nurse");
		row.getCell(8).setCellStyle(normalStyle);
		row.createCell(9).setCellValue("Doctor");
		row.getCell(9).setCellStyle(normalStyle);
		row.createCell(10).setCellValue("Pharmacist");
		row.getCell(10).setCellStyle(normalStyle);

		ObservableList<PatientDailyRecord> dailyRecordList = tvDailyReportTable.getItems();
		for( PatientDailyRecord r : dailyRecordList )
		{
			row = dailyReportSheet.createRow(rowNum++);
			row.createCell(0).setCellValue(r.getpatientName());
			row.getCell(0).setCellStyle(normalStyle);
			row.createCell(1).setCellValue(r.getsex());
			row.getCell(1).setCellStyle(normalStyle);
			row.createCell(2).setCellValue(r.getage());
			row.getCell(2).setCellStyle(normalStyle);
			if(r.getisNew())
				row.createCell(3).setCellValue("Yes");
			else
				row.createCell(3).setCellValue("No");
			row.getCell(3).setCellStyle(normalStyle);
			if(r.getheight() != 0)
				row.createCell(4).setCellValue(r.getheight());
			else
				row.createCell(4).setCellValue("");
			row.getCell(4).setCellStyle(normalStyle);
			if(r.getweight() != 0)
				row.createCell(5).setCellValue(r.getweight());
			else
				row.createCell(5).setCellValue("");
			row.getCell(5).setCellStyle(normalStyle);
			row.createCell(6).setCellValue(r.getchiefComplaint());
			row.getCell(6).setCellStyle(normalStyle);
			row.createCell(7).setCellValue(r.getdiagnosis());
			row.getCell(7).setCellStyle(normalStyle);
			row.createCell(8).setCellValue(r.getnurse());
			row.getCell(8).setCellStyle(normalStyle);
			row.createCell(9).setCellValue(r.getdoctor());
			row.getCell(9).setCellStyle(normalStyle);
			row.createCell(10).setCellValue(r.getpharmacist());
			row.getCell(10).setCellStyle(normalStyle);
		}

		rowNum = 0;
		row = totalDataSheet.createRow(rowNum++);
		row.createCell(0).setCellValue("Total Data");
		row.getCell(0).setCellStyle(headingStyle);
		totalDataSheet.addMergedRegion(new CellRangeAddress(0,0,0,6));

		row = totalDataSheet.createRow(rowNum++);
		row.createCell(0).setCellValue("Sex");
		row.getCell(0).setCellStyle(normalStyle);
		row.createCell(1).setCellValue(">18");
		row.getCell(1).setCellStyle(normalStyle);
		row.createCell(2).setCellValue("New");
		row.getCell(2).setCellStyle(normalStyle);
		row.createCell(3).setCellValue("<18");
		row.getCell(3).setCellStyle(normalStyle);
		row.createCell(4).setCellValue("New");
		row.getCell(4).setCellStyle(normalStyle);
		row.createCell(5).setCellValue("Total New");
		row.getCell(5).setCellStyle(normalStyle);
		row.createCell(6).setCellValue("Grand Total");
		row.getCell(6).setCellStyle(normalStyle);
		
		ObservableList<TotalTableItem> list = tvTotalTable.getItems();
		for( TotalTableItem i : list )
		{
			row = totalDataSheet.createRow(rowNum++);
			row.createCell(0).setCellValue(i.getpatientSex());
			row.getCell(0).setCellStyle(normalStyle);
			row.createCell(1).setCellValue(i.getageAbove18());
			row.getCell(1).setCellStyle(normalStyle);
			row.createCell(2).setCellValue(i.getageAbove18New());
			row.getCell(2).setCellStyle(normalStyle);
			row.createCell(3).setCellValue(i.getageBelow18());
			row.getCell(3).setCellStyle(normalStyle);
			row.createCell(4).setCellValue(i.getageBelow18New());
			row.getCell(4).setCellStyle(normalStyle);
			row.createCell(5).setCellValue(i.gettotalNew());
			row.getCell(5).setCellStyle(normalStyle);
			row.createCell(6).setCellValue(i.gettotal());
			row.getCell(6).setCellStyle(normalStyle);
		}

		for( int i = 0; i <= 10; i++ )
		{
			dailyReportSheet.autoSizeColumn(i);
			totalDataSheet.autoSizeColumn(i);
		}

		try
		{
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			//progressStage.close();
			//promptMessage("One-2-One Medical System", "Successfully Exported!", "Please check the directory.", AlertType.INFORMATION);
			alert.setHeaderText("Successfully Exported!");
			alert.setContentText("Please check the directory.");
			workbook.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	private void btnSynchronizePressed(ActionEvent event)
	{
		try
		{
			//DatabaseController.runMobileServerStatement("STOP Slave;");
			//DatabaseController.runMobileServerStatement("RESET Slave;");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd"); 
			PrintWriter writer = new PrintWriter("Backup.sql", "UTF-8");
			ArrayList<String> backupSQL = DatabaseController.getBackupSQL();
			for(String s : backupSQL)
				writer.println(s);
			writer.close();
			writer = new PrintWriter("Log_"+sdf.format(Calendar.getInstance().getTime())+".txt", "UTF-8");
			boolean isSuccess = DatabaseController.runBackupSql(writer);
			writer.close();
			DatabaseController.runMobileServerStatement("Flush Tables with read lock;");
			DatabaseController.runOfficeServerStatement("Flush Tables with read lock;");
			String fileName = DatabaseController.getMasterFileName("SHOW MASTER STATUS");
			int position = DatabaseController.getMasterFilePosition("SHOW MASTER STATUS");
			DatabaseController.runMobileServerStatement("CHANGE MASTER TO "
					+ "MASTER_HOST = '"+JDBC.getSyncIP()+"', "
					+ "MASTER_USER = 'slave_user', "
					+ "MASTER_PASSWORD = '1234', "
					+ "MASTER_LOG_FILE = '"+fileName+"', "
					+ "MASTER_LOG_POS = "+position+", "
					+ "MASTER_CONNECT_RETRY = 10;");
			DatabaseController.runMobileServerStatement("Unlock tables;");
			DatabaseController.runOfficeServerStatement("Unlock tables;");
			DatabaseController.runMobileServerStatement("START Slave;");
			DatabaseController.runMobileServerStatement("DROP TABLE IF EXISTS BackupSQL;");
			String sql = "CREATE TABLE IF NOT EXISTS BackupSQL("
				+ "`SQLID` INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ "`Script` VARCHAR(1024) NOT NULL,"
				+ "`Type` VARCHAR(1) NOT NULL"
				+ ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;";
			DatabaseController.runMobileServerStatement(sql);
			if(isSuccess)
				promptMessage("One-2-One Medical System", "Successful!", "Databases successfully synchronized.", AlertType.INFORMATION);
			else
				promptMessage("One-2-One Medical System", "Error!", "Please check the backup.sql and the error_log.txt.", AlertType.ERROR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			promptMessage("One-2-One Medical System", "Error!", "Please check the backup.sql and the error_log.txt.", AlertType.ERROR);
		}
	}

	private void initUserManagementPane()
	{
		tvUserManagementTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvUserManagementTable.setEditable(true);
		tbUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
		//tbUserName.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<User, String>, TableCell<User, String>> cfUserName =
			new Callback<TableColumn<User, String>, TableCell<User, String>>() {
			public TableCell<User, String> call(TableColumn<User, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvUserManagementTable, "User Name", (int)tbUserName.getPrefWidth(), "UserName");
			}
		};
		tbUserName.setCellFactory( cfUserName );
		tbTriageAccess.setCellValueFactory( new PropertyValueFactory<>("triageAccess"));
		tbTriageAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbTriageAccess) );
		tbConsultationAccess.setCellValueFactory( new PropertyValueFactory<>("consultationAccess"));
		tbConsultationAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbConsultationAccess) );
		tbPharmacyAccess.setCellValueFactory( new PropertyValueFactory<>("pharmacyAccess"));
		tbPharmacyAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbPharmacyAccess) );
		tbInventoryAccess.setCellValueFactory( new PropertyValueFactory<>("inventoryAccess"));
		tbInventoryAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbInventoryAccess) );
		tbStatisticsAccess.setCellValueFactory( new PropertyValueFactory<>("statisticsAccess"));
		tbStatisticsAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbStatisticsAccess) );
		tbSupervisorAccess.setCellValueFactory( new PropertyValueFactory<>("supervisorAccess"));
		tbSupervisorAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbSupervisorAccess) );
		tbAdminAccess.setCellValueFactory( new PropertyValueFactory<>("adminAccess"));
		tbAdminAccess.setCellFactory( CheckBoxTableCell.forTableColumn(tbAdminAccess) );
		tbAdminAccess.setEditable(false);
		tbUserIsActive.setCellValueFactory( new PropertyValueFactory<>("isActive"));
		tbUserIsActive.setCellFactory( CheckBoxTableCell.forTableColumn(tbUserIsActive) );
		fillUserTable();
	}

	private void initKeywordManagementPane()
	{
		cbKeywordType.getItems().addAll("General", "Chief Complaint", "Drug History", "Allergy", "Family History", "Social History", "Clinical Diagnosis", "Route");
		cbKeywordType.valueProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					ObservableList<String> curItemList = lvKeywordList.getItems();
					curItemList.clear();
					curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
					switch(newValue)
					{
						case "Chief Complaint":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_CC));
							break;
						case "Drug History":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_DH));
							break;
						case "Allergy":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_ALE));
							break;
						case "Family History":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_FH));
							break;
						case "Social History":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_SH));
							break;
						case "Clinical Diagnosis":
							curItemList.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_CD));
							break;
						case "Route":
							curItemList.addAll(DatabaseController.getKeywordList("Route"));
							break;
					}
					lvKeywordList.setItems(curItemList);
				}
				
			}
		);
	}

	private void initSlumManagementPane()
	{
		/*ObservableList<String> curItemList = lvSlumList.getItems();
		curItemList.addAll(DatabaseController.getSlumList());
		lvSlumList.setItems(curItemList);*/
		tvSlumManagementTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvSlumManagementTable.setEditable(true);
		tbSlumName.setCellValueFactory(new PropertyValueFactory<>("name"));
		//tbSlumName.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Slum, String>, TableCell<Slum, String>> cfSlumName =
			new Callback<TableColumn<Slum, String>, TableCell<Slum, String>>() {
			public TableCell<Slum, String> call(TableColumn<Slum, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvSlumManagementTable, "Slum Name", (int)tbSlumName.getPrefWidth(), "SlumName");
			}
		};
		tbSlumName.setCellFactory( cfSlumName );
		tbSlumIsActive.setCellValueFactory( new PropertyValueFactory<>("isActive"));
		tbSlumIsActive.setCellFactory( CheckBoxTableCell.forTableColumn(tbSlumIsActive) );

		ObservableList<Slum> curItemList = tvSlumManagementTable.getItems();
		curItemList.addAll(DatabaseController.getSlumList(false));
		tvSlumManagementTable.setItems(curItemList);
	}

	private void initDatabaseManagementPane()
	{
		
	}

	private void initSingleDayPatientRecordPane()
	{
		tvDailyReportTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvDailyReportTable.setEditable(false);
		tbPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
		tbPatientName.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
		tbSex.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tbAge.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNew.setCellValueFactory( new PropertyValueFactory<>("isNew"));
		tbNew.setCellFactory( CheckBoxTableCell.forTableColumn(tbNew) );
		tbHeight.setCellValueFactory( new PropertyValueFactory<>("height") );
		tbWeight.setCellValueFactory( new PropertyValueFactory<>("weight") );
		tbChiefComplaint.setCellValueFactory(new PropertyValueFactory<>("chiefComplaint"));
		tbChiefComplaint.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
		tbDiagnosis.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNurse.setCellValueFactory(new PropertyValueFactory<>("nurse"));
		tbNurse.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
		tbDoctor.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPharmacist.setCellValueFactory(new PropertyValueFactory<>("pharmacist"));
		tbPharmacist.setCellFactory( TextFieldTableCell.forTableColumn() );

		tvTotalTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvTotalTable.setEditable(false);
		tbPatientSex.setCellValueFactory(new PropertyValueFactory<>("patientSex"));
		tbPatientSex.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbAgeAbove18.setCellValueFactory(new PropertyValueFactory<>("ageAbove18"));
		tbAgeAbove18New.setCellValueFactory(new PropertyValueFactory<>("ageAbove18New"));
		tbAgeBelow18.setCellValueFactory(new PropertyValueFactory<>("ageBelow18"));
		tbAgeBelow18New.setCellValueFactory(new PropertyValueFactory<>("ageBelow18New"));
		tbTotalNew.setCellValueFactory(new PropertyValueFactory<>("totalNew"));
		tbTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		dpDate.valueProperty().addListener
		(
			new ChangeListener<LocalDate>()
			{
				@Override
				public void changed(ObservableValue<? extends LocalDate> arg0, LocalDate arg1, LocalDate arg2)
				{
					ObservableList<PatientDailyRecord> curItemList = tvDailyReportTable.getItems();
					curItemList.clear();
					curItemList.addAll(DatabaseController.getDailyRecord(java.sql.Date.valueOf( dpDate.getValue() )));
					tvDailyReportTable.setItems(curItemList);

					int maleAbove18 = 0, maleAbove18New = 0, maleBelow18 = 0, maleBelow18New = 0;
					int femaleAbove18 = 0, femaleAbove18New = 0, femaleBelow18 = 0, femaleBelow18New = 0;
					for( PatientDailyRecord r : curItemList )
					{
						if(r.getsex().equals("M"))
						{
							if(r.getage().contains("m"))//Contains 'm' means he is a baby
							{
								maleBelow18++;
								if(r.getisNew())
									maleBelow18New++;
							}
							else if( Integer.parseInt(r.getage()) < 18 )
							{
								maleBelow18++;
								if(r.getisNew())
									maleBelow18New++;
							}
							else
							{
								maleAbove18++;
								if(r.getisNew())
									maleAbove18New++;
							}
						}
						else
						{
							if(r.getage().contains("m"))
							{
								femaleBelow18++;
								if(r.getisNew())
									femaleBelow18New++;
							}
							else if( Integer.parseInt(r.getage()) < 18 )
							{
								femaleBelow18++;
								if(r.getisNew())
									femaleBelow18New++;
							}
							else
							{
								femaleAbove18++;
								if(r.getisNew())
									femaleAbove18New++;
							}
						}
					}
					ObservableList<TotalTableItem> itemList = tvTotalTable.getItems();
					itemList.clear();
					itemList.add(new TotalTableItem("M", maleAbove18, maleAbove18New, maleBelow18, maleBelow18New, maleAbove18New+maleBelow18New, maleAbove18+maleBelow18));
					itemList.add(new TotalTableItem("F", femaleAbove18, femaleAbove18New, femaleBelow18, femaleBelow18New, femaleAbove18New+femaleBelow18New, femaleAbove18+femaleBelow18));
					tvTotalTable.setItems(itemList);
				}
			}
		);
	}

	public void fillUserTable()
	{
		ObservableList<User> currentUserList = tvUserManagementTable.getItems();
		currentUserList.clear();
		currentUserList.addAll( DatabaseController.getUserList() );
		tvUserManagementTable.setItems( currentUserList );
	}

	public void fillSlumTable()
	{
		ObservableList<Slum> currentSlumList = tvSlumManagementTable.getItems();
		currentSlumList.clear();
		currentSlumList.addAll( DatabaseController.getSlumList(false) );
		tvSlumManagementTable.setItems( currentSlumList );
	}

	public void setSlum( int slumID )
	{
		this.slumID = slumID;
	}

	public Optional<ButtonType> promptMessage( String title, String header, String msg, AlertType type )
	{
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		DialogPane dp = alert.getDialogPane();
		dp.setPrefWidth(400);
		dp.lookup(".label.content").setStyle("-fx-font-size: 14px;");
		dp.lookup(".header-panel").setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		return alert.showAndWait();
	}

	/*@FXML
	private void btnAddUserPressed(ActionEvent ae)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanelUserManagementDialog.fxml"));
			Parent page = loader.load();
			userManagementController = loader.<AdminPanelUserManagementDialogController>getController();
			userManagementController.setAdminPanelController(this);
			Scene scene = new Scene(page);
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Add New User");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	*/

	/*@FXML
	private void btnEditUserPressed(ActionEvent ae)
	{
		User u = tvUserManagementTable.getSelectionModel().getSelectedItem();
		if( u == null )
			return;
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanelUserManagementDialog.fxml"));
			Parent page = loader.load();
			userManagementController = loader.<AdminPanelUserManagementDialogController>getController();
			userManagementController.setAdminPanelController(this);
			userManagementController.setEdit(u);
			Scene scene = new Scene(page);
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Edit User");
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}*/

	/*@FXML
	private void btnAddSlumPressed2(ActionEvent event)
	{
		//Version 3
		String newSlumName = tfSlum.getText().trim();
		if( newSlumName == null || newSlumName.isEmpty() )
			return;
		ObservableList<String> curSlumList = lvSlumList.getItems();
		if(curSlumList.contains(newSlumName))
			return;
		DatabaseController.addSlum(newSlumName);
		curSlumList.add(newSlumName);
		lvSlumList.setItems(curSlumList);
		java.util.Collections.sort
		(
			lvSlumList.getItems(),
			new java.util.Comparator<String>()
			{
				@Override
				public long compare(String s1, String s2){ return s1.compareToIgnoreCase(s2); }
			}
		);

		//Version 2
		String newSlumName = tfSlum.getText().trim();
		if( newSlumName == null || newSlumName.isEmpty() )
			return;
		ObservableList<Slum> curSlumList = tvSlumManagementTable.getItems();
		if(curSlumList.contains(newSlumName))
		{
			promptMessage("One-2-One Medical System", "Warning!", "The new slum name exists the list already.", AlertType.WARNING);
			return;
		}
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to add new slum?", AlertType.CONFIRMATION);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			long slumID = DatabaseController.addSlum(newSlumName);
			curSlumList.add(new Slum(slumID, newSlumName, true));
			tvSlumManagementTable.setItems(curSlumList);
			java.util.Collections.sort
			(
					tvSlumManagementTable.getItems(),
				new java.util.Comparator<Slum>()
				{
					@Override
					public long compare(Slum s1, Slum s2){ return s1.getname().compareToIgnoreCase(s2.getname()); }
				}
			);
		}
	}*/

	/*@FXML
	private void btnEditSlumPressed2(ActionEvent event)
	{
		//Version 1
		String curSlumName = DatabaseController.getSlum(slumID);
		String oldSlumName = lvSlumList.getSelectionModel().getSelectedItem();
		if( oldSlumName == null )
			return;
		if( curSlumName.equals(oldSlumName) )
		{
			promptMessage("One-2-One Medical System", "Warning!", "You cannot edit current slum.", AlertType.WARNING);
			return;
		}
		String newSlumName = tfSlum.getText().trim();
		if( newSlumName == null || newSlumName.isEmpty() )
			return;
		ObservableList<String> curSlumList = lvSlumList.getItems();
		if(curSlumList.contains(newSlumName))
			return;
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to edit slum?", AlertType.CONFIRMATION);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			DatabaseController.updateSlum(oldSlumName, newSlumName, true);
			curSlumList.remove(oldSlumName);
			curSlumList.add(newSlumName);
			lvSlumList.setItems(curSlumList);
			java.util.Collections.sort
			(
				lvSlumList.getItems(),
				new java.util.Comparator<String>()
				{
					@Override
					public long compare(String s1, String s2){ return s1.compareToIgnoreCase(s2); }
				}
			);
		}

		//Version 2
		String curSlumName = DatabaseController.getSlum(slumID);
		Slum selectedSlum = tvSlumManagementTable.getSelectionModel().getSelectedItem();
		String oldSlumName = selectedSlum.getname();
		if( oldSlumName == null )
			return;
		if( curSlumName.equals(oldSlumName) )
		{
			promptMessage("One-2-One Medical System", "Warning!", "You cannot edit current slum.", AlertType.WARNING);
			return;
		}
		String newSlumName = tfSlum.getText().trim();
		if( newSlumName == null || newSlumName.isEmpty() )
			return;
		ObservableList<Slum> curSlumList = tvSlumManagementTable.getItems();
		if(curSlumList.contains(newSlumName))
		{
			promptMessage("One-2-One Medical System", "Warning!", "The new slum name exists the list already.", AlertType.WARNING);
			return;
		}
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to edit slum?", AlertType.CONFIRMATION);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			DatabaseController.updateSlum(selectedSlum.getid(), newSlumName, true);
			selectedSlum.setname(newSlumName);
			//curSlumList.remove(selectedSlum);
			//curSlumList.add(new Slum(slumID, newSlumName, true));
			tvSlumManagementTable.setItems(curSlumList);
			java.util.Collections.sort
			(
					tvSlumManagementTable.getItems(),
				new java.util.Comparator<Slum>()
				{
					@Override
					public long compare(Slum s1, Slum s2){ return s1.getname().compareToIgnoreCase(s2.getname()); }
				}
			);
		}
	}*/

	/*@FXML
	private void btnDeleteSlumPressed(ActionEvent event)
	{
		//Version 1
		String curSlumName = DatabaseController.getSlum(slumID);
		String oldSlumName = lvSlumList.getSelectionModel().getSelectedItem();
		if( oldSlumName == null )
			return;
		if( curSlumName.equals(oldSlumName) )
		{
			promptMessage("One-2-One Medical System", "Warning!", "You cannot delete current slum.", AlertType.WARNING);
			return;
		}
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to delete slum?", AlertType.CONFIRMATION);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			ObservableList<String> curSlumList = lvSlumList.getItems();
			DatabaseController.updateSlum(oldSlumName, oldSlumName, false);
			curSlumList.remove(oldSlumName);
			lvSlumList.setItems(curSlumList);
		}

		//Version 2
		String curSlumName = DatabaseController.getSlum(slumID);
		Slum selectedSlum = tvSlumManagementTable.getSelectionModel().getSelectedItem();
		String oldSlumName = selectedSlum.getname();
		if( oldSlumName == null )
			return;
		if( curSlumName.equals(oldSlumName) )
		{
			promptMessage("One-2-One Medical System", "Warning!", "You cannot delete current slum.", AlertType.WARNING);
			return;
		}
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to delete slum?", AlertType.CONFIRMATION);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			//ObservableList<Slum> curSlumList = tvSlumManagementTable.getItems();
			DatabaseController.updateSlum(selectedSlum.getid(), oldSlumName, false);
			selectedSlum.setisActive(false);
			//curSlumList.remove(selectedSlum);
			//tvSlumManagementTable.setItems(curSlumList);
		}
	}*/
}
