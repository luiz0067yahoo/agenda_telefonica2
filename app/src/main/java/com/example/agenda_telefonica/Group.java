package com.example.agenda_telefonica;

public class Group {
    private long id;
    private String name;

    // Construtor vazio
    public Group() {

    }
    public Group( String name) {
        this.name = name;
    }

    // Construtor com parâmetros
    public Group(long id, String name) {
        this.id = id;
        this.name = name;

    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
