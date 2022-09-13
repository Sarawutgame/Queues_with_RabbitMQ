package com.example.queues_with_rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        for (int i = 0; i < this.sentences.badSentences.size(); i++){
            System.out.println(this.sentences.badSentences.get(i));
        }
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        System.out.println(s + " : Add Good Sentence");
        this.sentences.goodSentences.add(s);
        for (int i = 0; i < this.sentences.goodSentences.size(); i++){
            System.out.println(this.sentences.goodSentences.get(i));
        }
    }

    @RabbitListener(queues = "GetSen")
    public Sentence getGG(String s){
        return this.sentences;
    }
}
