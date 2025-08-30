package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hiruw
 */
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    private int stockId;
    private String name;
    private String category;
    private int quantity;
    private BigDecimal unitPrice;
    private Date expiryDate;
    private String status;
    private Date createdDate;
    private Date updatedDate;

    // Default constructor
    public Stock() {
    }

    // Parameterized constructor
    public Stock(int stockId, String name, String category, int quantity, BigDecimal unitPrice, 
                 Date expiryDate, String status) {
        this.stockId = stockId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.expiryDate = expiryDate;
        this.status = status;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    // Getters and Setters
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Helper methods
    public BigDecimal getTotalValue() {
        if (unitPrice != null && quantity > 0) {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    public boolean isLowStock() {
        return quantity <= 10; // Consider low stock if quantity is 10 or less
    }

    public boolean isExpired() {
        if (expiryDate != null) {
            return new Date().after(expiryDate);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Stock{"
                + "stockId=" + stockId
                + ", name='" + name + '\''
                + ", category='" + category + '\''
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice
                + ", expiryDate=" + expiryDate
                + ", status='" + status + '\''
                + '}';
    }
} 