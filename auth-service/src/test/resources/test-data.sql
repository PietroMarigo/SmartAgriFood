DELETE FROM users;
INSERT INTO users (name, surname, username, email, password, role, active, created_at) 
VALUES 
('Mario', 'Rossi', 'MR001', 'mario@example.com', '$2a$12$HMjMyK65W59e6h5lmW6YC.TEzMsTCsmau9ip65NVYG1kSNFDKOdm.', 'MAGAZZINIERE', 1, 1704700800000),
('Francesco', 'Bianchi', 'FB002', 'francesco@example.com', '$2a$12$HMjMyK65W59e6h5lmW6YC.TEzMsTCsmau9ip65NVYG1kSNFDKOdm.', 'UFFICIO', 1, 1704700800000),
('Giovanni', 'Verdi', 'GV003', 'giovanni@example.com', '$2a$12$HMjMyK65W59e6h5lmW6YC.TEzMsTCsmau9ip65NVYG1kSNFDKOdm.', 'MAGAZZINIERE', 1, 1704700800000),
('Laura', 'Neri', 'LN004', 'laura@example.com', '$2a$12$HMjMyK65W59e6h5lmW6YC.TEzMsTCsmau9ip65NVYG1kSNFDKOdm.', 'UFFICIO', 1, 1704700800000),
('Pietro', 'Smusi', 'PS005', 'pietro.smusi@warehouse.it', '$2a$12$HMjMyK65W59e6h5lmW6YC.TEzMsTCsmau9ip65NVYG1kSNFDKOdm.', 'MAGAZZINIERE', 0, 1704700800000);
