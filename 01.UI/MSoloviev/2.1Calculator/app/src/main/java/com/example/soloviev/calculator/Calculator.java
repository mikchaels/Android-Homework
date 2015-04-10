package com.example.soloviev.calculator;


import android.app.AlertDialog;

public class Calculator {
    private double operand_first;
    private double operand_second;
    private static final double EPS = 10e-15;

    public double summ() {
        return operand_first + operand_second;
    }

    public double subtraction() {
        return operand_first - operand_second;
    }

    public double division() {

        if (Math.abs(operand_second) > EPS) {
            return operand_first / operand_second;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double multiply() {
        return operand_first * operand_second;
    }

    Calculator(double operand_first, double operand_second) {
        this.operand_first = operand_first;
        this.operand_second = operand_second;

    }


}