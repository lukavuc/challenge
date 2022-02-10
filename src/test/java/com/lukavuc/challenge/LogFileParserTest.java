package com.lukavuc.challenge;

import com.lukavuc.challenge.domain.Event;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LogFileParserTest {

    @Test
    public void givenACorrectLogFile_itParsesCorrectly() {
        String logFilePath = "testlogfile.txt";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(logFilePath).getFile());
        String absolutePath = file.getAbsolutePath();

        List<Event> events = LogFileParser.parse(absolutePath);

        Optional<Event> scsmbstgraOptional = events.stream()
                .filter(e -> e.getId().equals("scsmbstgra"))
                .findFirst();
        assertEquals(3, events.size());
        assertTrue(scsmbstgraOptional.isPresent());
        Event scsmbstgra = scsmbstgraOptional.get();
        assertEquals("APPLICATION_LOG", scsmbstgra.getType());
        assertEquals(6, scsmbstgra.getDuration());
        assertEquals("12345", scsmbstgra.getHost());
    }

    /*

    @Test
    public void givenACorruptlogFile_aUsefulExceptionIsThrown() {

    }
    */
}

