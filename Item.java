package groupproject;

public class Item {
    
    private String name;
    private double price;
    
    /**
     * Constructor that initializes an item with a name and a price
     * @param name             The name of the item
     * @param price            The price of the item
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;

        // PSEUDO - FROM SQL DATABASE
        // Adds item to the database
        
    }
    /**
     * Constructor that initializes an item with a name 
     * The price is retrieved from the database based on the item name.
     * 
     * @param name            The name of the item
     */
    public Item(String name) {
        this.name = name;

        // PSEUDO - FROM SQL DATABASE
        // Gets item price from the database
        
    }
    
    //
    // GETTER METHODS
    //
    
    /**
     * Returns the item name
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the item price
     * @return the price of the item
     */
    public double getPrice() {
        return this.price;
    }
}