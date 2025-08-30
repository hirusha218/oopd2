package panlen;

import dao.StockDAO;
import model.Stock;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * StockManagementPanel - Implements the Single Responsibility Principle
 * 
 * This panel is responsible solely for stock management operations,
 * including CRUD operations, search functionality, and inventory tracking.
 * It encapsulates all stock-related UI logic and business rules,
 * ensuring that changes to stock management don't affect other system components.
 */
public class StockManagementPanel extends JPanel {
    
    private StockDAO stockDAO;
    private DefaultTableModel stockTableModel;
    private Stock selectedStock;
    
    // UI Components
    private JTable stockTable;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField unitPriceField;
    private JTextField expiryDateField;
    private JTextField statusField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField searchField;
    
    public StockManagementPanel(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
        initializeComponents();
        setupLayout();
        loadStockData();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Initialize table model
        stockTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Define column names
        String[] columnNames = {"Stock ID", "Name", "Category", "Quantity", "Unit Price", "Expiry Date", "Status"};
        for (String columnName : columnNames) {
            stockTableModel.addColumn(columnName);
        }

        // Initialize UI components
        stockTable = new JTable(stockTableModel);
        nameField = new JTextField(20);
        categoryField = new JTextField(20);
        quantityField = new JTextField(20);
        unitPriceField = new JTextField(20);
        expiryDateField = new JTextField(20);
        statusField = new JTextField(20);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchField = new JTextField(20);
    }
    
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // First row
        JPanel row1 = new JPanel();
        row1.add(new JLabel("Name:"));
        row1.add(nameField);
        row1.add(new JLabel("Category:"));
        row1.add(categoryField);
        formPanel.add(row1);
        
        // Second row
        JPanel row2 = new JPanel();
        row2.add(new JLabel("Quantity:"));
        row2.add(quantityField);
        row2.add(new JLabel("Unit Price:"));
        row2.add(unitPriceField);
        formPanel.add(row2);
        
        // Third row
        JPanel row3 = new JPanel();
        row3.add(new JLabel("Expiry Date:"));
        row3.add(expiryDateField);
        row3.add(new JLabel("Status:"));
        row3.add(statusField);
        formPanel.add(row3);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        formPanel.add(buttonPanel);
        
        add(formPanel);
    }
    
    private void setupEventHandlers() {
        // Table selection listener
        stockTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
        
        // Button action listeners
        addButton.addActionListener(e -> handleAddStock());
        updateButton.addActionListener(e -> handleUpdateStock());
        deleteButton.addActionListener(e -> handleDeleteStock());
        
        // Search field listener
        searchField.addActionListener(e -> performSearch());
    }
    
    private void loadStockData() {
        try {
            List<Stock> stockList = stockDAO.getAllStock();
            stockTableModel.setRowCount(0); // Clear existing rows
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            for (Stock stock : stockList) {
                Object[] row = {
                    stock.getStockId(),
                    stock.getName(),
                    stock.getCategory(),
                    stock.getQuantity(),
                    stock.getUnitPrice() != null ? "$" + stock.getUnitPrice().toString() : "$0.00",
                    stock.getExpiryDate() != null ? dateFormat.format(stock.getExpiryDate()) : "N/A",
                    stock.getStatus()
                };
                stockTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading stock data: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleTableSelection() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int stockId = (Integer) stockTableModel.getValueAt(selectedRow, 0);
                selectedStock = stockDAO.getStockById(stockId);
                if (selectedStock != null) {
                    populateFormFields(selectedStock);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading stock details: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFields(Stock stock) {
        nameField.setText(stock.getName());
        categoryField.setText(stock.getCategory());
        quantityField.setText(String.valueOf(stock.getQuantity()));
        unitPriceField.setText(stock.getUnitPrice() != null ? stock.getUnitPrice().toString() : "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        expiryDateField.setText(stock.getExpiryDate() != null ? dateFormat.format(stock.getExpiryDate()) : "");
        statusField.setText(stock.getStatus());
    }
    
    private void clearFormFields() {
        nameField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        unitPriceField.setText("");
        expiryDateField.setText("");
        statusField.setText("");
        selectedStock = null;
    }
    
    private Stock getStockFromForm() {
        Stock stock = new Stock();
        if (selectedStock != null) {
            stock.setStockId(selectedStock.getStockId());
        }
        stock.setName(nameField.getText().trim());
        stock.setCategory(categoryField.getText().trim());
        try {
            stock.setQuantity(Integer.parseInt(quantityField.getText().trim()));
        } catch (NumberFormatException e) {
            stock.setQuantity(0);
        }
        try {
            stock.setUnitPrice(new BigDecimal(unitPriceField.getText().trim()));
        } catch (NumberFormatException e) {
            stock.setUnitPrice(BigDecimal.ZERO);
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            stock.setExpiryDate(dateFormat.parse(expiryDateField.getText().trim()));
        } catch (Exception e) {
            stock.setExpiryDate(new Date());
        }
        stock.setStatus(statusField.getText().trim());
        return stock;
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        if (categoryField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Category", "Validation Error", JOptionPane.WARNING_MESSAGE);
            categoryField.requestFocus();
            return false;
        }
        if (quantityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Quantity", "Validation Error", JOptionPane.WARNING_MESSAGE);
            quantityField.requestFocus();
            return false;
        }
        if (unitPriceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Unit Price", "Validation Error", JOptionPane.WARNING_MESSAGE);
            unitPriceField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void handleAddStock() {
        if (!validateForm()) {
            return;
        }
        
        try {
            Stock newStock = getStockFromForm();
            if (stockDAO.createStock(newStock)) {
                JOptionPane.showMessageDialog(this, "Stock item added successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStockData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add stock item.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateStock() {
        if (selectedStock == null) {
            JOptionPane.showMessageDialog(this, "Please select a stock item to update.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Stock updatedStock = getStockFromForm();
            if (stockDAO.updateStock(updatedStock)) {
                JOptionPane.showMessageDialog(this, "Stock item updated successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStockData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update stock item.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeleteStock() {
        if (selectedStock == null) {
            JOptionPane.showMessageDialog(this, "Please select a stock item to delete.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete stock item: " + selectedStock.getName() + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (stockDAO.deleteStock(selectedStock.getStockId())) {
                    JOptionPane.showMessageDialog(this, "Stock item deleted successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                    loadStockData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete stock item.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadStockData();
            return;
        }
        
        try {
            List<Stock> searchResults = stockDAO.searchStock(searchTerm);
            stockTableModel.setRowCount(0);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Stock stock : searchResults) {
                Object[] row = {
                    stock.getStockId(),
                    stock.getName(),
                    stock.getCategory(),
                    stock.getQuantity(),
                    stock.getUnitPrice() != null ? "$" + stock.getUnitPrice().toString() : "$0.00",
                    stock.getExpiryDate() != null ? dateFormat.format(stock.getExpiryDate()) : "N/A",
                    stock.getStatus()
                };
                stockTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No stock items found matching: " + searchTerm, 
                                            "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshData() {
        loadStockData();
    }
} 