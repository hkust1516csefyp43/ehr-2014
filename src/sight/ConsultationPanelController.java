package sight;

import java.net.URL;
import java.util.ResourceBundle;




import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import java.util.logging.Level;
//import java.util.logging.Logger;
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

public class ConsultationPanelController implements Initializable {
	
	@FXML private SplitPane consultationSplitPane;
	private MainPanelController mainCtrl;
	private ConsultationPanelReportController reportCtrl;
	private ConsultationPanelTabController tabCtrl;
	//private long slumID = -1;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPanelReport.fxml"));
			Parent report = loader.load();
			reportCtrl = loader.<ConsultationPanelReportController>getController();
			consultationSplitPane.getItems().add( report );
			reportCtrl.setConsultationPanelController( this );

			loader = new FXMLLoader(getClass().getResource("ConsultationPanelTab.fxml"));
			Parent tab = loader.load();
			tabCtrl = loader.<ConsultationPanelTabController>getController();
			consultationSplitPane.getItems().add( tab );
			tabCtrl.setConsultancyPanelController( this );

			consultationSplitPane.setDividerPositions( 0.06 );
			consultationSplitPane.getDividers().get(0).positionProperty().addListener
			(
				new ChangeListener<Number>()
				{
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
					{
						consultationSplitPane.setDividerPositions( 0.06 );
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setIsSwitchable( boolean isSwitchable )
	{
		mainCtrl.setIsSwitchable(isSwitchable);
	}
	
	public void setMainPanelController( MainPanelController mainCtrl )
	{
		this.mainCtrl = mainCtrl;
	}

	public void viewPatientVisit( long visitID, String visitDate )
	{
		tabCtrl.viewPatientVisit(visitID, visitDate);
	}

	/*public void addSpecialFunction(String functionName, String patientName, String content)
	{
		mainCtrl.addSpecialFunction(functionName, patientName, content);
	}

	public void resetSpecialFunction()
	{
		mainCtrl.resetSpecialFunction();
	}*/

	public void setSlum( int slumID )
	{
		//this.slumID = slumID;
		reportCtrl.setSlum( slumID );
		tabCtrl.setSlum( slumID );
	}
}
