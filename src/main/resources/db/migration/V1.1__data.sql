INSERT INTO db_shop_app.roles (name) VALUES
('Admin'),
('User'),
('Moderator'),
('Guest');

INSERT INTO db_shop_app.categories (name) VALUES
('Tai nghe'),
('Sac'),
('Cap'),
('Pin du phong'),
('Op lung & dan man'),
('Gia do & phu kien'),
('Chuot'),
('Ban phim'),
('Phu kien laptop'),
('Thiet bi mang');

INSERT INTO db_shop_app.products (name, price, description, category_id) VALUES
('Tai nghe Bluetooth True Wireless A1', 29.90, 'Tai nghe TWS co hop sac, ket noi Bluetooth 5.3', 1),
('Tai nghe choi game G1', 24.50, 'Tai nghe co mic, am thanh stereo, de deo', 1),
('Tai nghe chong on C2', 59.00, 'Tai nghe over-ear, chong on chu dong', 1),
('Tai nghe the thao S3', 19.90, 'Tai nghe kieu vong co, pin 20 gio', 1),
('Tai nghe earbuds E4', 12.90, 'Tai nghe co day, jack 3.5mm', 1),
('Tai nghe Bluetooth T5', 34.90, 'Tai nghe TWS pin 30 gio, sac nhanh', 1),
('Tai nghe Bluetooth T6', 39.90, 'TWS ho tro codec AAC, do tre thap', 1),
('Tai nghe the thao S5', 21.90, 'Kieu vong co, khang nuoc IPX5', 1),
('Tai nghe on-ear H1', 27.90, 'Tai nghe nhe, dem tai mem', 1),
('Mic cho tai nghe G2', 6.90, 'Mic roi cho tai nghe game', 1),

('Cu sac nhanh 20W', 9.90, 'Cu sac USB-C PD 20W', 2),
('Cu sac nhanh 30W', 14.90, 'Cu sac USB-C PD 30W', 2),
('Sac khong day 15W', 15.90, 'De sac khong day ho tro 15W', 2),
('Cu sac nhanh 45W', 19.90, 'Cu sac USB-C PD 45W', 2),
('Cu sac 2 cong 35W', 17.90, 'Cu sac 2 cong USB-C', 2),
('Sac xe hoi 30W', 11.90, 'Cu sac xe hoi 30W', 2),
('Sac khong day 3-in-1', 29.90, 'Sac khong day cho dien thoai + dong ho + tai nghe', 2),

('Cap USB-C to USB-C 1m', 4.50, 'Cap du lieu va sac 60W', 3),
('Cap Lightning 1m', 5.90, 'Cap sac Lightning chuan MFi', 3),
('Cap USB-A to USB-C 1.5m', 4.90, 'Cap sac va truyen du lieu', 3),
('Cap USB-C to USB-C 2m', 6.90, 'Cap sac nhanh, do ben cao', 3),
('Cap USB-A to Lightning 2m', 7.90, 'Cap sac 2m, chuan MFi', 3),
('Cap USB-C to Lightning 1m', 8.90, 'Cap USB-C Lightning, sac nhanh', 3),

('Pin du phong 10000mAh', 18.90, 'Pin du phong nho gon, 2 cong sac', 4),
('Pin du phong 20000mAh', 29.90, 'Dung luong lon, ho tro sac nhanh', 4),
('Pin du phong 10000mAh co sac khong day', 24.90, 'Ho tro sac khong day 10W', 4),
('Pin du phong 5000mAh mini', 12.90, 'Sieu nho gon, bo tui', 4),
('Pin du phong 30000mAh', 39.90, 'Phu hop di chuyen dai ngay', 4),
('Pin du phong 10000mAh PD 20W', 22.90, 'Pin sac nhanh PD 20W', 4),
('Pin du phong 20000mAh PD 30W', 34.90, 'Pin sac nhanh PD 30W', 4),
('Pin du phong 10000mAh nho gon', 16.90, 'Pin nho gon, 1 cong USB-C', 4),
('Pin du phong co man hinh', 28.90, 'Hien thi phan tram pin', 4),
('Pin du phong cho may bay', 19.90, 'Dung luong duoc phep len may bay', 4),

('Op lung chong soc', 6.90, 'Op silicon chong va dap', 5),
('Cuong luc 9H', 3.90, 'Mieng dan man hinh 9H', 5),
('Op lung trong suot', 5.50, 'Op trong, chong xuoc', 5),
('Op lung chong va dap 360', 8.90, 'Op bao ve 360 do', 5),
('Cuong luc chong nhin trom', 6.90, 'Kinh cuong luc privacy', 5),
('Cuong luc full man', 4.90, 'Dan full man, chong bong bong', 5),

