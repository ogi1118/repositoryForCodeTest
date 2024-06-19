import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String[] lines = getStdin();
        if (lines.length == 0) {
            System.out.println("-1");
            return;
        }
        String input = lines[0];

        String integerPart = "";
        String fractionPart = "";
        if (input.contains(".")) {
            String[] tmp = input.split(".");
            integerPart = tmp[0];
            fractionPart = tmp[1];
        } else {
            integerPart = input;
        }
        if (fractionPart.isEmpty()) {
            String result = convertIntNumToWords(integerPart);
        } else {
            String intResult = convertIntNumToWords(fractionPart);
            // String fracResult = convertFracNumToWords(fractionPart);
        }

    }

    private static String[] getStdin() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    static String convertIntNumToWords(String strInt) {
        String[] ones = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        String[] teens = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
                "eighteen", "nineteen" };
        String[] tens = { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
        String[] thousands = { "", "thousand", "million", "billion" };
        long integer = Integer.parseInt(strInt);
        String[] result = new String[6];
        if (integer == 0) {
            return ones[0];
        }
        int thousandCounter = 0;
        while (integer > 0) {
            System.out.println(thousandCounter);
            if (integer % 1000 != 0) {
                long value = integer % 1000;
                String word = "";
                if (value >= 100) {
                    word += ones[(int) value / 100] + " hundred ";
                    value %= 100;
                }
                if (value >= 10 && value <= 19) {
                    word += teens[(int) value % 10] + " ";
                    value %= 10;
                }
                if (value < 10) {
                    word += ones[(int) value / 10];
                }
                result[thousandCounter++] = word;
                integer /= 1000;
                thousandCounter++;
            }
        }
        String res = "";
        for (int i = 0; i < result.length; i++) {
            res += result[result.length - i];
        }
        return res;
    }
}
