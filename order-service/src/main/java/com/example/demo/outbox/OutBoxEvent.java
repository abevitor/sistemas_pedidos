package com.example.demo.outbox;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutBoxEvent {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String chave;

    @Column(length = 4000)
    private String payload;

    private boolean publicado;
    private LocalDateTime criadoEm;

    protected OutBoxEvent() {}

    public OutBoxEvent(String topic, String chave, String payload) {
        this.topic = topic;
        this.chave = chave;
        this.payload = payload;
        this.publicado = false;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTopic() { return topic; }
    public String getChave() { return chave; }
    public String getPayload() { return payload; }
    public boolean isPublicado() { return publicado; }
    public void setPublicado(boolean publicado) { this.publicado = publicado;

    
    
  }
}
