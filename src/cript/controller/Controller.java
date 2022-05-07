package cript.controller;

import cript.model.Base;

import java.io.File;
import java.util.Scanner;

public class Controller {

    private final Base base;

    public Controller() {
        this.base = new Base("Ru");
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("===============================================");
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Установить сдвиг");
        System.out.println("2 - Зашифровать");
        System.out.println("3 - Расшифровать");
        System.out.println("4 - Частотный анализ");
        System.out.println("5 - Взлом Брут Форсом");
        System.out.println("0 - Выход");
    }

    public void route() {
        while (true) {
            Controller.printMenu();
            int command = getCommand();
            String stringCommand = null;
            if (command == 1) {
                System.out.println("Введите сдвиг");
                int ch = getCommand();
                this.base.setShift(ch);
                System.out.println("Сдвиг установлен");
            } else if (command == 2) {
                System.out.println("Введите адрес исходного текста относительно папки 'resources' -> `test.txt`");
                stringCommand = getStringCommand();
                if (this.encryption(stringCommand)) {
                    System.out.println("Зашифровано, посмотрите файл в папке 'resources'");
                } else {
                    System.out.println("Произошла ошибка, файл не найден");
                }
            } else if (command == 3) {
                System.out.println("Введите адрес зашифрованного текста относительно папки 'resources' -> `encryption.txt`");
                stringCommand = getStringCommand();
                if (this.decryption(stringCommand)) {
                    System.out.println("Расшифровано, посмотрите файл в папке 'resources'");
                } else {
                    System.out.println("Произошла ошибка, файл не найден");
                }
            } else if (command == 4) {
                this.setCurrentFrequency(null);
                System.out.println("Готово");
            } else if (command == 5) {
                System.out.println("Введите адрес зашифрованного текста относительно папки 'resources' -> `encryption.txt`");
                stringCommand = getStringCommand();
                if (this.bruteForce(stringCommand)) {
                    System.out.println("Расшифровано, посмотрите файл в папке 'resources'");
                } else {
                    System.out.println("Произошла ошибка, файл не найден");
                }
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public boolean decryption(String path) {
        if (path == null) {
            path = this.base.getDir() + "encryption.txt";
        }
        File file = new File(this.base.getDir() + "/" + path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "decryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, false);
            return true;
        }
        return false;
    }

    public boolean encryption(String path) {
        if (path == null) {
            path = this.base.getDir() + "test.txt";
        }
        File file = new File(this.base.getDir() + "/" + path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "encryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, true);
            return true;
        }
        return false;
    }

    public boolean bruteForce(String path) {
        if (path == null) {
            path = this.base.getDir() + "encryption.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "decryption";
            this.base.setBruteForceFile(file, uniqueName);
            return true;
        }
        return false;
    }

    public void setCurrentFrequency(String path) {
        if (path == null) {
            path = this.base.getDir() + "encryption.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            this.base.setCurrentFrequencyRu(file).compare();
        }
    }

    private int getCommand() {
        Scanner scanner = new Scanner(System.in);
        int command = Integer.MAX_VALUE;
        try {
            command = scanner.nextInt();
        } catch (Exception e) {
            scanner.next();
        }
        return command;
    }

    private String getStringCommand() {
        Scanner scanner = new Scanner(System.in);
        String command = null;
        try {
            command = scanner.nextLine();
        } catch (Exception e) {
            scanner.next();
        }
        return command;
    }

}
