public abstract class Product {
    private int itemNumber;
    private String productName;
    private double price;
    private int quantity;
    private boolean status = true;
    
    //Default constructor
	public Product() {
		productName = " ";
		price = 0.00;
		quantity = 0;
		itemNumber = 0;
		status = true;
	}
	
	//Parameterized constructor that takes in four parameters
    public Product(int itemNumber, String productName, double price, int quantity) {
        this.itemNumber = itemNumber;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.status = true; // Default status is active
    }

    // Getter for item number
    public int getItemNumber() {
        return itemNumber;
    }
    
    //Setter for item number
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    //Getter for product name
    public String getProductName() {
        return productName;
    }
    
    //Setter for product name
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    //Getter for product price
    public double getPrice() {
        return price;
    }

    //Setter for product price
    public void setPrice(double price) {
        this.price = price;
    }

    //Getter for quantity available
    public int getQuantity() {
        return quantity;
    }

    //Setter for quantity available
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //Getter for product status
    public boolean getStatus() {
        return status;
    }

    //Setter for product status
    public void setStatus(boolean status) {
        this.status = status;
    }

    //Instance method to get the total inventory value
    public double getTotalInventoryValue() {
        return price * quantity;
    }

    //Instance method to add the number of quantity available
    public void addStock(int quantity) {
        if (status) { // Only allow adding stock if product is active
            this.quantity += quantity;
        } else {
            System.out.println("Cannot add stock to discontinued product.\n");
        }
    }

    //Instance method to deduct the number of quantity available
    public void deductStock(int quantity) {
        if (quantity >= 0 && quantity <= this.quantity && status == true) {
            this.quantity -= quantity;
        } 
        else if (this.quantity == 0) { //Empty stock
            System.out.println("Stock is empty!");
        }
        else if (quantity > this.quantity) { //Not enough stock
        	System.out.println("Quantity entered exceeds the available quantity!");
        }
        else if (quantity < 0) { //A Not-Number-Value is entered
            System.out.println("Please enter a valid number!");
        }
        else { //Product is inactive
            System.out.println("Cannot deduct stock from discontinued product.");
        }
    }

    //Display information of Product object
    @Override
    public String toString() {
        return "Item number         : " + itemNumber +
               "\nProduct name      : " + productName +
               "\nQuantity available: " + quantity +
               "\nPrice (RM)        : " + String.format("%.2f", price) +
               "\nInventory value (RM): " + String.format("%.2f", getTotalInventoryValue()) +
               "\nProduct status    : " + (status ? "Active" : "Discontinued");
    }
}
