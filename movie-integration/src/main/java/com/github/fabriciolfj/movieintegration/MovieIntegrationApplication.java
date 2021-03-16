package com.github.fabriciolfj.movieintegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.movieintegration.domain.Movie;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@SpringBootApplication
public class MovieIntegrationApplication implements CommandLineRunner {

	@Autowired
	private StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(MovieIntegrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*final var movie = Movie.builder()
				.actor("keanu")
				.title("Matrix")
				.year(1999)
				.build();
		final var mapper = new ObjectMapper();
		final var json =  mapper.writeValueAsString(movie);
		final var msg = MessageBuilder.withPayload(json).build();
		streamBridge.send("log-in-0", msg);
		log.info("Msg send: {}", json);*/
	}
}
