-- Fase 2: Adiciona campos de soft-delete e razão de recusa do orçamento nas ordens
ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS deleted_at TIMESTAMP WITH TIME ZONE,
    ADD COLUMN IF NOT EXISTS approval_rejected_reason TEXT;

-- Índice para consulta de ordens ativas
CREATE INDEX IF NOT EXISTS idx_orders_deleted_at ON orders (deleted_at);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders (status);
