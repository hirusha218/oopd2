package utils;

/**
 * DashboardConstants - Implements the Constants Pattern
 * 
 * This class centralizes all magic numbers and strings used throughout
 * the dashboard, implementing the DRY (Don't Repeat Yourself) principle.
 * It provides a single source of truth for UI constants, making the
 * system more maintainable and reducing the risk of inconsistencies.
 */
public final class DashboardConstants {
    
    // Private constructor to prevent instantiation
    private DashboardConstants() {}
    
    // UI Constants
    public static final int TEXT_FIELD_WIDTH = 20;
    public static final int SCROLL_PANE_WIDTH = 884;
    public static final int SCROLL_PANE_HEIGHT = 495;
    public static final int BILLING_SCROLL_PANE_WIDTH = 887;
    public static final int BILLING_SCROLL_PANE_HEIGHT = 456;
    
    // Tab Names
    public static final String STOCK_MANAGEMENT_TAB = "Stock Management";
    public static final String STAFF_MANAGEMENT_TAB = "Staff Management";
    public static final String PATIENT_RECORDS_TAB = "Patient Records";
    public static final String BILLING_CLAIMS_TAB = "Billing & Claims";
    
    // Panel Titles
    public static final String STOCK_MANAGEMENT_TITLE = "Stock Management";
    public static final String STAFF_MANAGEMENT_TITLE = "Staff Management";
    public static final String PATIENT_RECORDS_TITLE = "Patient Records";
    public static final String BILLING_CLAIMS_TITLE = "Billing & Claims";
    
    // Button Text
    public static final String ADD_BUTTON_TEXT = "Add";
    public static final String UPDATE_BUTTON_TEXT = "Update";
    public static final String DELETE_BUTTON_TEXT = "Delete";
    
    // Form Labels
    public static final String NAME_LABEL = "Name";
    public static final String CATEGORY_LABEL = "Category";
    public static final String QUANTITY_LABEL = "Quantity";
    public static final String UNIT_PRICE_LABEL = "Unit Price";
    public static final String EXPIRY_DATE_LABEL = "Expiry Date";
    public static final String STATUS_LABEL = "Status";
    public static final String FIRST_NAME_LABEL = "First Name";
    public static final String LAST_NAME_LABEL = "Last Name";
    public static final String ROLE_LABEL = "Role";
    public static final String MOBILE_LABEL = "Mobile";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String DEPARTMENT_LABEL = "Department";
    public static final String EMAIL_LABEL = "Email";
    public static final String PATIENT_ID_LABEL = "Patient ID";
    public static final String PATIENT_NAME_LABEL = "Patient Name";
    public static final String DOCTOR_NAME_LABEL = "Doctor Name";
    public static final String AMOUNT_LABEL = "Amount";
    public static final String BILLING_ID_LABEL = "Billing ID";
    public static final String SEARCH_LABEL = "Search";
    
    // Table Column Names
    public static final String[] STAFF_TABLE_COLUMNS = {
        "Staff ID", "First Name", "Last Name", "Role", "Contact Number", 
        "Username", "Department", "Email", "Status"
    };
    
    public static final String[] STOCK_TABLE_COLUMNS = {
        "Stock ID", "Name", "Category", "Quantity", "Unit Price", "Expiry Date", "Status"
    };
    
    public static final String[] PATIENT_TABLE_COLUMNS = {
        "Patient ID", "First Name", "Last Name", "Gender", "Contact Number", "Email", "Status"
    };
    
    public static final String[] BILLING_TABLE_COLUMNS = {
        "Bill ID", "Patient Name", "Doctor Name", "Total Amount", "Payment Status", "Bill Date"
    };
    
    // Status Values
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_INACTIVE = "Inactive";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_PAID = "Paid";
    
    // Default Values
    public static final String DEFAULT_EMPTY_TEXT = "";
    public static final String DEFAULT_STATUS = "Active";
    public static final int DEFAULT_QUANTITY = 0;
    public static final String DEFAULT_CURRENCY_SYMBOL = "$";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    // Error Messages
    public static final String ERROR_LOADING_DATA = "Error loading data: ";
    public static final String ERROR_LOADING_DETAILS = "Error loading details: ";
    public static final String ERROR_DATABASE = "Database error: ";
    public static final String ERROR_SEARCH = "Search error: ";
    public static final String ERROR_VALIDATION = "Validation Error";
    public static final String ERROR_GENERAL = "Error";
    
    // Success Messages
    public static final String SUCCESS_ADDED = " added successfully!";
    public static final String SUCCESS_UPDATED = " updated successfully!";
    public static final String SUCCESS_DELETED = " deleted successfully!";
    
    // Information Messages
    public static final String INFO_SELECT_ITEM = "Please select an item to ";
    public static final String INFO_NO_RESULTS = "No items found matching: ";
    public static final String INFO_CONFIRM_DELETE = "Are you sure you want to delete ";
    
    // Validation Messages
    public static final String VALIDATION_ENTER_FIELD = "Please enter ";
    public static final String VALIDATION_USERNAME_EXISTS = "Username already exists. Please choose a different username.";
    
    // File Paths
    public static final String ABSOLUTE_LAYOUT_PATH = "org.netbeans.lib.awtextra.AbsoluteLayout";
    
    // Font Settings
    public static final String DEFAULT_FONT_FAMILY = "Segoe UI";
    public static final int TITLE_FONT_SIZE = 36;
    public static final int SUBTITLE_FONT_SIZE = 24;
    public static final int PANEL_TITLE_FONT_SIZE = 18;
} 