package com.example.utils;

public class FileSizeFormatter {
    public static String format(long bytes) {
        if (bytes < 1024) return bytes + "B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        double value = bytes / Math.pow(1024, exp);
        return String.format("%.1f%sB", value, unit);
    }
}
