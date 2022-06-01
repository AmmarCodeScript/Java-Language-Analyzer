import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GuessingLanguage {

    private HashMap<LangLabel, Double> guessLanguageSort1 = new HashMap<>();
    private HashMap<LangLabel, Double> guessLanguageSort2 = new HashMap<>();
    private HashMap<LangLabel, Double> guessLanguageSort3 = new HashMap<>();
    private HashMap<LangLabel, Double> guessLanguageSortAverage = new HashMap<>();
    private HashMap<Double, LangLabel> guessLanguageSorted = new HashMap<>();

    private HashMap<String, Integer> keysFile = new HashMap<>();
    private HashMap<String, Integer> keysInput = new HashMap<>();
    private ArrayList<Double> array = new ArrayList<>();
    private String text = "";

    public GuessingLanguage(String text) {
        this.text = text;
        for (LangLabel label : LangLabel.values()) {
            String content = FileUtils.readTextFile("assets/lang-samples/" + label + ".txt");
            Language textFromInput = new Language(text, label);
            Language textFromFile = new Language(content, label);

            guessTextInput(textFromInput, textFromFile);
            guessTextInputPart2(textFromInput, textFromFile);
            guessTextInputPart3(textFromInput, textFromFile);
        }
        guessTextInputAverage();
    }

    private double lengthOfLetters(HashMap<String, Integer> keysFile) {
        double total = 0;
        for (String sorted : keysFile.keySet()) {
            total += (double) keysFile.get(sorted);
        }
        return total;
    }

    // Here the function makes subtraction of letters different.
    private double subtractionOfLetters(HashMap<String, Integer> keysFile, HashMap<String, Integer> keysInput) {
        double letterTot = 0;
        double inputNum = 0;
        double fileNum = 0;

        double totInput = lengthOfLetters(keysInput);
        double totFile = lengthOfLetters(keysFile);

        for (String sorted : keysInput.keySet()) {
            inputNum = (keysInput.get(sorted) / totInput);
            if (keysFile.getOrDefault(sorted, 0) != 0) {
                fileNum = (keysFile.get(sorted) / totFile);
            }
            double total = (Math.abs(inputNum - fileNum));
            fileNum = 0;
            letterTot += total;
        }
        return letterTot;
    }

    // Here the function that divides the tree letters one after the other for the entered text
    public void treeLetters(Language label, HashMap<String, Integer> keys) {
        for (int i = 0; i < label.getContent().length(); i++) {
            if (i + 2 >= label.getContent().length())
                break;
            String key = label.getContent().substring(i, i + 3);
            keys.put(key, keys.getOrDefault(key, 0) + 1);
        }
    }

    // Here the function that divides the entered text into letters
    public void oneLetters(Language label, HashMap<String, Integer> keys) {
        for (int i = 0; i <= label.getContent().length() - 1; i++) {
            String key = label.getContent().substring(i, i + 1);
            if (i >= label.getContent().length()) {
                break;
            }
            keys.put(key, keys.getOrDefault(key, 0) + 1);
        }
    }

    // Here the function that divides first letter of every word of the entered text
    public void firstLetter(Language label, HashMap<String, Integer> keys) {
        for (String words : label.getOneWord().values()) {
            String key = words.charAt(0) + "";
            keys.put(key, keys.getOrDefault(key, 0) + 1);
        }
    }

    //This function guessing and comparing every letter from text input with text file
    public void guessTextInput(Language textFromInput, Language textFromFile) {
        oneLetters(textFromInput, keysInput);
        oneLetters(textFromFile, keysFile);
        double difRes = subtractionOfLetters(keysFile, keysInput);
        guessLanguageSort1.put(textFromFile.label, difRes);
        keysInput.clear();
        keysFile.clear();
    }

    //This function guessing and comparing every tree letters from text input with text file
    public void guessTextInputPart2(Language textFromInput, Language textFromFile) {
        treeLetters(textFromFile, keysFile);
        treeLetters(textFromInput, keysInput);
        double difRes = subtractionOfLetters(keysFile, keysInput);
        guessLanguageSort2.put(textFromFile.label, difRes);
        keysInput.clear();
        keysFile.clear();

    }

    //This function guessing and comparing first word letter from text input with text file
    public void guessTextInputPart3(Language textFromInput, Language textFromFile) {
        firstLetter(textFromInput, keysInput);
        firstLetter(textFromFile, keysFile);
        double difRes = subtractionOfLetters(keysFile, keysInput);
        guessLanguageSort3.put(textFromFile.label, difRes);
        keysInput.clear();
        keysFile.clear();
    }

    //This function makes average of all guessing and comparing functions, and sorting them
    public void guessTextInputAverage() {
        for (LangLabel language : guessLanguageSort1.keySet()) {
            double total = (guessLanguageSort1.get(language) + guessLanguageSort2.get(language) + guessLanguageSort3.get(language)) / 3;
            guessLanguageSortAverage.put(language, total);
            guessLanguageSorted.put(total, language);
            array.add(total);
        }
        Collections.sort(array);
    }

    //This function prints result
    public String text() {
        StringBuilder result = new StringBuilder();
        result.append("Language \t  Analysis 1 \t Analysis 2 \t Analysis 3 \t Combined \t Ranking(%) \n");
        int i = 0;
        for (double sorted : array) {
            i += 1;
            LangLabel languageInput = guessLanguageSorted.get(sorted);
            String sort = String.format("\t%s  \t  %.2f \t\t\t %.2f \t\t\t %.2f \t\t\t %.2f \t\t %s. (%.2f %%)  \n ", languageInput, guessLanguageSort1.get(languageInput), guessLanguageSort2.get(languageInput), guessLanguageSort3.get(languageInput), guessLanguageSortAverage.get(languageInput), i, (-guessLanguageSortAverage.get(languageInput) + 1) * 100);
            result.append(sort);
            if (i >= 5) {
                break;
            }
        }
        result.append("I guess you write in " + guessLanguageSorted.get(array.get(0)).getName());
        return result.toString();
    }
}
