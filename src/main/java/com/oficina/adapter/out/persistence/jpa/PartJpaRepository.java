package com.oficina.adapter.out.persistence.jpa;

import com.oficina.adapter.out.persistence.entity.PartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PartJpaRepository extends JpaRepository<PartJpaEntity, UUID> {
}
