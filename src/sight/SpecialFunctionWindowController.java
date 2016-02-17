package sight;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class SpecialFunctionWindowController implements Initializable
{
	@FXML private ScrollPane scrollPane;
	@FXML private Pane pane;
	private long yPosition;
	//private Rectangle printRectangle;
	//private Node nodeToPrint;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		//scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		yPosition = -20;
	}

	public void setData(String patientName, String cc)
	{
		Label patientNameLabel = new Label(patientName+"'s Chief Complaint");
		patientNameLabel.setLayoutX(10);
		patientNameLabel.setLayoutY(yPosition+=30);
		patientNameLabel.setFont(new Font("System Bold", 18));
		patientNameLabel.setPrefWidth(488);
		patientNameLabel.setAlignment(Pos.CENTER);
		pane.getChildren().add(patientNameLabel);

		Label chiefComplaintLabel = new Label(cc);
		chiefComplaintLabel.setLayoutX(10);
		chiefComplaintLabel.setLayoutY(yPosition+=30);
		chiefComplaintLabel.setWrapText(true);
		chiefComplaintLabel.setFont(new Font("System Bold", 16));
		chiefComplaintLabel.setPrefWidth(488);
		//chiefComplaintLabel.setAlignment(Pos.CENTER);
		pane.getChildren().add(chiefComplaintLabel);
		pane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
