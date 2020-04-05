/*- Jars location -*/
http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server-local/2.1.0.RELEASE/spring-cloud-dataflow-server-local-2.1.0.RELEASE.jar

http://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.1.0.RELEASE/spring-cloud-dataflow-shell-2.1.0.RELEASE.jar

http://repo.spring.io/release/org/springframework/cloud/spring-cloud-skipper-server/2.0.2.RELEASE/spring-cloud-skipper-server-2.0.2.RELEASE.jar

/*- RabbitMq -*/
https://www.rabbitmq.com/install-debian.html

/*Execute jars*/
java -jar spring-cloud-skipper-server-2.0.2.RELEASE.jar
java -jar spring-cloud-dataflow-server-2.1.0.RELEASE.jar
java -jar spring-cloud-dataflow-shell-2.1.0.RELEASE.jar

/*Local UI Data-flow-server*/
localhost:9393/dashboard/

/*Stream App Starters and Spring Cloud Data Flow (**)*/
/*Apps-> add Application -> second option(Bulk import application coordinates from an HTTP URI location. )*/
https://dataflow.spring.io/rabbitmq-maven-latest

/*Task App Starters and Spring Cloud Data Flow (**)*/
https://dataflow.spring.io/task-maven-latest


/*Setting enviroment variables*/
export DATAFLOW_VERSION=2.1.0.RELEASE
export SKIPPER_VERSION=2.0.2.RELEASE

/*Downloading the Docker Compose File*/
https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/master/spring-cloud-dataflow-server/docker-compose.yml
/*Donloading the docker compose file rabbitmq*/
https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/master/spring-cloud-dataflow-server/docker-compose-rabbitmq.yml


/*connect to docker Spring Cloud Data Flow shell*/

docker exec -it dataflow-server java -jar shell.jar

/*Create stream by shell the ip of mysql should be of the mysql docker instance*/
stream create --name mysqlstream1 --definition "http --server.port=9010 | jdbc --tableName=names --columns=name,lastname,firstname --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver --spring.datasource.url='jdbc:mysql://172.30.0.3:3306/test' --spring.datasource.username=root --spring.datasource.password='rootpw'" --deploy

/*Insert a row in db the ip is of skipper server*/
http post --contentType 'application/json;charset=UTF-8' --target http://172.30.0.5:9010 --data "{\"firstname\": \"drohne\", \"lastname\":\"enhord\", \"name\":\"dro\"}"


/*COMMANDs*/
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' name_of_container

