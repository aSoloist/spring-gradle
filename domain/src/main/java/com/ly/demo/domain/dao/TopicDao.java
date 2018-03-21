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
    
    private String getHql() {
        return "FROM Topic WHERE ver < 3";
    }
    
    /**
     * 随机获取题目(简单)
     * @return
     */
    public Topic getRandomSimple(Topic.Difficulty difficulty) {
        Map<String, Object> map = new HashMap<>();
        String hql = getHql() + " AND difficulty = :difficulty ORDER BY RAND()";
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
    
    public Topic getRandomMedium(Topic.Difficulty difficulty) {
        return null;    
    }

    public Topic getRandomDifficult(Topic.Difficulty difficulty) {
        return null;
    }
}
