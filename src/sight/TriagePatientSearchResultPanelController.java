package sight;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class TriagePatientSearchResultPanelController implements Initializable {

	private TriagePanelController ctrl;
	@FXML private Label lPatientNum;
	@FXML private TableView<Patient> tvSearchResultTable;
	@FXML private TableColumn<Patient, String> tbPatientFirstName;
	//@FXML private TableColumn<Patient, String> tbPatientMiddleName;
	@FXML private TableColumn<Patient, String> tbPatientLastName;
	@FXML private TableColumn<Patient, Integer> tbPatientAge;
	@FXML private TableColumn<Patient, String> tbPatientSex;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		prepareSearchResultTable();
	}	

	@FXML
	public void btnStartPatientPressed( ActionEvent Event )
	{
		boolean isConfirmed = false;
		if(!ctrl.getIsSwitchable())
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			alert.setContentText("Confirm to leave this page? (Changes will NOT be saved)");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			Patient p = tvSearchResultTable.getSelectionModel().getSelectedItem();
			long patientID = p.getpatientID();
			FemaleRecord fr = DatabaseController.getFemaleRecord( patientID );
			ArrayList<PatientHistory> ccList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CC );
			ArrayList<PatientHistory> pmhList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_PMH );
			ArrayList<PatientHistory> scList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_SCR );
			ArrayList<PatientHistory> drugList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_DH );
			ArrayList<PatientHistory> allergyList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_ALE );
			ctrl.startOldPatient( p, fr, ccList, pmhList, scList, drugList, allergyList );
			Stage stage = (Stage) tvSearchResultTable.getScene().getWindow();
	        stage.close();
		}
	}

	private void prepareSearchResultTable()
	{
		tvSearchResultTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbPatientFirstName.setCellValueFactory( new PropertyValueFactory<>("firstName") );
		//tbPatientMiddleName.setCellValueFactory( new PropertyValueFactory<>("middleName") );
		tbPatientLastName.setCellValueFactory( new PropertyValueFactory<>("lastName") );
		tbPatientAge.setCellValueFactory( new PropertyValueFactory<>("ageYear") );
		tbPatientSex.setCellValueFactory( new PropertyValueFactory<>("sex") );
		tvSearchResultTable.setEditable( false );
	}

	public void setTriagePanelController( TriagePanelController ctrl )
	{
		this.ctrl = ctrl;
	}

	public void setPatientList( ArrayList<Patient> pList )
	{
		ObservableList<Patient> patientList = tvSearchResultTable.getItems();
		patientList.addAll(pList);
		tvSearchResultTable.setItems( patientList );
	}
}
