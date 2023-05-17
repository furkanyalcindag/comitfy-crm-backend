package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Order;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order> {


    @Query("SELECT o.orderStatus, COUNT(o.orderStatus) FROM Order AS o where o.isDeleted=false GROUP BY o.orderStatus")
    List<Object[]> countTotalOrderStatus();
}
