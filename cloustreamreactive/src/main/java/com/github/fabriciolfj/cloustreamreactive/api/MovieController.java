package com.github.fabriciolfj.cloustreamreactive.api;

import com.github.fabriciolfj.cloustreamreactive.domain.Movie;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieController {

    private StreamBridge streamBridge;

    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void toMovieBingind(@RequestBody Movie movie) {
        var msg = MessageBuilder.withPayload(movie)
                .setHeader("tipo", "drama")
                .build();
        streamBridge.send("movie-out-0", msg);
        movie.setTitle("teste");
        movie.setActor("teste");
        movie.setYear(99999);
        streamBridge.send("movie-out-1", movie);
    }
}
