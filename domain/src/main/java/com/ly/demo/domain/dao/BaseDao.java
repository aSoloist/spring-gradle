package com.ly.demo.domain.dao;

import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Created by Soloist on 2018/3/19 0:17
 */
@MappedSuperclass
public abstract class BaseDao<T extends Serializable, PK extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(BaseDao.class);
    protected static final int DEAFULT_MAX_RESULT = 20;
    private final Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).
            getActualTypeArguments()[0];
    private boolean cacheQueries = false;
    private String queryCacheRegion;
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public BaseDao() {
    }

    public boolean isCacheQueries() {
        return this.cacheQueries;
    }

    public void setCacheQueries(boolean cacheQueries) {
        this.cacheQueries = cacheQueries;
    }

    public String getQueryCacheRegion() {
        return this.queryCacheRegion;
    }

    public void setQueryCacheRegion(String queryCacheRegion) {
        this.queryCacheRegion = queryCacheRegion;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public T get(PK id) {
        Object o = this.getSession().get(this.entityClass, id);
        return o == null ? (T) o : null;
    }

    public PK save(T model) {
        return (PK) this.getSession().save(model);
    }

    public void update(T model) {
        this.getSession().update(model);
    }

    public void saveOrUpdate(T model) {
        this.getSession().saveOrUpdate(model);
    }

    public void merge(T model) {
        this.getSession().merge(model);
    }

    public void delete(PK id) {
        T obj = this.get(id);
        if (obj != null) {
            this.getSession().delete(obj);
        }
    }

    public void deleteObject(T model) {
        if (model != null) {
            this.getSession().delete(model);
        }
    }

    public boolean exists(PK id) {
        return this.get(id) != null;
    }

    public void flush() {
        this.getSession().flush();
    }

    public void clear() {
        this.getSession().clear();
    }

    public T findUnique(String hql, Object... values) {
        List list = this.find(hql, values);
        return list != null && list.size() == 1 ? (T) list.get(0) : null;
    }

    public List<T> find(String hql, Object... values) {
        return find(hql, -1, -1, values);
    }
    
    public List<T> find(String hql, Integer firstResult, Integer maxResults, Object... values) {
        int _firstResult = 0;
        if (firstResult != null && firstResult > 0) {
            _firstResult = firstResult;
        }
        
        int _maxResults = 20;
        if (maxResults != null && maxResults > 0) {
            _maxResults = maxResults;
        }

        Query query = this.getSession().createQuery(hql);
        this.prepareQuery(query);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        
        query.setFirstResult(_firstResult);
        query.setMaxResults(_maxResults);
        
        return query.list();
    }
    
    protected void prepareQuery(Query query) {
        if (this.isCacheQueries()) {
            query.setCacheable(true);
            if (this.getQueryCacheRegion() != null) {
                query.setCacheRegion(queryCacheRegion);
            }
        } 
    }
    
    public List<T> findByNameParam(String hql, String paramName, Object values) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(paramName, values);
        return this.findByNameParam(hql, params);
    }
    
    public List<T> findByNameParam(String hql, Map<String, Object> paramAndValues) {
        return this.findByNameParam(hql, -1, -1, paramAndValues);
    }
    
    public List<T> findByNameParam(String hql, Integer firstResult, Integer maxResults, Map<String, Object> paramAndValues) {
        int _firstResult = 0;
        if (firstResult != null && firstResult > 0) {
            _firstResult = firstResult;
        }

        int _maxResults = 20;
        if (maxResults != null && maxResults > 0) {
            _maxResults = maxResults;
        }
        
        Query query = this.getSession().createQuery(hql);
        this.prepareQuery(query);
        if (paramAndValues != null) {
            for (Object o : paramAndValues.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                this.applyNamedParameterToQuery(query, (String) entry.getKey(), entry.getValue());
            }
        }
        
        query.setFirstResult(_firstResult);
        query.setMaxResults(_maxResults);
        
        return query.list();
    }
    
    public Integer getResultSize(String hql, Map<String, Object> paramAndValues) {
        Query query = this.getSession().createQuery(hql);
        this.prepareQuery(query);
        if (paramAndValues != null) {
            for (Object o : paramAndValues.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                this.applyNamedParameterToQuery(query, (String) entry.getKey(), entry.getValue());
            }
        }
        Object o = query.uniqueResult();
        int result = 0;
        if (o != null) {
            result = Integer.parseInt(o.toString());
        }
        
        return result;
    }
    
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return this.findByCirteria(criteria, -1, -1);
    }
    
    public List<T> findByCirteria(DetachedCriteria criteria, Integer firstResult, Integer maxResults) {
        int _firstResult = 0;
        if (firstResult != null && firstResult > 0) {
            _firstResult = firstResult;
        }

        int _maxResults = 20;
        if (maxResults != null && maxResults > 0) {
            _maxResults = maxResults;
        }

        Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
        this.prepareCriteria(executableCriteria);
        executableCriteria.setFetchSize(_firstResult);
        executableCriteria.setMaxResults(_maxResults);
        executableCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return executableCriteria.list();
    }
    
    public List<Map<Object, String>> findBySQL(String sql, Object... values) {
        return this.findBySQL(sql, -1, -1, values);
    }
    
    public List<Map<Object, String>> findBySQL(String sql, Integer firstResult, Integer maxResults, Object... values) {
        int _firstResult = 0;
        if (firstResult != null && firstResult > 0) {
            _firstResult = firstResult;
        }

        int _maxResults = 20;
        if (maxResults != null && maxResults > 0) {
            _maxResults = maxResults;
        }

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        this.prepareQuery(sqlQuery);
        
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                sqlQuery.setParameter(i, values[i]);
            }    
        }
        
        sqlQuery.setFirstResult(_firstResult);
        sqlQuery.setMaxResults(_maxResults);
        
        return sqlQuery.list();
    }
    
    public Map<String, Object> findUniqueBySQL(String sql, Object... values) {
        List list = findBySQL(sql, values);
        return list != null && list.size() == 1 ? (Map) list.get(0) : null;
    }
    
    public List<Map<String, Object>> findByNamedParamSQL(String sql, String paramName, Object value) {
        Map<String, Object> params = new HashMap<>();
        params.put(paramName, value);
        return this.findByNameParamSQL(sql, params);
    }
    
    public List<Map<String, Object>> findByNameParamSQL(String sql, Map<String, Object> paramAndValues) {
        return this.findByNameParamSQL(sql, -1, -1, paramAndValues);
    }
    
    public List<Map<String, Object>> findByNameParamSQL(String sql, Integer firstResult, Integer maxResults, Map<String, Object> paramAndValues) {
        int _firstResult = 0;
        if (firstResult != null && firstResult > 0) {
            _firstResult = firstResult;
        }

        int _maxResults = 20;
        if (maxResults != null && maxResults > 0) {
            _maxResults = maxResults;
        }
        
        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        this.prepareQuery(sqlQuery);
        if (paramAndValues != null) {
            for (Object o : paramAndValues.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                this.applyNamedParameterToQuery(sqlQuery, (String) entry.getKey(), entry.getValue());
            }
        }
        
        sqlQuery.setFirstResult(_firstResult);
        sqlQuery.setMaxResults(_maxResults);
        
        return sqlQuery.list();
    }
    
    protected Iterator<T> iterate(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        this.prepareQuery(query);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        
        return query.iterate();
    }
    
    protected void closeIterator(Iterator<T> iterator) {
        Hibernate.close(iterator);
    }
    
    public int bulkUpdate(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        this.prepareQuery(query);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        
        return query.executeUpdate();
    }
    
    public long getCount(String hql, Map<String, Object> paramAndValues) {
        String sql = "SELECT count(1) ";
        hql = sql + hql;
        Query query = this.getSession().createQuery(hql);
        query.setProperties(paramAndValues);
        Object o = query.uniqueResult();
        if (o != null) {
            return (Long) o;
        } else {
            return 0;
        }
    }
    
    protected void prepareCriteria(Criteria criteria) {
        if (this.isCacheQueries()) {
            criteria.setCacheable(true);
            if (this.getQueryCacheRegion() != null) {
                criteria.setCacheRegion(this.getQueryCacheRegion());
            }
        }
    }
    
    protected void applyNamedParameterToQuery(Query query, String paramName, Object value) {
        if (value instanceof Collection) {
            query.setParameterList(paramName, (Collection) value);
        } else if (value instanceof Object[]) {
            query.setParameterList(paramName, (Object[]) value);
        } else {
            query.setParameter(paramName, value);
        }
    }
}
