package sight;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
*
* @author Stephen, Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class MainPanelController implements Initializable
{
	@FXML private VBox vbMainMenu;
	@FXML private Button btnTriage;
	@FXML private Button btnConsultation;
	@FXML private Button btnPharmacy;
	@FXML private Button btnAccountSetting;
	@FXML private Button btnAdmin;
	//@FXML private ComboBox<String> cbSpecialFunction;
	@FXML private Button btnLogout;
	@FXML private SplitPane vsplitpane;
	@FXML private VBox vbox;
	@FXML private AnchorPane intialialcontentpane;
	@FXML private MenuItem miChangePassword;

	private long userLogID = -1;
	private int slumID = -1;
	private boolean isSwitchable = true;
	//private String curPatientName, curPatientCC;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		User curUser = User.getCurrentUser();
		
		if(!curUser.gettriageAccess())
			vbMainMenu.getChildren().remove(btnTriage);
			//btnTriage.setVisible(false);
		if(!curUser.getconsultationAccess())
			vbMainMenu.getChildren().remove(btnConsultation);
			//btnConsultation.setVisible(false);
		if(!curUser.getpharmacyAccess())
			vbMainMenu.getChildren().remove(btnPharmacy);
			//btnPharmacy.setVisible(false);
		if(!curUser.getadminAccess())
			vbMainMenu.getChildren().remove(btnAdmin);
			//btnAdmin.setVisible(false);

		/*resetSpecialFunction();
		cbSpecialFunction.valueProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(newValue == null)
						return;
					try
					{
						switch(newValue)
						{
							case "Chief Complaint":
								Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
								FXMLLoader loader = new FXMLLoader(getClass().getResource("SpecialFunctionWindow.fxml"));
								Parent specialFunctionWindow = loader.load();
								SpecialFunctionWindowController sfwCtrl = loader.<SpecialFunctionWindowController>getController();
								sfwCtrl.setData(curPatientName, curPatientCC);
								Stage stage = new Stage();
								stage.setScene(new Scene(specialFunctionWindow));
								stage.setTitle("One-2-One Medical System - Chief Complaint");
								stage.getIcons().setAll(image);
								stage.setMinHeight(200);
								stage.setMinWidth(500);
								stage.show();
								break;
						}
					} catch (IOException e) { e.printStackTrace(); }
				}
			}
		);*/
	}
	
	@FXML
	public void btnTriagePressed(ActionEvent event)
	{
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page? (Changes will NOT be saved)", AlertType.CONFIRMATION);;
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			//resetSpecialFunction();
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TriagePanel.fxml"));	
			try {
				root = loader.load();
				TriagePanelController triageCtrl = loader.<TriagePanelController>getController();
				triageCtrl.setMainPanelController(this);
				triageCtrl.setSlum(slumID);
				vsplitpane.getItems().remove(1);
				vsplitpane.getItems().add(root);
				vsplitpane.setDividerPositions(0.1);
				isSwitchable = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void btnConsultationPressed(ActionEvent event)
	{
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page? (Changes will NOT be saved)", AlertType.CONFIRMATION);;
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			//resetSpecialFunction();
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPanel.fxml"));
			try {
				root = loader.load();
				ConsultationPanelController consultationCtrl = loader.<ConsultationPanelController>getController();
				consultationCtrl.setMainPanelController(this);
				consultationCtrl.setSlum(slumID);
				vsplitpane.getItems().remove(1);
				vsplitpane.getItems().add(root);
				vsplitpane.setDividerPositions(0.1);
				isSwitchable = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void btnPharmacyPressed(ActionEvent event)
	{
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page? (Changes will NOT be saved)", AlertType.CONFIRMATION);;
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			//resetSpecialFunction();
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PharmacyPanel.fxml"));
			try {
				root = loader.load();
				PharmacyPanelController pharmacyCtrl = loader.<PharmacyPanelController>getController();
				pharmacyCtrl.setSlum(slumID);
				vsplitpane.getItems().remove(1);
				vsplitpane.getItems().add(root);
				vsplitpane.setDividerPositions(0.1);
				isSwitchable = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void btnAccountSettingPressed(ActionEvent event)
	{
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page? (Changes will NOT be saved)", AlertType.CONFIRMATION);;
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			//resetSpecialFunction();
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountSettingPanel.fxml"));
			
			try {
				root = loader.load();
				vsplitpane.getItems().remove(1);
				vsplitpane.getItems().add(root);
				vsplitpane.setDividerPositions(0.1);
				isSwitchable = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void btnAdminPressed(ActionEvent event)
	{
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page? (Changes will NOT be saved)", AlertType.CONFIRMATION);
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
			isConfirmed = true;
		if(isConfirmed)
		{
			//resetSpecialFunction();
			Parent root;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
			
			try {
				root = loader.load();
				loader.<AdminPanelController>getController().setSlum(slumID);
				vsplitpane.getItems().remove(1);
				vsplitpane.getItems().add(root);
				vsplitpane.setDividerPositions(0.1);
				isSwitchable = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void btnLogoutPressed(ActionEvent event)
	{
		if(btnLogout.getScene().getWindow() == null)
		{
			Platform.exit();
			System.exit(0);
		}
		boolean isConfirmed = false;
		if(!isSwitchable)
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to leave this page and logout? (Changes will NOT be saved)", AlertType.CONFIRMATION);
			if(result.isPresent() && (result.get() == ButtonType.OK))
				isConfirmed = true;
		}
		else
		{
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Confirm to logout?", AlertType.CONFIRMATION);
			if( (result.isPresent()) && (result.get() == ButtonType.OK) )
				isConfirmed = true;
		}
		if(isConfirmed)
		{
			//resetSpecialFunction();
			try {
				if(!DatabaseController.userLogout(userLogID))
				{
					promptMessage("One-2-One Medical System", "ERROR", "Please Try Again!", AlertType.ERROR);
					return;
				}
				Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
				Parent root;
				root = FXMLLoader.load(getClass().getResource("SIGHT.fxml"));
				Scene scene = new Scene(root);
				Stage stage = (Stage) btnLogout.getScene().getWindow();
				stage.hide();
				stage.setScene(scene);
				stage.setTitle("One-2-One Medical System");
				stage.getIcons().setAll(image);
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				stage.show();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	public void setIsSwitchable(boolean isSwitchable)
	{
		this.isSwitchable = isSwitchable;
	}

	public void setSlum(int slumID)
	{
		this.slumID = slumID;
	}

	public void setUserLogID(long userLogID)
	{
		this.userLogID = userLogID;
	}

	public void setClosePolicy()
	{
		Stage curStage = (Stage) vbMainMenu.getScene().getWindow();
		curStage.setOnCloseRequest
		(
			new EventHandler<WindowEvent>()
			{
				@Override
				public void handle(WindowEvent event)
				{
					if(event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
						btnLogoutPressed(null);
					event.consume();
				}
			}
		);
	}

	public boolean getIsSwitchable()
	{
		return isSwitchable;
	}

	/*public void addSpecialFunction(String functionName, String patientName, String content)
	{
		ObservableList<String> functionList = cbSpecialFunction.getItems();
		for(String s : functionList)
			if(s.equals(functionName))
				return;
		cbSpecialFunction.getItems().add(functionName);
		switch(functionName)
		{
			case "Chief Complaint":
				curPatientName = patientName;
				curPatientCC = content;
				break;
		}
	}

	public void resetSpecialFunction()
	{
		cbSpecialFunction.getItems().clear();
		cbSpecialFunction.getItems().add("***");
		cbSpecialFunction.getSelectionModel().selectFirst();
	}*/

	public Optional<ButtonType> promptMessage( String title, String header, String msg, AlertType type )
	{
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		DialogPane dp = alert.getDialogPane();
		dp.setPrefWidth(400);
		dp.lookup(".label.content").setStyle("-fx-font-size: 14px;");
		dp.lookup(".header-panel").setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		return alert.showAndWait();
	}
}
