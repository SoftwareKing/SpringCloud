package org.xujin.base.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.xujin.base.model.Pager;
import org.xujin.base.model.SystemContext;

public class BaseDaoImpl<T> implements BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<?> clz;

    public Class<?> getClz() {
        if (clz == null) {
            // 获取泛型的Class对象
            clz = ((Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }

    protected Session getSession() {

        return this.sessionFactory.getCurrentSession();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#add(java.lang.Object)
     */

    public T add(T t) {
        getSession().save(t);
        return t;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#update(java.lang.Object)
     */

    public void update(T t) {

        getSession().update(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#delete(int)
     */

    public void delete(int id) {

        getSession().delete(this.load(id));

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#load(int)
     */

    public T load(int id) {
        return (T) getSession().load(getClz(), id);
    }

    // 初始化排序
    private String initSort(String hql) {
        String order = SystemContext.getOrder();
        String sort = SystemContext.getSort();
        if (sort != null && !"".equals(sort.trim())) {
            hql += " order by " + sort;
            if (!"desc".equals(order))
                hql += " asc";
            else
                hql += " desc";
        }
        return hql;
    }

    // 设置查询条件
    private void setAliasParameter(Query query, Map<String, Object> alias) {
        if (alias != null) {
            Set<String> keys = alias.keySet();
            for (String key : keys) {
                Object val = alias.get(key);
                if (val instanceof Collection) {
                    // 查询条件是列表
                    query.setParameterList(key, (Collection) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        }
    }

    // 查询条件参数设置

    private void setParameter(Query query, Object[] args) {
        if (args != null && args.length > 0) {
            int index = 0;
            for (Object arg : args) {
                query.setParameter(index++, arg);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#list(java.lang.String,
     * java.lang.Object[])
     */

    public List<T> list(String hql, Object[] args) {
        return this.list(hql, args, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xujin.basic.dao.BaseDao#list(java.lang.String, java.lang.Object)
     */

    public List<T> list(String hql, Object arg) {
        // TODO Auto-generated method stub
        return this.list(hql, new Object[] { arg });
    }

    public List<T> list(String hql) {
        return this.list(hql, null);
    }

    public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        Query query = getSession().createQuery(hql);
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.list();
    }

    public List<T> listByalias(String hql, Map<String, Object> alias) {
        return this.list(hql, null, alias);
    }

    public Pager<T> find(String hql, Object[] args) {
        return this.find(hql, args, null);
    }

    public Pager<T> find(String hql, Object arg) {
        return this.find(hql, new Object[] { arg });
    }

    public Pager<T> find(String hql) {
        return this.find(hql, null);
    }

    @SuppressWarnings("rawtypes")
    // 设置分页
    private void setPagers(Query query, Pager pages) {
        Integer pageSize = SystemContext.getPageSize();
        Integer pageOffset = SystemContext.getPageOffset();
        if (pageOffset == null || pageOffset < 0)
            pageOffset = 0;
        if (pageSize == null || pageSize < 0)
            pageSize = 15;
        pages.setOffset(pageOffset);
        pages.setSize(pageSize);
        query.setFirstResult(pageOffset).setMaxResults(pageSize);
    }

    private String getCountHql(String hql, boolean isHql) {
        String e = hql.substring(hql.indexOf("from"));
        String c = "select count(*) " + e;
        if (isHql)
            c.replaceAll("fetch", "");
        return c;
    }

    public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
        hql = initSort(hql);
        String cq = getCountHql(hql, true);
        Query cquery = getSession().createQuery(cq);
        Query query = getSession().createQuery(hql);
        // 设置别名参数
        setAliasParameter(query, alias);
        setAliasParameter(cquery, alias);
        // 设置参数
        setParameter(query, args);
        setParameter(cquery, args);
        Pager<T> pages = new Pager<T>();
        setPagers(query, pages);
        List<T> datas = query.list();
        pages.setDatas(datas);
        long total = (Long) cquery.uniqueResult();
        pages.setTotal(total);
        return pages;
    }

    public Pager<T> findByalias(String hql, Map<String, Object> alias) {
        return this.find(hql, null, alias);
    }

    public Object Queryobject(String hql, Object[] args) {
        return this.Queryobject(hql, args, null);
    }

    public Object Queryobject(String hql, Object args) {
        return this.Queryobject(hql, new Object[] { args });
    }

    public Object Queryobject(String hql) {
        return this.Queryobject(hql, null);
    }

    public Object Queryobject(String hql, Object[] args, Map<String, Object> alias) {
        Query query = getSession().createQuery(hql);
        setAliasParameter(query, alias);
        setParameter(query, args);
        return query.uniqueResult();
    }

    public Object QueryobjectByAlias(String hql, Map<String, Object> alias) {
        return this.Queryobject(hql, null, alias);
    }

    public void UpdateByHql(String hql, Object[] args) {
        Query query = getSession().createQuery(hql);
        setParameter(query, args);
        query.executeUpdate();
    }

    public void UpdateByHql(String hql, Object args) {
        this.UpdateByHql(hql, new Object[] { args });

    }

    public void UpdateByHql(String hql) {
        this.UpdateByHql(hql, null);

    }

    public <N extends Object> List<N> listBySql(String sql, Object[] args, Class<?> clz, boolean hasEntiry) {
        // TODO Auto-generated method stub
        return this.listBySql(sql, args, null, clz, hasEntiry);
    }

    public <N extends Object> List<N> listBySql(String sql, Object args, Class<?> clz, boolean hasEntiry) {
        return this.listBySql(sql, new Object[] { args }, null, clz, hasEntiry);
    }

    public <N extends Object> List<N> listBySql(String sql, Class<?> clz, boolean hasEntiry) {
        return this.listBySql(sql, null, clz, hasEntiry);
    }

    public <N extends Object> List<N> listBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz,
            boolean hasEntiry) {
        sql = initSort(sql);
        SQLQuery sq = getSession().createSQLQuery(sql);
        setAliasParameter(sq, alias);
        setParameter(sq, args);
        if (hasEntiry) {

            sq.addEntity(clz);

        } else {

            sq.setResultTransformer(Transformers.aliasToBean(clz));
        }

        return sq.list();
    }

    public <N extends Object> List<N> listByaliasSql(String sql, Map<String, Object> alias, Class<?> clz,
            boolean hasEntiry) {
        return this.listBySql(sql, null, alias, clz, hasEntiry);
    }

    public <N extends Object> Pager<N> findBySql(String sql, Object[] args, Class<?> clz, boolean hasEntiry) {
        // TODO Auto-generated method stub
        return this.findBySql(sql, args, null, clz, hasEntiry);
    }

    public <N extends Object> Pager<N> findBySql(String sql, Object args, Class<?> clz, boolean hasEntiry) {
        // TODO Auto-generated method stub
        return this.findBySql(sql, new Object[] { args }, clz, hasEntiry);
    }

    public <N extends Object> Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntiry) {
        // TODO Auto-generated method stub
        return this.findBySql(sql, null, clz, hasEntiry);
    }

    public <N extends Object> Pager<N> findBySql(String sql, Object[] args, Map<String, Object> alias, Class<?> clz,
            boolean hasEntiry) {

        sql = initSort(sql);
        String cq = getCountHql(sql, false);
        // cq=initSort(cq);不需要再加上就多了一个Order

        SQLQuery sq = getSession().createSQLQuery(sql);
        SQLQuery cquery = getSession().createSQLQuery(cq);
        setAliasParameter(sq, alias);
        setAliasParameter(cquery, alias);
        setParameter(sq, args);
        setParameter(cquery, args);
        Pager<N> pages = new Pager<N>();
        setPagers(sq, pages);
        if (hasEntiry) {

            sq.addEntity(clz);
        } else {
            sq.setResultTransformer(Transformers.aliasToBean(clz));
        }
        List<N> datas = sq.list();
        pages.setDatas(datas);
        long total = ((BigInteger) cquery.uniqueResult()).longValue();
        pages.setTotal(total);

        return pages;

    }

    public <N extends Object> Pager<N> findByaliasSql(String sql, Map<String, Object> alias, Class<?> clz,
            boolean hasEntiry) {
        // TODO Auto-generated method stub
        return this.findBySql(sql, null, alias, clz, hasEntiry);
    }

}
