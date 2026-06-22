package io.github.sandeshkhatiwada05.englishnepaliconversion.EnglishNepali;

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
public class NepaliEnglishConversion {

    /**
     * Optional reference array (not used in this implementation).
     * Kept for symmetry or future bidirectional conversion support.
     */
    private static final char[] ENGLISH_DIGITS = {
            '0','1','2','3','4','5','6','7','8','9'
    };

    /**
     * Direct lookup table for converting Nepali digits to English digits.
     * Indexed by Unicode character value for O(1) conversion speed.
     */
    private static final char[] NEPALI_TO_ENGLISH_DIGITS = new char[128];

    static {
        NEPALI_TO_ENGLISH_DIGITS['०'] = '0';
        NEPALI_TO_ENGLISH_DIGITS['१'] = '1';
        NEPALI_TO_ENGLISH_DIGITS['२'] = '2';
        NEPALI_TO_ENGLISH_DIGITS['३'] = '3';
        NEPALI_TO_ENGLISH_DIGITS['४'] = '4';
        NEPALI_TO_ENGLISH_DIGITS['५'] = '5';
        NEPALI_TO_ENGLISH_DIGITS['६'] = '6';
        NEPALI_TO_ENGLISH_DIGITS['७'] = '7';
        NEPALI_TO_ENGLISH_DIGITS['८'] = '8';
        NEPALI_TO_ENGLISH_DIGITS['९'] = '9';
    }

    /**
     * Converts a string containing Nepali digits into English digits.
     *
     * Non-Nepali characters (commas, dots, spaces, etc.) are preserved as-is.
     *
     * @param nepaliInputNumber input string containing Nepali numerals
     * @return converted string in English numerals
     */
    public static String NepaliNumberToEnglish(String nepaliInputNumber) {

        if (nepaliInputNumber == null || nepaliInputNumber.isEmpty()) {
            return nepaliInputNumber;
        }

        char[] arr = nepaliInputNumber.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];

            if (c < NEPALI_TO_ENGLISH_DIGITS.length &&
                    NEPALI_TO_ENGLISH_DIGITS[c] != 0) {

                arr[i] = NEPALI_TO_ENGLISH_DIGITS[c];
            }
        }

        return new String(arr);
    }
}