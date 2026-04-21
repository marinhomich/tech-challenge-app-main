package com.oficina.adapter.out.persistence.jpa;

import com.oficina.adapter.out.persistence.entity.ServiceItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceItemJpaRepository extends JpaRepository<ServiceItemJpaEntity, UUID> {
}
