package sight;

//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class TriagePanelController implements Initializable {
	
	@FXML private SplitPane triageSplitPane;
	private MainPanelController mainCtrl;
	private TriagePanelSearchController searchCtrl;
	private TriagePatientQueuePanelController patientQueueCtrl;
	private TriagePatientController tpc;
	private int slumID = -1;
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePanelSearch.fxml"));
			Parent search = loader.load();
			searchCtrl = loader.<TriagePanelSearchController>getController();
			triageSplitPane.getItems().add( search );
			searchCtrl.setTriagePanelController( this );
			
			loader = new FXMLLoader(getClass().getResource("TriagePatientQueuePanel.fxml"));
			Parent page = loader.load();
			patientQueueCtrl = loader.<TriagePatientQueuePanelController>getController();
			triageSplitPane.getItems().add( page );
			patientQueueCtrl.setTriagePanelController( this );

			triageSplitPane.setDividerPositions( 0.06 );
			triageSplitPane.getDividers().get(0).positionProperty().addListener
			(
				new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
					{
						triageSplitPane.setDividerPositions( 0.06 );
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startNewPatient()
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePatient.fxml"));
			Parent page = loader.load();
			tpc = loader.<TriagePatientController>getController();
			triageSplitPane.getItems().set( 1, page );
			triageSplitPane.getDividers().get(0).positionProperty().addListener
			(
				new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
					{
						triageSplitPane.setDividerPositions( 0.06 );
					}
				}
			);
			tpc.setTriagePanelController( this );
			tpc.setSlum(slumID);
			tpc.hideGraphButtons();
			mainCtrl.setIsSwitchable(false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void editPatient( PatientVisit pv, boolean isEdit )
	{
		Patient p = DatabaseController.getPatient( pv.getpatientID() );
		FemaleRecord fr = DatabaseController.getFemaleRecord( pv.getpatientID() );
		TriageRecord tr = DatabaseController.getTriageRecord( pv.getvisitID() );
		ArrayList<PatientHistory> ccList = DatabaseController.getPatientHistoryList( pv.getpatientID(), PatientHistory.TYPE_CC );
		ArrayList<PatientHistory> pmhList = DatabaseController.getPatientHistoryList( pv.getpatientID(), PatientHistory.TYPE_PMH );
		ArrayList<PatientHistory> scList = DatabaseController.getPatientHistoryList( pv.getpatientID(), PatientHistory.TYPE_SCR );
		ArrayList<PatientHistory> drugList = DatabaseController.getPatientHistoryList( pv.getpatientID(), PatientHistory.TYPE_DH );
		ArrayList<PatientHistory> allergyList = DatabaseController.getPatientHistoryList( pv.getpatientID(), PatientHistory.TYPE_ALE );
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePatient.fxml"));
			Parent page = loader.load();
			TriagePatientController tpc = loader.<TriagePatientController>getController();
			tpc.isEdit( isEdit );
			tpc.setPatient( p );
			tpc.setTriageRecord( tr );
			tpc.setFemaleRecord( fr );
			tpc.setHistory( ccList, pmhList, scList, drugList, allergyList );
			tpc.setTriagePanelController( this );
			tpc.setSlum( slumID );
			triageSplitPane.getItems().set( 1, page );
			triageSplitPane.getDividers().get(0).positionProperty().addListener
			(
				new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
					{
						triageSplitPane.setDividerPositions( 0.06 );
					}
				}
			);
			if(isEdit)
				mainCtrl.setIsSwitchable(false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void startOldPatient( Patient p, FemaleRecord fr, ArrayList<PatientHistory> ccList, ArrayList<PatientHistory> pmhList, ArrayList<PatientHistory> scList, ArrayList<PatientHistory> drugList, ArrayList<PatientHistory> allergyList )
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePatient.fxml"));
			Parent page = loader.load();
			tpc = loader.<TriagePatientController>getController();
			triageSplitPane.getItems().set( 1, page );
			triageSplitPane.getDividers().get(0).positionProperty().addListener
			(
				new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
					{
						triageSplitPane.setDividerPositions( 0.06 );
					}
				}
			);
			tpc.setPatient( p );
			tpc.setFemaleRecord( fr );
			tpc.setHistory( ccList, pmhList, scList, drugList, allergyList );
			tpc.setTriagePanelController( this );
			mainCtrl.setIsSwitchable(false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void finishPatient()
	{
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePatientQueuePanel.fxml"));
			Parent page = loader.load();
			patientQueueCtrl = loader.<TriagePatientQueuePanelController>getController();
			triageSplitPane.getItems().set( 1, page );
			patientQueueCtrl.setTriagePanelController( this );
			patientQueueCtrl.setTable(slumID);
			mainCtrl.setIsSwitchable(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setMainPanelController( MainPanelController mainCtrl )
	{
		this.mainCtrl = mainCtrl;
	}

	public void setSlum( int slumID )
	{
		this.slumID = slumID;
		searchCtrl.setSlum( slumID );
		patientQueueCtrl.setTable( slumID );
	}

	public boolean getIsSwitchable()
	{
		return mainCtrl.getIsSwitchable();
	}
}
