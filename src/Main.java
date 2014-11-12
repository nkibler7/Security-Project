import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is the main class for the security
 * project.  It will generate passwords,
 * then attempt to guess them.
 * 
 * @author Chris Schweinhart (schwein)
 */
public class Main
{
	private static int[][] data;
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
	
	/**
	 * The main method for our security project.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		// Generate 100 random passwords
		String[] randoms = new String[100];
		for (int i = 0; i < 100; i++) {
			int numchars = 6 + (int) (Math.random()*11);
			char[] temp = new char[numchars];
			for (int j = 0; j < numchars; j++) {
				temp[j] = charset[(int)(Math.random() * charset.length)];
			}
			randoms[i] = new String(temp);
		}
		
		// Generate 100 typical passwords
		String[] typicals = new String[100];
		String temp = "";
		for (int i = 0; i < 100; i++) {
			int numwords = 1 + (int) (Math.random()*2);
			for (int j = 0; j < numwords; j++) {
				try {
					String str = "";
					BufferedReader in = new BufferedReader(new FileReader("dictionary.txt"));
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
		data = new int[4][100];
		int i;
		
		// Run the brute force on each
		for (i = 0; i < 100; i++)
			data[0][i] = brute(randoms[i]);
		for (i = 0; i < 100; i++)
			data[0][i] = brute(typicals[i]);
			
		// Run the dictionary attack on each
		for (i = 0; i < 100; i++)
			data[0][i] = dictionary(randoms[i]);
		for (i = 0; i < 100; i++)
			data[0][i] = dictionary(typicals[i]);
		
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
	private static int brute(String input)
	{
		// TODO: Implement
		return 0;
	}
	
	/**
	 * This method will dictionary-attack the
	 * given input string in order to crack it.
	 * 
	 * @param input The input string to break.
	 * @return      The time taken to break in ms.
	 */
	private static int dictionary(String input)
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
		// TODO: Implement
	}
}