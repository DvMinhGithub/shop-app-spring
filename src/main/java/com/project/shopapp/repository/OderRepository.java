package com.project.shopapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopapp.entity.Order;

@Repository
public interface OderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
