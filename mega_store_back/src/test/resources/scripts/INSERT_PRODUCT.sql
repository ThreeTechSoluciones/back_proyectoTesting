INSERT INTO categorias (id, nombre, fecha_eliminacion) VALUES (1, 'Oversize', null);
INSERT INTO marcas (id, nombre, fecha_eliminacion) VALUES (1, 'Nike', null);
INSERT INTO talles (id, nombre, fecha_eliminacion) VALUES (1, 'L', null);
INSERT INTO colores (id, nombre, fecha_eliminacion) VALUES (1, 'Negro', null);

INSERT INTO productos (id, nombre, descripcion, precio, peso, foto, stock_minimo, stock_medio, stock_actual, categoria_id, marca_id, talle_id, color_id, fecha_creacion, fecha_eliminacion, usuario_eliminacion) VALUES
(1, 'Remera Oversize', null, 25000, 20, 'foto', 1, 10, 4, 1, 1, 1, 1, CURRENT_TIMESTAMP, null, null);