package com.example.demo.sse;


import com.example.demo.model.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrderStatusStream {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter inscrever() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        try {
            emitter.send(SseEmitter.event().name("conectado").data("ok"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }
        return emitter;
    }

    public void publicar(Order order) {
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(SseEmitter.event()
            .name("pedido-atualizado")
        .data(order, MediaType.APPLICATION_JSON));
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
    
}
