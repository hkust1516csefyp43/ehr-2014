package sight;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class AccountSettingPanelController implements Initializable
{
	@FXML private Label HeadingLabel;
	@FXML private Label postPwChangeDisplay;
	@FXML private PasswordField pfOldPassword;
	@FXML private PasswordField pfNewPassword;
	@FXML private PasswordField pfConfirmPassword;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		HeadingLabel.setText(HeadingLabel.getText() + User.getCurrentUser().getname());
	}

	@FXML 
	private void btnSubmitPressed (ActionEvent event)
	{
		if( pfOldPassword != null && pfNewPassword != null && pfConfirmPassword!= null )
		{
			String oldPassword = pfOldPassword.getText();
			String newPassword = pfNewPassword.getText();
			String confirmPassword = pfConfirmPassword.getText();
			String curPassword = User.getCurrentUser().getpassword();  
			
			if(User.getEncryptedPassword(oldPassword).equals(curPassword))
			{
				if(newPassword.equals(confirmPassword))
				{
					//Can try RegEx later
					if(newPassword.length() < 6)
					{
						promptMessage("One-2-One Medical System", "Invalid Password Length", "The password must contain at least 8 characters!", AlertType.WARNING);
						pfNewPassword.setText("");
						pfConfirmPassword.setText("");
						return;
					}
					boolean isContainLetter = false;
					boolean isContainNumber = false;
					for(int i = 0; i < newPassword.length(); i++)
						if( Character.isLetter(newPassword.charAt(i) ) )
						{
							isContainLetter = true;
							break;
						}
					if(!isContainLetter)
					{
						promptMessage("One-2-One Medical System", "Invalid Combination", "The password must contain at least an english letter!", AlertType.WARNING);
						pfNewPassword.setText("");
						pfConfirmPassword.setText("");
						return;
					}
					for(int i = 0; i < newPassword.length(); i++)
						if( Character.isDigit(newPassword.charAt(i) ) )
						{
							isContainNumber = true;
							break;
						}
					if(!isContainNumber)
					{
						promptMessage("One-2-One Medical System", "Invalid Combination", "The password must contain at least a digit!", AlertType.WARNING);
						pfNewPassword.setText("");
						pfConfirmPassword.setText("");
						return;
					}
					String encryptedPassword = User.getEncryptedPassword(newPassword);
					if(encryptedPassword == null)
					{
						promptMessage("One-2-One Medical System", "ERROR", "Please try again!", AlertType.ERROR);
						pfOldPassword.setText("");
						pfNewPassword.setText("");
						pfConfirmPassword.setText("");
						return;
					}
					User.getCurrentUser().setpassword(encryptedPassword);
					DatabaseController.updateUserPassword(User.getCurrentUser().getid(), encryptedPassword);
					promptMessage("One-2-One Medical System", "Successful!", "Password Changed", AlertType.INFORMATION);
					pfOldPassword.setText("");
					pfNewPassword.setText("");
					pfConfirmPassword.setText("");
				}
				else
				{
					promptMessage("One-2-One Medical System", "Invalid Password!", "New Password and Confirm Password Does Not Match. Please Retype.", AlertType.WARNING);
					pfNewPassword.setText("");
					pfConfirmPassword.setText("");
				}
			}
			else
			{
				promptMessage("One-2-One Medical System", "Invalid Password!", "Old Password Is Not A Match. Please Retype.", AlertType.WARNING);
				pfOldPassword.setText("");
				pfNewPassword.setText("");
				pfConfirmPassword.setText("");
			}
		}
		else
			promptMessage("One-2-One Medical System", "ERROR", "The Textboxes Have Not Been Properly Filled. Please Retype", AlertType.ERROR);
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
