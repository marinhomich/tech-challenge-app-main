package com.oficina.adapter.out.persistence.jpa;

import com.oficina.adapter.out.persistence.entity.StockTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockTransactionJpaRepository extends JpaRepository<StockTransactionJpaEntity, UUID> {
}
