import java.util.Scanner; // import Scanner class to take input
import java.lang.Math; // import Math class to generate random number

public class ScissorRockPaper {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in); // create object input

		// declare and initialize variable answer
		int answer = -1;

		// declare and initialize array of Strings choice
		String[] choices = new String[] {"scissor", "paper", "rock"};

		// declare and initialize String result
		String result = "";

		// generate random integer 0, 1 or 2 representing scissor, rock or paper
		int random = (int)(Math.random() * 3);

		//repeat until input 0, 1 or 2
		while (answer < 0 || answer > 2){
			// message to console asking for input
			System.out.print("Choose one of them: scissor(0), paper(1), rock(2): ");
			answer = input.nextInt(); // take input
		}

		// close input
		input.close();

		// update result variable
		if (answer == random){ // if draw
			result = "It is a draw.";
		}		else if ((answer == 0 && random == 1)	|| (answer == 1 && random == 2)||
		(answer == 2 && random == 0)) { // else if victory
			result = "You won.";

		} else { // not a draw or victory, you lost
			result = "You lost.";
		}

		// display result
		System.out.println("The computer is " + choices[random] +
		". You are " + choices[answer] + ". " + result);
	}
}
