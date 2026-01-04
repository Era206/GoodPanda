----user table
INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Abdur Rahman', 'abdur123@gmail.com', 'abdur123', 'Basabo', 1234123412, 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Mintu Shekh', 'mintu213@gmail.com', 'mintu123', 'Demra', 1614232345, 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Tanjeem Azwad Zaman', 'tanjeem29@gmail.com', 'taz123', 'Bailey Road', 0424052054, 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Tofa Islam', 'tofa421@gmail.com', 'tofa789', 'Rampura', '3456123412', 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Sanjida Islam Era', 'era216@gmail.com', 'eraera12', 'Rampura', '1212123412', 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Ishika Ime', 'ime213@gmail.com', 'imeim123', 'Taltola', '1613232345', 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Sayeed Bati', 'bati420@gmail.com', 'batibati', 'Dhanmondi', '0404052054', 1);

INSERT INTO user_table (id, name, email, password, address, nid, version)
VALUES (user_id_seq.nextval, 'Sofa Islam', 'sofa421@gmail.com', 'sofa7389', 'Rampura', '3356123412', 1);

--turncate password and nid length
UPDATE user_table
SET password = SUBSTR(password, 1, 8),
    nid = SUBSTR(nid, 1, 10);

---padding to make the correct length
UPDATE user_table
SET password = RPAD(password, 8, '0');

---location data
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Rampura', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Banasree', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Banani', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Khilgaon', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Dhanmondi', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Utara', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Goran', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Azimpur', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Shahbag', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Gulshan', 1);
INSERT INTO location_tag (id, name, version) VALUES (location_id_seq.nextval, 'Badda', 1);

---category data
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Asian', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Bakery', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Bangladeshi', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Baverage', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Biryani', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Breakfast', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Burgers', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Cakes', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Chicken', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Chinese', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Curry', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Dessert', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Fast Food', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Fish', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Indian', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Italian', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Kebab', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Meat', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Pizza', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Seafood', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Snacks', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Sweets', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Turkish', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Rice Dishes', 1);
insert into category_tag(id, name, version) values (CATEGORY_ID_SEQ.nextval, 'Western', 1);

---ORDER TABLE
INSERT INTO order_table (
    id, user_id, restaurant_id, order_date, order_placement_time, order_acceptance_time,
    rider_assignment_time, delivery_time, rider_id, delivery_proof, status, version
)
VALUES (
    ORDER_ID_SEQ.nextval,
    5, -- User ID
    2, -- Restaurant ID
    TO_DATE('2024-12-01', 'YYYY-MM-DD'), -- Order Date
    TO_TIMESTAMP('2024-12-01 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), -- Order Placement Time
    TO_TIMESTAMP('2024-12-01 14:40:00', 'YYYY-MM-DD HH24:MI:SS'), -- Order Acceptance Time
    TO_TIMESTAMP('2024-12-01 14:50:00', 'YYYY-MM-DD HH24:MI:SS'), -- Rider Assignment Time
    TO_TIMESTAMP('2024-12-01 15:20:00', 'YYYY-MM-DD HH24:MI:SS'), -- Delivery Time
    2, -- Rider ID
    'Delivered to door with signature', -- Delivery Proof
    'DELIVERED', -- Status
    1 -- Version
);

INSERT INTO order_table (
    id, user_id, restaurant_id, order_date, order_placement_time, order_acceptance_time,
    rider_assignment_time, delivery_time, rider_id, delivery_proof, status, version
)
VALUES (
    ORDER_ID_SEQ.nextval,
    8,
    2,
    TO_DATE('2024-12-02', 'YYYY-MM-DD'),
    TO_TIMESTAMP('2024-12-02 18:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2024-12-02 18:15:00', 'YYYY-MM-DD HH24:MI:SS'),
    TO_TIMESTAMP('2024-12-02 18:25:00', 'YYYY-MM-DD HH24:MI:SS'),
    NULL, -- Delivery time
    2,
    NULL, -- Delivery Proof (not delivered yet)
    'IN_PROGRESS',
    1
);

-----rider
INSERT INTO rider (id, user_id, location_id, rider_status, version)
VALUES (rider_id_seq.NEXTVAL, 8, 1, 'FREE', 1); -- Sofa Islam, Rampura

INSERT INTO rider (id, user_id, location_id, rider_status, version)
VALUES (rider_id_seq.NEXTVAL, 9, 1, 'FREE', 1); -- ERA, Rampura

----ROLE
INSERT INTO role (id, name, version, createdTime, lastUpdatedTime) VALUES(ROLE_ID_SEQ.nextval, 'RESTAURANT_ADMIN',1 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO role (id, name, version, createdTime, lastUpdatedTime) VALUES(ROLE_ID_SEQ.nextval, 'RIDER',1 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO role (id, name, version, createdTime, lastUpdatedTime) VALUES(ROLE_ID_SEQ.nextval, 'USER',1 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO role (id, name, version, createdTime, lastUpdatedTime) VALUES(ROLE_ID_SEQ.nextval, 'ADMIN',1 , CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

----user table role
INSERT INTO user_table_role (user_id, role_id)
SELECT u.id AS user_id, r.id AS role_id
FROM user_table u
JOIN role r ON r.name = 'RESTAURANT_ADMIN'
WHERE u.restaurant_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1
      FROM user_table_role ur
      WHERE ur.user_id = u.id
        AND ur.role_id = r.id
  );

INSERT INTO user_table_role (user_id, role_id)
SELECT u.id AS user_id, r.id AS role_id
FROM user_table u
JOIN rider rd ON rd.user_id = u.id
JOIN role r ON r.name = 'RIDER'
WHERE NOT EXISTS (
    SELECT 1
    FROM user_table_role ur
    WHERE ur.user_id = u.id
      AND ur.role_id = r.id
);
