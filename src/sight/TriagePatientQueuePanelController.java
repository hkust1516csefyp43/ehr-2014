package sight;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class TriagePatientQueuePanelController implements Initializable {

	private TriagePanelController ctrl;
	@FXML private Label lPatientNum;
	@FXML private TableView<PatientVisit> tvTodayPatientTable;
	@FXML private TableColumn<PatientVisit, Integer> tbTagNumber;
	@FXML private TableColumn<PatientVisit, String> tbPatientName;
	@FXML private TableColumn<PatientVisit, String> tbTime;
	@FXML private TableColumn<PatientVisit, String> tbNurse;
	@FXML private TableColumn<PatientVisit, String> tbDoctor;
	@FXML private TableColumn<PatientVisit, String> tbPharmacist;
	
	private ArrayList<PatientVisit> patientForConsultationQueueList;
	private ArrayList<PatientVisit> patientForPharmacyQueueList;
	private ArrayList<PatientVisit> patientFinishedQueueList;
	//private long slumID = -1;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
	}	

	@FXML
	public void btnStartNewPatientPressed( ActionEvent Event )
	{
		ctrl.startNewPatient();
	}

	@FXML
	public void btnViewPatientPressed( ActionEvent Event )
	{
		PatientVisit pv = tvTodayPatientTable.getSelectionModel().getSelectedItem();
		if( pv != null )
			ctrl.editPatient( pv, false );
	}

	@FXML
	public void btnEditPatientPressed( ActionEvent Event )
	{
		PatientVisit pv = tvTodayPatientTable.getSelectionModel().getSelectedItem();
		if( pv == null )
			return;
		PatientVisit update = DatabaseController.getPatientVisit(pv.getvisitID());
		if( update.getisConsulted() )
		{
			Label message = new Label("You cannot edit consulted patient record.");
			Stage stage = new Stage();
			stage.setScene( new Scene( message, 250, 100 ) );
			stage.sizeToScene();
			stage.show();
		}
		else
		{
			//TODO: Reset status before editing(if do in this way, click to other panel=GG
			//DatabaseController.updatePatientVisit(pv.getvisitID(), pv.getnurseID(), false, false, false);
			ctrl.editPatient( pv, true );
		}
	}

	private void prepareQueueTable()
	{
		tvTodayPatientTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbTagNumber.setCellValueFactory( new PropertyValueFactory<>("tagNumber") );
		//tbTagNumber.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPatientName.setCellValueFactory( new PropertyValueFactory<>("patientName") );
		tbPatientName.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbTime.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbTime.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNurse.setCellValueFactory( new PropertyValueFactory<>("nurseName") );
		tbNurse.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDoctor.setCellValueFactory( new PropertyValueFactory<>("doctorName") );
		tbDoctor.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPharmacist.setCellValueFactory( new PropertyValueFactory<>("pharmacistName") );
		tbPharmacist.setCellFactory( TextFieldTableCell.forTableColumn() );
		tvTodayPatientTable.setEditable( false );
	}

	private void addToQueue( ArrayList<PatientVisit> pqList )
	{
		ObservableList<PatientVisit> currentQueueList = tvTodayPatientTable.getItems();
		currentQueueList.addAll( pqList );
		tvTodayPatientTable.setItems( currentQueueList );
		java.util.Collections.sort
		(
			tvTodayPatientTable.getItems(),
			new java.util.Comparator<PatientVisit>()
			{
			    @Override
			    public int compare(PatientVisit pv1, PatientVisit pv2)
			    {
			    	if( pv1.gettagNumber() < pv2.gettagNumber() )
			    		return 1;
			    	else if( pv1.gettagNumber() > pv2.gettagNumber() )
			    		return -1;
			    	else
			    		return 0;
			    }
			}
		);
	}

	public void setTriagePanelController( TriagePanelController ctrl )
	{
		this.ctrl = ctrl;
	}

	public void setTable( int slumID )
	{
		//this.slumID = slumID;
		patientForConsultationQueueList = DatabaseController.getPatientQueue( slumID, true, false, false );
		patientForPharmacyQueueList = DatabaseController.getPatientQueue( slumID, true, true, false );
		patientFinishedQueueList = DatabaseController.getPatientQueue( slumID, true, true, true );
		prepareQueueTable();
		addToQueue(patientForConsultationQueueList);
		addToQueue(patientForPharmacyQueueList);
		addToQueue(patientFinishedQueueList);
		long totalPatientNum = patientForConsultationQueueList.size() + patientForPharmacyQueueList.size() + patientFinishedQueueList.size();
		lPatientNum.setText("Number of Patient Today: "+totalPatientNum);
	}
}
