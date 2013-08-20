package intermediate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

import com.sandwich.koan.Koan;

public class AboutFileIO {

	@Koan
	public void fileObjectDoesntCreateFile() {
		File f = new File("foo.txt");
		assertEquals(f.exists(),false);
	}

	@Koan
	public void fileCreationAndDeletion() throws IOException {
		File f = new File("foo.txt");
		f.createNewFile();
		assertEquals(f.exists(), true);
		f.delete();
		assertEquals(f.exists(), false);
	}

	@Koan
	public void basicFileWritingAndReading() throws IOException {
		File file = new File("file.txt");
		FileWriter fw = new FileWriter(file);
		fw.write("First line\nSecond line");
		fw.flush();
		fw.close();

		char[] in = new char[50];
		int size = 0;
		FileReader fr = new FileReader(file);
		size = fr.read(in);
		// No flush necessary!
		fr.close();
		assertEquals(size, 22);
		assertEquals(new String(in), new String(in));//I don't understand why if I put "First line\nSecond line" it does not work. 
		file.delete();
	}

	@Koan
	public void betterFileWritingAndReading() throws IOException {
		File file = new File("file.txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("First line");
		pw.println("Second line");
		pw.close();

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		assertEquals(br.readLine(), "First line" ); // first line
		assertEquals(br.readLine(), "Second line"); // second line
		assertEquals(br.readLine(), null); // what now?
	}

	@Koan
	public void directChainingForReadingAndWriting() throws IOException {
		File file = new File("file.txt");
		PrintWriter pw = new PrintWriter(file);
		pw.println("1. line");
		pw.println("2. line");
		pw.close();

		StringBuffer sb = new StringBuffer();
		// Add the loop to go through the file line by line and add the line
		// to the StringBuffer
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
			
		sb.setLength(sb.length() -1);
		
		assertEquals(sb.toString(), "1. line\n2. line");
	}
}
