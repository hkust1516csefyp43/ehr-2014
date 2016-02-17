package sight;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
*
* @author Alton
* @version 1.0
* @date 22/05/2015
* 
*/

public class PharmacyPatientController implements Initializable {

	private PharmacyPanelController panelCtrl;
	@FXML private Tab currentTab;
	@FXML private Label PatientNameLabel;
	@FXML private Button btnDelete;
	@FXML private Button btnSeparate;
	@FXML private Button btnPrescribe;
	@FXML private TableView<Prescription> tvPrescriptionTable;
	@FXML private TableColumn<Prescription, Boolean> tbPrescribe;
	@FXML private TableColumn<Prescription, String> tbMedicine;
	@FXML private TableColumn<Prescription, String> tbStrength;
	@FXML private TableColumn<Prescription, String> tbDosage;
	@FXML private TableColumn<Prescription, String> tbRoute;
	@FXML private TableColumn<Prescription, Integer> tbFrequency;
	@FXML private TableColumn<Prescription, Integer> tbNumOfDays;
	@FXML private TableColumn<Prescription, String> tbBrand;
	@FXML private TableColumn<Prescription, Float> tbQuantity;
	@FXML private TableColumn<Prescription, String> tbUnitOfQuantity;
	@FXML private TableColumn<Prescription, String> tbRemark;

	@FXML private Label PrescriptionLabel;
	@FXML private ComboBox<String> cbStrength;
	@FXML private ComboBox<String> cbBrand;

	@FXML private TableView<Prescription> tvPrescriptedTable;
	@FXML private TableColumn<Prescription, String> tbMedicine2;
	@FXML private TableColumn<Prescription, String> tbStrength2;
	@FXML private TableColumn<Prescription, Integer> tbDosage2;
	@FXML private TableColumn<Prescription, String> tbRoute2;
	@FXML private TableColumn<Prescription, Integer> tbFrequency2;
	@FXML private TableColumn<Prescription, Integer> tbNumOfDays2;
	@FXML private TableColumn<Prescription, String> tbBrand2;
	@FXML private TableColumn<Prescription, Float> tbQuantity2;
	@FXML private TableColumn<Prescription, String> tbUnitOfQuantity2;
	@FXML private TableColumn<Prescription, String> tbRemark2;

