# Kafka Setup with Zookeeper

Kafka uses Zookeeper to manage distributed services, so you need to start Zookeeper first. Below are the steps to get Kafka and Zookeeper up and running, create topics, produce, consume messages, and manage topics.

## Step 1: Start Zookeeper

1. Open a **Command Prompt** and navigate to the Kafka directory:

    ```bash
    cd C:\kafka
    ```

2. Start Zookeeper:

    ```bash
    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
    ```

   This will start the Zookeeper service on the default port **2181**.

## Step 2: Start Kafka Server

1. Open a **New Command Prompt** and navigate to the Kafka directory again:

    ```bash
    cd C:\kafka
    ```

2. Start the Kafka Server:

    ```bash
    .\bin\windows\kafka-server-start.bat .\config\server.properties
    ```

   This will start the Kafka broker on the default port **9092**.

## Step 3: Create Kafka Topics

Once the Kafka server is running, you can create topics.

1. Open **Another Command Prompt** and navigate to the Kafka directory:

    ```bash
    cd C:\kafka
    ```

2. Create a Kafka Topic:

    ```bash
    .\bin\windows\kafka-topics.bat --create --topic <your-topic-name> --bootstrap-server localhost:9092 --partitions <number-of-partitions> --replication-factor <replication-factor>
    ```

    Example to create a topic called `payment_request_topic` with 1 partition and a replication factor of 1:

    ```bash
    .\bin\windows\kafka-topics.bat --create --topic payment_request_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
    ```

    Example to create a `payment_response_topic`:

    ```bash
    .\bin\windows\kafka-topics.bat --create --topic payment_response_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
    ```

## Step 4: Produce and Consume Messages

### Produce a Message

To start producing messages on the topic:

```bash
.\bin\windows\kafka-console-producer.bat --topic <your-topic-name> --bootstrap-server localhost:9092
