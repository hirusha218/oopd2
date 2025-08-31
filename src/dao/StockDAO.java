package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Stock;
import java.math.BigDecimal;
import java.util.Date;

/**
 * StockDAO - Implements the DAO (Data Access Object) Pattern
 * 
 * This class encapsulates all database operations for Stock entities,
 * providing a clean separation between business logic and data access.
 * It handles CRUD operations, search functionality, and inventory
 * management while abstracting the underlying database implementation details.
 */
public class StockDAO {

    private final Connection connection;

    public StockDAO(Connection connection) {
        this.connection = connection;
    }

    // Create new stock item
    public boolean createStock(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock (name, category, quantity, unit_price, expiry_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stock.getName());
            pstmt.setString(2, stock.getCategory());
            pstmt.setInt(3, stock.getQuantity());
            pstmt.setBigDecimal(4, stock.getUnitPrice());
            if (stock.getExpiryDate() != null) {
                pstmt.setDate(5, new java.sql.Date(stock.getExpiryDate().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            pstmt.setString(6, stock.getStatus() != null ? stock.getStatus() : "Active");
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get all stock items
    public List<Stock> getAllStock() throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT * FROM stock ORDER BY stock_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setStockId(rs.getInt("stock_id"));
                stock.setName(rs.getString("name"));
                stock.setCategory(rs.getString("category"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                stock.setExpiryDate(rs.getDate("expiry_date"));
                stock.setStatus(rs.getString("status"));
                stockList.add(stock);
            }
        }
        return stockList;
    }

    // Get stock by ID
    public Stock getStockById(int stockId) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stock_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, stockId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setName(rs.getString("name"));
                    stock.setCategory(rs.getString("category"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setStatus(rs.getString("status"));
                    return stock;
                }
            }
        }
        return null;
    }

    // Update stock item
    public boolean updateStock(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET name=?, category=?, quantity=?, unit_price=?, expiry_date=?, status=? WHERE stock_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, stock.getName());
            pstmt.setString(2, stock.getCategory());
            pstmt.setInt(3, stock.getQuantity());
            pstmt.setBigDecimal(4, stock.getUnitPrice());
            if (stock.getExpiryDate() != null) {
                pstmt.setDate(5, new java.sql.Date(stock.getExpiryDate().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            pstmt.setString(6, stock.getStatus() != null ? stock.getStatus() : "Active");
            pstmt.setInt(7, stock.getStockId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete stock item
    public boolean deleteStock(int stockId) throws SQLException {
        String sql = "DELETE FROM stock WHERE stock_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, stockId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Search stock by name, category, or status
    public List<Stock> searchStock(String searchTerm) throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE name LIKE ? OR category LIKE ? OR status LIKE ? ORDER BY stock_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setName(rs.getString("name"));
                    stock.setCategory(rs.getString("category"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setStatus(rs.getString("status"));
                    stockList.add(stock);
                }
            }
        }
        return stockList;
    }

    // Advanced search stock by multiple fields
    public List<Stock> searchStockAdvanced(String nameSearch, String idSearch, String categorySearch, 
                                          String qtySearch, String dateSearch, String statusSearch) throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM stock WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        
        if (nameSearch != null && !nameSearch.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
            parameters.add("%" + nameSearch.trim() + "%");
        }
        
        if (idSearch != null && !idSearch.trim().isEmpty()) {
            try {
                int stockId = Integer.parseInt(idSearch.trim());
                sql.append(" AND stock_id = ?");
                parameters.add(stockId);
            } catch (NumberFormatException e) {
                // If ID is not a valid number, skip this search criteria
            }
        }
        
        if (categorySearch != null && !categorySearch.trim().isEmpty()) {
            sql.append(" AND category LIKE ?");
            parameters.add("%" + categorySearch.trim() + "%");
        }
        
        if (qtySearch != null && !qtySearch.trim().isEmpty()) {
            try {
                int quantity = Integer.parseInt(qtySearch.trim());
                sql.append(" AND quantity = ?");
                parameters.add(quantity);
            } catch (NumberFormatException e) {
                // If quantity is not a valid number, skip this search criteria
            }
        }
        
        if (dateSearch != null && !dateSearch.trim().isEmpty()) {
            sql.append(" AND DATE(expiry_date) = ?");
            parameters.add(dateSearch.trim());
        }
        
        if (statusSearch != null && !statusSearch.trim().isEmpty()) {
            sql.append(" AND status LIKE ?");
            parameters.add("%" + statusSearch.trim() + "%");
        }
        
        sql.append(" ORDER BY stock_id");
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setName(rs.getString("name"));
                    stock.setCategory(rs.getString("category"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setStatus(rs.getString("status"));
                    stockList.add(stock);
                }
            }
        }
        return stockList;
    }

    // Get low stock items
    public List<Stock> getLowStockItems(int threshold) throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE quantity <= ? ORDER BY quantity ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, threshold);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setName(rs.getString("name"));
                    stock.setCategory(rs.getString("category"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setStatus(rs.getString("status"));
                    stockList.add(stock);
                }
            }
        }
        return stockList;
    }

    // Get expired stock items
    public List<Stock> getExpiredStockItems() throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE expiry_date < CURDATE() ORDER BY expiry_date ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Stock stock = new Stock();
                stock.setStockId(rs.getInt("stock_id"));
                stock.setName(rs.getString("name"));
                stock.setCategory(rs.getString("category"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                stock.setExpiryDate(rs.getDate("expiry_date"));
                stock.setStatus(rs.getString("status"));
                stockList.add(stock);
            }
        }
        return stockList;
    }

    // Update stock quantity
    public boolean updateStockQuantity(int stockId, int newQuantity) throws SQLException {
        String sql = "UPDATE stock SET quantity = ? WHERE stock_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, stockId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get stock by category
    public List<Stock> getStockByCategory(String category) throws SQLException {
        List<Stock> stockList = new ArrayList<>();
        String sql = "SELECT * FROM stock WHERE category = ? ORDER BY name";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockId(rs.getInt("stock_id"));
                    stock.setName(rs.getString("name"));
                    stock.setCategory(rs.getString("category"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setUnitPrice(rs.getBigDecimal("unit_price"));
                    stock.setExpiryDate(rs.getDate("expiry_date"));
                    stock.setStatus(rs.getString("status"));
                    stockList.add(stock);
                }
            }
        }
        return stockList;
    }

    // Get total stock value
    public BigDecimal getTotalStockValue() throws SQLException {
        String sql = "SELECT SUM(quantity * unit_price) as total_value FROM stock WHERE status = 'Active'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total_value");
                return total != null ? total : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    // Get all categories
    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM stock ORDER BY category";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }
        return categories;
    }
} 