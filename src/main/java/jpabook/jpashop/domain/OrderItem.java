package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders order;

    private int orderPrice;
    private int count;

    //======생성 메서드 ======//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderPrice(orderPrice);
        orderItem.setItem(item);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    //=====비즈니스 로직=====//
    public void cancel() {
        getItem().addStock(count);
    }

    //=====조회 로직====//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
