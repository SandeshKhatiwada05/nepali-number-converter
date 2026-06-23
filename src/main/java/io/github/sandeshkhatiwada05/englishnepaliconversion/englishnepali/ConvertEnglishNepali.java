package io.github.sandeshkhatiwada05.englishnepaliconversion.englishnepali;

import java.math.BigDecimal;

/**
 * Utility class for converting English numbers into Nepali formats.
 *
 * This class provides three main features:
 * 
 *     Conversion of English digits (0-9) into Nepali digits (०-९)
 *     Conversion of numbers into Nepali word representation
 *     Formatting numbers using the Nepali/Indian comma system
 * 
 *
 * The numbering system follows the South Asian unit system:
 * thousand, lakh, crore, arab, kharab.
 *
 * This class is stateless and safe to use across threads.
 *
 * @author Sandesh Khatiwada
 */
public class ConvertEnglishNepali {

    /**
     * Mapping table for English digits (0-9) to Nepali digits.
     */
    private static final char[] NEPALI_DIGITS = {
            '०', '१', '२', '३', '४', '५', '६', '७', '८', '९'
    };

    /**
     * Nepali word mapping for numbers from 0 to 99.
     */
    private static final String[] UNIT = {
            "", "एक", "दुई", "तीन", "चार", "पाँच", "छ", "सात", "आठ", "नौ", "दश",
            "एघार", "बाह्र", "तेह्र", "चौध", "पन्ध्र", "सोह्र", "सत्र", "अठार", "उन्नाइस", "बीस",
            "एक्काइस", "बाइस", "तेइस", "चौबिस", "पच्चिस", "छब्बिस", "सत्ताइस", "अठ्ठाइस", "उनन्तीस", "तीस",
            "एकतीस", "बत्तीस", "तेत्तीस", "चौँतीस", "पैंतीस", "छत्तीस", "सैंतीस", "अठतीस", "उनन्चालीस", "चालीस",
            "एकचालीस", "बयालीस", "त्रिचालीस", "चौवालीस", "पैंतालीस", "छयालीस", "सतचालीस", "अठचालीस", "उनन्चास", "पचास",
            "एकाउन्न", "बाउन्न", "त्रिपन्न", "चौवन्न", "पच्पन्न", "छपन्न", "सन्ताउन्न", "अन्ठाउन्न", "उनन्साठी", "साठी",
            "एकसाठी", "बासठ्ठी", "त्रिसाठी", "चौँसठ्ठी", "पैंसठ्ठी", "छयसठ्ठी", "सतसठ्ठी", "अठसठ्ठी", "उनन्सत्तरी", "सत्तरी",
            "एकहत्तर", "बहत्तर", "त्रिहत्तर", "चौहत्तर", "पचहत्तर", "छहत्तर", "सतहत्तर", "अठहत्तर", "उनासी", "असी",
            "एकासी", "बयासी", "त्रियासी", "चौरासी", "पचासी", "छयासी", "सतासी", "अठासी", "नवासी", "नब्बे",
            "एकानब्बे", "बयानब्बे", "त्रियानब्बे", "चौरानब्बे", "पञ्चानब्बे", "छयानब्बे", "सन्तानब्बे", "अन्ठानब्बे", "उनान्सय"
    };

    /**
     * Converts an English number into Nepali digits.
     *
     * @param userInputNumber input number in English format
     * @return string containing Nepali digits
     */
    public static String englishNumberToNepali(Number userInputNumber) {

        String number = convertToString(userInputNumber);
        char[] arr = number.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c >= '0' && c <= '9') {
                arr[i] = NEPALI_DIGITS[c - '0'];
            }
        }

        return new String(arr);
    }

    /**
     * Converts a number into its Nepali word representation.
     *
     * @param englishNumber input number
     * @return number written in Nepali words
     */
    public static String englishNumberToNepaliSentence(Number englishNumber) {

        if (englishNumber == null) return "";

        BigDecimal bd = (englishNumber instanceof BigDecimal)
                ? (BigDecimal) englishNumber
                : new BigDecimal(englishNumber.toString());

        long n = bd.longValue();

        if (n == 0) return "शून्य";

        return convert(n);
    }

    /**
     * Formats a number using Nepali/Indian comma system and converts digits to Nepali.
     *
     * Example:
     * Input: 12345678
     * Output: १,२३,४५,६७८
     *
     * @param englishNumber input number
     * @return formatted string with Nepali digits and commas
     */
    public static String addNepaliCommas(Number englishNumber) {

        String num = convertToString(englishNumber);

        if (num.length() <= 3) {
            return toNepaliDigits(num);
        }

        StringBuilder sb = new StringBuilder();

        int len = num.length();

        // last 3 digits
        sb.insert(0, num.substring(len - 3));
        len -= 3;

        // remaining digits in pairs
        while (len > 0) {

            int start = Math.max(len - 2, 0);
            String part = num.substring(start, len);

            sb.insert(0, part + ",");
            len -= 2;
        }

        return toNepaliDigits(sb.toString());
    }

    /**
     * Internal recursive converter for number-to-word conversion.
     */
    private static String convert(long n) {

        if (n < 100) return UNIT[(int) n];
        if (n < 1_000) return twoPart(n, 100, " सय");
        if (n < 100_000) return twoPart(n, 1_000, " हजार");
        if (n < 10_000_000) return twoPart(n, 100_000, " लाख");
        if (n < 1_000_000_000) return twoPart(n, 10_000_000, " करोड");
        if (n < 100_000_000_000L) return twoPart(n, 1_000_000_000L, " अर्ब");

        return twoPart(n, 100_000_000_000L, " खर्ब");
    }

    /**
     * Helper for splitting number into quotient and remainder.
     */
    private static String twoPart(long n, long scale, String label) {

        long q = n / scale;
        long r = n % scale;

        StringBuilder sb = new StringBuilder();
        sb.append(convert(q)).append(label);

        if (r > 0) {
            sb.append(" ").append(convert(r));
        }

        return sb.toString();
    }

    /**
     * Converts input number to plain string representation.
     */
    private static String convertToString(Number input) {

        if (input == null) return "0";

        BigDecimal bd = (input instanceof BigDecimal)
                ? (BigDecimal) input
                : new BigDecimal(input.toString());

        return bd.stripTrailingZeros().toPlainString();
    }

    /**
     * Converts ASCII digits in a string into Nepali digits.
     */
    private static String toNepaliDigits(String input) {

        char[] arr = input.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c >= '0' && c <= '9') {
                arr[i] = NEPALI_DIGITS[c - '0'];
            }
        }

        return new String(arr);
    }
}