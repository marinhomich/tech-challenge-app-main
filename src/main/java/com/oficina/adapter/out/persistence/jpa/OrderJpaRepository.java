package com.oficina.adapter.out.persistence.jpa;

import com.oficina.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {

    @Query("SELECT o FROM OrderJpaEntity o WHERE o.deletedAt IS NULL AND o.status NOT IN ('FINISHED', 'DELIVERED')")
    List<OrderJpaEntity> findAllActive();
}
