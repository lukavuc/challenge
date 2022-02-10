package com.lukavuc.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukavuc.challenge.domain.Event;
import com.lukavuc.challenge.domain.LogEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class LogFileParser {

    /**
     *
     * Reading line for line the input File
     * and then creating a List with same events.
     * @param logFilePath
     * @return events
     */
    public static List<Event> parse(String logFilePath) {

        List<Event> events = new ArrayList<>();

        HashMap<String, LogEntry> logEntries = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        try (
                FileReader reader = new FileReader(logFilePath);
                BufferedReader bufferedReader = new BufferedReader(reader);
        ){
            String jsonLogEntry;
            while ((jsonLogEntry = bufferedReader.readLine()) != null) {
                LogEntry logEntry = mapper.readValue(jsonLogEntry, LogEntry.class);
                if(logEntries.containsKey(logEntry.getId())) {
                    LogEntry storedLogEntry = logEntries.get(logEntry.getId());
                    long duration = calculateDuration(logEntry.getTimestamp(), storedLogEntry.getTimestamp());
                    boolean alert = duration >= 4;
                    events.add(new Event(logEntry.getId(), duration, logEntry.getType(), logEntry.getHost(), alert));
                } else {
                    logEntries.put(logEntry.getId(), logEntry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }
    private static long calculateDuration(long timestamp, long timestamp1) {
            return Math.abs(timestamp - timestamp1);
    }

    }


