public class Refrigerator extends Product {
    private String doorDesign;
    private String color;
    private int capacity;

    //Parameterized constructor
    public Refrigerator(int itemNumber, String productName, int quantity, double price,
                        String doorDesign, String color, int capacity) {
        super(itemNumber, productName, price, quantity);
        this.doorDesign = doorDesign;
        this.color = color;
        this.capacity = capacity;
    }

    // Getters and setters
    public String getDoorDesign() {
        return doorDesign;
    }

    public void setDoorDesign(String doorDesign) {
        this.doorDesign = doorDesign;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Method to calculate the value of refrigerator stock
    public double calculateRefrigeratorStockValue() {
        return getPrice() * getQuantity();
    }

    //Display information of Refrigerator object
    @Override
	public String toString() {
		
		return	"Item number \t\t: " + getItemNumber() + "\n" + 
				"Product name \t\t: " + getProductName() + "\n" +
				"Door design \t\t: " + doorDesign + "\n" +
				"Color \t\t\t: " + color + "\n" +
				"Capacity (in Litres)\t: " + capacity + "\n" +
				"Quantity available\t: " + getQuantity() + "\n" +
				"Price (RM) \t\t: " + String.format("%.2f", getPrice()) + "\n" + 
				"Inventory value (RM)\t: " + String.format("%.2f", getTotalInventoryValue()) +  "\n" +
				"Product status \t\t: " + (getStatus()? "Active" : "Discontinued") + "\n";	
	}

}
