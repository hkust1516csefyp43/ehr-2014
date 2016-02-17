package sight;

import java.util.Calendar;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
*
* @author Lance, Alton
* @version 1.0
* @date 27/05/2015
* @see http://java-buddy.blogspot.com/
* @see http://stackoverflow.com/questions/23632884/how-to-commit-when-clicking-outside-an-editable-tableview-cell-in-javafx
* 
*/

class TableEditingTextCell<S, T> extends TextFieldTableCell<S, T>
{
	//private final TextField mTextField;
	private final TextArea mTextArea;
	private int type, row;
	private TableView<S> tableView;
	public static final int TYPE_TEXT = 0, TYPE_DATE_TEXT = 1, TYPE_INT = 2, TYPE_FLOAT = 3;
	private boolean isBackspace;
	private String fieldName;

	public TableEditingTextCell(int fieldType, TableView<S> tableView, String tableName, int fieldWidth, String fieldName)
	{
		super();
		//super(new IntegerStringConverter());
		//TableEditingTextCell.forTableColumn(new IntegerStringConverter());
		this.type = fieldType;
		this.tableView = tableView;
		this.fieldName = fieldName;
		isBackspace = false;
		if(type == TYPE_DATE_TEXT)
			this.setTooltip(new Tooltip("Format: dd/mm/yyyy"));
		mTextArea = new TextArea();
		mTextArea.setWrapText(true);
		mTextArea.setPrefWidth(fieldWidth-10);
		//if(type == TYPE_TEXT)
		mTextArea.setPrefHeight(40);
		mTextArea.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void handle(KeyEvent event)
			{
				isBackspace = false;
				if (event.getCode().equals(KeyCode.ENTER) && event.isControlDown())
					mTextArea.appendText("\n");
				else if (event.getCode().equals(KeyCode.ENTER) && event.isShiftDown())
					mTextArea.appendText("\n");
				else if (event.getCode().equals(KeyCode.ENTER) && event.isAltDown())
					mTextArea.appendText("\n");
				else if( event.getCode().equals(KeyCode.ENTER) )
				{
					String input = mTextArea.getText();
					if(input == null || input.length()==0)
						return;
					if(type == TYPE_TEXT)
						commitEdit((T)input);
					else if(type == TYPE_INT)
						commitEdit((T)new Integer(Integer.parseInt(input)));
					else if(type == TYPE_FLOAT)
						commitEdit((T)new Float(Float.parseFloat(input)));
					else if(type == TYPE_DATE_TEXT)
					{
						if(input.length() >= 8)
						{
							String[] splitArray = input.split( "/" );
							int day = Integer.parseInt( splitArray[0] );
							int month = Integer.parseInt( splitArray[1] );
							int year = Integer.parseInt( splitArray[2] );
							year = getYear(year);
							input = splitArray[0]+"/"+splitArray[1]+"/"+year;
							if( isDateValid( day, month, year ) )
								commitEdit((T)input);
							else
							{
								mTextArea.clear();
								//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
							}
						}
						else
						{
							mTextArea.clear();
							//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
						}
					}
				}
				else if (event.getCode().equals(KeyCode.BACK_SPACE) )
				{
					isBackspace = true;
				}
				//TODO: Now cant get the cell, so cant really set focus
				/*else if( event.getCode().equals(KeyCode.TAB) && nextColumn != null )
				{
					String input = mTextField.getText();
					if(input != null && input.length() != 0)
					{
						if(type == TYPE_TEXT)
							commitEdit((T)input);
						else if(type == TYPE_DATE_TEXT)
						{
							if(input.length() == 10)
							{
								String[] splitArray = input.split( "/" );
								long day = Integer.parseInt( splitArray[0] );
								long month = Integer.parseInt( splitArray[1] );
								long year = Integer.parseInt( splitArray[2] );
								if( isDateValid( day, month, year ) )
									commitEdit((T)input);
								else
								{
									mTextField.clear();
									//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
								}
							}
							else
							{
								mTextField.clear();
								//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
							}
						}
					}
					tableView.requestFocus();
					if(isHorizontal)
					{
						tableView.edit(tableView.getFocusModel().getFocusedCell().getRow(), nextColumn);
						TableEditingTextCell cell = (TableEditingTextCell)nextColumn.getCellFactory().call(nextColumn);
						cell.requestFocus();
						//tableView.getFocusModel().focusRightCell();
						//cell.setFocus();
					}
					else
					{
						long row = tableView.getFocusModel().getFocusedCell().getRow()+1;
						if( row <= tableView.getItems().size() )
							tableView.edit(row, nextColumn);
						TableEditingTextCell cell = (TableEditingTextCell)nextColumn.getCellFactory().call(nextColumn);
						cell.requestFocus();
					}
				}*/
			}
		});

		mTextArea.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if( !newValue )
				{
					String input = mTextArea.getText();
					if(input == null || input.length()==0)
						return;
					if(type == TYPE_TEXT)
					{
						commitEdit((T)input);
						S s = tableView.getItems().get(row);
						if(s instanceof PatientHistory)
						{
							switch(fieldName)
							{
								/*case "Medicine":
									((Prescription)s).setmedicine(input);
									break;*/
								case "UnitOfQuantity":
									((Prescription)s).setunitOfQuantity(input);
									break;
								case "Description":
									((PatientHistory)s).setdescription(input);
									break;
								case "UserName":
									((User)s).setname(input);
									break;
								case "SlumName":
									((Slum)s).setname(input);
									break;
							}
							//tableView.getColumns().get(row).setVisible(false);
							//tableView.getColumns().get(row).setVisible(true);
						}
					}
					else if(type == TYPE_INT)
					{
						Integer inputInt = new Integer(Integer.parseInt(input));
						commitEdit((T)inputInt);
						S s = tableView.getItems().get(row);
						if(s instanceof Prescription)
						{
							switch(fieldName)
							{
								case "Dosage":
									((Prescription)s).setdosage(inputInt);
									break;
								case "Frequency":
									((Prescription)s).setfrequency(inputInt);
									break;
								case "NumOfDays":
									((Prescription)s).setnumOfDays(inputInt);
									break;
								/*case "Quantity":
									((Prescription)s).setquantity(inputInt);
									break;*/
							}
							tableView.getColumns().get(row).setVisible(false);
							tableView.getColumns().get(row).setVisible(true);
						}
					}
					else if(type == TYPE_FLOAT)
					{
						if(input.trim().equals("0.0"))
							input = "0";
						Float inputInt = new Float(Float.parseFloat(input));
						commitEdit((T)inputInt);
						S s = tableView.getItems().get(row);
						if(s instanceof Prescription)
						{
							switch(fieldName)
							{
								case "Quantity":
									((Prescription)s).setquantity(inputInt);
									break;
							}
							tableView.getColumns().get(row).setVisible(false);
							tableView.getColumns().get(row).setVisible(true);
						}
					}
					else if(type == TYPE_DATE_TEXT)
					{
						if(input.length() >= 8)
						{
							String[] splitArray = input.split( "/" );
							int day = Integer.parseInt( splitArray[0] );
							int month = Integer.parseInt( splitArray[1] );
							int year = Integer.parseInt( splitArray[2] );
							year = getYear(year);
							input = splitArray[0]+"/"+splitArray[1]+"/"+year;
							if( isDateValid( day, month, year ) )
							{
								commitEdit((T)input);
								S s = tableView.getItems().get(row);
								if(s instanceof PatientHistory)
								{
									switch(fieldName)
									{
										case "StartDate":
											((PatientHistory)s).setstartDateString(input);
											break;
										case "RemissionDate":
											((PatientHistory)s).setremissionDateString(input);
											break;
									}
									tableView.getColumns().get(row).setVisible(false);
									tableView.getColumns().get(row).setVisible(true);
								}
							}
							else
							{
								mTextArea.clear();
								//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
							}
						}
						else
						{
							mTextArea.clear();
							//promptMessage("One-2-One Medical System", tableName+": "+colName, "Please input a valid date.", AlertType.INFORMATION);
						}
					}
				}
			}

		});
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@SuppressWarnings("unchecked")
			@Override	
			public void handle(MouseEvent event)
			{
				mTextArea.requestFocus();
				if(event.getButton() == MouseButton.SECONDARY)
				{
					if(event.getSource() instanceof TableCell<?,?>)
					{
						TableCell<S, T> c = (TableCell<S,T>) event.getSource();
						if(tableName.equals("Prescription"))
						{
							Prescription p = (Prescription)c.getTableRow().getItem();
							String msg = "Strength: "+p.getstrength(); 
							msg += "\nDosage: "+p.getdosage();
							msg += "\nTimes/Day: "+p.getfrequency();
							msg += "\nDays/Week: "+p.getnumOfDays();
							msg += "\nRoute: "+p.getroute();
							msg += "\nBrand: "+p.getbrand();
							msg += "\nTotal Quantity: "+p.getquantity();
							msg += "\nRemarks: "+p.getremark();
							promptMessage("One-2-One Medical System", tableName+": "+p.getmedicine(), msg, AlertType.INFORMATION);
						}
						else if(tableName.equals("Chief Complaint") || tableName.equals("Drug History") ||tableName.equals("Allergy") || tableName.equals("Clinical Diagnosis") || tableName.equals("Investigation"))
						{
							PatientHistory ph = (PatientHistory)c.getTableRow().getItem();
							promptMessage("One-2-One Medical System", tableName+": "+ph.getentryDateTimeString(), "Details: "+ph.getdescription(), AlertType.INFORMATION);
						}
						else if(tableName.equals("Previous Medical History"))
						{
							PatientHistory ph = (PatientHistory)c.getTableRow().getItem();
							String msg = "Disease: "+ph.getdescription();
							if(ph.getstartDateString() != null)
								msg += "\nStart Date: "+ph.getstartDateString();
							if(ph.getremissionDateString() != null)
								msg += "\nRemission Date: "+ph.getremissionDateString();
							if(ph.getisUnderTreatment())
								msg += "\nIs Under Treatment: YES";
							else
								msg += "\nIs Under Treatment: NO";
							
							promptMessage("One-2-One Medical System", tableName, msg, AlertType.INFORMATION);
						}
						else if(tableName.equals("HPI, Family & Social History"))
						{
							PatientHistory ph = (PatientHistory)c.getTableRow().getItem();
							String header = "Details";
							switch(ph.gethistoryType())
							{
								case "HPI":
									header = "History or Present Illness";
									break;
								case "Family":
									header = "Family History";
									break;
								case "Social":
									header = "Social History";
									break;
							}
							promptMessage("One-2-One Medical System", header, "Date: "+ph.getentryDateTimeString()+"\nDetails: "+ph.getdescription(), AlertType.INFORMATION);
						}
						else if(tableName.equals("Review of System") || tableName.equals("Physical Examination"))
						{
							PatientHistory ph = (PatientHistory)c.getTableRow().getItem();
							promptMessage("One-2-One Medical System", tableName+": "+ph.getremarks(), "Details: "+ph.getdescription(), AlertType.INFORMATION);
						}
						/*else if(tableName.equals("Physical Examination"))
						{
							PatientHistory ph = (PatientHistory)c.getTableRow().getItem();
							promptMessage("One-2-One Medical System", tableName, "Details: "+ph.getdescription(), AlertType.INFORMATION);
						}*/
					}
				}
				//if (event.getClickCount() > 1)
			}
		});

		mTextArea.textProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(newValue == null)
						return;
					if(type == TableEditingTextCell.TYPE_DATE_TEXT)
					{
						long len = newValue.length();
						if(len > 10)
						{
							mTextArea.setText(oldValue);
							return;
						}
						String reg = "";
						for(int i = 0; i < len; i++)
							if(i == 2 || i == 5)
								reg+="[/]";
							else
								reg+="[0-9]";
						if(!newValue.matches(reg))
						{
							mTextArea.setText(oldValue);
							return;
						}
						if((len == 2 || len == 5) && !isBackspace)
						{
							mTextArea.setText(newValue+"/");
							mTextArea.positionCaret(mTextArea.getText().length());
						}
					}
				}
			}
		);
		mTextArea.textProperty().bindBidirectional(textProperty());
	}

	@Override
	public void startEdit()
	{
		super.startEdit();
		row = tableView.getSelectionModel().getFocusedIndex();
		//setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		Object o = tableView.getSelectionModel().getSelectedItem();
		switch(fieldName)
		{
			case "Description":
				mTextArea.setText(((PatientHistory)o).getdescription());
				break;
			case "UserName":
				mTextArea.setText(((User)o).getname());
				break;
			case "SlumName":
				mTextArea.setText(((Slum)o).getname());
				break;
			case "Quantity":
				mTextArea.setText(((Prescription)o).getquantity()+"");
				break;
			case "StartDate":
				mTextArea.setText(((PatientHistory)o).getstartDateString());
				break;
			case "RemissionDate":
				mTextArea.setText(((PatientHistory)o).getremissionDateString());
				break;
		}
		if(mTextArea.getText()!=null)
			mTextArea.positionCaret(mTextArea.getText().length());
		setGraphic(mTextArea);
		//mTextField.requestFocus();
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();
		setGraphic(null);
		//setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}

	@Override
	public void updateItem(final T item, final boolean empty)
	{
		super.updateItem(item, empty);
		if( empty )
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			if( item == null )
				setGraphic(null);
			else
			{
				if( isEditing() )
				{
					setGraphic(mTextArea);
					setText((String)getItem());
				}
				else
				{
					setGraphic(null);
					if(type == TableEditingTextCell.TYPE_TEXT || type == TableEditingTextCell.TYPE_DATE_TEXT)
						setText((String)getItem());
					else if(type == TableEditingTextCell.TYPE_INT)
						setText((Integer)getItem()+"");
					else if(type == TableEditingTextCell.TYPE_FLOAT)
						setText((Float)getItem()+"");
				}
			}
		}
	}

	public static int getYear(int year)
	{
		if(year >= 100)
			return year;
		else
		{
			int thisYear = Calendar.getInstance().get(Calendar.YEAR);
			if(year > (thisYear % 100))
				return (thisYear / 100 - 1) * 100 + year;
			else
				return (thisYear / 100) * 100 + year;
		}
	}

	public static boolean isDateValid( int day, int month, int year )
	{
		long dayLimit;
		if( day <= 0 || month <= 0 || month > 12 || year < 1900 )
			return false;
		if( month == 2 )
		{
			if( ( year % 4 == 0 && year % 100 != 0 ) || year % 400 == 0 )
				dayLimit = 29;
			else
				dayLimit = 28;
		}
		else if( month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 )
			dayLimit = 31;
		else
			dayLimit = 30;
		if( day > dayLimit )
			return false;
		else
		{
			Calendar c1 = Calendar.getInstance();
			c1.setTime( new java.util.Date() );
			Calendar c2 = Calendar.getInstance();
			c2.set( year, month - 1, day );
			if( c2.after(c1) )
				return false;
			else
				return true;
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

	public TableEditingTextCell<S, T> getThis()
	{
		return this;
	}
}