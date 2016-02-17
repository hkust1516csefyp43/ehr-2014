package sight;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class PharmacyPanelController implements Initializable {

	@FXML private SplitPane pharmacySplitPane;
	private PharmacyPatientQueuePanelController patientQueueCtrl;
	private PharmacyPatientController patientCtrl;
	private int slumID = -1;
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PharmacyPatientQueuePanel.fxml"));
			Parent page = loader.load();
			patientQueueCtrl = loader.<PharmacyPatientQueuePanelController>getController();
			pharmacySplitPane.getItems().add(page);
			patientQueueCtrl.setPharmacyPanelController(this);
		} catch(Exception e){ e.printStackTrace(); }
	}

	public void startPatient( PatientVisit pv, boolean isView )
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PharmacyPatient.fxml"));
			Parent page = loader.load();
			patientCtrl = loader.<PharmacyPatientController>getController();
			patientCtrl.setPatientVisit(pv);
			//patientCtrl.setSlum(slumID);
			patientCtrl.isView(isView);
			pharmacySplitPane.getItems().set(0, page);
			patientCtrl.setPharmacyPanelController(this);
		} catch(Exception e){ e.printStackTrace(); }
	}

	public void finishPatient()
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PharmacyPatientQueuePanel.fxml"));
			Parent page = loader.load();
			patientQueueCtrl = loader.<PharmacyPatientQueuePanelController>getController();
			pharmacySplitPane.getItems().set(0, page);
			patientQueueCtrl.setPharmacyPanelController(this);
			patientQueueCtrl.setTable(slumID);
		} catch(Exception e){ e.printStackTrace(); }
	}

	public void setSlum(int slumID)
	{
		this.slumID = slumID;
		patientQueueCtrl.setTable(slumID);
	}
}
