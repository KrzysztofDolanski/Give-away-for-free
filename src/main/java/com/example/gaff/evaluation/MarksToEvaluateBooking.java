package com.example.gaff.evaluation;

public enum MarksToEvaluateBooking {

    VERY_PLEASED(3), GLAD(2), NOT_SATISFIED(1);

    private int condition;

    MarksToEvaluateBooking(int condition) {
        this.condition = condition;
    }
}
