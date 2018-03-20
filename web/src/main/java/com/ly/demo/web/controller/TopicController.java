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
    public Topic getTopic() {
        return topicService.getTopic();
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Topic saveTopic(@RequestBody Topic topic) {
        return topicService.saveOrUpdate(topic);
    }
}
