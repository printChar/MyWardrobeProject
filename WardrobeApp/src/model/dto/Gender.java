package model.dto;

public enum Gender {
    UNISEX("Unisex"),
    WOMEN("Kvinna"),
    MEN("Man");

    private final String size;

    Gender(String size) {
        this.size = size;
    }
}
