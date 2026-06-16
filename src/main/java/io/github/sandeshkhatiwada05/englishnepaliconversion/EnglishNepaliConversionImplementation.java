package io.github.sandeshkhatiwada05.englishnepaliconversion;

import java.math.BigDecimal;

/**
 * English to Nepali number conversion utility.
 *
 * <p>
 * Supports:
 * <ul>
 *     <li>English digits → Nepali digits (०-९)</li>
 *     <li>Number → Nepali word representation</li>
 *     <li>Nepali comma formatting</li>
 * </ul>
 *
 * <p>
 * Number system used: Nepali (hundred, thousand, lakh, crore, arab, kharab)
 *
 * @author Sandesh Khatiwada
 */
public class EnglishNepaliConversionImplementation implements EnglishNepaliConversion {

    // ===================== DIGIT MAP =====================

    private static final char[] NEPALI_DIGITS = {
            '०', '१', '२', '३', '४', '५', '६', '७', '८', '९'
    };

    // ===================== WORD TABLE (0–99) =====================

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

    // ===================== API 1: DIGITS =====================

    /**
     * Converts English digits into Nepali digits.
     */
    @Override
    public String EnglishNumberToNepali(Number userInputNumber) {

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

    // ===================== API 2: SENTENCE =====================

    /**
     * Converts number into Nepali word sentence.
     */
    @Override
    public String EnglishNumberToNepaliSentence(Number number) {

        if (number == null) return "";

        BigDecimal bd = (number instanceof BigDecimal)
                ? (BigDecimal) number
                : new BigDecimal(number.toString());

        long n = bd.longValue();

        if (n == 0) return "शून्य";

        return convert(n);
    }

    // ===================== API 3: COMMA FORMAT =====================

    /**
     * Formats number using Nepali/Indian comma system and converts digits to Nepali.
     *
     * Example:
     * 12345678 → १,२३,४५,६७८
     */
    @Override
    public String addNepaliCommas(Number numNepali) {

        String num = convertToString(numNepali);

        int len = num.length();
        if (len <= 3) return toNepaliDigits(num);

        StringBuilder sb = new StringBuilder();

        int i = len;

        // last 3 digits
        sb.append(num, i - 3, i);
        i -= 3;

        while (i > 0) {

            int start = Math.max(i - 2, 0);

            sb.insert(0, "," + num.substring(start, i));

            i -= 2;
        }

        return toNepaliDigits(sb.toString());
    }

    // ===================== CORE RECURSION =====================

    private String convert(long n) {

        if (n < 100) {
            return UNIT[(int) n];
        }

        if (n < 1_000) {
            return twoPart(n, 100, " सय");
        }

        if (n < 100_000) {
            return twoPart(n, 1_000, " हजार");
        }

        if (n < 10_000_000) {
            return twoPart(n, 100_000, " लाख");
        }

        if (n < 1_000_000_000) {
            return twoPart(n, 10_000_000, " करोड");
        }

        if (n < 100_000_000_000L) {
            return twoPart(n, 1_000_000_000L, " अर्ब");
        }

        return twoPart(n, 100_000_000_000L, " खर्ब");
    }

    private String twoPart(long n, long scale, String label) {

        long q = n / scale;
        long r = n % scale;

        StringBuilder sb = new StringBuilder();
        sb.append(convert(q)).append(label);

        if (r > 0) {
            sb.append(" ").append(convert(r));
        }

        return sb.toString();
    }

    // ===================== UTIL =====================

    private String convertToString(Number input) {

        if (input == null) return "0";

        BigDecimal bd = (input instanceof BigDecimal)
                ? (BigDecimal) input
                : new BigDecimal(input.toString());

        return bd.stripTrailingZeros().toPlainString();
    }

    private String toNepaliDigits(String input) {

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