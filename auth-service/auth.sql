CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK(role IN('MAGAZZINIERE', 'UFFICIO')),
    active BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users(email);

INSERT OR IGNORE INTO users (name, surname, username, email, password, role, active) VALUES
    ('Mario', 'Rossi', 'MR001', 'mario.rossi@warehouse.it', 'pippo123', 'MAGAZZINIERE', 1),
    ('Francesco', 'Bianchi', 'FB002', 'francesco.bianchi@warehouse.it', 'pippo123', 'UFFICIO', 1),
    ('Giovanni', 'Verdi', 'GV003', 'giovanni.verdi@warehouse.it', 'pippo123', 'MAGAZZINIERE', 1),
    ('Laura', 'Neri', 'LN004', 'laura.neri@warehouse.it', 'pippo123', 'UFFICIO', 1);

