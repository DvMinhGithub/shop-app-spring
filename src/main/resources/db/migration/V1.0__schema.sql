CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) DEFAULT '',
    password VARCHAR(100) NOT NULL DEFAULT '', -- mật khẩu đã mã hóa
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1,
    date_of_birth DATE,
    facebook_account_id BIGINT DEFAULT 0,
    role_id BIGINT
);

CREATE TABLE tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id BIGINT
);

CREATE TABLE social_accounts(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
    name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
    user_id BIGINT
);

CREATE TABLE categories(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

CREATE TABLE products(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK(price >= 0),
    description LONGTEXT,
    created_at DATETIME,
    updated_at DATETIME,
    category_id BIGINT
);

CREATE TABLE product_images(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    image_url VARCHAR(300) NOT NULL
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status enum('PENDING', 'SHIPPING', 'DELIVERED', 'CANCELED') DEFAULT 'PENDING',
    total_money FLOAT CHECK(total_money >= 0),
    shipping_method VARCHAR(50),
    shipping_address VARCHAR(200),
    shipping_date DATE,
    tracking_number VARCHAR(50),
    payment_method VARCHAR(50),
    is_active TINYINT(1) DEFAULT 1
);

CREATE TABLE order_details(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id BIGINT,
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);

-- FOREIGN KEY

ALTER TABLE users
ADD FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE tokens
ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE social_accounts
ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE products
ADD FOREIGN KEY (category_id) REFERENCES categories(id);

ALTER TABLE product_images
ADD FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE orders
ADD FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE order_details
ADD FOREIGN KEY (order_id) REFERENCES orders(id);

ALTER TABLE order_details
ADD FOREIGN KEY (product_id) REFERENCES products(id);