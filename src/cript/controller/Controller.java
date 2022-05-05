package cript.controller;

import cript.model.Base;
import java.io.*;
import java.util.ArrayList;
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

    public void route() throws IOException {
        while (true) {
            Controller.printMenu();
            int command = getCommand();
            if (command == 1) {
                int count = this.base.getTableChar().size() - 1;
                System.out.println("Введите сдвиг, но не больше " + count + " и не меньше -" + count);
                int ch = getCommand();
                if (this.base.setShift(ch)) {
                    System.out.println("Сдвиг установлен");
                } else {
                    System.out.println("Произошла ошибка, сдвиг не установлен"); //TODO Реализовать повтор
                }
            } else if (command == 2) {
                this.encryption(null);
                System.out.println("Зашифровано, посмотрите файл в папке 'resources'");
            } else if (command == 3) {
                this.decryption(null);
                System.out.println("Расшифровано, посмотрите файл в папке 'resources'");
            } else if (command == 4) {
                this.setCurrentFrequency(null);
                System.out.println("Готово");
            } else if (command == 5) {
                this.bruteForce(null);
                System.out.println("Расшифровано, посмотрите файлы в папке 'resources'");
            } else if (command == 0) {

                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public void decryption(String path) {
        if (path == null) {
            path = this.base.getDir() + "encryption.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "decryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, false);
        }
    }

    public void encryption(String path) {
        if (path == null) {
            path = this.base.getDir() + "test.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "encryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, true);
        }
    }

    public void bruteForce(String path){
        if (path == null) {
            path = this.base.getDir() + "encryption.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "decryption";
            this.base.setBruteForceFile(file, uniqueName);
        }
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

}
