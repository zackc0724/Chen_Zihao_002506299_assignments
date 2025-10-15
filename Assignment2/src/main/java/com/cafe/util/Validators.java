package com.cafe.util;

import javax.swing.*;

public final class Validators {
    private Validators() {}

    public static boolean requireText(JTextField field, String label) {
        if (field.getText() == null || field.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, label + " is required.", "Validation", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            return false;
        }
        return true;
    }

    public static Integer parseInt(JTextField field, String label) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, label + " must be an integer.", "Validation", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            return null;
        }
    }

    public static Long parseLong(JTextField field, String label) {
        try {
            return Long.parseLong(field.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, label + " must be a whole number.", "Validation", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            return null;
        }
    }

    public static Double parseDouble(JTextField field, String label) {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, label + " must be a number.", "Validation", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            return null;
        }
    }
}
