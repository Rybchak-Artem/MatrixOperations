import java.util.Random;
import java.util.Scanner;


public class matrix {


    static long[][] generateMatrix(int m, int n, int min, int max) {
        Random rnd = new Random();
        long[][] a = new long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = min + rnd.nextInt(max - min + 1);
            }
        }
        return a;
    }


    static void printMatrix(long[][] a) {
        if (a.length == 0 || a[0].length == 0) {
            System.out.println("Matrix is empty");
            return;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print("[");
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]);
                if (j < a[i].length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }


    static void subtractRowAverage(long[][] a) {
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int j = 0; j < a[i].length; j++) sum += a[i][j];
            long avg = Math.round((double) sum / a[i].length);
            for (int j = 0; j < a[i].length; j++) a[i][j] -= avg;
        }
    }


    static long[][] cyclicShift(long[][] a, int k) {
        int m = a.length, n = a[0].length;
        int kr = Math.floorMod(k, n);
        int ku = Math.floorMod(k, m);
        long[][] res = new long[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int ni = Math.floorMod(i - ku, m);
                int nj = (j + kr) % n;
                res[ni][nj] = a[i][j];
            }
        }
        return res;
    }


    static long[][] removeMaxRowsAndCols(long[][] a) {
        int m = a.length, n = a[0].length;
        long max = a[0][0];
        for (long[] row : a)
            for (long v : row)
                if (v > max) max = v;


        boolean[] delRow = new boolean[m];
        boolean[] delCol = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == max) {
                    delRow[i] = true;
                    delCol[j] = true;
                }
            }
        }


        int newM = 0, newN = 0;
        for (boolean b : delRow) if (!b) newM++;
        for (boolean b : delCol) if (!b) newN++;
        if (newM == 0 || newN == 0) return new long[0][0];


        long[][] res = new long[newM][newN];
        int ri = 0;
        for (int i = 0; i < m; i++) {
            if (delRow[i]) continue;
            int rj = 0;
            for (int j = 0; j < n; j++) {
                if (delCol[j]) continue;
                res[ri][rj++] = a[i][j];
            }
            ri++;
        }
        return res;
    }


    static long[][] rotate90(long[][] a) {
        if (a.length == 0 || a[0].length == 0) return new long[0][0];
        int m = a.length, n = a[0].length;
        long[][] res = new long[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][m - 1 - i] = a[i][j];
            }
        }
        return res;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Line number m: ");
        int m = sc.nextInt();
        System.out.print("Column number n: ");
        int n = sc.nextInt();
        System.out.print("min: ");
        int min = sc.nextInt();
        System.out.print("max: ");
        int max = sc.nextInt();
        System.out.print("Slide k: ");
        int k = sc.nextInt();


        long[][] a = generateMatrix(m, n, min, max);
        System.out.println("\nMatrix:\n");
        printMatrix(a);


        subtractRowAverage(a);
        System.out.println("\n1) After average minus:\n");
        printMatrix(a);


        a = cyclicShift(a, k);
        System.out.println("\n2) After cycle slide " + k + " right and up:\n");
        printMatrix(a);


        a = removeMaxRowsAndCols(a);
        System.out.println("\n3) After deleting lines and colums with max\n");
        printMatrix(a);


        System.out.print("\n4) Matrix for 90 degres rotate:\n");
        System.out.println("\nBefore:");
        printMatrix(a);
        a = rotate90(a);
        System.out.println("\nAfter:");
        printMatrix(a);
    }
}

