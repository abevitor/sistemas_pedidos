package com.example.demo.outbox;


import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling 
public class OutboxPublisher {

    private final OutboxEventRepository repository;
    private final DefaultKafkaProducerFactory<String, String> factory;
    private final KafkaTemplate<String, String> template;

    public OutboxPublisher(OutboxEventRepository repository,
                           @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        this.repository = repository;

        // produtor próprio que envia o JSON como texto puro (o JSON já está pronto na tabela)
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000); // não travar 60s se o Kafka cair

        this.factory = new DefaultKafkaProducerFactory<>(props);
        this.template = new KafkaTemplate<>(factory);
    }

    @Scheduled(fixedDelay = 1000)
    public void publicarPendentes() {
        List<OutboxEvent> pendentes = repository.findtTop50ByPublicadoFalseOrderByIdAsc();

        for (OutboxEvent evento : pendentes) {
            try {
                // espera a confirmação do Kafka antes de marcar como publicado
                template.send(evento.getTopic(), evento.getChave(), evento.getPayload())
                        .get(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                System.out.println("Outbox: falha ao publicar o evento " + evento.getId()
                        + ", tenta de novo no próximo ciclo (" + e.getMessage() + ")");
                return; // para aqui para manter a ordem; o resto fica para o próximo ciclo
            }

            evento.setPublicado(true);
            repository.save(evento);
            System.out.println("Outbox: evento " + evento.getId() + " publicado em " + evento.getTopic());
        }
    }

    @PreDestroy
    public void fechar() {
        factory.destroy();
    }


    
}
