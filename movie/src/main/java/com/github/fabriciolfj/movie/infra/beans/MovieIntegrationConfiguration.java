package com.github.fabriciolfj.movie.infra.beans;

import com.github.fabriciolfj.movie.infra.properties.MovieProperties;
import com.github.fabriciolfj.movie.infra.util.MovieConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.http.dsl.Http;

import java.io.File;
import java.net.URI;

@Configuration
public class MovieIntegrationConfiguration {

    @Autowired
    private MovieProperties movieProperties;
    @Autowired
    private MovieConverter movieConverter;

    @Bean
    public IntegrationFlow fileFlow() {
        return IntegrationFlows.from(Files.inboundAdapter(new File(this.movieProperties.getDirectory()))
                .preventDuplicates(true)
                .patternFilter(this.movieProperties.getFilePattern()),
                e -> e.poller(Pollers.fixedDelay(this.movieProperties.getFixedDelay()))) //vou revisitar em x segundos
               .split(Files.splitter().markers()) //dividi o arquivo em linhas e marca Start e end (no inico e fim do arquivo)
               .filter(p -> !(p instanceof FileSplitter.FileMarker)) //verificar se são linhas e nao os marcadores Start  e end00000000000000,
               .transform(Transformers.converter(this.movieConverter))
                .transform(Transformers.toJson())
                .handle(Http.outboundChannelAdapter(URI.create(this.movieProperties.getRemoteService()))
                .httpMethod(HttpMethod.POST))
                .get();
    }
}
