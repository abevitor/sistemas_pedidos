package com.example.demo.kafka;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorConfig {

     @Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        // quando as tentativas acabam, publica a mensagem em "<topico>.DLT".
        // partição -1 = o Kafka escolhe, então o DLT não precisa ter o mesmo número de partições
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
            kafkaTemplate,
            (record, ex) -> new TopicPartition(record.topic() + ".DLT", -1));

        // 1 execução + 3 retentativas, com 2 segundos entre elas
        return new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 3L));
    }
    
}
