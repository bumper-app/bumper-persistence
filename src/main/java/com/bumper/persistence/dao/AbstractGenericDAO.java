package com.bumper.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author math
 * @param <T>
 */
public abstract class AbstractGenericDAO<T extends Serializable> {

    /**
     * Enable T type resolution at runtime
     */
    private final Class<T> type;

    /**
     * Criteria being constructed
     */
    protected Criteria criteria;

    /**
     *
     */
    protected final int nullParameter = -1;

    /**
     *
     * @param cls
     */
    public AbstractGenericDAO(Class<T> cls) {
        this.type = cls;
    }

    private Session getSession() {
        return HibernateUtil.getSession();
    }

    /**
     *
     * @param entity
     * @throws DAOException
     */
    public void save(T entity) throws DAOException {

        Session hibernateSession = this.getSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {

            hibernateSession.saveOrUpdate(entity);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new DAOException("delete", entity.toString(),
                    e.getMessage(), e.getStackTrace());

        } finally {
            hibernateSession.close();
        }

    }

    /**
     *
     * @param entity
     * @throws DAOException
     */
    public void merge(T entity) throws DAOException {

        Session hibernateSession = this.getSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {

            hibernateSession.merge(entity);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new DAOException("delete", entity.toString(),
                    e.getMessage(), e.getStackTrace());

        } finally {
            hibernateSession.close();
        }
    }

    /**
     *
     * @param entity
     * @throws DAOException
     */
    public void delete(T entity) throws DAOException {
        Session hibernateSession = this.getSession();
        Transaction tx = hibernateSession.beginTransaction();

        try {

            hibernateSession.delete(entity);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new DAOException("delete", entity.toString(),
                    e.getMessage(), e.getStackTrace());

        } finally {
            hibernateSession.close();
        }

    }

    /**
     *
     * @param entities
     * @throws DAOException
     */
    public void delete(List<T> entities) throws DAOException {

        for (T entity : entities) {
            this.delete(entity);
        }

    }

    /**
     *
     * @param id
     * @return
     */
    public T findById(int id) {
        return this.select().where("id", id).findOne();
    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        if (this.criteria == null) {
            this.select();
        }

        List<T> results = this.criteria.list(); //this.criteria.setFirstResult(20).setMaxResults(20).list();

        //ensure that criterias/restrictions are deleted between two selects
        this.criteria = null;

        return results;
    }

    /**
     *
     * @return
     */
    public T findOne() {
        if (this.criteria == null) {
            this.select();
        }

        T result = (T) this.criteria.uniqueResult();

        //ensure that criterias/restrictions are deleted between two selects
        this.criteria = null;

        return result;
    }

    /**
     *
     * @param field
     * @param min
     * @param max
     * @return
     */
    protected AbstractGenericDAO<T> between(String field, int min, int max) {
        if (min != nullParameter) {
            this.greaterEqual(field, min);
        }
        if (max != nullParameter) {
            this.lowerEqual(field, max);
        }

        return this;
    }

    /**
     *
     * @param field
     * @param value
     * @return
     */
    protected AbstractGenericDAO<T> lowerEqual(String field, int value) {
        if (value != nullParameter) {
            this.criteria.add(Restrictions.le(field, value));
        }
        return this;
    }

    /**
     *
     * @param field
     * @param value
     * @return
     */
    protected AbstractGenericDAO<T> greaterEqual(String field, int value) {
        if (value != nullParameter) {
            this.criteria.add(Restrictions.ge(field, value));
        }
        return this;
    }

    /**
     *
     * @param field
     * @param ints
     * @return
     */
    protected AbstractGenericDAO<T> inAnyIds(String field, List<Integer> ints) {

        if (ints != null && !ints.isEmpty()) {
            this.criteria.add(Restrictions.in(field, ints));
        }

        return this;
    }

    /**
     *
     * @param field
     * @param in
     * @return
     */
    protected AbstractGenericDAO<T> inAny(String field, List in) {

        if (in != null && !in.isEmpty()) {
            this.criteria.add(Restrictions.in(field, in));
        }

        return this;
    }

    /**
     *
     * @param field
     * @param ints
     * @return
     */
    protected AbstractGenericDAO<T> notInAnyIds(String field, List<Integer> ints) {

        if (ints != null && !ints.isEmpty()) {
            this.criteria.add(Restrictions.not(Restrictions.in(field, ints)));
        }

        return this;
    }

    /**
     *
     * @param field
     * @param in
     * @return
     */
    protected AbstractGenericDAO<T> notInAny(String field, Collection<Object> in) {

        if (in != null && !in.isEmpty()) {
            this.criteria.add(Restrictions.not(Restrictions.in(field, in)));
        }

        return this;
    }

