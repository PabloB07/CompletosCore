package me.blancocl.completoscore.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hex {
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String message(String message) {
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String prefix(String s) {
        return "&6&l" + s;
    }
}