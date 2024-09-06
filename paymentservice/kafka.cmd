@echo off

.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

REM Start Kafka server using the specified configuration file
.\bin\windows\kafka-server-start.bat .\config\server.properties

REM Start another Kafka server instance (if needed)
REM Uncomment the next line if you want to start a second server
REM .\bin\windows\kafka-server-start.bat .\config\server.properties

REM Create a Kafka topic named payment_request_topic
.\bin\windows\kafka-topics.bat --create --topic payment_request_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

REM Create a Kafka topic named payment_response_topic
.\bin\windows\kafka-topics.bat --create --topic payment_response_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

REM Add more commands as needed
