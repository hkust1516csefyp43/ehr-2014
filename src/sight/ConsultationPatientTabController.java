package sight;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
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
import sight.TableEditingTextCell;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ConsultationPatientTabController implements Initializable, EventHandler<KeyEvent>
{
	@FXML Tab currentTab;
	@FXML Accordion aConsultationPatient;

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
	@FXML private ImageView patientPhoto;
	@FXML private Button btnUploadPhoto;
	@FXML private Button btnComment;

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
	@FXML private TextField tfChiefComplaint;
	//@FXML private Button btnAddChiefComplaint;
	@FXML private TableView<PatientHistory> tvChiefComplaintTable;
	@FXML private TableColumn<PatientHistory, String> tbCCDate;
	@FXML private TableColumn<PatientHistory, String> tbChiefComplaint;

	//Previous Medical History and Screening
	@FXML private TitledPane tpPMHScreening;
	@FXML private TextField tfPMH;
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
	@FXML private TableView<PatientHistory> tvDrugHistoryTable;
	@FXML private TableColumn<PatientHistory, String> tbDHDate;
	@FXML private TableColumn<PatientHistory, String> tbDrugHistory;
	@FXML private ComboBox<String> cbAllergy;
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

	//HPI, Family History and Social History
	@FXML private TitledPane tpHPI_FH_SH;
	@FXML private ToggleGroup tgHistoryType;
	@FXML private RadioButton rbHPI;
	@FXML private RadioButton rbFH;
	@FXML private RadioButton rbSH;
	@FXML private ComboBox<String> cbHistoryDetails;
	@FXML private Button btnAddHistory;
	@FXML private Button btnDeleteHistory;
	@FXML private TableView<PatientHistory> tvHPI_FH_SHTable;
	//@FXML private TableColumn<PatientHistory, String> tbHistoryDate;
	@FXML private TableColumn<PatientHistory, String> tbHistoryDate;
	@FXML private TableColumn<PatientHistory, String> tbHistoryType;
	@FXML private TableColumn<PatientHistory, String> tbHistoryDetails;

	//Review of System
	@FXML private TitledPane tpROS;
	@FXML private TableView<PatientHistory> tvROSTable;
	@FXML private TableColumn<PatientHistory, String> tbROSDate;
	@FXML private TableColumn<PatientHistory, String> tbROSType;
	@FXML private TableColumn<PatientHistory, String> tbROSDetails;
	@FXML private TableColumn<PatientHistory, Boolean> tbROS_NAD;

	//Physical Examination
	@FXML private TitledPane tpPhysicalExamination;
	@FXML private CheckBox cbAlertness;
	@FXML private CheckBox cbBreathing;
	@FXML private CheckBox cbCirculation;
	@FXML private CheckBox cbDehydration;
	@FXML private CheckBox cbDEFG;
	@FXML private TableView<PatientHistory> tvPETable;
	@FXML private TableColumn<PatientHistory, String> tbPEDate;
	@FXML private TableColumn<PatientHistory, String> tbPEType;
	@FXML private TableColumn<PatientHistory, String> tbPEDetails;
	@FXML private TableColumn<PatientHistory, Boolean> tbPE_NAD;

	//Clinical Diagnosis and Investigation
	@FXML private TitledPane tpCD_Investigation;
	@FXML private ComboBox<String> cbDiagnosis;
	@FXML private Button btnAddDiagnosis;
	@FXML private Button btnDeleteDiagnosis;
	@FXML private TableView<PatientHistory> tvDiagnosisTable;
	@FXML private TableColumn<PatientHistory, String> tbDiagnosisDate;
	@FXML private TableColumn<PatientHistory, String> tbDiagnosis;
	@FXML private TextField tfInvestigation;
	@FXML private Button btnAddInvestigation;
	@FXML private Button btnDeleteInvestigation;
	@FXML private TableView<PatientHistory> tvInvestigationTable;
	@FXML private TableColumn<PatientHistory, String> tbInvestigationDate;
	@FXML private TableColumn<PatientHistory, String> tbInvestigation;

	//Medication, Advice and Follow-up
	@FXML private TitledPane tpM_A_FU;
	@FXML private ComboBox<String> cbMedicine;
	@FXML private Label remainingInventoryLabel;
	@FXML private Label dosageLabel;
	@FXML private TextField tfDosage;
	@FXML private Label unitOfDosageLabel;
	@FXML private Label strengthLabel;
	@FXML private ComboBox<String> cbStrength;
	@FXML private ComboBox<String> cbRoute;
	@FXML private Label frequencyLabel;
	@FXML private TextField tfFrequency;
	@FXML private Label numOfDaysLabel;
	@FXML private TextField tfNumOfDays;
	//@FXML private Label quantityLabel;
	//@FXML private TextField tfQuantity;
	//@FXML private Label unitOfQuantityLabel;
	@FXML private TextField tfPrescriptionRemark;
	@FXML private Button btnResetPrescription;
	@FXML private Button btnAddPrescription;
	@FXML private Button btnDeletePrescription;
	@FXML private TableView<Prescription> tvPrescriptionTable;
	@FXML private TableColumn<Prescription, String> tbMedicine;
	@FXML private TableColumn<Prescription, String> tbStrength;
	@FXML private TableColumn<Prescription, String> tbDosage;
	@FXML private TableColumn<Prescription, String> tbRoute;
	@FXML private TableColumn<Prescription, Integer> tbFrequency;
	@FXML private TableColumn<Prescription, Integer> tbNumOfDays;
	@FXML private TableColumn<Prescription, String> tbPrescriptionRemark;
	@FXML private CheckBox cbDrinkMoreWater;
	@FXML private CheckBox cbEatMore;
	@FXML private CheckBox cbSleepMore;
	@FXML private CheckBox cbTakeBath;
	@FXML private TextField tfOtherAdvice;
	@FXML private CheckBox cbReferToNurse;
	@FXML private TextField tfReferToNurse;
	@FXML private CheckBox cbReferToHospital;
	@FXML private TextField tfReferToHospital;
	@FXML private TextField tfOtherFollowUp;

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

	private ConsultationPanelTabController panelCtrl;
	private boolean isEditPatient = false;
	private boolean isCommentResponded = true;
	private long patientID, visitID = -1;
	private int userID;
	private String patientName;
	private Calendar dob, lmp, ldt;
	private int dob_day, dob_month, dob_year, lmp_day, lmp_month, lmp_year, ldt_month, ldt_year;
	//private Patient patient;
	private String tabID;
	private boolean isTpROSOpening = false, isTpPEOpening = false;
	private TitledPane curTitledPane = null;
	private int yPosition;
	private ArrayList<PatientHistory> redFlagList, adviceList, followUpList, adviceFollowUpList, deletedHistoryList;
	private ArrayList<Prescription> prescriptionList, deletedPrescriptionList;
	private ArrayList<Comment> commentList;
	private float curRemainingInv = 0.0F;
	//private String curMedicineUnit;

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
		initHPI_FH_SHPane();
		initROSPane();
		initPhysicalExaminationPane();
		initCD_InvestigationPane();
		initM_A_FUPane();
		
		aConsultationPatient.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpPersonalData.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpVitalSigns.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpChiefComplaint.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpPMHScreening.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpDHAllergy.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpFemale.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpHPI_FH_SH.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpROS.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpPhysicalExamination.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpCD_Investigation.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpM_A_FU.addEventHandler(KeyEvent.KEY_PRESSED, this);
		tpSubmission.addEventHandler(KeyEvent.KEY_PRESSED, this);
		redFlagList = new ArrayList<PatientHistory>();
		adviceList = new ArrayList<PatientHistory>();
		followUpList = new ArrayList<PatientHistory>();
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
			int i = 0;
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
		aConsultationPatient.getPanes().remove( tpPersonalData );
		aConsultationPatient.getPanes().add( tpVitalSigns );
		tpVitalSigns.setExpanded(true);
	}

	@FXML
	public void btnVitalSignsNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpVitalSigns );
		aConsultationPatient.getPanes().add( tpChiefComplaint );
		tpChiefComplaint.setExpanded(true);
	}
	
	@FXML
	public void btnChiefComplaintNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpChiefComplaint );
		aConsultationPatient.getPanes().add( tpPMHScreening );
		tpPMHScreening.setExpanded(true);
	}

	@FXML
	public void btnPMHScreeningNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpPMHScreening );
		aConsultationPatient.getPanes().add( tpDHAllergy );
		tpDHAllergy.setExpanded(true);
	}

	@FXML
	public void btnDHAllergyNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpDHAllergy );
		if( rbF.isSelected() )
		{
			aConsultationPatient.getPanes().add( tpFemale );
			tpFemale.setExpanded(true);
		}
		else
		{
			aConsultationPatient.getPanes().add( tpHPI_FH_SH );
			tpHPI_FH_SH.setExpanded(true);
		}
	}

	@FXML
	public void btnFemaleNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpFemale );
		aConsultationPatient.getPanes().add( tpHPI_FH_SH );
		tpHPI_FH_SH.setExpanded(true);
	}

	@FXML
	public void btnHPI_FH_SHNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpHPI_FH_SH );
		aConsultationPatient.getPanes().add( tpROS );
		tpROS.setExpanded(true);
	}

	@FXML
	public void btnROSNextPressed( ActionEvent Event )//TODO:
	{
		ObservableList<PatientHistory> rosList = tvROSTable.getItems();
		for(PatientHistory ros : rosList)
			if( !ros.getisNAD() && ros.getdescription().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of ROS before the submission.", AlertType.INFORMATION, 14);
				return;
			}
		aConsultationPatient.getPanes().remove( tpROS );
		aConsultationPatient.getPanes().add( tpPhysicalExamination );
		tpPhysicalExamination.setExpanded(true);
	}

	@FXML
	public void btnPENextPressed( ActionEvent Event )
	{
		ObservableList<PatientHistory> peList = tvPETable.getItems();
		for(PatientHistory pe : peList)
			if( !pe.getisNAD() && pe.getdescription().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of Physical Examination before the submission.", AlertType.INFORMATION, 14);
				return;
			}
		aConsultationPatient.getPanes().remove( tpPhysicalExamination );
		aConsultationPatient.getPanes().add( tpCD_Investigation );
		tpCD_Investigation.setExpanded(true);
	}

	@FXML
	public void btnCD_InvestigationNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpCD_Investigation );
		aConsultationPatient.getPanes().add( tpM_A_FU );
		tpM_A_FU.setExpanded(true);
	}

	@FXML
	public void btnM_A_FUNextPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpM_A_FU );
		aConsultationPatient.getPanes().add( tpSubmission );
		tpSubmission.setExpanded(true);
	}

	@FXML
	public void btnVitalSignsBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpVitalSigns );
		aConsultationPatient.getPanes().add( tpPersonalData );
		tpPersonalData.setExpanded(true);
	}
	
	@FXML
	public void btnChiefComplaintBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpChiefComplaint );
		aConsultationPatient.getPanes().add( tpVitalSigns );
		tpVitalSigns.setExpanded(true);
	}

	@FXML
	public void btnPMHScreeningBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpPMHScreening );
		aConsultationPatient.getPanes().add( tpChiefComplaint );
		tpChiefComplaint.setExpanded(true);
	}

	@FXML
	public void btnDHAllergyBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpDHAllergy );
		aConsultationPatient.getPanes().add( tpPMHScreening );
		tpPMHScreening.setExpanded(true);
	}

	@FXML
	public void btnFemaleBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpFemale );
		aConsultationPatient.getPanes().add( tpDHAllergy );
		tpDHAllergy.setExpanded(true);
	}

	@FXML
	public void btnHPI_FH_SHBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpHPI_FH_SH );
		if( rbF.isSelected() )
		{
			aConsultationPatient.getPanes().add( tpFemale );
			tpFemale.setExpanded(true);
		}
		else
		{
			aConsultationPatient.getPanes().add( tpDHAllergy );
			tpDHAllergy.setExpanded(true);
		}
	}

	@FXML
	public void btnROSBackPressed( ActionEvent Event )
	{
		ObservableList<PatientHistory> rosList = tvROSTable.getItems();
		for(PatientHistory ros : rosList)
			if( !ros.getisNAD() && ros.getdescription().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of ROS before the submission.", AlertType.INFORMATION, 14);
				return;
			}
		aConsultationPatient.getPanes().remove( tpROS );
		aConsultationPatient.getPanes().add( tpHPI_FH_SH );
		tpHPI_FH_SH.setExpanded(true);
	}

	@FXML
	public void btnPEBackPressed( ActionEvent Event )
	{
		ObservableList<PatientHistory> peList = tvPETable.getItems();
		for(PatientHistory pe : peList)
			if( !pe.getisNAD() && pe.getdescription().isEmpty() )
			{
				promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of Physical Examination before the submission.", AlertType.INFORMATION, 14);
				return;
			}
		aConsultationPatient.getPanes().remove( tpPhysicalExamination );
		aConsultationPatient.getPanes().add( tpROS );
		tpROS.setExpanded(true);
	}

	@FXML
	public void btnCD_InvestigationBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpCD_Investigation );
		aConsultationPatient.getPanes().add( tpPhysicalExamination );
		tpPhysicalExamination.setExpanded(true);
	}

	@FXML
	public void btnM_A_FUBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpM_A_FU );
		aConsultationPatient.getPanes().add( tpCD_Investigation );
		tpCD_Investigation.setExpanded(true);
	}

	@FXML
	public void btnSubmissionBackPressed( ActionEvent Event )
	{
		aConsultationPatient.getPanes().remove( tpSubmission );
		aConsultationPatient.getPanes().add( tpM_A_FU );
		tpM_A_FU.setExpanded(true);
	}

	@FXML
	public void btnCommentPressed( ActionEvent Event )
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentPanel.fxml"));
			Parent searchResult = loader.load();
			CommentPanelController commentCtrl = loader.<CommentPanelController>getController();
			commentCtrl.setCommentList(commentList, userID);
			commentCtrl.setConsultationPatientController(this);
			Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
			Stage stage = new Stage();
			stage.setScene(new Scene(searchResult));
			stage.setTitle("Comment on "+patientName);
			stage.getIcons().setAll(image);
			stage.sizeToScene();
			stage.setResizable(false);
			commentCtrl.setStage(stage);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void btnAddHistoryPressed( ActionEvent Event )
	{
		if( cbHistoryDetails.getEditor().getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentHistoryList = tvHPI_FH_SHTable.getItems();
		String historyType = "";
		if( rbHPI.isSelected() )
			historyType = PatientHistory.TYPE_HPI;
		else if( rbFH.isSelected() )
			historyType = PatientHistory.TYPE_FH;
		else if( rbSH.isSelected() )
			historyType = PatientHistory.TYPE_SH;
		else
			return;
		PatientHistory ph = new PatientHistory( cbHistoryDetails.getEditor().getText().trim(), historyType, visitID );
		ph.setvisitID(visitID);
		currentHistoryList.add( 0, ph );
		tvHPI_FH_SHTable.setItems( currentHistoryList );
		cbHistoryDetails.getEditor().clear();
		java.util.Collections.sort
		(
			tvHPI_FH_SHTable.getItems(),
			new java.util.Comparator<PatientHistory>()
			{
			    @Override
			    public int compare(PatientHistory ph1, PatientHistory ph2)
			    {
			    	return ph1.compareTo(ph2);
			    }
			}
		);
	}

	@FXML
	public void btnDeleteHistoryPressed( ActionEvent Event )
	{
		/*PatientHistory ph = tvHPI_FH_SHTable.getSelectionModel().getSelectedItem();
		if( ph.getpatientHistoryID() != -1 )
			return;
		ObservableList<PatientHistory> currentHistoryList = tvHPI_FH_SHTable.getItems();
		currentHistoryList.remove(ph);
		tvHPI_FH_SHTable.setItems( currentHistoryList );*/

		PatientHistory ph = tvHPI_FH_SHTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentHistoryList = tvHPI_FH_SHTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentHistoryList.remove(ph);
			tvHPI_FH_SHTable.setItems( currentHistoryList );
		}
	}

	@FXML
	public void btnAddDiagnosisPressed( ActionEvent Event )
	{
		if( cbDiagnosis.getEditor().getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentDiagnosisList = tvDiagnosisTable.getItems();
		if( btnAddDiagnosis.getText().equals( "Add" ) )
		{
			ObservableList<PatientHistory> newDiagnosisList = FXCollections.observableArrayList();
			PatientHistory ph = new PatientHistory( cbDiagnosis.getEditor().getText().trim(), PatientHistory.TYPE_CD, visitID );
			ph.setisUnderTreatment( true );
			newDiagnosisList.add( ph );
			newDiagnosisList.addAll( currentDiagnosisList );
			btnAddDiagnosis.setText( "Update" );
			tvDiagnosisTable.setItems( newDiagnosisList );
		}
		else
		{
			//currentDiagnosisList.get( 0 ).setdescription( cbDiagnosis.getEditor().getText().trim() );
			for(PatientHistory cd: currentDiagnosisList)
				if(cd.getvisitID() == visitID || cd.getvisitID() == -1)
					cd.setdescription(cbDiagnosis.getEditor().getText().trim());
			tvDiagnosisTable.setItems( currentDiagnosisList );
		}
	}

	@FXML
	public void btnDeleteDiagnosisPressed( ActionEvent Event )
	{
		/*PatientHistory ph = tvDiagnosisTable.getSelectionModel().getSelectedItem();
		if( ph.getpatientHistoryID() != -1 )
			return;
		ObservableList<PatientHistory> currentDiagnosisList = tvDiagnosisTable.getItems();
		currentDiagnosisList.remove(ph);
		tvDiagnosisTable.setItems( currentDiagnosisList );
		btnAddDiagnosis.setText( "Add" );
		cbDiagnosis.getEditor().setText("");*/
		
		PatientHistory ph = tvDiagnosisTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentDiagnosisList = tvDiagnosisTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentDiagnosisList.remove(ph);
			tvDiagnosisTable.setItems( currentDiagnosisList );
			btnAddDiagnosis.setText( "Add" );
			cbDiagnosis.getEditor().setText("");
		}
	}

	@FXML
	public void btnAddInvestigationPressed( ActionEvent Event )
	{
		if( tfInvestigation.getText().trim().equals("") )
			return;
		ObservableList<PatientHistory> currentInvestigationList = tvInvestigationTable.getItems();
		PatientHistory ph = new PatientHistory( tfInvestigation.getText().trim(), PatientHistory.TYPE_INV, visitID );
		ph.setisUnderTreatment( true );
		currentInvestigationList.add( ph );
		tvInvestigationTable.setItems( currentInvestigationList );
		tfInvestigation.clear();
	}

	@FXML
	public void btnDeleteInvestigationPressed( ActionEvent Event )
	{
		/*PatientHistory ph = tvPhysicalExamTable.getSelectionModel().getSelectedItem();
		if( ph.getpatientHistoryID() != -1 )
			return;
		ObservableList<PatientHistory> currentPEList = tvPhysicalExamTable.getItems();
		ph.setisActive(false);
		if(ph.getvisitID() != -1)
			deletedHistoryList.add(ph);
		currentPEList.remove(ph);
		tvPhysicalExamTable.setItems( currentPEList );*/
		PatientHistory ph = tvInvestigationTable.getSelectionModel().getSelectedItem();
		if( ph.getvisitID() == visitID )//IF wanna disable delete during EDIT: ph.getpatientHistoryID() == -1
		{
			ObservableList<PatientHistory> currentInvestigationList = tvInvestigationTable.getItems();
			ph.setisActive(false);
			if(ph.getvisitID() != -1)
				deletedHistoryList.add(ph);
			currentInvestigationList.remove(ph);
			tvInvestigationTable.setItems( currentInvestigationList );
		}
	}

	@FXML
	public void btnResetPrescriptionPressed( ActionEvent Event )
	{
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		currentPrescriptionList.clear();
		currentPrescriptionList.addAll( prescriptionList );
		tvPrescriptionTable.setItems( currentPrescriptionList );
		cbMedicine.getEditor().setText("");
	}

	@FXML
	public void btnAddPrescriptionPressed( ActionEvent Event )
	{
		String medicine = cbMedicine.getEditor().getText().trim();
		if( medicine.equals("") )
			return;
		int dosage, frequency, numOfDays, quantity = 1;
		String strength, route, remark;
		try
		{
			strength = cbStrength.getSelectionModel().getSelectedItem();
			if(tfDosage.isVisible())
				dosage = Integer.parseInt(tfDosage.getText().trim());
			else
				dosage = 0;
			frequency = Integer.parseInt(tfFrequency.getText().trim());
			numOfDays = Integer.parseInt(tfNumOfDays.getText().trim());
			route = cbRoute.getSelectionModel().getSelectedItem();
			remark = tfPrescriptionRemark.getText().trim();
			/*if(tfQuantity.isVisible())
				quantity = Integer.parseInt(tfQuantity.getText().trim());
			else
				quantity = 1;*/
			String type = DatabaseController.getMedicineType(medicine);
			if(type.isEmpty())
				return;
			switch(type)
			{
				case "Tablet":
					if(dosage != 0)
					{
						int s = Prescription.getSolidStrengthMgPerTablet(strength);
						//quantity = (int) Math.ceil(dosage * frequency * numOfDays * quantity / s);
						quantity = (int) Math.ceil(dosage * frequency * numOfDays / s);
					}
					break;
				case "Capsule":
					if(dosage != 0)
					{
						int s = Prescription.getSolidStrengthMgPerTablet(strength);
						//quantity = (int) Math.ceil(dosage * frequency * numOfDays * quantity / s);
						quantity = (int) Math.ceil(dosage * frequency * numOfDays / s);
					}
					break;
				case "Syrup":
					quantity = dosage * frequency * numOfDays;
					break;
			}
		}
		catch(NumberFormatException e)
		{
			promptMessage("One-2-One Medical System", "Invalid Information", "Please input correct information.", AlertType.ERROR, 16);
			return;
		}
		if(quantity > curRemainingInv)
		{
			String msg = "Inventory of "+medicine+" is not enough, do you still want to prescribe this medicine?";
			msg += "\nRemaining Inventory: " + curRemainingInv;
			msg += "\nYour Prescription: " + quantity;
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Out of Stock!", msg, AlertType.CONFIRMATION, 16);
			if(result.isPresent() && (result.get() == ButtonType.CANCEL))
				return;
		}
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		ArrayList<String> medicineList = DatabaseController.getMedicineList();
		Prescription p = new Prescription( medicine );
		if(!medicineList.contains(medicine))
		{
			p.setisPrescripted(true);
			p.setpharmacistID(userID);
		}
		p.setvisitID(visitID);
		p.setstrength(strength);
		p.setdosage(dosage);
		p.setunitOfDosage(unitOfDosageLabel.getText());
		p.setfrequency(frequency);
		p.setnumOfDays(numOfDays);
		p.setquantity(quantity);
		p.setroute(route);
		p.setremark(remark);
		currentPrescriptionList.add( p );
		tvPrescriptionTable.setItems( currentPrescriptionList );
		cbMedicine.getEditor().setText("");
		tfDosage.setText("1");
		cbStrength.getSelectionModel().selectFirst();
		cbRoute.getSelectionModel().select("p.o.");
		tfFrequency.setText("1");
		tfNumOfDays.setText("1");
		//tfQuantity.setText("1");
		tfPrescriptionRemark.setText("");
	}

	@FXML
	public void btnDeletePrescriptionPressed( ActionEvent Event )
	{
		Prescription p = tvPrescriptionTable.getSelectionModel().getSelectedItem();
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		if(p.getbrand() != null && !p.getbrand().isEmpty())
		{
			promptMessage("One-2-One Medical System", "Prescripted Already!", "It is already prescripted.", AlertType.ERROR, 16);
			return;
		}
		p.setisActive(false);
		deletedPrescriptionList.add(p);
		currentPrescriptionList.remove(p);
		tvPrescriptionTable.setItems( currentPrescriptionList );
	}

	@FXML
	public void btnSubmitPressed( ActionEvent Event )
	{
		if( !isCommentResponded )
		{
			long numOfComment = commentList.size();
			String msg;
			if(numOfComment == 0)
				return;
			else if(numOfComment == 1)
				msg = "There is 1 comment on this patient, do you want to read it now?";
			else
				msg = "There are "+numOfComment+" comments on this patient, do you want to respond now?";

			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Comment", msg, AlertType.CONFIRMATION, 16);
			if(result.isPresent() && (result.get() == ButtonType.OK))
			{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentPanel.fxml"));
					Parent searchResult = loader.load();
					CommentPanelController commentCtrl = loader.<CommentPanelController>getController();
					commentCtrl.setCommentList(commentList, userID);
					commentCtrl.setConsultationPatientController(this);
					Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
					Stage stage = new Stage();
					stage.setScene(new Scene(searchResult));
					stage.setTitle("Comment on "+patientName);
					stage.getIcons().setAll(image);
					stage.sizeToScene();
					stage.setResizable(false);
					commentCtrl.setStage(stage);
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
		if( isNecessaryInfoFilled() && isPrescriptionProperlyFilled() )
		{
			for( PatientHistory mh : deletedHistoryList )
				DatabaseController.removePatientHistory( mh.getpatientHistoryID() );

			ArrayList<PatientHistory> newList = getNewHPI_FH_SHList();
			for( PatientHistory ph : newList )
				DatabaseController.addPatientHistory( ph );

			ArrayList<PatientHistory> oldList = getOldHPI_FH_SHList();
			for( PatientHistory ph : oldList )
				DatabaseController.updatePatientHistory( ph );

			ArrayList<PatientHistory> ROSList = getROSList();
			for( PatientHistory ros : ROSList )
				if( isEditPatient )
					DatabaseController.updatePatientHistory( ros );
				else
					DatabaseController.addPatientHistory( ros );

			for( PatientHistory rf : redFlagList )
				if( rf.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( rf );
				else
					DatabaseController.updatePatientHistory( rf );

			ArrayList<PatientHistory> peList = getPEList();
			for( PatientHistory pe : peList )
				if( isEditPatient )
					DatabaseController.updatePatientHistory( pe );
				else
					DatabaseController.addPatientHistory( pe );

			PatientHistory diagnosis = getNewDiagnosis();
			if( diagnosis != null )
			{
				if( diagnosis.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( diagnosis );
				else
					DatabaseController.updatePatientHistory( diagnosis );
			}

			ArrayList<PatientHistory> investigationList = getInvestigationList();
			for( PatientHistory inv : investigationList )
				if( inv.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( inv );
				else
					DatabaseController.updatePatientHistory( inv );

			ArrayList<Prescription> prescriptionList = getPrescriptionList();
			for( Prescription p : prescriptionList )
				if( p.getprescriptionID() == -1 )
					DatabaseController.addPrescription( p );
				else
					DatabaseController.updatePrescription( p );
			for( Prescription p : deletedPrescriptionList )
				DatabaseController.updatePrescription( p );

			/*ArrayList<PatientHistory> newAdviceList = getAdviceList();
			for( PatientHistory a : newAdviceList )
				if( a.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( a );
				else
					DatabaseController.updatePatientHistory( a );

			ArrayList<PatientHistory> newfollowUpList = getFollowUpList();
			for( PatientHistory f : newfollowUpList )
				if( f.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( f );
				else
					DatabaseController.updatePatientHistory( f );*/

			for( PatientHistory af : adviceFollowUpList )
				if( af.getpatientHistoryID() == -1 )
					DatabaseController.addPatientHistory( af );
				else
					DatabaseController.updatePatientHistory( af );

			String msg = "\n\nPatient Name: " + patientName;
			//msg += "\nPatient ID: " + patientID;
			//msg += "\nTag Number: " + DatabaseController.getTagNumber(visitID);
			if( prescriptionList.size() == 0 )
			{
				msg += "\n\nThis is the end of the visit.";
				DatabaseController.updatePatientVisit( visitID, User.getCurrentUser().getid(), true, true, false );
				DatabaseController.updatePatientVisit( visitID, User.getCurrentUser().getid(), true, true, true );
			}
			else
			{
				msg += "\n\nPlease go to the pharmacy station!";
				DatabaseController.updatePatientVisit( visitID, User.getCurrentUser().getid(), true, true, false );
			}
			panelCtrl.finishPatient(currentTab);
			promptMessage("One-2-One Medical System", "Successfully Submitted!\nTag Number: "+DatabaseController.getTagNumber(visitID), msg, AlertType.INFORMATION, 16);
		}
	}

	@FXML
	public void btnOKPressed( ActionEvent Event )
	{
		panelCtrl.finishPatient(currentTab);
	}

	private boolean isNecessaryInfoFilled()
	{
		boolean isOkay = (tfFirstName != null && !tfFirstName.getText().trim().isEmpty()) || (tfMiddleName != null && !tfMiddleName.getText().trim().isEmpty()) || (tfLastName != null && !tfLastName.getText().trim().isEmpty());
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
		boolean b1 = tfTel1.getText().trim().isEmpty();
		boolean b2 = tfTel2.getText().trim().isEmpty();
		boolean b3 = tfTel3.getText().trim().isEmpty();
		if( (!b1||!b2||!b3) && (b1||b2||b3) )
		{
			promptMessage("One-2-One Medical System", "Invalid Information!", "Please input a valid telephone number.", AlertType.ERROR, 16);
			return false;
		}
		return true;
	}

	private boolean isPrescriptionProperlyFilled()
	{
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		String warningMsg = "The following information is missing:\n\n";
		long warningCount = 0;
		for( Prescription p : currentPrescriptionList )
		{
			if(p.getroute().isEmpty())
			{
				promptMessage("One-2-One Medical System", "Missing Information!", "Please fill the route of "+p.getmedicine()+".", AlertType.ERROR, 16);
				return false;
			}
			if(p.getdosage() == 0)
				warningMsg += (++warningCount) + ". Dosage of " + p.getmedicine() + " is zero.\n";
			if(p.getfrequency() == 0)
				warningMsg += (++warningCount) + ". Frequency of " + p.getmedicine() + " is zero.\n";
			if(p.getnumOfDays() == 0)
				warningMsg += (++warningCount) + ". 'No. of Days' of " + p.getmedicine() + " is zero.\n";
		}
		if( warningCount == 0 )
			return true;
		warningMsg += "\nAre you confirm to continue?";
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", warningMsg, AlertType.CONFIRMATION, 14);
		if(result.isPresent() && (result.get() == ButtonType.OK))
			return true;
		else
			return false;
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
			if( tfLMP_Day.getText() == null || tfLMP_Day.getText().trim().isEmpty() )
				lmp_day = 1;
			else
				lmp_day = Integer.parseInt( tfLMP_Day.getText() );
			if( tfLMP_Month.getText() == null || tfLMP_Month.getText().trim().isEmpty() )
				lmp_month = 0;
			else
				lmp_month = Integer.parseInt( tfLMP_Month.getText() ) - 1;
			if( tfLMP_Year.getText() == null || tfLMP_Year.getText().trim().isEmpty() )
				lmp_year = 2015;
			else
				lmp_year = Integer.parseInt( tfLMP_Year.getText() );
			lmp = Calendar.getInstance();
			lmp.set( lmp_year, lmp_month, lmp_day );
		}

		//LDT
		if( tfLDT_Month.getText() == null || tfLDT_Month.getText().trim().isEmpty() )
			ldt_month = 0;
		else
			ldt_month = Integer.parseInt( tfLDT_Month.getText() ) - 1;
		if( tfLDT_Year.getText() == null || tfLDT_Year.getText().trim().isEmpty() )
			ldt_year = 2015;
		else
			ldt_year = Integer.parseInt( tfLDT_Year.getText() );
		ldt = Calendar.getInstance();
		ldt.set( ldt_year, ldt_month, 1 );
	}

	public ArrayList<PatientHistory> getNewHPI_FH_SHList()
	{
		ArrayList<PatientHistory> list = new ArrayList<>();
		ObservableList<PatientHistory> currentList = tvHPI_FH_SHTable.getItems();
		for( PatientHistory ph : currentList )
		{
			//if( ph.getpatientHistoryID() != -1 )
				//continue;
			if( ph.getpatientHistoryID() == -1 )
			{
				ph.setstartDateString( PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()) );
				ph.setpatientID( patientID );
				ph.setuserID( userID );
				//ph.setvisitID( visitID );
				list.add( ph );
			}
		}
		return list;
	}

	public ArrayList<PatientHistory> getOldHPI_FH_SHList()
	{
		ArrayList<PatientHistory> list = new ArrayList<>();
		ObservableList<PatientHistory> currentList = tvHPI_FH_SHTable.getItems();
		for( PatientHistory ph : currentList )
		{
			//if( ph.getpatientHistoryID() != -1 )
				//continue;
			if( ph.getpatientHistoryID() != -1 && ph.getvisitID() == visitID )
			{
				ph.setstartDateString( PatientHistory.PH_DATE_FORMAT.format(new java.util.Date()) );
				ph.setpatientID( patientID );
				ph.setuserID( userID );
				//ph.setvisitID( visitID );
				list.add( ph );
			}
		}
		return list;
	}

	public ArrayList<PatientHistory> getROSList()
	{
		ArrayList<PatientHistory> ROSList = new ArrayList<>();
		ObservableList<PatientHistory> currentROSList = tvROSTable.getItems();
		for( PatientHistory ros : currentROSList )
		{
			ros.setpatientID( patientID );
			ros.setuserID( userID );
			ros.setvisitID( visitID );
			ROSList.add( ros );
		}
		return ROSList;
	}

	public ArrayList<PatientHistory> getRedFlagList()
	{
		ArrayList<PatientHistory> newRFList = new ArrayList<>();
		for( PatientHistory rf : redFlagList )
			switch( rf.getdescription() )
			{
				case "Alertness":
					if( !cbAlertness.isSelected() )
					{
						rf.setisActive(false);
						newRFList.add(rf);
					}
					else
					{
						rf.setisActive(true);
						cbAlertness.setSelected(false);
						newRFList.add(rf);
					}
					break;
				case "Breathing":
					if( !cbBreathing.isSelected() )
					{
						rf.setisActive(false);
						newRFList.add(rf);
					}
					else
					{
						rf.setisActive(true);
						cbBreathing.setSelected(false);
						newRFList.add(rf);
					}	
					break;
				case "Circulation":
					if( !cbCirculation.isSelected() )
					{
						rf.setisActive(false);
						newRFList.add(rf);
					}
					else
					{
						rf.setisActive(true);
						cbCirculation.setSelected(false);
						newRFList.add(rf);
					}
					break;
				case "Dehydration":
					if( !cbDehydration.isSelected() )
					{
						rf.setisActive(false);
						newRFList.add(rf);
					}
					else
					{
						rf.setisActive(true);
						cbDehydration.setSelected(false);
						newRFList.add(rf);
					}
					break;
				case "DEFG":
					if( !cbDEFG.isSelected() )
					{
						rf.setisActive(false);
						newRFList.add(rf);
					}
					else
					{
						rf.setisActive(true);
						cbDEFG.setSelected(false);
						newRFList.add(rf);
					}
					break;
			}
		if( cbAlertness.isSelected() )
			newRFList.add( new PatientHistory( "Alertness", PatientHistory.TYPE_RF, patientID, visitID, userID, true ) );
		if( cbBreathing.isSelected() )
			newRFList.add( new PatientHistory( "Breathing", PatientHistory.TYPE_RF, patientID, visitID, userID, true ) );
		if( cbCirculation.isSelected() )
			newRFList.add( new PatientHistory( "Circulation", PatientHistory.TYPE_RF, patientID, visitID, userID, true ) );
		if( cbDehydration.isSelected() )
			newRFList.add( new PatientHistory( "Dehydration", PatientHistory.TYPE_RF, patientID, visitID, userID, true ) );
		if( cbDEFG.isSelected() )
			newRFList.add( new PatientHistory( "DEFG", PatientHistory.TYPE_RF, patientID, visitID, userID, true ) );
		for(PatientHistory rf : newRFList)
			if(rf.getisActive())
				switch( rf.getdescription() )
				{
					case "Alertness":
						cbAlertness.setSelected(true);
						break;
					case "Breathing":
						cbBreathing.setSelected(true);
						break;
					case "Circulation":
						cbCirculation.setSelected(true);
						break;
					case "Dehydration":
						cbDehydration.setSelected(true);
						break;
					case "DEFG":
						cbDEFG.setSelected(true);
						break;
				}
		return newRFList;
	}

	public ArrayList<PatientHistory> getPEList()
	{
		ArrayList<PatientHistory> PEList = new ArrayList<>();
		ObservableList<PatientHistory> currentPEList = tvPETable.getItems();
		for( PatientHistory pe : currentPEList )
		{
			pe.setpatientID( patientID );
			pe.setuserID( userID );
			pe.setvisitID( visitID );
			PEList.add( pe );
		}
		return PEList;
	}

	public PatientHistory getThisVisitDiagnosis()
	{
		ObservableList<PatientHistory> cdList = tvDiagnosisTable.getItems();
		for(PatientHistory cd : cdList)
			if( cd.getpatientHistoryID() == -1 || cd.getvisitID() == visitID )
				return cd;
		return null;
	}
	
	public PatientHistory getNewDiagnosis()
	{
		ObservableList<PatientHistory> cdList = tvDiagnosisTable.getItems();
		for(PatientHistory cd : cdList)
		{
			if( cd.getpatientHistoryID() == -1 || cd.getvisitID() == visitID )
			{
				cd.setstartDateString( (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new java.util.Date()) );
				cd.setpatientID( patientID );
				cd.setuserID( userID );
				cd.setvisitID( visitID );
				return cd;
			}
		}
		return null;
	}

	public ArrayList<PatientHistory> getInvestigationList()
	{
		ArrayList<PatientHistory> investigationList = new ArrayList<>();
		ObservableList<PatientHistory> currentInvestigationList = tvInvestigationTable.getItems();
		for( PatientHistory inv : currentInvestigationList )
		{
			//if( inv.getpatientHistoryID() != -1 )
				//continue;
			if( inv.getpatientHistoryID() == -1 || inv.getvisitID() == visitID )
			{
				inv.setstartDateString( (new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)).format(new java.util.Date()) );
				inv.sethistoryType( PatientHistory.TYPE_INV );
				inv.setpatientID( patientID );
				inv.setuserID( userID );
				//inv.setvisitID( visitID );
				investigationList.add( inv );
			}
		}
		return investigationList;
	}

	public ArrayList<Prescription> getPrescriptionList()
	{
		ArrayList<Prescription> PEList = new ArrayList<>();
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		for( Prescription p : currentPrescriptionList )
		{
			//p.setvisitID( visitID );
			p.setdoctorID( userID );
			//p.setisPrescripted(false);
			PEList.add( p );
		}
		return PEList;
	}

	public ArrayList<PatientHistory> getAdviceList()
	{
		ArrayList<PatientHistory> newAdviceList = new ArrayList<>();
		for( PatientHistory a : adviceList )
			switch( a.getdescription() )
			{
				case "Drink More Water":
					if( !cbDrinkMoreWater.isSelected() )
					{
						a.setisActive(false);
						newAdviceList.add(a);
					}
					else
					{
						a.setisActive(true);
						cbDrinkMoreWater.setSelected(false);
						newAdviceList.add(a);
					}
					break;
				case "Sleep More":
					if( !cbSleepMore.isSelected() )
					{
						a.setisActive(false);
						newAdviceList.add(a);
					}
					else
					{
						a.setisActive(true);
						cbSleepMore.setSelected(false);
						newAdviceList.add(a);
					}	
					break;
				case "Eat More":
					if( !cbEatMore.isSelected() )
					{
						a.setisActive(false);
						newAdviceList.add(a);
					}
					else
					{
						a.setisActive(true);
						cbEatMore.setSelected(false);
						newAdviceList.add(a);
					}
					break;
				case "Take Bath":
					if( !cbTakeBath.isSelected() )
					{
						a.setisActive(false);
						newAdviceList.add(a);
					}
					else
					{
						a.setisActive(true);
						cbTakeBath.setSelected(false);
						newAdviceList.add(a);
					}
					break;
				default:
					if( tfOtherAdvice.getText() == null || tfOtherAdvice.getText().trim().isEmpty() )
					{
						a.setisActive(false);
						newAdviceList.add(a);
					}
					else
					{
						a.setdescription( tfOtherAdvice.getText().trim() );
						a.setisActive(true);
						newAdviceList.add(a);
						tfOtherAdvice.setText("");
					}
					break;
			}
		if( cbDrinkMoreWater.isSelected() )
			newAdviceList.add( new PatientHistory( "Drink More Water", PatientHistory.TYPE_ADV, patientID, visitID, userID, true ) );
		if( cbSleepMore.isSelected() )
			newAdviceList.add( new PatientHistory( "Sleep More", PatientHistory.TYPE_ADV, patientID, visitID, userID, true ) );
		if( cbEatMore.isSelected() )
			newAdviceList.add( new PatientHistory( "Eat More", PatientHistory.TYPE_ADV, patientID, visitID, userID, true ) );
		if( cbTakeBath.isSelected() )
			newAdviceList.add( new PatientHistory( "Take Bath", PatientHistory.TYPE_ADV, patientID, visitID, userID, true ) );
		if ( tfOtherAdvice.getText() != null && !tfOtherAdvice.getText().trim().isEmpty() )
			newAdviceList.add( new PatientHistory( tfOtherAdvice.getText().trim(), PatientHistory.TYPE_ADV, patientID, visitID, userID, true ) );
		for(PatientHistory a : newAdviceList)
			if(a.getisActive())
				switch( a.getdescription() )
				{
					case "Drink More Water":
						cbDrinkMoreWater.setSelected(true);
						break;
					case "Sleep More":
						cbSleepMore.setSelected(true);
						break;
					case "Eat More":
						cbEatMore.setSelected(true);
						break;
					case "Take Bath":
						cbTakeBath.setSelected(true);
						break;
					default:
						tfOtherAdvice.setText(a.getdescription());
						break;
				}
		return newAdviceList;
	}

	public ArrayList<PatientHistory> getFollowUpList()
	{
		ArrayList<PatientHistory> newFollowUpList = new ArrayList<>();
		for( PatientHistory f : followUpList )
			switch( f.getremarks() )
			{
				case "Refer To Nurse":
					if( !cbReferToNurse.isSelected() )
					{
						f.setisActive(false);
						newFollowUpList.add(f);
					}
					else
					{
						f.setdescription( tfReferToNurse == null ? "" : tfReferToNurse.getText().trim() );
						f.setisActive(true);
						cbReferToNurse.setSelected(false);
						newFollowUpList.add(f);
					}
					break;
				case "Refer To Hospital":
					if( !cbReferToHospital.isSelected() )
					{
						f.setisActive(false);
						newFollowUpList.add(f);
					}
					else
					{
						f.setdescription( tfReferToHospital == null ? "" : tfReferToHospital.getText().trim() );
						f.setisActive(true);
						cbReferToHospital.setSelected(false);
						newFollowUpList.add(f);
					}
					break;
				default:
					if( tfOtherFollowUp.getText() == null || tfOtherFollowUp.getText().trim().isEmpty() )
					{
						f.setisActive(false);
						newFollowUpList.add(f);
					}
					else
					{
						f.setdescription( tfOtherFollowUp.getText().trim() );
						f.setisActive(true);
						newFollowUpList.add(f);
						tfOtherFollowUp.setText("");
					}
					break;
			}
		if( cbReferToNurse.isSelected() )
		{
			PatientHistory ph = new PatientHistory( tfReferToNurse == null ? "" : tfReferToNurse.getText().trim(), PatientHistory.TYPE_FOL, patientID, visitID, userID, true );
			ph.setremarks( "Refer To Nurse" );
			newFollowUpList.add( ph );
		}
		if( cbReferToHospital.isSelected() )
		{
			PatientHistory ph = new PatientHistory( tfReferToHospital == null ? "" : tfReferToHospital.getText().trim(), PatientHistory.TYPE_FOL, patientID, visitID, userID, true );
			ph.setremarks( "Refer To Hospital" );
			newFollowUpList.add( ph );
		}
		if ( tfOtherFollowUp.getText() != null && !tfOtherFollowUp.getText().trim().isEmpty() )
			newFollowUpList.add( new PatientHistory( tfOtherFollowUp.getText().trim(), PatientHistory.TYPE_FOL, patientID, visitID, userID, true ) );
		for(PatientHistory ph : newFollowUpList)
			if(ph.getisActive())
				switch( ph.getremarks() )
				{
					case "Refer To Nurse":
						cbReferToNurse.setSelected(true);
						tfReferToNurse.setText(ph.getdescription());
						break;
					case "Refer To Hospital":
						cbReferToHospital.setSelected(true);
						tfReferToHospital.setText(ph.getdescription());
						break;
					default:
						tfOtherFollowUp.setText(ph.getdescription());
						break;
				}
		return newFollowUpList;
	}

	public void setCurrentTab( Tab currentTab )
	{
		this.currentTab = currentTab;
	}

	public void setTabID( String tabID )
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
						openTitledPane( tpPersonalData );
					else
						addAllTitledPanes();
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

		tpHPI_FH_SH.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpHPI_FH_SH );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpROS.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
					{
						openTitledPane( tpROS );
						isTpROSOpening = true;
					}
					else
					{
						if(isTpROSOpening)//TODO:
						{
							ObservableList<PatientHistory> rosList = tvROSTable.getItems();
							for(PatientHistory ros : rosList)
								if( !ros.getisNAD() && ros.getdescription().isEmpty() )
								{
									promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of ROS before the submission.", AlertType.INFORMATION, 14);
									tpROS.setExpanded(true);
									return;
								}
						}
						addAllTitledPanes();
						isTpROSOpening = false;
					}
					});
				}
			}
		);

		tpPhysicalExamination.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
					{
						openTitledPane( tpPhysicalExamination );
						isTpPEOpening = true;
					}
					else
					{
						if(isTpPEOpening)
						{
							ObservableList<PatientHistory> peList = tvPETable.getItems();
							for(PatientHistory pe : peList)
								if( !pe.getisNAD() && pe.getdescription().isEmpty() )
								{
									promptMessage("One-2-One Medical System", "Missing Information", "Remember to fill all the information of Physical Examination before the submission.", AlertType.INFORMATION, 14);
									tpPhysicalExamination.setExpanded(true);
									return;
								}
						}
						addAllTitledPanes();
						isTpPEOpening = false;
					}
					});
				}
			}
		);

		tpCD_Investigation.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpCD_Investigation );
					else
						addAllTitledPanes();
					});
				}
			}
		);

		tpM_A_FU.expandedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Platform.runLater(() -> {
					if( newValue )
						openTitledPane( tpM_A_FU );
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
		if( !tp.equals( tpPersonalData ) && aConsultationPatient.getPanes().contains( tpPersonalData ) )
			aConsultationPatient.getPanes().remove( tpPersonalData );
		if( !tp.equals( tpVitalSigns ) && aConsultationPatient.getPanes().contains( tpVitalSigns ) )
			aConsultationPatient.getPanes().remove( tpVitalSigns );
		if( !tp.equals( tpChiefComplaint ) && aConsultationPatient.getPanes().contains( tpChiefComplaint ) )
			aConsultationPatient.getPanes().remove( tpChiefComplaint );
		if( !tp.equals( tpPMHScreening ) && aConsultationPatient.getPanes().contains( tpPMHScreening ) )
			aConsultationPatient.getPanes().remove( tpPMHScreening );
		if( !tp.equals( tpDHAllergy ) && aConsultationPatient.getPanes().contains( tpDHAllergy ) )
			aConsultationPatient.getPanes().remove( tpDHAllergy );
		if( !tp.equals( tpFemale ) && aConsultationPatient.getPanes().contains( tpFemale ) )
			aConsultationPatient.getPanes().remove( tpFemale );
		if( !tp.equals( tpHPI_FH_SH ) && aConsultationPatient.getPanes().contains( tpHPI_FH_SH ) )
			aConsultationPatient.getPanes().remove( tpHPI_FH_SH );
		if( !tp.equals( tpROS ) && aConsultationPatient.getPanes().contains( tpROS ) )
			aConsultationPatient.getPanes().remove( tpROS );
		if( !tp.equals( tpPhysicalExamination ) && aConsultationPatient.getPanes().contains( tpPhysicalExamination ) )
			aConsultationPatient.getPanes().remove( tpPhysicalExamination );
		if( !tp.equals( tpCD_Investigation ) && aConsultationPatient.getPanes().contains( tpCD_Investigation ) )
			aConsultationPatient.getPanes().remove( tpCD_Investigation );
		if( !tp.equals( tpM_A_FU ) && aConsultationPatient.getPanes().contains( tpM_A_FU ) )
			aConsultationPatient.getPanes().remove( tpM_A_FU );
		if( !tp.equals( tpSubmission ) && aConsultationPatient.getPanes().contains( tpSubmission ) )
			aConsultationPatient.getPanes().remove( tpSubmission );
		curTitledPane = tp;
	}

	private void addAllTitledPanes()
	{
		if( curTitledPane != null )
			aConsultationPatient.getPanes().remove( curTitledPane );	
		curTitledPane = null;
		if( !aConsultationPatient.getPanes().contains( tpPersonalData ) )
			aConsultationPatient.getPanes().add( 0, tpPersonalData );
		if( !aConsultationPatient.getPanes().contains( tpVitalSigns ) )
			aConsultationPatient.getPanes().add( 1, tpVitalSigns );
		if( !aConsultationPatient.getPanes().contains( tpChiefComplaint ) )
			aConsultationPatient.getPanes().add( 2, tpChiefComplaint );
		if( !aConsultationPatient.getPanes().contains( tpPMHScreening ) )
			aConsultationPatient.getPanes().add( 3, tpPMHScreening );
		if( !aConsultationPatient.getPanes().contains( tpDHAllergy ) )
			aConsultationPatient.getPanes().add( 4, tpDHAllergy );
		//long temp = 5;
		if( !aConsultationPatient.getPanes().contains( tpFemale ) )
			aConsultationPatient.getPanes().add( 5, tpFemale );
		if( rbM.isSelected())
			tpFemale.setDisable( true );
		if( !aConsultationPatient.getPanes().contains( tpHPI_FH_SH ) )
			aConsultationPatient.getPanes().add( 6, tpHPI_FH_SH );
		if( !aConsultationPatient.getPanes().contains( tpROS ) )
			aConsultationPatient.getPanes().add( 7, tpROS );
		if( !aConsultationPatient.getPanes().contains( tpPhysicalExamination ) )
			aConsultationPatient.getPanes().add( 8, tpPhysicalExamination );
		if( !aConsultationPatient.getPanes().contains( tpCD_Investigation ) )
			aConsultationPatient.getPanes().add( 9, tpCD_Investigation );
		if( !aConsultationPatient.getPanes().contains( tpM_A_FU ) )
			aConsultationPatient.getPanes().add( 10, tpM_A_FU );
		if( !aConsultationPatient.getPanes().contains( tpSubmission ) )
			aConsultationPatient.getPanes().add( 11, tpSubmission );
	}

	private void initPersonalDataPane()
	{
		Calendar now = Calendar.getInstance();
		now.setTime( new java.util.Date() );

		//tfFirstName.textProperty().addListener( new TextChangeListener( tfFirstName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		//tfMiddleName.textProperty().addListener( new TextChangeListener( tfMiddleName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		//tfLastName.textProperty().addListener( new TextChangeListener( tfLastName, TextChangeListener.TYPE_TF ).setMaxLength( 32 ) );
		middleNameLabel.setVisible(false);
		tfMiddleName.setVisible(false);
		
		/*lAgeYear.setVisible(false);
		tfAgeMonth.setVisible(false);
		lAgeMonth.setVisible(false);
		tfAgeDay.setVisible(false);
		lAgeDay.setVisible(false);*/

		//cbStatus.getItems().addAll( Patient.STATUS_SINGLE, Patient.STATUS_MARRIED, Patient.STATUS_DIVORCED, Patient.STATUS_WIDOWED );
		tfTel1.setText("");
		//tfTel1.textProperty().addListener( new TextChangeListener( tfTel1, TextChangeListener.TYPE_TF ).setRegEx( "\\d{3}" ).setMaxLength(3).setIsFixedLength(true).setNextObject(tfTel2) );
		tfTel2.setText("");
		//tfTel2.textProperty().addListener( new TextChangeListener( tfTel2, TextChangeListener.TYPE_TF ).setRegEx( "\\d{3}" ).setMaxLength(3).setIsFixedLength(true).setNextObject(tfTel3) );
		tfTel3.setText("");
		//tfTel3.textProperty().addListener( new TextChangeListener( tfTel3, TextChangeListener.TYPE_TF ).setRegEx( "\\d{4}" ).setMaxLength(4).setIsFixedLength(true) );
		taAddress.textProperty().addListener( new TextChangeListener( taAddress, TextChangeListener.TYPE_TA ).setMaxLength( 256 ) );
		Image photo = new Image( "file:"+"Kevin.jpg" );
		patientPhoto.setImage( photo );
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
								if(t < 80.0 || t > 140.0)
									tfTemperature.setText("");
							}
						}
					}
				}
			}
		);
		tfSpo2.textProperty().addListener( new TextChangeListener( tfSpo2, TextChangeListener.TYPE_FLOAT ).setBoundaries(0.0F, 100.0F) );
		tfWeight.textProperty().addListener( new TextChangeListener( tfWeight, TextChangeListener.TYPE_FLOAT ).setBoundaries(0.0F, 200.0F) );
		tfHeight.textProperty().addListener( new TextChangeListener( tfHeight, TextChangeListener.TYPE_FLOAT ).setBoundaries(0.0F, 300.0F) );
		tfLDT_Month.textProperty().addListener( new TextChangeListener( tfLDT_Month, TextChangeListener.TYPE_INT ).setAbsoluteBoundaries( 1, 12 ).setMaxLength(2).setNextObject(tfLDT_Year) );
		tfLDT_Year.textProperty().addListener( new TextChangeListener( tfLDT_Year, TextChangeListener.TYPE_INT ).setBoundaries( 0, now.get( Calendar.YEAR ) ) );
		
	}

	private void initChiefComplaintPane()
	{
		tvChiefComplaintTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbCCDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfCCDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvChiefComplaintTable, "Chief Complaint", (int)tbCCDate.getPrefWidth(), "");
			}
		};
		tbCCDate.setCellFactory( cfCCDate );
		//tbCCDate.setCellFactory( TextFieldTableCell.forTableColumn() );
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
		tvPMHTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
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
		tfPMH.textProperty().addListener( new TextChangeListener( tfOtherScreening, TextChangeListener.TYPE_TF ).setMaxLength( 512 ) );

		/**
		 * PreviousMedicalHistoryCheckBoxListener which is responsible for 
		 * updating the list in medical history table when the checkboxes are checked.
		 * @author khl
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
	}

	private void initHPI_FH_SHPane()
	{
		cbHistoryDetails.setEditable(true);
		ArrayList<String> keywords = DatabaseController.getKeywordList(PatientHistory.TYPE_CC);
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_FH));
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_SH));
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbHistoryDetails.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbHistoryDetails, keywords ) );
		rbHPI.setSelected(true);

		tvHPI_FH_SHTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbHistoryDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfHistoryDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvHPI_FH_SHTable, "HPI, Family & Social History", (int)tbHistoryDate.getPrefWidth(), "");
			}
		};
		tbHistoryDate.setCellFactory( cfHistoryDate );
		tbHistoryType.setCellValueFactory( new PropertyValueFactory<>("historyType") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfHistoryType =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvHPI_FH_SHTable, "HPI, Family & Social History", (int)tbHistoryType.getPrefWidth(), "");
			}
		};
		tbHistoryType.setCellFactory( cfHistoryType );
		tbHistoryType.setEditable( false );
		tbHistoryDetails.setCellValueFactory( new PropertyValueFactory<>("description") );
		tbHistoryDetails.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfHistoryDetails =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvHPI_FH_SHTable, "HPI, Family & Social History", (int)tbHistoryDetails.getPrefWidth(), "Description");
			}
		};
		tbHistoryDetails.setCellFactory( cfHistoryDetails );
	}

	private void initROSPane()
	{
		tvROSTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvROSTable.setSortPolicy(null);
		tbROSDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfROSDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvROSTable, "Review of System", (int)tbROSDate.getPrefWidth(), "");
			}
		};
		tbROSDate.setCellFactory( cfROSDate );
		tbROSDate.setEditable( false );
		tbROSType.setCellValueFactory( new PropertyValueFactory<>("remarks") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfROSType =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvROSTable, "Review of System", (int)tbROSType.getPrefWidth(), "");
			}
		};
		tbROSType.setCellFactory( cfROSType );
		tbROSType.setEditable( false );
		tbROSDetails.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbROSDetails.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfROSDetails =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvROSTable, "Review of System", (int)tbROSDetails.getPrefWidth(), "Description");
			}
		};
		tbROSDetails.setCellFactory( cfROSDetails );
		tbROS_NAD.setCellValueFactory( new PropertyValueFactory<>("isNAD"));
		tbROS_NAD.setCellFactory( CheckBoxTableCell.forTableColumn(tbROS_NAD) );

		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "EENT" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Respiratory" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Cardiovascular" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Gastrointestinal" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Genital/Urinary" ) );
		//tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "ENT" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Skin" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Locomotor" ) );
		tvROSTable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_ROS, "Neurology" ) );
		tvROSTable.setEditable( true );
	}

	private void initPhysicalExaminationPane()
	{
		tvPETable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvPETable.setSortPolicy(null);
		tbPEDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPEDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPETable, "Physical Examination", (int)tbPEDate.getPrefWidth(), "");
			}
		};
		tbPEDate.setCellFactory( cfPEDate );
		tbPEDate.setEditable( false );
		tbPEType.setCellValueFactory( new PropertyValueFactory<>("remarks") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPEType =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPETable, "Physical Examination", (int)tbPEType.getPrefWidth(), "");
			}
		};
		tbPEType.setCellFactory( cfPEType );
		tbPEType.setEditable( false );
		tbPEDetails.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbPEDetails.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPEDetails =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPETable, "Physical Examination", (int)tbPEDetails.getPrefWidth(), "Description");
			}
		};
		tbPEDetails.setCellFactory( cfPEDetails );
		tbPE_NAD.setCellValueFactory( new PropertyValueFactory<>("isNAD"));
		tbPE_NAD.setCellFactory( CheckBoxTableCell.forTableColumn(tbPE_NAD) );

		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "General Appearance" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "EENT" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Respiratory" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Cardiovascular" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Gastrointestinal" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Genital/Urinary" ) );
		//tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "ENT" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Skin" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Locomotor" ) );
		tvPETable.getItems().add( new PatientHistory( "", PatientHistory.TYPE_PE, "Neurology" ) );
		tvPETable.setEditable( true );
	}

	private void initCD_InvestigationPane()
	{
		cbDiagnosis.setEditable(true);
		ArrayList<String> keywords = DatabaseController.getKeywordList(PatientHistory.TYPE_CD);
		keywords.addAll(DatabaseController.getKeywordList(PatientHistory.TYPE_GENERAL));
		cbDiagnosis.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbDiagnosis, keywords ) );
		tvDiagnosisTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvDiagnosisTable.setEditable(false);
		tbDiagnosisDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfDiagnosisDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvDiagnosisTable, "Clinical Diagnosis", (int)tbDiagnosisDate.getPrefWidth(), "");
			}
		};
		tbDiagnosisDate.setCellFactory( cfDiagnosisDate );
		tbDiagnosis.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbDiagnosis.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfDiagnosis =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvDiagnosisTable, "Clinical Diagnosis", (int)tbDiagnosis.getPrefWidth(), "");
			}
		};
		tbDiagnosis.setCellFactory( cfDiagnosis );

		tvInvestigationTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvInvestigationTable.setEditable(true);
		tbInvestigationDate.setCellValueFactory( new PropertyValueFactory<>("entryDateTimeString") );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPEDate =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvInvestigationTable, "Investigation", (int)tbInvestigationDate.getPrefWidth(), "");
			}
		};
		tbInvestigationDate.setCellFactory( cfPEDate );
		tbInvestigationDate.setEditable(false);
		tbInvestigation.setCellValueFactory( new PropertyValueFactory<>("description") );
		//tbInvestigation.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>> cfPhysicalExamination =
			new Callback<TableColumn<PatientHistory, String>, TableCell<PatientHistory, String>>() {
			public TableCell<PatientHistory, String> call(TableColumn<PatientHistory, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvInvestigationTable, "Investigation", (int)tbInvestigation.getPrefWidth(), "Description");
			}
		};
		tbInvestigation.setCellFactory( cfPhysicalExamination );
	}

	private void initM_A_FUPane()
	{
		/*strengthLabel.setVisible(false);
		cbStrength.setVisible(false);
		dosageLabel.setVisible(false);
		tfDosage.setVisible(false);
		unitOfDosageLabel.setVisible(false);
		frequencyLabel.setVisible(false);
		tfFrequency.setVisible(false);
		numOfDaysLabel.setVisible(false);
		tfNumOfDays.setVisible(false);
		quantityLabel.setVisible(false);
		tfQuantity.setVisible(false);
		unitOfQuantityLabel.setVisible(false);*/
		
		btnResetPrescription.setVisible(false);
		cbMedicine.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbMedicine, DatabaseController.getMedicineList() ) );
		cbMedicine.getEditor().textProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2)
				{
					String medicine = cbMedicine.getEditor().getText().trim();
					if( medicine.equals("") )
					{
						remainingInventoryLabel.setVisible(false);
						curRemainingInv = 0.0F;
						return;
					}
					String type = DatabaseController.getMedicineType(medicine);
					if(type.isEmpty())
					{
						remainingInventoryLabel.setVisible(false);
						curRemainingInv = 0.0F;
						return;
					}
					cbStrength.getItems().clear();
					cbStrength.getItems().addAll(DatabaseController.getStrengthList(medicine));
					cbStrength.getSelectionModel().selectFirst();
				}
			}
		);

		cbStrength.getItems().add("N/A");
		cbStrength.getSelectionModel().selectedItemProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2)
				{
					String medicine = cbMedicine.getEditor().getText().trim();
					if( medicine.equals("") )
					{
						remainingInventoryLabel.setVisible(false);
						curRemainingInv = 0.0F;
						return;
					}
					String strength = cbStrength.getSelectionModel().getSelectedItem();
					if(strength == null)
					{
						remainingInventoryLabel.setVisible(false);
						curRemainingInv = 0.0F;
						return;
					}
					String type = DatabaseController.getMedicineType(medicine);
					if(type.isEmpty())
					{
						remainingInventoryLabel.setVisible(false);
						curRemainingInv = 0.0F;
						tfDosage.setVisible(true);
						//tfQuantity.setVisible(true);
						//tfQuantity.setText("1");
						return;
					}
					//unitOfQuantityLabel.setText(DatabaseController.getMedicineUnit(medicine));
					switch(type)
					{
						case "Tablet":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							if(strength.contains("Iu"))
								unitOfDosageLabel.setText("Iu");
							else if(strength.contains("unit"))
							{
								unitOfDosageLabel.setText("unit");
								//quantityLabel.setVisible(false);
								//tfQuantity.setVisible(false);
								//unitOfQuantityLabel.setVisible(false);
							}
							else
							{
								//curMedicineUnit = "mg";
								unitOfDosageLabel.setText("mg");
							}
							break;
						case "Capsule":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							if(strength.contains("Iu"))
								unitOfDosageLabel.setText("Iu");
							else if(strength.contains("unit"))
							{
								unitOfDosageLabel.setText("unit");
								//quantityLabel.setVisible(false);
								//tfQuantity.setVisible(false);
								//unitOfQuantityLabel.setVisible(false);
							}
							else
								unitOfDosageLabel.setText("mg");
							break;
						case "Syrup":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							unitOfDosageLabel.setText("ml");
							//quantityLabel.setVisible(false);
							//tfQuantity.setVisible(false);
							//unitOfQuantityLabel.setText("");
							break;
						case "Cream":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							if(strength.contains("%"))
								unitOfDosageLabel.setText("% w/w");
							else if(strength.contains("unit"))
								unitOfDosageLabel.setText("");
							else
								unitOfDosageLabel.setText("mg/g");
							break;
						case "Drop":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							unitOfDosageLabel.setText("drop(s)");
							break;
						case "Gel":
							dosageLabel.setVisible(false);
							tfDosage.setVisible(false);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							//curMedicineUnit = "";
							unitOfDosageLabel.setText("");
							break;
						case "Injection":
							dosageLabel.setVisible(true);
							tfDosage.setVisible(true);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							if(strength.contains("mg"))
								unitOfDosageLabel.setText("mg");
							else if(strength.contains("g"))
								unitOfDosageLabel.setText("g");
							else
								unitOfDosageLabel.setText("");
							break;
						case "Powder":
							dosageLabel.setVisible(false);
							tfDosage.setVisible(false);
							//quantityLabel.setVisible(true);
							//tfQuantity.setVisible(true);
							//tfQuantity.setText("1");
							unitOfDosageLabel.setText("");
							break;
					}
					float inventory = DatabaseController.getMedicineQuantity(medicine, strength);
					remainingInventoryLabel.setText("Remaining Inv.: " + inventory + " " + DatabaseController.getMedicineUnit(medicine) );
					curRemainingInv = inventory;
					remainingInventoryLabel.setVisible(true);
					/*if(inventory != 0)
					{
						remainingInventoryLabel.setText("Remaining Inv.: " + inventory + " " + unitOfQuantityLabel.getText() );
						remainingInventoryLabel.setVisible(true);
					}
					else
						remainingInventoryLabel.setVisible(false);*/
				}
			}
		);
		cbRoute.getItems().addAll(DatabaseController.getKeywordList("Route"));
		cbRoute.getSelectionModel().select("p.o.");

		tvPrescriptionTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbMedicine.setCellValueFactory( new PropertyValueFactory<>("medicine") );
		//tbMedicine.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfMedicine =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbMedicine.getPrefWidth(), "Medicine");
			}	
		};
		tbMedicine.setCellFactory( cfMedicine );
		//tbMedicine.setEditable(false);

		tbStrength.setCellValueFactory( new PropertyValueFactory<>("strength") );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfStrength =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbStrength.getPrefWidth(), "Strength");
			}
		};
		tbStrength.setCellFactory( cfStrength );

		tbDosage.setCellValueFactory( new PropertyValueFactory<>("dosageString") );
		//tbDosage.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfDosage =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbDosage.getPrefWidth(), "Dosage");
			}
		};
		tbDosage.setCellFactory( cfDosage );
		
		tbRoute.setCellValueFactory( new PropertyValueFactory<>("route") );
		//tbRoute.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfRoute =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbFrequency.getPrefWidth(), "Frequency");
			}
		};
		tbRoute.setCellFactory( cfRoute );

		tbFrequency.setCellValueFactory( new PropertyValueFactory<>("frequency") );
		//tbFrequency.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>> cfFrequency =
			new Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>>() {
			public TableCell<Prescription, Integer> call(TableColumn<Prescription, Integer> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_INT, tvPrescriptionTable, "Prescription", (int)tbFrequency.getPrefWidth(), "Frequency");
			}
		};
		tbFrequency.setCellFactory( cfFrequency );
		
		tbNumOfDays.setCellValueFactory( new PropertyValueFactory<>("numOfDays") );
		//tbNumOfDays.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>> cfNumOfDays =
			new Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>>() {
			public TableCell<Prescription, Integer> call(TableColumn<Prescription, Integer> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_INT, tvPrescriptionTable, "Prescription", (int)tbNumOfDays.getPrefWidth(), "NumOfDays");
			}
		};
		tbNumOfDays.setCellFactory( cfNumOfDays );

		tbPrescriptionRemark.setCellValueFactory( new PropertyValueFactory<>("remark") );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfPrescriptionRemark =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbPrescriptionRemark.getPrefWidth(), "Remark");
			}
		};
		tbPrescriptionRemark.setCellFactory( cfPrescriptionRemark );

		tvPrescriptionTable.setEditable(false);
		this.prescriptionList = new ArrayList<Prescription>();
		this.deletedPrescriptionList = new ArrayList<Prescription>();

		tfReferToNurse.textProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(newValue != null && !newValue.isEmpty())
						cbReferToNurse.setSelected(true);
					else
						cbReferToNurse.setSelected(false);
				}
			}
		);
		tfReferToHospital.textProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(newValue != null && !newValue.isEmpty())
						cbReferToHospital.setSelected(true);
					else
						cbReferToHospital.setSelected(false);
				}
			}
		);
		cbReferToNurse.selectedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue)
				{
					if(!newValue)
						tfReferToNurse.setText("");
				}
			}
		);
		cbReferToHospital.selectedProperty().addListener
		(
			new ChangeListener<Boolean>()
			{
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue)
				{
					if(!newValue)
						tfReferToHospital.setText("");
				}
			}
		);
		
		tfDosage.setText("1");
		cbStrength.getSelectionModel().selectFirst();;
		cbRoute.getSelectionModel().select("p.o.");
		tfFrequency.setText("1");
		tfNumOfDays.setText("1");
		//tfQuantity.setText("1");
		tfPrescriptionRemark.setText("");
	}

	public void setPatient( Patient p, String patientName )
	{
		//this.patient = p;
		this.patientName = patientName;
		this.patientID = p.getpatientID();
		//TODO: Photo
		//this.patientPhoto.setImage( new Image( p.getphoto().toURI().toString() ) );
		this.tfFirstName.setText( p.getfirstName() );
		this.tfMiddleName.setText( p.getmiddleName() );
		this.tfLastName.setText( p.getlastName() );
		this.patientIDLabel.setText( "ID: " + patientID );
		this.tfAgeYear.setText( "" + p.getageYear() );
		this.tfAgeMonth.setText( "" + p.getageMonth() );
		this.tfAgeDay.setText( "" + p.getageDay() );

		Calendar c = Calendar.getInstance();
		if( p.getDOB() != null )
		{
			c.setTime( p.getDOB() );
			this.tfDOB_Day.setText( "" + c.get( Calendar.DAY_OF_MONTH ) );
			this.tfDOB_Month.setText( "" + ( c.get( Calendar.MONTH ) + 1) );
			this.tfDOB_Year.setText( "" + c.get( Calendar.YEAR ) );
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
		lockPersonalDataPane();
	}

	public void setVisitID( long visitID )
	{
		this.visitID = visitID;
	}

	public void setTriageRecord( TriageRecord tr )
	{
		this.tfSystolicBloodPressure.setText( "" + tr.getSBP() );
		this.tfDiastolicBloodPressure.setText( "" + tr.getDBP() );
		this.tfPulseRate.setText( "" + tr.getPR() );
		this.tfRespiratoryRate.setText( "" + tr.getRR() );
		if( tr.gettemperatureScale() == 'C' )
			this.rbCelsius.setSelected( true );
		else
			this.rbFahrenheit.setSelected( true );
		this.tfTemperature.setText( "" + tr.gettemperature() );
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
		lockVitalSignsPane();
	}

	public void setFemaleRecord( FemaleRecord fr )
	{
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
		lockFemalePane();
	}

	public void setHistory( ArrayList<PatientHistory> ccList, ArrayList<PatientHistory> pmhList, ArrayList<PatientHistory> scList, ArrayList<PatientHistory> drugList, ArrayList<PatientHistory> allergyList, ArrayList<PatientHistory> hpi_fh_shList, ArrayList<PatientHistory> cdList )
	{
		tvChiefComplaintTable.getItems().addAll( ccList );
		/*for(PatientHistory cc : ccList)
			if(cc.getvisitID() == visitID)
				panelCtrl.addSpecialFunction("Chief Complaint", patientName, cc.getdescription());*/
		lockChiefComplaintPane();
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
		for( PatientHistory sc : scList )
			if( sc.getdescription().equals(PatientHistory.SCN_TO) )
				cbTobacco.setSelected(true);
			else if( sc.getdescription().equals(PatientHistory.SCN_ETH) )
				cbEthanol.setSelected(true);
			else if( sc.getdescription().equals(PatientHistory.SCN_DRUG) )
				cbDrug.setSelected(true);
			else if( sc.getdescription().equals(PatientHistory.SCN_EX_TO) )
				cbExSmoker.setSelected(true);
			else if( sc.getdescription().equals(PatientHistory.SCN_EX_DRUG) )
				cbExDrug.setSelected(true);
			else
				tfOtherScreening.setText(sc.getdescription());
		lockPMHScreenPane();
		tvDrugHistoryTable.getItems().addAll( drugList );
		tvAllergyTable.getItems().addAll( allergyList );
		lockDHAllergyPane();
		tvHPI_FH_SHTable.getItems().addAll( hpi_fh_shList );
		if(isEditPatient)
			tvHPI_FH_SHTable.setEditable( true );
		tvDiagnosisTable.getItems().addAll( cdList );
		for(PatientHistory cd : cdList)
			if( cd.getvisitID() == visitID )
			{
				btnAddDiagnosis.setText("Update");
				cbDiagnosis.getEditor().setText(cd.getdescription());
				break;
			}
	}

	public void setHistoryForViewOrEditPatient( ArrayList<PatientHistory> rosList, ArrayList<PatientHistory> redFlagList, ArrayList<PatientHistory> peList, ArrayList<PatientHistory> investigationList, ArrayList<Prescription> preList, ArrayList<PatientHistory> advList, ArrayList<PatientHistory> followList )
	{
		tvROSTable.getItems().clear();
		tvROSTable.getItems().addAll(rosList);
		this.redFlagList = redFlagList;
		for( PatientHistory rf : redFlagList )
		{
			if(rf.getisUnderTreatment())
				switch( rf.getdescription() )
				{
					case "Alertness":
						cbAlertness.setSelected(true);
						break;
					case "Breathing":
						cbBreathing.setSelected(true);
						break;
					case "Circulation":
						cbCirculation.setSelected(true);
						break;
					case "Dehydration":
						cbDehydration.setSelected(true);
						break;
					case "DEFG":
						cbDEFG.setSelected(true);
						break;
				}
		}
		tvPETable.getItems().clear();
		tvPETable.getItems().addAll(peList);
		tvInvestigationTable.getItems().addAll(investigationList);
		if(isEditPatient)
			tbInvestigation.setEditable( true );
		tvPrescriptionTable.getItems().addAll(preList);
		this.prescriptionList = preList;
		this.adviceList = advList;
		for( PatientHistory a : advList )
		{
			if(a.getisUnderTreatment())
				switch( a.getdescription() )
				{
					case "Drink More Water":
						cbDrinkMoreWater.setSelected(true);
						break;
					case "Sleep More":
						cbSleepMore.setSelected(true);
						break;
					case "Eat More":
						cbEatMore.setSelected(true);
						break;
					case "Take Bath":
						cbTakeBath.setSelected(true);
						break;
					default:
						tfOtherAdvice.setText(a.getdescription());
						break;
				}
		}
		this.followUpList = followList;
		for( PatientHistory f : followList )
		{
			if(f.getisUnderTreatment())
				switch( f.getremarks() )
				{
					case "Refer To Nurse":
						cbReferToNurse.setSelected(true);
						tfReferToNurse.setText(f.getdescription());
						break;
					case "Refer To Hospital":
						cbReferToHospital.setSelected(true);
						tfReferToHospital.setText(f.getdescription());
						break;
					default:
						tfOtherFollowUp.setText(f.getdescription());
						break;
				}
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
		tfChiefComplaint.setVisible(false);
		tvChiefComplaintTable.setEditable(false);
	}

	private void lockPMHScreenPane()
	{
		tfPMH.setVisible(false);
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

	private void lockHPI_FH_SHPane()
	{
		rbHPI.setDisable(true);
		rbFH.setDisable(true);
		rbSH.setDisable(true);
		cbHistoryDetails.setVisible(false);
		btnAddHistory.setVisible(false);
		btnDeleteHistory.setVisible(false);
		tvHPI_FH_SHTable.setEditable(false);
	}

	private void lockROSPane()
	{
		tvROSTable.setEditable(false);
	}

	private void lockPhysicalExaminationPane()
	{
		tvPETable.setEditable(false);
	}

	private void lockCD_InvestigationPane()
	{
		cbDiagnosis.setVisible(false);
		btnAddDiagnosis.setVisible(false);
		btnDeleteDiagnosis.setVisible(false);
		tvDiagnosisTable.setEditable(false);
		tfInvestigation.setVisible(false);
		btnAddInvestigation.setVisible(false);
		btnDeleteInvestigation.setVisible(false);
		tvInvestigationTable.setEditable(false);
	}

	private void lockM_A_FUPane()
	{
		cbMedicine.setVisible(false);
		tvPrescriptionTable.setEditable(false);
		btnAddPrescription.setVisible(false);
		btnDeletePrescription.setVisible(false);
		cbDrinkMoreWater.setDisable(true);
		cbEatMore.setDisable(true);
		cbSleepMore.setDisable(true);
		cbTakeBath.setDisable(true);
		tfOtherAdvice.setVisible(false);
		cbReferToNurse.setDisable(true);
		tfReferToNurse.setVisible(false);
		cbReferToHospital.setDisable(true);
		tfReferToHospital.setVisible(false);
		tfOtherFollowUp.setVisible(false);
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

	public void setConsultationPanelTabController( ConsultationPanelTabController ctrl )
	{
		this.panelCtrl = ctrl;
	}

	public void setSlum( long slumID )
	{
		//this.slumID = slumID;
	}

	public void isEdit( boolean isEdit )
	{
		this.isEditPatient = isEdit;
		if(!isEdit)
		{
			btnSubmitTop.setVisible(false);
			btnOKTop.setVisible(true);
			btnSubmit.setVisible(false);
			btnOK.setVisible(true);
			lockHPI_FH_SHPane();
			lockROSPane();
			lockPhysicalExaminationPane();
			lockCD_InvestigationPane();
			lockM_A_FUPane();
		}
		else
			btnResetPrescription.setVisible(true);
	}

	public void setComment( ArrayList<Comment> commentList )
	{
		long numOfComment = commentList.size();
		String msg;
		if(numOfComment == 0)
		{
			btnComment.setVisible(false);
			return;
		}
		else if(numOfComment == 1)
			msg = "There is 1 comment on this patient, do you want to read it now?";
		else
			msg = "There are "+numOfComment+" comments on this patient, do you want to read them now?";
		isCommentResponded = false;
		btnComment.setVisible(true);
		this.commentList = commentList;
		
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Comment", msg, AlertType.CONFIRMATION, 14);
		if(result.isPresent() && (result.get() == ButtonType.OK))
		{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentPanel.fxml"));
				Parent searchResult = loader.load();
				CommentPanelController commentCtrl = loader.<CommentPanelController>getController();
				commentCtrl.setCommentList(commentList, userID);
				commentCtrl.setConsultationPatientController(this);
				Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
				Stage stage = new Stage();
				stage.setScene(new Scene(searchResult));
				stage.setTitle("Comment on "+patientName);
				stage.getIcons().setAll(image);
				stage.sizeToScene();
				stage.setResizable(false);
				commentCtrl.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			return;
	}

	public void setIsCommentResponded( boolean isResponded )
	{
		this.isCommentResponded = isResponded;
		if(isResponded)
			btnComment.setVisible(false);
		else
			btnComment.setVisible(true);
	}

	public void resetCommentList()
	{
		this.commentList = DatabaseController.getCommentList(patientID);
	}

	private void showSummary()
	{
		yPosition = 10;
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

		showPatientName();
		/*ArrayList<PatientHistory> historyList = getNewHPI_FH_SHList();
		historyList.addAll(getOldHPI_FH_SHList());*/
		ArrayList<PatientHistory> CD_InvestigationList = new ArrayList<PatientHistory>();
		PatientHistory diagnosis = getThisVisitDiagnosis();
		if(diagnosis != null)
			CD_InvestigationList.add(diagnosis);
		CD_InvestigationList.addAll(getInvestigationList());
		showHistory("HPI, Family History & Social History", getNewHPI_FH_SHList(), tpHPI_FH_SH);
		showHistory("Review of System", getROSList(), tpROS);
		redFlagList = getRedFlagList();
		showHistory("Physical Examination", redFlagList, tpPhysicalExamination);
		showHistory(null, getPEList(), null);
		showHistory("Clinical Diagnosis & Investigation", CD_InvestigationList, tpCD_Investigation);

		ArrayList<Prescription> prescriptionList = getPrescriptionList();
		adviceFollowUpList = getAdviceList();
		adviceFollowUpList.addAll(getFollowUpList());		
		showMedication(prescriptionList, adviceFollowUpList, tpM_A_FU);

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
	}

	private void showPatientName()
	{
		Label patientNameLabel = new Label(patientName);
		patientNameLabel.setLayoutX(10);
		patientNameLabel.setLayoutY(yPosition+=30);
		patientNameLabel.setFont(new Font("System Bold", 18));
		patientNameLabel.setPrefWidth(850);
		patientNameLabel.setAlignment(Pos.CENTER);
		submissionPane.getChildren().add(patientNameLabel);
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
							aConsultationPatient.getPanes().remove( tpSubmission );
							aConsultationPatient.getPanes().add( tp );
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
			String s = type;
			if(ph.getdescription().isEmpty())
				s += "NAD";
			else
				s += ph.getdescription();
			Label historyLabel = new Label(s);
			historyLabel.setLayoutX(10);
			historyLabel.setLayoutY(yPosition+=30);
			historyLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(historyLabel);
		}
	}

	private void showMedication(ArrayList<Prescription> preList, ArrayList<PatientHistory> advFollowList, TitledPane tp)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Medication, Advice and Follow-Up");
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
						aConsultationPatient.getPanes().remove( tpSubmission );
						aConsultationPatient.getPanes().add( tp );
						tp.setExpanded(true);
					}
				}
			}
		);
		submissionPane.getChildren().add(subtitleLabel);

		if(preList.size() == 0)
		{
			Label noneLabel = new Label("No prescription made.");
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			noneLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(noneLabel);
		}
		for(int i = 0; i < preList.size(); i++)
		{
			Prescription p = preList.get(i);
			String line = (i+1)+". "+p.getmedicine()+": ";
			line += p.getdosage()+"mg/ml each time\n     "+p.getfrequency()+" times/day, "+p.getnumOfDays()+" days.\n";
			line += "     Route: "+p.getroute();
			Label noneLabel = new Label(line);
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			yPosition+=30;
			noneLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(noneLabel);
		}

		long advFollowCount = 0;
		for(PatientHistory ph : advFollowList)
		{
			if(!ph.getisActive())
				continue;
			advFollowCount++;
			String type;
			switch(ph.gethistoryType())
			{
				case PatientHistory.TYPE_ADV:
					type = "Advice: ";
					break;
				case PatientHistory.TYPE_FOL:
					if(ph.getremarks().isEmpty())
						type = "Follow-Up: ";
					else
						type = ph.getremarks() +": ";
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
		if(advFollowCount == 0)
		{
			Label noneLabel = new Label("No advice or follow-up action should be taken.");
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			noneLabel.setFont(new Font("System Bold", 14));
			submissionPane.getChildren().add(noneLabel);
		}
	}

	@Override
	public void handle(KeyEvent event)
	{
		// TODO: handle page up page down later
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