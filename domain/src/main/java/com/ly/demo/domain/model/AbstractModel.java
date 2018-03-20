package com.ly.demo.domain.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Soloist on 2018/3/19 0:16
 */
@MappedSuperclass
public class AbstractModel implements Serializable {
    @Id
    private String id = UUID.randomUUID().toString();

    public AbstractModel() {
    }

    public AbstractModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractModel)) return false;

        AbstractModel that = (AbstractModel) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }
}