('Gia do dien thoai', 7.50, 'Gia do de ban, goc dieu chinh', 6),
('Gia do tren o to', 9.90, 'Gia do hut kinh cho o to', 6),
('Doc the USB-C', 8.50, 'Doc the nho cho dien thoai', 6),
('Hub OTG USB-C', 12.90, 'Chuyen USB-C sang USB-A', 6),
('Gia do de ban gap gon', 6.90, 'Gia do nho gon, de ban', 6),
('Cap chong roi dien thoai', 3.90, 'Day deo chong roi', 6),
('Chong nuoc dien thoai', 4.50, 'Tui chong nuoc, di bien', 6),
('Bop dung cap', 2.90, 'Hop nho dung cap sac', 6),

('Chuot khong day M1', 12.90, 'Chuot silent, ket noi 2.4G', 7),
('Chuot Bluetooth M2', 16.90, 'Chuot Bluetooth da thiet bi', 7),
('Chuot khong day silent', 14.90, 'Chuot click em, pin lau', 7),
('Chuot gaming co day', 19.90, 'Chuot 7 nut, DPI cao', 7),

('Ban phim co mini 68 key', 39.90, 'Ban phim co gon, switch brown', 8),
('Ban phim Bluetooth sieu mong', 29.90, 'Ban phim pin lau, ket noi BT', 8),
('Ban phim co TKL 87 key', 45.90, 'Ban phim co layout TKL', 8),
('Ban phim co switch red', 42.90, 'Switch red, go em', 8),

('Gia do laptop', 14.90, 'Gia do nhom, tang do thong thoang', 9),
('Tui chong soc laptop 14 inch', 11.90, 'Tui day, chong va dap', 9),
('Gia do laptop gap gon', 12.90, 'Gia do gap gon, de mang theo', 9),
('De tan nhiet laptop', 24.90, 'De quat 2 fan', 9),
('Tui chong soc laptop 15.6 inch', 13.90, 'Tui day, chong va dap', 9),
('Hub USB-C 5-in-1', 18.90, 'Hub USB-C 5 cong, ho tro HDMI', 9),

('Router Wi-Fi 6 AX1500', 49.90, 'Router Wi-Fi 6 phu hop nha nho', 10),
('Bo kich song Wi-Fi', 29.90, 'Kich song Wi-Fi, mo rong phu song', 10),
('Switch mang 5 port', 12.90, 'Switch 5 port 10/100/1000', 10),
('Bo phat 4G', 35.90, 'Bo phat 4G dung SIM', 10),
('Adapter USB Wi-Fi', 9.90, 'Adapter USB Wi-Fi bang tan 2.4G', 10),
('Router Wi-Fi 6 AX3000', 79.90, 'Router Wi-Fi 6, toc do cao', 10),
('Bo kich song Wi-Fi Mesh', 59.90, 'He thong Mesh 2 node', 10),
('Switch mang 8 port', 19.90, 'Switch 8 port 10/100/1000', 10),
('Cap mang CAT6 5m', 4.90, 'Cap mang CAT6 5m', 10),
('Bo phat 4G Pro', 49.90, 'Bo phat 4G pin 3000mAh', 10),
('Adapter USB Wi-Fi 5G', 14.90, 'Adapter USB Wi-Fi bang tan 5G', 10),

('Tai nghe Bluetooth T8', 44.90, 'TWS chong on, pin 28 gio', 1),
('Tai nghe Bluetooth T9', 49.90, 'TWS co sac khong day, am bass', 1),
('Tai nghe in-ear I1', 9.90, 'Tai nghe co day, am thanh can bang', 1),
('Tai nghe over-ear H2', 33.90, 'Over-ear, dem tai lon, nghe lau', 1),
('Tai nghe Bluetooth treo tai', 15.90, 'Treo tai, pin 12 gio', 1),

('Cu sac nhanh 65W', 29.90, 'Cu sac GaN 65W', 2),
('Cu sac 3 cong 65W', 34.90, 'Cu sac GaN 2C1A', 2),
('Sac khong day 10W', 12.90, 'Sac khong day 10W co den', 2),
('Sac khong day 2-in-1', 19.90, 'Sac dien thoai + dong ho', 2),
('Sac xe hoi 45W', 14.90, 'Cu sac xe hoi 45W', 2),

('Cap USB-C to USB-C 0.5m', 3.90, 'Cap ngan, do ben cao', 3),
('Cap USB-A to USB-C 0.5m', 3.50, 'Cap ngan, sac nhanh', 3),
('Cap Lightning 2m', 7.50, 'Cap dai 2m, do ben cao', 3),
('Cap sac nam cham', 6.90, 'Cap nam cham, thao lap nhanh', 3),
('Cap USB-C to HDMI', 12.90, 'Cap truyen man hinh', 3),

