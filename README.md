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
