package me.verwelius.bot.utils;

import java.io.*;

public class FileUtils {

    public static Boolean createIfNotExists(File file) {
        if (file.exists()) return true;

        // Saving default
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(file.getName()); OutputStream os = new FileOutputStream(file)) {
            byte[] buffer = is.readAllBytes();
            os.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
