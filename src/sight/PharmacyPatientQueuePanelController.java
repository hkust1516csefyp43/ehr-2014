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

public class PharmacyPatientQueuePanelController implements Initializable {

	private PharmacyPanelController ctrl;
	@FXML private TableView<PatientVisit> tvPatientQueueTable;
	@FXML private TableColumn<PatientVisit, Integer> tbTagNumber;
	@FXML private TableColumn<PatientVisit, String> tbPatientName;
	@FXML private TableColumn<PatientVisit, String> tbTime;
	@FXML private TableColumn<PatientVisit, String> tbNurse;
	@FXML private TableColumn<PatientVisit, String> tbDoctor;
	@FXML private TableView<PatientVisit> tvTodayPatientTable;
	@FXML private TableColumn<PatientVisit, Integer> tbTagNumber2;
	@FXML private TableColumn<PatientVisit, String> tbPatientName2;
	@FXML private TableColumn<PatientVisit, String> tbTime2;
	@FXML private TableColumn<PatientVisit, String> tbNurse2;
	@FXML private TableColumn<PatientVisit, String> tbDoctor2;
	@FXML private TableColumn<PatientVisit, String> tbPharmacist;
	
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
		PatientVisit update = DatabaseController.getPatientVisit(pv.getvisitID());
		if( update.getisConsulted() )
			ctrl.startPatient( pv, false );
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
			ctrl.startPatient( pv, true );
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
		tbDoctor.setCellValueFactory( new PropertyValueFactory<>("doctorName") );
		tbDoctor.setCellFactory( TextFieldTableCell.forTableColumn() );
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
		//tbNumberTag.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbPatientName2.setCellValueFactory( new PropertyValueFactory<>("patientName") );
		tbPatientName2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbTime2.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbTime2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbNurse2.setCellValueFactory( new PropertyValueFactory<>("nurseName") );
		tbNurse2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDoctor2.setCellValueFactory( new PropertyValueFactory<>("doctorName") );
		tbDoctor2.setCellFactory( TextFieldTableCell.forTableColumn() );
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

	public void setPharmacyPanelController( PharmacyPanelController ctrl )
	{
		this.ctrl = ctrl;
	}

	public void setTable( int slumID )
	{
		//this.slumID = slumID;
		patientForPharmacyQueueList = DatabaseController.getPatientQueue( slumID, true, true, false );
		fillPatientQueueTable( patientForPharmacyQueueList );
		patientFinishedQueueList = DatabaseController.getPatientQueue( slumID, true, true, true );
		fillTodayPatientTable( patientFinishedQueueList );
	}
}
