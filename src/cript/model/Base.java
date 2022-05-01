package cript.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Base {
    private ArrayList<Character> tableCharRu;
    private ArrayList<Character> tableCharEn;
    private int shift;
    private String mod;
    private String dir;

    public Base(String mod) {
        this.mod = mod;
        this.dir = System.getProperty("user.dir");
        tableCharRu = new ArrayList<>();
        tableCharEn = new ArrayList<>();
        Collections.addAll(tableCharRu,
                'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р',
                'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '”', ':', ';', '-', '!', '?', ' ');
        Collections.addAll(tableCharEn, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '.', ',', '”', ':', ';', '-', '!', '?', ' ');
    }

    public ArrayList<Character> getTableChar() {
        if (this.mod.equals("Ru")) {
            return tableCharRu;
        }
        return tableCharEn;
    }

    public void setTableChar(ArrayList<Character> tableChar) {
        if (this.mod.equals("Ru")) {
            this.tableCharRu = tableChar;
        } else {
            this.tableCharEn = tableChar;
        }
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getShift() {
        return shift;
    }

    public boolean setShift(int shift) {
        int shiftLimit = 0;
        if (this.mod.equals("Ru")) {
            shiftLimit = this.tableCharRu.size() - 1;
        } else {
            shiftLimit = this.tableCharEn.size() - 1;
        }
        if (Math.abs(shift) > shiftLimit) {
            return false;
        }
        this.shift = shift;
        return true;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    private Character getCharacterForDecryption(Character ch) {
        ch = Character.toLowerCase(ch);
        int indexOf = this.getTableChar().indexOf(ch);
        if (indexOf == -1) {
            return null;
        }
        int maxIndex = this.getTableChar().size() - 1;
        int newIndex = indexOf - this.getShift();
        if (newIndex < 0) {
            newIndex = maxIndex - Math.abs(newIndex) + 1;
        }
        if (newIndex > maxIndex) {
            newIndex = newIndex - maxIndex;
        }
        return this.getTableChar().get(newIndex);
    }

    private Character getCharacterForEncryption(Character ch) {
        ch = Character.toLowerCase(ch);
        int indexOf = this.getTableChar().indexOf(ch);
        if (indexOf == -1) {
            return null;
        }
        int maxIndex = this.getTableChar().size() - 1;
        int newIndex = indexOf + this.getShift();
        if (newIndex > maxIndex) {
            newIndex = newIndex - maxIndex - 1;
        }
        if (newIndex < 0) {
            newIndex = maxIndex - Math.abs(newIndex);
        }
        return this.getTableChar().get(newIndex);
    }

    public void converter(File in, String to, boolean isEncode) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(in));
                FileWriter writer = new FileWriter(to);
        ) {
            int symbol = bufferedReader.read();
            while (symbol != -1) {
                char ch = (char) symbol;
                Character newCh = isEncode ? this.getCharacterForEncryption(ch) : this.getCharacterForDecryption(ch);
                if (newCh != null) {
                    writer.append(newCh);
                }
                symbol = bufferedReader.read();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
