package io.github.sandeshkhatiwada05.englishnepaliconversion.englishnepali;

/**
 * Implementation for converting Nepali numerals to English numerals.
 *
 * Supports conversion of Unicode Nepali digits (०-९) into standard English digits (0-9).
 * This is a lightweight O(n) character mapping utility designed for high-performance use.
 *
 *
 * Example:
 * Input  : १२३४५
 * Output : 12345
 *
 * @author Sandesh Khatiwada
 */
public class ConvertNepaliEnglish {

    /**
     * Converts a string containing Nepali digits into English digits.
     *
     * Non-Nepali characters (commas, dots, spaces, etc.) are preserved as-is.
     *
     * @param nepaliInputNumber input string containing Nepali numerals
     * @return converted string in English numerals
     */
    public static String NepaliNumberToEnglish(String nepaliInputNumber) {

        if (nepaliInputNumber == null || nepaliInputNumber.isEmpty()) return nepaliInputNumber;

        char[] arr = nepaliInputNumber.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = switch (arr[i]) {
                case '०' -> '0';
                case '१' -> '1';
                case '२' -> '2';
                case '३' -> '3';
                case '४' -> '4';
                case '५' -> '5';
                case '६' -> '6';
                case '७' -> '7';
                case '८' -> '8';
                case '९' -> '9';
                default -> arr[i];
            };
        }

        return new String(arr);
    }
}