import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockManagement extends Application{
    static Scanner scanner = new Scanner(System.in);
    //REGEX to define string pattern for validation
	final static String ALPHA_REGEX = "[a-zA-Z\\s]+";
	final static String ALPHA_NUM_REGEX = "^[a-zA-Z0-9\\s]+";
	//ERROR message
	final static String onlyAlphaNum = "Please enter only alphabetical characters and numbers";
	final static String onlyAlpha = "Please enter only alphabetical characters";
	final static String onlyNum = "Please enter a valid number";
    private static ArrayList<Product> productList = new ArrayList<>();
    
	UserInfo user = new UserInfo();
    
    private Stage stage;
    //Various scene to be used in switchScene
    private Scene login;
    private Scene menu;
    private Scene exit;
    private Scene addProducts;
    private Scene refrigerator;
    private Scene TV;
    private Scene printer;
    private Scene speaker;
    private Scene viewProducts;
    private Scene displayProducts;
	
	//Create a border pane as stage
    public void start(Stage primaryStage) {
    	stage = primaryStage;
    	stage.setTitle("OOPP Stock Management System");
    	login = loginScene();
    	menu = menuScene();
    	exit = exitScene();
    	addProducts = addProdScene();
    	refrigerator = addRefrigerator();
    	printer = addPrinter();
    	TV = addTV();
    	speaker = addSpeaker();
    	viewProducts = viewProdScene();
    	displayProducts = displayProdScene(0);
    	stage.setResizable(false);
    	stage.setScene(login);
    	stage.show(); 			
    }
    
    //Switch scene within the same stage
    public void switchScene(Scene scene) {
    	stage.setScene(scene);
    }
    
    //System banner
    public HBox getHBox() {
    	HBox hBox = new HBox(30);
    	hBox.setAlignment(Pos.CENTER);
    	hBox.setPadding(new Insets(15));
    	hBox.setStyle("-fx-background-color: YELLOW");
    	Text welcomeMsg = new Text("Welcome to the OOPP Stock Management System");
    	welcomeMsg.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 25));
    	welcomeMsg.setFill(Color.BLUE);
    	hBox.getChildren().add(welcomeMsg);
    	return hBox;
    	}
    
    //System starting scene to ask for username to login
    public Scene loginScene() {
    	BorderPane pane = new BorderPane();
    	pane.setTop(getHBox());
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	VBox vBox = new VBox();
    	vBox.setAlignment(Pos.TOP_CENTER);
    	vBox.setPadding(new Insets(20));
    	pane.setCenter(vBox);
    	Text text1 = new Text(displayTime());
    	text1.setFont(Font.font("Arial", 20));
    	Text text2 = new Text("Group Members: Lee Chorng Huah, Ng Tuck Seng, Ng Wei Hong, Ng Wei Yu");
    	text2.setFont(Font.font("Arial", 20));
    	Text text3 = new Text("USERNAME:");
    	Text[] texts = {text1, text2};
    	for (Text text: texts) {
    		VBox.setMargin(text, new Insets(0, 0, 25, 0));
    		vBox.getChildren().add(text);
    		}
    	VBox.setMargin(text3, new Insets(100, 0, 25, 0));
		vBox.getChildren().add(text3);
		TextField usernameTF = new TextField();
		usernameTF.setMaxWidth(300);
		usernameTF.setPromptText("Username");
		usernameTF.setFocusTraversable(false);
		Button loginBTN = new Button("LOGIN");
		loginBTN.setPadding(new Insets(10));
		Text loginFail = new Text();
		loginFail.setFill(Color.RED);
		vBox.getChildren().addAll(usernameTF, loginBTN, loginFail);
		VBox.setMargin(loginBTN, new Insets(10));
		loginBTN.setOnAction(e -> {
			String username;
			username = usernameTF.getText();
			if (user.setName(username)) {
				switchScene(menu); //go to main menu if name is valid
			}
			else {
				loginFail.setText("Error: Invalid name format. Please enter only alphabetic characters and spaces.");
			}

		});
		
    	
    	return login = new Scene(pane, 800, 600);
    }
    
    //Main menu
    public Scene menuScene() {
    	BorderPane pane = new BorderPane();
    	pane.setTop(getHBox());
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	VBox vBox = new VBox();
    	vBox.setAlignment(Pos.TOP_CENTER);
    	vBox.setPadding(new Insets(15));
    	pane.setCenter(vBox);
    	Text text1 = new Text("MAIN MENU");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 50));
    	vBox.getChildren().add(text1);
		Button button1 = new Button("ADD PRODUCTS");
		Button button2 = new Button("VIEW PRODUCTS");
		Button button3 = new Button("ADD STOCK");
		Button button4 = new Button("DEDUCT STOCK");
		Button button5 = new Button("DISCONTINUE PRODUCT");
		Button button0 = new Button("EXIT");
    	Button[] buttons = {button1, button2, button3, button4, button5, button0};
    	for (Button button : buttons) {
    		VBox.setMargin(button, new Insets(30, 0, 0, 0));
    		button.setPadding(new Insets(10));
    		button.setMinWidth(200);
    		vBox.getChildren().add(button);
    		}
    	button0.setOnAction(e -> switchScene(exitScene())); //Exit action
    	button1.setOnAction(e -> switchScene(addProducts)); //Add product option
    	button2.setOnAction(e -> { //View product option
    	viewProducts = viewProdScene();
    	switchScene(viewProducts);});
    	button3.setOnAction(e -> { //Add stock option
    		if(productList.isEmpty()) { //No product in list
    	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
    	        a.setContentText("Product list is empty!");
    	        a.show();
    		}
    		else {
    			displayProducts = displayProdScene(3);
    			switchScene(displayProducts);
    		}
    	});
    	button4.setOnAction(e -> { //Deduct stock
    		if(productList.isEmpty()) {
    	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
    	        a.setContentText("Product list is empty!");
    	        a.show();
    		}
    		else {
    			displayProducts = displayProdScene(4);
    			switchScene(displayProducts);
    		}
    	});
    	button5.setOnAction(e -> { //Discontinue product option
    		if(productList.isEmpty()) {
    	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
    	        a.setContentText("Product list is empty!");
    	        a.show();
    		}
    		else {
    			displayProducts = displayProdScene(5);
    			switchScene(displayProducts);
    		}
    	});

    	return menu = new Scene(pane, 800, 600);
    	
    }
    //Exit scene to display username and userID
    public Scene exitScene() {
    	BorderPane pane = new BorderPane();
    	HBox hBox = new HBox(30);
    	hBox.setAlignment(Pos.CENTER);
    	hBox.setPadding(new Insets(15));
    	hBox.setStyle("-fx-background-color: YELLOW");
    	Text exitMsg = new Text("EXIT");
    	exitMsg.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 25));
    	exitMsg.setFill(Color.BLUE);
    	hBox.getChildren().add(exitMsg);
    	pane.setTop(hBox);
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	VBox vBox = new VBox();
    	vBox.setAlignment(Pos.CENTER);
    	vBox.setPadding(new Insets(5));
    	pane.setCenter(vBox);
    	Text text1 = new Text("User ID : " + user.getUserID());
    	Text text2 = new Text("User Name : " + user.getName());
    	Text text3 = new Text("EXIT SUCCESSFULLY! SEE YOU AGAIN!");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 30));   
    	text2.setFill(Color.BLUE);
    	text2.setFont(Font.font("Arial", 30));
    	text3.setFill(Color.DARKBLUE);
    	text3.setFont(Font.font("Arial", 40));
    	Text[] texts = {text1, text2, text3};
    	for (Text text : texts) {
    		VBox.setMargin(text, new Insets(60, 0, 0, 0));
    		vBox.getChildren().add(text);
    		}
    	return exit = new Scene(pane, 800, 600);

    }
    
    //Check String Input
    public static boolean getInput(String input, String regex) {
        if (!input.matches(regex)) {
           return false;
        }
        return true;
    }
    
    //Check Double Input
    public static boolean getDoubleInput(String input) {
    	double value;
        try {
        	value = Double.parseDouble(input);
    		if (value < 0) {
    			return false;
    		}
        } catch (NumberFormatException e) {
        	return false;
        }
        return true;
    }

    //Check integer input
    public static boolean getIntInput(String input, String prompt) {
    	int value;
    	try {
    		value = Integer.parseInt(input);
    		if (value < 0) {
    			return false;
    		}

    	} catch (NumberFormatException e) {
    		return false;
    	}
    	if (prompt.equals("Item Number")) { //To check new item number
  			if (value == 0) {
    			return false;
    		}
    		for (Product product : productList) {
    			if (value == product.getItemNumber()) {
    				return false;
    			}
    		}
    	}
	    //To check duplication ID        
    	if (prompt.equals("ID to Update")) {
    		for (Product product : productList) {
    			if (value == product.getItemNumber()) {
    				return true;
    			}           
    		}
    		return false;
    	}
		return true;
    }
    
    //Generate add product scene
    public Scene addProdScene() {
    	BorderPane pane = new BorderPane();
    	pane.setTop(getHBox());
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	VBox vBox = new VBox();
    	vBox.setAlignment(Pos.TOP_CENTER);
    	vBox.setPadding(new Insets(15));
    	pane.setCenter(vBox);
		Button button1 = new Button("REFRIGERATOR");
		Button button2 = new Button("TV");
		Button button3 = new Button("PRINTER");
		Button button4 = new Button("SPEAKER");
		Button button0 = new Button("EXIT");
    	Button[] buttons = {button1, button2, button3, button4, button0};
    	for (Button button : buttons) {
    		VBox.setMargin(button, new Insets(50, 0, 0, 0));
    		button.setPadding(new Insets(10));
    		button.setMinWidth(200);
    		vBox.getChildren().add(button);
    		}
    	button1.setOnAction(e -> switchScene(refrigerator));
    	button2.setOnAction(e -> switchScene(TV));
    	button3.setOnAction(e -> switchScene(printer));
    	button4.setOnAction(e -> switchScene(speaker));
    	button0.setOnAction(e -> switchScene(menu));
  
    	return addProducts = new Scene(pane, 800, 600);
    }
    
    //Generate add refrigerator scene
    public Scene addRefrigerator() {
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	FlowPane fpane = new FlowPane();
    	fpane.setAlignment(Pos.TOP_CENTER);
    	fpane.setPadding(new Insets(15));
    	pane.setCenter(fpane);
    	fpane.setPadding(new Insets(10, 15, 10, 15));
    	fpane.setHgap(5);
    	fpane.setVgap(5);
    	Text text1 = new Text("Enter refrigerator info");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 20));
    	
    	Label label1 = new Label("Name:");
    	Label label2 = new Label("Door Design:");
    	Label label3 = new Label("Color:");
    	Label label4 = new Label("Capacity:");
    	Label label5 = new Label("Quantity Available:");
    	Label label6 = new Label("Price:");
    	Label label7 = new Label("Item Number:");
    	TextField tf1 = new TextField();
    	TextField tf2 = new TextField();
    	TextField tf3 = new TextField();
     	TextField tf4 = new TextField();
    	TextField tf5 = new TextField();
    	TextField tf6 = new TextField();
    	TextField tf7 = new TextField();
    	fpane.getChildren().addAll(text1, label1, tf1, label2, tf2, label3, tf3, label4, tf4,
    			label5, tf5, label6, tf6, label7, tf7);
		Button button1 = new Button("ENTER");
		button1.setPadding(new Insets(10));
		button1.setMinWidth(100);
		fpane.getChildren().add(button1);
		button1.setOnAction(e -> {
			String name = tf1.getText();
			String design = tf2.getText();
			String color = tf3.getText();
			String capacity = tf4.getText();
			String quantity = tf5.getText();
			String price = tf6.getText();
			String itemNo = tf7.getText();
	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
	        //All inputs' validation checking
	        if (!getInput(name, ALPHA_NUM_REGEX)) { //Name
                // set content text
                a.setContentText(onlyAlphaNum + " for name.");
                // show the dialog
                a.show();
	        }
	        else if (!getInput(design, ALPHA_REGEX)) { //Door Design
                a.setContentText(onlyAlpha + " for door design.");
                a.show();
	        }
	        else if (!getInput(color, ALPHA_REGEX)) { //Color
                a.setContentText(onlyAlpha + " for color.");
                a.show();
	        }
	        else if (!getIntInput(capacity, "")) { //Capacity
                a.setContentText(onlyNum + " for capacity >= 0.");
                a.show();	        
            }
	        else if (!getIntInput(quantity, "")) { //Quantity Available
                a.setContentText(onlyNum + " for quantity >= 0.");
                a.show();
            }
	        else if (!getDoubleInput(price)) { //Price
                a.setContentText(onlyNum + " for price >= 0.");
                a.show();
            }        
	        else if (!getIntInput(itemNo, "Item Number")) { //Item Number
                a.setContentText("Invalid Item Number. Please enter a non-duplicate & non-zero positive item number.");
                a.show();	
	        }
			else {
		        Refrigerator refrigerator = new Refrigerator(Integer.parseInt(itemNo), name, Integer.parseInt(quantity), 
		        		Double.parseDouble(price), tf2.getText(), tf3.getText(), Integer.parseInt(capacity));
		        productList.add(refrigerator);
		        a.setAlertType(AlertType.INFORMATION);
		        a.setTitle("SUCCESSFUL");
                a.setContentText("A refrigerator is added successfully!");
                a.show();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf6.clear();
                tf7.clear();
				switchScene(addProducts);}	        
		});
		
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
		button0.setMinWidth(100);
		fpane.getChildren().add(button0);
    	button0.setOnAction(e -> {
            tf1.clear();
            tf2.clear();
            tf3.clear();
            tf4.clear();
            tf5.clear();
            tf6.clear();
            tf7.clear();
            switchScene(addProducts);});
  
    	return refrigerator = new Scene(pane, 250, 600);
    }
    
    //Generate add TV scene
    public Scene addTV() {
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	FlowPane fpane = new FlowPane();
    	fpane.setAlignment(Pos.TOP_CENTER);
    	fpane.setPadding(new Insets(15));
    	pane.setCenter(fpane);
    	fpane.setPadding(new Insets(10, 15, 10, 15));
    	fpane.setHgap(5);
    	fpane.setVgap(5);
    	Text text1 = new Text("      Enter TV info      ");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 20));
    	
    	Label label1 = new Label("Name:");
    	Label label2 = new Label("Screen Type:");
    	Label label3 = new Label("Resolution:");
    	Label label4 = new Label("Display Size (inches):");
    	Label label5 = new Label("Quantity Available:");
    	Label label6 = new Label("Price:");
    	Label label7 = new Label("Item Number:");
    	TextField tf1 = new TextField();
    	TextField tf2 = new TextField();
    	TextField tf3 = new TextField();
     	TextField tf4 = new TextField();
    	TextField tf5 = new TextField();
    	TextField tf6 = new TextField();
    	TextField tf7 = new TextField();
    	fpane.getChildren().addAll(text1, label1, tf1, label2, tf2, label3, tf3, label4, tf4,
    			label5, tf5, label6, tf6, label7, tf7);
		Button button1 = new Button("ENTER");
		button1.setPadding(new Insets(10));
		button1.setMinWidth(100);
		fpane.getChildren().add(button1);
		button1.setOnAction(e -> {
			String name = tf1.getText();
			String screen = tf2.getText();
			String resolution = tf3.getText();
			String size = tf4.getText();
			String quantity = tf5.getText();
			String price = tf6.getText();
			String itemNo = tf7.getText();
	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
	        if (!getInput(name, ALPHA_NUM_REGEX)) { //Name
                // set content text
                a.setContentText(onlyAlphaNum + " for name.");
                // show the dialog
                a.show();
	        }
	        else if (!getInput(screen, ALPHA_REGEX)) { //Screen Type
                a.setContentText(onlyAlpha + " for screen type.");
                a.show();
	        }
	        else if (!getInput(resolution, ALPHA_NUM_REGEX)) { //Resolution
                a.setContentText(onlyAlphaNum + " for resolution.");
                a.show();
	        }
	        else if (!getDoubleInput(size)) { //Display Size
                a.setContentText(onlyNum + " for display size >= 0.");
                a.show();	        
            }
	        else if (!getIntInput(quantity, "")) { //Quantity Available
                a.setContentText(onlyNum + " for quantity >= 0.");
                a.show();
            }
	        else if (!getDoubleInput(price)) { //Price
                a.setContentText(onlyNum + " for price >= 0.");
                a.show();
            }        
	        else if (!getIntInput(itemNo, "Item Number")) { //Item Number
                a.setContentText("Invalid Item Number. Please enter a non-duplicate & non-zero positive item number.");
                a.show();	
	        }
			else {
		        TV tv = new TV(Integer.parseInt(itemNo), name, Integer.parseInt(quantity), 
		        		Double.parseDouble(price), tf2.getText(), tf3.getText(), Double.parseDouble(size));
		        productList.add(tv);
		        a.setAlertType(AlertType.INFORMATION);
		        a.setTitle("SUCCESSFUL");
                a.setContentText("A TV is added successfully!");
                a.show();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf6.clear();
                tf7.clear();
				switchScene(addProducts);}	        
		});
		
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
		button0.setMinWidth(100);
		fpane.getChildren().add(button0);
    	button0.setOnAction(e -> {
            tf1.clear();
            tf2.clear();
            tf3.clear();
            tf4.clear();
            tf5.clear();
            tf6.clear();
            tf7.clear();
            switchScene(addProducts);});
  
    	return TV = new Scene(pane, 250, 600);
    }
    
    //Generate add printer scene
    public Scene addPrinter() {
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	FlowPane fpane = new FlowPane();
    	fpane.setAlignment(Pos.TOP_CENTER);
    	fpane.setPadding(new Insets(15));
    	pane.setCenter(fpane);
    	fpane.setPadding(new Insets(10, 15, 10, 15));
    	fpane.setHgap(5);
    	fpane.setVgap(5);
    	Text text1 = new Text("   Enter printer info   ");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 20));
    	
    	Label label1 = new Label("Name:");
    	Label label2 = new Label("Color:");
    	Label label3 = new Label("Model:");
    	Label label4 = new Label("Technology:");
    	Label label5 = new Label("Quantity Available:");
    	Label label6 = new Label("Price:");
    	Label label7 = new Label("Item Number:");
    	TextField tf1 = new TextField();
    	TextField tf2 = new TextField();
    	TextField tf3 = new TextField();
     	TextField tf4 = new TextField();
    	TextField tf5 = new TextField();
    	TextField tf6 = new TextField();
    	TextField tf7 = new TextField();
    	fpane.getChildren().addAll(text1, label1, tf1, label2, tf2, label3, tf3, label4, tf4,
    			label5, tf5, label6, tf6, label7, tf7);
		Button button1 = new Button("ENTER");
		button1.setPadding(new Insets(10));
		button1.setMinWidth(100);
		fpane.getChildren().add(button1);
		button1.setOnAction(e -> {
			String name = tf1.getText();
			String color = tf2.getText();
			String model = tf3.getText();
			String technology = tf4.getText();
			String quantity = tf5.getText();
			String price = tf6.getText();
			String itemNo = tf7.getText();
	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
	        if (!getInput(name, ALPHA_NUM_REGEX)) { //Name
                // set content text
                a.setContentText(onlyAlphaNum + " for name.");
                // show the dialog
                a.show();
	        }
	        else if (!getInput(color, ALPHA_REGEX)) { //Color
                a.setContentText(onlyAlpha + " for color.");
                a.show();
	        }
	        else if (!getInput(model, ALPHA_NUM_REGEX)) { //Model
                a.setContentText(onlyAlphaNum + " for model.");
                a.show();
	        }
	        else if (!getInput(technology, ALPHA_REGEX)) { //Technology
                a.setContentText(onlyAlpha + " for technology.");
                a.show();	        
            }
	        else if (!getIntInput(quantity, "")) { //Quantity Available
                a.setContentText(onlyNum + " for quantity >= 0.");
                a.show();
            }
	        else if (!getDoubleInput(price)) { //Price
                a.setContentText(onlyNum + " for price >= 0.");
                a.show();
            }        
	        else if (!getIntInput(itemNo, "Item Number")) { //Item Number
                a.setContentText("Invalid Item Number. Please enter a non-duplicate & non-zero positive item number.");
                a.show();	
	        }
			else {
		        Printer printer = new Printer(Integer.parseInt(itemNo), name, Double.parseDouble(price), 
		        		Integer.parseInt(quantity), tf2.getText(), tf3.getText(), tf4.getText());
		        productList.add(printer);
		        a.setAlertType(AlertType.INFORMATION);
		        a.setTitle("SUCCESSFUL");
                a.setContentText("A printer is added successfully!");
                a.show();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf6.clear();
                tf7.clear();
				switchScene(addProducts);}	        
		});
		
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
		button0.setMinWidth(100);
		fpane.getChildren().add(button0);
    	button0.setOnAction(e -> {
            tf1.clear();
            tf2.clear();
            tf3.clear();
            tf4.clear();
            tf5.clear();
            tf6.clear();
            tf7.clear();
            switchScene(addProducts);});
  
    	return printer = new Scene(pane, 250, 600);
    }
    
    //Generate add speaker scene
    public Scene addSpeaker() {
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	FlowPane fpane = new FlowPane();
    	fpane.setAlignment(Pos.TOP_CENTER);
    	fpane.setPadding(new Insets(15));
    	pane.setCenter(fpane);
    	fpane.setPadding(new Insets(10, 15, 10, 15));
    	fpane.setHgap(5);
    	fpane.setVgap(5);
    	Text text1 = new Text("   Enter speaker info   ");
    	text1.setFill(Color.BLUE);
    	text1.setFont(Font.font("Arial", 20));
    	
    	Label label1 = new Label("Name:");
    	Label label2 = new Label("Color:");
    	Label label3 = new Label("Brand:");
    	Label label4 = new Label("Model:");
    	Label label5 = new Label("Quantity Available:");
    	Label label6 = new Label("Price:");
    	Label label7 = new Label("Item Number:");
    	TextField tf1 = new TextField();
    	TextField tf2 = new TextField();
    	TextField tf3 = new TextField();
     	TextField tf4 = new TextField();
    	TextField tf5 = new TextField();
    	TextField tf6 = new TextField();
    	TextField tf7 = new TextField();
    	fpane.getChildren().addAll(text1, label1, tf1, label2, tf2, label3, tf3, label4, tf4,
    			label5, tf5, label6, tf6, label7, tf7);
		Button button1 = new Button("ENTER");
		button1.setPadding(new Insets(10));
		button1.setMinWidth(100);
		fpane.getChildren().add(button1);
		button1.setOnAction(e -> {
			String name = tf1.getText();
			String color = tf2.getText();
			String brand = tf3.getText();
			String model = tf4.getText();
			String quantity = tf5.getText();
			String price = tf6.getText();
			String itemNo = tf7.getText();
	        Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
	        if (!getInput(name, ALPHA_NUM_REGEX)) { //Name
                // set content text
                a.setContentText(onlyAlphaNum + " for name.");
                // show the dialog
                a.show();
	        }
	        else if (!getInput(color, ALPHA_REGEX)) { //Color
                a.setContentText(onlyAlpha + " for color.");
                a.show();
	        }
	        else if (!getInput(brand, ALPHA_REGEX)) { //Brand
                a.setContentText(onlyAlphaNum + " for brand.");
                a.show();
	        }
	        else if (!getInput(model, ALPHA_NUM_REGEX)) { //Model
                a.setContentText(onlyAlphaNum + " for model.");
                a.show();	        
            }
	        else if (!getIntInput(quantity, "")) { //Quantity Available
                a.setContentText(onlyNum + " for quantity >= 0.");
                a.show();
            }
	        else if (!getDoubleInput(price)) { //Price
                a.setContentText(onlyNum + " for price >= 0.");
                a.show();
            }        
	        else if (!getIntInput(itemNo, "Item Number")) { //Item Number
                a.setContentText("Invalid Item Number. Please enter a non-duplicate & non-zero positive item number.");
                a.show();	
	        }
			else {
		        Speaker speaker = new Speaker(Integer.parseInt(itemNo), name, Double.parseDouble(price), 
		        		Integer.parseInt(quantity), tf2.getText(), tf3.getText(), tf4.getText());
		        productList.add(speaker);
		        a.setAlertType(AlertType.INFORMATION);
		        a.setTitle("SUCCESSFUL");
                a.setContentText("A speaker is added successfully!");
                a.show();
                tf1.clear();
                tf2.clear();
                tf3.clear();
                tf4.clear();
                tf5.clear();
                tf6.clear();
                tf7.clear();
				switchScene(addProducts);}	        
		});
		
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
		button0.setMinWidth(100);
		fpane.getChildren().add(button0);
    	button0.setOnAction(e -> {
            tf1.clear();
            tf2.clear();
            tf3.clear();
            tf4.clear();
            tf5.clear();
            tf6.clear();
            tf7.clear();
            switchScene(addProducts);});
  
    	return speaker = new Scene(pane, 250, 600);
    }
    
    //Generate product's some information before updating
    public void updateProductInfo(TextArea prodInfo) {
        if (productList.isEmpty()) {
            prodInfo.setText("Error: The list is empty.\n");
        } else {
            prodInfo.clear();
            for (Product product : productList) {
                prodInfo.appendText("\nProduct Type\t\t: " + getProductType(product));
                prodInfo.appendText(product.toString());
            }
        }
    }
	
    //Generate view product scene
    public Scene viewProdScene(){
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	pane.setTop(getHBox());
    	TextArea prodInfo = new TextArea();
    	prodInfo.setPrefHeight(600);
    	prodInfo.setPrefWidth(781);
    	prodInfo.setStyle("-fx-font-size: 1.5em;");
    	pane.setCenter(prodInfo);
    	prodInfo.setEditable(false);
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
    	button0.setMinWidth(200);
    	button0.setOnAction(e -> {
    	switchScene(menu);
    	prodInfo.clear();;
    	});
    	pane.setBottom(button0);

    	
    	if(productList.isEmpty()){	// check whether the product list is empty
			prodInfo.setText("Error: The list is empty.\n");
    	}
    	else {
    		for (Product product : productList) {		//print all product details in the list
	            prodInfo.appendText("\nProduct Type\t\t: " + getProductType(product) + "\n"); // Display subtype name
	            prodInfo.appendText(product.toString());
			}
    	}
    	
    	return viewProducts = new Scene(pane, 800, 600);

	}
	
	// Function to get the subtype name of a product
    public static String getProductType(Product product) {
	    if (product instanceof Refrigerator) {
	        return "Refrigerator";
	    } else if (product instanceof TV) {
	        return "TV";
	    } else if (product instanceof Printer) {
	        return "Printer";
	    } else if (product instanceof Speaker) {
	        return "Speaker";
	    }
	    return null;
	}
	
	
    //Generate display product scene
    public Scene displayProdScene(int opt) {
    	
    	BorderPane pane = new BorderPane();
    	pane.setStyle("-fx-background-color: LIGHTYELLOW");
    	TextArea prodInfo = new TextArea();
    	prodInfo.setStyle("-fx-font-size: 1.5em;");
    	prodInfo.setEditable(false);
    	pane.setCenter(prodInfo);
    	prodInfo.setEditable(false);
    	VBox vBox = new VBox(10);
    	vBox.setAlignment(Pos.TOP_CENTER);
    	vBox.setPadding(new Insets(15));
		TextField updateID = new TextField();
		updateID.setMaxWidth(150);
		updateID.setPromptText("ID to update");
		updateID.setFocusTraversable(false);
		Button button1 = new Button("ENTER");
		button1.setPadding(new Insets(10));
    	button1.setMinWidth(200);
		Button button0 = new Button("EXIT");
		button0.setPadding(new Insets(10));
        TextInputDialog td = new TextInputDialog(); 
    	button1.setOnAction(e -> {
    		Alert a = new Alert(AlertType.ERROR); //Set alert with type ERROR
    		String ID = updateID.getText();
    		if (!getIntInput(ID, "ID to Update")) {
    			a.setContentText(onlyNum + " for ID to update.");
    			a.show();
    	    }
    		else {
    	        int inputID = Integer.parseInt(ID);
    			int productArr = 0;
    	        //Obtain the product's array number in the list
    	        for (int i = 0; i < productList.size(); i++) {
    	        	if (productList.get(i).getItemNumber() == inputID) {
    	        		productArr = i;
    	        	}
    	        }
    			Optional<String> result;
    			if(opt == 5) { //Discontinue product option
    				td.setHeaderText("1 = Active   2 = Discontinued\nEnter 1 or 2 to set product status.");
    				result = td.showAndWait();
    				if(result.isPresent()) {
    					String input = result.orElse("");
    					if(!getIntInput(input, "")) {
    						td.getEditor().clear();
    						a.setContentText(onlyNum);
    						a.show();
    					}
    					else {
    						if(Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2) {
        						td.getEditor().clear();
        						a.setContentText("Enter 1 or 2 only!");
        						a.show();
    						}
    						else {
    							if (Integer.parseInt(input) == 1) {
    	    		            	productList.get(productArr).setStatus(true);
    	    				        a.setAlertType(AlertType.INFORMATION);
    	    				        a.setTitle("SUCCESSFUL");
    	    		                a.setContentText("Product is set to ACTIVE status!");
    	    		                a.show();
    	        					td.getEditor().clear();
    	        					prodInfo.clear();
    	        					updateID.clear();
    	        					switchScene(menu);
    							}
    							else if (Integer.parseInt(input) == 2) {
    	    		            	productList.get(productArr).setStatus(false);
    	    				        a.setAlertType(AlertType.INFORMATION);
    	    				        a.setTitle("SUCCESSFUL");
    	    		                a.setContentText("Product is set to DISCONTINUED status!");
    	    		                a.show();
    	        					td.getEditor().clear();
    	        					prodInfo.clear();
    	        					updateID.clear();
    	        					switchScene(menu);
    							}
    						}
    					}	
    				}
    				else {
    					td.getEditor().clear();
    				}
    			}
    			//Check the product status
    			if (productList.get(productArr).getStatus() == false) {
    	    		a.setContentText("The product is DISCONTINUED!");
    	    		a.show();
    				prodInfo.clear();
    				updateID.clear();
    				switchScene(menu);
    			}
    			else if (opt == 3) { //Add stock option
    				td.setHeaderText("Enter amount to add:");
    				result = td.showAndWait();
    				if(result.isPresent()) {
    					String input = result.orElse("");
    					if(!getIntInput(input, "")) {
    						td.getEditor().clear();
    						a.setContentText(onlyNum);
    						a.show();
    					}
    					else {
    		            	productList.get(productArr).addStock(Integer.parseInt(input));
    				        a.setAlertType(AlertType.INFORMATION);
    				        a.setTitle("SUCCESSFUL");
    		                a.setContentText(Integer.parseInt(input) + " stock(s) added successfully!");
    		                a.show();
        					td.getEditor().clear();
        					prodInfo.clear();
        					updateID.clear();
        					switchScene(menu);
    					}	
    				}
    				else {
    					td.getEditor().clear();
    				}
    			}
    			else if (opt == 4) { //Deduct stock option
    				td.setHeaderText("Enter amount to deduct:");
    				result = td.showAndWait();
    				if(result.isPresent()) {
    					String input = result.orElse("");
    					if(!getIntInput(input, "")) {
    						td.getEditor().clear();
    						a.setContentText(onlyNum);
    						a.show();
    					}
    					else {
    						if(productList.get(productArr).getQuantity() < Integer.parseInt(input)) {
    	    	                a.setContentText("Quantity to deduct exceeds the available stock.");
    	    	                a.show();
    	    					td.getEditor().clear();
    						}
    						else {
    							productList.get(productArr).deductStock(Integer.parseInt(input));
    							a.setAlertType(AlertType.INFORMATION);
    							a.setTitle("SUCCESSFUL");
    							a.setContentText(Integer.parseInt(input) + " stock(s) deducted successfully!");
    							a.show();
    		                	td.getEditor().clear();
    		                	prodInfo.clear();
    		                	updateID.clear();
    		                	switchScene(menu);
    						}
    					}	
    				}
    				else {
    					td.getEditor().clear();
    				}
    			}
    		}
    	});
    	
    	button0.setMinWidth(200);
    	button0.setOnAction(e -> {
    		switchScene(menu);
    		prodInfo.clear();;
    	});
    	vBox.getChildren().addAll(updateID, button1, button0);
    	pane.setBottom(vBox);
		for (Product product : productList) {		//print product id, name, quantity
			prodInfo.appendText("\nID" + product.getItemNumber() + ", " + product.getProductName() + ", " + product.getQuantity() + ", " + (product.getStatus() ? "Active" : "Discontinued"));
		}
    	return displayProducts = new Scene(pane, 600, 600);
	}

    //Display current date and time
    public static String displayTime() {        
        // Display current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return "Current Date and Time: " + formattedDateTime;
        
    }
    
    // Main method
    public static void main(String[] args) {
    	launch(args);
    }
}
