package sight;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import sight.TextChangeListener;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class TriagePatientController implements Initializable, EventHandler<KeyEvent>
{
	@FXML Accordion aTriagePatient;

	//Personal Data
	@FXML private TitledPane tpPersonalData;
	@FXML private TextField tfFirstName;
	@FXML private Label middleNameLabel;
	@FXML private TextField tfMiddleName;
	@FXML private TextField tfLastName;
	@FXML private Label patientIDLabel;
	@FXML private Label lAgeYear;
	@FXML private TextField tfAgeYear;
	@FXML private Label lAgeMonth;
	@FXML private TextField tfAgeMonth;
	@FXML private Label lAgeDay;
	@FXML private TextField tfAgeDay;
	@FXML private TextField tfDOB_Day;
	@FXML private TextField tfDOB_Month;
	@FXML private TextField tfDOB_Year;
	@FXML private ToggleGroup tgSex;
	@FXML private RadioButton rbM;
	@FXML private RadioButton rbF;
	@FXML private ComboBox<String> cbStatus;
	@FXML private TextField tfTel1;
	@FXML private TextField tfTel2;
	@FXML private TextField tfTel3;
	@FXML private TextArea taAddress;
	@FXML private Button btnFingerprintRegistration;
	@FXML private ImageView patientPhoto;
	@FXML private Button btnUploadPhoto;

	//Vital Signs
	@FXML private TitledPane tpVitalSigns;
	@FXML private TextField tfSystolicBloodPressure;
	@FXML private TextField tfDiastolicBloodPressure;
	@FXML private Button btnBloodPressureGraph;
	@FXML private TextField tfPulseRate;
	@FXML private Button btnPulseRateGraph;
	@FXML private TextField tfRespiratoryRate;
	@FXML private Button btnRespiratoryRateGraph;
	@FXML private TextField tfTemperature;
	@FXML private Button btnTemperatureGraph;
	@FXML private ToggleGroup tgTemperature;
	@FXML private RadioButton rbCelsius;
	@FXML private RadioButton rbFahrenheit;
	@FXML private TextField tfSpo2;
	@FXML private Button btnSpo2Graph;
	@FXML private TextField tfWeight;
	@FXML private Button btnWeightGraph;
	@FXML private TextField tfHeight;
	@FXML private Button btnHeightGraph;
	@FXML private TextField tfLDT_Month;
	@FXML private TextField tfLDT_Year;

	//Chief Complaint
	@FXML private TitledPane tpChiefComplaint;
	@FXML private ComboBox<String> cbChiefComplaint;
	@FXML private Button btnAddChiefComplaint;
	@FXML private Button btnDeleteChiefComplaint;
	@FXML private TableView<PatientHistory> tvChiefComplaintTable;
	@FXML private TableColumn<PatientHistory, String> tbCCDate;
	@FXML private TableColumn<PatientHistory, String> tbChiefComplaint;

	//Previous Medical History and Screening
	@FXML private TitledPane tpPMHScreening;
	@FXML private ComboBox<String> cbPMH;
	@FXML private Button btnAddPMH;
	@FXML private Button btnDeletePMH;
	@FXML private CheckBox cbHyperTension;
	@FXML private CheckBox cbDiabetes;
	@FXML private CheckBox cbAsthma;
	@FXML private CheckBox cbTuberculosis;
	@FXML private CheckBox cbHepA;
	@FXML private CheckBox cbHepB;
	@FXML private CheckBox cbMalaria;
	@FXML private CheckBox cbHIV;
	@FXML private TableView<PatientHistory> tvPMHTable;
	@FXML private TableColumn<PatientHistory, String> tbPMHDisease;
	@FXML private TableColumn<PatientHistory, String> tbPMHStartDate;
	@FXML private TableColumn<PatientHistory, String> tbPMHRemissionDate;
	@FXML private TableColumn<PatientHistory, Boolean> tbPMHIsUnderTreatment;
	@FXML private CheckBox cbTobacco;
	@FXML private CheckBox cbEthanol;
	@FXML private CheckBox cbDrug;
	@FXML private CheckBox cbExSmoker;
	@FXML private CheckBox cbExDrug;
	@FXML private TextField tfOtherScreening;

	//Drug History and Allergy
	@FXML private TitledPane tpDHAllergy;
	@FXML private ComboBox<String> cbDrugHistory;
	@FXML private Button btnAddDrugHistory;
	@FXML private Button btnDeleteDrugHistory;
	@FXML private TableView<PatientHistory> tvDrugHistoryTable;
	@FXML private TableColumn<PatientHistory, String> tbDHDate;
	@FXML private TableColumn<PatientHistory, String> tbDrugHistory;
	@FXML private ComboBox<String> cbAllergy;
	@FXML private Button btnAddAllergy;
	@FXML private Button btnDeleteAllergy;
	@FXML private TableView<PatientHistory> tvAllergyTable;
	@FXML private TableColumn<PatientHistory, String> tbAllergyDate;
	@FXML private TableColumn<PatientHistory, String> tbAllergy;
	
	//Pregnancy(Female Only)
	@FXML private TitledPane tpFemale;
	@FXML private TextField tfLMP_Day;
	@FXML private TextField tfLMP_Month;
	@FXML private TextField tfLMP_Year;
	@FXML private CheckBox cbPregnant;
	@FXML private TextField tfGestation;
	@FXML private CheckBox cbBreastFeeding;
	@FXML private CheckBox cbContraceptive;
	@FXML private TextField tfNumOfPregnancy;
	@FXML private TextField tfNumOfLiveBirth;
	@FXML private TextField tfNumOfMiscarriage;
	@FXML private TextField tfNumOfAbortion;
	@FXML private TextField tfNumOfStillBirth;
	@FXML private TextField tfPregnancyOtherInfo;

	//Submission
	@FXML private TitledPane tpSubmission;
	@FXML private Pane submissionPane;
	@FXML private Label summaryLabel;
	@FXML private Button btnSubmissionTopBack;
	@FXML private Button btnSubmitTop;
	@FXML private Button btnOKTop;
	@FXML private Label summaryInstructionLabel;
	@FXML private Button btnSubmissionBack;
	@FXML private Button btnSubmit;
	@FXML private Button btnOK;

	private TriagePanelController panelCtrl;
	private boolean isNewPatient = true;
	private long patientID = -1, visitID = -1, triageID = -1, femaleID = -1;
	private int slumID = -1, userID;
	private Calendar dob, lmp, ldt;
	private int dob_day, dob_month, dob_year, lmp_day, lmp_month, lmp_year, ldt_month, ldt_year;
	private String tabID;
	private boolean isAgeChanging = false, isDOBChanging = false, isTpPersonalDataOpening = false;
	private boolean isTobacco = false, isEthanol = false, isDrug = false, isExSmoker = false, isExDrug = false, isOtherScreening = false;
	private ArrayList<PatientHistory> initScreeningList;
	private ArrayList<PatientHistory> deletedHistoryList;
	private TitledPane curTitledPane = null;
	private int yPosition;
	//private long CCIndex = -1, DHIndex = -1, AllergyIndex = -1;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		userID = User.getCurrentUser().getid();
		deletedHistoryList = new ArrayList<PatientHistory>();
		setTitledPaneListeners();
		initPersonalDataPane();
		initVitalSignsPane();
		initChiefComplaintPane();
		initPMHPane();
		initDHAllergyPane();
		initFemalePane();
		aTriagePatient.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpPersonalData.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpVitalSigns.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpChiefComplaint.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpPMHScreening.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpDHAllergy.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpFemale.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpSubmission.addEventHandler(KeyEvent.KEY_PRESSED, this);
	}

	public void showGraph( String title, String yAxisLabel, String... dataName )
	{
		Stage stage = new Stage();
		stage.setTitle(title);
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel(yAxisLabel);
		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(title);

		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = null;
		ArrayList<ChartData<String, Number>> chartData1 = DatabaseController.getChartData(dataName[0], patientID);
		
		series1.setName(dataName[0]);
		for( int i = 0; i < chartData1.size(); i++ )
			series1.getData().add(new XYChart.Data<>(chartData1.get(i).getX(), chartData1.get(i).getY()));

		if( dataName.length > 1 )
		{
			series2 = new XYChart.Series<>();
			series2.setName(dataName[1]);
			ArrayList<ChartData<String, Number>> chartData2 = DatabaseController.getChartData(dataName[1], patientID);
			for( int i = 0; i < chartData2.size(); i++ )
				series2.getData().add(new XYChart.Data<>(chartData2.get(i).getX(), chartData2.get(i).getY()));
			lineChart.setLegendVisible(true);
		}

		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series1);
		if( series2 != null )
		{
			lineChart.getData().add(series2);

			Node lineColor = lineChart.lookup(".default-color1.chart-series-line");
			lineColor.setStyle("-fx-stroke: blue;");
			
			Set<Node> nodes = lineChart.lookupAll(".default-color1.chart-line-symbol");
			for (Node n : nodes)
				n.setStyle("-fx-background-color: blue, white;");
			long i = 0;
			for (Node node : lineChart.lookupAll(".chart-legend-item"))
			    if (node instanceof Label && ((Label) node).getGraphic() != null && i++ != 0)
			    	((Label) node).getGraphic().setStyle("-fx-background-color: blue, white;");
		}
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void btnUploadPhotoPressed( ActionEvent Event )
	{
		
	}

	@FXML
	public void btnPersonalDataNextPressed( ActionEvent Event )
	{
		if(!isNecessaryInfoFilled())
			return;
		aTriagePatient.getPanes().remove( tpPersonalData );
		aTriagePatient.getPanes().add( tpVitalSigns );
		tpVitalSigns.setExpanded(true);
	}

	@FXML
	public void btnVitalSignsNextPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpVitalSigns );
		aTriagePatient.getPanes().add( tpChiefComplaint );
		tpChiefComplaint.setExpanded(true);
	}
	
	@FXML
	public void btnChiefComplaintNextPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpChiefComplaint );
		aTriagePatient.getPanes().add( tpPMHScreening );
		tpPMHScreening.setExpanded(true);
	}

	@FXML
	public void btnPMHScreeningNextPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpPMHScreening );
		aTriagePatient.getPanes().add( tpDHAllergy );
		tpDHAllergy.setExpanded(true);
	}

	@FXML
	public void btnDHAllergyNextPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpDHAllergy );
		if( rbF.isSelected() )
		{
			aTriagePatient.getPanes().add( tpFemale );
			tpFemale.setExpanded(true);
		}
		else
		{
			aTriagePatient.getPanes().add( tpSubmission );
			tpSubmission.setExpanded(true);
		}
	}

	@FXML
	public void btnFemaleNextPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpFemale );
		aTriagePatient.getPanes().add( tpSubmission );
		tpSubmission.setExpanded(true);
	}

	@FXML
	public void btnVitalSignsBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpVitalSigns );
		aTriagePatient.getPanes().add( tpPersonalData );
		tpPersonalData.setExpanded(true);
	}
	
	@FXML
	public void btnChiefComplaintBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpChiefComplaint );
		aTriagePatient.getPanes().add( tpVitalSigns );
		tpVitalSigns.setExpanded(true);
	}

	@FXML
	public void btnPMHScreeningBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpPMHScreening );
		aTriagePatient.getPanes().add( tpChiefComplaint );
		tpChiefComplaint.setExpanded(true);
	}

	@FXML
	public void btnDHAllergyBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpDHAllergy );
		aTriagePatient.getPanes().add( tpPMHScreening );
		tpPMHScreening.setExpanded(true);
	}

	@FXML
	public void btnFemaleBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpFemale );
		aTriagePatient.getPanes().add( tpDHAllergy );
		tpDHAllergy.setExpanded(true);
	}

	@FXML
	public void btnSubmissionBackPressed( ActionEvent Event )
	{
		aTriagePatient.getPanes().remove( tpSubmission );
		if( rbF.isSelected() )
		{
			aTriagePatient.getPanes().add( tpFemale );
			tpFemale.setExpanded(true);
		}
		else
		{
			aTriagePatient.getPanes().add( tpDHAllergy );
			tpDHAllergy.setExpanded(true);
		}
		
	}

	@FXML
	public void btnFingerprintRegistrationPressed( ActionEvent Event )
	{
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("C://sight/Registered.txt");
			PrintWriter writer = new PrintWriter(out);
			writer.write(patientID+"");
			writer.close();
			out.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(TriagePatientController.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(TriagePatientController.class.getName()).log(Level.SEVERE, null, ex);
		}
		String msg = "\n\nPatient Name: " + (this.tfLastName == null ? "" : ( this.tfLastName.getText().trim()+" " )) + (this.tfFirstName == null ? "" : ( this.tfFirstName.getText().trim()+"" ));
		msg += "\n\nPlease get ready for fingerprint!";
		promptMessage("One-2-One Medical System", "Fingerprint Registration", msg, AlertType.INFORMATION, 16);
	}

	@FXML
	public void btnBloodPressureGraphPressed( ActionEvent Event )
	{
		showGraph( "Blood Pressure Graph", "Blood Pressure/mm Hg", "SBP", "DBP" );
	}

	@FXML
	public void btnPulseRateGraphPressed( ActionEvent Event )
	{
		showGraph( "Pulse Rate Graph", "Pulse Rate/bpm", "PR" );
	}

	@FXML
	public void btnRespiratoryRateGraphPressed( ActionEvent Event )
	{
		showGraph( "Respiratory Rate Graph", "Respiratory Rate/cmn", "RR" );
	}

	@FXML
	public void btnTemperatureGraphPressed( ActionEvent Event )
	{
		showGraph( "Temperature Graph", "Temperature/\u00b0C", "Temperature" );
	}

	@FXML
	public void btnSpo2GraphPressed( ActionEvent Event )
	{
		showGraph( "Spo2 Graph", "Spo2/%", "Spo2" );
	}

	@FXML
	public void btnWeightGraphPressed( ActionEvent Event )
	{
		showGraph( "Weight Graph", "Weight/kg", "Weight" );
	}

	@FXML
	public void btnHeightGraphPressed( ActionEvent Event )
	{
		showGraph( "Height Graph", "Height/cm", "Height" );
	}

	@FXML
	public void btnAddChiefComplaintPressed( ActionEvent Event )
	{
		ObservableList<PatientHistory> currentCCList = tvChiefComplaintTable.getItems();
		if( btnAddChiefComplaint.getText().equals( "Add" ) )
		{
			/*if( tfChiefComplaint.getText().trim().equals("") )
				return;*/
			if( cbChiefComplaint.getEditor().getText().trim().equals("") )
				return;
			ObservableList<PatientHistory> newCCList = FXCollections.observableArrayList();
			//newCCList.add( new PatientHistory( tfChiefComplaint.getText().trim(), PatientHistory.TYPE_CC ) );
			newCCList.add(new PatientHistory( cbChiefComplaint.getEditor().getText().trim(), PatientHistory.TYPE_CC, visitID ));
			newCCList.addAll( currentCCList );
			btnAddChiefComplaint.setText( "Update" );
			tvChiefComplaintTable.setItems( newCCList );
		}
		else
		{
			//currentCCList.get( 0 ).setdescription( tfChiefComplaint.getText().trim() );
			for(PatientHistory cc: currentCCList)
				if(cc.getvisitID() == visitID || cc.getvisitID() == -1)
					cc.setdescription(cbChiefComplaint.getEditor().getText().trim());
			//currentCCList.get( 0 ).setdescription( cbChiefComplaint.getEditor().getText().trim() );
			tvChiefComplaintTable.setItems( currentCCList );
		}
		//tvChiefComplaintTable.setEditable( true );
		//tvChiefComplaintTable.autosize();
	}

	@FXML
	public void btnDeleteChiefComplaintPressed( ActionEvent Event )
	{
		PatientHistory ph = tvChiefComplaintTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentPMHList = tvChiefComplaintTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentPMHList.remove(ph);
			tvChiefComplaintTable.setItems( currentPMHList );
			btnAddChiefComplaint.setText( "Add" );
			cbChiefComplaint.getEditor().setText("");
		}
	}

	@FXML
	public void btnAddPMHPressed( ActionEvent Event )
	{
		if( cbPMH.getEditor().getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentPMHList = tvPMHTable.getItems();
		currentPMHList.add( new PatientHistory( cbPMH.getEditor().getText().trim(), PatientHistory.TYPE_PMH, visitID ) );
		tvPMHTable.setItems( currentPMHList );
		tvPMHTable.setEditable( true );
		cbPMH.getEditor().clear();
	}

	@FXML
	public void btnDeletePMHPressed( ActionEvent Event )
	{
		PatientHistory ph = tvPMHTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentPMHList = tvPMHTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentPMHList.remove(ph);
			tvPMHTable.setItems( currentPMHList );
		}
	}

	@FXML
	public void btnAddDrugHistoryPressed( ActionEvent Event )
	{
		if( cbDrugHistory.getEditor().getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentDHList = tvDrugHistoryTable.getItems();
		if( btnAddDrugHistory.getText().equals( "Add" ) )
		{
			ObservableList<PatientHistory> newDHList = FXCollections.observableArrayList();
			//newDHList.add( new TextInformation( tfDrugHistory.getText().trim(), new java.sql.Date( (new java.util.Date()).getTime() ), TextInformation.TYPE_DH ) );
			newDHList.add( new PatientHistory( cbDrugHistory.getEditor().getText().trim(), PatientHistory.TYPE_DH, visitID ) );
			newDHList.addAll( currentDHList );
			btnAddDrugHistory.setText( "Update" );
			tvDrugHistoryTable.setItems( newDHList );
		}
		else
		{
			//currentDHList.get( 0 ).setdescription( cbDrugHistory.getEditor().getText().trim() );
			for(PatientHistory dh: currentDHList)
				if(dh.getvisitID() == visitID || dh.getvisitID() == -1)
					dh.setdescription(cbDrugHistory.getEditor().getText().trim());
			tvDrugHistoryTable.setItems( currentDHList );
		}
		//tvDrugHistoryTable.setEditable( true );
	}

	@FXML
	public void btnDeleteDrugHistoryPressed( ActionEvent Event )
	{
		PatientHistory ph = tvDrugHistoryTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentDHList = tvDrugHistoryTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentDHList.remove(ph);
			tvDrugHistoryTable.setItems( currentDHList );
			btnAddDrugHistory.setText( "Add" );
			cbDrugHistory.getEditor().setText("");
		}
	}

	@FXML
	public void btnAddAllergyPressed( ActionEvent Event )
	{
		if( cbAllergy.getEditor().getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentAllergyList = tvAllergyTable.getItems();
		if( btnAddAllergy.getText().equals( "Add" ) )
		{
			ObservableList<PatientHistory> newAllergyList = FXCollections.observableArrayList();
			newAllergyList.add( new PatientHistory( cbAllergy.getEditor().getText().trim(), PatientHistory.TYPE_ALE, visitID ) );
			newAllergyList.addAll( currentAllergyList );
			btnAddAllergy.setText( "Update" );
			tvAllergyTable.setItems( newAllergyList );
		}
		else
		{
			//currentAllergyList.get( 0 ).setdescription( cbAllergy.getEditor().getText().trim() );
			for(PatientHistory allergy: currentAllergyList)
				if(allergy.getvisitID() == visitID || allergy.getvisitID() == -1)
					allergy.setdescription(cbAllergy.getEditor().getText().trim());
			tvAllergyTable.setItems( currentAllergyList );
		}
		//tvAllergyTable.setEditable( true );
	}

	@FXML
	public void btnDeleteAllergyPressed( ActionEvent Event )
	{
		PatientHistory ph = tvAllergyTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentAllergyList = tvAllergyTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentAllergyList.remove(ph);
			tvAllergyTable.setItems( currentAllergyList );
			btnAddAllergy.setText( "Add" );
			cbAllergy.getEditor().setText("");
		}
	}

	@FXML
	public void btnSubmitPressed( ActionEvent Event )
	{
		if( isNecessaryInfoFilled() )
		{
			if( visitID != -1 )//Edit visit
			{
				Patient p = newPatientRecord();
				p.setpatientID(patientID);
				DatabaseController.updatePatient(p);
				TriageRecord tr = newTriageRecord();
				tr.settriageID(triageID);
				DatabaseController.updateTriageRecord( tr );
				if( rbF.isSelected() )
				{
					FemaleRecord fr = newFemaleRecord();
					fr.setfemaleID( femaleID );
					DatabaseController.updateFemaleRecord( fr );
				}

				PatientHistory cc = getNewChiefComplaint();
				if( cc != null )
				{
					if( cc.getpatientHistoryID() == -1 )
						DatabaseController.addPatientHistory( cc );
					else
						DatabaseController.updatePatientHistory( cc );
				}
				ArrayList<PatientHistory> newPMHList = getNewPMHList();
				for( PatientHistory mh : newPMHList )
					DatabaseController.addPatientHistory( mh );
				ArrayList<PatientHistory> oldPMHList = getOldPMHList();
				for( PatientHistory mh : oldPMHList )
					DatabaseController.updatePatientHistory( mh );
				ArrayList<PatientHistory> newScreeningList = getNewScreeningList();
				for( PatientHistory sc : newScreeningList )
					DatabaseController.addPatientHistory( sc );
				ArrayList<PatientHistory> oldScreeningList = getOldScreeningList();
				for( PatientHistory sc : oldScreeningList )
					DatabaseController.updatePatientHistory( sc );
				PatientHistory dh = getNewDrugHistory();
				if( dh != null )
				{
					if( dh.getpatientHistoryID() == -1 )
						DatabaseController.addPatientHistory( dh );
					else
						DatabaseController.updatePatientHistory( dh );
				}
				PatientHistory al = getNewAllergy();
				if( al != null )
				{
					if( al.getpatientHistoryID() == -1 )
						DatabaseController.addPatientHistory( al );
					else
						DatabaseController.updatePatientHistory( al );
				}

				for( PatientHistory mh : deletedHistoryList )
					DatabaseController.removePatientHistory( mh.getpatientHistoryID() );

				String msg = "\n\nPatient Name: " + (this.tfLastName == null ? "" : ( this.tfLastName.getText().trim()+" " )) + (this.tfFirstName == null ? "" : ( this.tfFirstName.getText().trim()+"" ));
				promptMessage("One-2-One Medical System", "Successfully Submitted!\nTag Number: "+DatabaseController.getTagNumber(visitID), msg, AlertType.INFORMATION, 16);
				DatabaseController.updatePatientVisit(visitID, userID, true, false, false);
			}
			else if( isNewPatient )//Add new patient
			{
				Patient p = newPatientRecord();
				patientID = DatabaseController.addPatient( p );
				p.setpatientID( patientID );
				patientIDLabel.setVisible(true);
				patientIDLabel.setText( "ID: " + patientID );
				btnFingerprintRegistration.setVisible(true);
				visitID = DatabaseController.addPatientVisit( patientID, userID, slumID );
				TriageRecord tr = newTriageRecord();
				triageID = DatabaseController.addTriageRecord( tr );
				if( rbF.isSelected() )
				{
					FemaleRecord fr = newFemaleRecord();
					femaleID = DatabaseController.addFemaleRecord( fr );
				}
				PatientHistory cc = getNewChiefComplaint();
				if( cc != null && cc.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( cc );
				ArrayList<PatientHistory> newMHList = getNewPMHList();
				for( PatientHistory mh : newMHList )
					DatabaseController.addPatientHistory( mh );
				ArrayList<PatientHistory> newScreeningList = getNewScreeningList();
				for( PatientHistory sc : newScreeningList )
					DatabaseController.addPatientHistory( sc );
				PatientHistory dh = getNewDrugHistory();
				if( dh != null && dh.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( dh );
				PatientHistory al = getNewAllergy();
				if( al != null && al.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( al );

				FileOutputStream out = null;
				try {
					//out = new FileOutputStream("C://Users//khl//Desktop//BMED 4950B//FingerPrint//Registered.txt");
					out = new FileOutputStream("C://sight/Registered.txt");
					PrintWriter writer = new PrintWriter(out);
					writer.write(patientID+"");
					writer.close();
					out.close();
				} catch (FileNotFoundException ex) {
					Logger.getLogger(TriagePatientController.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(TriagePatientController.class.getName()).log(Level.SEVERE, null, ex);
				}
				String msg = "\n\nPatient Name: " + (this.tfLastName == null ? "" : ( this.tfLastName.getText().trim()+" " )) + (this.tfFirstName == null ? "" : ( this.tfFirstName.getText().trim()+"" ));
				msg += "\n\nPlease get ready for fingerprint!";
				promptMessage("One-2-One Medical System", "Successfully Submitted!\nTag Number: "+DatabaseController.getTagNumber(visitID), msg, AlertType.INFORMATION, 18);
			}
			else//Old patient(not 1st visit)
			{
				Patient p = newPatientRecord();
				p.setpatientID( patientID );
				DatabaseController.updatePatient(p);
				visitID = DatabaseController.addPatientVisit( patientID, userID, slumID );
				TriageRecord tr = newTriageRecord();
				triageID = DatabaseController.addTriageRecord( tr );
				if( rbF.isSelected() )
				{
					FemaleRecord fr = newFemaleRecord();
					fr.setfemaleID( femaleID );
					DatabaseController.updateFemaleRecord( fr );
				}
				PatientHistory cc = getNewChiefComplaint();
				if( cc != null )
					if( cc.getpatientHistoryID() == -1 )
						DatabaseController.addPatientHistory( cc );
				ArrayList<PatientHistory> newMHList = getNewPMHList();
				for( PatientHistory mh : newMHList )
					DatabaseController.addPatientHistory( mh );
				ArrayList<PatientHistory> oldMHList = getOldPMHList();
				for( PatientHistory mh : oldMHList )
					DatabaseController.updatePatientHistory( mh );
				ArrayList<PatientHistory> newScreeningList = getNewScreeningList();
				for( PatientHistory sc : newScreeningList )
					DatabaseController.addPatientHistory( sc );
				ArrayList<PatientHistory> oldScreeningList = getOldScreeningList();
				for( PatientHistory sc : oldScreeningList )
					DatabaseController.updatePatientHistory( sc );
				PatientHistory dh = getNewDrugHistory();
				if( dh != null && dh.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( dh );
				PatientHistory al = getNewAllergy();
				if( al != null && al.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( al );

				String msg = "\n\nPatient Name: " + (this.tfLastName == null ? "" : ( this.tfLastName.getText().trim()+" " )) + (this.tfFirstName == null ? "" : ( this.tfFirstName.getText().trim()+"" ));
				promptMessage("One-2-One Medical System", "Successfully Submitted!\nTag Number: "+DatabaseController.getTagNumber(visitID), msg, AlertType.INFORMATION, 16);
			}
			panelCtrl.finishPatient();
		}
	}

	@FXML
	public void btnOKPressed( ActionEvent Event )
	{
		panelCtrl.finishPatient();
	}

	private boolean isNecessaryInfoFilled()
	{
		boolean isOkay = (tfFirstName != null && !tfFirstName.getText().trim().isEmpty()) || (tfLastName != null && !tfLastName.getText().trim().isEmpty());// || (tfMiddleName != null && !tfMiddleName.getText().trim().isEmpty())
		if( !isOkay )
		{
			promptMessage("One-2-One Medical System", "Missing Information!", "Please fill name of the patient!", AlertType.ERROR, 16);
			return false;
		}
		isOkay = rbM.isSelected() || rbF.isSelected();
		if( !isOkay )
		{
			promptMessage("One-2-One Medical System", "Missing Information!", "Please indicate the sex of the patient!", AlertType.ERROR, 16);
			return false;
		}
		prepareDateFields();
		if( !TableEditingTextCell.isDateValid( dob_day, dob_month + 1, dob_year ) )
		{
			promptMessage("One-2-One Medical System", "Invalid Information!", "Invalid D.O.B.", AlertType.ERROR, 16);
			return false;
		}
		//if( tfTel1.getText() != null && !tfTel1.getText().trim().isEmpty() && !tfTel1.getText().matches("\\d{3}-\\d{3}-\\d{4}") )
		boolean b1 = tfTel1.getText().trim().isEmpty();
		boolean b2 = tfTel2.getText().trim().isEmpty();
		boolean b3 = tfTel3.getText().trim().isEmpty();
		//if( (!b1||!b2||!b3) && (b1||b2||b3) )
		if( b1&&b2&&b3 )
			return true;
		else if( !b1&&!b2&&!b3 )
		{
			if(tfTel1.getText().trim().length() == 3 && tfTel2.getText().trim().length() == 3 && ( tfTel3.getText().trim().length() == 3 || tfTel3.getText().trim().length() == 4 ))
				return true;
			promptMessage("One-2-One Medical System", "Invalid Information!", "Please input a valid telephone number.", AlertType.ERROR, 16);
			return false;
		}
		else
		{
			promptMessage("One-2-One Medical System", "Invalid Information!", "Please input a valid telephone number.", AlertType.ERROR, 16);
			return false;
		}
	}

	private void prepareDateFields()
	{
		//Date of Birth
		if( tfDOB_Day.getText() == null || tfDOB_Day.getText().trim().isEmpty() )
			dob_day = 1;
		else
			dob_day = Integer.parseInt( tfDOB_Day.getText() );
		if( tfDOB_Month.getText() == null || tfDOB_Month.getText().trim().isEmpty() )
			dob_month = 0;
		else
			dob_month = Integer.parseInt( tfDOB_Month.getText() ) - 1;
		if( tfDOB_Year.getText() == null || tfDOB_Year.getText().trim().isEmpty() )
			dob_year = 2015;
		else
			dob_year = Integer.parseInt( tfDOB_Year.getText() );
		dob = Calendar.getInstance();
		dob.set( dob_year, dob_month, dob_day );

		//LMP Date
		if( rbF.isSelected() )
		{
			if( tfLMP_Day.getText() == null || tfLMP_Day.getText().trim().isEmpty() || tfLMP_Month.getText() == null || tfLMP_Month.getText().trim().isEmpty() || tfLMP_Year.getText() == null || tfLMP_Year.getText().trim().isEmpty() )
				lmp = null;
			else
			{
				lmp_day = Integer.parseInt( tfLMP_Day.getText() );
				lmp_month = Integer.parseInt( tfLMP_Month.getText() ) - 1;
				lmp_year = Integer.parseInt( tfLMP_Year.getText() );
				lmp = Calendar.getInstance();
				lmp.set( lmp_year, lmp_month, lmp_day );
			}
		}

		//LDT
		if( tfLDT_Month.getText() == null || tfLDT_Month.getText().trim().isEmpty() || tfLDT_Year.getText() == null || tfLDT_Year.getText().trim().isEmpty() )
			ldt = null;
		else
		{
			ldt_month = Integer.parseInt( tfLDT_Month.getText() ) - 1;
			ldt_year = Integer.parseInt( tfLDT_Year.getText() );
			ldt = Calendar.getInstance();
			ldt.set( ldt_year, ldt_month, 1 );
		}
	}

	private Patient newPatientRecord()
	{
		Patient p = new Patient();
		//Personal Data
		p.setfirstName( tfFirstName.getText().trim() );
		p.setmiddleName( tfMiddleName.getText().trim() );
		p.setlastName( tfLastName.getText().trim() );
		//TODO: Upload photo
		//File photo = new File( "./src/sight/Patient_Photo.jpg" );
		//By Lance
		//File photo = new File( "Patient_Photo.jpg" );
		//p.setphoto( photo );
		if( rbM.isSelected() )
			p.setsex( 'M' );
		else
			p.setsex( 'F' );
		if(dob != null)
		{
			p.setDOB( new java.sql.Date(dob.getTimeInMillis()) );
			p.setage( new java.sql.Date(dob.getTimeInMillis()) );
		}
		p.setstatus( cbStatus.getValue() );
		if ( tfTel1.getText().trim().isEmpty() )
			p.settelNo( "" );
		else
			p.settelNo( tfTel1.getText().trim()+tfTel2.getText().trim()+tfTel3.getText().trim() );
		if ( taAddress.getText() == null || taAddress.getText().trim().isEmpty() )
			p.setaddress( "" );
		else
			p.setaddress( taAddress.getText().trim() );
		
		Calendar c = Calendar.getInstance();
		c.setTime( new java.util.Date() );
		//p.setlastUpdate( new Date( c.getTimeInMillis() ) );
		p.setslumID( slumID );
		p.setuserID( userID );
		return p;
	}

	public TriageRecord newTriageRecord()
	{
		TriageRecord tr = new TriageRecord();
		if( tfTemperature.getText() == null || tfTemperature.getText().trim().isEmpty() )
			tr.settemperature( 0.0F );
		else
			tr.settemperature( Float.parseFloat( tfTemperature.getText().trim() ) );
		if( rbCelsius.isSelected() )
			tr.settemperatureScale('C');
		else
			tr.settemperatureScale('F');
		if( tfSystolicBloodPressure.getText() == null || tfSystolicBloodPressure.getText().trim().isEmpty() )
			tr.setSBP( 0 );
		else
			tr.setSBP( Integer.parseInt( tfSystolicBloodPressure.getText().trim() ) );
		if( tfDiastolicBloodPressure.getText() == null || tfDiastolicBloodPressure.getText().trim().isEmpty() )
			tr.setDBP( 0 );
		else
			tr.setDBP( Integer.parseInt( tfDiastolicBloodPressure.getText().trim() ) );
		if( tfPulseRate.getText() == null || tfPulseRate.getText().trim().isEmpty() )
			tr.setPR( 0 );
		else
			tr.setPR( Integer.parseInt( tfPulseRate.getText().trim() ) );
		if( tfRespiratoryRate.getText() == null || tfRespiratoryRate.getText().trim().isEmpty() )
			tr.setRR( 0 );
		else
			tr.setRR( Integer.parseInt( tfRespiratoryRate.getText().trim() ) );
		if( tfSpo2.getText() == null || tfSpo2.getText().trim().isEmpty() )
			tr.setSpo2( 0.0F );
		else
			tr.setSpo2( Float.parseFloat( tfSpo2.getText().trim() ) );
		if( tfWeight.getText() == null || tfWeight.getText().trim().isEmpty() )
			tr.setweight( 0.0F );
		else
			tr.setweight( Float.parseFloat( tfWeight.getText().trim() ) );
		if( tfHeight.getText() == null || tfHeight.getText().trim().isEmpty() )
			tr.setheight( 0.0F );
		else
			tr.setheight( Float.parseFloat( tfHeight.getText().trim() ) );
		if( ldt != null )
			tr.setLDT( new java.sql.Date(ldt.getTimeInMillis()) );
		else
			tr.setLDT(null);
		tr.setvisitID( visitID );
		tr.setuserID( userID );
		return tr;
	}

	public FemaleRecord newFemaleRecord()
	{
		FemaleRecord fr = new FemaleRecord();
		if( lmp == null )
			fr.setLMP(null);
		else
			fr.setLMP( new java.sql.Date(lmp.getTimeInMillis()) );
		fr.setisPregnant( cbPregnant.isSelected() );
		if ( tfGestation.getText() == null || tfGestation.getText().trim().isEmpty() )
			fr.setgestation( 0 );
		else
			fr.setgestation( Integer.parseInt( tfGestation.getText().trim() ) );
		fr.setisBreastFeeding( cbBreastFeeding.isSelected() );
		fr.setisContraceptive( cbContraceptive.isSelected() );
		if ( tfNumOfPregnancy.getText() == null || tfNumOfPregnancy.getText().trim().isEmpty() )
			fr.setnumOfPregnancy( 0 );
		else
			fr.setnumOfPregnancy( Integer.parseInt( tfNumOfPregnancy.getText().trim() ) );
		if ( tfNumOfLiveBirth.getText() == null || tfNumOfLiveBirth.getText().trim().isEmpty() )
			fr.setnumOfLiveBirth( 0 );
		else
			fr.setnumOfLiveBirth( Integer.parseInt( tfNumOfLiveBirth.getText().trim() ) );
		if ( tfNumOfMiscarriage.getText() == null || tfNumOfMiscarriage.getText().trim().isEmpty() )
			fr.setnumOfMiscarriage( 0 );
		else
			fr.setnumOfMiscarriage( Integer.parseInt( tfNumOfMiscarriage.getText().trim() ) );
		if ( tfNumOfAbortion.getText() == null || tfNumOfAbortion.getText().trim().isEmpty() )
			fr.setnumOfAbortion( 0 );
		else
			fr.setnumOfAbortion( Integer.parseInt( tfNumOfAbortion.getText().trim() ) );
		if ( tfNumOfStillBirth.getText() == null || tfNumOfStillBirth.getText().trim().isEmpty() )
			fr.setnumOfStillBirth( 0 );
		else
			fr.setnumOfStillBirth( Integer.parseInt( tfNumOfStillBirth.getText().trim() ) );
		if ( tfPregnancyOtherInfo.getText() == null || tfPregnancyOtherInfo.getText().trim().isEmpty() )
			fr.setotherInfo( "" );
		else
			fr.setotherInfo( tfPregnancyOtherInfo.getText().trim() );
		fr.setpatientID( patientID );
		fr.setuserID( userID );
		return fr;
	}

	public PatientHistory getThisVisitChiefComplaint()
	{
		ObservableList<PatientHistory> ccList = tvChiefComplaintTable.getItems();
		for(PatientHistory cc : ccList)
			if( cc.getpatientHistoryID() == -1 || cc.getvisitID() == visitID )
				return cc;
		return null;
	}

	public PatientHistory getNewChiefComplaint()
	{
		ObservableList<PatientHistory> phList = tvChiefComplaintTable.getItems();
		if( phList.size() == 0 )
			return null;
		PatientHistory cc;
		for(int i = 0; i < phList.size(); i++)
		{
			cc = tvChiefComplaintTable.getItems().get(i);
			if( cc.getpatientHistoryID() == -1 )
			{
				cc.setstartDateString( (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new java.util.Date()) );
				cc.sethistoryType( PatientHistory.TYPE_CC );
				cc.setpatientID( patientID );
				cc.setuserID( userID );
				cc.setvisitID( visitID );
				return cc;
			}
		}
		return null;
	}

	public ArrayList<PatientHistory> getNewPMHList()
	{
		ArrayList<PatientHistory> newPMHList = new ArrayList<>();
		ObservableList<PatientHistory> currentMHList = tvPMHTable.getItems();
		for( PatientHistory mh : currentMHList )
		{
			if( mh.getpatientHistoryID() != -1 )//New PH has a ID = -1
				continue;
			mh.sethistoryType( PatientHistory.TYPE_PMH );
			mh.setpatientID( patientID );
			mh.setuserID( userID );
			mh.setvisitID( visitID );
			newPMHList.add( mh );
		}
		return newPMHList;
	}

	public ArrayList<PatientHistory> getOldPMHList()
	{
		ArrayList<PatientHistory> oldPMHList = new ArrayList<>();
		ObservableList<PatientHistory> currentMHList = tvPMHTable.getItems();
		for( PatientHistory mh : currentMHList )
		{
			/*if( mh.getpatientHistoryID() == -1 )
				continue;
			mh.setuserID( userID );
			mh.setvisitID( visitID );*/
			if(mh.getpatientHistoryID() != -1 && mh.isChanged())
				oldPMHList.add( mh );
		}
		return oldPMHList;
	}

	public ArrayList<PatientHistory> getNewScreeningList()
	{
		ArrayList<PatientHistory> newScreeningList = new ArrayList<>();
		if( cbTobacco.isSelected() && !isTobacco )
			newScreeningList.add( new PatientHistory( PatientHistory.SCN_TO, PatientHistory.TYPE_SCR, patientID, visitID, userID, true ) );
		if( cbEthanol.isSelected() && !isEthanol )
			newScreeningList.add( new PatientHistory( PatientHistory.SCN_ETH, PatientHistory.TYPE_SCR, patientID, visitID, userID, true ) );
		if( cbDrug.isSelected() && !isDrug )
			newScreeningList.add( new PatientHistory( PatientHistory.SCN_DRUG, PatientHistory.TYPE_SCR, patientID, visitID, userID, true ) );
		if( cbExSmoker.isSelected() && !isExSmoker )
			newScreeningList.add( new PatientHistory( PatientHistory.SCN_EX_TO, PatientHistory.TYPE_SCR, patientID, visitID, userID, true ) );
		if( cbExDrug.isSelected() && !isExDrug )
			newScreeningList.add( new PatientHistory( PatientHistory.SCN_EX_DRUG, PatientHistory.TYPE_SCR, patientID, visitID, userID, true ) );
		if ( tfOtherScreening.getText() != null && !tfOtherScreening.getText().trim().isEmpty() && !isOtherScreening )
		{
			PatientHistory ph = new PatientHistory( tfOtherScreening.getText().trim(), PatientHistory.TYPE_SCR, patientID, visitID, userID, true );
			ph.setremarks("other");
			newScreeningList.add( ph );
		}
		return newScreeningList;
	}

	public ArrayList<PatientHistory> getOldScreeningList()
	{
		ArrayList<PatientHistory> oldScreeningList = new ArrayList<>();
		if(initScreeningList == null)
			return oldScreeningList;
		for( PatientHistory sc : initScreeningList )
		//for( int i = initScreeningList.size() - 1; i >= 0; i-- )
		{
			//PatientHistory sc = initScreeningList.get(i);
			switch( sc.getdescription() )
			{
				case PatientHistory.SCN_TO:
					if( !cbTobacco.isSelected() )
					{
						if(sc.getvisitID() == visitID)
						{
							sc.setisActive(false);
							deletedHistoryList.add(sc);
						}
						else
						{
							sc.setisUnderTreatment(false);
							sc.setremissionDateString(PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()));
							oldScreeningList.add(sc);
						}
					}
					break;
				case PatientHistory.SCN_ETH:
					if( !cbEthanol.isSelected() )
					{
						if(sc.getvisitID() == visitID)
						{
							sc.setisActive(false);
							deletedHistoryList.add(sc);
						}
						else
						{
							sc.setisUnderTreatment(false);
							sc.setremissionDateString(PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()));
							oldScreeningList.add(sc);
						}
					}
					break;
				case PatientHistory.SCN_DRUG:
					if( !cbDrug.isSelected() )
					{
						if(sc.getvisitID() == visitID)
						{
							sc.setisActive(false);
							deletedHistoryList.add(sc);
						}
						else
						{
							sc.setisUnderTreatment(false);
							sc.setremissionDateString(PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()));
							oldScreeningList.add(sc);
						}
					}
					break;
				case PatientHistory.SCN_EX_TO:
					if( !cbExSmoker.isSelected() )
					{
						if(sc.getvisitID() == visitID)
						{
							sc.setisActive(false);
							deletedHistoryList.add(sc);
						}
						else
						{
							sc.setisUnderTreatment(false);
							sc.setremissionDateString(PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()));
							oldScreeningList.add(sc);
						}
					}
					break;
				case PatientHistory.SCN_EX_DRUG:
					if( !cbExDrug.isSelected() )
					{
						if(sc.getvisitID() == visitID)
						{
							sc.setisActive(false);
							deletedHistoryList.add(sc);
						}
						else
						{
							sc.setisUnderTreatment(false);
							sc.setremissionDateString(PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()));
							oldScreeningList.add(sc);
						}
					}
					break;
				default:
					if(isOtherScreening)
					{
						sc.setdescription(tfOtherScreening.getText().trim());
						oldScreeningList.add(sc);
					}
					break;
			}
		}
		return oldScreeningList;
	}

	public PatientHistory getThisVisitDrugHistory()
	{
		ObservableList<PatientHistory> dhList = tvDrugHistoryTable.getItems();
		for(PatientHistory dh : dhList)
			if( dh.getpatientHistoryID() == -1 || dh.getvisitID() == visitID )
				return dh;
		return null;
	}

	public PatientHistory getNewDrugHistory()
	{
		ObservableList<PatientHistory> dhList = tvDrugHistoryTable.getItems();
		if( dhList.size() == 0 )
			return null;
		PatientHistory dh;
		for(int i = 0; i < dhList.size(); i++)
		{
			dh = tvDrugHistoryTable.getItems().get( i );
			if( dh.getpatientHistoryID() == -1 )
			{
				dh.sethistoryType( PatientHistory.TYPE_DH );
				dh.setstartDateString( (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new java.util.Date()) );
				dh.setpatientID( patientID );
				dh.setuserID( userID );
				dh.setvisitID( visitID );
				return dh;
			}
		}
		return null;
	}

	public PatientHistory getThisVisitAllergy()
	{
		ObservableList<PatientHistory> allergyList = tvAllergyTable.getItems();
		for(PatientHistory allergy : allergyList)
			if( allergy.getpatientHistoryID() == -1 || allergy.getvisitID() == visitID )
				return allergy;
		return null;
	}

	public PatientHistory getNewAllergy()
	{
		ObservableList<PatientHistory> allergyList = tvAllergyTable.getItems();
		if( allergyList.size() == 0 )
			return null;
		PatientHistory allergy;
		for(int i = 0; i < allergyList.size(); i++)
		{
			allergy = tvAllergyTable.getItems().get( i );
			if( allergy.getpatientHistoryID() == -1 )
			{
				allergy.sethistoryType( PatientHistory.TYPE_ALE );
				allergy.setstartDateString( (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new java.util.Date()) );
				allergy.setpatientID( patientID );
				allergy.setuserID( userID );
				allergy.setvisitID( visitID );
				return allergy;
			}
		}
		return null;
	}

	public void setTriagePanelController( TriagePanelController ctrl )
	{
		this.panelCtrl = ctrl;
	}

	public void setID( String tabID )
	{
		this.tabID = tabID;
	}

	public String getID()
	{
		return tabID;
	}

	private void setTitledPaneListeners()
	{
		tpPersonalData.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
					{
						openTitledPane( tpPersonalData );
						isTpPersonalDataOpening = true;
					}
					else
					{
						if(isTpPersonalDataOpening)
							if(isNecessaryInfoFilled())
							{
								addAllTitledPanes();
								isTpPersonalDataOpening = false;
							}
							else
								tpPersonalData.setExpanded(true);
					}
					});
				}
			}
		);
	
		tpVitalSigns.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpVitalSigns );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpChiefComplaint.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpChiefComplaint );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpPMHScreening.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpPMHScreening );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpDHAllergy.expandedProperty().addListener
		(
				new ChangeListener<Boolean>()
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
					{
						Platform.runLater(() -> {
						if( newValue )
							openTitledPane( tpDHAllergy );
						else
							addAllTitledPanes();
						});
					}
				}
			);

		tpFemale.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpFemale );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpSubmission.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
					{
						openTitledPane( tpSubmission );
						showSummary();
					}
					else
						addAllTitledPanes();
					});
				}
			}
		);
	}

	private void openTitledPane( TitledPane tp )
	{
		if( !tp.equals( tpPersonalData ) && aTriagePatient.getPanes().contains( tpPersonalData ) )
			aTriagePatient.getPanes().remove( tpPersonalData );
		if( !tp.equals( tpVitalSigns ) && aTriagePatient.getPanes().contains( tpVitalSigns ) )
			aTriagePatient.getPanes().remove( tpVitalSigns );
		if( !tp.equals( tpChiefComplaint ) && aTriagePatient.getPanes().contains( tpChiefComplaint ) )
			aTriagePatient.getPanes().remove( tpChiefComplaint );
		if( !tp.equals( tpPMHScreening ) && aTriagePatient.getPanes().contains( tpPMHScreening ) )
			aTriagePatient.getPanes().remove( tpPMHScreening );
		if( !tp.equals( tpDHAllergy ) && aTriagePatient.getPanes().contains( tpDHAllergy ) )
			aTriagePatient.getPanes().remove( tpDHAllergy );
		if( !tp.equals( tpFemale ) && aTriagePatient.getPanes().contains( tpFemale ) )
			aTriagePatient.getPanes().remove( tpFemale );
		if( !tp.equals( tpSubmission ) && aTriagePatient.getPanes().contains( tpSubmission ) )
			aTriagePatient.getPanes().remove( tpSubmission );
		curTitledPane = tp;
	}

	private void addAllTitledPanes()
	{
		if( curTitledPane != null )
			aTriagePatient.getPanes().remove( curTitledPane );
		curTitledPane = null;
		if( !aTriagePatient.getPanes().contains( tpPersonalData ) )
			aTriagePatient.getPanes().add( 0, tpPersonalData );
		if( !aTriagePatient.getPanes().contains( tpVitalSigns ) )
			aTriagePatient.getPanes().add( 1, tpVitalSigns );
		if( !aTriagePatient.getPanes().contains( tpChiefComplaint ) )
			aTriagePatient.getPanes().add( 2, tpChiefComplaint );
		if( !aTriagePatient.getPanes().contains( tpPMHScreening ) )
			aTriagePatient.getPanes().add( 3, tpPMHScreening );
		if( !aTriagePatient.getPanes().contains( tpDHAllergy ) )
			aTriagePatient.getPanes().add( 4, tpDHAllergy );
		//long temp = 5;
		if( !aTriagePatient.getPanes().contains( tpFemale ) )
			aTriagePatient.getPanes().add( 5, tpFemale );
		if( rbM.isSelected())
			tpFemale.setDisable( true );
		if( !aTriagePatient.getPanes().contains( tpSubmission ) )
			aTriagePatient.getPanes().add( 6, tpSubmission );
	}

	private void initPersonalDataPane()
	{
		Calendar now = Calendar.getInstance();
		now.setTime( new java.util.Date() );

		tfFirstName.textProperty().addListener( new TextChangeListener( tfFirstName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		tfMiddleName.textProperty().addListener( new TextChangeListener( tfMiddleName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		tfLastName.textProperty().addListener( new TextChangeListener( tfLastName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		middleNameLabel.setVisible(false);
		tfMiddleName.setVisible(false);

		/**
		 * ChangeListener which is responsible for updating the D.O.B when the age is updated.
		 * @author khl
		 *
		 * @param <T>
		 */
		class AgeFieldChangeListener extends TextChangeListener
		{
			public AgeFieldChangeListener( TextField tfAge, long type )
			{
				super( tfAge, type );
			}
	
			@Override
			public void changed( ObservableValue<? extends String> observable, String oldValue, String newValue )
			{
				super.changed( observable, oldValue, newValue );
				if( isDOBChanging )
					return;
				if( ( newValue == null || newValue.trim().equals( "" ) ) && ( tfAgeYear.getText() == null || tfAgeYear.getText().trim().isEmpty() ) )
					return;
				if( tfAgeYear.getText() != null && !tfAgeYear.getText().trim().isEmpty() )
				{
					if( Integer.parseInt( tfAgeYear.getText().trim() ) == 0 )
					{
						lAgeYear.setVisible(true);
						tfAgeMonth.setVisible(true);
						lAgeMonth.setVisible(true);
						tfAgeDay.setVisible(true);
						lAgeDay.setVisible(true);
					}
					else
					{
						lAgeYear.setVisible(false);
						tfAgeMonth.setVisible(false);
						lAgeMonth.setVisible(false);
					}	
				}

				LocalDate today = LocalDate.now();
				int day = 1, month = 1, year = today.getYear();
				if( tfDOB_Day.getText() != null && !tfDOB_Day.getText().trim().isEmpty() )
					day = Integer.parseInt( tfDOB_Day.getText().trim() );
				else
					tfDOB_Day.setText( "01" );
				if( tfDOB_Month.getText() != null && !tfDOB_Month.getText().trim().isEmpty() )
					month = Integer.parseInt( tfDOB_Month.getText().trim() );
				if( tfDOB_Year.getText() != null && !tfDOB_Year.getText().trim().isEmpty() )
					year = Integer.parseInt( tfDOB_Year.getText().trim() );

				int day2, month2, year2;
				if( !tfAgeDay.isVisible() || tfAgeDay.getText() == null || tfAgeDay.getText().trim().equals( "" ) )
					day2 = 0;
				else
					day2= Integer.parseInt( tfAgeDay.getText().trim() );
				if( !tfAgeMonth.isVisible() || tfAgeMonth.getText() == null || tfAgeMonth.getText().trim().equals( "" ) )
					month2 = 0;
				else
					month2= Integer.parseInt( tfAgeMonth.getText().trim() );
				if( tfAgeYear.getText() == null || tfAgeYear.getText().trim().equals( "" ) )
					year2 = 0;
				else
					year2 = Integer.parseInt( tfAgeYear.getText().trim() );
				Calendar now = Calendar.getInstance();
				now.setTime( new java.util.Date() );
				now.add( Calendar.DAY_OF_MONTH, -1*day2 );
				now.add( Calendar.MONTH, -1*month2 );
				now.add( Calendar.YEAR, -1*year2 );
				LocalDate dob1 = LocalDate.of( year, month, day );
				LocalDate dob2 = LocalDate.of( now.get( Calendar.YEAR ), now.get( Calendar.MONTH )+1, 1 );
				Period p = Period.between( dob1, dob2 );
				
				if( p.getYears() == 0 && p.getMonths() == 0 )
					return;
				isAgeChanging = true;
				tfDOB_Month.setText( "" + dob2.getMonthValue() );
				tfDOB_Year.setText( "" + dob2.getYear() );
				isAgeChanging = false;
				if( observable.toString().contains("tfAgeYear") )
					tfAgeYear.requestFocus();
				else if( observable.toString().contains("tfAgeMonth") )
					tfAgeMonth.requestFocus();
				else if( observable.toString().contains("tfAgeDay") )
					tfAgeDay.requestFocus();
			}
		}//END of AgeFieldChangeListener

		tfAgeYear.textProperty().addListener( new AgeFieldChangeListener( tfAgeYear, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 0, 120 ) );
		tfAgeMonth.textProperty().addListener( new AgeFieldChangeListener( tfAgeMonth, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 0, 11 ) );
		tfAgeDay.textProperty().addListener( new AgeFieldChangeListener( tfAgeDay, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 0, 30 ) );
		lAgeYear.setVisible(false);
		tfAgeMonth.setVisible(false);
		lAgeMonth.setVisible(false);
		tfAgeDay.setVisible(false);
		lAgeDay.setVisible(false);
		
		/**
		 * DOBFieldChangeListener which is responsible for updating the age when the D.O.B. is updated.
		 * @author khl
		 *
		 * @param <T>
		 */
		class DOBFieldChangeListener extends TextChangeListener
		{
			public DOBFieldChangeListener( TextField tfDOB_Year, long type )
			{
				super( tfDOB_Year, type );
			}
	
			@Override
			public void changed( ObservableValue<? extends String> observable, String oldValue, String newValue )
			{
				super.changed( observable, oldValue, newValue );
				if( isAgeChanging || newValue == null || newValue.trim().equals( "" ) || tfDOB_Year == null || tfDOB_Year.getText().trim().equals( "" ) )
					return;
				int day = 1, month = 1, year = Integer.parseInt( tfDOB_Year.getText().trim() );
				if( tfDOB_Day.getText() != null && !tfDOB_Day.getText().trim().isEmpty() )
					day = Integer.parseInt( tfDOB_Day.getText().trim() );
				if( tfDOB_Month.getText() != null && !tfDOB_Month.getText().trim().isEmpty() )
					month = Integer.parseInt( tfDOB_Month.getText().trim() );
	
				LocalDate today = LocalDate.now();
				LocalDate dob = LocalDate.of( year, month, day );
				Period p = Period.between( dob, today );
				isDOBChanging = true;
				tfAgeYear.setText( "" + p.getYears() );
				if( p.getYears() >= 0 && (tfAgeMonth.getText() == null || !tfAgeMonth.getText().trim().isEmpty() || p.getMonths() != 0) )
					tfAgeMonth.setText( "" + p.getMonths() );
				if( p.getYears() >= 0 && (tfAgeDay.getText() == null || !tfAgeDay.getText().trim().isEmpty() || p.getDays() != 0) )
					tfAgeDay.setText( "" + p.getDays() );
				if( p.getYears() == 0 )
				{
					lAgeYear.setVisible(true);
					tfAgeMonth.setVisible(true);
					lAgeMonth.setVisible(true);
					tfAgeDay.setVisible(true);
					lAgeDay.setVisible(true);
				}
				else if( p.getYears() != 0 )
				{
					lAgeYear.setVisible(false);
					tfAgeMonth.setVisible(false);
					lAgeMonth.setVisible(false);
					tfAgeDay.setVisible(false);
					lAgeDay.setVisible(false);
				}
				isDOBChanging = false;
			}
		}//END of DOBFieldChangeListener

		tfDOB_Day.textProperty().addListener( new DOBFieldChangeListener( tfDOB_Day, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 31 ).setMaxLength(2).setNextObject(tfDOB_Month) );
		tfDOB_Month.textProperty().addListener( new DOBFieldChangeListener( tfDOB_Month, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 12 ).setMaxLength(2).setNextObject(tfDOB_Year) );
		tfDOB_Year.textProperty().addListener( new DOBFieldChangeListener( tfDOB_Year, TextChangeListener.TYPE_INT ).setBoundaries( 0, now.get( Calendar.YEAR ) ) );
		tgSex.selectedToggleProperty().addListener
		(
			new ChangeListener<Toggle>()
			{
				@Override
				public void changed( ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle )
				{
					if(rbF.isSelected())
						tpFemale.setDisable( false );
					else
						tpFemale.setDisable( true );
					/*if ( tgSex.getSelectedToggle() != null )
					{
						if( ( (RadioButton)tgSex.getSelectedToggle() ).getText().trim().equals( "M" ) )
							tpFemale.setDisable( true );
						else if( ( (RadioButton)tgSex.getSelectedToggle() ).getText().trim().equals( "F" ) )
							tpFemale.setDisable( false );
					}*/
				}
			}
		);
		cbStatus.getItems().addAll( Patient.STATUS_SINGLE, Patient.STATUS_MARRIED, Patient.STATUS_DIVORCED, Patient.STATUS_WIDOWED );
		cbStatus.getSelectionModel().select( 0 );
		tfTel1.setText("");
		tfTel1.textProperty().addListener( new TextChangeListener( tfTel1, TextChangeListener.TYPE_INT ).setRegEx( "\\d{3}" ).setMaxLength(3).setIsFixedLength(true).setNextObject(tfTel2) );
		tfTel2.setText("");
		tfTel2.textProperty().addListener( new TextChangeListener( tfTel2, TextChangeListener.TYPE_INT ).setRegEx( "\\d{3}" ).setMaxLength(3).setIsFixedLength(true).setNextObject(tfTel3) );
		tfTel3.setText("");
		tfTel3.textProperty().addListener( new TextChangeListener( tfTel3, TextChangeListener.TYPE_INT ).setRegEx( "\\d{4}" ).setMaxLength(4).setIsFixedLength(true) );
		taAddress.textProperty().addListener( new TextChangeListener( taAddress, TextChangeListener.TYPE_TA ).setMaxLength( 256 ) );
		btnFingerprintRegistration.setVisible(false);
		//TODO: PHOTO!
		//Image photo = new Image( "file:"+"./src/sight/Patient_Photo.jpg" );
		//Image photo = new Image( "file:"+"Patient_Photo.jpg" );
		//patientPhoto.setImage( photo );
		btnUploadPhoto.setVisible(false);
	}

	private void initVitalSignsPane()
	{
		Calendar now = Calendar.getInstance();
		now.setTime( new java.util.Date() );
		tfSystolicBloodPressure.textProperty().addListener( new TextChangeListener( tfSystolicBloodPressure, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries(0.0F, 250.0F) );
		tfDiastolicBloodPressure.textProperty().addListener( new TextChangeListener( tfDiastolicBloodPressure, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries(0.0F, 200.0F) );
		tfPulseRate.textProperty().addListener( new TextChangeListener( tfPulseRate, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries(0.0F, 250.0F) );
		tfRespiratoryRate.textProperty().addListener( new TextChangeListener( tfRespiratoryRate, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries(0.0F, 100.0F) );
		TextChangeListener celsiusListener = new TextChangeListener( tfTemperature, TextChangeListener.TYPE_FLOAT ).setAbsoluteBoundaries(0.0F, 60.0F);
		TextChangeListener fahrenheitListener = new TextChangeListener( tfTemperature, TextChangeListener.TYPE_FLOAT ).setAbsoluteBoundaries(0.0F, 140.0F);
		tfTemperature.textProperty().addListener( celsiusListener );
		tgTemperature.selectedToggleProperty().addListener
		(
			new ChangeListener<Toggle>()
			{
				@Override
				public void changed( ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle )
				{
					if ( tgTemperature.getSelectedToggle() != null )
					{
						if( rbCelsius.isSelected() )
						{
							tfTemperature.textProperty().removeListener(fahrenheitListener);
							tfTemperature.textProperty().addListener(celsiusListener);
							if(tfTemperature.getText() != null && !tfTemperature.getText().isEmpty())
							{
								double t = Double.parseDouble(tfTemperature.getText().trim());
								if(t < 0.0 || t > 60.0)
									tfTemperature.setText("");
							}
						}
						else if( rbFahrenheit.isSelected() )
						{
							tfTemperature.textProperty().removeListener(celsiusListener);
							tfTemperature.textProperty().addListener(fahrenheitListener);
							if(tfTemperature.getText() != null && !tfTemperature.getText().isEmpty())
							{
								double t = Double.parseDouble(tfTemperature.getText().trim());
								if(t < 0.0 || t > 140.0)
									tfTemperature.setText("");
							}
						}
					}
				}
			}
		);
		tfSpo2.textProperty().addListener( new TextChangeListener( tfSpo2, TextChangeListener.TYPE_FLOAT ).setAbsoluteBoundaries(0.0F, 100.0F) );
		tfWeight.textProperty().addListener( new TextChangeListener( tfWeight, TextChangeListener.TYPE_FLOAT ).setAbsoluteBoundaries(0.0F, 200.0F) );
		tfHeight.textProperty().addListener( new TextChangeListener( tfHeight, TextChangeListener.TYPE_FLOAT ).setAbsoluteBoundaries(0.0F, 300.0F) );
		tfLDT_Month.textProperty().addListener( new TextChangeListener( tfLDT_Month, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 12 ).setMaxLength(2).setNextObject(tfLDT_Year) );
		tfLDT_Year.textProperty().addListener( new TextChangeListener( tfLDT_Year, TextChangeListener.TYPE_INT ).setBoundaries( 1900, now.get( Calendar.YEAR ) ) );
	}

	private void initChiefComplaintPane()
	{
		cbChiefComplaint.setEditable(true);
		ArrayList<String> keywords = DatabaseController.getKeywordList(PatientHistory.TYPE_CC);
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbChiefComplaint.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbChiefComplaint, keywords ) );
		tvChiefComplaintTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbCCDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		//tbCCDate.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfCCDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvChiefComplaintTable, "Chief Complaint", (int)tbCCDate.getPrefWidth(), "");
			}
		};
		tbCCDate.setCellFactory( cfCCDate );
		tbChiefComplaint.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbChiefComplaint.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfChiefComplaint =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvChiefComplaintTable, "Chief Complaint", (int)tbChiefComplaint.getPrefWidth(), "");
			}
		};
		tbChiefComplaint.setCellFactory( cfChiefComplaint );
	}

	private void initPMHPane()
	{
		cbPMH.setEditable(true);
		ArrayList<String> keywords = DatabaseController.getKeywordList(PatientHistory.TYPE_CC);
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbPMH.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbPMH, keywords ) );
		tvPMHTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvPMHTable.setEditable(true);
		tbPMHDisease.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbDisease.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPMHDisease =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_DATE_TEXT, tvPMHTable, "Previous Medical History", (int)tbPMHDisease.getPrefWidth(), "");
			}
		};
		tbPMHDisease.setCellFactory( cfPMHDisease );
		tbPMHDisease.setEditable( false );
		tbPMHStartDate.setCellValueFactory( new PropertyValueFactory<>("startDateString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPMHStartDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_DATE_TEXT, tvPMHTable, "Previous Medical History", (int)tbPMHStartDate.getPrefWidth(), "StartDate");
			}
		};
		tbPMHStartDate.setCellFactory( cfPMHStartDate );
		tbPMHRemissionDate.setCellValueFactory( new PropertyValueFactory<>("remissionDateString") );
		//tbRemissionDate.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPMHRemissionDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_DATE_TEXT, tvPMHTable, "Previous Medical History", (int)tbPMHRemissionDate.getPrefWidth(), "RemissionDate");
			}
		};
		tbPMHRemissionDate.setCellFactory( cfPMHRemissionDate );
		tbPMHIsUnderTreatment.setCellValueFactory( new PropertyValueFactory<>("isUnderTreatment") );
		tbPMHIsUnderTreatment.setCellFactory( CheckBoxTableCell.forTableColumn(tbPMHIsUnderTreatment) );
		
		/**
		 * ChangeListener which is responsible for updating the list in medical history table when the checkboxes are checked.
		 * @author khl
		 *
		 * @param <T>
		 */
		class PreviousMedicalHistoryCheckBoxListener<T> implements ChangeListener<T>
		{
			private javafx.scene.control.CheckBox cb;
			private String desc;

			public PreviousMedicalHistoryCheckBoxListener( javafx.scene.control.CheckBox cb, String desc )
			{
				this.cb = cb;
				this.desc = desc;
			}

			@Override
			public void changed( ObservableValue<? extends T> observable, T oldValue, T newValue )
			{
				if( cb.isSelected() )
				{
					ObservableList<PatientHistory> currentPMHList = tvPMHTable.getItems();
					for( PatientHistory pmh : currentPMHList )
						if( pmh.getdescription().equals(desc) )
							return;
					currentPMHList.add( new PatientHistory( desc, PatientHistory.TYPE_PMH, visitID ) );
					tvPMHTable.setItems( currentPMHList );
					tvPMHTable.setEditable( true );
			 	}
				else
				{
					ObservableList<PatientHistory> currentMHList = tvPMHTable.getItems();
					for( PatientHistory ph : currentMHList )
						if( ph.getdescription().equals( desc ) )
						{
							currentMHList.remove( ph );
							break;
						}
				}
			}
		}
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbHyperTensionListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbHyperTension, "Hyper Tension" );
		cbHyperTension.selectedProperty().addListener( cbHyperTensionListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbDiabetesListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbDiabetes, "Diabetes" );
		cbDiabetes.selectedProperty().addListener( cbDiabetesListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbAsthmaListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbAsthma, "Asthma" );
		cbAsthma.selectedProperty().addListener( cbAsthmaListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbTuberculosisListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbTuberculosis, "Tuberculosis" );
		cbTuberculosis.selectedProperty().addListener( cbTuberculosisListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbHepAListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbHepA, "Hepatitis A" );
		cbHepA.selectedProperty().addListener( cbHepAListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbHepBListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbHepB, "Hepatitis B" );
		cbHepB.selectedProperty().addListener( cbHepBListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbMalariaListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbMalaria, "Malaria" );
		cbMalaria.selectedProperty().addListener( cbMalariaListener );
		PreviousMedicalHistoryCheckBoxListener<Boolean> cbHIVListener = new PreviousMedicalHistoryCheckBoxListener<Boolean>( cbHIV, "HIV" );
		cbHIV.selectedProperty().addListener( cbHIVListener );
		tfOtherScreening.textProperty().addListener( new TextChangeListener( tfOtherScreening, TextChangeListener.TYPE_TF ).setMaxLength( 512 ) );
	}

	private void initDHAllergyPane()
	{
		cbDrugHistory.setEditable(true);
		ArrayList<String> keywords = DatabaseController.getKeywordList(PatientHistory.TYPE_DH);
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbDrugHistory.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbDrugHistory, keywords ) );
		tvDrugHistoryTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbDHDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		//tbDHDate.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfDHDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvDrugHistoryTable, "Drug History", (int)tbDHDate.getPrefWidth(), "");
			}
		};
		tbDHDate.setCellFactory( cfDHDate );
		tbDrugHistory.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbDrugHistory.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfDrugHistory =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvDrugHistoryTable, "Drug History", (int)tbDrugHistory.getPrefWidth(), "");
			}
		};
		tbDrugHistory.setCellFactory( cfDrugHistory );

		cbAllergy.setEditable(true);
		ArrayList<String> keywords2 = DatabaseController.getKeywordList(PatientHistory.TYPE_ALE);
		keywords2.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_DH));
		keywords2.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbAllergy.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbAllergy, keywords2 ) );
		tvAllergyTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbAllergyDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		//tbAllergyDate.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfAllergyDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvAllergyTable, "Allergy", (int)tbAllergyDate.getPrefWidth(), "");
			}
		};
		tbAllergyDate.setCellFactory( cfAllergyDate );
		tbAllergy.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbAllergy.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfAllergy =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvAllergyTable, "Allergy", (int)tbAllergy.getPrefWidth(), "");
			}
		};
		tbAllergy.setCellFactory( cfAllergy );
	}

	private void initFemalePane()
	{
		Calendar now = Calendar.getInstance();
		now.setTime( new java.util.Date() );

		//tpFemale.setDisable( true );
		tfLMP_Day.textProperty().addListener( new TextChangeListener( tfLMP_Day, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 31 ).setMaxLength(2).setNextObject(tfLMP_Month) );
		tfLMP_Month.textProperty().addListener( new TextChangeListener( tfLMP_Month, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 12 ).setMaxLength(2).setNextObject(tfLMP_Year) );
		tfLMP_Year.textProperty().addListener( new TextChangeListener( tfLMP_Year, TextChangeListener.TYPE_INT ).setBoundaries( 0, now.get( Calendar.YEAR ) ) );
		tfNumOfPregnancy.textProperty().addListener( new TextChangeListener( tfNumOfPregnancy, TextChangeListener.TYPE_INT ).setBoundaries( 0, 100 ) );
		tfNumOfLiveBirth.textProperty().addListener( new TextChangeListener( tfNumOfLiveBirth, TextChangeListener.TYPE_INT ).setBoundaries( 0, 100 ) );
		tfNumOfMiscarriage.textProperty().addListener( new TextChangeListener( tfNumOfMiscarriage, TextChangeListener.TYPE_INT ).setBoundaries( 0, 100 ) );
		tfNumOfAbortion.textProperty().addListener( new TextChangeListener( tfNumOfAbortion, TextChangeListener.TYPE_INT ).setBoundaries( 0, 100 ) );
		tfNumOfStillBirth.textProperty().addListener( new TextChangeListener( tfNumOfStillBirth, TextChangeListener.TYPE_INT ).setBoundaries( 0, 100 ) );
		tfPregnancyOtherInfo.textProperty().addListener( new TextChangeListener( tfPregnancyOtherInfo, TextChangeListener.TYPE_TF ).setMaxLength( 512 ) );
		cbPregnant.selectedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					if( !newValue )
						tfGestation.setDisable( true );
					else
						tfGestation.setDisable( false );
				}
			}
		);
		tfGestation.setDisable( true );
		tfGestation.textProperty().addListener( new TextChangeListener( tfGestation, TextChangeListener.TYPE_INT ).setBoundaries( 0, 50 ) );
		//aTriagePatient.getPanes().remove( tpFemale );
	}

	public void setPatient( Patient p )
	{
		isNewPatient = false;
		this.slumID = p.getslumID();
		this.patientID = p.getpatientID();
		//this.patientPhoto.setImage( new Image( p.getphoto().toURI().toString() ) );
		this.tfFirstName.setText( p.getfirstName() );
		this.tfMiddleName.setText( p.getmiddleName() );
		this.tfLastName.setText( p.getlastName() );
		this.patientIDLabel.setVisible(true);
		this.patientIDLabel.setText( "ID: " + patientID );
		this.btnFingerprintRegistration.setVisible(true);
		//this.tfAgeYear.setText( "" + p.getageYear() );
		//this.tfAgeMonth.setText( "" + p.getageMonth() );
		//this.tfAgeDay.setText( "" + p.getageDay() );
		Calendar c = Calendar.getInstance();
		if( p.getDOB() != null )
		{
			Date patientDOB = p.getDOB();
			c.setTime( patientDOB );
			this.tfDOB_Day.setText( "" + c.get( Calendar.DAY_OF_MONTH ) );
			this.tfDOB_Month.setText( "" + ( c.get( Calendar.MONTH ) + 1) );
			this.tfDOB_Year.setText( "" + c.get( Calendar.YEAR ) );

			int ay = (int)(patientDOB.toLocalDate().until(LocalDate.now(), ChronoUnit.YEARS));
			this.tfAgeYear.setText(ay+"");
			if( ay == 0 )
			{
				this.lAgeYear.setVisible(true);
				this.tfAgeMonth.setVisible(true);
				this.lAgeMonth.setVisible(true);
				this.tfAgeDay.setVisible(true);
				this.lAgeDay.setVisible(true);
				int am = (int)(patientDOB.toLocalDate().until(LocalDate.now(), ChronoUnit.MONTHS));
				this.tfAgeMonth.setText(am+"");
				Calendar c2 = Calendar.getInstance();
				c2.setTime(patientDOB);
				c2.add(Calendar.MONTH, am);
				Date dobForDay = new java.sql.Date(c2.getTimeInMillis());
				int ad = (int)(dobForDay.toLocalDate().until(LocalDate.now(), ChronoUnit.DAYS));
				this.tfAgeDay.setText(ad+"");
			}
		}
		
		if( p.getsex() == 'M' )
		{
			this.rbM.setSelected( true );
			tpFemale.setDisable( true );
		}
		else
		{
			this.rbF.setSelected( true );
			tpFemale.setDisable( false );
		}
		this.cbStatus.setValue( p.getstatus() );
		if(!p.gettelNo().isEmpty())
		{
			this.tfTel1.setText( p.gettelNo().substring(0, 3) );
			this.tfTel2.setText( p.gettelNo().substring(3, 6) );
			this.tfTel3.setText( p.gettelNo().substring(6, p.gettelNo().length()) );
		}
		this.taAddress.setText( p.getaddress() );
	}

	public void setTriageRecord( TriageRecord tr )
	{
		this.triageID = tr.gettriageID();
		this.tfSystolicBloodPressure.setText( "" + tr.getSBP() );
		this.tfDiastolicBloodPressure.setText( "" + tr.getDBP() );
		this.tfPulseRate.setText( "" + tr.getPR() );
		this.tfRespiratoryRate.setText( "" + tr.getRR() );
		this.tfTemperature.setText( "" + tr.gettemperature() );
		if( tr.gettemperatureScale() == 'C' )
			this.rbCelsius.setSelected( true );
		else
			this.rbFahrenheit.setSelected( true );
		this.tfSpo2.setText( "" + tr.getspo2() );
		this.tfWeight.setText( "" + tr.getweight() );
		this.tfHeight.setText( "" + tr.getheight() );
		Calendar c = Calendar.getInstance();
		if( tr.getLDT() != null )
		{
			c.setTime( tr.getLDT() );
			this.tfLDT_Month.setText( "" + ( c.get( Calendar.MONTH ) + 1) );
			this.tfLDT_Year.setText( "" + c.get( Calendar.YEAR ) );
		}
		this.visitID = tr.getvisitID();
	}

	public void setFemaleRecord( FemaleRecord fr )
	{
		this.femaleID = fr.getfemaleID();
		Calendar c = Calendar.getInstance();
		if( fr.getLMP() != null )
		{
			c.setTime( fr.getLMP() );
			this.tfLMP_Day.setText( "" + c.get( Calendar.DAY_OF_MONTH ) );
			this.tfLMP_Month.setText( "" + ( c.get( Calendar.MONTH ) + 1) );
			this.tfLMP_Year.setText( "" + c.get( Calendar.YEAR ) );
		}
		if( fr.isBreastFeeding() )
			this.cbBreastFeeding.setSelected( true );
		if( fr.isContraceptive() )
			this.cbContraceptive.setSelected( true );
		this.tfNumOfPregnancy.setText( "" + fr.getnumOfPregnancy() );
		this.tfNumOfLiveBirth.setText( "" + fr.getnumOfLiveBirth() );
		if( fr.isPregnant() )
			this.cbPregnant.setSelected( true );
		this.tfGestation.setText( "" + fr.getgestation() );
		this.tfNumOfMiscarriage.setText( "" + fr.getnumOfMiscarriage() );
		this.tfNumOfAbortion.setText( "" + fr.getnumOfAbortion() );
		this.tfNumOfStillBirth.setText( "" + fr.getnumOfStillBirth() );
		this.tfPregnancyOtherInfo.setText( fr.getotherInfo() );
	}

	public void setHistory( ArrayList<PatientHistory> ccList, ArrayList<PatientHistory> pmhList, ArrayList<PatientHistory> screeningList, ArrayList<PatientHistory> drugList, ArrayList<PatientHistory> allergyList )
	{
		tvChiefComplaintTable.getItems().addAll( ccList );
		for(PatientHistory cc : ccList)
			if( cc.getvisitID() == visitID )
			{
				btnAddChiefComplaint.setText("Update");
				cbChiefComplaint.getEditor().setText(cc.getdescription());
				break;
			}
		tvPMHTable.getItems().addAll( pmhList );
		for( PatientHistory pmh : pmhList )
			if( pmh.getdescription().equals("Hyper Tension") )
				cbHyperTension.setSelected(true);
			else if( pmh.getdescription().equals("Diabetes") )
				cbDiabetes.setSelected(true);
			else if( pmh.getdescription().equals("Asthma") )
				cbAsthma.setSelected(true);
			else if( pmh.getdescription().equals("Tuberculosis") )
				cbTuberculosis.setSelected(true);
			else if( pmh.getdescription().equals("Hepatitis A") )
				cbHepA.setSelected(true);
			else if( pmh.getdescription().equals("Hepatitis B") )
				cbHepB.setSelected(true);
			else if( pmh.getdescription().equals("Malaria") )
				cbMalaria.setSelected(true);
			else if( pmh.getdescription().equals("HIV") )
				cbHIV.setSelected(true);
		for( PatientHistory sc : screeningList )
		{
			switch( sc.getdescription() )
			{
				case PatientHistory.SCN_TO:
					cbTobacco.setSelected(true);
					isTobacco = true;
					break;
				case PatientHistory.SCN_ETH:
					cbEthanol.setSelected(true);
					isEthanol = true;
					break;
				case PatientHistory.SCN_DRUG:
					cbDrug.setSelected(true);
					isDrug = true;
					break;
				case PatientHistory.SCN_EX_TO:
					cbExSmoker.setSelected(true);
					isExSmoker = true;
					break;
				case PatientHistory.SCN_EX_DRUG:
					cbExDrug.setSelected(true);
					isExDrug = true;
					break;
				default:
					tfOtherScreening.setText(sc.getdescription());
					isOtherScreening = true;
					break;
			}
		}
		initScreeningList = screeningList;
		tvDrugHistoryTable.getItems().addAll(drugList);
		for(PatientHistory dh : drugList)
			if( dh.getvisitID() == visitID )
			{
				btnAddDrugHistory.setText("Update");
				cbDrugHistory.getEditor().setText(dh.getdescription());
				break;
			}
		
		tvAllergyTable.getItems().addAll(allergyList);
		for(PatientHistory allergy : allergyList)
			if( allergy.getvisitID() == visitID )
			{
				btnAddAllergy.setText("Update");
				cbAllergy.getEditor().setText(allergy.getdescription());
				break;
			}
	}

	public void setSlum( int slumID )
	{
		this.slumID = slumID;
	}

	public void hideGraphButtons()
	{
		btnBloodPressureGraph.setVisible(false);
		btnPulseRateGraph.setVisible(false);
		btnRespiratoryRateGraph.setVisible(false);
		btnTemperatureGraph.setVisible(false);
		btnSpo2Graph.setVisible(false);
		btnWeightGraph.setVisible(false);
		btnHeightGraph.setVisible(false);
	}

	public void isEdit( boolean isEdit )
	{
		if( !isEdit )
		{
			btnSubmit.setVisible( false );
			btnOK.setVisible( true );
			lockPersonalDataPane();
			lockVitalSignsPane();
			lockChiefComplaintPane();
			lockPMHScreenPane();
			lockDHAllergyPane();
			lockFemalePane();
		}
	}

	private void lockPersonalDataPane()
	{
		tfFirstName.setDisable(true);
		tfMiddleName.setDisable(true);
		tfLastName.setDisable(true);
		tfAgeYear.setDisable(true);
		tfAgeMonth.setDisable(true);
		tfAgeDay.setDisable(true);
		tfDOB_Day.setDisable(true);
		tfDOB_Month.setDisable(true);
		tfDOB_Year.setDisable(true);
		rbM.setDisable(true);
		rbF.setDisable(true);
		cbStatus.setDisable(true);
		tfTel1.setDisable(true);
		tfTel2.setDisable(true);
		tfTel3.setDisable(true);
		taAddress.setDisable(true);
		btnUploadPhoto.setVisible(false);
	}

	private void lockVitalSignsPane()
	{
		tfSystolicBloodPressure.setDisable(true);
		tfDiastolicBloodPressure.setDisable(true);
		tfPulseRate.setDisable(true);
		tfRespiratoryRate.setDisable(true);
		tfTemperature.setDisable(true);
		rbCelsius.setDisable(true);
		rbFahrenheit.setDisable(true);
		tfSpo2.setDisable(true);
		tfWeight.setDisable(true);
		tfHeight.setDisable(true);
		tfLDT_Month.setDisable(true);
		tfLDT_Year.setDisable(true);
	}

	private void lockChiefComplaintPane()
	{
		cbChiefComplaint.setVisible(false);
		btnAddChiefComplaint.setVisible(false);
		btnDeleteChiefComplaint.setVisible(false);
		tvChiefComplaintTable.setEditable(false);
	}

	private void lockPMHScreenPane()
	{
		cbPMH.setVisible(false);
		btnAddPMH.setVisible(false);
		btnDeletePMH.setVisible(false);
		cbHyperTension.setDisable(true);
		cbDiabetes.setDisable(true);
		cbAsthma.setDisable(true);
		cbTuberculosis.setDisable(true);
		cbHepA.setDisable(true);
		cbHepB.setDisable(true);
		cbMalaria.setDisable(true);
		cbHIV.setDisable(true);
		tvPMHTable.setEditable(false);
		cbTobacco.setDisable(true);
		cbEthanol.setDisable(true);
		cbDrug.setDisable(true);
		cbExSmoker.setDisable(true);
		cbExDrug.setDisable(true);
		tfOtherScreening.setDisable(true);
	}

	private void lockDHAllergyPane()
	{
		cbDrugHistory.setVisible(false);
		cbAllergy.setVisible(false);
		btnAddDrugHistory.setVisible(false);
		btnDeleteDrugHistory.setVisible(false);
		btnAddAllergy.setVisible(false);
		btnDeleteAllergy.setVisible(false);
		tvDrugHistoryTable.setEditable(false);
		tvAllergyTable.setEditable(false);
	}

	private void lockFemalePane()
	{
		tfLMP_Day.setDisable( true );
		tfLMP_Month.setDisable( true );
		tfLMP_Year.setDisable( true );
		cbBreastFeeding.setDisable( true );
		cbContraceptive.setDisable( true );
		tfNumOfPregnancy.setDisable( true );
		tfNumOfLiveBirth.setDisable( true );
		cbPregnant.setDisable( true );
		tfGestation.setDisable( true );
		tfNumOfMiscarriage.setDisable( true );
		tfNumOfAbortion.setDisable( true );
		tfNumOfStillBirth.setDisable( true );
		tfPregnancyOtherInfo.setDisable( true );
	}

	public Optional<ButtonType> promptMessage( String title, String header, String msg, AlertType type, int contentFontSize )
	{
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		DialogPane dp = alert.getDialogPane();
		dp.setPrefWidth(400);
		dp.lookup(".label.content").setStyle("-fx-font-size: "+contentFontSize+"px;");
		dp.lookup(".header-panel").setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		return alert.showAndWait();
	}

	private void showSummary()
	{
		yPosition = 40;
		submissionPane.getChildren().clear();
		submissionPane.getChildren().add(summaryLabel);
		submissionPane.getChildren().add(btnSubmissionTopBack);
		submissionPane.getChildren().add(btnSubmitTop);
		submissionPane.getChildren().add(btnOKTop);
		submissionPane.getChildren().add(summaryInstructionLabel);
		submissionPane.getChildren().add(btnSubmissionBack);
		submissionPane.getChildren().add(btnSubmit);
		submissionPane.getChildren().add(btnOK);
		summaryLabel.setFont(new Font("System Bold", 18));
		summaryLabel.setAlignment(Pos.CENTER);
		summaryLabel.setUnderline(true);
		
		ArrayList<PatientHistory> PMHList = getNewPMHList();
		PMHList.addAll(getOldPMHList());
		PMHList.addAll(getNewScreeningList());
		//PMHList.addAll(getOldScreeningList());
		ArrayList<PatientHistory> drugAllergyList = new ArrayList<PatientHistory>();
		PatientHistory dh = getThisVisitDrugHistory();
		if(dh != null && dh.getvisitID() == visitID)
			drugAllergyList.add(dh);
		PatientHistory al = getThisVisitAllergy();
		if(al != null)
			drugAllergyList.add(al);

		prepareDateFields();
		showPersonalData(newPatientRecord(), tpPersonalData);
		showTriageRecord(newTriageRecord(), tpVitalSigns);
		showChiefComplaint(getThisVisitChiefComplaint(), tpChiefComplaint);
		showHistory("Previous Medical History & Screening", PMHList, tpPMHScreening);
		showHistory("Drug History & Allergy", drugAllergyList, tpDHAllergy);
		if(rbF.isSelected())
			showFemaleRecord(newFemaleRecord(), tpFemale);

		Label endOfReportLabel = new Label("-This is the end of the summary-");
		endOfReportLabel.setLayoutX(10);
		endOfReportLabel.setLayoutY(yPosition+=30);
		endOfReportLabel.setFont(new Font("System Bold", 18));
		endOfReportLabel.setPrefWidth(850);
		endOfReportLabel.setAlignment(Pos.CENTER);
		submissionPane.getChildren().add(endOfReportLabel);
		//submissionPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));

		btnSubmissionBack.setLayoutY(yPosition+=30);
		btnSubmit.setLayoutY(yPosition);
		btnOK.setLayoutY(yPosition);

		Label emptyLabel = new Label("");
		emptyLabel.setLayoutX(10);
		emptyLabel.setLayoutY(yPosition+=20);
		emptyLabel.setPrefHeight(10);
		submissionPane.getChildren().add(emptyLabel);
	}

	private void showPersonalData(Patient p, TitledPane tp)
	{
		String first = p.getfirstName();
		String patientName = p.getlastName();
		if( !first.isEmpty() )
		{
			if(patientName.isEmpty())
				patientName += first;
			else
				patientName += (" " + first);
		}
		Label patientNameLabel = new Label(patientName);
		patientNameLabel.setLayoutX(10);
		patientNameLabel.setLayoutY(yPosition+=30);
		patientNameLabel.setFont(new Font("System Bold", 18));
		patientNameLabel.setPrefWidth(850);
		patientNameLabel.setAlignment(Pos.CENTER);
		submissionPane.getChildren().add(patientNameLabel);

		Label subtitleLabel = new Label("Personal Data");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		subtitleLabel.setOnMouseClicked
		(
			new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						tpSubmission.setExpanded(false);
						aTriagePatient.getPanes().remove( tpSubmission );
						aTriagePatient.getPanes().add( tp );
						tp.setExpanded(true);
					}
				}
			}
		);
		submissionPane.getChildren().add(subtitleLabel);

		if(p.getDOB() != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.ENGLISH);
			Label dobAndAgeLabel;
			if(p.getageYear()== 0)
				dobAndAgeLabel = new Label("Date of Birth: "+sdf.format(p.getDOB())+"     Age: "+p.getageMonth()+" month(s) "+p.getageDay()+" day(s)");
			else
				dobAndAgeLabel = new Label("Date of Birth: "+sdf.format(p.getDOB())+"     Age: "+p.getageYear());
			dobAndAgeLabel.setLayoutX(10);
			dobAndAgeLabel.setLayoutY(yPosition+=30);
			dobAndAgeLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(dobAndAgeLabel);
		}

		Label sexAndStatusLabel = new Label("Sex: "+p.getsex()+"     Status: "+p.getstatus());
		sexAndStatusLabel.setLayoutX(10);
		sexAndStatusLabel.setLayoutY(yPosition+=30);
		sexAndStatusLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(sexAndStatusLabel);

		Label telphoneLabel = new Label("Telephone No.: "+p.gettelNo());
		telphoneLabel.setLayoutX(10);
		telphoneLabel.setLayoutY(yPosition+=30);
		telphoneLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(telphoneLabel);

		Label addressLabel = new Label("Address: "+p.getaddress());
		addressLabel.setLayoutX(10);
		addressLabel.setLayoutY(yPosition+=30);
		addressLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(addressLabel);
	}

	private void showTriageRecord(TriageRecord tr, TitledPane tp)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Vital Signs");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		subtitleLabel.setOnMouseClicked
		(
			new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						tpSubmission.setExpanded(false);
						aTriagePatient.getPanes().remove( tpSubmission );
						aTriagePatient.getPanes().add( tp );
						tp.setExpanded(true);
					}
				}
			}
		);
		submissionPane.getChildren().add(subtitleLabel);

		Label bloodPressureLabel = new Label("Blood Pressure: "+tr.getSBP()+" / "+tr.getDBP()+" mmHg");
		bloodPressureLabel.setLayoutX(10);
		bloodPressureLabel.setLayoutY(yPosition+=30);
		bloodPressureLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(bloodPressureLabel);

		Label pulseLabel = new Label("Pulse: "+tr.getPR()+" bpm");
		pulseLabel.setLayoutX(10);
		pulseLabel.setLayoutY(yPosition+=30);
		pulseLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(pulseLabel);

		Label respiratoryLabel = new Label("Respiratory Rate: "+tr.getRR()+" cmn");
		respiratoryLabel.setLayoutX(10);
		respiratoryLabel.setLayoutY(yPosition+=30);
		respiratoryLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(respiratoryLabel);

		Label temperatureLabel = new Label("Temperature: "+tr.gettemperature()+" \u00b0"+tr.gettemperatureScale());
		temperatureLabel.setLayoutX(10);
		temperatureLabel.setLayoutY(yPosition+=30);
		temperatureLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(temperatureLabel);

		Label spo2Label = new Label("SPO2: "+tr.getspo2()+" %");
		spo2Label.setLayoutX(10);
		spo2Label.setLayoutY(yPosition+=30);
		spo2Label.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(spo2Label);

		Label weightLabel = new Label("Weight: "+tr.getweight()+" kg");
		weightLabel.setLayoutX(10);
		weightLabel.setLayoutY(yPosition+=30);
		weightLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(weightLabel);

		Label heightLabel = new Label("Height: "+tr.getheight()+" cm");
		heightLabel.setLayoutX(10);
		heightLabel.setLayoutY(yPosition+=30);
		heightLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(heightLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy", java.util.Locale.ENGLISH);
		String ldtString;
		if(tr.getLDT() != null)
			ldtString = sdf.format(tr.getLDT());
		else
			ldtString = "N/A";
		Label ldtLabel = new Label("Last de-worming tablet: "+ldtString);
		ldtLabel.setLayoutX(10);
		ldtLabel.setLayoutY(yPosition+=30);
		ldtLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(ldtLabel);
	}

	private void showChiefComplaint(PatientHistory chiefComplaint, TitledPane tp)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Chief Complaint");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		subtitleLabel.setOnMouseClicked
		(
			new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						tpSubmission.setExpanded(false);
						aTriagePatient.getPanes().remove( tpSubmission );
						aTriagePatient.getPanes().add( tp );
						tp.setExpanded(true);
					}
				}
			}
		);
		submissionPane.getChildren().add(subtitleLabel);

		String s;
		if(chiefComplaint == null)
			s = "N/A";
		else
			s = chiefComplaint.getdescription();
		Label chiefComplaintLabel = new Label(s);
		chiefComplaintLabel.setLayoutX(10);
		chiefComplaintLabel.setLayoutY(yPosition+=30);
		chiefComplaintLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(chiefComplaintLabel);
	}

	private void showHistory(String subtitle, ArrayList<PatientHistory> patientHistoryList, TitledPane tp)
	{
		if(subtitle != null)
		{
			yPosition += 30;
			Label subtitleLabel = new Label(subtitle);
			subtitleLabel.setLayoutX(10);
			subtitleLabel.setLayoutY(yPosition+=30);
			subtitleLabel.setFont(new Font("System Bold", 16));
			subtitleLabel.setUnderline(true);
			subtitleLabel.setOnMouseClicked
			(
				new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event)
					{
						if(event.getButton() == MouseButton.PRIMARY)
						{
							tpSubmission.setExpanded(false);
							aTriagePatient.getPanes().remove( tpSubmission );
							aTriagePatient.getPanes().add( tp );
							tp.setExpanded(true);
						}
					}
				}
			);
			submissionPane.getChildren().add(subtitleLabel);
		}

		if(patientHistoryList.size() == 0)
		{
			Label noneLabel = new Label("No Update.");
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			noneLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(noneLabel);
		}
		for(PatientHistory ph : patientHistoryList)
		{
			String type;
			switch(ph.gethistoryType())
			{
				case PatientHistory.TYPE_PMH:
					type = "Previous Medical History: ";
					break;
				case PatientHistory.TYPE_SCR:
					if(ph.getremarks().equalsIgnoreCase("other"))
						type = "Screening(Other): ";
					else
						type = "Screening: ";
					break;
				case PatientHistory.TYPE_DH:
					type = "Drug History: ";
					break;
				case PatientHistory.TYPE_ALE:
					type = "Allergy: ";
					break;
				case PatientHistory.TYPE_HPI:
					type = "History of Present Illness: ";
					break;
				case PatientHistory.TYPE_FH:
					type = "Family History: ";
					break;
				case PatientHistory.TYPE_SH:
					type = "Social History: ";
					break;
				case PatientHistory.TYPE_ROS:
					type = ph.getremarks()+": ";
					break;
				case PatientHistory.TYPE_RF:
					type = "Red Flag: ";
					break;
				case PatientHistory.TYPE_PE:
					type = ph.getremarks()+": ";
					break;
				case PatientHistory.TYPE_CD:
					type = "Clinical Diagnosis: ";
					break;
				case PatientHistory.TYPE_INV:
					type = "Investigation: ";
					break;
				default:
					type = "History: ";
			}
			Label historyLabel = new Label(type+ph.getdescription());
			historyLabel.setLayoutX(10);
			historyLabel.setLayoutY(yPosition+=30);
			historyLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(historyLabel);
		}
	}

	private void showFemaleRecord(FemaleRecord fr, TitledPane tp)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Female Record");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		subtitleLabel.setOnMouseClicked
		(
			new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						tpSubmission.setExpanded(false);
						aTriagePatient.getPanes().remove( tpSubmission );
						aTriagePatient.getPanes().add( tp );
						tp.setExpanded(true);
					}
				}
			}
		);
		submissionPane.getChildren().add(subtitleLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.ENGLISH);
		String lmpString;
		if(fr.getLMP() != null)
			lmpString = sdf.format(fr.getLMP());
		else
			lmpString = "N/A";
		Label lmpLabel = new Label("LMP Date: "+lmpString);
		lmpLabel.setLayoutX(10);
		lmpLabel.setLayoutY(yPosition+=30);
		lmpLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(lmpLabel);

		Label gestationLabel;
		if(fr.isPregnant())
			gestationLabel = new Label("Pregnant: YES     Gestation: "+fr.getgestation()+" week(s)");
		else
			gestationLabel = new Label("Pregnant: NO");
		gestationLabel.setLayoutX(10);
		gestationLabel.setLayoutY(yPosition+=30);
		gestationLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(gestationLabel);

		Label breastFeedingLabel;
		if(fr.isBreastFeeding())
			breastFeedingLabel = new Label("Breast Feeding: YES");
		else
			breastFeedingLabel = new Label("Breast Feeding: NO");
		breastFeedingLabel.setLayoutX(10);
		breastFeedingLabel.setLayoutY(yPosition+=30);
		breastFeedingLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(breastFeedingLabel);

		Label contraceptiveLabel;
		if(fr.isContraceptive())
			contraceptiveLabel = new Label("Contraceptive: YES");
		else
			contraceptiveLabel = new Label("Contraceptive: NO");
		contraceptiveLabel.setLayoutX(10);
		contraceptiveLabel.setLayoutY(yPosition+=30);
		contraceptiveLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(contraceptiveLabel);

		Label numOfPregnancyLabel = new Label("No. of Pregnancy: "+fr.getnumOfPregnancy());
		numOfPregnancyLabel.setLayoutX(10);
		numOfPregnancyLabel.setLayoutY(yPosition+=30);
		numOfPregnancyLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(numOfPregnancyLabel);

		Label numOfLiveBirthLabel = new Label("No. of Live Birth: "+fr.getnumOfLiveBirth());
		numOfLiveBirthLabel.setLayoutX(10);
		numOfLiveBirthLabel.setLayoutY(yPosition+=30);
		numOfLiveBirthLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(numOfLiveBirthLabel);

		Label numOfMiscarriageLabel = new Label("No. of Miscarriage: "+fr.getnumOfMiscarriage());
		numOfMiscarriageLabel.setLayoutX(10);
		numOfMiscarriageLabel.setLayoutY(yPosition+=30);
		numOfMiscarriageLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(numOfMiscarriageLabel);

		Label numOfAbortionLabel = new Label("No. of Abortion: "+fr.getnumOfAbortion());
		numOfAbortionLabel.setLayoutX(10);
		numOfAbortionLabel.setLayoutY(yPosition+=30);
		numOfAbortionLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(numOfAbortionLabel);

		Label numOfStillBirthLabel = new Label("No. of Still Birth: "+fr.getnumOfStillBirth());
		numOfStillBirthLabel.setLayoutX(10);
		numOfStillBirthLabel.setLayoutY(yPosition+=30);
		numOfStillBirthLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(numOfStillBirthLabel);

		Label otherInfoLabel;
		if(fr.getotherInfo().isEmpty())
			otherInfoLabel = new Label("Other Information: N/A");
		else
			otherInfoLabel = new Label("Other Information: "+fr.getotherInfo());
		otherInfoLabel.setLayoutX(10);
		otherInfoLabel.setLayoutY(yPosition+=30);
		otherInfoLabel.setFont(new Font("System Bold", 14));
		submissionPane.getChildren().add(otherInfoLabel);
	}

	@Override
	public void handle(KeyEvent event)
	{
		// TODO: handle page up page down later(Can try CTRL+Key)
		if(event.getCode() == KeyCode.PAGE_UP)
		{
			addAllTitledPanes();
			System.out.println( event.getSource()+":UP" );
		}
		else if(event.getCode() == KeyCode.PAGE_DOWN)
		{
			addAllTitledPanes();
			System.out.println( event.getSource()+":DOWN" );
		}
		else if(event.getCode() == KeyCode.HOME)
		{
			addAllTitledPanes();
			System.out.println( event.getSource()+":HOME" );
		}
		else if(event.getCode() == KeyCode.END)
		{
			addAllTitledPanes();
			System.out.println( event.getSource()+":END" );
		}
	}
}
