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

### Versão 3
- Uma das principais adições, é a capacidade de usar funções para produzir ou consumir mensagens.
- Não precisamos adicionar qualquer anotação extra como @EnabledBinding ou @StreamListener.
- Tipos de contexto:
  -  supplier: seria o source
  -  function: seria o processor
  -  consumer: seria o sink
- Possibilidade de usar programação reactive.

#### Convernsão de nomes
- Para vinculação de entrada:
```
spring.cloud.stream.binding.<nome da funcao>+in+<index>
```
- Para vinculação de saida
```
spring.cloud.stream.binding.<nome da funcao>+out+<index>

spring.cloud.stream.bindings.uppercase-in-0.destination=uppercase
spring.cloud.stream.bindings.uppercase-out-0.destination=log
```
- Indice sempre começa com 0, o que tem a ver com as funções com múltiplos argumentos de entrada e saída.

- Exemplo de configuração utilizando diversas functions:
```
spring.cloud.stream.bindings.movie-out-0.destination=movie
spring.cloud.stream.bindings.movie-out-1.destination=movie2

spring.cloud.function.definition=onlyGenreTest;titleUpperCase
spring.cloud.stream.bindings.onlyGenreTest-in-0.destination=movie
spring.cloud.stream.bindings.onlyGenreTest-in-1.destination=movie2
spring.cloud.stream.bindings.onlyGenreTest-out-0.destination=titleUpperCase
spring.cloud.stream.bindings.titleUpperCase-in-0.destination=titleUpperCase
spring.cloud.stream.bindings.titleUpperCase-out-0.destination=log

```
- Exemplo de funções com base na configuração acima: (obs os índices).

```
    @Bean
    public Function<Tuple2<Flux<Movie>, Flux<Movie>>, Flux<Movie>> onlyGenreTest() {
        return flux ->
            flux.getT1().filter(movie -> {
                log.info("Valor tipo 2: {}", flux.getT2().blockFirst().toString());
                log.info("Filter: {}", movie);
                return movie.getGenre().equalsIgnoreCase(GENRE);
            });
    }

    @Bean
    public Function<Flux<Movie>, Flux<Movie>> titleUpperCase() {
        return movieFlux -> movieFlux.map(movie -> {
            log.info("Uppercase: {}", movie);
            movie.setTitle(movie.getTitle().toUpperCase());
            return movie;
        });
    }
```

#### Rotas
- Podemos direcionar para funções, com base em alguns critérios, como valor de uma propriedade no header.
- Devemos habilitar a configuração abaixo e automaticamente o sistema criará os bindings functionRouter-in-0 e funcitonRouter-out-0.
```
spring.cloud.stream.function.routing.enabled=true
spring.cloud.function.routing-expression=headers['genre']
```
## Spring cloud data flow
- É uma tecnologia de código aberto que compõe topologias complexas para pipelines de dados de streaming e lote.
- Utiliza-se microservices pré construídos e permite faz a integração entre eles.
- Um orchestrator de spring cloud stream
- Cria-se aplicativos com base no spring cloud stream por ele
- Em um ambiente kubernetes, ele cria um pod para cada cloud stream.
- Em resumo, spring data flow orquestra vários aplicativos stream, criando, implantando, destruindo e integrando (criando fluxo, definindo qual aplicativo é a origem (source), que processa (proccess) e que recebe a mensagem (sink).

#### Comandos via shell
```
wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.4.2.RELEASE/spring-cloud-dataflow-shell-2.4.2.RELEASE.jar 

java -jar spring-cloud-dataflow-shell-2.4.2.RELEASE.jar

dataflow config server --uri http://192.168.64.6:30724

app list

// importar rabbitmq ou kafka
app import --uri https://dataflow.spring.io/rabbitmq-docker-latest

stream create --name simple --definition "time | log"

stream list

stream info --name simple

//subir os streams
stream deploy --name simple


```

## Skipper server
- Server para atualizar as aplicações
- Registrar os historicos de deploy
- efetuar deploy
- rollbacks de apps.

## Criando uma pipeline
- Segue abaixo uma forma de criar uma pippeline usando cloud stream (obs: cada etapa é uma aplicação):
```
movie = http | splitter | groovy-transform | jdbc > log
imdb = :movie.groovy-transform > filter

movie = , seria uma variável ou nome da dsl.

aplicativo http, envie mensagem para aplicativo splitter, que envia mensagem para o aplicativo groovy transform que envia ao aplicativo jdbc salvar na base, e redirecionando uma cópia da mensagem para o aplicativo de log.

imbd, ficará ouvindo até a etapa do movie.groovy-transform e quando chegar nessa etapa, receberá uma cópia da mensagem (resultado do processamento de groovy-transform) e encaminhará para aplicação filter.
```

#### Registrando aplicações
```
Jar no gihub
dataflow:>app register --name movie-imdb --type source --uri https://github.com/fabriciolfj/spring-data-flow/blob/main/movie-source-0.0.1.jar

Imagem docker
docker://fabricio211/imagem:versao

maven
app register --name splitter --type processor --uri maven://org.springframework.cloud.stream.app:splitter-processor-rabbit:2.1.2.RELEASE --metadata-uri maven://org.springframework.cloud.stream.app:splitter-processor-rabbit:jar:metadata:2.1.2.RELEASE

```

- Podemos personalizar as configurações das aplicações, no momento do deploy, através da aba freetext
```
app.movie-processor.movie.header-key=e1d57bc780msh2ab0500b21047acp10b199jsnd21a2e07bb7e
app.movie-processor.spring.cloud.stream.bindings.input.destination=imdb
app.movie-processor.spring.cloud.stream.bindings.output.destination=log
app.movie-sink.spring.cloud.stream.bindings.input.destination=log
app.movie-source.server.port=8081
app.movie-source.spring.cloud.stream.bindings.output.destination=movie
app.splitter.expression=#jsonPath(payload,'$.MovieRequest.movies')
app.splitter.spring.cloud.stream.bindings.input.destination=movie
app.splitter.spring.cloud.stream.bindings.output.destination=imdb
spring.cloud.dataflow.skipper.platformName=default

app.movie-processor.movie.header-key=e1d57bc780msh2ab0500b21047acp10b199jsnd21a2e07bb7e
app.movie-processor.spring.cloud.stream.bindings.input.destination=movie
app.movie-processor.spring.cloud.stream.bindings.output.destination=log
app.movie-sink.spring.cloud.stream.bindings.input.destination=log
app.movie-source.server.port=8080
app.movie-processor.server.port=8080
app.movie-sink.server.port=8080
app.movie-source.spring.cloud.stream.bindings.output.destination=movie
spring.cloud.dataflow.skipper.platformName=default
```
