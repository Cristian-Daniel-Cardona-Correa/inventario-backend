-- Script de datos de prueba para el inventario
-- Ejecutar después de que Hibernate cree las tablas:
-- psql -h localhost -U inventario_user -d inventario_db -f src/main/resources/data.sql

INSERT INTO productos (nombre, codigo, descripcion, precio, cantidad, categoria) VALUES
('Laptop Dell Inspiron 15', 'LAP-001', 'Laptop con procesador Intel Core i5, 8GB RAM, 512GB SSD', 2499000.00, 15, 'Computadores'),
('Mouse Logitech MX Master 3', 'MOU-001', 'Mouse inalambrico ergonomico con scroll horizontal y multidispositivo', 189000.00, 42, 'Perifericos'),
('Teclado Mecanico Redragon K552', 'TEC-001', 'Teclado mecanico compacto RGB con switches rojo', 145000.00, 30, 'Perifericos'),
('Monitor Samsung 24 pulgadas FHD', 'MON-001', 'Monitor 24 pulgadas Full HD 75Hz panel IPS con soporte VESA', 659000.00, 8, 'Monitores'),
('Auriculares Sony WH-1000XM5', 'AUR-001', 'Auriculares inalambricos con cancelacion activa de ruido y 30h bateria', 899000.00, 20, 'Audio'),
('Webcam Logitech C920 HD Pro', 'CAM-001', 'Camara web Full HD 1080p para streaming y videollamadas profesionales', 279000.00, 25, 'Perifericos'),
('Disco Duro Externo Seagate 1TB', 'DIS-001', 'Disco duro portatil USB 3.0 capacidad 1 Terabyte', 199000.00, 50, 'Almacenamiento'),
('Hub USB-C 7 en 1', 'HUB-001', 'Adaptador multipuerto USB-C con HDMI 4K, USB 3.0 y lector SD', 89000.00, 35, 'Accesorios'),
('Silla Ergonomica Gamer Pro', 'SIL-001', 'Silla de oficina con soporte lumbar, reposabrazos 4D y reclinacion 160', 789000.00, 10, 'Mobiliario'),
('Impresora HP LaserJet Pro M15w', 'IMP-001', 'Impresora laser monocromatica con WiFi, duplex automatico y pantalla LCD', 549000.00, 12, 'Impresoras');
