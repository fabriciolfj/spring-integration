## Stream Creation
curl -s -X POST \
 --form 'name=movie' \
 --form 'definition=movie=http --port=9001 | splitter --expression="#jsonPath(payload,'\''$.movies'\'')" | groovy-transform --script=https://raw.githubusercontent.com/fabriciolfj/movie-stream/main/movie-transform.groovy | jdbc --columns=id:id,title:title,actor:actor,year:year,genre:genre,stars:stars,rating:imdb.rating,ratingcount:imdb.ratingCount --table-name=movies --password=rootpw --driver-class-name=org.mariadb.jdbc.Driver --username=root --url=jdbc:mysql://mysql:3306/reviews?autoReconnect=true&useSSL=false' \
 http://10.27.28.20:31499/streams/definitions | jq .


curl -s -X POST \
--form 'name=stars' \
--form 'definition=stars= :movie.splitter > filter --expression="#jsonPath(payload,'\''$.stars'\'') > 3" | log' \
http://localhost:9393/streams/definitions | jq .


curl -s -X POST \
--form 'name=imdb-high-rating' \
--form 'definition=imdb-high-rating= :movie.groovy-transform > filter --expression="#jsonPath(payload,'\''$.imdb.rating'\'') > 8.0" | log' \
http://localhost:9393/streams/definitions | jq .