('Pin du phong 5000mAh sac nhanh', 14.90, 'Pin mini, sac nhanh 18W', 4),
('Pin du phong 15000mAh', 26.90, 'Dung luong vua, 2 cong', 4),
('Pin du phong 20000mAh co man hinh', 32.90, 'Hien thi %, 2 cong', 4),
('Pin du phong 10000mAh nam cham', 23.90, 'Nam cham cho dien thoai', 4),
('Pin du phong 30000mAh PD 45W', 44.90, 'Sac nhanh PD 45W', 4),

('Op lung chong soc 2 lop', 7.90, 'Op 2 lop, chong va dap', 5),
('Op lung kieu da', 9.90, 'Op kieu da, cam tay tot', 5),
('Cuong luc nham', 5.90, 'Chong loe, giam dau van tay', 5),
('Cuong luc camera', 4.90, 'Bao ve cum camera', 5),
('Dan man hinh deo', 3.50, 'Man deo, de dan', 5),

('Gia do kep ban', 8.90, 'Gia do kep ban, linh hoat', 6),
('Gia do de ban kim loai', 9.50, 'Gia do nhom, chac chan', 6),
('Day deo dien thoai', 3.50, 'Day deo co khoa kim loai', 6),
('Hop dung phu kien', 4.90, 'Hop dung sac, cap, nho gon', 6),
('Chot che chong that lac', 2.50, 'Phu kien chong that lac', 6),

('Chuot Bluetooth sieu nhe', 17.90, 'Chuot nhe, pin 3 thang', 7),
('Chuot van phong co day', 9.90, 'Chuot co day, ben', 7),
('Chuot gaming LED', 22.90, 'Chuot gaming LED, 6 nut', 7),
('Chuot vertical', 24.90, 'Chuot cong thai hoc', 7),
('Chuot Bluetooth im lang', 18.90, 'Click em, ket noi BT', 7),

('Ban phim co 75%', 49.90, 'Layout 75%, switch blue', 8),
('Ban phim co 104 key', 54.90, 'Full size, switch brown', 8),
('Ban phim Bluetooth 2 thiet bi', 31.90, 'Ket noi 2 thiet bi', 8),
('Ban phim co hot-swap', 69.90, 'Hot-swap, de thay switch', 8),
('Ban phim mini 60%', 35.90, 'Layout 60%, nho gon', 8),

('De tan nhiet laptop RGB', 29.90, 'De quat RGB, 2 fan', 9),
('Gia do laptop cong thai hoc', 19.90, 'Tang goc nhin, giam moi', 9),
('Hub USB-C 7-in-1', 26.90, 'Hub 7 cong, ho tro HDMI', 9),
('Tui chong soc laptop 13 inch', 10.90, 'Tui 13 inch, nho gon', 9),
('Lop dan bao ve laptop', 7.90, 'Dan bao ve, chong xuoc', 9),

('Router Wi-Fi 5 AC1200', 39.90, 'Router Wi-Fi 5, gia tot', 10),
('Switch mang 16 port', 34.90, 'Switch 16 port 10/100/1000', 10),
('Bo phat 4G du phong', 29.90, 'Bo phat 4G, nho gon', 10),
('Adapter USB Wi-Fi toc do cao', 19.90, 'USB Wi-Fi, 2 bang tan', 10),
('Cap mang CAT6 10m', 6.90, 'Cap mang CAT6 10m', 10);