	private PatientVisit patientVisit;
	private long visitID;//patientID
	//private ArrayList<Prescription> prescriptionList;
	//private long slumID = -1;
	private String curEditingMedicine = "";
	private boolean isChangingSelection = false;
	//private boolean isViewPatient = false;
	private int userID;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		userID = User.getCurrentUser().getid();
		initPrescriptionTable();
		//prescriptionList = new ArrayList<>();
		initComboBox();
	}	

	@FXML
	public void btnDeletePressed( ActionEvent Event )
	{
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		for( int i = currentPrescriptionList.size()-1; i >= 0; i-- )
		{
			Prescription p = currentPrescriptionList.get(i);
			if( p.getisSelected() && p.getprescriptionID() == -1 )
				currentPrescriptionList.remove(p);
		}
		tvPrescriptionTable.setItems(currentPrescriptionList);
		refreshPrescriptionTable();
	}

	@FXML
	public void btnSeparatePressed( ActionEvent Event )
	{
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		for( int i = currentPrescriptionList.size()-1; i >= 0; i-- )
		{
			Prescription p = currentPrescriptionList.get(i);
			if( p.getisSelected() )
			{
				p.setisSelected(false);
				Prescription newP = new Prescription(-1, p.getmedicine(), p.getdosage(), p.getunitOfDosage(), p.getroute(), p.getstrength(), p.getfrequency(), p.getnumOfDays(), p.getquantity(), "", "", p.getremark(), p.getisPrescripted(), p.getinventoryID(), p.getvisitID(), p.getdoctorID(), p.getpharmacistID());
				newP.setunitOfQuantity(p.getunitOfQuantity());
				currentPrescriptionList.add(newP);
			}
		}
		tvPrescriptionTable.setItems(currentPrescriptionList);
		refreshPrescriptionTable();
	}

	@FXML
	public void btnPrescribePressed( ActionEvent Event )
	{
		if(btnPrescribe.getText().equals("Finish") || btnPrescribe.getText().equals("OK"))
		{
			PatientVisit pv = DatabaseController.getPatientVisit(visitID);
			if(!pv.getisFinished())
				DatabaseController.updatePatientVisit( visitID, userID, true, true, true );
			panelCtrl.finishPatient();
			return;
		}
		ArrayList<Prescription> selectedList = new ArrayList<>();
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		ObservableList<Prescription> prescriptedList = tvPrescriptedTable.getItems();
		for( int i = 0; i < currentPrescriptionList.size(); i++ )
		{
			Prescription p = currentPrescriptionList.get(i);
			//prescriptionList.add(p);
			if(p.getisSelected())
			{
				if(p.getstrength().isEmpty())
				{
					promptMessage("One-2-One Medical System", "Missing Information!", "Please fill the strength!", AlertType.ERROR, 14);
					return;
				}
				else if(p.getbrand() == null || p.getbrand().isEmpty())
				{
					promptMessage("One-2-One Medical System", "Missing Information!", "Please fill the brand!", AlertType.ERROR, 14);
					return;
				}
				else if(p.getquantity() == 0)
				{
					Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Zero Quantity!", "Quantity of "+p.getmedicine()+" is zero, are you confirm to prescript?", AlertType.CONFIRMATION, 14);
					if(result.isPresent() && (result.get() == ButtonType.CANCEL))
						return;
				}
				float stock = DatabaseController.getMedicineQuantity(p.getmedicine(), p.getstrength(), p.getbrand());
				if( p.getquantity() > stock )
				{
					promptMessage("One-2-One Medical System", "Out of Stock!", p.getmedicine() + "is out of Stock!", AlertType.WARNING, 14);
					return;
				}
				selectedList.add(p);
			}
		}
		if(selectedList.size() == 0)
		{
			promptMessage("One-2-One Medical System", "No Item Selected.", "Please tick the corresponding checkboxes.", AlertType.WARNING, 14);
			return;
		}
		Optional<ButtonType> result = promptMessage("One-2-One Medical System", "Confirm To Prescribe?", "", AlertType.CONFIRMATION, 14);
		if(result.isPresent() && (result.get() == ButtonType.CANCEL))
			return;
		for( int i = 0; i < selectedList.size(); i++ )
		{
			Prescription p = selectedList.get(i);
			prescriptedList.add(p);
			if(p.getprescriptionID() == -1)
				p.setprescriptionID( DatabaseController.addPrescription(p) );
			float total = p.getquantity();
			while(total != 0.0)
			{
				String batchNo = DatabaseController.getBatchNumber(p.getmedicine(), p.getstrength(), p.getbrand());
				if(batchNo == null)
				{
					promptMessage("One-2-One Medical System", "Out of Stock!", p.getmedicine() + "is out of Stock!", AlertType.WARNING, 14);
					p.setquantity(total);
					return;
				}
				float amountForThisBatch = DatabaseController.getMedicineQuantity(p.getmedicine(), p.getstrength(), p.getbrand(), batchNo);
				if( amountForThisBatch > total )
				{
					p.setbatchNo(batchNo);
					int inventoryID = DatabaseController.updateInventory(p.getmedicine(), p.getbrand(), batchNo, total, true);
					DatabaseController.updatePrescription(p.getprescriptionID(), total, p.getbrand(), batchNo, true, inventoryID, userID);
					total = 0;
				}
				else if( amountForThisBatch == total )
				{
					p.setbatchNo(batchNo);
					int inventoryID = DatabaseController.updateInventory(p.getmedicine(), p.getbrand(), batchNo, total, false);
					DatabaseController.updatePrescription(p.getprescriptionID(), total, p.getbrand(), batchNo, true, inventoryID, userID);
					total = 0;
				}
				else
				{
					Prescription newP = new Prescription( -1, p.getmedicine(), p.getdosage(), p.getunitOfDosage(), p.getroute(), p.getstrength(), p.getfrequency(), p.getnumOfDays(), amountForThisBatch, p.getbrand(), p.getbatchNo(), p.getremark(), true, p.getinventoryID(), p.getvisitID(), p.getdoctorID(), userID );
					p.setbatchNo(batchNo);
					int inventoryID = DatabaseController.updateInventory(p.getmedicine(), p.getbrand(), batchNo, amountForThisBatch, false);
					long prescriptionID = DatabaseController.addPrescription(newP);
					newP.setprescriptionID(prescriptionID);
					DatabaseController.updatePrescription(prescriptionID, amountForThisBatch, newP.getbrand(), batchNo, true, inventoryID, userID);
					total -= amountForThisBatch;
				}
			}
		}
		for( int i = 0; i < selectedList.size(); i++ )
			currentPrescriptionList.remove(selectedList.get(i));
		if( currentPrescriptionList.size() == 0 )
		{
			DatabaseController.updatePatientVisit( visitID, userID, true, true, true );

			String msg = "\n\nPatient Name: " + patientVisit.getpatientName();
			//msg += "\nPatient ID: " + patientID;
			msg += "\nTag Number: " + DatabaseController.getTagNumber(visitID);
			msg += "\n\nThis is the end of the slum visit!";
			msg += "\nDo you want to go back to previous page?";
			result = promptMessage("One-2-One Medical System", "Successfully Submitted!", msg, AlertType.CONFIRMATION, 14);
			if(result.isPresent() && (result.get() == ButtonType.OK))
				panelCtrl.finishPatient();
			else
				btnPrescribe.setText("Finish");
		}
	}

	private void initPrescriptionTable()
	{
		tvPrescriptionTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		tvPrescriptionTable.setEditable( true );
		//tvPrescriptionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tbPrescribe.setCellValueFactory( new PropertyValueFactory<>("isSelected"));
		tbPrescribe.setCellFactory( CheckBoxTableCell.forTableColumn(tbPrescribe) );
		tbPrescribe.setText("\u2713");
		tbMedicine.setCellValueFactory( new PropertyValueFactory<>("medicine") );
		//tbMedicine.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfMedicine =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbMedicine.getPrefWidth(), "Medicine");
			}
		};
		tbMedicine.setCellFactory( cfMedicine );
		tbMedicine.setEditable( false );
		tbStrength.setCellValueFactory( new PropertyValueFactory<>("strength") );
		//tbStrength.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfStrength =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbStrength.getPrefWidth(), "Strength");
			}
		};
		tbStrength.setCellFactory( cfStrength );
		tbStrength.setEditable( false );
		//tbDosage.setCellValueFactory( new PropertyValueFactory<>("dosage") );
		//tbDosage.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		tbDosage.setCellValueFactory( new PropertyValueFactory<>("dosageString") );
		//tbDosage.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfDosage =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbRoute.getPrefWidth(), "DosageString");
			}
		};
		tbDosage.setCellFactory( cfDosage );
		tbDosage.setEditable( false );
		tbRoute.setCellValueFactory( new PropertyValueFactory<>("route") );
		//tbRoute.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfRoute =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbRoute.getPrefWidth(), "Route");
			}
		};
		tbRoute.setCellFactory( cfRoute );
		tbRoute.setEditable( false );
		tbFrequency.setCellValueFactory( new PropertyValueFactory<>("frequency") );
		//tbFrequency.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>> cfFrequency =
			new Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>>() {
			public TableCell<Prescription, Integer> call(TableColumn<Prescription, Integer> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_INT, tvPrescriptionTable, "Prescription", (int)tbFrequency.getPrefWidth(), "Frequency");
			}
		};
		tbFrequency.setCellFactory( cfFrequency );
		tbFrequency.setEditable( false );
		tbNumOfDays.setCellValueFactory( new PropertyValueFactory<>("numOfDays") );
		//tbNumOfDays.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>> cfNumOfDays =
			new Callback<TableColumn<Prescription, Integer>, TableCell<Prescription, Integer>>() {
			public TableCell<Prescription, Integer> call(TableColumn<Prescription, Integer> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_INT, tvPrescriptionTable, "Prescription", (int)tbNumOfDays.getPrefWidth(), "NumOfDays");
			}
		};
		tbNumOfDays.setCellFactory( cfNumOfDays );
		tbNumOfDays.setEditable( false );
		tbBrand.setCellValueFactory( new PropertyValueFactory<>("brand") );
		//tbBrand.setCellFactory( TextFieldTableCell.forTableColumn() );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfBrand =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbBrand.getPrefWidth(), "Brand");
			}
		};
		tbBrand.setCellFactory( cfBrand );
		tbBrand.setEditable( false );
		tbQuantity.setCellValueFactory( new PropertyValueFactory<>("quantity") );
		//tbQuantity.setCellFactory( TextFieldTableCell.forTableColumn(new FloatStringConverter()) );
		Callback<TableColumn<Prescription, Float>, TableCell<Prescription, Float>> cfQuantity =
			new Callback<TableColumn<Prescription, Float>, TableCell<Prescription, Float>>() {
			public TableCell<Prescription, Float> call(TableColumn<Prescription, Float> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_FLOAT, tvPrescriptionTable, "Prescription", (int)tbQuantity.getPrefWidth(), "Quantity");
			}
		};
		tbQuantity.setCellFactory( cfQuantity );
		tbUnitOfQuantity.setCellValueFactory( new PropertyValueFactory<>("unitOfQuantity") );
		Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>> cfUnitOfQuantity =
			new Callback<TableColumn<Prescription, String>, TableCell<Prescription, String>>() {
			public TableCell<Prescription, String> call(TableColumn<Prescription, String> p)
			{
				return new TableEditingTextCell<>(TableEditingTextCell.TYPE_TEXT, tvPrescriptionTable, "Prescription", (int)tbUnitOfQuantity.getPrefWidth(), "UnitOfQuantity");
			}
		};
		tbUnitOfQuantity.setCellFactory( cfUnitOfQuantity );
		tbUnitOfQuantity.setEditable( false );

		java.util.Collections.sort
		(
			tvPrescriptionTable.getItems(),
			new java.util.Comparator<Prescription>()
			{
			    @Override
			    public int compare(Prescription p1, Prescription p2)
			    {
			    	return p1.getmedicine().compareTo(p2.getmedicine());
			    }
			}
		);

		tvPrescriptedTable.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY );
		//tvPrescriptedTable.setEditable( false );
		tvPrescriptedTable.setEditable( true );
		//tbPrescript2.setCellValueFactory( new PropertyValueFactory<>("isSelected"));
		//tbPrescript2.setCellFactory( CheckBoxTableCell.forTableColumn(tbPrescript) );
		tbMedicine2.setCellValueFactory( new PropertyValueFactory<>("medicine") );
		tbMedicine2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbDosage2.setCellValueFactory( new PropertyValueFactory<>("dosage") );
		tbDosage2.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		tbRoute2.setCellValueFactory( new PropertyValueFactory<>("route") );
		tbRoute2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbFrequency2.setCellValueFactory( new PropertyValueFactory<>("frequency") );
		tbFrequency2.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		tbNumOfDays2.setCellValueFactory( new PropertyValueFactory<>("numOfDays") );
		tbNumOfDays2.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()) );
		tbBrand2.setCellValueFactory( new PropertyValueFactory<>("brand") );
		tbBrand2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbStrength2.setCellValueFactory( new PropertyValueFactory<>("strength") );
		tbStrength2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbQuantity2.setCellValueFactory( new PropertyValueFactory<>("quantity") );
		tbQuantity2.setCellFactory( TextFieldTableCell.forTableColumn(new FloatStringConverter()) );
		tbUnitOfQuantity2.setCellValueFactory( new PropertyValueFactory<>("unitOfQuantity") );
		tbUnitOfQuantity2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbRemark2.setCellValueFactory( new PropertyValueFactory<>("remark") );
		tbRemark2.setCellFactory( TextFieldTableCell.forTableColumn() );
		tbRemark2.setEditable(true);
		java.util.Collections.sort
		(
			tvPrescriptedTable.getItems(),
			new java.util.Comparator<Prescription>()
			{
			    @Override
			    public int compare(Prescription p1, Prescription p2)
			    {
			    	return p1.getmedicine().compareTo(p2.getmedicine());
			    }
			}
		);
	}

	private void initComboBox()
	{
		cbStrength.setDisable(true);
		cbBrand.setDisable(true);
		tvPrescriptionTable.getSelectionModel().selectedItemProperty().addListener(
		(obs, oldSelection, newSelection) -> {
		    if(newSelection != null)
		    {
		    	isChangingSelection = true;
		    	curEditingMedicine = newSelection.getmedicine();
		    	PrescriptionLabel.setText("Prescription: "+curEditingMedicine);
		    	ObservableList<String> strengthList = FXCollections.observableList( DatabaseController.getStrengthList(curEditingMedicine));
		    	cbStrength.getSelectionModel().select(newSelection.getstrength());
		    	cbStrength.setItems(strengthList);
		    	cbStrength.setDisable(false);
		    	cbBrand.setDisable(false);
				ObservableList<String> brandList = FXCollections.observableList( DatabaseController.getBrandList(curEditingMedicine, newSelection.getstrength()) );
				cbBrand.setItems(brandList);
				cbBrand.getSelectionModel().select(-1);
		    	isChangingSelection = false;
		    }
		});
		cbStrength.valueProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(isChangingSelection)
						return;
					String strength = cbStrength.getSelectionModel().getSelectedItem();
					ObservableList<String> list = FXCollections.observableList( DatabaseController.getBrandList(curEditingMedicine, strength) );
					cbBrand.setItems(list);
					cbBrand.getSelectionModel().select(-1);
					tvPrescriptionTable.getSelectionModel().getSelectedItem().setstrength(cbStrength.getSelectionModel().getSelectedItem());
					refreshPrescriptionTable();
				}
			}
		);
		cbBrand.valueProperty().addListener
		(
			new ChangeListener<String>()
			{
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue)
				{
					if(isChangingSelection)
						return;
					String brand = cbBrand.getSelectionModel().getSelectedItem();
					tvPrescriptionTable.getSelectionModel().getSelectedItem().setbrand(brand);
					refreshPrescriptionTable();
				}
			}
		);
	}

	private void refreshPrescriptionTable()
	{
		java.util.Collections.sort
		(
			tvPrescriptionTable.getItems(),
			new java.util.Comparator<Prescription>()
			{
			    @Override
			    public int compare(Prescription p1, Prescription p2)
			    {
			    	return p1.getmedicine().compareTo(p2.getmedicine());
			    }
			}
		);
		long num = tvPrescriptionTable.getItems().size();
		for( int i = 0; i < num; i++ )
		{
			tvPrescriptionTable.getColumns().get(i).setVisible(false);
			tvPrescriptionTable.getColumns().get(i).setVisible(true);
		}
	}

	public void setPharmacyPanelController( PharmacyPanelController ctrl )
	{
		this.panelCtrl = ctrl;
	}

	public void setPatientVisit( PatientVisit pv )
	{
		this.patientVisit = pv;
		//this.patientID = pv.getpatientID();
		this.visitID = pv.getvisitID();
		ArrayList<Prescription> prescriptionList = DatabaseController.getPrescriptionList(visitID, false);
		ArrayList<Prescription> prescriptedList = DatabaseController.getPrescriptionList(visitID, true);
		setPrescriptionList(prescriptionList, prescriptedList);
		Patient p = DatabaseController.getPatient(pv.getpatientID());
		PatientNameLabel.setText("Patient: "+p.getfirstName()+" "+p.getlastName());
		ArrayList<PatientHistory> followUpList = DatabaseController.getPatientHistoryList(pv.getpatientID(), visitID, PatientHistory.TYPE_FOL);
		if(followUpList.size() != 0)
		{
			String msg = "Please follow up with this patient:\n";
			for(PatientHistory ph : followUpList)
				if(ph.getremarks().equals("Refer To Nurse"))
					msg += "Nurse: " + ph.getdescription()+"\n";
				else if(ph.getremarks().equals("Refer To Hospital"))
					msg += "Hospital: " + ph.getdescription()+"\n";
				else
					msg += "Follow Up: " + ph.getdescription()+"\n";
			promptMessage("One-2-One Medical System", "Follow Up", msg, AlertType.INFORMATION, 14);
		}
	}

	public void setPrescriptionList( ArrayList<Prescription> pList1, ArrayList<Prescription> pList2 )
	{
		ObservableList<Prescription> currentPrescriptionList = tvPrescriptionTable.getItems();
		currentPrescriptionList.addAll( pList1 );
		tvPrescriptionTable.setItems( currentPrescriptionList );
		if(currentPrescriptionList.size() == 0)
			btnPrescribe.setText("Finish");

		ObservableList<Prescription> prescriptedList = tvPrescriptedTable.getItems();
		prescriptedList.addAll( pList2 );
		tvPrescriptedTable.setItems( prescriptedList );
	}

	/*public void setSlum(long slumID)
	{
		this.slumID = slumID;
	}*/

	public void isView(boolean isView)
	{
		//this.isViewPatient = isView;
		if(isView)
		{
			btnDelete.setVisible(false);
			btnSeparate.setVisible(false);
			btnPrescribe.setText("OK");
			tbQuantity.setEditable(false);
		}
	}

	public Optional<ButtonType> promptMessage( String title, String header, String msg, AlertType type, int contentFontSize )
	{
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		DialogPane dp = alert.getDialogPane();
		dp.setPrefWidth(400);
		dp.lookup(".label.content").setStyle("-fx-font-size: "+contentFontSize+"px;");
		dp.lookup(".header-panel").setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		return alert.showAndWait();
	}
}