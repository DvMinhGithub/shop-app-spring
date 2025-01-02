INSERT INTO roles (name) VALUES
('Admin'),
('User'),
('Moderator'),
('Guest');

INSERT INTO categories (name) VALUES
('Điện tử'), 
('Sách'),
('Thời trang'),
('Đồ chơi');

INSERT INTO products (name, price, description, created_at, updated_at, category_id) VALUES
('MacBook Air 15 inch 2023', 1200.00, 'MacBook Air mới nhất với chip M2', NOW(), NOW(), 1),
-- ('iPhone 15 Pro', 999.00,  'iPhone mới nhất với các tính năng cao cấp', NOW(), NOW(), 5),
('Sách Lập trình Java', 45.00, 'Hướng dẫn toàn diện về lập trình Java', NOW(), NOW(), 2),
('Váy hè', 35.00, 'Váy hè nhẹ nhàng', NOW(), NOW(), 3),
('Bộ xếp hình Lego', 50.00, 'Bộ xếp hình Lego sáng tạo cho trẻ em', NOW(), NOW(), 4);
