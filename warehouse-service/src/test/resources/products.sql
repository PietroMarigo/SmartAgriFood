DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sku VARCHAR(10) UNIQUE NOT NULL, -- Format: A001, A002
    name VARCHAR(100) NOT NULL,
    shelf_life_days INTEGER NOT NULL,
    category VARCHAR(50)
);

-- Initial Test Data
INSERT INTO products (sku, name, shelf_life_days, category) VALUES 
('A001', 'Mele Golden', 30, 'FRUTTA'),
('A002', 'Banane', 7, 'FRUTTA'),
('A003', 'Pomodori', 10, 'VERDURA');
