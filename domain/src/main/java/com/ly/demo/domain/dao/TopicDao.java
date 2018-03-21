package com.ly.demo.domain.dao;

import com.ly.demo.domain.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Soloist on 2018/3/21 1:27
 */
@Repository
public class TopicDao extends BaseDao<Topic, String> {
    
    /**
     * 随机获取题目(简单)
     * @return 题目
     */
    public Topic getRandom(Topic.Difficulty difficulty) {
        Map<String, Object> map = new HashMap<>();
        String hql = "FROM Topic WHERE ver < 3 AND difficulty = :difficulty ORDER BY RAND()";
        map.put("difficulty", difficulty);
        List<Topic> topics = findByNameParam(hql, -1, 1, map);
        if (topics.size() == 1) {
            Topic topic = topics.get(0);
            topic.setVer(topic.getVer() + 1);
            update(topic);
            return topic;
        } else {
            return null;
        }
    }

    /**
     * 根据题号获取题目
     * @param number 题号
     * @return 题目
     */
    public Topic getTopicByNumber(String number) {
        String hql = "FROM Topic WHERE topicNumber = :number";
        Map<String, Object> map = new HashMap<>();
        map.put("number", number);
        List<Topic> topics = findByNameParam(hql, map);
        if (topics.size() == 1) {
            return topics.get(0);
        } else {
            return null;
        }
    }
}
