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

    /**
     * 根据难度获取题目
     * @param difficulty 枚举 SIMPLE, MEDIUM, DIFFICULT
     * @return 题目
     */
    @RequestMapping(value = "/getTopic", method = RequestMethod.GET)
    public Topic getTopic(@RequestParam String difficulty) {
        Topic.Difficulty difficulty1 = null;
        if (difficulty != null) {
            difficulty1 = Topic.Difficulty.valueOf(difficulty);
            System.out.println(difficulty1);
        }
        Topic topic = topicService.updateTopic(difficulty1);
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
