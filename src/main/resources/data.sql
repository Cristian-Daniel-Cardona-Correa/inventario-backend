-- Script de creación y datos de prueba para el inventario con URLs de imágenes

CREATE TABLE IF NOT EXISTS productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo VARCHAR(255) NOT NULL UNIQUE,
    descripcion TEXT,
    precio NUMERIC(12, 2) NOT NULL,
    cantidad INTEGER NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    imagen_url TEXT
);

INSERT INTO productos (nombre, codigo, descripcion, precio, cantidad, categoria, imagen_url) VALUES
('Laptop Dell Inspiron 15', 'LAP-001', 'Laptop con procesador Intel Core i5, 8GB RAM, 512GB SSD', 2499000.00, 15, 'Computadores', 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=500&q=80'),
('Mouse Logitech MX Master 3', 'MOU-001', 'Mouse inalambrico ergonomico con scroll horizontal y multidispositivo', 189000.00, 42, 'Perifericos', 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500&q=80'),
('Teclado Mecanico Redragon K552', 'TEC-001', 'Teclado mecanico compacto RGB con switches rojo', 145000.00, 30, 'Perifericos', 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&q=80'),
('Monitor Samsung 24 pulgadas FHD', 'MON-001', 'Monitor 24 pulgadas Full HD 75Hz panel IPS con soporte VESA', 659000.00, 8, 'Monitores', 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=500&q=80'),
('Auriculares Sony WH-1000XM5', 'AUR-001', 'Auriculares inalambricos con cancelacion activa de ruido y 30h bateria', 899000.00, 20, 'Audio', 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80'),
('Webcam Logitech C920 HD Pro', 'CAM-001', 'Camara web Full HD 1080p para streaming y videollamadas profesionales', 279000.00, 25, 'Perifericos', 'https://images.unsplash.com/photo-1587826080692-f439cd0b70da?w=500&q=80'),
('Disco Duro Externo Seagate 1TB', 'DIS-001', 'Disco duro portatil USB 3.0 capacidad 1 Terabyte', 199000.00, 50, 'Almacenamiento', 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?w=500&q=80'),
('Hub USB-C 7 en 1', 'HUB-001', 'Adaptador multipuerto USB-C con HDMI 4K, USB 3.0 y lector SD', 89000.00, 35, 'Accesorios', 'https://images.unsplash.com/photo-1625842268584-8f3296236761?w=500&q=80'),
('Silla Ergonomica Gamer Pro', 'SIL-001', 'Silla de oficina con soporte lumbar, reposabrazos 4D y reclinacion 160', 789000.00, 10, 'Mobiliario', 'https://images.unsplash.com/photo-1598550476439-6847785fcea6?w=500&q=80'),
('Impresora HP LaserJet Pro M15w', 'IMP-001', 'Impresora laser monocromatica con WiFi, duplex automatico y pantalla LCD', 549000.00, 12, 'Impresoras', 'https://images.unsplash.com/photo-1612815154858-60aa4c59eaa6?w=500&q=80')
ON CONFLICT (codigo) DO UPDATE SET
  imagen_url = EXCLUDED.imagen_url,
  nombre = EXCLUDED.nombre,
  precio = EXCLUDED.precio,
  cantidad = EXCLUDED.cantidad,
  categoria = EXCLUDED.categoria,
  descripcion = EXCLUDED.descripcion;
