package com.example.paasswordmanager.managers;

import android.content.Context;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonFileManager {

    private final Gson gson;

    public JsonFileManager() {
        this.gson = new Gson();
    }

    public <T> void saveToFile(Context context, String fileName, T data) {
        try {
            String json = gson.toJson(data);
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T loadFromFile(Context context, String fileName, Type type) {
        try {
            File file = new File(context.getFilesDir(), fileName);
            if (!file.exists()) return null;
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String json = new String(data, "UTF-8");
            return gson.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
