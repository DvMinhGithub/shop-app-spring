package com.project.shopapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.shopapp.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE "
            + "(:keyword IS NULL OR :keyword = ''"
            + " OR o.fullName LIKE %:keyword%"
            + " OR o.address LIKE %:keyword%"
            + " OR o.note LIKE %:keyword%)")
    Page<Order> findAllByKeyword(String keyword, Pageable pageable);
}
