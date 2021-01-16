package com.example.gaff.article;

public enum ProductCondition {
    VERY_GOOD(0), GOOD(1), NOT_BAD(2), BAD(3);

    private int condition;

    ProductCondition(int condition) {
        this.condition = condition;
    }
}
