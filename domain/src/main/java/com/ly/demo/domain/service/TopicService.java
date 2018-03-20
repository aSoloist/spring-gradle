package com.ly.demo.domain.service;

import com.ly.demo.domain.dao.TopicDao;
import com.ly.demo.domain.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Soloist on 2018/3/21 1:49
 */
@Service
public class TopicService {
    @Autowired
    private TopicDao topicDao;
    
    public Topic getTopic() {
        return topicDao.getRandom();
    }
    
    public Topic saveOrUpdate(Topic topic) {
        topicDao.save(topic);
        return topic;
    }
}
