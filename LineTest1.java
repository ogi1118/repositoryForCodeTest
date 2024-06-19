import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String[] lines = getStdin();
        String[] tmpStr = lines[0].split(" ");
        if (tmpStr.length != 2 || !isInteger(tmpStr[0]) || !isInteger(tmpStr[1])) {
            System.exit(100);
        }
        int n = Integer.parseInt(tmpStr[0]);
        int m = Integer.parseInt(tmpStr[1]);
        int nm = n * m;
        if (nm % 2 == 1) {
            System.out.println("000000000");
        } else {
            BigInteger result = nCm(nm - 1, (nm / 2) - 1);
            String resString = String.format("%09d", result.multiply(BigInteger.valueOf(2)));
            if (resString.length() > 9) {
                System.out.println(resString.substring(resString.length() - 9));
            } else {
                System.out.println(resString);
            }
        }
    }

    private static String[] getStdin() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        lines.add(scanner.nextLine());
        return lines.toArray(new String[lines.size()]);
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static BigInteger nCm(int n, int m) {
        BigInteger denom = BigInteger.ONE;
        BigInteger numer = BigInteger.ONE;
        for (int i = 1; i <= m; i++) {
            denom = denom.multiply(BigInteger.valueOf(i));
            numer = numer.multiply(BigInteger.valueOf(n - i + 1));
        }
        BigInteger res = numer.divide(denom);
        return res;
    }
}
