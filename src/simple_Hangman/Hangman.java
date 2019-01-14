package simple_Hangman;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {

	public static void guess(String word, int life) {
		char[] filler = new char[word.length()];
		int i = 0;

		//Shows how many letters the word to guess contains
		while (i<word.length()) {
			filler [i] = '-';
			if (word.charAt(i) == ' ') {
				filler[i] = ' ';
			}
			i++;
		} //end while

		System.out.println(filler);
		System.out.println("Life remaining = " + life);

		Scanner scan = new Scanner(System.in);
		
		ArrayList<Character> l = new ArrayList<Character>();
		
		while(life>0) {
			char x = scan.next().charAt(0);
			
			if(l.contains(x)) {	//Checks if letter was already guessed. If yes, goes to next iteration of the loop.
				System.out.println("Already entered " + " life remaining = " + life);
				continue;
			}
			
			l.add(x);	//Adds guessed letter to list of guessed letters

			if(word.contains(x + "")) {		//Checks if the word contains the letter
				for (int y=0; y<word.length(); y++) {
					if (word.charAt(y) == x) {
						filler[y]=x;
					}
				}
			}
			else {		//removes a life if the word did not contain the letter
				life--;
			}
			
			if(word.equals(String.valueOf(filler))) {	//prints victory if the word has been guessed and breaks loop
				System.out.println(filler);
				System.out.println("You Won!!!");
				break;
			}
			
			System.out.println(filler);		//prints current progress on word
			System.out.println("Life remaining = " + life);
		} //end while
		
		if(life==0) {
			System.out.println("You lose ;-; ");
		}
		
		scan.close();

	}

	public static void main (String [] args ) {
		String word = "hello world";
		int life = 5;
		guess(word,life);
	}
}

