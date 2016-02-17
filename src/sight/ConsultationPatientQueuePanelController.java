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
import javafx.scene.control.Tab;
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

public class ConsultationPatientQueuePanelController implements Initializable {

	private ConsultationPanelTabController ctrl;
	@FXML private Tab currentTab;
	@FXML private TableView<PatientVisit> tvPatientQueueTable;
	@FXML private TableColumn<PatientVisit, Integer> tbTagNumber;
	@FXML private TableColumn<PatientVisit, String> tbPatientName;
	@FXML private TableColumn<PatientVisit, String> tbTime;
	@FXML private TableColumn<PatientVisit, String> tbNurse;
	@FXML private TableView<PatientVisit> tvTodayPatientTable;
	@FXML private TableColumn<PatientVisit, Integer> tbTagNumber2;
	@FXML private TableColumn<PatientVisit, String> tbPatientName2;
	@FXML private TableColumn<PatientVisit, String> tbTime2;
	@FXML private TableColumn<PatientVisit, String> tbNurse2;
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
	public void btnStartPatientPressed( ActionEvent Event )
	{
		PatientVisit pv = tvPatientQueueTable.getSelectionModel().getSelectedItem();
		if(pv == null)
			return;
		PatientVisit update = DatabaseController.getPatientVisit(pv.getvisitID());
		if( update.getisTriage() )
			ctrl.startPatient( currentTab, pv );
		else
		{
			Label message = new Label("Please refresh the panel.");
			Stage stage = new Stage();
			stage.setScene( new Scene( message ) );
			stage.sizeToScene();
			stage.show();
		}
	}

	@FXML
	public void btnViewPatientPressed( ActionEvent Event )
	{
		PatientVisit pv = tvTodayPatientTable.getSelectionModel().getSelectedItem();
		if( pv != null )
			ctrl.editPatient( currentTab, pv, false );
	}

	@FXML
	public void btnEditPatientPressed( ActionEvent Event )
	{
		PatientVisit pv = tvTodayPatientTable.getSelectionModel().getSelectedItem();
		if( pv == null)
			return;
		PatientVisit update = DatabaseController.getPatientVisit(pv.getvisitID());
		if( update.getisTriage() )
		{
			//Reset status before editing(if do in this way, click to other panel=GG
			//DatabaseController.updatePatientVisit(pv.getvisitID(), pv.getnurseID(), true, false, false);
			ctrl.editPatient( currentTab, pv, true );
		}
		else if( update.getisFinished() )
		{
			Label message = new Label("You cannot edit finished patient record..");
			Stage stage = new Stage();
			stage.setScene( new Scene( message, 200, 100 ) );
			stage.sizeToScene();
			stage.show();
		}
		else
		{
			Label message = new Label("Please refresh the panel.");
			Stage stage = new Stage();
			stage.setScene( new Scene( message, 200, 100 ) );
			stage.sizeToScene();
			stage.show();
		}
	}

	private void fillPatientQueueTable( ArrayList<PatientVisit> pqList )
	{
		tvPatientQueueTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbTagNumber.setCellValueFactory( new PropertyValueFactory<>("tagNumber") );
		//tbNumberTag.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPatientName.setCellValueFactory( new PropertyValueFactory<>("patientName") );
		tbPatientName.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbTime.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbTime.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNurse.setCellValueFactory( new PropertyValueFactory<>("nurseName") );
		tbNurse.setCellFactory( TextFieldTableCell.forTableColumn() );
		ObservableList<PatientVisit> currentQueueList = tvPatientQueueTable.getItems();
		currentQueueList.addAll( pqList );
		tvPatientQueueTable.setItems( currentQueueList );
		tvPatientQueueTable.setEditable( false );
		java.util.Collections.sort
		(
			tvPatientQueueTable.getItems(),
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

	private void fillTodayPatientTable( ArrayList<PatientVisit> pqList )
	{
		tvTodayPatientTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbTagNumber2.setCellValueFactory( new PropertyValueFactory<>("tagNumber") );
		//tbNumberTag2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPatientName2.setCellValueFactory( new PropertyValueFactory<>("patientName") );
		tbPatientName2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbTime2.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbTime2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNurse2.setCellValueFactory( new PropertyValueFactory<>("nurseName") );
		tbNurse2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDoctor.setCellValueFactory( new PropertyValueFactory<>("doctorName") );
		tbDoctor.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPharmacist.setCellValueFactory( new PropertyValueFactory<>("pharmacistName") );
		tbPharmacist.setCellFactory( TextFieldTableCell.forTableColumn() );
		ObservableList<PatientVisit> currentQueueList = tvTodayPatientTable.getItems();
		currentQueueList.addAll( pqList );
		tvTodayPatientTable.setItems( currentQueueList );
		tvTodayPatientTable.setEditable( false );
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

	public void setCurrentTab( Tab currentTab )
	{
		this.currentTab = currentTab;
	}

	public void setConsultancyPanelTabController( ConsultationPanelTabController ctrl )
	{
		this.ctrl = ctrl;
	}

	public void setTable( int slumID )
	{
		//this.slumID = slumID;
		patientForConsultationQueueList = DatabaseController.getPatientQueue( slumID, true, false, false );
		fillPatientQueueTable( patientForConsultationQueueList );
		patientForPharmacyQueueList = DatabaseController.getPatientQueue( slumID, true, true, false );
		fillTodayPatientTable( patientForPharmacyQueueList );
		patientFinishedQueueList = DatabaseController.getPatientQueue( slumID, true, true, true );
		fillTodayPatientTable( patientFinishedQueueList );
	}
}
