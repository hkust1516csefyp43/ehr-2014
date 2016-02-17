package sight;

//import java.io.IOException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ConsultationPanelTabController implements Initializable {
	
	@FXML private TabPane ConsultancyTabPane;
	@FXML private Tab NewTab;
	private ConsultationPanelController panelCtrl;
	private ArrayList<ConsultationPatientTabController> consultancyTabControllerList;
	private ConsultationPatientQueuePanelController patientQueueCtrl;
	private int tabCount = 0;
	private int slumID = -1;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		consultancyTabControllerList = new ArrayList<>();
		ConsultancyTabPane.tabClosingPolicyProperty().set(TabPane.TabClosingPolicy.SELECTED_TAB);
		createQueuePanel(true);
		ConsultancyTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			@Override
			public void changed(ObservableValue<? extends Tab> observable,
					Tab Tab1, Tab Tab2){
				if(Tab2==NewTab){
					createQueuePanel(false);
				}
			}
		});
	}	
	
	private void createQueuePanel(boolean isFirstTab)
	{
		Tab tab = new Tab("New Patient");
		Pane node = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientQueuePanel.fxml"));
			node = loader.load();
			tab.setId(""+tabCount);
			tabCount++;
			patientQueueCtrl = loader.<ConsultationPatientQueuePanelController>getController();
			patientQueueCtrl.setCurrentTab( tab );
			patientQueueCtrl.setConsultancyPanelTabController( this );
			if(!isFirstTab)
				patientQueueCtrl.setTable( slumID );
		}catch(Exception e){
			e.printStackTrace();
		}
		tab.setContent(node);
		tab.setClosable(true);
		ObservableList<Tab> Tabs = ConsultancyTabPane.getTabs();
		tab.closableProperty().bind(Bindings.size(Tabs).greaterThan(2));
		Tabs.add(Tabs.size() - 1, tab);
		
		ConsultancyTabPane.getSelectionModel().select(tab);
	}

	public void setConsultancyPanelController( ConsultationPanelController ctrl )
	{
		this.panelCtrl = ctrl;
	}

	public void startPatient( Tab workingTab, PatientVisit pv )
	{
		long patientID = pv.getpatientID();
		Patient p = DatabaseController.getPatient( patientID );
		FemaleRecord fr = DatabaseController.getFemaleRecord( patientID );
		TriageRecord tr = DatabaseController.getTriageRecord( pv.getvisitID() );
		ArrayList<PatientHistory> ccList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CC );
		ArrayList<PatientHistory> pmhList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_PMH );
		ArrayList<PatientHistory> scList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_SCR );
		ArrayList<PatientHistory> drugList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_DH );
		ArrayList<PatientHistory> allergyList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_ALE );
		ArrayList<PatientHistory> hpi_fh_shList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_HPI, PatientHistory.TYPE_FH, PatientHistory.TYPE_SH );
		ArrayList<PatientHistory> diagnosisList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CD );
		ArrayList<Comment> commentList = DatabaseController.getCommentList(patientID);
		Accordion node = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientTab.fxml"));
			node = loader.load();
			ConsultationPatientTabController ptc = loader.<ConsultationPatientTabController>getController();
			workingTab.setText( pv.getpatientName() );
			ptc.setCurrentTab( workingTab );
			ptc.setTabID( workingTab.getId() );
			ptc.setConsultationPanelTabController( this );
			ptc.setPatient( p, pv.getpatientName() );
			ptc.setVisitID( pv.getvisitID() );
			ptc.setTriageRecord( tr );
			ptc.setFemaleRecord( fr );
			ptc.setHistory( ccList, pmhList, scList, drugList, allergyList, hpi_fh_shList, diagnosisList );
			ptc.setSlum( slumID );
			ptc.setComment( commentList );
			for(PatientHistory cc : ccList)
				if(cc.getvisitID() == pv.getvisitID())
					showChiefComplaintWindow(pv.getpatientName(), cc.getdescription());
			consultancyTabControllerList.add( ptc );
			//patientTabController.setConsultancyPanelTabController( this );
			panelCtrl.setIsSwitchable(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		workingTab.setContent(node);
	}

	public void editPatient( Tab workingTab, PatientVisit pv, boolean isEdit )
	{
		long patientID = pv.getpatientID();
		Patient p = DatabaseController.getPatient( patientID );
		FemaleRecord fr = DatabaseController.getFemaleRecord( patientID );
		TriageRecord tr = DatabaseController.getTriageRecord( pv.getvisitID() );
		ArrayList<PatientHistory> ccList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CC );
		ArrayList<PatientHistory> pmhList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_PMH );
		ArrayList<PatientHistory> scList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_SCR );
		ArrayList<PatientHistory> drugList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_DH );
		ArrayList<PatientHistory> allergyList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_ALE );
		ArrayList<PatientHistory> hpi_fh_shList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_HPI, PatientHistory.TYPE_FH, PatientHistory.TYPE_SH );
		ArrayList<PatientHistory> rosList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_ROS );
		for(PatientHistory ros : rosList)
			if(ros.getdescription().isEmpty())
				ros.setisNAD(true);
		ArrayList<PatientHistory> peList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_PE );
		for(PatientHistory pe : peList)
			if(pe.getdescription().isEmpty())
				pe.setisNAD(true);
		ArrayList<PatientHistory> redFlagList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_RF );
		ArrayList<PatientHistory> investgationList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_INV );
		ArrayList<PatientHistory> diagnosisList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CD );
		ArrayList<Prescription> preList = DatabaseController.getPrescriptionList( pv.getvisitID(), false );
		ArrayList<Prescription> preList2 = DatabaseController.getPrescriptionList( pv.getvisitID(), true );
		preList.addAll(preList2);
		ArrayList<PatientHistory> advList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_ADV );
		ArrayList<PatientHistory> followList = DatabaseController.getPatientHistoryList( patientID, pv.getvisitID(), PatientHistory.TYPE_FOL );
		ArrayList<Comment> commentList = DatabaseController.getCommentList(patientID);
		Accordion node = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientTab.fxml"));
			node = loader.load();
			ConsultationPatientTabController ptc = loader.<ConsultationPatientTabController>getController();
			workingTab.setText( pv.getpatientName() );
			ptc.isEdit( isEdit );
			ptc.setCurrentTab( workingTab );
			ptc.setTabID( workingTab.getId() );
			ptc.setConsultationPanelTabController( this );
			ptc.setPatient( p, pv.getpatientName() );
			ptc.setVisitID( pv.getvisitID() );
			ptc.setTriageRecord( tr );
			ptc.setFemaleRecord( fr );
			ptc.setHistory( ccList, pmhList, scList, drugList, allergyList, hpi_fh_shList, diagnosisList );
			ptc.setHistoryForViewOrEditPatient(rosList, redFlagList, peList, investgationList, preList, advList, followList );
			ptc.setSlum( slumID );
			ptc.setComment( commentList );
			for(PatientHistory cc : ccList)
				if(cc.getvisitID() == pv.getvisitID())
					showChiefComplaintWindow(pv.getpatientName(), cc.getdescription());
			consultancyTabControllerList.add( ptc );
			//patientTabController.setConsultancyPanelTabController( this );
			if(isEdit)
				panelCtrl.setIsSwitchable(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		workingTab.setContent(node);
	}

	public void finishPatient(Tab workingTab)
	{
		//panelCtrl.resetSpecialFunction();
		Tab tab = new Tab("New Patient");
		Pane node = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientQueuePanel.fxml"));
			node = loader.load();
			tab.setId(""+tabCount);
			tabCount++;
			patientQueueCtrl = loader.<ConsultationPatientQueuePanelController>getController();
			patientQueueCtrl.setCurrentTab( tab );
			patientQueueCtrl.setConsultancyPanelTabController( this );
			patientQueueCtrl.setTable(slumID);
		}catch(Exception e){
			e.printStackTrace();
		}
		tab.setContent(node);
		tab.setClosable(true);
		ObservableList<Tab> tabs = ConsultancyTabPane.getTabs();
		tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
		tabs.add(tabs.size() - 1, tab);
		tabs.remove(workingTab);
		ConsultancyTabPane.getSelectionModel().select(tab);
		panelCtrl.setIsSwitchable(true);
	}

	public void viewPatientVisit(long visitID, String visitDate)
	{
		Tab tab = new Tab("Report_"+visitDate);
		ScrollPane node = null;
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientVisitReport.fxml"));
			node = loader.load();
			loader.<ConsultationPatientVisitReportController>getController().setPatientVisit(visitID);
			tab.setId(""+tabCount);
			tabCount++;
		}catch(Exception e){
			e.printStackTrace();
		}
		tab.setContent(node);
		tab.setClosable(true);
		ObservableList<Tab> tabs = ConsultancyTabPane.getTabs();
		tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
		tabs.add(tabs.size() - 1, tab);
		ConsultancyTabPane.getSelectionModel().select(tab);
	}

	/*public void addSpecialFunction(String functionName, String patientName, String content)
	{
		panelCtrl.addSpecialFunction(functionName, patientName, content);
	}*/

	public void showChiefComplaintWindow(String curPatientName, String curPatientCC)
	{
		try
		{
			Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SpecialFunctionWindow.fxml"));
			Parent specialFunctionWindow = loader.load();
			SpecialFunctionWindowController sfwCtrl = loader.<SpecialFunctionWindowController>getController();
			sfwCtrl.setData(curPatientName, curPatientCC);
			Stage stage = new Stage();
			stage.setScene(new Scene(specialFunctionWindow));
			stage.setTitle("One-2-One Medical System - Chief Complaint");
			stage.getIcons().setAll(image);
			stage.setMinHeight(200);
			stage.setMinWidth(500);
			stage.show();
		} catch (IOException e) { e.printStackTrace(); }
	}
	public void setSlum( int slumID )
	{
		this.slumID = slumID;
		patientQueueCtrl.setTable( slumID );
	}
}
