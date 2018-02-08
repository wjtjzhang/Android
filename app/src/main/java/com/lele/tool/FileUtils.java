package com.lele.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileUtils {

    private static ArrayList<File> fileList = new ArrayList<File>();

    public static void extractAllFiles(String fileDir) {
        File file = new File(fileDir);
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                extractAllFiles(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {

        String source = "C:\\Projects\\FiveLinkBall\\app\\src\\main\\java";
        String target = "C:\\Projects\\FiveLinkBall\\app\\src\\main\\java_Encrypted";
        String decrypt = "C:\\Projects\\FiveLinkBall\\app\\src\\main\\java_Decrypted";

        String key = "123456";
        FileUtils.encrypt(key, source, target);
        fileList.clear();
        FileUtils.decrypt(key, target, decrypt);
    }

    public static void encrypt(String key, String sourcePath, String targetPath) {
        extractAllFiles(sourcePath);
        for (File sourceFile : fileList) {
            File targetFile = new File(sourceFile.getAbsolutePath().replace(sourcePath, targetPath));
            createFileIfNoExist(targetFile);
            String sourceFileContent = readFrom(sourceFile);
            byte[] content = EncrptUtils.encrypt(sourceFileContent, key);
            writeTo(EncrptUtils.parseByte2HexStr(content), targetFile);
        }
    }

    private static void writeTo(String content, File targetFile) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(targetFile));
            out.write(content);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String readFrom(File sourceFile) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {

            in = new BufferedReader(new FileReader(sourceFile));

            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result =buffer.toString();
        return result.substring(0,result.lastIndexOf("\n"));
    }

    private static void createFileIfNoExist(File targetFile) {
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void decrypt(String key, String sourcePath, String targetPath) {
        extractAllFiles(sourcePath);
        for (File sourceFile : fileList) {
            File targetFile = new File(sourceFile.getAbsolutePath().replace(sourcePath, targetPath));
            createFileIfNoExist(targetFile);
            byte[] content = EncrptUtils.parseHexStr2Byte(readFrom(sourceFile));
            String decryptedContent = new String(EncrptUtils.decrypt(content, key));
            writeTo(decryptedContent,targetFile);
        }
    }

    public static void copyFileWithEncrpt(File file, String sourcePath, String targetPath, int key) {
        File targetFile = new File(file.getAbsolutePath().replace(sourcePath, targetPath));
        BufferedWriter out = null;
        InputStream in = null;
        try {
            targetFile.getParentFile().mkdirs();
            targetFile.createNewFile();
            out = new BufferedWriter(new FileWriter(targetFile));

            in = new FileInputStream(file);
            int tempByte;
            while ((tempByte = in.read()) != -1) {
                tempByte = tempByte + key;
                out.write(tempByte);

            }
            out.flush();
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
