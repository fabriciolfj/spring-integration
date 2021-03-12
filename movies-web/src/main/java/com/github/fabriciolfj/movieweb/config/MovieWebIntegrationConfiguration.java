package com.github.fabriciolfj.movieweb.config;

import com.github.fabriciolfj.movieweb.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.messaging.MessageHandler;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(MovieWebProperties.class)
public class MovieWebIntegrationConfiguration {

    private MovieWebProperties movieWebProperties;

    @Bean
    public IntegrationFlow httpFlow() {
        return IntegrationFlows.from(Http.inboundChannelAdapter(movieWebProperties.getPath()).requestPayloadType(Movie.class).requestMapping(m -> //aceita a requisicao
                m.methods(HttpMethod.POST)))
                .channel(MessageChannels.publishSubscribe("publisher")) //publica nesse canal chamado publisher
                .get();
    }

    @Bean
    public IntegrationFlow logFlow() {
        return IntegrationFlows.from("publisher")
                .log(LoggingHandler.Level.INFO, "Movie", m -> m).get();
    }

    @Bean
    @ServiceActivator(inputChannel = "publisher") //outra maneira que usar um ouvinte no canal
    public MessageHandler process(DataSource dataSource) {
        JdbcMessageHandler jdbcMessageHandler = new JdbcMessageHandler(dataSource, "insert into movies (title, actor, year) values (?,?,?)");
        jdbcMessageHandler.setPreparedStatementSetter((ps, message) -> {
            ps.setString(1, ((Movie) message.getPayload()).getTitle());
            ps.setString(2, ((Movie) message.getPayload()).getActor());
            ps.setInt(3, ((Movie) message.getPayload()).getYear());
        });

        jdbcMessageHandler.setOrder(1);
        return jdbcMessageHandler;
    }


}
