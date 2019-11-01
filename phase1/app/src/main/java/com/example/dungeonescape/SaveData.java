package com.example.dungeonescape;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SaveData implements Serializable {
    private FileOutputStream outputStream;

    public static void save(Serializable data, File f) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(f.toPath()))) {
            oos.writeObject(data);

        }
    }

    public static Object load(File f) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(f.toPath()))) {
            return ois.readObject();
        }
    }
}