package com.example.queues_with_rabbitmq;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class Sentence implements Serializable {
    public ArrayList<String> badSentences;
    public ArrayList<String> goodSentences;

    public Sentence() {
        this.badSentences = new ArrayList<String>();
        this.goodSentences = new ArrayList<String>();
    }

}
