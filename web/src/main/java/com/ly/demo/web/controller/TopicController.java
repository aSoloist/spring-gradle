package com.ly.demo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ly.demo.domain.model.Topic;
import com.ly.demo.domain.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Soloist on 2018/3/21 1:54
 */
@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {
    @Autowired
    private TopicService topicService;

    /**
     * 根据难度获取题目
     *
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
            topic.setAnswer(null);
            return topic;
        } else {
            topic = new Topic();
            topic.setTitle("未查到符合的题目");
            return topic;
        }
    }
    
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(@RequestParam String userAgent) {
        logger.info("用户：" + userAgent);
    }

    /**
     * 返回答题结果
     * @param topicNumber 题号
     * @param answer 答案
     * @return 结果
     */
    @RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
    public JSONObject getAnswer(@RequestParam String topicNumber,
                                @RequestParam String answer) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "success");
        logger.info(topicNumber + ":" + answer);
        if (topicService.isRight(topicNumber, answer)) {
            jsonObject.put("result", "right");
        } else {
            jsonObject.put("result", "error");
        }

        return jsonObject;
    }

    /**
     * 保存题目
     * @param topic 题目
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Topic saveTopic(@RequestBody Topic topic) {
        return topicService.saveOrUpdate(topic);
    }
}
