import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Stack;
import java.util.Random;

public class HangmanGame {
	public static LinkedList<String> wordsGuessed = new LinkedList<>();
	public static ArrayList<String> words = new ArrayList<>();
	public static Stack<String> wordStack = new Stack<>();
	public static Random random = new Random();
	public static boolean playAgain = true;
	public static boolean wordGuessed = false;
	public static int randomIndx;
	public static String randomWord;
	public static char[] underscores;
	
	public static void playAgain(String response) {
		
		if(!response.equals("yes")) {
	    	
	    	playAgain = false;
	    	System.out.println("Thanks for playing!");
	        System.out.println("Correct words guessed: " + wordsGuessed);

	    }
	}
		
	public static boolean wordMatch(char[] underscores, String randomWord) {
		if (String.valueOf(underscores).equals(randomWord)) {
            System.out.println("You guessed to word correctly!");
            wordsGuessed.add(randomWord);
            wordGuessed = true;
        }
		return wordGuessed;
		
	}
	/*
	- Chat GPT
	- 12/3/24
	- binary search when taking in an arraylist of strings 
	- CHAt GPT 4
	*/
	
	public static int binarySearch( ArrayList<String> words, String target) {
		int low = 0;
		int high = words.size() - 1;
		
		while(high >= low) {
			int mid = (high + low) / 2;
			String midWord = words.get(mid);
			int comparison = words.get(mid).compareTo(target);
			
			if(comparison == 0) {
				
				wordStack.push(midWord);
				words.remove(midWord);
				return mid;
				
			}
			else if ( comparison > 0) {
				low = mid + 1; 
			}
			else  {
				high = mid -1;
			}

		}
		return -1;
	}
		
		
	
	
	
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		
//		take in word file
		/*
		- Chat GPT
		- 12/3/24
		- code to take in file I used from our scrabble project
		- CHAt GPT 4
		*/
		String filePath = "src/Collins Scrabble Words (2019)2.txt";
		
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                words.add(new String(line));
//                 System.out.println(words); // Print each line  
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        while( playAgain ) {
        	int wrongGuesses = 0;
//        get a randomWord from the arrayList
         randomIndx = random.nextInt(words.size());
        
         randomWord = words.get(randomIndx);
//        hide word
        underscores = new char[randomWord.length()];
        for (int i = 0; i < underscores.length; i++) {
        	underscores[i] = '_'; 
        }
//        System.out.println(underscores);
        System.out.println(randomWord);
        
        
//      have user guess letter and take in the first letter guessed
        wordGuessed = false;
        while(!wordGuessed) {
        System.out.println(underscores);
        System.out.println("Guess a letter: ");
        char guess = userInput.nextLine().toUpperCase().charAt(0);
        boolean correct = false;
        
//        loop thru word to see if letter matches
        for( int i = 0; i < randomWord.length();i++) {
        	if(randomWord.charAt(i) == guess) {
        		underscores[i] = guess;
        		correct = true;
        	}
        	
        }
        	
//        check if letter is not in the word
        if(correct == false) {
    		System.out.println("Sorry wrong guess");
    		wrongGuesses++;
    		int guessesLeft = 6 - wrongGuesses;
    		System.out.println("Wrong guesses left: " + guessesLeft);
	
        }
//        check if words match to end game
        wordMatch(underscores, randomWord);
        
//        check if they are out of guesses
         if ( wrongGuesses >= 6) {
        		System.out.println("Sorry you are out of guesses, the word was: "+ randomWord);
        		break;
            }
        }
//        ask user if he wants to play again
        System.out.println("Do you want to play again? yes/no:");
        String response = userInput.nextLine().toLowerCase();
        playAgain(response);
        binarySearch(words,randomWord);
         
                    
        }
        
                

	}
	
}
