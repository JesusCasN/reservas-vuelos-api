-- Insertar roles
INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('CLIENTE');

-- Insertar vuelos
INSERT INTO vuelos (aerolinea, origen, destino, fecha_salida, fecha_llegada, precio, asientos_disponibles) VALUES
                                                                                                               ('Aeroméxico', 'Ciudad de México', 'Cancún', '2025-05-10 08:00:00', '2025-05-10 10:00:00', 2500.00, 100),
                                                                                                               ('VivaAerobus', 'Guadalajara', 'Monterrey', '2025-05-12 13:30:00', '2025-05-12 15:00:00', 1800.00, 80),
                                                                                                               ('Volaris', 'Tijuana', 'Ciudad de México', '2025-05-14 06:00:00', '2025-05-14 09:30:00', 3000.00, 120),
                                                                                                               ('Interjet', 'Monterrey', 'Los Cabos', '2025-05-20 10:00:00', '2025-05-20 12:30:00', 3200.00, 70);
