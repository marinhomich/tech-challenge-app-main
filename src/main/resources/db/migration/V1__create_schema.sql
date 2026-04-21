CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT true,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE roles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
  PRIMARY KEY(user_id, role_id)
);

CREATE TABLE customers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  document VARCHAR(32) NOT NULL UNIQUE,
  email VARCHAR(255),
  phone VARCHAR(50),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE vehicles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL REFERENCES customers(id),
  plate VARCHAR(20) NOT NULL UNIQUE,
  brand VARCHAR(100),
  model VARCHAR(100),
  year INTEGER,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE services (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  code VARCHAR(50) NOT NULL UNIQUE,
  description TEXT,
  hourly_rate NUMERIC(12,2),
  estimated_hours NUMERIC(6,2),
  active BOOLEAN DEFAULT true
);

CREATE TABLE parts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  sku VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  stock_qty INTEGER DEFAULT 0,
  cost_price NUMERIC(12,2),
  sale_price NUMERIC(12,2),
  min_stock INTEGER DEFAULT 0,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  number VARCHAR(50) NOT NULL UNIQUE,
  customer_id UUID NOT NULL REFERENCES customers(id),
  vehicle_id UUID NOT NULL REFERENCES vehicles(id),
  status VARCHAR(50) NOT NULL,
  total_estimated NUMERIC(12,2),
  total_final NUMERIC(12,2),
  approved BOOLEAN DEFAULT false,
  received_at TIMESTAMP WITH TIME ZONE,
  diagnostic_at TIMESTAMP WITH TIME ZONE,
  awaiting_approval_at TIMESTAMP WITH TIME ZONE,
  started_at TIMESTAMP WITH TIME ZONE,
  finished_at TIMESTAMP WITH TIME ZONE,
  delivered_at TIMESTAMP WITH TIME ZONE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE order_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  service_id UUID REFERENCES services(id),
  quantity NUMERIC(12,2),
  unit_price NUMERIC(12,2),
  total_price NUMERIC(12,2)
);

CREATE TABLE order_parts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  part_id UUID NOT NULL REFERENCES parts(id),
  quantity INTEGER,
  unit_cost NUMERIC(12,2),
  total_cost NUMERIC(12,2),
  reserved BOOLEAN DEFAULT false
);

CREATE TABLE stock_transactions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  part_id UUID NOT NULL REFERENCES parts(id),
  order_id UUID,
  type VARCHAR(50) NOT NULL,
  qty INTEGER NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  user_id UUID
);

CREATE EXTENSION IF NOT EXISTS pgcrypto;
