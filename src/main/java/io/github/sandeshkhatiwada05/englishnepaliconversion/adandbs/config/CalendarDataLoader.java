package io.github.sandeshkhatiwada05.englishnepaliconversion.adandbs.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class CalendarDataLoader {

    private static final Map<Integer, int[]> CALENDAR_DATA = load();

    private CalendarDataLoader() {
    }

    public static Map<Integer, int[]> getCalendarData() {
        return CALENDAR_DATA;
    }

    private static Map<Integer, int[]> load() {

        Map<Integer, int[]> data = new HashMap<>();

        try (
                InputStream is =
                        CalendarDataLoader.class.getResourceAsStream("/bs-calendar.csv");

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(is))
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",");

                int year = Integer.parseInt(parts[0]);

                int[] months = new int[13];

                for (int i = 1; i <= 13; i++) {
                    months[i - 1] = Integer.parseInt(parts[i]);
                }

                data.put(year, months);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to load BS calendar", e);
        }

        return data;
    }
}
