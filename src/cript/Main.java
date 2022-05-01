package cript;

import cript.controller.Controller;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.route();
    }
}
