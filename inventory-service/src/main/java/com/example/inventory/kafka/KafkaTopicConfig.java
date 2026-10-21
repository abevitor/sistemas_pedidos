package com.example.inventory.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaTopicConfig {

    @Bean 
    public NewTopic estoqueReservadoTopic(){
        return TopicBuilder.name("estoque-reservado").partitions(3).replicas(1).build();
    }
    
    @Bean 
    public NewTopic estoqueInsuficienteTopic() {
        return TopicBuilder.name("estoque-insuficiente").partitions(3).replicas(1).build();
    }
    
}
