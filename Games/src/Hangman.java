import java.util.Random;
import java.util.Scanner;

public class Hangman {

    private static final String[] HARD = new String[]{"table", "star", "root", "flat", "turtle", "fish", "goat", "rocket",
            "dragon", "power", "source", "program", "wheel", "candle", "dog", "cat", "branch"};

    private static final String[] EASY = new String[]{"dragonborn", "monosaccharide", "disposable", "environment",
            "implementation", "disorientation", "information", "carbohydrate", "disrespectful", "dominant"};

    private static boolean IS_HARD;
    private static int GUESS;
    private static String WORD;

    private static void HardSelected(){
        IS_HARD = true;
    }
    private static void SetDifficulty(){
        String[] mode;
        Random rand = new Random();
        if (IS_HARD) mode = HARD;
        else mode = EASY;

        int RandInd = rand.nextInt(mode.length);
        WORD = mode[RandInd];
        GUESS = WORD.length();
    }
    private static char[] CreateCharArray(){
        char[] chars = new char[WORD.length()];
        for (int i = 0; i < WORD.length(); i++){
            chars[i] = WORD.charAt(i);
        }
        return chars;
    }
    private static String GuessWord(char[] word){
        String text = "";
        for (char e : word){
            text += e;
        }
        return text;
    }
    private static void DifficultySelect(){
        String difficulty;
        Scanner dif = new Scanner(System.in);

        while (true) {
            System.out.print("Please select your difficulty (type h or e):");
            difficulty = dif.next();
            if (difficulty.equals("h")){
                HardSelected();
                SetDifficulty();
                break;
            }
            else if (difficulty.equals("e")){
                if (IS_HARD) IS_HARD = false;
                SetDifficulty();
                break;
            }
        }
    }
    private static void Game(){

        DifficultySelect();

        char[] word = new char[WORD.length()];
        for (int i = 0; i < word.length; i++){
            word[i] = '*';
        }

        Scanner input = new Scanner(System.in);
        char character;
        boolean over = false;

        while (true){

            boolean changed = false;
            System.out.println("\n" + "Guesses left: " + GUESS);
            System.out.println("The word is: " + GuessWord(word));
            System.out.print("Please enter single character: ");
            character = input.next().charAt(0);

            for (int i = 0; i < WORD.length(); i++){

                if (character == CreateCharArray()[i]){
                    word[i] = character;
                    changed = true;
                }
            }
            if (!changed) {
                System.out.println("The character '" + character + "' is not in the word!");
                GUESS -= 1;
            }
            if (GUESS == 0 && !(GuessWord(word).equals(WORD))){
                System.out.println("The word was: " + WORD);
                System.out.println("You lost!");
                over = true;
            }
            else if (GuessWord(word).equals(WORD)){
                System.out.println("The word was: " + WORD);
                System.out.println("You win!");
                over = true;
            }
            if (over){

                System.out.println("Would you like to play again? (y/n): ");
                character = input.next().charAt(0);
                if (character == 'y'){
                    Game();
                }
                else{
                    System.exit(1);
                }
            }
        }
    }
    public static void main(String[] args){
        Game();
    }
}