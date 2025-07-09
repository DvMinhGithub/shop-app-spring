INSERT INTO db_shop_app.roles (name) VALUES
('Admin'),
('User'),
('Moderator'),
('Guest');

INSERT INTO db_shop_app.categories (name) VALUES
('Điện tử'), 
('Sách'),
('Thời trang'),
('Đồ chơi');

INSERT INTO db_shop_app.products (name, price, description, category_id) VALUES
('MacBook Air 15 inch 2023', 1200.00, 'MacBook Air mới nhất với chip M2', 1),
('iPhone 15 Pro', 999.00,  'iPhone mới nhất với các tính năng cao cấp', 1),
('Sách Lập trình Java', 45.00, 'Hướng dẫn toàn diện về lập trình Java', 2),
('Váy hè', 35.00, 'Váy hè nhẹ nhàng', 3),
('Bộ xếp hình Lego', 50.00, 'Bộ xếp hình Lego sáng tạo cho trẻ em', 4);
