package ru.yandex.praktikum.util;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class ConfigReader {

    private static final Properties properties = new Properties();
    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти файл config.properties");
            }

            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении config.properties", e);
        }
    }

    public static String getBrowserName() {
        return properties.getProperty("browser", "chrome");
    }
}
