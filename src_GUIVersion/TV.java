public class TV extends Product {
    private String screenType;
    private String resolution;
    private double displaySize;

    // Constructor
    public TV(int itemNumber, String productName, int quantity, double price,
              String screenType, String resolution, double displaySize) {
        super(itemNumber, productName, price, quantity);
        this.screenType = screenType;
        this.resolution = resolution;
        this.displaySize = displaySize;
    }

    // Getter and setter methods
    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(double displaySize) {
        this.displaySize = displaySize;
    }

    // Method to calculate the value of TV stock
    public double calculateTVStockValue() {
        return getPrice() * getQuantity();
    }

    //Display information of TV
	@Override
	public String toString() {
		
		return "Item number \t\t: " + getItemNumber() + "\n" + 
				"Product name \t\t: " + getProductName() + "\n" +
				"Screen Type \t\t: " + screenType + "\n" +
				"Resolution \t\t: " + resolution + "\n" +
				"Display Size\t\t: " + displaySize + "\n" +
				"Quantity available \t: " + getQuantity() + "\n" +
				"Price (RM) \t\t: " + String.format("%.2f", getPrice()) + "\n" + 
				"Inventory value (RM): " + String.format("%.2f", getTotalInventoryValue()) +  "\n" +
				"Product status \t: " + (getStatus()? "Active" : "Discontinued") + "\n";

	}


}
