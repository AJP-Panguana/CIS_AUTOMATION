package mz.co.maximo.cis.core.driver;

public enum BrowserType {
    CHROME, FIREFOX, EDGE;

    public static BrowserType from(String name) {
        try {
            return valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Unsupported browser '" + name + "'. Use chrome, firefox or edge.");
        }
    }
}
