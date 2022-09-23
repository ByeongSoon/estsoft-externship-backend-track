INSERT INTO delivery(delivery_id, delivery_fee, delivery_name, member_id, created_by, create_time, modified_by , update_time)
VALUES(99999, 3000, '마포구 물류센터', 1, 'test@test.com', now(), 'test@test.com', now())
;
INSERT INTO delivery(delivery_id, delivery_fee, delivery_name, member_id, created_by, create_time, modified_by , update_time)
VALUES(100000, 0, '마포구 물류센터 무료배송', 1, 'test@test.com', now(), 'test@test.com', now())
;
INSERT INTO delivery(delivery_id, delivery_fee, delivery_name, member_id, created_by, create_time, modified_by , update_time)
VALUES(99998, 3000, '강남구 물류센터', 2, 'test@test.com', now(), 'test@test.com', now())
;
INSERT INTO delivery(delivery_id, delivery_fee, delivery_name, member_id, created_by, create_time, modified_by , update_time)
VALUES(99997, 0, '강남구 물류센터 무료배송', 2, 'test@test.com', now(), 'test@test.com', now())
;
COMMIT;