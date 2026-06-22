package io.github.sandeshkhatiwada05.englishnepaliconversion.AdAndBs.Service;

import io.github.sandeshkhatiwada05.englishnepaliconversion.AdAndBs.config.CalendarDataLoader;
import io.github.sandeshkhatiwada05.englishnepaliconversion.AdAndBs.model.NepaliDate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Core utility for converting between AD (Gregorian calendar)
 * and BS (Bikram Sambat calendar).
 *
 * <p>
 * Uses a preloaded calendar dataset and a fixed reference mapping:
 * BS 2026, day 264 <-> AD 1970-01-01.
 * </p>
 *
 * <p>
 * All methods are static and thread-safe.
 * </p>
 *
 * @author Sandesh Khatiwada
 */

public final class AdAndBsConverter {

    private static final Map<Integer, int[]> CALENDAR =
            CalendarDataLoader.getCalendarData();

    private static final LocalDate AD_REFERENCE =
            LocalDate.of(1970, 1, 1);

    private static final int BS_REFERENCE_YEAR = 2026;
    private static final int BS_REFERENCE_DAY = 264;

    private AdAndBsConverter() {
        // prevent instantiation
    }

    /**
     * Converts BS date to AD date.
     */
    public static LocalDate bsToAd(int year, int month, int day) {

        validateBsDate(year, month, day);

        long diff = calculateDayDifferenceFromReference(year, month, day);

        LocalDate result = AD_REFERENCE.plusDays(diff);

        return result;
    }

    /**
     * Converts BS date object to AD date.
     */
    public static LocalDate bsToAd(NepaliDate date) {
        return bsToAd(date.year(), date.month(), date.day());
    }

    /**
     * Converts AD date to BS date.
     */
    public static NepaliDate adToBs(LocalDate adDate) {


        long diff = ChronoUnit.DAYS.between(AD_REFERENCE, adDate);

        return
                (diff >= 0)
                        ? convertForward(diff)
                        : convertBackward(-diff);
    }

    private static NepaliDate convertForward(long diff) {

        int year = BS_REFERENCE_YEAR;
        long total = BS_REFERENCE_DAY + diff;

        while (total > CALENDAR.get(year)[12]) {
            total -= CALENDAR.get(year)[12];
            year++;
        }

        return findBsDate(year, (int) total);
    }

    private static NepaliDate convertBackward(long diff) {

        int year = BS_REFERENCE_YEAR;
        long remaining = BS_REFERENCE_DAY - diff;

        while (remaining <= 0) {
            year--;
            remaining += CALENDAR.get(year)[12];
        }

        return findBsDate(year, (int) remaining);
    }

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

    private static long calculateDayDifferenceFromReference(
            int year, int month, int day
    ) {

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

    private static int passedDaysInYear(int year, int month, int day) {

        int total = 0;
        int[] months = CALENDAR.get(year);

        for (int i = 0; i < month - 1; i++) {
            total += months[i];
        }

        return total + day;
    }

    private static void validateBsDate(int year, int month, int day) {

        if (!CALENDAR.containsKey(year)) {
            throw new IllegalArgumentException("Unsupported BS year: " + year);
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }

        if (day < 1 || day > CALENDAR.get(year)[month - 1]) {
            throw new IllegalArgumentException("Invalid day");
        }
    }
}