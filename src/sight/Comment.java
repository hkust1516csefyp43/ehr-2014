package sight;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Comment
{
	private IntegerProperty commentID;
	private StringProperty comment;
	private StringProperty response;
	private LongProperty visitID;
	private IntegerProperty commentUserID;
	private StringProperty commentUser;
	private IntegerProperty responseUserID;
	private StringProperty commentDateTimeString;
	private StringProperty responseDateTimeString;
	private StringProperty visitDateTimeString;
	private BooleanProperty isResponded;
	private BooleanProperty isChanged;

	public Comment(String comment)
	{
		this.commentID = new SimpleIntegerProperty(0);
		this.comment = new SimpleStringProperty(comment);
		this.response = new SimpleStringProperty("");
		this.visitID = new SimpleLongProperty(0);
		this.commentUserID = new SimpleIntegerProperty(0);
		this.commentUser = new SimpleStringProperty("");
		this.responseUserID = new SimpleIntegerProperty(0);
		this.commentDateTimeString = new SimpleStringProperty("");
		this.responseDateTimeString = new SimpleStringProperty("");
		this.visitDateTimeString = new SimpleStringProperty("");
		this.isResponded = new SimpleBooleanProperty(false);
		this.isChanged = new SimpleBooleanProperty(false);
	}

	public Comment( int commentID, String comment, String response, long visitID, int commentUserID, String commentUser, int responseUserID, String commentDateTimeString, String responseDateTimeString, String visitDateTimeString, boolean isResponded )
	{
		this.commentID = new SimpleIntegerProperty(commentID);
		this.comment = new SimpleStringProperty(comment);
		this.response = new SimpleStringProperty(response);
		this.visitID = new SimpleLongProperty(visitID);
		this.commentUserID = new SimpleIntegerProperty(commentUserID);
		this.commentUser = new SimpleStringProperty(commentUser);
		this.responseUserID = new SimpleIntegerProperty(responseUserID);
		this.commentDateTimeString = new SimpleStringProperty(commentDateTimeString);
		this.responseDateTimeString = new SimpleStringProperty(responseDateTimeString);
		this.visitDateTimeString = new SimpleStringProperty(visitDateTimeString);
		this.isResponded = new SimpleBooleanProperty(isResponded);
		this.isChanged = new SimpleBooleanProperty(false);
	}

	public IntegerProperty commentIDProperty() { return commentID; }
	public StringProperty commentProperty() { return comment; }
	public StringProperty responseProperty() { return response; }
	public LongProperty visitIDProperty() { return visitID; }
	public IntegerProperty commentUserIDProperty() { return commentUserID; }
	public StringProperty commentUserProperty() { return commentUser; }
	public IntegerProperty responseUserIDProperty() { return responseUserID; }
	public StringProperty commentDateTimeStringProperty() { return commentDateTimeString; }
	public StringProperty responseDateTimeStringProperty() { return responseDateTimeString; }
	public StringProperty visitDateTimeStringProperty() { return visitDateTimeString; }
	public BooleanProperty isRespondedProperty() { return isResponded; }
	public BooleanProperty isChangedProperty() { return isChanged; }

	public int getcommentID() { return commentID.get(); }
	public String getcomment() { return comment.get(); }
	public String getresponse() { return response.get(); }
	public long getvisitID() { return visitID.get(); }
	public int getcommentUserID() { return commentUserID.get(); }
	public String getcommentUser() { return commentUser.get(); }
	public int getresponseUserID() { return responseUserID.get(); }
	public String getcommentDateTimeString() { return commentDateTimeString.get(); }
	public String getresponseDateTimeString() { return responseDateTimeString.get(); }
	public String getvisitDateTimeString() { return visitDateTimeString.get(); }
	public boolean getisResponded() { return isResponded.get(); }
	public boolean getisChanged() { return isChanged.get(); }

	public void setcommentID(int commentID) { this.commentID = new SimpleIntegerProperty( commentID ); }
	public void setcomment(String comment) { this.comment.set(comment); }
	public void setresponse(String response) { this.response = new SimpleStringProperty( response ); }
	public void setvisitID(long visitID) { this.visitID = new SimpleLongProperty( visitID ); }
	public void setcommentUserID(int commentUserID) { this.commentUserID = new SimpleIntegerProperty( commentUserID ); }
	public void setcommentUser(String commentUser) { this.commentUser = new SimpleStringProperty( commentUser ); }
	public void setresponseUserID(int responseUserID) { this.responseUserID = new SimpleIntegerProperty( responseUserID ); }
	public void setcommentDateTimeString(String commentDateTimeString) { this.commentDateTimeString.set(commentDateTimeString); }
	public void setresponseDateTimeString(String responseDateTimeString) { this.responseDateTimeString.set(responseDateTimeString); }
	public void setvisitDateTimeString(String visitDateTimeString) { this.visitDateTimeString.set(visitDateTimeString); }
	public void setisResponded(boolean isResponsed) { this.isResponded = new SimpleBooleanProperty( isResponsed ); }
	public void setisChanged(boolean isChanged) { this.isChanged = new SimpleBooleanProperty( isChanged ); }
}
