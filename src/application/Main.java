package application;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		// Make an arrayList
		ArrayList<String> questionsArrayList = new ArrayList<>();

		// This method will read from the file and save it to the ArrayList
		readToArray(questionsArrayList);

		
		//-----------------------------StartPage-----------------------------//
		
		// Label of the program in the first menu
		Text startMessage = new Text("Quiz bank Program");
		startMessage.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 36));
		// Adds the Start Button in the Start menu
		Button startProgramButton = new Button("Start");
		startProgramButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 36));

		VBox mainPane = new VBox();
		mainPane.getChildren().addAll(startMessage, startProgramButton);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(125);

		Scene startPage = new Scene(mainPane,720,480);
		primaryStage.setTitle("Question bank");
		primaryStage.setResizable(false);
		primaryStage.setScene(startPage);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				saveToFile(questionsArrayList);
				primaryStage.close();

			}
		});
		//-----------------------------End-----------------------------//
		
		
		
		//-----------------------------MainMenu-----------------------------//

		// Create buttons
		Button createNewQuestions = new Button("Create");
		Button backToStartMenu = new Button("Back");
		Button editQuestion = new Button("Edit");
		Button viewQuestion = new Button("View");
		Button deleteQuestion = new Button("Delete");
		Button exitButton = new Button("Exit & Save");

		// Sets the Fonts for the buttons
		createNewQuestions.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		backToStartMenu.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		editQuestion.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		viewQuestion.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		exitButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		deleteQuestion.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));

		// insert nodes into Layout boxes
		HBox midPane = new HBox();
		midPane.getChildren().addAll(createNewQuestions, viewQuestion, editQuestion, deleteQuestion);
		midPane.setAlignment(Pos.CENTER);
		midPane.setSpacing(15);

		HBox leftBox = new HBox(backToStartMenu);
		leftBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(leftBox, Priority.ALWAYS);

		HBox rightBox = new HBox(exitButton);
		rightBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(rightBox, Priority.ALWAYS);

		HBox bottomPane = new HBox(leftBox, rightBox);
		bottomPane.setPadding(new Insets(10));
		// create Vertical box to hold the Hboxes we created
		VBox allPane = new VBox();
		allPane.getChildren().addAll(midPane, bottomPane); // add the Hbox panes to the Vbox

		// Align HBoxes and VBoxes in a Borderpane
		BorderPane borderPane = new BorderPane(allPane);
		borderPane.setCenter(midPane);
		borderPane.setBottom(bottomPane);
		borderPane.setMinSize(720, 480);
		borderPane.setPadding(new Insets(10));
		Scene mainMenu = new Scene(borderPane);
		//-----------------------------End-----------------------------//

		
		
		//-----------------------------Create Question-----------------------------//

		// Create 4 radio buttons to indicate the correct answer
		RadioButton radioButton1 = new RadioButton();
		RadioButton radioButton2 = new RadioButton();
		RadioButton radioButton3 = new RadioButton();
		RadioButton radioButton4 = new RadioButton();

		// Makes it so the user can only pick one RadioButton
		ToggleGroup groupRadio = new ToggleGroup();
		radioButton1.setToggleGroup(groupRadio);
		radioButton2.setToggleGroup(groupRadio);
		radioButton3.setToggleGroup(groupRadio);
		radioButton4.setToggleGroup(groupRadio);

		// create TextArea for the question (in case lengthy)
		TextArea question = new TextArea();
		question.setPromptText("Enter your question"); // a prompt text
		question.setFocusTraversable(false); // Not focused
		question.setPrefWidth(400); // set width
		question.setPrefHeight(50); // set Height

		// Process is the same for the code bellow
		TextField answer1 = new TextField();
		answer1.setPromptText("Enter your first answer");
		answer1.setFocusTraversable(false);
		answer1.setPrefWidth(400);
		answer1.setPrefHeight(30);

		TextField answer2 = new TextField();
		answer2.setPromptText("Enter your second answer");
		answer2.setFocusTraversable(false);
		answer2.setPrefWidth(400);
		answer2.setPrefHeight(30);

		TextField answer3 = new TextField();
		answer3.setPromptText("Enter your third answer");
		answer3.setFocusTraversable(false);
		answer3.setPrefWidth(400);
		answer3.setPrefHeight(30);

		TextField answer4 = new TextField();
		answer4.setPromptText("Enter your fourth answer");
		answer4.setFocusTraversable(false);
		answer4.setPrefWidth(400);
		answer4.setPrefHeight(30);

		// Text is to guide the user in using the program
		Label miniGuide = new Label(
				"Type your question and answers then select the correct answer by checking a button.\n"
						+ "You can only choose one answer to be correct.\n"
						+ "You must write a question to be able to save.");

		// Sets the prompt for the user to fill the questions and answers
		Label questionLable = new Label("Question: ", question);
		questionLable.setContentDisplay(ContentDisplay.RIGHT);
		Label answer1Lable = new Label("Answer A: ", answer1);
		answer1Lable.setContentDisplay(ContentDisplay.RIGHT);
		Label answer2Lable = new Label("Answer B: ", answer2);
		answer2Lable.setContentDisplay(ContentDisplay.RIGHT);
		Label answer3Lable = new Label("Answer C: ", answer3);
		answer3Lable.setContentDisplay(ContentDisplay.RIGHT);
		Label answer4Lable = new Label("Answer D: ", answer4);
		answer4Lable.setContentDisplay(ContentDisplay.RIGHT);

		// Button to navigate back to main menu and to save the question
		Button cancelToMainMenu = new Button("Cancel");
		cancelToMainMenu.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
		Button saveNewQuestionButton = new Button("Save");
		saveNewQuestionButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));

		GridPane createQuestion = new GridPane(); // a grid pane to store all the elements we created
		createQuestion.setAlignment(Pos.CENTER); // aligned in the center of the scene
		createQuestion.setHgap(10); // horizontal gap between nodes
		createQuestion.setVgap(15); // vertical gap between nodes

		// Adding the nodes to the grid pane (row,column)
		createQuestion.add(miniGuide, 0, 0);
		createQuestion.add(questionLable, 0, 1);
		createQuestion.add(answer1Lable, 0, 2);
		createQuestion.add(radioButton1, 1, 2);
		createQuestion.add(answer2Lable, 0, 3);
		createQuestion.add(radioButton2, 1, 3);
		createQuestion.add(answer3Lable, 0, 4);
		createQuestion.add(radioButton3, 1, 4);
		createQuestion.add(answer4Lable, 0, 5);
		createQuestion.add(radioButton4, 1, 5);

		// Layout panes for buttons
		HBox leftBoxCreate = new HBox(cancelToMainMenu);
		leftBoxCreate.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(leftBoxCreate, Priority.ALWAYS);

		HBox rightBoxCreate = new HBox(saveNewQuestionButton);
		rightBoxCreate.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(rightBoxCreate, Priority.ALWAYS);

		HBox bottomPaneCreate = new HBox(leftBoxCreate, rightBoxCreate);
		bottomPaneCreate.setSpacing(15); // Spacing between nodes

		BorderPane allPanesCreate = new BorderPane();
		allPanesCreate.setPadding(new Insets(15)); // outer border
		allPanesCreate.setCenter(createQuestion);
		allPanesCreate.setBottom(bottomPaneCreate);
		// Insert panes to the scene and set a size
		Scene createQuestionScene = new Scene(allPanesCreate, 720, 480);
		//-----------------------------End-----------------------------//
		
		
		
		//-----------------------------All Buttons-----------------------------//
		
		// From the Question Scene  //
		saveNewQuestionButton.setOnAction(e -> {
			if (!(question.getText().isEmpty()) && (radioButton1.isSelected() ^ radioButton2.isSelected()
					^ radioButton3.isSelected() ^ radioButton4.isSelected())) {
				questionsArrayList.add(question.getText());
				questionsArrayList.add(answer1.getText());
				questionsArrayList.add(answer2.getText());
				questionsArrayList.add(answer3.getText());
				questionsArrayList.add(answer4.getText());

				if (radioButton1.isSelected()) {
					questionsArrayList.add("The correct answer is: " + answer1.getText());
				}
				if (radioButton2.isSelected()) {
					questionsArrayList.add("The correct answer is: " + answer2.getText());
				}
				if (radioButton3.isSelected()) {
					questionsArrayList.add("The correct answer is: " + answer3.getText());
				}
				if (radioButton4.isSelected()) {
					questionsArrayList.add("The correct answer is: " + answer4.getText());
				}

				questionsArrayList.add("");
				question.clear();
				answer1.clear();
				answer2.clear();
				answer3.clear();
				answer4.clear();
				radioButton1.setSelected(false);
				radioButton2.setSelected(false);
				radioButton3.setSelected(false);
				radioButton4.setSelected(false);
				primaryStage.setScene(mainMenu);

			}
		});
		
		// From the Question Scene //
		cancelToMainMenu.setOnAction(e -> {
			question.clear();
			answer1.clear();
			answer2.clear();
			answer3.clear();
			answer4.clear();
			radioButton1.setSelected(false);
			radioButton2.setSelected(false);
			radioButton3.setSelected(false);
			radioButton4.setSelected(false);
			primaryStage.setScene(mainMenu);
		});
		
		
		
		// From the MainMenu Scene //
		viewQuestion.setOnAction(e -> {
			Button backButtonView = new Button("Back");
			backButtonView.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
			ListView<String> viewquestionsListView = new ListView<>(FXCollections.observableArrayList(questionsArrayList));
			viewquestionsListView.setPrefSize(200, 400);
			VBox showViewElements = new VBox();
			showViewElements.setPadding(new Insets(10));
			showViewElements.setAlignment(Pos.CENTER);
			showViewElements.getChildren().add(viewquestionsListView);
			showViewElements.getChildren().add(backButtonView);
			showViewElements.setSpacing(15);
			Scene viewQuestionsScene = new Scene(showViewElements, 720, 480);
			primaryStage.setScene(viewQuestionsScene);
			backButtonView.setOnAction(event -> primaryStage.setScene(mainMenu));

		});

		
		
		// From the MainMenu Scene // 
		editQuestion.setOnAction(e -> {
			Button backButtonEdit = new Button("Back");
			backButtonEdit.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
			Button edittingButton = new Button("Edit");
			edittingButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));

			int onlyQuestionsCounter = 0;
			ListView<String> editQuestionListView = new ListView<>();
			for (int x = 0; x < questionsArrayList.size() / 7; x++) {
				editQuestionListView.getItems().add(questionsArrayList.get(onlyQuestionsCounter));
				onlyQuestionsCounter = onlyQuestionsCounter + 7;
			}
			
			
			// Used to hold the buttons and for formatting
			HBox leftBoxBackButton = new HBox(backButtonEdit);
			leftBoxBackButton.setAlignment(Pos.CENTER_LEFT);
			HBox.setHgrow(leftBoxBackButton, Priority.ALWAYS);
			HBox rightBoxEditButton = new HBox(edittingButton);
			rightBoxEditButton.setAlignment(Pos.CENTER_RIGHT);
			HBox.setHgrow(rightBoxEditButton, Priority.ALWAYS);
			HBox editAndBackButtonBox = new HBox(leftBoxBackButton, rightBoxEditButton);
			VBox deleteQuestionPane = new VBox(editQuestionListView, editAndBackButtonBox);
			deleteQuestionPane.setPadding(new Insets(10));
			deleteQuestionPane.setAlignment(Pos.CENTER);
			deleteQuestionPane.setSpacing(10);
			
			Scene editScene = new Scene(deleteQuestionPane, 720, 480);
			primaryStage.setTitle("Question bank");
			primaryStage.setScene(editScene);
			primaryStage.show();
			// Back button (to main menu)
			backButtonEdit.setOnAction(event -> primaryStage.setScene(mainMenu));
			
			
			
			// From the MainMenu Scene //
			edittingButton.setOnAction(event -> {
				if (editQuestionListView.getSelectionModel().getSelectedIndex() >= 0) {
				
				// Shows the index of the selected items from the ViewList
				int index = editQuestionListView.getSelectionModel().getSelectedIndex();
				RadioButton editRadioButton1 = new RadioButton();
				RadioButton editRadioButton2 = new RadioButton();
				RadioButton editRadioButton3 = new RadioButton();
				RadioButton editRadioButton4 = new RadioButton();

				ToggleGroup groupEditRadio = new ToggleGroup();
				editRadioButton1.setToggleGroup(groupEditRadio);
				editRadioButton2.setToggleGroup(groupEditRadio);
				editRadioButton3.setToggleGroup(groupEditRadio);
				editRadioButton4.setToggleGroup(groupEditRadio);
				
				// Gets the question from the array to be Edited
				TextArea editQuestions = new TextArea();
				// each question is stored on intervals of 7 (0,7,14,21,28...)
				editQuestions.setText(questionsArrayList.get(index * 7)); 
				editQuestions.setPromptText("Enter your question");
				editQuestions.setFocusTraversable(false);
				editQuestions.setPrefWidth(400);
				editQuestions.setPrefHeight(50);
				
				// Gets the answers to be Edited
				TextField editAnswer1 = new TextField();
				// Answers are stored from 1-6 (1,2,3,4,5,6,8,9...)
				editAnswer1.setText(questionsArrayList.get(index * 7 + 1));
				editAnswer1.setPromptText("Enter your first answer");
				editAnswer1.setFocusTraversable(false);
				editAnswer1.setPrefWidth(400);
				editAnswer1.setPrefHeight(30);

				TextField editAnswer2 = new TextField();
				editAnswer2.setPromptText("Enter your second answer");
				editAnswer2.setFocusTraversable(false);
				editAnswer2.setPrefWidth(400);
				editAnswer2.setPrefHeight(30);
				editAnswer2.setText(questionsArrayList.get(index * 7 + 2));

				TextField editAnswer3 = new TextField();
				editAnswer3.setText(questionsArrayList.get(index * 7 + 3));
				editAnswer3.setPromptText("Enter your third answer");
				editAnswer3.setFocusTraversable(false);
				editAnswer3.setPrefWidth(400);
				editAnswer3.setPrefHeight(30);

				TextField editAnswer4 = new TextField();
				editAnswer4.setText(questionsArrayList.get(index * 7 + 4));
				editAnswer4.setPromptText("Enter your fourth answer");
				editAnswer4.setFocusTraversable(false);
				editAnswer4.setPrefWidth(400);
				editAnswer4.setPrefHeight(30);
				
				// Sets up the menu for Editing by showing the selected Question and its Answers
				Label editGuide = new Label("Hello");
				Label editQuestionLable = new Label("Question: ", editQuestions);
				editQuestionLable.setContentDisplay(ContentDisplay.RIGHT);
				Label editAnswer1Lable = new Label("Answer A: ", editAnswer1);
				editAnswer1Lable.setContentDisplay(ContentDisplay.RIGHT);
				Label editAnswer2Lable = new Label("Answer B: ", editAnswer2);
				editAnswer2Lable.setContentDisplay(ContentDisplay.RIGHT);
				Label editAnswer3Lable = new Label("Answer C: ", editAnswer3);
				editAnswer3Lable.setContentDisplay(ContentDisplay.RIGHT);
				Label editAnswer4Lable = new Label("Answer D: ", editAnswer4);
				editAnswer4Lable.setContentDisplay(ContentDisplay.RIGHT);
				Button saveEditedQuestionButton = new Button("Save");
				saveEditedQuestionButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
				Button backButtonEditScene = new Button("Back");
				backButtonEditScene.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
				
				// Used for adding nodes and formatting
				GridPane editQuestionPane = new GridPane();
				editQuestionPane.setAlignment(Pos.CENTER);
				editQuestionPane.setHgap(10);
				editQuestionPane.setVgap(15);
				editQuestionPane.add(editGuide, 0, 0);
				editQuestionPane.add(editQuestionLable, 0, 1);
				editQuestionPane.add(editAnswer1Lable, 0, 2);
				editQuestionPane.add(editRadioButton1, 1, 2);
				editQuestionPane.add(editAnswer2Lable, 0, 3);
				editQuestionPane.add(editRadioButton2, 1, 3);
				editQuestionPane.add(editAnswer3Lable, 0, 4);
				editQuestionPane.add(editRadioButton3, 1, 4);
				editQuestionPane.add(editAnswer4Lable, 0, 5);
				editQuestionPane.add(editRadioButton4, 1, 5);
				
				//Holds the buttons and formats them
				HBox leftBoxEdit = new HBox(backButtonEditScene);
				leftBoxEdit.setAlignment(Pos.CENTER_LEFT);
				HBox.setHgrow(leftBoxEdit, Priority.ALWAYS);

				HBox rightBoxEdit = new HBox(saveEditedQuestionButton);
				rightBoxEdit.setAlignment(Pos.CENTER_RIGHT);
				HBox.setHgrow(rightBoxEdit, Priority.ALWAYS);

				HBox bottomPaneEdit = new HBox(leftBoxEdit, rightBoxEdit);
				bottomPaneCreate.setSpacing(15);
				
				BorderPane allPanesEdit = new BorderPane();
				allPanesEdit.setPadding(new Insets(15));
				allPanesEdit.setCenter(editQuestionPane);
				allPanesEdit.setBottom(bottomPaneEdit);
				Scene EditQuestionScene = new Scene(allPanesEdit, 720, 480);
				primaryStage.setTitle("Question bank");
				primaryStage.setScene(EditQuestionScene);
				primaryStage.show();
				
				// Redirect back to the viewList 
				backButtonEditScene.setOnAction(events -> primaryStage.setScene(editScene));
				
				
				// From the Editing Menu
				saveEditedQuestionButton.setOnAction(saveEvent -> {
				// Forces the user to put a question and select an answer before they are allowed to Save the new questions
					if (!(editQuestions.getText().isEmpty())
							&& (editRadioButton1.isSelected() ^ editRadioButton2.isSelected()
									^ editRadioButton3.isSelected() ^ editRadioButton4.isSelected())) {
						// Deletes old questions
						for (int x = 0; x < 7; x++) {
							questionsArrayList.remove(index * 7);
						}
						// Saves the new edited questions
						questionsArrayList.add(editQuestions.getText());
						questionsArrayList.add(editAnswer1.getText());
						questionsArrayList.add(editAnswer2.getText());
						questionsArrayList.add(editAnswer3.getText());
						questionsArrayList.add(editAnswer4.getText());

						if (editRadioButton1.isSelected()) {
							questionsArrayList.add("The correct answer is: " + editAnswer1.getText());
						}
						if (editRadioButton2.isSelected()) {
							questionsArrayList.add("The correct answer is: " + editAnswer2.getText());
						}
						if (editRadioButton3.isSelected()) {
							questionsArrayList.add("The correct answer is: " + editAnswer3.getText());
						}
						if (editRadioButton4.isSelected()) {
							questionsArrayList.add("The correct answer is: " + editAnswer4.getText());
						}
						
						questionsArrayList.add("");
						editQuestions.clear();
						editAnswer1.clear();
						editAnswer2.clear();
						editAnswer3.clear();
						editAnswer4.clear();
						editRadioButton1.setSelected(false);
						editRadioButton2.setSelected(false);
						editRadioButton3.setSelected(false);
						editRadioButton4.setSelected(false);
						primaryStage.setScene(mainMenu);

					}

				});
				}
			});
		});

		
		
		// From the MainMenu Scene //
		deleteQuestion.setOnAction(e -> {
			// Adding the Back button and formatting it
			Button backButtonToMenu = new Button("Back");
			backButtonToMenu.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
			Button deleteQuestionButton = new Button("Delete");
			deleteQuestionButton.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 22));
			
			// This Variable is used to show only questions in the listView 
			int onlyQuestionsCounter = 0;
			ListView<String> deletQuestionListView = new ListView<>();
			
			// The loop is for adding all the questions to the viewList
			for (int x = 0; x < questionsArrayList.size() / 7; x++) {
				deletQuestionListView.getItems().add(questionsArrayList.get(onlyQuestionsCounter));
				onlyQuestionsCounter = onlyQuestionsCounter + 7;
			}
			
			// Used for formatting
			HBox leftBoxBackButton = new HBox(backButtonToMenu);
			leftBoxBackButton.setAlignment(Pos.CENTER_LEFT);
			HBox.setHgrow(leftBoxBackButton, Priority.ALWAYS);
		
			HBox rightBoxDeleteButton = new HBox(deleteQuestionButton);
			rightBoxDeleteButton.setAlignment(Pos.CENTER_RIGHT);
			HBox.setHgrow(rightBoxDeleteButton, Priority.ALWAYS);

			HBox DeleteAndBackButtonBox = new HBox(leftBoxBackButton, rightBoxDeleteButton);

			VBox deleteQuestionPane = new VBox(deletQuestionListView, DeleteAndBackButtonBox);
			deleteQuestionPane.setPadding(new Insets(10));
			deleteQuestionPane.setAlignment(Pos.CENTER);
			deleteQuestionPane.setSpacing(10);
			
			Scene scene = new Scene(deleteQuestionPane, 720, 480);
			primaryStage.setTitle("Question bank");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Redirect back the main menu
			backButtonToMenu.setOnAction(event -> primaryStage.setScene(mainMenu));
			
			// Once pressed; deletes the question from the arrayList and updates the ViewList
			deleteQuestionButton.setOnAction(event -> {
				if (deletQuestionListView.getSelectionModel().getSelectedIndex() >= 0) {
				int index = deletQuestionListView.getSelectionModel().getSelectedIndex();
				int removeFromArrayIndex = index * 7;
				for (int x = 0; x < 7; x++) {
					questionsArrayList.remove(removeFromArrayIndex);
				}
				deletQuestionListView.getItems().remove(index);
				}
			});

		});
		


		// From the MainMenu Scene //
		exitButton.setOnAction(e -> {
			saveToFile(questionsArrayList);
			Platform.exit();
		});

		// From the MainMenu Scene //
		createNewQuestions.setOnAction(e -> primaryStage.setScene(createQuestionScene));

		// From the StartPage Scene //
		startProgramButton.setOnAction(e -> primaryStage.setScene(mainMenu));
		
		// From the StartPage Scene //
		backToStartMenu.setOnAction(e -> primaryStage.setScene(startPage));
	}
	//-----------------------------End-----------------------------//	

	
	
	//-----------------------------Saving & Writing-----------------------------//

	public static void saveToFile(ArrayList<String> list) {
		try (DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream("QuestionBank.dat"));) {
			// Saves the ArrayList to the binary file
			for (int x = 0; x < list.size(); x++) {
				dataOutput.writeUTF(list.get(x));
			}

		} catch (Exception exception) {
			System.out.print("");
		}

	}

	public static ArrayList<String> readToArray(ArrayList<String> list) {
		try (DataInputStream dataReader = new DataInputStream(new FileInputStream("QuestionBank.dat"));) {
			String element;
			// Reads the data from the binary file and adding it into an array
			while ((element = dataReader.readUTF()) != null) {
				list.add(element);
			}

		} catch (EOFException ex) {
			System.out.println("");
		} catch (IOException ex) {
			System.out.println("");
		}
		return list;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

