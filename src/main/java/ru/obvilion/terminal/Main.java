package ru.obvilion.terminal;

import ru.obvilion.terminal.gui.Gui;

public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "Cp866");
        System.setProperty("prism.lcdtext", "false");

        Gui.load();
    }
}