INSERT INTO db_shop_app.product_images (product_id, image_url)
SELECT p.id, u.url
FROM db_shop_app.products p
JOIN db_shop_app.categories c ON p.category_id = c.id
JOIN (
  SELECT 'Tai nghe' AS category, 'https://images.unsplash.com/photo-1737291937135-3a0fcb5e0c44?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDJ8fHx8fHx8fDE3NTkwMTcyODV8&ixlib=rb-4.1.0&q=60&w=3000' AS url UNION ALL
  SELECT 'Tai nghe', 'https://images.unsplash.com/photo-1549172573-01f758949251?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDV8fHx8fHx8fDE3NTkwMTcyODV8&ixlib=rb-4.0.3&q=60&w=3000' UNION ALL
  SELECT 'Tai nghe', 'https://images.unsplash.com/photo-1757946718516-fddeb8d3ed9b?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE1fHx8fHx8fDE3NTkwMTcyODV8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Sac', 'https://images.unsplash.com/photo-1758578070291-0c22ff555df9?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDJ8fHx8fHx8fDE3NTkwMTc1NTZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Sac', 'https://images.unsplash.com/photo-1750332191594-d5387a69f7c8?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDd8fHx8fHx8fDE3NTkwMTc1NTZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Sac', 'https://images.unsplash.com/photo-1629960414183-fba0e2e137e7?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE0fHx8fHx8fDE3NTkwMTc1ODF8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Cap', 'https://images.unsplash.com/photo-1605192020788-24d8eae86e59?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDJ8fHx8fHx8fDE3NTkwMTc3MDJ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Cap', 'https://images.unsplash.com/photo-1729549223893-b340db51e577?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDR8fHx8fHx8fDE3NTkwMTc3MDJ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Cap', 'https://images.unsplash.com/photo-1739742473151-d73df9c2a7b9?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE5fHx8fHx8fDE3NTkwMTc3MDJ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Pin du phong', 'https://images.unsplash.com/photo-1667411424245-6e1b9d0d055e?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE0fHx8fHx8fDE3NTkwMTc4ODR8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Pin du phong', 'https://images.unsplash.com/photo-1564286026321-4e3ad4d6022d?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE1fHx8fHx8fDE3NTkwMTc4ODR8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Pin du phong', 'https://images.unsplash.com/photo-1618939979009-623191b2d396?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE0fHx8fHx8fDE3NTkwMTc5MDZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Op lung & dan man', 'https://images.unsplash.com/photo-1740988255458-cd9eb104ca7b?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDR8fHx8fHx8fDE3NTkwMTgzNTZ8&ixlib=rb-4.0.3&q=60&w=3000' UNION ALL
  SELECT 'Op lung & dan man', 'https://images.unsplash.com/photo-1670886241169-79cadea8f4cd?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDZ8fHx8fHx8fDE3NTkwMTgzNTZ8&ixlib=rb-4.0.3&q=60&w=3000' UNION ALL
  SELECT 'Op lung & dan man', 'https://images.unsplash.com/photo-1658369354760-894ec27b488a?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDd8fHx8fHx8fDE3NTkwMTgzNTZ8&ixlib=rb-4.0.3&q=60&w=3000' UNION ALL

  SELECT 'Gia do & phu kien', 'https://images.unsplash.com/photo-1681422480866-6f6b81f72bbf?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDd8fHx8fHx8fDE3NTkwMTg1MTV8&ixlib=rb-4.0.3&q=60&w=3000' UNION ALL
  SELECT 'Gia do & phu kien', 'https://images.unsplash.com/photo-1758914223866-d4c278a26849?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDEwfHx8fHx8fDE3NTkwMTg1MTV8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Gia do & phu kien', 'https://images.unsplash.com/photo-1753802864817-e925fba730b4?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDEzfHx8fHx8fDE3NTkwMTg1MTV8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Chuot', 'https://images.unsplash.com/photo-1739742473235-34a7bd9b8f87?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE0fHx8fHx8fDE3NTkwMTg4MDJ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Chuot', 'https://images.unsplash.com/photo-1496128858413-b36217c2ce36?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE1fHx8fHx8fDE3NTkwMTg4MDJ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Chuot', 'https://images.unsplash.com/photo-1636487658538-ce06868f2ff0?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDE1fHx8fHx8fDE3NTkwMTg4MTN8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Ban phim', 'https://images.unsplash.com/photo-1755985022555-09c0ec136e95?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDN8fHx8fHx8fDE3NTkwMTg4NTR8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Ban phim', 'https://images.unsplash.com/photo-1768445386309-cc291d2c77dc?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDd8fHx8fHx8fDE3NTkwMTg4NTR8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Ban phim', 'https://images.unsplash.com/photo-1755986008154-c81f2c289d78?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDh8fHx8fHx8fDE3NTkwMTg4NTR8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Phu kien laptop', 'https://images.unsplash.com/photo-1763034179057-acad3a072568?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDJ8fHx8fHx8fDE3NTkwMTg5NTZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Phu kien laptop', 'https://images.unsplash.com/photo-1667411424771-cadd97150827?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDZ8fHx8fHx8fDE3NTkwMTg5NTZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Phu kien laptop', 'https://images.unsplash.com/photo-1667411425122-8b6be5da1c48?fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDd8fHx8fHx8fDE3NTkwMTg5NTZ8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL

  SELECT 'Thiet bi mang', 'https://images.unsplash.com/photo-1750711158632-5273ec9b9b86?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDEwfHx8fHx8fDE3NTkwMTkwNTB8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Thiet bi mang', 'https://images.unsplash.com/photo-1750712263185-edde9f359e33?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDExfHx8fHx8fDE3NTkwMTkwNTB8&ixlib=rb-4.1.0&q=60&w=3000' UNION ALL
  SELECT 'Thiet bi mang', 'https://images.unsplash.com/photo-1750710583720-8b3bdd0f658a?auto=format&fit=crop&fm=jpg&ixid=M3w3NzE4N3wwfDF8YWxsfDEzfHx8fHx8fDE3NTkwMTkwNTB8&ixlib=rb-4.1.0&q=60&w=3000'
) u ON u.category = c.name;
