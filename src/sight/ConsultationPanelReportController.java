package sight;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;








import javafx.event.ActionEvent;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class ConsultationPanelReportController implements Initializable
{	
	@FXML private SplitPane ConsultancySplitPane;
	@FXML private ComboBox<String> cbFirstName;
	@FXML private ComboBox<String> cbLastName;
	@FXML private Label lToday;

	private ConsultationPanelController panelCtrl;
	private ConsultationPatientSearchResultPanelController resultCtrl;
	private int slumID = -1;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
	}

	public void setConsultationPanelController( ConsultationPanelController ctrl )
	{
		this.panelCtrl = ctrl;
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy", java.util.Locale.ENGLISH);
		lToday.setText(sdf.format(new java.util.Date()));
	}

	@FXML
	public void btnSearchPressed( ActionEvent Event )
	{
		if ( cbFirstName.getEditor().getText().trim().length() != 0 || cbLastName.getEditor().getText().trim().length() != 0 )
		{
			try {
				ArrayList<Patient> patientList = DatabaseController.getPatient( slumID, cbFirstName.getEditor().getText().trim(), cbLastName.getEditor().getText().trim() );
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientSearchResultPanel.fxml"));
				Parent searchResult = loader.load();
				resultCtrl = loader.<ConsultationPatientSearchResultPanelController>getController();
				resultCtrl.setConsultationPanelController(panelCtrl);
				resultCtrl.setPatientList(patientList);
				Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
				Stage stage = new Stage();
				stage.setScene(new Scene(searchResult));
				stage.setTitle("Select the patient you are looking for...");
				stage.getIcons().setAll(image);
				stage.sizeToScene();
				stage.setResizable(false);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("One-2-One Medical System");
			alert.setHeaderText("Missing Information!");
			alert.setContentText("Please input the patient name.");
			alert.showAndWait();
		}
	}

	public void setSlum( int slumID )
	{
		this.slumID = slumID;
		cbFirstName.setEditable(true);
		cbFirstName.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbFirstName, DatabaseController.getPatientFirstNameList(slumID) ) );
		cbLastName.setEditable(true);
		cbLastName.setOnKeyReleased( new AutoCompleteComboBoxListener<>( cbLastName, DatabaseController.getPatientLastNameList(slumID) ) );
	}
}
