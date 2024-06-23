public class Speaker extends Product {
    private String color;
    private String brand;
    private String model;

    // Constructor for Speaker class
    public Speaker(int itemNumber, String productName, double price, int quantity, String color, String brand, String model) {
        super(itemNumber, productName, price, quantity);
        this.color = color;
        this.brand = brand;
        this.model = model;
    }

    // Getters and setters for color, brand, and model
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Method to calculate the value of speaker stock
    public double calculateRefrigeratorStockValue() {
        return getPrice() * getQuantity();
    }
    
    // Override toString method to display Speaker information
    @Override
    public String toString() {
        return  "Item number \t\t: " + getItemNumber() + "\n" +
                "Product name \t\t: " + getProductName() + "\n" +
                "Color \t\t\t: " + color + "\n" +
                "Brand \t\t\t: " + brand + "\n" +
                "Model \t\t\t: " + model + "\n" +
        		"Quantity available\t: " + getQuantity() + "\n" +
        		"Price (RM) \t\t: " + String.format("%.2f", getPrice()) + "\n" + 
        		"Inventory value (RM)\t: " + String.format("%.2f", getTotalInventoryValue()) +  "\n" +
        		"Product status \t\t: " + (getStatus()? "Active" : "Discontinued") + "\n";	

    }
}