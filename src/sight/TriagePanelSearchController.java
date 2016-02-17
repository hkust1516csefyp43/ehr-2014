package sight;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class TriagePanelSearchController implements Initializable
{
	@FXML private SplitPane TriageSplitPane;
	@FXML private ComboBox<String> cbFirstName;
	@FXML private ComboBox<String> cbLastName;
	@FXML private Label lToday;
	@FXML private Button btnSearch;

	private TriagePanelController panelCtrl;
	private TriagePatientSearchResultPanelController resultCtrl;
	private int slumID = -1;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
	}

	public void setTriagePanelController( TriagePanelController ctrl )
	{
		this.panelCtrl = ctrl;
		SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy", java.util.Locale.ENGLISH);
		lToday.setText(sdf.format(new java.util.Date()));
	}

	@FXML
	public void GetIDfromfingerprintScanner( ActionEvent Event )
	{
		boolean isConfirmed = false;
		if(!panelCtrl.getIsSwitchable())
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
			try
			{
				//FileInputStream in = new FileInputStream( "C://sight/fpPrint.txt" );
				Scanner scanner = new Scanner(new File("C://sight//fpPrint.txt"));
				//long patientID = in.read();
				long patientID = scanner.nextLong();
				if( patientID == -1 )
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("One-2-One Medical System");
					alert.setHeaderText("Not Found!");
					alert.setContentText("Patient not found.");
					alert.showAndWait();
					//in.close();
					scanner.close();
					return;
				}
				//in.close();
				scanner.close();
				Patient p = DatabaseController.getPatient( patientID );
				FemaleRecord fr = DatabaseController.getFemaleRecord( patientID );
				ArrayList<PatientHistory> ccList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_CC );
				ArrayList<PatientHistory> pmhList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_PMH );
				ArrayList<PatientHistory> scList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_SCR );
				ArrayList<PatientHistory> drugList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_DH );
				ArrayList<PatientHistory> allergyList = DatabaseController.getPatientHistoryList( patientID, PatientHistory.TYPE_ALE );
				panelCtrl.startOldPatient( p, fr, ccList, pmhList, scList, drugList, allergyList );
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(TriagePanelSearchController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@FXML
	public void btnSearchPressed( ActionEvent Event )
	{
		if ( cbFirstName.getEditor().getText().trim().length() != 0 || cbLastName.getEditor().getText().trim().length() != 0 )
		{
			try {
				ArrayList<Patient> patientList = DatabaseController.getPatient( slumID, cbFirstName.getEditor().getText().trim(), cbLastName.getEditor().getText().trim() );
				ArrayList<PatientVisit> todayVisitList = DatabaseController.getPatientQueue( slumID, true, false, false );
				todayVisitList.addAll(DatabaseController.getPatientQueue( slumID, true, true, false ));
				//todayVisitList.addAll(DatabaseController.getPatientQueue( slumID, true, true, true ));
				for(int i = 0; i < patientList.size(); i++)
				{
					for(int j = 0; j < todayVisitList.size(); j++)
						if(patientList.get(i).getpatientID() == todayVisitList.get(j).getpatientID())
						{
							patientList.remove(i);
							i -= 1;
							todayVisitList.remove(j);
							break;
						}
				}
				FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePatientSearchResultPanel.fxml"));
				Parent searchResult = loader.load();
				resultCtrl = loader.<TriagePatientSearchResultPanelController>getController();
				resultCtrl.setTriagePanelController(panelCtrl);
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
