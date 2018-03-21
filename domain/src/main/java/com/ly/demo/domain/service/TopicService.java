package com.ly.demo.domain.service;

import com.ly.demo.domain.dao.TopicDao;
import com.ly.demo.domain.model.Topic;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Soloist on 2018/3/21 1:49
 */
@Service
public class TopicService {
    @Autowired
    private TopicDao topicDao;

    /**
     * 根据难度获取题目
     * @param difficulty 难度
     * @return 题目
     */
    public Topic updateTopic(Topic.Difficulty difficulty) {
        if (difficulty == null || StringUtils.isEmptyOrWhitespaceOnly(difficulty.toString())) {
            return null;
        }
        
        Topic topic = null;
        switch (difficulty) {
            case SIMPLE:
                topic = topicDao.getRandomSimple(difficulty);
                break;
            case MEDIUM:
                topic = topicDao.getRandomMedium(difficulty);
                break;
            case DIFFICULT:
                topic = topicDao.getRandomDifficult(difficulty);
                break;
        }
        return topic;
    }
    
    public Topic saveOrUpdate(Topic topic) {
        topicDao.save(topic);
        return topic;
    }
}
