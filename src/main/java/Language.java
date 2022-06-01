import java.util.HashMap;

public class Language {
    String content;
    LangLabel label;


    private HashMap<String, Integer> charCount = new HashMap<String,Integer>();
    private HashMap<String, Double> charDistribution = new HashMap<String,Double>();
    private HashMap<Integer,String > StringOneWord = new HashMap<Integer,String>();

    public Language(String content, LangLabel label) {
        this.content = content;
        this.label = label;

        this.content = this.content.replaceAll("[^\\p{L}]", "").toLowerCase();

        calcCharDistribution();
        textToWord(content);
    }

    public String getContent() {
        return content;
    }

    public HashMap<Integer,String > getOneWord() {
        return StringOneWord;
    }

    private void calcCharDistribution() {

        for (int i = 0; i < content.length(); i++) {
            String letter = Character.toString(content.charAt(i));
            if (!charCount.containsKey(letter)) {
                charCount.put(letter, 0);
            }
            charCount.put(letter, charCount.get(letter)+1);
        }

        for (String letter: charCount.keySet()) {
            Double distr = (double) charCount.getOrDefault(letter, 0) / content.length()  ;
            charDistribution.put(letter, distr);
        }
    }

    //Here the function splitting word from text.
    private void textToWord(String textInput){
        String text = textInput;
        text = text.replaceAll("[^a-zA-ZåäöÅÄÖ ]", "").toLowerCase();
        text = text.replaceAll("[^\\p{L} ]", "");
        text = text.replaceAll("\\s+", " ");
        String[] words = text.split(" ");
        int i=0;
        for (String word: words) {
            StringOneWord.put( i,word+"");
            i+=1;
        }
    }

}