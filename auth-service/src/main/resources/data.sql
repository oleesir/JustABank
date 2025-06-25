CREATE TABLE IF NOT EXISTS "users" (
                       id UUID PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);



INSERT INTO "users" (id, first_name, last_name, password, email, address, role)
SELECT '93c1791e-d6c6-4763-9cf3-e3af543ea3b8',
       'Admin',
       'Super',
       '$2y$10$JMYX5K90Fr6Ws/Eh5sQTCekquqXIkZBE9V1yBr7yxX5w88e9EjMxe',
       'admin@example.com',
       '123 Admin Lane',
       'ROLE_ADMIN'
    WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '93c1791e-d6c6-4763-9cf3-e3af543ea3b8'
       OR email = 'admin@example.com'
);