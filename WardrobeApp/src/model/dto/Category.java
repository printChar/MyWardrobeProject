package model.dto;

public enum Category {
    TOP("Top"),
    BOTTOM("Bottom"),
    SHOES("Shoes");
    private final String category;
    Category(String category) {
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
}
