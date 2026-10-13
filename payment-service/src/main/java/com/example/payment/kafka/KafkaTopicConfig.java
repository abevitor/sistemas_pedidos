package com.example.payment.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaTopicConfig {

    @Bean
    public NewTopic pagamentoAprovadoTopic(){
        return TopicBuilder.name("pagamento-aprovado").partitions(3).replicas(1).build();
    }

    @Bean NewTopic pagamentoRecusadoTopic() {
        return TopicBuilder.name("pagamento-recusado").partitions(3).replicas(1).build();
    }
    
}
