import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

public class FoodPermitsGUI extends Application {

	private RadioButton truckRadioButton, cartRadioButton, approvedRadioButton, expiredRadioButton, issuedRadioButton, requestedRadioButton, suspendedRadioButton;

	private Text resultBox;
	private TextField foodTruckInputField, addressInputField;
	private Button submitButton;
	private List<FoodPermit> foodPermitList = readFoodFromCSV("src/FoodPermits.csv");
	private ToggleGroup permitStatus, foodFacility;

	public void start(Stage primaryStage) {

		primaryStage.setHeight(500);
		primaryStage.setWidth(700);
		primaryStage.setResizable(false);
		VBox mainVBox = new VBox();
		mainVBox.setStyle("-fx-background-color: lavender");
		mainVBox.setAlignment(Pos.CENTER);
		mainVBox.setSpacing(10);

		Text nameText = new Text("Please Input the Name of the Food Facility");
		foodTruckInputField = new TextField();
		foodTruckInputField.setMaxWidth(200);
		foodTruckInputField.setOnAction(this::processTextField);
		VBox nameBox = new VBox(nameText,foodTruckInputField);
		nameBox.setAlignment(Pos.CENTER);
		mainVBox.getChildren().add(nameBox);

		Text addressText = new Text("Please Input the Corresponding Address");
		addressInputField = new TextField();
		addressInputField.setMaxWidth(200);
		addressInputField.setOnAction(this::processTextField);
		mainVBox.getChildren().add(addressInputField);
		VBox addressBox = new VBox(addressText,addressInputField);
		addressBox.setAlignment(Pos.CENTER);
		mainVBox.getChildren().add(addressBox);

		Text foodFacilityText = new Text("What type of food facility?");
		foodFacilityText.setFont(Font.font(12));
		foodFacilityText.setFill(Color.DARKRED);
		truckRadioButton = new RadioButton("Food Truck");
		truckRadioButton.setSelected(true);
		cartRadioButton = new RadioButton("Food Cart");

		foodFacility = new ToggleGroup();
		truckRadioButton.setToggleGroup(foodFacility);
		cartRadioButton.setToggleGroup(foodFacility);

		HBox serviceBox = new HBox(foodFacilityText, truckRadioButton, cartRadioButton);
		serviceBox.setAlignment(Pos.CENTER);
		serviceBox.setSpacing(10);
		mainVBox.getChildren().add(serviceBox);

		Text permitStatusText = new Text("What type of permit status?");
		permitStatusText.setFont(Font.font(12));
		permitStatusText.setFill(Color.DARKRED);
		approvedRadioButton = new RadioButton("Approved");
		approvedRadioButton.setSelected(true);
		expiredRadioButton = new RadioButton("Expired");
		issuedRadioButton = new RadioButton("Issued");
		requestedRadioButton = new RadioButton("Requested");
		suspendedRadioButton = new RadioButton("Suspended");

		permitStatus = new ToggleGroup();
		approvedRadioButton.setToggleGroup(permitStatus);
		expiredRadioButton.setToggleGroup(permitStatus);
		issuedRadioButton.setToggleGroup(permitStatus);
		requestedRadioButton.setToggleGroup(permitStatus);
		suspendedRadioButton.setToggleGroup(permitStatus);

		HBox permitBox = new HBox(permitStatusText, approvedRadioButton, expiredRadioButton, issuedRadioButton,
				requestedRadioButton, suspendedRadioButton);
		permitBox.setAlignment(Pos.CENTER);
		permitBox.setSpacing(10);
		mainVBox.getChildren().add(permitBox);

		resultBox = new Text("Please enter a name to search");
		resultBox.setFont(Font.font("Comic Sans MS", 14));
		resultBox.setFill(Color.BLACK);
		resultBox.setWrappingWidth(600);

		submitButton = new Button("Submit");
		submitButton.setOnAction(this::processTextField);

		mainVBox.getChildren().add(submitButton);
		mainVBox.getChildren().add(resultBox);

		Scene scene = new Scene(mainVBox, 350, 400);

		primaryStage.setTitle("Food Permits");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	private void processTextField(ActionEvent event) {
		String userInputText = foodTruckInputField.getText();
		String userInputAddress = addressInputField.getText();
		boolean foundFacility = false;
		boolean foundPermit = false;
		if (!userInputText.equals("") && !userInputAddress.equals("")) {
			resultBox.setText("Searching for: " + userInputText + "\nAt :" + userInputAddress);
			if (foodPermitList != null) {
				for (FoodPermit foodPermit : foodPermitList) {
					if (foodPermit.getName().equalsIgnoreCase(userInputText) && foodPermit.getAddress().equalsIgnoreCase(userInputAddress)) {
						if(truckRadioButton.isSelected()){
							if(foodPermit.getFacilityType().equals(FacilityType.valueOf("TRUCK"))){
								foundFacility = true;
							}
						}
						else{
							if(foodPermit.getFacilityType().equals(FacilityType.valueOf("PUSH_CART"))){
								foundFacility = true;
							}
						}
						if(approvedRadioButton.isSelected()){
							if(foodPermit.getPermitStatus().equals(PermitStatus.valueOf("APPROVED"))){
								foundPermit = true;
							}
						}
						else if(expiredRadioButton.isSelected()){
							if(foodPermit.getPermitStatus().equals(PermitStatus.valueOf("EXPIRED"))){
								foundPermit = true;
							}
						}
						else if(issuedRadioButton.isSelected()){
							if(foodPermit.getPermitStatus().equals(PermitStatus.valueOf("ISSUED"))){
								foundPermit = true;
							}
						}
						else if(requestedRadioButton.isSelected()){
							if(foodPermit.getPermitStatus().equals(PermitStatus.valueOf("REQUESTED"))){
								foundPermit = true;
							}
						}
						else if (suspendedRadioButton.isSelected()) {
							if(foodPermit.getPermitStatus().equals(PermitStatus.valueOf("SUSPEND"))){
								foundPermit = true;
							}
						}
						if(foundFacility && foundPermit){
							resultBox.setFill(Color.BLACK);
							resultBox.setText(foodPermit.toString());
						}
					}
				}
			}
			if(!foundFacility || !foundPermit) {
				resultBox.setText("Valid Permit Not Found");
				resultBox.setFill(Color.RED);
			}
		} else {
			resultBox.setText("Please Input A Valid Name and Address Pair");
			resultBox.setFill(Color.RED);
		}
	}
	public static List<FoodPermit> readFoodFromCSV(String fileName) {
		List<FoodPermit> foodie = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				FoodPermit food = new FoodPermit(attributes[0], attributes[2], attributes[4],
						PermitStatus.valueOf(attributes[3].toUpperCase()), FacilityType.valueOf(attributes[1].toUpperCase()));
				foodie.add(food);
				line = br.readLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return foodie;
	}

}
