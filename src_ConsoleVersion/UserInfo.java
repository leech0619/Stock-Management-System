import java.util.Scanner;

public class UserInfo {
    private String name;
    private String userID;

    // Constructor
    public UserInfo() {
        this.name = "";
        this.userID = "";
    }

    // Method to get name of the user
    public void setName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your first name and surname: ");
        try {
            String inputName = scanner.nextLine().trim();
            if (isValidName(inputName)) {
                this.name = inputName;
            } else {
                System.out.println("Error: Invalid name format. Please enter only alphabetic characters and spaces.");
                setName(); // Recursive call to retry input
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
            scanner.nextLine(); // Consume invalid input
            setName(); // Recursive call to retry input
        }
    }

    // Method to check if the name is valid (contains only alphabetic characters and spaces)
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    // Method to check if the name contains spaces
    public boolean hasSpaces() {
        return name.contains(" ");
    }

    // Method to generate user ID
    public void generateUserID() {
        try {
            if (hasSpaces()) {
                String[] names = name.split(" ");
                this.userID = names[0].substring(0, 1).toUpperCase() + names[names.length - 1];
            } else {
                this.userID = "guest";
            }
        } catch (Exception e) {
            System.out.println("Error: Unable to generate user ID. Please check input.");
            this.userID = "error"; // Set user ID to indicate error
        }
    }

    // Getter method
    public String getUserID() {
        return userID;
    }
    
    public String getName() {
    	return name;
    }
}
