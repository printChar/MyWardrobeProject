package model.dto;

public class Outfit {
    private int id;
    private int top;
    private int bottom;
    private int shoes;
    private int style;

    public Outfit() {
    }

    public Outfit(int top, int bottom, int shoes, int style) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.style = style;
    }

    public int getId() {
        return id;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getShoes() {
        return shoes;
    }

    public int getStyle() {
        return style;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setShoes(int shoes) {
        this.shoes = shoes;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}