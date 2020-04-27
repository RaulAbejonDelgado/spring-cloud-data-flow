### Jars location

http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server-local/2.1.0.RELEASE/spring-cloud-dataflow-server-local-2.1.0.RELEASE.jar

http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.1.0.RELEASE/spring-cloud-dataflow-shell-2.1.0.RELEASE.jar

http://repo.spring.io/release/org/springframework/cloud/spring-cloud-skipper-server/2.0.2.RELEASE/spring-cloud-skipper-server-2.0.2.RELEASE.jar

### RabbitMq 
https://www.rabbitmq.com/install-debian.html

### Execute jars
java -jar spring-cloud-skipper-server-2.0.2.RELEASE.jar

java -jar spring-cloud-dataflow-server-2.1.0.RELEASE.jar

java -jar spring-cloud-dataflow-shell-2.1.0.RELEASE.jar

### Local UI Data-flow-server
localhost:9393/dashboard/

### Stream App Starters and Spring Cloud Data Flow 
##### Apps-> add Application -> second option(Bulk import application coordinates from an HTTP URI location. )_/
https://dataflow.spring.io/rabbitmq-maven-latest

##### Task App Starters and Spring Cloud Data Flow (\*\*)_/
https://dataflow.spring.io/task-maven-latest

### Setting enviroment variables
export DATAFLOW_VERSION=2.1.0.RELEASE

export SKIPPER_VERSION=2.0.2.RELEASE

### Downloading the Docker Compose File
https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/master/spring-cloud-dataflow-server/docker-compose.yml
### Donloading the docker compose file rabbitmq
https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/master/spring-cloud-dataflow-server/docker-compose-rabbitmq.yml

### Up docker-compose replacing kafka by rabbitmq
docker-compose -f ./docker-compose.yml -f ./docker-compose-rabbitmq.yml up

### connect to docker Spring Cloud Data Flow shell

docker exec -it dataflow-server java -jar shell.jar

### Create stream by shell the ip of mysql should be of the mysql docker instance_/
stream create --name mysqlstream1 --definition "http --server.port=9010 | jdbc --tableName=names --columns=name,lastname,firstname --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver --spring.datasource.url='jdbc:mysql://172.30.0.3:3306/test' --spring.datasource.username=root --spring.datasource.password='rootpw'" --deploy

### Insert a row in db the ip is of skipper server_/
http post --contentType 'application/json;charset=UTF-8' --target http://172.30.0.5:9010 --data "{\\"firstname\\": \\"drohne\\", \\"lastname\\":\\"enhord\\", \\"name\\":\\"dro\\"}"

### COMMANDs
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' name_of_container

### Tweeter Stream

stream create tweets --definition "twitterstream --consumerKey=MXpwTQbcvmhT7HIZhC5JXZ7yc --consumerSecret=NAQyuP6yYDDmmUGAU50Az92u51EBCNrV7e8NkKFnQ4ArDuHaYa --accessToken=313483553-nFOfhCVSThta4ERYcfpf0enWAaEEWFdbbRFN0QET --accessTokenSecret=NF8EO9WVAAYj8YfdfJvTRLKMX8elidbYhQwtdXD77WAZb | log" --deploy

##### Tweets by lang 
stream create tweetlang  --definition ":tweets.twitterstream > counter --name=language --counter.tag.expression.lang=#jsonPath(payload,'$..[?(@.lang!=null)].lang')" --deploy

sort_desc(topk(10, sum(language_total) by (lang)))

##### Tweets by hastag 
stream create tagcount  --definition ":tweets.twitterstream > counter --name=hashtags --counter.tag.expression.htag=#jsonPath(payload,'$.entities.hashtags[*].text')" --deploy

sort_desc(topk(100, sum(hashtags_total) by (htag)))

