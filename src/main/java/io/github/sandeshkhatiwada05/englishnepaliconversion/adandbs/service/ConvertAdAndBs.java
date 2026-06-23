package io.github.sandeshkhatiwada05.englishnepaliconversion.adandbs.service;

import io.github.sandeshkhatiwada05.englishnepaliconversion.adandbs.config.CalendarDataLoader;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Utility class for converting between AD (Gregorian calendar)
 * and BS (Bikram Sambat calendar).
 *
 * 
 * This converter uses a preloaded BS calendar dataset and a fixed reference mapping:
 * BS 2026 day 264 corresponds to AD 1970-01-01.
 * 
 *
 * 
 * All methods are static and stateless, making them thread-safe.
 * 
 *
 * 
 * Note: This class depends on complete BS calendar data being available
 * for all supported years in {@link CalendarDataLoader}.
 * 
 */
public final class ConvertAdAndBs {

    /**
     * Preloaded BS calendar data.
     * Key: BS year
     * Value: int array of month lengths (12 months + total days at index 12)
     */
    private static final Map<Integer, int[]> CALENDAR =
            CalendarDataLoader.getCalendarData();

    /**
     * Reference AD date used as anchor point for conversion.
     */
    private static final LocalDate AD_REFERENCE =
            LocalDate.of(1970, 1, 1);

    /**
     * Reference BS year used in conversion mapping.
     */
    private static final int BS_REFERENCE_YEAR = 2026;

    /**
     * Reference day within BS year that maps to AD_REFERENCE.
     */
    private static final int BS_REFERENCE_DAY = 264;

    private ConvertAdAndBs() {
        // utility class
    }

    /**
     * Converts a BS date (year, month, day) to AD date.
     *
     * @param year BS year
     * @param month BS month (1-12)
     * @param day BS day (1-based day of month)
     * @return corresponding AD {@link LocalDate}
     * @throws IllegalArgumentException if date is invalid or unsupported
     */
    public static LocalDate bsToAd(int year, int month, int day) {

        validateBsDate(year, month, day);

        long diff = calculateDayDifferenceFromReference(year, month, day);

        return AD_REFERENCE.plusDays(diff);
    }

    /**
     * Converts a BS date object to AD date.
     *
     * @param date BS date object
     * @return corresponding AD {@link LocalDate}
     */
    public static LocalDate bsToAd(NepaliDate date) {
        return bsToAd(date.year(), date.month(), date.day());
    }

    /**
     * Converts AD date to BS date.
     *
     * @param adDate Gregorian calendar date
     * @return corresponding BS {@link NepaliDate}
     */
    public static NepaliDate adToBs(LocalDate adDate) {

        long diff = ChronoUnit.DAYS.between(AD_REFERENCE, adDate);

        return (diff >= 0)
                ? convertForward(diff)
                : convertBackward(-diff);
    }

    /**
     * Converts forward from reference BS year.
     */
    private static NepaliDate convertForward(long diff) {

        int year = BS_REFERENCE_YEAR;
        long total = BS_REFERENCE_DAY + diff;

        while (total > CALENDAR.get(year)[12]) {
            total -= CALENDAR.get(year)[12];
            year++;
        }

        return findBsDate(year, (int) total);
    }

    /**
     * Converts backward from reference BS year.
     */
    private static NepaliDate convertBackward(long diff) {

        int year = BS_REFERENCE_YEAR;
        long remaining = BS_REFERENCE_DAY - diff;

        while (remaining <= 0) {
            year--;
            remaining += CALENDAR.get(year)[12];
        }

        return findBsDate(year, (int) remaining);
    }

    /**
     * Finds BS month/day from total day count.
     */
    private static NepaliDate findBsDate(int year, int days) {

        int[] months = CALENDAR.get(year);
        int month = 1;

        for (int i = 0; i < 12; i++) {
            if (days <= months[i]) {
                month = i + 1;
                break;
            }
            days -= months[i];
        }

        return new NepaliDate(year, month, days);
    }

    /**
     * Calculates day difference between input BS date and reference date.
     */
    private static long calculateDayDifferenceFromReference(int year, int month, int day) {

        int passedDays = passedDaysInYear(year, month, day);
        long dayDiff = 0;

        if (year > BS_REFERENCE_YEAR) {

            for (int i = BS_REFERENCE_YEAR + 1; i < year; i++) {
                dayDiff += CALENDAR.get(i)[12];
            }

            dayDiff += passedDays + 102;

        } else if (year < BS_REFERENCE_YEAR) {

            for (int i = BS_REFERENCE_YEAR - 1; i > year; i--) {
                dayDiff -= CALENDAR.get(i)[12];
            }

            dayDiff -= ((CALENDAR.get(year)[12] - passedDays) + 264);

        } else {
            dayDiff = passedDays - 264;
        }

        return dayDiff;
    }

    /**
     * Calculates passed days in a BS year up to given month/day.
     */
    private static int passedDaysInYear(int year, int month, int day) {

        int total = 0;
        int[] months = CALENDAR.get(year);

        for (int i = 0; i < month - 1; i++) {
            total += months[i];
        }

        return total + day;
    }

    /**
     * Validates BS date before conversion.
     *
     * @throws IllegalArgumentException if year/month/day is invalid
     */
    private static void validateBsDate(int year, int month, int day) {

        if (!CALENDAR.containsKey(year)) {
            throw new IllegalArgumentException("Unsupported BS year: " + year);
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }

        if (day < 1 || day > CALENDAR.get(year)[month - 1]) {
            throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

    private record NepaliDate(
            int year,
            int month,
            int day
    ) {
    }
}