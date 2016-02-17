package sight;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ConsultationPatientSearchResultPanelController implements Initializable {

	private ConsultationPanelController ctrl;
	@FXML private Label lPatientNum;
	@FXML private TableView<Patient> tvSearchResultTable;
	@FXML private TableColumn<Patient, String> tbPatientFirstName;
	//@FXML private TableColumn<Patient, String> tbPatientMiddleName;
	@FXML private TableColumn<Patient, String> tbPatientLastName;
	@FXML private TableColumn<Patient, Integer> tbPatientAge;
	@FXML private TableColumn<Patient, String> tbPatientSex;

	private ConsultationPatientVisitSearchResultPanelController resultCtrl;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		prepareSearchResultTable();
	}	

	@FXML
	public void btnSelectPatientPressed( ActionEvent Event )
	{
		Patient p = tvSearchResultTable.getSelectionModel().getSelectedItem();
		if(p == null)
			return;
		try {
			ArrayList<PatientVisit> patientVisitList = DatabaseController.getPatientVisitList( p.getpatientID(), false );
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientVisitSearchResultPanel.fxml"));
			Parent searchResult = loader.load();
			resultCtrl = loader.<ConsultationPatientVisitSearchResultPanelController>getController();
			resultCtrl.setConsultationPanelController(ctrl);
			resultCtrl.setPatientVisitList(patientVisitList);
			Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
			Stage stage = new Stage();
			stage.setScene(new Scene(searchResult));
			stage.setTitle("Select the visit you are looking for...");
			stage.getIcons().setAll(image);
			stage.sizeToScene();
			stage.setResizable(false);
			stage.show();
			Stage curStage = (Stage) tvSearchResultTable.getScene().getWindow();
			curStage.close();
		} catch (Exception e) {
			e.printStackTrace();
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

	public void setConsultationPanelController( ConsultationPanelController ctrl )
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
