package readability;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String filePath = args[0];
        String textFromFile = "";
        try {
            textFromFile = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println("The text is:");
            System.out.println(textFromFile);

        }catch(Exception e) {
            e.getMessage();
        }
        ReadabilityValues newReading = new ReadabilityValues(textFromFile);

        System.out.printf("Words: %d\n", newReading.getWordCount().length);
        System.out.printf("Sentences: %d\n", newReading.getSentences().length);
        System.out.printf("Characters: %d\n", newReading.getNumChar());
        System.out.printf("Syllables: %d\n", newReading.getSyllables());
        System.out.printf("Polysyllables: %d\n", newReading.getPolySyllables());

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        newReading.printUserResponse(userInput);
    }
}
