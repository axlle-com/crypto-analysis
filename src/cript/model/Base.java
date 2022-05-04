package cript.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Base {
    private final ArrayList<Character> tableCharRu;
    private final ArrayList<Character> tableCharEn;
    public HashMap<Character, Double> standardFrequencyRu;
    public HashMap<Character, Double> standardFrequencyEn;
    public HashMap<Character, Double> currentFrequencyRu;
    public HashMap<Character, Double> currentFrequencyEn;
    private int shift;
    private String mod;
    private String dir;

    public Base(String mod) {
        this.standardFrequencyRu = new HashMap<>();
        this.standardFrequencyEn = new HashMap<>();
        this.currentFrequencyRu = new HashMap<>();
        this.currentFrequencyEn = new HashMap<>();
        this.mod = mod;
        this.dir = System.getProperty("user.dir") + "/resources/";
        tableCharRu = new ArrayList<>();
        tableCharEn = new ArrayList<>();
        this.setTableChar();
        this.setStandardFrequencyRu();
    }

    public ArrayList<Character> getTableChar() {
        if (this.mod.equals("Ru")) {
            return tableCharRu;
        }
        return tableCharEn;
    }

    //https://dpva.ru/Guide/GuideUnitsAlphabets/Alphabets/FrequencyRuLetters/
    public void setStandardFrequencyRu() {
        standardFrequencyRu.put('о', 10.983);
        standardFrequencyRu.put('е', 8.483);
        standardFrequencyRu.put('а', 7.998);
        standardFrequencyRu.put('и', 7.367);
        standardFrequencyRu.put('н', 6.7);
        standardFrequencyRu.put('т', 6.318);
        standardFrequencyRu.put('с', 5.473);
        standardFrequencyRu.put('р', 4.746);
        standardFrequencyRu.put('в', 4.533);
        standardFrequencyRu.put('л', 4.343);
        standardFrequencyRu.put('к', 3.486);
        standardFrequencyRu.put('м', 3.203);
        standardFrequencyRu.put('д', 2.977);
        standardFrequencyRu.put('п', 2.804);
        standardFrequencyRu.put('у', 2.615);
        standardFrequencyRu.put('я', 2.001);
        standardFrequencyRu.put('ь', 1.735);
        standardFrequencyRu.put('г', 1.687);
        standardFrequencyRu.put('з', 1.641);
        standardFrequencyRu.put('б', 1.592);
        standardFrequencyRu.put('ч', 1.45);
        standardFrequencyRu.put('й', 1.208);
        standardFrequencyRu.put('х', 0.966);
        standardFrequencyRu.put('ж', 0.94);
        standardFrequencyRu.put('ш', 0.718);
        standardFrequencyRu.put('ю', 0.638);
        standardFrequencyRu.put('ц', 0.486);
        standardFrequencyRu.put('щ', 0.361);
        standardFrequencyRu.put('э', 0.331);
        standardFrequencyRu.put('ф', 0.267);
        standardFrequencyRu.put('ъ', 0.037);
        standardFrequencyRu.put('ё', 0.013);
    }

    public HashMap<Character, Double> getStandardFrequencyRu() {
        return standardFrequencyRu;
    }

    public HashMap<Character, Double> getCurrentFrequencyRu() {
        return currentFrequencyRu;
    }

    public void setTableChar() {
        Collections.addAll(tableCharRu,
                'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р',
                'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '”', ':', ';', '-', '!', '?', ' ');
        Collections.addAll(tableCharEn, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '.', ',', '”', ':', ';', '-', '!', '?', ' ');
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

    public void setCurrentFrequencyRu(File in, String to, boolean isEncode) {
        int cnt = 0;
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(in));
                FileWriter writer = new FileWriter(to);
        ) {
            int symbol = bufferedReader.read();
            while (symbol != -1) {
                char ch = (char) symbol;
                if (this.standardFrequencyRu.get(ch) != null) {
                    if (this.currentFrequencyRu.get(ch) != null) {
                        Double value = 1.0;
                        value += this.currentFrequencyRu.get(ch);
                        this.currentFrequencyRu.put(ch, value);
                    } else {
                        this.currentFrequencyRu.put(ch, 1.0);
                    }
                    cnt++;
                }
                symbol = bufferedReader.read();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (this.currentFrequencyRu.size() > 0) {
            HashMap<Character, Double> temp = this.currentFrequencyRu;
            for (Map.Entry<Character, Double> entry : temp.entrySet()) {
                Character key = entry.getKey();
                Double value = entry.getValue();
                value = (value / cnt) * 100;
                this.currentFrequencyRu.put(key, value);
            }
        }
    }
}
