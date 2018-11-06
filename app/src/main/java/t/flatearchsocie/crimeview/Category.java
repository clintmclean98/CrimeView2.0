package t.flatearchsocie.crimeview;

public class  Category {
    private int categoryID, severityIndicator;
    private String categoryName;

    public Category(int categoryID, int severityIndicator, String categoryName) {
        this.categoryID = categoryID;
        this.severityIndicator = severityIndicator;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getSeverityIndicator() {
        return severityIndicator;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
