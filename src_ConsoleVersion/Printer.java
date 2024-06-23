public class Printer extends Product {
    private String color;
    private String model;
    private String technology; // Inkjet, Laser, Dot Matrix, etc.

    // Constructor for Printer class
    public Printer(int itemNumber, String productName, double price, int quantity, String color, String model, String technology) {
        super(itemNumber, productName, price, quantity);
        this.color = color;
        this.model = model;
        this.technology = technology;
    }

    // Getters and setters for brand, model, and technology
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
    
    // Method to calculate the value of printer stock
    public double calculateRefrigeratorStockValue() {
        return getPrice() * getQuantity();
    }
    
    @Override
    public String toString() {
        return  "Item number \t\t: " + getItemNumber() + "\n" +
                "Product name \t\t: " + getProductName() + "\n" +
                "Color \t\t\t: " + color + "\n" +
                "Model \t\t\t: " + model + "\n" +
                "Technology \t\t: " + technology + "\n" +
                "Quantity available\t: " + getQuantity() + "\n" +
                "Price (RM) \t\t: " + String.format("%.2f", getPrice()) + "\n" +
                "Inventory value (RM)\t: " + String.format("%.2f", getTotalInventoryValue()) + "\n" +
                "Product status \t\t: " + (getStatus()? "Active" : "Discontinued") + "\n";
    }
}