package com.zt.app.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    private File str2File(String path) throws IOException {
        if (Objects.isNull(path) || path.trim().isEmpty()) {
            throw new NullPointerException();
        }

        File file = new File(path);
        System.out.println(file.getCanonicalPath());
//        if (!file.exists() || !file.isFile()) {
//            throw new IllegalArgumentException("file scheme is not a file");
//        }

        return file;
    }

    public List<String> getSrcFiles(String path) {
        File file = null;
        try {
            file = str2File(path);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }

        List<String> paths = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                paths.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    public List<String> transJava2Class(List<String> paths, String srcDir, String targetDir) {
        if (paths.isEmpty()) {
            return new LinkedList<>();
        }

        List<String> targetFiles = new LinkedList<>();
        for (String path : paths) {
            if (path.startsWith(srcDir)) {
                String targetFile = path.replace(srcDir, targetDir).replace(".java", ".class");
                targetFiles.add(targetFile);
            }
        }
        return targetFiles;
    }

    public List<File> srcPath2TargetFile(List<String> paths, String srcDir, String targetDir) {
        if (paths.isEmpty()) {
            return new LinkedList<>();
        }

        List<File> targetFiles = new LinkedList<>();
        for (String path : paths) {
            if (path.startsWith(srcDir)) {
                String targetFile = path.replace(srcDir, targetDir).replace(".java", ".class");
                System.out.println(String.format("src file: %s, target file: %s", path, targetFile));
                try {
                    targetFiles.add(str2File(targetFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return targetFiles;
    }
}
