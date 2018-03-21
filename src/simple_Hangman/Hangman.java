package simple_Hangman;


import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {

	public static void guess(String word, int life) {
		char[] filler = new char[word.length()];
		int i = 0;

		while (i<word.length()) {
			filler [i] = '-';
			if (word.charAt(i) == ' ') {
				filler[i] = ' ';
			}
			i++;
		}

		System.out.println(filler);
		System.out.println("Life remaining = " + life);

		Scanner scan = new Scanner(System.in);
		
		ArrayList<Character> l = new ArrayList<Character>();
		
		while(life>0) {
			char x = scan.next().charAt(0);
			
			if(l.contains(x)) {
				System.out.println("Already entered " + " life remaining = " + life);
				continue;
			}
			
			l.add(x);

			if(word.contains(x + "")) {
				for (int y=0; y<word.length(); y++) {
					if (word.charAt(y) == x) {
						filler[y]=x;
					}
				}
			}
			else {
				life--;
			}
			
			if(word.equals(String.valueOf(filler))) {
				System.out.println(filler);
				System.out.println("You Won!!!");
				break;
			}
			
			System.out.println(filler);
			System.out.println("Life remaining = " + life);
		}
		
		if(life==0) {
			System.out.println("You lose ;-; ");
		}

	}

	public static void main (String [] args ) {
		String word = "hello world";
		int life = 5;
		guess(word,life);
	}
}

