package com.hiroshi.cimoc.utils;

import com.hiroshi.cimoc.saf.DocumentFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiroshi on 2016/12/4.
 */

public class DocumentUtils {

    public static DocumentFile getOrCreateFile(DocumentFile parent, String displayName) {
        DocumentFile file = parent.findFile(displayName);
        if (file != null) {
            if (file.isFile()) {
                return file;
            }
            return null;
        }
        return parent.createFile(displayName);
    }

    public static DocumentFile findFile(DocumentFile parent, String... filenames) {
        if (parent != null) {
            for (String filename : filenames) {
                parent = parent.findFile(filename);
                if (parent == null) {
                    return null;
                }
            }
        }
        return parent;
    }

    public static int countWithoutSuffix(DocumentFile dir, String suffix) {
        int count = 0;
        if (dir.isDirectory()) {
            for (DocumentFile file : dir.listFiles()) {
                if (file.isFile() && !file.getName().endsWith(suffix)) {
                    ++count;
                }
            }
        }
        return count;
    }

    public static String[] listFilesWithSuffix(DocumentFile dir, String... suffix) {
        List<String> list = new ArrayList<>();
        if (dir.isDirectory()) {
            for (DocumentFile file : dir.listFiles()) {
                if (file.isFile()) {
                    String name = file.getName();
                    for (String str : suffix) {
                        if (name.endsWith(str)) {
                            list.add(name);
                            break;
                        }
                    }
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public static DocumentFile getOrCreateSubDirectory(DocumentFile parent, String displayName) {
        DocumentFile file = parent.findFile(displayName);
        if (file != null) {
            if (file.isDirectory()) {
                return file;
            }
            return null;
        }
        return parent.createDirectory(displayName);
    }

    public static String readLineFromFile(DocumentFile file) {
        InputStream input = null;
        BufferedReader reader = null;
        try {
            input = file.openInputStream();
            if (input != null) {
                reader = new BufferedReader(new InputStreamReader(input));
                return reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(input, reader);
        }
        return null;
    }

    public static char[] readCharFromFile(DocumentFile file, int count) {
        InputStream input = null;
        BufferedReader reader = null;
        try {
            input = file.openInputStream();
            if (input != null) {
                reader = new BufferedReader(new InputStreamReader(input));
                char[] buffer = new char[count];
                if (reader.read(buffer, 0, count) == count) {
                    return buffer;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(input, reader);
        }
        return null;
    }

    public static void writeStringToFile(DocumentFile file, String mode, String data) throws IOException {
        OutputStream output = null;
        BufferedWriter writer = null;
        try {
            output = file.openOutputStream(mode);
            if (output != null) {
                writer = new BufferedWriter(new OutputStreamWriter(output));
                writer.write(data);
                writer.flush();
            } else {
                throw new IOException();
            }
        } finally {
            closeStream(output, writer);
        }
    }

    public static void writeStringToFile(DocumentFile file, String data) throws IOException {
        writeStringToFile(file, "w", data);
    }

    public static void writeBinaryToFile(DocumentFile file, InputStream input) throws IOException {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {
            OutputStream output = file.openOutputStream();

            if (output != null) {
                inputStream = new BufferedInputStream(input, 8192);
                outputStream = new BufferedOutputStream(output, 8192);

                int length;
                byte[] buffer = new byte[8192];
                while ((length = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, length);
                }
                output.flush();
            } else {
                closeStream(input);
                throw new FileNotFoundException();
            }
        } finally {
            closeStream(inputStream, outputStream);
        }
    }

    public static void writeBinaryToFile(DocumentFile src, DocumentFile dst) throws IOException {
        writeBinaryToFile(dst, src.openInputStream());
    }

    private static void closeStream(Closeable... stream) {
        for (Closeable closeable : stream) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
