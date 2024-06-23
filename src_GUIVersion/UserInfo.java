
public class UserInfo {
    private String name;
    private String userID;

    // Constructor
    public UserInfo() {
        this.name = "";
        this.userID = "";
    }
    
    // Constructor
    public UserInfo(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }

    // Method to get name of the user
    public boolean setName(String inputName) {

        try {

            if (isValidName(inputName)) {
                this.name = inputName;
                generateUserID();
                return true;
            } else {
            	return false;
            }
        } catch (Exception e) {
        	return false;
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
