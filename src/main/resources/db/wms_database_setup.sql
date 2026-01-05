-- ============================================================
-- WMS DATABASE SETUP - SQLite
-- ============================================================
-- Database 1: wms_database.db (Users + Product Codes + Location Codes)
-- ============================================================

-- ============================================================
-- TABLE 1: USERS (Autenticazione)
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK(role IN('MAGAZZINIERE', 'UFFICIO')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indice per login veloce
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Dati di test
INSERT OR IGNORE INTO users (name, surname, username, password, role) VALUES
    ('Mario', 'Rossi', 'MR001', 'pippo123', 'MAGAZZINIERE'),
    ('Francesco', 'Bianchi', 'FB002', 'pippo123', 'UFFICIO'),
    ('Giovanni', 'Verdi', 'GV003', 'pippo123', 'MAGAZZINIERE'),
    ('Laura', 'Neri', 'LN004', 'pippo123', 'UFFICIO');

-- ============================================================
-- TABLE 2: PRODUCT_CODES (Codici Prodotto)
-- ============================================================
CREATE TABLE IF NOT EXISTS product_codes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code_prefix TEXT UNIQUE NOT NULL,
    product_name TEXT NOT NULL,
    description TEXT,
    category TEXT CHECK(category IN ('FRUTTA', 'VERDURA', 'ALTRO')),
    shelf_life_days INTEGER NOT NULL DEFAULT 30,
    is_active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indici per ricerca veloce
CREATE UNIQUE INDEX IF NOT EXISTS idx_product_code ON product_codes(code_prefix);
CREATE INDEX IF NOT EXISTS idx_product_category ON product_codes(category);
CREATE INDEX IF NOT EXISTS idx_product_active ON product_codes(is_active);

-- Dati di test - FRUTTA (6 prodotti)
INSERT OR IGNORE INTO product_codes (code_prefix, product_name, description, category, shelf_life_days) VALUES
    ('1001', 'Mele Golden', 'Mele Golden di qualità A, dolci e croccanti', 'FRUTTA', 45),
    ('1002', 'Mele Fuji', 'Mele Fuji rosse, succose e equilibrate', 'FRUTTA', 40),
    ('1003', 'Arance Rosse', 'Arance rosse siciliane, ricche di vitamina C', 'FRUTTA', 50),
    ('1004', 'Banane', 'Banane gialle da maturare, ricche di potassio', 'FRUTTA', 12),
    ('1005', 'Limoni', 'Limoni IGP da Sorrento, succosi e profumati', 'FRUTTA', 35),
    ('1006', 'Fragole', 'Fragole fresche, delicate e profumate', 'FRUTTA', 7);

-- Dati di test - VERDURA (6 prodotti)
INSERT OR IGNORE INTO product_codes (code_prefix, product_name, description, category, shelf_life_days) VALUES
    ('2001', 'Radicchio Tardivo', 'Radicchio tardivo di Treviso IGP, croccante', 'VERDURA', 25),
    ('2002', 'Lattuga Iceberg', 'Lattuga iceberg freschissima, croccante', 'VERDURA', 10),
    ('2003', 'Spinaci', 'Spinaci freschi ricchi di ferro', 'VERDURA', 8),
    ('2004', 'Pomodori Ciliegini', 'Pomodori ciliegini maturi e dolci', 'VERDURA', 15),
    ('2005', 'Insalata Mista', 'Mix di insalate fresche e colorate', 'VERDURA', 6),
    ('2006', 'Zucchine', 'Zucchine verde tenere e digeribili', 'VERDURA', 20);

-- Dati di test - ALTRO (4 prodotti)
INSERT OR IGNORE INTO product_codes (code_prefix, product_name, description, category, shelf_life_days) VALUES
    ('3001', 'Funghi Prataioli', 'Funghi prataioli freschi e carnosi', 'ALTRO', 5),
    ('3002', 'Melanzane', 'Melanzane viola scuro, morbide', 'ALTRO', 18),
    ('3003', 'Carote', 'Carote arancioni dolci e succose', 'ALTRO', 40),
    ('3004', 'Patate Rosse', 'Patate rosse da tavola, cremose', 'ALTRO', 60);

-- ============================================================
-- TABLE 3: LOCATION_CODES (Codici Locazione Magazzino)
-- ============================================================
CREATE TABLE IF NOT EXISTS location_codes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    location_barcode TEXT UNIQUE NOT NULL,
    zone TEXT NOT NULL,
    aisle TEXT NOT NULL,
    slot TEXT NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT 1,
    capacity_kg DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indici per ricerca veloce
CREATE UNIQUE INDEX IF NOT EXISTS idx_location_barcode ON location_codes(location_barcode);
CREATE INDEX IF NOT EXISTS idx_location_zone ON location_codes(zone);
CREATE INDEX IF NOT EXISTS idx_location_active ON location_codes(is_active);

-- Dati di test - ZONA A (Frutta Fredda - 12 posizioni)
INSERT OR IGNORE INTO location_codes (location_barcode, zone, aisle, slot, description, is_active, capacity_kg) VALUES
    ('LOC-A-01-001', 'A', '1', '1', 'Zona A, Corridoio 1, Piazzola 1', 1, 500.00),
    ('LOC-A-01-002', 'A', '1', '2', 'Zona A, Corridoio 1, Piazzola 2', 1, 500.00),
    ('LOC-A-01-003', 'A', '1', '3', 'Zona A, Corridoio 1, Piazzola 3', 1, 500.00),
    ('LOC-A-02-001', 'A', '2', '1', 'Zona A, Corridoio 2, Piazzola 1', 1, 500.00),
    ('LOC-A-02-002', 'A', '2', '2', 'Zona A, Corridoio 2, Piazzola 2', 1, 500.00),
    ('LOC-A-02-003', 'A', '2', '3', 'Zona A, Corridoio 2, Piazzola 3', 1, 500.00),
    ('LOC-A-03-001', 'A', '3', '1', 'Zona A, Corridoio 3, Piazzola 1', 1, 500.00),
    ('LOC-A-03-002', 'A', '3', '2', 'Zona A, Corridoio 3, Piazzola 2', 1, 500.00),
    ('LOC-A-03-003', 'A', '3', '3', 'Zona A, Corridoio 3, Piazzola 3', 1, 500.00),
    ('LOC-A-04-001', 'A', '4', '1', 'Zona A, Corridoio 4, Piazzola 1', 1, 500.00),
    ('LOC-A-04-002', 'A', '4', '2', 'Zona A, Corridoio 4, Piazzola 2', 1, 500.00),
    ('LOC-A-04-003', 'A', '4', '3', 'Zona A, Corridoio 4, Piazzola 3', 1, 500.00);

-- Dati di test - ZONA B (Verdura Fresca - 12 posizioni)
INSERT OR IGNORE INTO location_codes (location_barcode, zone, aisle, slot, description, is_active, capacity_kg) VALUES
    ('LOC-B-01-001', 'B', '1', '1', 'Zona B, Corridoio 1, Piazzola 1', 1, 400.00),
    ('LOC-B-01-002', 'B', '1', '2', 'Zona B, Corridoio 1, Piazzola 2', 1, 400.00),
    ('LOC-B-01-003', 'B', '1', '3', 'Zona B, Corridoio 1, Piazzola 3', 1, 400.00),
    ('LOC-B-02-001', 'B', '2', '1', 'Zona B, Corridoio 2, Piazzola 1', 1, 400.00),
    ('LOC-B-02-002', 'B', '2', '2', 'Zona B, Corridoio 2, Piazzola 2', 1, 400.00),
    ('LOC-B-02-003', 'B', '2', '3', 'Zona B, Corridoio 2, Piazzola 3', 1, 400.00),
    ('LOC-B-03-001', 'B', '3', '1', 'Zona B, Corridoio 3, Piazzola 1', 1, 400.00),
    ('LOC-B-03-002', 'B', '3', '2', 'Zona B, Corridoio 3, Piazzola 2', 1, 400.00),
    ('LOC-B-03-003', 'B', '3', '3', 'Zona B, Corridoio 3, Piazzola 3', 1, 400.00),
    ('LOC-B-04-001', 'B', '4', '1', 'Zona B, Corridoio 4, Piazzola 1', 1, 400.00),
    ('LOC-B-04-002', 'B', '4', '2', 'Zona B, Corridoio 4, Piazzola 2', 1, 400.00),
    ('LOC-B-04-003', 'B', '4', '3', 'Zona B, Corridoio 4, Piazzola 3', 1, 400.00);

-- Dati di test - ZONA C (Magazzino Generale - 12 posizioni)
INSERT OR IGNORE INTO location_codes (location_barcode, zone, aisle, slot, description, is_active, capacity_kg) VALUES
    ('LOC-C-01-001', 'C', '1', '1', 'Zona C, Corridoio 1, Piazzola 1', 1, 600.00),
    ('LOC-C-01-002', 'C', '1', '2', 'Zona C, Corridoio 1, Piazzola 2', 1, 600.00),
    ('LOC-C-01-003', 'C', '1', '3', 'Zona C, Corridoio 1, Piazzola 3', 1, 600.00),
    ('LOC-C-02-001', 'C', '2', '1', 'Zona C, Corridoio 2, Piazzola 1', 1, 600.00),
    ('LOC-C-02-002', 'C', '2', '2', 'Zona C, Corridoio 2, Piazzola 2', 1, 600.00),
    ('LOC-C-02-003', 'C', '2', '3', 'Zona C, Corridoio 2, Piazzola 3', 1, 600.00),
    ('LOC-C-03-001', 'C', '3', '1', 'Zona C, Corridoio 3, Piazzola 1', 1, 600.00),
    ('LOC-C-03-002', 'C', '3', '2', 'Zona C, Corridoio 3, Piazzola 2', 1, 600.00),
    ('LOC-C-03-003', 'C', '3', '3', 'Zona C, Corridoio 3, Piazzola 3', 1, 600.00),
    ('LOC-C-04-001', 'C', '4', '1', 'Zona C, Corridoio 4, Piazzola 1', 1, 600.00),
    ('LOC-C-04-002', 'C', '4', '2', 'Zona C, Corridoio 4, Piazzola 2', 1, 600.00),
    ('LOC-C-04-003', 'C', '4', '3', 'Zona C, Corridoio 4, Piazzola 3', 1, 600.00);

-- Dati di test - ZONA D (Scorte/Backup - 6 posizioni, alcune disattivate)
INSERT OR IGNORE INTO location_codes (location_barcode, zone, aisle, slot, description, is_active, capacity_kg) VALUES
    ('LOC-D-01-001', 'D', '1', '1', 'Zona D, Corridoio 1, Piazzola 1 (MANUTENZIONE)', 0, 500.00),
    ('LOC-D-01-002', 'D', '1', '2', 'Zona D, Corridoio 1, Piazzola 2', 1, 500.00),
    ('LOC-D-02-001', 'D', '2', '1', 'Zona D, Corridoio 2, Piazzola 1', 1, 500.00),
    ('LOC-D-02-002', 'D', '2', '2', 'Zona D, Corridoio 2, Piazzola 2', 1, 500.00),
    ('LOC-D-03-001', 'D', '3', '1', 'Zona D, Corridoio 3, Piazzola 1 (MANUTENZIONE)', 0, 500.00),
    ('LOC-D-03-002', 'D', '3', '2', 'Zona D, Corridoio 3, Piazzola 2', 1, 500.00);
