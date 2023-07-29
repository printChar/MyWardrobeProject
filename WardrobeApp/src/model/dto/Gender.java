package model.dto;

public enum Gender {
    UNISEX("Unisex"),
    WOMEN("Kvinna"),
    MEN("Man");
    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
