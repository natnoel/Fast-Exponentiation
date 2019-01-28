import java.math.BigInteger;
import java.util.Scanner;

/**
 * Tan Shi Terng Leon
 * 4000602
 * FE.java
 */

/**
 * @author User
 *
 */
public class FE {

	/**
	 * @param args
	 */
	public static BigInteger g, a, p, result;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input, bitString;
		
		System.out.print("Fast Exponentiation with Square and Multiply\n" +
		"g^a (mod p)\n\n");
		
		/*********************************Getting input****************************************/
		do {	//Input g
			System.out.print("Enter g: ");
			input = sc.nextLine();
			g = new BigInteger(input);
			
			if (g.compareTo(new BigInteger("0")) == -1)	//If g is negative
				System.out.println("Please enter a number greater or equal then 0");
		} while (g.compareTo(new BigInteger("0")) == -1);
		
		do {	//Input a
			System.out.print("Enter a: ");
			input = sc.nextLine();
			a = new BigInteger(input);
			
			if (a.compareTo(new BigInteger("0")) == -1)	//If a is negative
				System.out.println("Please enter a number greater or equal then 0");
		} while (a.compareTo(new BigInteger("0")) == -1);
		
		do {	//Input p
			System.out.print("Enter p: ");
			input = sc.nextLine();
			p = new BigInteger(input);
			
			if (p.compareTo(new BigInteger("0")) < 1)	//If p is less or equal than 0
				System.out.println("Please enter a number greater than 0");
		} while (p.compareTo(new BigInteger("0")) < 1);
		
		System.out.print("\nComputing: " + g + "^" + a + " (mod " + p + ")");
		BigInteger value = g.mod(p);
		
		if (!value.equals(g)) {	//If g mod p is not equal to g
			g = value;	//Make g = g mod p
			System.out.print(" = " + g + "^" + a + " (mod " + p + ")");
		}
		System.out.println();
		
		bitString = toBinary(a);	//Convert the exponent to bitstring
		
		System.out.println(a + " in binary is " + bitString + "\n(" + expression(bitString) +
				") mod " + p + "\n");
		
		/*********************************Computing result****************************************/
		result = fastExp(g, a, p, bitString);	//Compute using fast exponentiation square multiply
		
		System.out.println("\nThe result is " + result);	//Output result
		
		sc.close();

	}
	
	private static BigInteger fastExp(BigInteger n, BigInteger p, BigInteger m, String bitString) {
		String pw = bitString;
		BigInteger v = n;
		for (int i = 1; i < pw.length(); i++) {
			if (pw.charAt(i) == '0') {	//If bit is 0
				System.out.print("0 S  " + v + "^2 = ");
				
				v = v.multiply(v);	//square the value
				System.out.print(v);
				
				if (v.compareTo(m) > -1) {	//If value exceeds the modulus
					v = v.mod(m);	//mod the value
					System.out.print(" = " + v);
				}
				System.out.println(" (mod " + m + ")");
			}
			else if (pw.charAt(i) == '1') {	//If bit is 1
				System.out.print("1 SX (" + v + "^2) * " + n + " = ");
				
				v = v.multiply(v).multiply(n);	//Square the value and multiply by n
				System.out.print(v);
				
				if (v.compareTo(m) > -1) {	//If value exceeds the modulus
					v = v.mod(m);	//mod the value
					System.out.print(" = " + v);
				}
				System.out.println(" (mod " + m + ")");
			}
		}
		
		return v;
	}
	
	private static String toBinary(BigInteger n) {	//Converts a BigInteger into binary form
		StringBuffer sb = new StringBuffer();
		BigInteger num = n, one = new BigInteger("1"), two = new BigInteger("2");
		while (num.compareTo(new BigInteger("0")) == 1) {
			if (num.mod(two).equals(one))
				sb.insert(0, '1');
			else
				sb.insert(0, '0');
			
			num = num.divide(two);
		}
		return sb.toString();
	}
	
	private static String expression(String bits) {
		StringBuffer sb = new StringBuffer(g.toString());
		
		for (int i = 1; i < bits.length(); i++) {
			if (bits.charAt(i) == '0') {
				sb.insert(0, '(');
				sb.append(")^2");
			}
			else if (bits.charAt(i) == '1') {
				sb.insert(0, '(');
				sb.append(")^2 * " + g);
			}
		}
		
		return sb.toString();
	}
}