### tweeter sentinel
stream create tweetsentiment --definition "tweets=twitterstream --consumerKey=MXpwTQbcvmhT7HIZhC5JXZ7yc --consumerSecret=NAQyuP6yYDDmmUGAU50Az92u51EBCNrV7e8NkKFnQ4ArDuHaYa --accessToken=313483553-nFOfhCVSThta4ERYcfpf0enWAaEEWFdbbRFN0QET --accessTokenSecret=NF8EO9WVAAYj8YfdfJvTRLKMX8elidbYhQwtdXD77WAZb | filter --expression=#jsonPath(payload,'$.lang')=='en' | twitter-sentiment --vocabulary='https://dl.bintray.com/big-data/generic/vocab.csv' --output-name=output/Softmax --model='https://dl.bintray.com/big-data/generic/minimal_graph.proto' --model-fetch=output/Softmax | log" --deploy

### Copy files to docker container

docker cp ../source/. ubuntu_dataFlow://home/remote_user/source/


### Tensor flow
stream create tensoflow-1 --definition "object-detector-stream=file --directory='/home/remote_user/spring-cloud-data-flow/source' | object-detection --tensorflow.model='https://dl.bintray.com/big-data/generic/faster_rcnn_resnet101_coco_2018_01_28_frozen_inference_graph.pb' --tensorflow.model-fetch='detection_scores,detection_classes,detection_boxes' --tensorflow.object.detection.labels='https://dl.bintray.com/big-data/generic/mscoco_label_map.pbtxt' | log" --deploy

stream create tensoflow-1-file --definition "object-detector-stream=file --directory='/home/drohne/spring-cloud-data-flow/source' | object-detection --tensorflow.mode=header --tensorflow.model='https://dl.bintray.com/big-data/generic/faster_rcnn_resnet101_coco_2018_01_28_frozen_inference_graph.pb' --tensorflow.model-fetch='detection_scores,detection_classes,detection_boxes' --tensorflow.object.detection.labels='https://dl.bintray.com/big-data/generic/mscoco_label_map.pbtxt' | file-sink: file --directory='/home/drohne/spring-cloud-data-flow/sink' --name-expression='headers[file_name]'" --deploy

### TEnsor flow pose estimation_/
stream create pose-detection --definition "file-source: file --directory='/home/drohne/spring-cloud-data-flow/pose-source' | pose-estimation --mode=header | file-sink: file --directory='/home/drohne/spring-cloud-data-flow/sink' --name-expression='headers[file_name]'" --deploy

### Adding custom task to application_/
app register --name custom-task --type task --uri https://github.com/RaulAbejonDelgado/spring-cloud-data-flow/raw/master/task-system-logging/task-system-logging-0.0.1-SNAPSHOT.jar

### Create custom task
task create --name mysystemloggingTask --definition "custom-task"

### Adding custom proceesor to application
app register --name custom-processor --type processor --uri https://github.com/RaulAbejonDelgado/spring-cloud-data-flow/raw/master/custom-processor/custom-processor-0.0.1-SNAPSHOT.jar

### Create stream_/
stream create --name celsiusConverter --definition "http --port=9091 | custom-processor | log" --deploy

### post_/
http post --target http://172.31.0.5:9091 --data 1500

### Destroy stream
stream destroy --name celsiusConverter

### Unregister app
app unregister --name celsiusConverter --type processor

### Adding custom source to application_/
app register --name custom-source --type source --uri https://github.com/RaulAbejonDelgado/spring-cloud-data-flow/raw/master/custom-source/custom-source-0.0.1-SNAPSHOT.jar

### create stream_/
stream create --name custom-source-stream --definition "custom-source | log" --deploy

### Adding custom sink to application_/
app register --name custom-sink --type sink --uri https://github.com/RaulAbejonDelgado/spring-cloud-data-flow/raw/master/custom-sink/custom-sink-0.0.1-SNAPSHOT.jar
### stream_/
stream create --name custom-sink --definition "http --port=9092 | custom-sink" --deploy

### post_/
http post --target http://172.31.0.5:9092 --data 01-jun-2020

### custom stream application with custom source, processor, sink_/

##### Adding custom stream to application_/

app register --name time-processor --type processor --uri https://github.com/RaulAbejonDelgado/spring-cloud-data-flow/raw/master/custom-stream-application/custom-stream-application-0.0.1-SNAPSHOT.jar

###  stream/
stream create --name myTimeSteram --definition "custom-source | time-processor | custom-sink" --deploy
