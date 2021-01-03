package com.example.gaff.category;

public enum CategoryType {

    CLOTHES("clothes"),
    CHILDREN_PRODUCTS("children products"),
    BEAUTY("beauty"),
    HOUSEHOLD_EQUIPMENT("household equipment"),
    FOOD("food"),
    OTHER("other");

    private String typeOfCategory;

    CategoryType(String typeOfCategory) {
        this.typeOfCategory = typeOfCategory;
    }
}
