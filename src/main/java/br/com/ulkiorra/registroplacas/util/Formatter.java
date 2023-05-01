package br.com.ulkiorra.registroplacas.util;

import javafx.scene.control.TextFormatter;

public class Formatter {
    public static TextFormatter<String>  noNumberFormatter(){
        return new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("\\D*")) {
                return change;
            }
            return null;
        });
    }

    public static TextFormatter<String> noSpaceFormatter(){
        return new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("^\\S+$")) {
                return change;
            }
            return null;
        });
    }

    public static TextFormatter<String>  noLettersFormatter(){
        return new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("^[0-9.]+$")) {
                return change;
            }
            return null;
        });
    }

    public static TextFormatter<String>  PlacasFormatter(){
        return new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("^[a-zA-Z0-9]{1,7}$")) {
                return change;
            }
            return null;
        });
    }
}
