import java.util.Scanner;

public class UI {

    private Scanner scan = new Scanner(System.in);

    public void startUI() {
        System.out.println("Write a text:");
         String input = scan.nextLine();
        GuessingLanguage gussingtext = new GuessingLanguage(input);
        System.out.println(gussingtext.text());
    }
}
