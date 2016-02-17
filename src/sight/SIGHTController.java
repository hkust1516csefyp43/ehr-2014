package sight;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class SIGHTController implements Initializable {

	@FXML TextField tfUser;
	@FXML PasswordField pfPassword;
	@FXML ComboBox<String> cbDevice;
	@FXML ComboBox<Slum> cbSlum; 
	@FXML Button btnLogin;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		/*cbDevice.getItems().add("Testing Mode");
		cbDevice.getItems().add("User Mode");
		//cbDevice.getSelectionModel().selectFirst();
		cbDevice.getSelectionModel().select(-1);
		cbDevice.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (t1.equals("Testing Mode")) {
					JDBC.setLocal(true);
					DatabaseController.setURL(JDBC.getURL());
					cbSlum.getItems().addAll(DatabaseController.getSlumList());
					cbSlum.getSelectionModel().selectFirst();
				} else {
					JDBC.setLocal(false);
					DatabaseController.setURL(JDBC.getURL());
					cbSlum.getItems().addAll(DatabaseController.getSlumList());
					cbSlum.getSelectionModel().selectFirst();
				}
			}
		});*/

		/*btnLogin.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent value)
			{
				if(value.getCode() == KeyCode.ENTER)
					btnLoginPressed();
			}
		});*/
		//btnLogin.setDefaultButton(true);
		btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());

		cbDevice.setVisible(false);
		try
		{
			//JDBC.setLocal(true);
			if(DatabaseController.isFirst())
				DatabaseController.setURL(JDBC.getTestingURL());
			cbSlum.getItems().addAll(DatabaseController.getSlumList(true));
			cbSlum.getSelectionModel().selectFirst();
			DatabaseController.setIsFirst(false);
		}
		catch (NullPointerException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Cannot find the server");
			alert.setContentText("Are you sure you are connected to the correct Wi-Fi? \n\nAre you sure XAMPP (MySQL and Apache) is running? \n\nClick 'OK' to exit the program and try again later.");
			alert.showAndWait();
			System.exit(404);
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
			System.out.println("E1");
			try
			{
				DatabaseController.setURL(JDBC.getSyncURL());
				cbSlum.getItems().addAll(DatabaseController.getSlumList(true));
				cbSlum.getSelectionModel().selectFirst();
				DatabaseController.setIsFirst(false);
			}
			catch(Exception e2)
			{
				System.out.println("E2");
				try
				{
					//JDBC.setLocal(false);
					DatabaseController.setURL(JDBC.getRealURL());
					cbSlum.getItems().addAll(DatabaseController.getSlumList(true));
					cbSlum.getSelectionModel().selectFirst();
					DatabaseController.setIsFirst(false);
				}
				catch(Exception e3)
				{
					System.out.println("E3");
				}
			}
		}
		finally
		{
			cbSlum.getItems().addAll(DatabaseController.getSlumList(true));
			cbSlum.getSelectionModel().selectFirst();
			DatabaseController.setIsFirst(false);
		}
	}

	@FXML
	public void btnLoginPressed()
	{
		User u = DatabaseController.getUser(tfUser.getText());
		if(u == null)
			promptMessage("One-2-One Medical System", "ERROR", "No Such User!", AlertType.ERROR);
		else
		{
			String encryptedPassword = User.getEncryptedPassword(pfPassword.getText());
			if( encryptedPassword.equals(u.getpassword()) )
			{
				String macAddress = "";
				try
				{
					InetAddress ip;
					ip = InetAddress.getLocalHost();
					NetworkInterface network = NetworkInterface.getByInetAddress(ip);
					byte[] mac = network.getHardwareAddress();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < mac.length; i++)
						sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
					macAddress = sb.toString();
				}
				catch(SocketException | UnknownHostException e)
				{
					e.printStackTrace();
				}

				long userLogID = DatabaseController.getUserLogIDIfUserLoggedIn(u.getid(), macAddress);
				if(userLogID != -1)
				{
					String msg = "User has logged in already!\n"
							+ "Are you sure you want to log in with this account?\n"
							+ "(Remarks: Sharing of accounts will trigger SYSTEM CRASH)";
					Optional<ButtonType> result = promptMessage("One-2-One Medical System", "WARNING", msg, AlertType.CONFIRMATION);
					if( (result.isPresent()) && (result.get() == ButtonType.CANCEL) )
						return;
				}
				else
					userLogID = DatabaseController.userLogin(u.getid());
				Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
				User curUser = User.getCurrentUser();
				curUser.setid(u.getid());
				curUser.setname(tfUser.getText());
				curUser.setpassword(encryptedPassword);
				curUser.setroleID(u.getroleID());
				curUser.settriageAccess(u.gettriageAccess());
				curUser.setconsultationAccess(u.getconsultationAccess());
				curUser.setpharmacyAccess(u.getpharmacyAccess());
				curUser.setinventoryAccess(u.getinventoryAccess());
				curUser.setstatisticsAccess(u.getstatisticsAccess());
				curUser.setsupervisorAccess(u.getsupervisorAccess());
				curUser.setadminAccess(u.getadminAccess());
				try
				{
					Parent root = loader.load();
					MainPanelController ctrl = loader.<MainPanelController>getController();
					ctrl.setSlum(cbSlum.getSelectionModel().getSelectedItem().getid());
					ctrl.setUserLogID(userLogID);
					Stage stage = new Stage(StageStyle.DECORATED);
					Scene scene = new Scene(root, 1000, 620);
					stage.setScene(scene);
					stage.getIcons().setAll(image);
					stage.setTitle("One-2-One Medical System: "+tfUser.getText().trim()+" @ "+cbSlum.getSelectionModel().getSelectedItem());
					stage.show();
					stage.setMinHeight(stage.getHeight());
					stage.setMinWidth(stage.getWidth());
					ctrl.setClosePolicy();
	
					Stage loginstage = (Stage) btnLogin.getScene().getWindow();
					loginstage.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			else
				promptMessage("One-2-One Medical System", "ERROR", "Invalid Password!", AlertType.ERROR);
		}
	}

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