    /**
     *
     * @param field
     * @param object
     * @return
     */
    protected AbstractGenericDAO<T> where(String field, Object object) {
        this.criteria.add(Restrictions.eq(field, object));
        return this;
    }

    /**
     *
     * @param limit
     * @param offset
     * @return
     */
    protected AbstractGenericDAO<T> limit(int limit, int offset) {
        this.criteria.setFirstResult(offset).setMaxResults(limit);
        return this;
    }

    /**
     * convenient method to use instead of where and mimic SQL language
     * select().where().and().and() ...
     *
     * @param field
     * @param object
     * @return
     */
    protected AbstractGenericDAO<T> and(String field, Object object) {
        return this.where(field, object);
    }

    /**
     *
     * @param field
     * @param object
     * @return
     */
    protected AbstractGenericDAO<T> andNotEqual(String field, Object object) {
        this.criteria.add(Restrictions.ne(field, object));
        return this;
    }

    /**
     *
     * @param field
     * @param objects
     * @return
     */
    protected AbstractGenericDAO<T> andNotEqual(String field, List<Object> objects) {

        Disjunction disj = Restrictions.disjunction();

        for (Object o : objects) {
            disj.add(Restrictions.ne(field, o));
        }

        this.criteria.add(disj);
        return this;
    }

    /**
     * Generates where field like %pattern%
     *
     * @param field
     * @param pattern
     * @return
     */
    protected AbstractGenericDAO<T> andLike(String field, String pattern) {
        this.criteria.add(Restrictions.like(field, "%" + pattern + "%"));
        return this;
    }

    /**
     *
     * @param field
     * @param patterns
     * @return
     */
    protected AbstractGenericDAO<T> andLike(String field, List<String> patterns) {

        Disjunction disj = Restrictions.disjunction();

        for (String o : patterns) {
            disj.add(Restrictions.like(field, o));
        }
        this.criteria.add(disj);
        return this;
    }

    /**
     * Generates where field not like %pattern%
     *
     * @param field
     * @param pattern
     * @return
     */
    protected AbstractGenericDAO<T> andNotLike(String field, String pattern) {
        this.criteria.add(Restrictions.not(Restrictions.like(field, "%" + pattern + "%")));
        return this;
    }

    /**
     * Allows or logic for a field select().or(field1, value1, value2)
     *
     * Generates Select where field 1 = (value1 or value2)
     *
     * @param field
     * @param object
     * @param object2
     * @return
     */
    protected AbstractGenericDAO<T> or(String field, Object object, Object object2) {
        this.criteria.add(Restrictions.or(Restrictions.eq(field, object),
                Restrictions.eq(field, object2)));
        return this;
    }

    /**
     *
     * @param field
     * @param objects
     * @return
     */
    protected AbstractGenericDAO<T> or(String field, List<Object> objects) {
        Disjunction disj = Restrictions.disjunction();

        for (Object o : objects) {
            disj.add(Restrictions.eq(field, o));
        }

        this.criteria.add(disj);
        return this;
    }

    /**
     *
     * @param field
     * @param object
     * @param object2
     * @return
     */
    protected AbstractGenericDAO<T> orNotEqual(String field, Object object, Object object2) {
        this.criteria.add(Restrictions.or(Restrictions.ne(field, object),
                Restrictions.ne(field, object2)));
        return this;
    }

    /**
     *
     * @param field
     * @param objects
     * @return
     */
    protected AbstractGenericDAO<T> orNotEqual(String field, List<Object> objects) {
        Disjunction disj = Restrictions.disjunction();

        for (Object o : objects) {
            disj.add(Restrictions.ne(field, o));
        }
        this.criteria.add(disj);
        return this;
    }

    /**
     *
     * @param field
     * @return
     */
    protected AbstractGenericDAO<T> orderByASC(String field) {
        this.criteria.addOrder(Order.asc(field));
        return this;
    }

    /**
     *
     * @param field
     * @return
     */
    protected AbstractGenericDAO<T> orderByDSC(String field) {
        this.criteria.addOrder(Order.desc(field));
        return this;
    }

    /**
     *
     * @return
     */
    protected AbstractGenericDAO<T> select() {
        return this.selectRestrict();
    }

    private AbstractGenericDAO<T> selectRestrict(String... fields) {

        Session hibernateSession = HibernateUtil.getSession();

        this.criteria = hibernateSession.createCriteria(this.type.getName());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (fields != null && fields.length > 0) {

            ProjectionList pj = Projections.projectionList();

            for (String field : fields) {
                pj.add(Projections.property(field));
            }

            this.criteria.setProjection(pj);
        }

        return this;
    }
}
