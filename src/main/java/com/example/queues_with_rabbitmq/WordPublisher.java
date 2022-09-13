package com.example.queues_with_rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;


@RestController
public class WordPublisher implements Serializable {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words;

    public WordPublisher() {
        this.words = new Word();
        this.words.badWords.add("Fuck");
        this.words.badWords.add("Kuy");
        this.words.goodWords.add("Happy");
        this.words.goodWords.add("Enjoy");
        this.words.goodWords.add("life");
    }

    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s){
        this.words.badWords.remove(s);
        return this.words.badWords;
    }

    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGoodWord(@PathVariable("word") String s){
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }

    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s){
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public void proofSentence(@PathVariable("sentence") String s){
        boolean found_good = false;
        boolean found_bad = false;
        for (int i = 0; i < this.words.goodWords.size(); i++){
            if(s.contains(this.words.goodWords.get(i))){
//                rabbitTemplate.convertAndSend("Direct", "good", s);
                found_good = true;
                break;
            }
        }

        for (int i = 0; i < this.words.badWords.size(); i++){
            if(s.contains(this.words.badWords.get(i))){
//                rabbitTemplate.convertAndSend("Direct", "bad", s);
                found_bad = true;
                break;
            }
        }
        if(found_good && found_bad){
            rabbitTemplate.convertAndSend("Fanout", "", s);
        } else if (found_good) {
            rabbitTemplate.convertAndSend("Direct", "good", s);
        } else if (found_bad){
            rabbitTemplate.convertAndSend("Direct", "bad", s);
        }


//        rabbitTemplate.convertAndSend();
    }

}
