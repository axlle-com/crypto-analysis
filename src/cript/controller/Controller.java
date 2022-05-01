package cript.controller;

import cript.model.Base;

import java.io.*;
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
        System.out.println("4 - Пусто");
        System.out.println("5 - Тест");
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
                System.out.println("Привет");
            } else if (command == 5) {
                this.test();
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
            path = this.base.getDir() + "/resources/encryption.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "/resources/decryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, false);
        }
    }

    public void encryption(String path) {
        if (path == null) {
            path = this.base.getDir() + "/resources/test.txt";
        }
        File file = new File(path);
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "/resources/encryption.txt";//System.currentTimeMillis()
            this.base.converter(file, uniqueName, true);
        }
    }

    public void test() throws IOException {
        File file = new File(this.base.getDir() + "/resources/test.txt");
        if (file.isFile()) {
            String uniqueName = this.base.getDir() + "/resources/test_" + System.currentTimeMillis() + ".txt";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            FileWriter writer = new FileWriter(uniqueName);
            int symbol = bufferedReader.read();
            while (symbol != -1) {
                char ch = (char) symbol;
                ch = Character.toLowerCase(ch);
                int indexOf = this.base.getTableChar().indexOf(ch);
                if (indexOf != -1) {
                    char newChar = this.base.getTableChar().get(indexOf);
                    System.out.print(newChar);
                    writer.append(newChar);
                }
                symbol = bufferedReader.read();
            }
            System.out.println();
            writer.flush();
            writer.close();
            bufferedReader.close();
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
