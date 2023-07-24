package model.dto;
public class Article {
    private int id;
    private int colour;
    private int style;
    private int category;
    private Enum size;
    private Enum gender;
    private int model;
    private int brand;
    private String picSrc;
    private boolean isClean;

    public Article(int colour, int style, int category, Enum size, Enum gender, int model, int brand, String picSrc, boolean isClean) {
        this.colour = colour;
        this.style = style;
        this.category = category;
        this.size = size;
        this.gender = gender;
        this.model = model;
        this.brand = brand;
        this.picSrc = picSrc;
        this.isClean = isClean;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Enum getSize() {
        return size;
    }

    public void setSize(Enum size) {
        this.size = size;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " Colour: " + getColour()
                + " Style: " + getStyle() + " Category: " + getCategory()
                + " Gender: " + getGender() + " Model: " + getModel()
                + " Brand: " + getBrand() + " PicSrc: " + getPicSrc()
                + "Is clean: " + isClean();
    }
}

