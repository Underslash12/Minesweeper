// MineUtilities.java

import java.util.Arrays;

public class MineUtilities {

	public static Boolean[][] booleanMatrixToInteger (boolean[][] b)
	{
		Boolean[][] fin = new Boolean[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				fin[i][j] = new Boolean(b[i][j]);
			}
		}
		return fin;
	}

	public static void setBoolFromBool (boolean[][] a, Boolean[][] b)
	{
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = b[i][j].booleanValue();
			}
		}
	}

	public static Integer[][] intMatrixToInteger (int[][] a)
	{
		Integer[][] fin = new Integer[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			fin[i] = Arrays.stream(a[i]).boxed().toArray(Integer[]::new);
		}
		return fin;
	}

	public static void setIntFromInt (int[][] a, Integer[][] b)
	{
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = b[i][j].intValue();
			}
		}
	}

	// shifts an array by the value, looping around when at the end
	public static <T> void shiftArray (T[] a, int n)
	{
		n = a.length + n % a.length;
		n = n % a.length;

		for (int i = 0; i < n; i++) {
			T temp = a[a.length - 1];
			for (int j = a.length - 2; j >= 0; j--) {
				// System.out.println(Arrays.toString(a));
				a[j + 1] = a[j];
			}
			a[0] = temp;
		}
	}

	// shifts a matrix by each value, looping around when at the end
	public static <T> void shiftMatrix (T[][] a, int x, int y)
	{
		shiftArray(a, y);
		for (int i = 0; i < a[0].length; i++) {
			shiftArray(a[i], x);
		}
	}

	public static void main (String[] args)
	{
		// Integer[] a = new Integer[10];
		// for (int i = 0; i < 10; i++) {
		// 	a[i] = i;
		// }
		// System.out.println(Arrays.toString(a));
		// shiftArray(a, 2);
		// System.out.println(Arrays.toString(a));
		int[][] m = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				m[i][j] = i * 4 + j;
			}
		}
		Integer[][] m1 = intMatrixToInteger(m);
		for (Integer[] a : m1) {
			System.out.println(Arrays.toString(a));
		}
		shiftMatrix(m1, 2, 1);
		for (Integer[] a : m1) {
			System.out.println(Arrays.toString(a));
		}
	}
}
