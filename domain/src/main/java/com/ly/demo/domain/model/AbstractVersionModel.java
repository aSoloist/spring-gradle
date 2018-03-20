package com.ly.demo.domain.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Created by Soloist on 2018/3/20 22:36
 */
@MappedSuperclass
public class AbstractVersionModel extends AbstractModel {
    @Version
    @Column(nullable = false)
    private Integer ver = 0;

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
}
