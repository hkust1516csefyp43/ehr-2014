package sight;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ConsultationPatientVisitSearchResultPanelController implements Initializable {

	private ConsultationPanelController ctrl;
	@FXML private Label lPatientNum;
	@FXML private TableView<PatientVisit> tvSearchResultTable;
	@FXML private TableColumn<PatientVisit, String> tbVisitDate;
	@FXML private TableColumn<PatientVisit, String> tbVisitNurse;
	@FXML private TableColumn<PatientVisit, String> tbVisitDoctor;
	@FXML private TableColumn<PatientVisit, String> tbVisitPharmacist;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		prepareSearchResultTable();
	}	

	@FXML
	public void btnViewPatientVisitPressed( ActionEvent Event )
	{
		PatientVisit pv = tvSearchResultTable.getSelectionModel().getSelectedItem();
		if(pv == null)
			return;
		ctrl.viewPatientVisit(pv.getvisitID(), pv.getvisitDateTimeString());
		Stage stage = (Stage) tvSearchResultTable.getScene().getWindow();
        stage.close();
	}

	private void prepareSearchResultTable()
	{
		tvSearchResultTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tbVisitDate.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbVisitNurse.setCellValueFactory( new PropertyValueFactory<>("nurseName") );
		tbVisitDoctor.setCellValueFactory( new PropertyValueFactory<>("doctorName") );
		tbVisitPharmacist.setCellValueFactory( new PropertyValueFactory<>("pharmacistName") );
		tvSearchResultTable.setEditable( false );
	}

	public void setConsultationPanelController( ConsultationPanelController ctrl )
	{
		this.ctrl = ctrl;
	}

	public void setPatientVisitList( ArrayList<PatientVisit> pList )
	{
		ObservableList<PatientVisit> patientList = tvSearchResultTable.getItems();
		patientList.addAll(pList);
		tvSearchResultTable.setItems( patientList );
	}
}
