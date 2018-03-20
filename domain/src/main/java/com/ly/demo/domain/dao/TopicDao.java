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
    public Topic getRandom() {
        String hql = "from Topic where ver < 3";
        long result = getCount(hql, null);
        logger.info("查询到共有" + result + "条数据");
        int index = (int) (Math.random() * result) + 1;
        hql = hql + " and topicNumber = :number";
        Map<String, Object> map = new HashMap<>();
        map.put("number", index + "");
        logger.info("下标" + index);
        List<Topic> topics = findByNameParam(hql, map);
        return topics.get(0);
    }
}
