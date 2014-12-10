import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ChangeDictionary {

	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("bin/dictionary.txt"));
			PrintWriter out = new PrintWriter("bin/new_dictionary.txt", "UTF-8");
			String line;
			int inCount = 0, outCount = 0;
			while ((line = in.readLine()) != null) {
				inCount++;
				if (line.length() < 7) {
					outCount++;
					out.println(line);
				}
			}
			in.close();
			out.close();
			System.out.println("Number of words in: " + inCount);
			System.out.println("Number of words out: " + outCount);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

}
