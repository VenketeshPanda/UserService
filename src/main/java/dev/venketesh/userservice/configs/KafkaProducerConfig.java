package dev.venketesh.userservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;


@Configuration
public class KafkaProducerConfig {
    public KafkaTemplate<String,String> kafkaTemplate;

    public KafkaProducerConfig(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publishEventToKafka(String topic,String message){
        kafkaTemplate.send(topic,message);
    }
}
