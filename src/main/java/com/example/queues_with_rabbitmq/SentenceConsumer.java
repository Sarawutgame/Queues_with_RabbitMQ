package com.example.queues_with_rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SentenceConsumer {

    protected Sentence sentences;

    public SentenceConsumer() {
        this.sentences = new Sentence();
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        System.out.println(s + " : Add Bad Sentence");
        this.sentences.badSentences.add(s);
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        System.out.println(s + " : Add Good Sentence");
        this.sentences.goodSentences.add(s);
    }
}
