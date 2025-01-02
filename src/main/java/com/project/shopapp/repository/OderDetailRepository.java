package com.project.shopapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shopapp.entity.OderDetail;

@Repository
public interface OderDetailRepository extends JpaRepository<OderDetail, Long> {
    List<OderDetail> findByOrderId(Long orderId);
}
