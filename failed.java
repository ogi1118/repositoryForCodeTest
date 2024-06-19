
import java.util.Scanner;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        String[] lines = getStdin();
        if (lines.length == 0) {
            System.out.println("-1");
            return;
        }

        String input = lines[0];
        try {
            double number = Double.parseDouble(input);
            if (number < 0 || number > 999999999.99) {
                System.out.println("-1");
                return;
            }
            String result = convertNumberToWords(number);
            System.out.println(capitalizeFirstLetter(result));
        } catch (NumberFormatException e) {
            System.out.println("-1");
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

    private static String convertNumberToWords(double number) {
        String[] ones = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        String[] teens = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
                "eighteen", "nineteen" };
        String[] tens = { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
        String[] thousands = { "", "thousand", "million", "billion" };

        long integerPart = (long) number;
        int fractionPart = (int) Math.round((number - integerPart) * 100);

        String integerWords = convertIntegerToWords(integerPart, ones, teens, tens, thousands);
        String fractionWords = fractionPart > 0 ? convertFractionToWords(fractionPart, ones) : "";

        if (fractionWords.isEmpty()) {
            return integerWords;
        } else {
            return integerWords + " point " + fractionWords;
        }
    }

    private static String convertIntegerToWords(long number, String[] ones, String[] teens, String[] tens,
            String[] thousands) {
        if (number == 0) {
            return ones[0];
        }

        String words = "";
        int thousandCounter = 0;

        while (number > 0) {
            if (number % 1000 != 0) {
                String part = convertLessThanThousand((int) (number % 1000), ones, teens, tens);
                words = part + (thousandCounter > 0 ? " " + thousands[thousandCounter] : "")
                        + (words.isEmpty() ? "" : " " + words);
            }
            number /= 1000;
            thousandCounter++;
        }

        return words;
    }

    private static String convertLessThanThousand(int number, String[] ones, String[] teens, String[] tens) {
        String words;

        if (number % 100 < 20) {
            words = number % 100 < 10 ? ones[number % 10] : teens[number % 10];
            number /= 100;
        } else {
            words = ones[number % 10];
            number /= 10;
            words = tens[number % 10] + (words.equals("zero") ? "" : " " + words);
            number /= 10;
        }

        if (number == 0) {
            return words;
        }
        return ones[number] + " hundred" + (words.equals("zero") ? "" : " " + words);
    }

    private static String convertFractionToWords(int number, String[] ones) {
        StringBuilder fractionWords = new StringBuilder();

        if (number < 10) {
            fractionWords.append(ones[number]);
        } else {
            fractionWords.append(ones[number / 10]).append(" ").append(ones[number % 10]);
        }

        // Remove trailing " zero" if it exists
        while (fractionWords.length() > 0 && fractionWords.toString().endsWith(" zero")) {
            fractionWords.setLength(fractionWords.length() - 5);
        }

        return fractionWords.toString().trim();
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
