import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class StockManagement {
    static Scanner scanner = new Scanner(System.in);
    //REGEX to define string pattern for validation
	final static String ALPHA_REGEX = "[a-zA-Z\\s]+";
	final static String ALPHA_NUM_REGEX = "^[a-zA-Z0-9\\s]+";
	//ERROR message
	final static String onlyAlphaNum = "Please enter only alphabetical characters and numbers.";
	final static String onlyAlpha = "Please enter only alphabetical characters.";
	final static String onlyNumber = "Please enter a valid number.";
    private static ArrayList<Product> productList = new ArrayList<>();
    
    //Check String Input
    public static String getInput(String prompt, String regex, String errorMessage) {
        String input;
        boolean validInput;
        do {
            System.out.print(prompt + " > ");
            input = scanner.nextLine().trim();
            validInput = input.matches(regex);
            if (!validInput) {
                System.out.println("Error: " + errorMessage);
            }
        } while (!validInput);
        return input;
    }

    //Check Double Input
    public static double getDoubleInput(String prompt, String errorMessage) {
        double input = 0;
        boolean validInput;
        do {
            System.out.print(prompt + " > ");
            try {
                input = Double.parseDouble(scanner.nextLine().trim());
                validInput = true;
                if (input < 0) {
                    validInput = false;
                }
            } catch (NumberFormatException e) {
                validInput = false;
            }
            if (!validInput) {
                System.out.println("Error: " + errorMessage);
            }
        } while (!validInput);
        return input;
    }

    //Check integer input
    public static int getIntInput(String prompt, String errorMessage) {
        int input = 0;
        boolean validInput;
        do {
            System.out.print(prompt + " > ");
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                validInput = true;
                if (input < 0) {
                    validInput = false;
                }
            } catch (NumberFormatException e) {
                validInput = false;
            }
            if (!validInput) {
                System.out.println("Error: " + errorMessage);
                continue;
            }
            if (prompt.equals("Item Number")) { //To check new item number
        		if (input == 0) {
        			validInput = false;
                    System.out.println("Error: Item Number Can't Be Zero!");
        		}
            	for (Product product : productList) {
            		if (input == product.getItemNumber()) {
            			validInput = false;
                        System.out.println("Error: Duplicate Item Number Found!");
                        break;
            		}
            	}
            }
            //To check duplication ID
            if (prompt.equals("Enter the ID of the product to update")) {
            	if (input == 0) {
            		return input;
            	}
            	for (Product product : productList) {
            		if (input == product.getItemNumber()) {
            			return input;
            		}           
            	}
                System.out.println("Error: ID does not exist.");
                return -2;
            }
        } while (!validInput);
        return input;
    }
    
    
    // Method to execute appropriate methods based on user selection
    public static void executeMenu(int choice, Scanner scanner) {
        switch (choice) {
        	case 1:
        		addProducts();
        		break;
            case 2:
                viewProducts();
                break;
            case 3:
                addStock();
                break;
            case 4:
                deductStock();
                break;
            case 5:
                setProductStatus();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
        }
    }
    
    // Method to display the menu of the system and get user choice
    public static int displayMenu() {
        int choice = -1;

        System.out.println("\nMenu:");
        System.out.println("1. Add products");
        System.out.println("2. View products");
        System.out.println("3. Add stock");
        System.out.println("4. Deduct stock");
        System.out.println("5. Discontinue product");
        System.out.println("0. Exit");
        do {
            System.out.print("Please enter a menu option: ");
            try {
    			choice = Integer.parseInt(scanner.nextLine());
    			if(choice < 0 ||  choice > 5) {
    				System.out.println(onlyNumber);
    			}
    		} catch (NumberFormatException e) {
    			System.out.println(onlyNumber);
    		}
        } while (choice < 0 || choice > 5);
        return choice;
    }
    
    public static void addProducts() {
    	int choice = 0;
    	while (!(choice >= 1 && choice <= 4)) {
    		try {
    			System.out.print("\nWhich product you want to add? \n1. Refrigerator \n2. TV \n3. Printer \n4. Speaker \n0. Exit \n> ");
    			choice = Integer.parseInt(scanner.nextLine());
    			if(choice == 0) {
    				return;
    			}
    			if(choice != 1 && choice != 2 && choice != 3 && choice != 4) {
    				System.out.println(onlyNumber);
    			}
    		} catch (NumberFormatException e) {
    			System.out.println(onlyNumber);
    		}
			
    		System.out.println();
    		switch(choice) {
    		case 1:
    			addRefrigerator();
    			break;	
    		case 2:
    			addTV();	
    			break;
    		case 3:
    			addPrinter();
    			break;
    		case 4:
    			addSpeaker();
    			break;
    		}  	
    	}
    }
    
    public static void addRefrigerator() {
        System.out.println("Please enter the information for the refrigerator.");

        String name = getInput("Name", ALPHA_NUM_REGEX, onlyAlphaNum);
        String design = getInput("Door Design", ALPHA_REGEX, onlyAlpha);
        String color = getInput("Color", ALPHA_REGEX, onlyAlpha);
        int capacity = getIntInput("Capacity", onlyNumber);
        int quantity = getIntInput("Quantity Available in Stock", onlyNumber);
        double price = getDoubleInput("Price", onlyNumber);
        int itemNo = getIntInput("Item Number", onlyNumber);

        Refrigerator refrigerator = new Refrigerator(itemNo, name, quantity, price, design, color, capacity);
        productList.add(refrigerator);

        System.out.println();
    }
    
    public static void addTV() {
        System.out.println("Please enter the information for the TV.");

        String name = getInput("Name", ALPHA_NUM_REGEX, onlyAlphaNum);
        String screenType = getInput("Screen Type", ALPHA_REGEX, onlyAlpha);
        String resolution = getInput("Resolution", ALPHA_NUM_REGEX, onlyAlphaNum);
        double displaySize = getDoubleInput("Display Size (inches)", onlyNumber);
        int quantity = getIntInput("Quantity Available in Stock", onlyNumber);
        double price = getDoubleInput("Price", onlyNumber);
        int itemNo = getIntInput("Item Number", onlyNumber);

        TV tv = new TV(itemNo, name, quantity, price, screenType, resolution, displaySize);
        productList.add(tv);

        System.out.println();
    }
    
    public static void addPrinter() {
        System.out.println("Please enter the information for the printer.");

        String name = getInput("Name", ALPHA_NUM_REGEX, onlyAlphaNum);
        String color = getInput("Color", ALPHA_REGEX, onlyAlpha);
        String model = getInput("Model", ALPHA_NUM_REGEX, onlyAlphaNum);
        String technology = getInput("Technology", ALPHA_REGEX, onlyAlpha);
        double price = getDoubleInput("Price", onlyNumber);
        int quantity = getIntInput("Quantity Available in Stock", onlyNumber);
        int itemNo = getIntInput("Item Number", onlyNumber);

        Printer printer = new Printer(itemNo, name, price, quantity, color, model, technology);
        productList.add(printer);

        System.out.println();
    }

    public static void addSpeaker() {
        System.out.println("Please enter the information for the speaker.");

        String name = getInput("Name", ALPHA_NUM_REGEX, onlyAlphaNum);
        String color = getInput("Color", ALPHA_REGEX, onlyAlpha);
        String brand = getInput("Brand", ALPHA_REGEX, onlyAlpha);
        String model = getInput("Model", ALPHA_NUM_REGEX, onlyAlphaNum);
        double price = getDoubleInput("Price", onlyNumber);
        int quantity = getIntInput("Quantity Available in Stock", onlyNumber);
        int itemNo = getIntInput("Item Number", onlyNumber);

        Speaker speaker = new Speaker(itemNo, name, price, quantity, color, brand, model);
        productList.add(speaker);

        System.out.println();
    }
    
	// Function to display products in the list
	public static void viewProducts() {
		if(productList.isEmpty())		// check whether the product list is empty
			System.out.println("Error: The list is empty.\n");
		else 
			for (Product product : productList) {		//print all product details in the list
				System.out.println();
	            System.out.println("Product Type\t\t: " + getProductType(product)); // Display subtype name
				System.out.println(product);
			}

	}
	
	// Function to get the subtype name of a product
	private static String getProductType(Product product) {
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
	
    // Function to display products in the list
    public static int displayProducts(boolean notFirstTime) {
    	
    	if(!notFirstTime) {//Check if it is first time display product list
    		if (productList.isEmpty()) {
            	System.out.println("Error: The list is empty.\n");
            	return 0;
        	} else {
            	System.out.println("\nProducts     Quantity");
            	for (int i = 0; i < productList.size(); i++) {
            		System.out.println("ID" + productList.get(i).getItemNumber() + "\t\t" + productList.get(i).getQuantity());
            	}
        	}
        	System.out.println("0.\tExit");
    	}
        // Ask the user to choose which product to update
        int productIndex = getIntInput("Enter the ID of the product to update", onlyNumber);
        if (productIndex == 0) {
        	return 0;
        }
        else {
        	return productIndex;
        }
    }
    
    // Method to add stock values to a product
    public static void addStock() {
    	
        boolean idExist = false;
        int productID = displayProducts(idExist);
        //Validate for not exist ID and exit option
        do {
            idExist = false;
            // Display the products
            if (productID == 0) {
                return;
            } else if (productID == -2) {
                idExist = true;
                productID = displayProducts(idExist);
            }
        } while (idExist);
        
        int productArr = 0;
        //Obtain the product's array number in the list
        for (int i = 0; i < productList.size(); i++) {
        	if (productList.get(i).getItemNumber() == productID) {
        		productArr = i;
        	}
        }
        
        System.out.println("\nID" + productList.get(productArr).getItemNumber() 
        		+ "\t Current Stock Available: " + productList.get(productArr).getQuantity());
        
        // Prompt the user for how many products to add
        int quantityToAdd = getIntInput("Enter the quantity of products to add", onlyNumber);
        if (quantityToAdd == 0) { //No quantity to add
            System.out.println("No stock is added.\n");
            return;
        }
        else {
        	// Update the quantity of the selected product in stock
            	productList.get(productArr).addStock(quantityToAdd);
            	if (productList.get(productArr).getStatus() == true) {
            	System.out.println("Stock added successfully.\n");     
            	}
        }
    }
    
    // Method to deduct stock values from a product
    public static void deductStock() {
    	
        boolean idExist = false;
        int productID = displayProducts(idExist);
        //Validate for duplication ID and exit option
        do {
            idExist = false;
            // Display the products
            if (productID == 0) {
                return;
            } else if (productID == -2) {
                idExist = true;
                productID = displayProducts(idExist);
            }
        } while (idExist);
        
        int productArr = 0;        
        //Obtain the product's array number in the list
        for (int i = 0; i < productList.size(); i++) {
        	if (productList.get(i).getItemNumber() == productID) {
        		productArr = i;
        	}
        }
        
        System.out.println("\nID" + productList.get(productArr).getItemNumber() 
        		+ "\t Current Stock Available: " + productList.get(productArr).getQuantity());
        
        // Prompt the user for how many products to deduct
        int quantityToDeduct = getIntInput("Enter the quantity of products to deduct", onlyNumber);
        if (quantityToDeduct == 0) { //No quantity to deduct
            System.out.println("No stock is deducted.\n");
            return;
        }
        else {
        	// Update the quantity of the selected product in stock
        		if(productList.get(productArr).getQuantity() < quantityToDeduct) { //Exceed the available stock quantity to deduct
            		System.out.println("Quantity to deduct exceeds the available stock.\n");
        		}
        		else {
            		productList.get(productArr).deductStock(quantityToDeduct);
                	if (productList.get(productArr).getStatus() == true) {
                    	System.out.println("Stock deducted successfully.\n");     
                    	}
        		}
            }
    }


    // Method to set the product status (active or discontinued)
    // Method to add stock values to a product
    public static void setProductStatus() {
    	
        boolean idExist = false;
        int productID = displayProducts(idExist);
        //Validate for duplication ID and exit option
        do {
            idExist = false;
            // Display the products
            if (productID == 0) {
                return;
            } else if (productID == -2) {
                idExist = true;
                productID = displayProducts(idExist);
            }
        } while (idExist);
        
        int productArr = 0;
        //Obtain the product's array number in the list
        for (int i = 0; i < productList.size(); i++) {
        	if (productList.get(i).getItemNumber() == productID) {
        		productArr = i;
        	}
        }
        
        System.out.println("\n1. Active\n2. Discontinued\n0. Exit");
        boolean endSetting;
        do {
        	endSetting = true;
        	// Prompt the user for choosing option to set product's status
        	int statusToSet = getIntInput("Enter the status to set", onlyNumber);
        	if (statusToSet == 0) { //Exit
            	return;
        	}
        	else {
        		// Update the product status
        		if (statusToSet == 1) {
        			productList.get(productArr).setStatus(true);
                	System.out.println("Product is set to ACTIVE.\n");
        		}
        		else if (statusToSet == 2){
        			productList.get(productArr).setStatus(false);
                	System.out.println("Product is set to DISCONTINUED.\n");
        		}
        		else {
                	System.out.println(onlyNumber);
                	endSetting = false;
        		}
        	}
        } while (!endSetting);
    }


    public static void displayWelcomeMessage() {
        System.out.println("Welcome to the Stock Management System");
        
        // Display current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Current Date and Time: " + formattedDateTime);
        
        // Display group member names
        System.out.println("Group Members: Lee Chorng Huah, Ng Tuck Seng, Ng Wei Hong, Ng Wei Yu\n"); // Replace with actual group member names
    }
    
    // Main method
    public static void main(String[] args) {
    	displayWelcomeMessage();
    	UserInfo user = new UserInfo();
    	user.setName();
    	user.generateUserID();

        int menuChoice;
        do {
            menuChoice = displayMenu();
            executeMenu(menuChoice, scanner);
        } while (menuChoice != 0);
		System.out.println("User ID   : " + user.getUserID() + "\nUser Name : " + user.getName());
		scanner.close();
		System.exit(1);
    }
}
