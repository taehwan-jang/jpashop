package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Orders orders) {
        em.persist(orders);
    }

    public Orders findOne(Long id) {
        return em.find(Orders.class, id);
    }

    public List<Orders> findAll() {
        return em.createQuery("select o from Orders o", Orders.class)
                .getResultList();
    }

    public List<Orders> findAll(OrderSearch orderSeach) {
        String jqpl = "select o from Orders o join o.member m";
        boolean isFirstCondition = true;
        if (orderSeach.getStatus() != null) {
            if (isFirstCondition) {
                jqpl += " where";
                isFirstCondition = false;
            } else {
                jqpl += " and";
            }
        }
        jqpl += " o.status = :status ";
        if (StringUtils.hasText(orderSeach.getMemberName())) {
            if (isFirstCondition) {
                jqpl += " where";
                isFirstCondition = false;
            } else {
                jqpl += " and";
            }
        }
        jqpl += " m.name like :name";

        TypedQuery<Orders> query = em.createQuery(jqpl, Orders.class)
                .setMaxResults(100);

        if (orderSeach.getStatus() != null) {
            query.setParameter("status", orderSeach.getStatus());
        }
        if (StringUtils.hasText(orderSeach.getMemberName())) {
            query.setParameter("name", orderSeach.getMemberName());
        }
        return query.getResultList();
    }

}
