package com.lukavuc.challenge;

import com.lukavuc.challenge.domain.Event;
import com.lukavuc.challenge.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Autowired
	private EventRepository eventRepository;

	@Override
	public void run(String... args) throws Exception {
		if(args.length != 1) {
			throw new Exception("Exactly one argument expected.");
		}
		String logFilePath = args[0];
		List<Event> events = LogFileParser.parse(logFilePath);
		eventRepository.saveAll(events);
	}
}