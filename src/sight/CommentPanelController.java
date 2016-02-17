package sight;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class CommentPanelController implements Initializable
{
	@FXML private Label lPatientNum;
	@FXML private TableView<Comment> tvCommentTable;
	@FXML private TableColumn<Comment, String> tbVisitDate;
	@FXML private TableColumn<Comment, String> tbCommentUser;
	@FXML private TableColumn<Comment, String> tbComment;
	@FXML private TableColumn<Comment, String> tbResponse;
	@FXML private TableColumn<Comment, Boolean> tbIsResponded;
	@FXML private TextArea taComment;
	@FXML private TextArea taResponse;

	private ConsultationPatientTabController patientCtrl;
	private int userID;
	private boolean isChangingSelection = false;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		prepareCommentTable();
		tvCommentTable.getSelectionModel().selectedItemProperty().addListener
		(
			(obs, oldSelection, newSelection) -> {
			    if(newSelection != null)
			    {
			    	isChangingSelection = true;
			    	taComment.setText(newSelection.getcomment());
			    	taResponse.setText(newSelection.getresponse());
			    	isChangingSelection = false;
			    }
			}
		);
		taComment.setEditable(false);
		taResponse.textProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(isChangingSelection)
						return;
					tvCommentTable.getSelectionModel().getSelectedItem().setresponse(taResponse.getText());
					tvCommentTable.getSelectionModel().getSelectedItem().setisResponded(true);
					tvCommentTable.getSelectionModel().getSelectedItem().setisChanged(true);
					refreshCommentTable();
				}
			}
		);
	}	

	@FXML
	public void btnViewPatientVisitPressed( ActionEvent Event )
	{
		try
		{
			Comment c = tvCommentTable.getSelectionModel().getSelectedItem();
			if(c == null)
				return;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsultationPatientVisitReport.fxml"));
			ScrollPane patientVisitReport = loader.load();
			loader.<ConsultationPatientVisitReportController>getController().setPatientVisit(c.getvisitID());
			Image image = new Image(SIGHT.class.getResourceAsStream("logo.jpg"));
			Stage stage = new Stage();
			stage.setScene(new Scene(patientVisitReport));
			stage.setTitle("Select the visit you are looking for...");
			stage.getIcons().setAll(image);
			stage.sizeToScene();
			stage.setResizable(false);
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	public void btnConfirmCommentPressed( ActionEvent Event )
	{
		long responseCount = 0;
		ObservableList<Comment> curCommentList = tvCommentTable.getItems();
		if(curCommentList.size() == 0)
		{
			patientCtrl.setIsCommentResponded(true);
			Optional<ButtonType> result = promptMessage("One-2-One Medical System", "No Comment", "No comments left.\nDo you want to exit the panel?", AlertType.CONFIRMATION);
			if(result.isPresent() && (result.get() == ButtonType.OK))
			{
				Stage curStage = (Stage) tvCommentTable.getScene().getWindow();
				curStage.close();
				return;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		for(int i = 0; i < curCommentList.size(); i++)
		{
			Comment c = curCommentList.get(i);
			if(c.getisChanged() || c.getisResponded())
			{
				responseCount++;
				c.setresponseDateTimeString(sdf.format(new java.util.Date()));
				c.setresponseUserID(userID);
				DatabaseController.updateComment(c);
				if(c.getisResponded())
				{
					curCommentList.remove(c);
					i -= 1;
				}
				else
					c.setisChanged(false);
			}
		}
		if(curCommentList.size() == 0)
		{
			patientCtrl.setIsCommentResponded(true);
			Optional<ButtonType> result;
			result = promptMessage("One-2-One Medical System", "Successful", "Successfully submitted " + responseCount + " response(s).\n"
					+ "All comments are responded.\nDo you want to exit the panel?", AlertType.CONFIRMATION);
			if(result.isPresent() && (result.get() == ButtonType.OK))
			{
				Stage curStage = (Stage) tvCommentTable.getScene().getWindow();
				curStage.close();
				return;
			}
		}
		else
		{
			if(responseCount > 0 )
				promptMessage("One-2-One Medical System", "Successful", "Successfully submitted " + responseCount + " response(s).", AlertType.INFORMATION);
			else
				promptMessage("One-2-One Medical System", "No Update", "No update has been done on the comment(s).", AlertType.INFORMATION);
		}
	}

	private void prepareCommentTable()
	{
		tvCommentTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvCommentTable.setEditable(true);
		tbVisitDate.setCellValueFactory( new PropertyValueFactory<>("visitDateTimeString") );
		tbVisitDate.setEditable(false);
		tbCommentUser.setCellValueFactory( new PropertyValueFactory<>("commentUser") );
		tbCommentUser.setEditable(false);
		tbComment.setCellValueFactory( new PropertyValueFactory<>("comment") );
		tbComment.setEditable(false);
		tbResponse.setCellValueFactory( new PropertyValueFactory<>("response") );
		tbResponse.setEditable(false);
		tbIsResponded.setCellValueFactory( new PropertyValueFactory<>("isResponded") );
		tbIsResponded.setCellFactory( CheckBoxTableCell.forTableColumn(tbIsResponded) );
		//tbIsResponded.setText("\u2713");//A tick
	}

	private void refreshCommentTable()
	{
		java.util.Collections.sort
		(
				tvCommentTable.getItems(),
			new java.util.Comparator<Comment>()
			{
			    @Override
			    public int compare(Comment c1, Comment c2)
			    {
			    	return c1.getvisitDateTimeString().compareTo(c2.getvisitDateTimeString());
			    }
			}
		);
		long num = tvCommentTable.getItems().size();
		for( int i = 0; i < num; i++ )
		{
			tvCommentTable.getColumns().get(i).setVisible(false);
			tvCommentTable.getColumns().get(i).setVisible(true);
		}
	}

	public void setConsultationPatientController( ConsultationPatientTabController patientCtrl )
	{
		this.patientCtrl = patientCtrl;
	}

	public void setCommentList( ArrayList<Comment> commentList, int userID )
	{
		ObservableList<Comment> curCommentList = tvCommentTable.getItems();
		curCommentList.addAll(commentList);
		tvCommentTable.setItems( curCommentList );
		this.userID = userID;
	}

	public void setStage( Stage curStage )
	{
		curStage.setOnCloseRequest
		(
			new EventHandler<WindowEvent>()
			{
				@Override
				public void handle(WindowEvent event)
				{
					ObservableList<Comment> curCommentList = tvCommentTable.getItems();
					for(Comment c : curCommentList)
						if(c.getisChanged() || c.getisResponded())
						{
							Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirmation", "Are you sure to discard the changes?", AlertType.CONFIRMATION);
							if(result.isPresent() && (result.get() == ButtonType.OK))
							{
								patientCtrl.resetCommentList();
								curStage.close();
							}
							else
								event.consume();
							return;
						}
					
				}
			}
		);
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
