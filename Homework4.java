package soa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Homework 4: read Homework 3 text file to console; simple CLI calculator (Scanner, StringBuilder).
 */
public class Homework4 {

	/** Same filename as {@link Homework3} output. */
	private static final String DATA_FILE = "homework3_output.txt";

	public static void main(String[] args) {
		readHomework3File();
		runCalculator();
	}

	/** Read UTF-8 text: first line int, second line student ID. */
	static void readHomework3File() {
		try (InputStream raw = new FileInputStream(DATA_FILE);
				InputStream buffered = new BufferedInputStream(raw);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(buffered, StandardCharsets.UTF_8))) {
			String line1 = reader.readLine();
			String line2 = reader.readLine();
			if (line1 == null || line2 == null) {
				System.err.println("File incomplete. Run Homework3 first to create " + DATA_FILE);
				return;
			}
			int i = Integer.parseInt(line1.trim());
			String sno = line2.trim();
			System.out.println("[Homework 3 file]");
			System.out.println("int = " + i);
			System.out.println("studentId = " + sno);
		} catch (IOException e) {
			System.err.println("Read failed (run Homework3 to generate " + DATA_FILE + "): " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("First line is not a valid int: " + e.getMessage());
		}
	}

	/** Menu calculator: Scanner for input, StringBuilder for result line. */
	static void runCalculator() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Simple calculator, author 3123004529");
			System.out.println("+ addition");
			System.out.println("- subtraction");
			System.out.println("* multiplication");
			System.out.println("/ division");
			System.out.println("q quit");
			System.out.print("Enter choice (+, -, *, / or q): ");
			String choiceLine = sc.nextLine().trim();
			if (choiceLine.isEmpty()) {
				continue;
			}
			char op = choiceLine.charAt(0);
			if (op == 'q' || op == 'Q') {
				System.out.println("Bye.");
				break;
			}
			String opName;
			switch (op) {
			case '+':
				opName = "addition";
				break;
			case '-':
				opName = "subtraction";
				break;
			case '*':
				opName = "multiplication";
				break;
			case '/':
				opName = "division";
				break;
			default:
				System.out.println("Invalid choice. Use +, -, *, / or q.");
				continue;
			}

			System.out.print("You chose " + opName + ". Enter two integers separated by space: ");
			String nums = sc.nextLine().trim();
			String[] parts = nums.split("\\s+");
			if (parts.length < 2) {
				System.out.println("Need two integers. Try again.");
				continue;
			}
			int a;
			int b;
			try {
				a = Integer.parseInt(parts[0]);
				b = Integer.parseInt(parts[1]);
			} catch (NumberFormatException ex) {
				System.out.println("Not valid integers. Try again.");
				continue;
			}

			if (op == '/' && b == 0) {
				System.out.println("Divisor cannot be 0. Try again.");
				continue;
			}

			long result;
			switch (op) {
			case '+':
				result = (long) a + b;
				break;
			case '-':
				result = (long) a - b;
				break;
			case '*':
				result = (long) a * b;
				break;
			default:
				result = a / b;
				break;
			}

			StringBuilder line = new StringBuilder();
			line.append(a).append(' ').append(op).append(' ').append(b).append(" = ").append(result);
			System.out.println(line);
		}
	}
}
