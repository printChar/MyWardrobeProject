package model.dto;

public enum Gender {
    UNISEX("Unisex"),
    WOMEN("Women"),
    MEN("Men");
    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
