-- Nailong Order Service core schema
DROP TABLE IF EXISTS after_sale;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS address;

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    province VARCHAR(64) NOT NULL,
    city VARCHAR(64) NOT NULL,
    district VARCHAR(64) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(32) NOT NULL,
    receiver_name VARCHAR(64) NOT NULL,
    is_default TINYINT(1) NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    order_id VARCHAR(32) PRIMARY KEY,
    client INT NOT NULL,
    address_id INT NOT NULL,
    item_id INT NOT NULL,
    amount INT NOT NULL,
    total_price INT NOT NULL,
    supplier INT NOT NULL,
    status INT NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL,
    pay_time DATETIME NULL,
    pay_method VARCHAR(32) NULL,
    ship_time DATETIME NULL,
    confirm_time DATETIME NULL,
    after_sale_time DATETIME NULL,
    remark VARCHAR(255) NULL,
    is_occupy TINYINT(1) NOT NULL DEFAULT 0,
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(32) NOT NULL,
    item_id INT NOT NULL,
    item_num INT NOT NULL,
    item_name VARCHAR(128) NOT NULL,
    item_url VARCHAR(255) NULL,
    item_price INT NOT NULL,
    user_id INT NOT NULL,
    status INT NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

CREATE TABLE after_sale (
    order_id VARCHAR(32) PRIMARY KEY,
    after_sale_status INT NOT NULL DEFAULT 0,
    business_solve INT NOT NULL DEFAULT 0,
    admin_solve INT NOT NULL DEFAULT 0,
    business_solve_time DATETIME NULL,
    admin_solve_time DATETIME NULL,
    CONSTRAINT fk_after_sale_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- Sample data for development
INSERT INTO address (user_id, province, city, district, detail_address, phone_number, receiver_name, is_default)
VALUES
    (1, '广西', '南宁', '青秀区', '民族大道 88 号', '13800138000', '李雷', 1),
    (2, '广东', '广州', '天河区', '花城大道 66 号', '13900139000', '韩梅梅', 1);

INSERT INTO orders (order_id, client, address_id, item_id, amount, total_price, supplier, status, create_time, pay_time, pay_method, ship_time, confirm_time, after_sale_time, remark, is_occupy)
VALUES
    ('202401010001', 1, 1, 101, 2, 3998, 9001, 1, NOW(), NOW(), 'ALIPAY', NOW(), NULL, NULL, '首单优惠', 0),
    ('202401020002', 2, 2, 102, 1, 1999, 9002, 2, NOW(), NOW(), 'WECHAT', NOW(), NOW(), NULL, NULL, 0);

INSERT INTO order_item (order_id, item_id, item_num, item_name, item_url, item_price, user_id, status)
VALUES
    ('202401010001', 101, 2, '奶龙限定手办', 'https://static.example.com/item101.png', 1999, 1, 1),
    ('202401020002', 102, 1, '奶龙抱枕', 'https://static.example.com/item102.png', 1999, 2, 2);

INSERT INTO after_sale (order_id, after_sale_status, business_solve, admin_solve)
VALUES
    ('202401010001', 0, 0, 0),
    ('202401020002', 1, 1, 0);
