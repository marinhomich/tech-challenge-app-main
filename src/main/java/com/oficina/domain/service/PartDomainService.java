package com.oficina.domain.service;

import com.oficina.domain.model.Part;
import com.oficina.domain.port.in.PartUseCase;
import com.oficina.domain.port.out.PartRepositoryPort;
import com.oficina.domain.port.out.StockTransactionPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço de domínio para Peças/Insumos com controle de estoque.
 */
@Service
public class PartDomainService implements PartUseCase {

    private final PartRepositoryPort partRepository;
    private final StockTransactionPort stockTransactionPort;

    public PartDomainService(PartRepositoryPort partRepository, StockTransactionPort stockTransactionPort) {
        this.partRepository = partRepository;
        this.stockTransactionPort = stockTransactionPort;
    }

    @Transactional
    @Override
    public Part save(Part part) {
        return partRepository.save(part);
    }

    @Override
    public Optional<Part> findById(UUID id) {
        return partRepository.findById(id);
    }

    @Override
    public List<Part> findAll() {
        return partRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        partRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Part adjustStock(UUID partId, int delta, UUID userId) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new IllegalArgumentException("Part not found: " + partId));
        int newQty = (part.getStockQty() == null ? 0 : part.getStockQty()) + delta;
        part.setStockQty(newQty);
        Part saved = partRepository.save(part);
        stockTransactionPort.recordAdjustment(partId, delta, userId);
        return saved;
    }
}
