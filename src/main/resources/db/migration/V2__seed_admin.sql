INSERT INTO roles (id, name)
VALUES (gen_random_uuid(), 'ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (id, username, password, enabled)
VALUES (gen_random_uuid(), 'admin', crypt('Challenge@2024', gen_salt('bf')), true)
ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u CROSS JOIN roles r WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;
