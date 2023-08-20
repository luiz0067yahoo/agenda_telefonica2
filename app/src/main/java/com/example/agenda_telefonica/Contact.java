package com.example.agenda_telefonica;
public class Contact {
    private long id;
    private String name;
    private String phone;
    private String gender;
    private String name_group;
    private long id_group;
    private boolean isFavorite;


    public Contact() {
    }

    public Contact(long id,String name, String phone,String gender, long id_category,String name_group, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.id_group = id_group;
        this.name_group = name_group;
        this.isFavorite = isFavorite;
    }

    public void setName(String name) {
        this.name=name;
    }
    public void setGender(String gender) {
        this.gender=gender;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public void setIDGroup(long id_category) {
        this.id_group=id_category;
    }
    public void setNameGroup(String name_category) {
        this.name_group=name_category;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite=isFavorite;
    }



    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public String getGender() {
        return gender;
    }

    public long getIdGroup() {
        return id_group;
    }
    public String getNameGroup() {
        return name_group;
    }
    public long getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
