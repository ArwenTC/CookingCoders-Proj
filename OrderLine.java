
package groupproject;
/**
 * this class represents a single line item in an order, consisting of a product and its quantity.
 * 
 *
 */
public class OrderLine {
    
    private String productName;
    private int quantity;
    
    /**
      * Constructs an OrderLine with the specified product name and quantity.
     *
     * @param productName The name of the product.
     * @param quantity The quantity of the product in the order line.
     */
     
    public OrderLine(String productName, int quantity) { 
        this.productName = productName;
        this.quantity = quantity;

        // PSEUDO - FROM SQL DATABASE
        // Adds new orderline to the SQL database
    }
    
    /**
     * Constructor given an SQL query
     */
    public OrderLine() {

        // PSEUDO - FROM SQL DATABASE
        // Collects data for this.fields from SQL database
        
    }
    
    
     /**
     * Returns the name of the product in this order line.
     *
     * @return The product name.
     */
    public String getProductName() {
        return this.productName;
    }
    
     /**
     * Returns the quantity of the product in this order line.
     *
     * @return The quantity.
     */
    public int getQuantity() {
        return this.quantity;
    }
    
    
     /**
     * Sets the quantity of the product in this order line.
     *
     * @param newQuantity The new quantity to set.
     */
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
    
}