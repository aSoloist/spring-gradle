package com.ly.demo.web.controller;

import com.ly.demo.domain.model.Topic;
import com.ly.demo.domain.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Soloist on 2018/3/21 1:54
 */
@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    
    @RequestMapping(value = "/getTopic", method = RequestMethod.GET)
    public Topic getTopic(@RequestParam(required = true) String difficulty) {
        Topic.Difficulty difficulty1 = null;
        if (difficulty != null) {
            difficulty1 = Topic.Difficulty.valueOf(difficulty);
        }
        Topic topic = topicService.getTopic(difficulty1);
        if (topic != null) {
            return topic;
        } else {
            topic = new Topic();
            topic.setTitle("未查到符合的题目");
            return topic;
        }
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Topic saveTopic(@RequestBody Topic topic) {
        return topicService.saveOrUpdate(topic);
    }
}
