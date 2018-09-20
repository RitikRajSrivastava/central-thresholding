import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.IKafkaConstants;
import consumer.ConsumerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import producer.ProducerCreator;
import repository.Employee;
import service.JsonFilter;

import java.util.concurrent.ExecutionException;

public class App {

    private static JsonFilter kafkaFilter;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        runProducer();

        simpleProducer();
        runConsumer();

//        final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,"This is record " + index);
//
//        RecordMetadata metadata = producer.send(record).get();
    }

    /*static void runConsumer() throws InterruptedException {
        final Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
        final int giveUp = 100;   int noRecordsCount = 0;
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);
            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }*/


    static void runConsumer() {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        int noMessageToFetch = 0;

        int i = 0;
        while (true) {
//            System.out.println("entry:" + i++);
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT) {
//                    System.out.println("exit:" + i++);
                    break;
                } else {
//                    System.out.println("entry:" + i++);
                    continue;
                }
            }

//            System.out.println("entry:" + i++);
            consumerRecords.forEach(record -> {
//                System.out.println("Record Key " + record.key());

                String value = record.value();
                System.out.println("Record value " + value);

                JsonFilter kafkaFilter = new JsonFilter();

                kafkaFilter.filter(value);

//                System.out.println("Record partition " + record.partition());
//                System.out.println("Record offset " + record.offset());
            });
            consumer.commitAsync();
        }
//        System.out.println("exit:" + i++);
        consumer.close();
    }

    static void runProducer() {
        Producer<Long, String> producer = ProducerCreator.createProducer();

        for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
            final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
                    "This is record " + index);
            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            } catch (ExecutionException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            } catch (InterruptedException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            }
        }
    }

    static void simpleProducer() throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        Employee testEmp = new Employee("x", 1);

        JsonNode node = mapper.valueToTree(testEmp);


        Producer<Long, String> producer = ProducerCreator.createProducer();
        final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME,
                node.toString());

        /*RecordMetadata metadata = */
        producer.send(record).get();
    }
}
