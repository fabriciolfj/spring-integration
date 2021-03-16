# spring-integration

### Spring cloud stream
- Defina o nome dos grupos de consumidos, pois isso deixa as mensagnes duraveis (caso não defina, spring cria um grupo anonima mensagem não durável, ou seja, caso o grupo de consumidos esteja fora, ao subir não háverá mensagens).

#### Tipos de canais
- Source: origem, saida
- Sink: destino, entrada
- Processor: processador, precisa de uma entrada e saida. Exemplo
```
@EnableBinding(Processor.class)
public class MovieStream {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Movie process(Movie movie) {
        movie.setTitle(movie.getTitle().toUpperCase());
        return movie;
    }
}


```

### Convenções
- Para configurar, segue o seguinte padrão:
```
spring.cloud.stream.bindings.[channel-name].destination=[new-channel-name]

o codigo configurado abaixo, produzirá a configuração em seguida:

spring.cloud.stream.bindings.new-movie.destination=new-movie-event

public interface MovieGenre {
    
    @Input("new-movie")
    SubscribableChannel movie();

}
```
