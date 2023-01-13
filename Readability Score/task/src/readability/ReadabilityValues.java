package readability;

public class ReadabilityValues {

    private String fullTextFromFile;
    private String[] wordCount;
    private String[] sentences;
    private int numChar;
    private double ariScore;
    private double fkrScore;
    private double smogScore;
    private double cliScore;
    private int syllables;
    private int polySyllables;
    private int numLetters;

    public ReadabilityValues(String fullTextFromFile) {
        this.fullTextFromFile = fullTextFromFile;
        this.sentences = this.fullTextFromFile.split("[\\.!?]");
        this.wordCount = this.fullTextFromFile.split("[\\.!?]? ");
        setNumChar();
        setNumLetters();
        setAriScore();
        countSyllables();
        setFkrScore();
        setSmogScore();
        setCli();
    }

    private void countSyllables(){
        int numSyllables = 0;
        int polySyllables = 0;
        for (int i = 0; i < wordCount.length; i++) {
            int vowels = 0;
            for(int j = 0; j < wordCount[i].length(); j++) {
                if(wordCount[i].substring(j, j + 1).matches("[aeiouyAEIOUY]")) {
                    vowels += 1;
                    if(wordCount[i].length() == 1 ){
                        break;
                    }
                    try{
                        if(wordCount[i].substring(j - 1, j).matches("[aeioyAEIOUY]")){
                            vowels -= 1;
                        }
                    } catch (Exception e){

                    }
                    if(j == wordCount[i].length() - 1) {
                        if (wordCount[i].substring(j).matches("e")) {
                            vowels -= 1;
                        } else if(wordCount[i].substring(j - 1).matches("e[\\.!?]")) {
                            vowels -= 1;
                        }
                        if (vowels == 0) {
                            vowels = 1;
                        }
                    }
                }
            }
            numSyllables += vowels;
            if (vowels > 2) {
                polySyllables += 1;
            }
        }
        this.syllables = numSyllables;
        this.polySyllables = polySyllables;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolySyllables() {
        return polySyllables;
    }

    public String[] getWordCount() {
        return wordCount;
    }

    public String[] getSentences() {
        return sentences;
    }

    public int getNumChar() {
        return numChar;
    }

    private void setNumChar(){
        for (int i = 0; i < this.fullTextFromFile.length(); i++) {
            if(this.fullTextFromFile.substring(i, i +1).matches("\\S")) {
                this.numChar += 1;
            }
        }
    }

    private void setNumLetters(){
        for (int i = 0; i < this.fullTextFromFile.length(); i++) {
            if(this.fullTextFromFile.substring(i, i +1).matches("[a-zA-Z]")) {
                this.numLetters += 1;
            }
        }
    }

    private void setAriScore(){
        this.ariScore = 4.71 * ((double) this.numChar / this.wordCount.length) + (0.5 * ((double)this.wordCount.length / this.sentences.length)) - 21.43;
    }

    private void setFkrScore(){
        this.fkrScore = 0.39 * ((double)this.wordCount.length / this.sentences.length) + 11.8 * ((double)this.syllables / this.wordCount.length) - 15.59;
    }

    private void setSmogScore(){
        double valForsqrt = this.polySyllables * (30 / (double)this.sentences.length);
        this.smogScore = 1.043 * Math.sqrt(valForsqrt) + 3.1291;
    }

    private void setCli(){
        double avgLettersPer100 = ((double)this.numLetters / this.wordCount.length) * 100;
        double avgSentencesPer100 = ((double)this.sentences.length / this.wordCount.length) * 100;
        this.cliScore = (0.0588 * avgLettersPer100) - (0.296 * avgSentencesPer100) - 15.8;
    }

    public void printUserResponse(String userChoice) {
        switch(userChoice) {
            case "ARI" -> printAri();
            case "FK" -> printFkrScore();
            case "SMOG" -> printSmogScore();
            case "CL" -> printClScore();
            case "all" -> {
                printAri();
                printFkrScore();
                printSmogScore();
                printClScore();
            }
            default -> System.out.println("Invalid input");
        }
        printAvgScore();
    }

    private void printAri(){
        System.out.printf("Automated Readability Index: %.2f ",this.ariScore);
        printAgeRating(this.ariScore);
    }

    private void printFkrScore(){
        System.out.printf("Flesch–Kincaid readability tests: %.2f ", this.fkrScore);
        printAgeRating(this.fkrScore);
    }

    private void printSmogScore(){
        System.out.printf("Simple Measure of Gobbledygook: %.2f ", this.smogScore);
        printAgeRating(this.smogScore);
    }

    private void printClScore(){
        System.out.printf("Coleman–Liau index: %.2f ", this.cliScore);
        printAgeRating(this.cliScore);
    }

    private void printAvgScore(){
        double avgScore = (this.ariScore + this.cliScore + this.smogScore + this.fkrScore) / 4;
        System.out.printf("This text should be understood in average by %.2f-year-olds\n", avgScore);
    }

    private void printAgeRating(double score){
        int ageRating = (int)Math.ceil(score);

        switch(ageRating) {
            case 1 -> System.out.println("(about 6-year-olds).");
            case 2 -> System.out.println("(about 7-year-olds).");
            case 3 -> System.out.println("(about 8-year-olds).");
            case 4 -> System.out.println("(about 9-year-olds).");
            case 5 -> System.out.println("(about 10-year-olds).");
            case 6 -> System.out.println("(about 11-year-olds).");
            case 7 -> System.out.println("(about 12-year-olds).");
            case 8 -> System.out.println("(about 13-year-olds).");
            case 9 -> System.out.println("(about 14-year-olds).");
            case 10 -> System.out.println("(about 15-year-olds).");
            case 11 -> System.out.println("(about 16-year-olds).");
            case 12 -> System.out.println("(about 17-year-olds).");
            case 13 -> System.out.println("(about 18-year-olds).");
            case 14 -> System.out.println("(about 22-year-olds).");
            case 15 -> System.out.println("(about 22-year-olds)");
            default -> System.out.println("Invalid score");
        }
    }
}
