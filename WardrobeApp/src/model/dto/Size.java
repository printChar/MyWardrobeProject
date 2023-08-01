package model.dto;

public enum Size {
    XS(34),
    S(36),
    SM(37),
    M(38),
    ML(39),
    L(40),
    XL(42);
    private final int value;
    Size(int value) {
        this.value = value;
    }
    public int toIntValue() {
        return value;
    }

    public static Size toTextValue(int value) {
        Size size = null;
        switch (value){
            case 36:
                size = S;
            break;
            case 37:
                size = SM;
                break;
            case 38:
                size = M;
            break;
            case 39:
                size = ML;
                break;
            case 40:
                size = L;
            break;
        }
        return size;
    }
}
