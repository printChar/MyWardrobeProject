package model.dto;

public class Style {
    private Long id;
    private String name;
    public Style(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Style() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
