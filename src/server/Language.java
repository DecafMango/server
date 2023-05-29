package server;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {

    public static String getProperty(String key, String language) {
        switch (language) {
            case "english":
                return ResourceBundle.getBundle("resources_US", new Locale("US", "US")).getString(key);
            case "íslenskur":
                return ResourceBundle.getBundle("resources_IS", new Locale("IS", "IS")).getString(key);
            case "shqiptare":
                return ResourceBundle.getBundle("resources_AL", new Locale("AL", "AL")).getString(key);
            case "русский":
                return ResourceBundle.getBundle("resources_RU", new Locale("RU", "RU")).getString(key);
        }
        return null;
    }
}
