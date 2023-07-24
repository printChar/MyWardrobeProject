package model.dto;

public enum Size {
    XS(34),
    S(36),
    M(38),
    L(40),
    XL(42);

    private int value;

    Size(int value) {
        this.value = value;
    }

    public int getSizeValue() {
        return value;
    }



}
