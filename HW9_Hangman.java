
import java.util.*;
import java.io.*;

public class HW9_Hangman {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Choose difficulty level (exit the game: -1, easy: 0, medium: 1, hard: 2): ");
        String level_diff = in.next();
        level(level_diff);
    }
    
    // This method will pass the appropriate .txt file depending on the level selected.
    public static void level(String level_diff) {
        Scanner in = new Scanner(System.in);
        /*
        The try / catch inside the while will continue running if the user enters
        a letter or a number greater than 2. This will prevent the program from
        crashing from an invalid input. If 0, 1, or 2 is inputted then the processFile
        method will be called passing along the appropriate .txt file.
         */
        while (true) {
            String temp;
            try {
                int level = Integer.valueOf(level_diff);
                if (level == -1) {
                    System.out.println("Exiting....Bye");
                    System.exit(0);
                } else if (level == 0) {
                    temp = "medium_1000.txt";
                    processFile(temp, level);
                    break;
                } else if (level == 1) {
                    temp = "long.txt";
                    processFile(temp, level);
                    break;
                } else if (level == 2) {
                    temp = "medium_2500.txt";
                    processFile(temp, level);
                    break;
                } else {
                    System.out.print("Choose difficulty level (exit the game: -1, easy: 0, medium: 1, hard: 2): ");
                    level_diff = in.next();
                    level(level_diff);
                } 
            } catch (Exception e) {
                System.out.print("Choose difficulty level (exit the game: -1, easy: 0, medium: 1, hard: 2): ");
                level_diff = in.next();
            }
        }
    }
    
    // Method to process .txt file and store words into an arrayList
    public static void processFile(String temp, int level) {
        Scanner inFile;
        File temp2 = new File(temp);
        try {
            inFile = new Scanner(temp2);
        } catch (Exception e) {
            System.out.println("Could not open file: ");
            return;
        }
        // Create arraylist to store strings obtained from text file
        ArrayList<String> words = new ArrayList<>();
        while (inFile.hasNextLine()) {
            String lineStr = inFile.nextLine();
            words.add(lineStr);  
        }
        getWord(words, level);
    }
    
    // Random word generator method
    public static void getWord(ArrayList<String> words, int level) {
        Random r = new Random();
        int mx = words.size(); //Range of mx is the length of the words arraylist
        int N = r.nextInt(mx); //Generates a random number
        for (int i = 1; i <= 1; i++) {
            String getWord = words.get(N);
            play(getWord, words, level);
        }
    }
    
    // Main play method
    public static void play(String getWord, ArrayList<String> words, int level) {
        Scanner in = new Scanner(System.in);
        int badCount = 0;   // Counter to keep track of bad guesses
        int totalCount = 0; // Counter to keep track of total games played

        // Initial print sequence
        //System.out.println(getWord); // Uncomment to see random word
        System.out.println("Level: " + level);
        System.out.println("Tried: ");     
        for (int i = 0; i < getWord.length(); i++) {
            System.out.print("_");
        }
        System.out.printf("\nBad guesses: %d/12\n", badCount);
        
        // Blank string, will add the letters the user has inputted each time
        String tried = ""; 
        
        // result and compare arrays will be used to compare if the user gets all the correct letters
        String[] result = new String[getWord.length()];
        String[] compare = new String[getWord.length()];
        
        // This loop adds the random word into the compare array
        for (int a = 0; a < getWord.length(); a++) {
            compare[a] = getWord.substring(a, a + 1);
        }
        
        // This loop will start the result array with "_"
        for (int line = 0; line < result.length; line++) {
            result[line] = "_";
        }
        
        // Main play loop
        for (int i = 0; i < 12;) {
            System.out.print("\nEnter a letter (quit: -1): ");
            String input = in.next();
            String inputLower = input.toLowerCase();
            System.out.printf("\nLevel: %d\n", level);
            
            // Try/catch in case user enters -1 to exit game
            try { 
                int exit = Integer.valueOf(inputLower);
                if (exit == -1) {
                    System.out.println("Exiting...");
                    break;
                }
            } catch (Exception e) {
                //break;
            }
            
            // if/else will increment Count variables depending on if user entered letter
            // is correct or incorrect.
            if (getWord.indexOf(inputLower) != -1) {
                totalCount += 1;
                System.out.printf("Total count: %d\n", totalCount);
                i++;
                
            } else {
                totalCount += 1;
                badCount += 1;
                System.out.printf("Bad guesses: %d\n", badCount);
                System.out.printf("Total count: %d\n", totalCount);
                i++;
            }
            
            // Add user entered letter into tried String to keep track
            tried = tried + inputLower;
            
            // Convert tried String to array and use sort method
            char[] sorted = tried.toCharArray();
            Arrays.sort(sorted);
            System.out.print("Tried: ");
            System.out.print(sorted);
            System.out.println();

            // This loop will replace the result array with the correct letters if user enters a correct letter.
            for (int d = 0; d < getWord.length(); d++) {                
                if (inputLower.equals(getWord.substring(d, d + 1))) {
                    result[d] = inputLower;
                }
            }

            // This loop will print the result array
            for (int p = 0; p < result.length; p++) {
                String extract = result[p];
                System.out.print(extract);
            }
            
            // Compare totalCount and if 12, user loses. Ask if play again
            if (totalCount == 12) {
                System.out.println("\nSorry. You lost.");
                System.out.printf("\nThe word was: %s\n", getWord);
                
                System.out.printf("Play again (no: -1, yes: other)?");
                input = in.next();
                inputLower = input.toLowerCase();
                
                try { // Try/catch in case user enters -1 to exit game
                    int exit = Integer.valueOf(inputLower);
                    if (exit == -1) {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }                    
                } catch (Exception e) {
                }
                //Replay game using same difficulty level
                String level2 = Integer.toString(level);
                level(level2); 
            } 
            // If totalCount not 12, compare two arrays to see if the same
            else if (Arrays.equals(compare, result)) {
                System.out.printf("\nYou won with %d bad guesses", badCount);
                System.out.printf("\nPlay again (no: -1, yes: other)?");
                input = in.next();
                inputLower = input.toLowerCase();
                
                // Try/catch in case user enters -1 to exit game
                try { 
                    int exit = Integer.valueOf(inputLower);
                    if (exit == -1) {
                        System.out.println("Exiting...");
                        break;
                    }                    
                } catch (Exception e) {
                }
                // Replay game using same difficulty level
                String level2 = Integer.toString(level);
                level(level2); 
            }
            
        }
    }
    
    
    
}
