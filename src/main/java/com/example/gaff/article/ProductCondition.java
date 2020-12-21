package com.example.gaff.article;

public enum ProductCondition {
    VERY_GOOD(4), GOOD(3), NOT_BAD(2), BAD(1);

    private int condition;

    ProductCondition(int condition) {
        this.condition = condition;
    }
}
