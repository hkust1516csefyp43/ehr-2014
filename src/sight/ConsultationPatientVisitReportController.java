package sight;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ConsultationPatientVisitReportController implements Initializable
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

	public void setPatientVisit(long visitID)
	{
		PatientVisit pv = DatabaseController.getPatientVisit(visitID);
		long patientID = pv.getpatientID();
		showPersonalData(DatabaseController.getPatient(patientID), pv.getvisitDateTimeString());
		showTriageRecord(DatabaseController.getTriageRecord(visitID));
		showChiefComplaint(DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_CC));
		showHistory("Updated History in Triage Section", DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_PMH, PatientHistory.TYPE_SCR, PatientHistory.TYPE_DH, PatientHistory.TYPE_ALE));
		showFemaleRecord(DatabaseController.getFemaleRecord(patientID));
		showHistory("Updated History in Consultation Section", DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_HPI, PatientHistory.TYPE_FH, PatientHistory.TYPE_SH));
		showHistory("Review of System", DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_ROS));
		showHistory("Physical Examination & Clinical Diagnosis", DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_PE, PatientHistory.TYPE_CD));
		
		ArrayList<Prescription> preList = DatabaseController.getPrescriptionList(visitID, false);
		ArrayList<Prescription> preList2 = DatabaseController.getPrescriptionList(visitID, true);
		preList.addAll(preList2);
		showMedication(preList, DatabaseController.getPatientHistoryList(patientID, visitID, PatientHistory.TYPE_ADV, PatientHistory.TYPE_FOL));

		Label endOfReportLabel = new Label("-This is the end of the report-");
		endOfReportLabel.setLayoutX(10);
		endOfReportLabel.setLayoutY(yPosition+=30);
		endOfReportLabel.setFont(new Font("System Bold", 18));
		endOfReportLabel.setPrefWidth(850);
		endOfReportLabel.setAlignment(Pos.CENTER);
		pane.getChildren().add(endOfReportLabel);
		pane.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));

		/*Button printButton = new Button("Print");
		printButton.setLayoutX(765);
		printButton.setLayoutY(10);
		printButton.setPrefWidth(100);
		printButton.setPrefHeight(20);
		printButton.setOnAction
		(
			new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent arg0)
				{
					Printer printer = Printer.getDefaultPrinter();
					PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
					double scaleX = pageLayout.getPrintableWidth() / pane.getBoundsInParent().getWidth();
					double scaleY = pageLayout.getPrintableHeight() / pane.getBoundsInParent().getHeight();
					pane.getTransforms().add(new Scale(scaleX, scaleY));
					PrinterJob job = PrinterJob.createPrinterJob();
					if(job != null)
					{
						boolean success = job.printPage(pane);
						if(success)
							job.endJob();
					}
				}
			}
		);
		pane.getChildren().add(printButton);*/
	}

	private void showPersonalData(Patient p, String visitDate)
	{
		String patientName = p.getlastName();
		/*if( !p.getmiddleName().isEmpty() )
			patientName += (" " + p.getmiddleName());
		else */if( !p.getfirstName().isEmpty() )
			patientName += (" " + p.getfirstName());
		Label patientNameLabel = new Label(patientName+"'s visit on "+visitDate.substring(0, 11));
		patientNameLabel.setLayoutX(10);
		patientNameLabel.setLayoutY(yPosition+=30);
		patientNameLabel.setFont(new Font("System Bold", 18));
		patientNameLabel.setPrefWidth(850);
		patientNameLabel.setAlignment(Pos.CENTER);
		pane.getChildren().add(patientNameLabel);
		/*List<String> list = Font.getFontNames();
		for(String s : list)
			System.out.println(s);*/

		Label subtitleLabel = new Label("Personal Data");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		pane.getChildren().add(subtitleLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.ENGLISH);
		Label dobAndAgeLabel;
		if(p.getageYear()== 0)
			dobAndAgeLabel = new Label("Date of Birth: "+sdf.format(p.getDOB())+"     Age: "+p.getageMonth()+" month(s) "+p.getageDay()+" day(s)");
		else
			dobAndAgeLabel = new Label("Date of Birth: "+sdf.format(p.getDOB())+"     Age: "+p.getageYear());
		dobAndAgeLabel.setLayoutX(10);
		dobAndAgeLabel.setLayoutY(yPosition+=30);
		dobAndAgeLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(dobAndAgeLabel);

		Label sexAndStatusLabel = new Label("Sex: "+p.getsex()+"     Status: "+p.getstatus());
		sexAndStatusLabel.setLayoutX(10);
		sexAndStatusLabel.setLayoutY(yPosition+=30);
		sexAndStatusLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(sexAndStatusLabel);

		Label telphoneLabel = new Label("Telephone No.: "+p.gettelNo());
		telphoneLabel.setLayoutX(10);
		telphoneLabel.setLayoutY(yPosition+=30);
		telphoneLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(telphoneLabel);

		Label addressLabel = new Label("Address: "+p.getaddress());
		addressLabel.setLayoutX(10);
		addressLabel.setLayoutY(yPosition+=30);
		addressLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(addressLabel);
	}

	private void showTriageRecord(TriageRecord tr)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Vital Signs");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		pane.getChildren().add(subtitleLabel);

		Label bloodPressureLabel = new Label("Blood Pressure: "+tr.getSBP()+" / "+tr.getDBP()+" mmHg");
		bloodPressureLabel.setLayoutX(10);
		bloodPressureLabel.setLayoutY(yPosition+=30);
		bloodPressureLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(bloodPressureLabel);

		Label pulseLabel = new Label("Pulse: "+tr.getPR()+" bpm");
		pulseLabel.setLayoutX(10);
		pulseLabel.setLayoutY(yPosition+=30);
		pulseLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(pulseLabel);

		Label respiratoryLabel = new Label("Respiratory Rate: "+tr.getRR()+" cmn");
		respiratoryLabel.setLayoutX(10);
		respiratoryLabel.setLayoutY(yPosition+=30);
		respiratoryLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(respiratoryLabel);

		Label temperatureLabel = new Label("Temperature: "+tr.gettemperature()+" \u00b0"+tr.gettemperatureScale());
		temperatureLabel.setLayoutX(10);
		temperatureLabel.setLayoutY(yPosition+=30);
		temperatureLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(temperatureLabel);

		Label spo2Label = new Label("SPO2: "+tr.getspo2()+" %");
		spo2Label.setLayoutX(10);
		spo2Label.setLayoutY(yPosition+=30);
		spo2Label.setFont(new Font("System Bold", 14));
		pane.getChildren().add(spo2Label);

		Label weightLabel = new Label("Weight: "+tr.getweight()+" kg");
		weightLabel.setLayoutX(10);
		weightLabel.setLayoutY(yPosition+=30);
		weightLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(weightLabel);

		Label heightLabel = new Label("Height: "+tr.getheight()+" cm");
		heightLabel.setLayoutX(10);
		heightLabel.setLayoutY(yPosition+=30);
		heightLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(heightLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", java.util.Locale.ENGLISH);
		String ldtString;
		if(tr.getLDT() != null)
			ldtString = sdf.format(tr.getLDT());
		else
			ldtString = "N/A";
		Label ldtLabel = new Label("Last de-worming tablet: "+ldtString);
		ldtLabel.setLayoutX(10);
		ldtLabel.setLayoutY(yPosition+=30);
		ldtLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(ldtLabel);
	}

	private void showChiefComplaint(ArrayList<PatientHistory> ccList)
	{	
		yPosition += 30;

		Label subtitleLabel = new Label("Chief Complaint");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		pane.getChildren().add(subtitleLabel);

		String s;
		if(ccList.size() == 0)
			s = "N/A";
		else
			s = ccList.get(0).getdescription();
		Label chiefComplaintLabel = new Label(s);
		chiefComplaintLabel.setLayoutX(10);
		chiefComplaintLabel.setLayoutY(yPosition+=30);
		chiefComplaintLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(chiefComplaintLabel);
	}

	private void showHistory(String subtitle, ArrayList<PatientHistory> patientHistoryList)
	{
		yPosition += 30;

		if(subtitle != null)
		{
			Label subtitleLabel = new Label(subtitle);
			subtitleLabel.setLayoutX(10);
			subtitleLabel.setLayoutY(yPosition+=30);
			subtitleLabel.setFont(new Font("System Bold", 16));
			subtitleLabel.setUnderline(true);
			pane.getChildren().add(subtitleLabel);
		}

		if(patientHistoryList.size() == 0)
		{
			Label noneLabel = new Label("No Update.");
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			noneLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(noneLabel);
		}
		for(PatientHistory ph : patientHistoryList)
		{
			String type;
			switch(ph.gethistoryType())
			{
				case PatientHistory.TYPE_PMH:
					type = "Previous Medical History: ";
					break;
				case PatientHistory.TYPE_SCR:
					if(ph.getremarks().equalsIgnoreCase("other"))
						type = "Screening(Other): ";
					else
						type = "Screening: ";
					break;
				case PatientHistory.TYPE_DH:
					type = "Drug History: ";
					break;
				case PatientHistory.TYPE_ALE:
					type = "Allergy: ";
					break;
				case PatientHistory.TYPE_HPI:
					type = "History of Present Illness: ";
					break;
				case PatientHistory.TYPE_FH:
					type = "Family History: ";
					break;
				case PatientHistory.TYPE_SH:
					type = "Social History: ";
					break;
				case PatientHistory.TYPE_ROS:
					type = ph.getremarks()+": ";
					break;
				case PatientHistory.TYPE_PE:
					type = "Physical Examination: ";
					break;
				case PatientHistory.TYPE_CD:
					type = "Clinical Diagnosis: ";
					break;
				default:
					type = "History: ";
			}
			Label historyLabel = new Label(type+ph.getdescription());
			historyLabel.setLayoutX(10);
			historyLabel.setLayoutY(yPosition+=30);
			historyLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(historyLabel);
		}
	}

	private void showFemaleRecord(FemaleRecord fr)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Female Record");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		pane.getChildren().add(subtitleLabel);

		if(fr.getLMP() != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", java.util.Locale.ENGLISH);
			Label lmpLabel;
			lmpLabel = new Label("LMP Date: "+sdf.format(fr.getLMP()));
			lmpLabel.setLayoutX(10);
			lmpLabel.setLayoutY(yPosition+=30);
			lmpLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(lmpLabel);
		}

		Label gestationLabel;
		if(fr.isPregnant())
			gestationLabel = new Label("Pregnant: YES     Gestation: "+fr.getgestation()+" week(s)");
		else
			gestationLabel = new Label("Pregnant: NO");
		gestationLabel.setLayoutX(10);
		gestationLabel.setLayoutY(yPosition+=30);
		gestationLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(gestationLabel);

		Label breastFeedingLabel;
		if(fr.isBreastFeeding())
			breastFeedingLabel = new Label("Breast Feeding: YES");
		else
			breastFeedingLabel = new Label("Breast Feeding: NO");
		breastFeedingLabel.setLayoutX(10);
		breastFeedingLabel.setLayoutY(yPosition+=30);
		breastFeedingLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(breastFeedingLabel);

		Label contraceptiveLabel;
		if(fr.isContraceptive())
			contraceptiveLabel = new Label("Contraceptive: YES");
		else
			contraceptiveLabel = new Label("Contraceptive: NO");
		contraceptiveLabel.setLayoutX(10);
		contraceptiveLabel.setLayoutY(yPosition+=30);
		contraceptiveLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(contraceptiveLabel);

		Label numOfPregnancyLabel = new Label("No. of Pregnancy: "+fr.getnumOfPregnancy());
		numOfPregnancyLabel.setLayoutX(10);
		numOfPregnancyLabel.setLayoutY(yPosition+=30);
		numOfPregnancyLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(numOfPregnancyLabel);

		Label numOfLiveBirthLabel = new Label("No. of Live Birth: "+fr.getnumOfLiveBirth());
		numOfLiveBirthLabel.setLayoutX(10);
		numOfLiveBirthLabel.setLayoutY(yPosition+=30);
		numOfLiveBirthLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(numOfLiveBirthLabel);

		Label numOfMiscarriageLabel = new Label("No. of Miscarriage: "+fr.getnumOfMiscarriage());
		numOfMiscarriageLabel.setLayoutX(10);
		numOfMiscarriageLabel.setLayoutY(yPosition+=30);
		numOfMiscarriageLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(numOfMiscarriageLabel);

		Label numOfAbortionLabel = new Label("No. of Abortion: "+fr.getnumOfAbortion());
		numOfAbortionLabel.setLayoutX(10);
		numOfAbortionLabel.setLayoutY(yPosition+=30);
		numOfAbortionLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(numOfAbortionLabel);

		Label numOfStillBirthLabel = new Label("No. of Still Birth: "+fr.getnumOfStillBirth());
		numOfStillBirthLabel.setLayoutX(10);
		numOfStillBirthLabel.setLayoutY(yPosition+=30);
		numOfStillBirthLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(numOfStillBirthLabel);

		Label otherInfoLabel;
		if(fr.getotherInfo().isEmpty())
			otherInfoLabel = new Label("Other Information: N/A");
		else
			otherInfoLabel = new Label("Other Information: "+fr.getotherInfo());
		otherInfoLabel.setLayoutX(10);
		otherInfoLabel.setLayoutY(yPosition+=30);
		otherInfoLabel.setFont(new Font("System Bold", 14));
		pane.getChildren().add(otherInfoLabel);
	}

	private void showMedication(ArrayList<Prescription> preList, ArrayList<PatientHistory> advFollowList)
	{
		yPosition += 30;

		Label subtitleLabel = new Label("Medication, Advice and Follow-Up");
		subtitleLabel.setLayoutX(10);
		subtitleLabel.setLayoutY(yPosition+=30);
		subtitleLabel.setFont(new Font("System Bold", 16));
		subtitleLabel.setUnderline(true);
		pane.getChildren().add(subtitleLabel);

		for(int i = 0; i < preList.size(); i++)
		{
			Prescription p = preList.get(i);
			String line = (i+1)+". "+p.getmedicine()+": ";
			line += p.getdosage()+"mg/ml each time\n     "+p.getfrequency()+" times/day, "+p.getnumOfDays()+" days.\n";
			line += "     Route: "+p.getroute();
			Label noneLabel = new Label(line);
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			yPosition+=30;
			noneLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(noneLabel);
		}

		if(advFollowList.size() == 0)
		{
			Label noneLabel = new Label("No advice or follow-up action taken.");
			noneLabel.setLayoutX(10);
			noneLabel.setLayoutY(yPosition+=30);
			noneLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(noneLabel);
		}
		for(PatientHistory ph : advFollowList)
		{
			String type;
			switch(ph.gethistoryType())
			{
				case PatientHistory.TYPE_ADV:
					type = "Advice: ";
					break;
				case PatientHistory.TYPE_FOL:
					if(ph.getremarks().isEmpty())
						type = "Follow-Up: ";
					else
						type = ph.getremarks() +": ";
					break;
				default:
					type = "History: ";
			}
			Label historyLabel = new Label(type+ph.getdescription());
			historyLabel.setLayoutX(10);
			historyLabel.setLayoutY(yPosition+=30);
			historyLabel.setFont(new Font("System Bold", 14));
			pane.getChildren().add(historyLabel);
		}
	}
}
