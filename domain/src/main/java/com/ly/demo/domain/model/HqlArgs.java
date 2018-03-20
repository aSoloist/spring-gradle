package com.ly.demo.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soloist on 2018/3/21 1:29
 */
public class HqlArgs {
    private String hql;
    private Map<String, Object> args = new HashMap<>();

    public HqlArgs() {
    }

    public HqlArgs(String hql, Map<String, Object> args) {
        this.hql = hql;
        this.args = args;
    }

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }
}
