import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is the main class for the security
 * project.  It will generate passwords,
 * then attempt to guess them.
 * 
 * @author Chris Schweinhart (schwein)
 * @author Nate Kibler (nkibler7)
 */
public class Main
{
	private static long[][] data;
	private static final char[] charset = {'a', 'b', 'c', 'd', 'e',
										  'f', 'g', 'h', 'i', 'j',
										  'k', 'l', 'm', 'n', 'o',
										  'p', 'q', 'r', 's', 't',
										  'u', 'v', 'w', 'x', 'y',
										  'z', 'A', 'B', 'C', 'D',
										  'E', 'F', 'G', 'H', 'I',
										  'J', 'K', 'L', 'M', 'N',
										  'O', 'P', 'Q', 'R', 'S',
										  'T', 'U', 'V', 'W', 'X',
										  'Y', 'Z', '0', '1', '2',
										  '3', '4', '5', '6', '7',
										  '8', '9'};
	
	// Random and typical password arrays
	private static final int NUM_PASSWORDS = 1;
	private static final String[] randoms = new String[NUM_PASSWORDS];
	private static final String[] typicals = new String[NUM_PASSWORDS];
	
	// Password length constants
	private static final int LENGTH_LOWER_BOUND = 3;
	private static final int LENGTH_UPPER_BOUND = 6;
	
	// Output constants
	private static final String[] TYPE_OF_ATTACK = {"BRUTE ON RANDOMS",
													"BRUTE ON TYPICALS",
													"DICTIONARY ON RANDOMS",
													"DICTIONARY ON TYPICALS"};
	
	/**
	 * The main method for our security project.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		// Generate random passwords
		for (int i = 0; i < NUM_PASSWORDS; i++) {
			int numchars = LENGTH_LOWER_BOUND + (int) (Math.random()*((double)(LENGTH_UPPER_BOUND - LENGTH_LOWER_BOUND)));
			char[] temp = new char[numchars];
			for (int j = 0; j < numchars; j++) {
				temp[j] = charset[(int)(Math.random() * charset.length)];
			}
			randoms[i] = new String(temp);
		}
		
		// Generate typical passwords
		String temp = "";
		for (int i = 0; i < NUM_PASSWORDS; i++) {
			int numwords = 1 + (int) (Math.random()*2);
			for (int j = 0; j < numwords; j++) {
				try {
					String str = "";
					BufferedReader in = new BufferedReader(new FileReader("bin/dictionary.txt"));
					int index = (int) (Math.random() * 127143);
					for (; index >= 0; index--) {
						str = in.readLine();
					}
					in.close();
					temp += str;
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(0);
				}
			}
			typicals[i] = temp;
		}
		
		// Set up the data arrays
		data = new long[4][NUM_PASSWORDS];
		
		// Run the attacks on each password
		for (int i = 0; i < NUM_PASSWORDS; i++) {
			// Try bruteforce
			data[0][i] = brute(randoms[i]);
			data[1][i] = brute(typicals[i]);
			// Try a dictionary attack
			data[2][i] = dictionary(randoms[i]);
			data[3][i] = dictionary(typicals[i]);
		}
		
		// Output the results
		output();
	}
	
	/**
	 * This method will brute-force the given
	 * input string in order to crack it.
	 * 
	 * @param input The input string to break.
	 * @return      The time taken to break in ms.
	 */
	private static long brute(String input)
	{
		long startTime = System.currentTimeMillis();
		char[] guess = new char[LENGTH_LOWER_BOUND];
		Arrays.fill(guess,  charset[0]);
		
		while (guess.length <= LENGTH_UPPER_BOUND) {
			String stringGuess = new String(guess);
			if (stringGuess.equals(input))
				break;
			
			guess = increment(guess);
			System.out.println("Trying '" + stringGuess + "' against password '" + input + "'.");
		}

		return (System.currentTimeMillis() - startTime);
	}
	
	private static char[] increment(char[] guess)
    {
		char[] newGuess = guess.clone();
        int index = newGuess.length - 1;
        while (index >= 0)
        {
            if (newGuess[index] == charset[charset.length - 1])
            {
                if (index == 0)
                {
                	newGuess = new char[newGuess.length + 1];
                    Arrays.fill(newGuess, charset[0]);
                    break;
                }
                else
                {
                	newGuess[index] = charset[0];
                    index--;
                }
            }
            else
            {
            	newGuess[index] = charset[indexInCharset(newGuess[index]) + 1];
                break;
            }
        }
        return newGuess;
    }
	
	/**
	 * This method will dictionary-attack the
	 * given input string in order to crack it.
	 * 
	 * @param input The input string to break.
	 * @return      The time taken to break in ms.
	 */
	private static long dictionary(String input)
	{
		// TODO: Implement
		return 0;
	}
	
	/**
	 * This method will output the results to
	 * standard out in statistical format.
	 */
	private static void output()
	{
		for (int i = 0; i < data.length; i++) {
			System.out.println("-- " + TYPE_OF_ATTACK[i] + " --");
			for (int j = 0; j < data[i].length; j++) {
				String pwd = (i < 2) ? randoms[j] : typicals[j];
				System.out.println("Time to crack '" + pwd + "': " + data[i][j]);
			}
		}
	}
	
	private static int indexInCharset(char c) {
		for (int i = 0; i < charset.length; i++) {
			if (charset[i] == c)
				return i;
		}
		return -1;
	}
}