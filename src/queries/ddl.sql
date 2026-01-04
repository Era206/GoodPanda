-- Restaurant Table
CREATE TABLE restaurant (
    id INT,
    name VARCHAR2(50 CHAR) NOT NULL,
    description VARCHAR2(255 CHAR),
    version NUMBER(19),
    CONSTRAINT pk_restaurant PRIMARY KEY (id)
);

-- User Table
CREATE TABLE user_table (
    id INT,
    name VARCHAR2(50 CHAR) NOT NULL,
    email VARCHAR2(50 CHAR) NOT NULL,
    password VARCHAR2(8 CHAR) NOT NULL,
    address VARCHAR2(50 CHAR),
    nid VARCHAR2(10 CHAR) NOT NULL,
    restaurant_id INT,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_nid UNIQUE (nid),
    CONSTRAINT fk_user_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id)
);

-- Role Table
CREATE TABLE role (
    id INT,
    name VARCHAR2(50 CHAR) NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- User-Role Relationship
CREATE TABLE user_table_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id)
        REFERENCES user_table(id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id)
        REFERENCES role(id)
);

-- Location Table
CREATE TABLE location_tag (
    id INT,
    name VARCHAR2(20 CHAR) NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

--- Rider Table
CREATE TABLE rider (
    id INT,
    user_id INT UNIQUE NOT NULL,
    location_id INT NOT NULL,
    rider_status VARCHAR2(20 CHAR) NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_rider PRIMARY KEY (id),
    CONSTRAINT fk_rider_user FOREIGN KEY (user_id)
        REFERENCES user_table(id) ON DELETE CASCADE,
    CONSTRAINT fk_rider_location FOREIGN KEY (location_id)
        REFERENCES location_tag(id)
);

-- Category Table
CREATE TABLE category_tag (
    id INT,
    name VARCHAR2(20 CHAR) NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

-- Restaurant-Location Relationship
CREATE TABLE restaurant_location (
    restaurant_id INT NOT NULL,
    location_id INT NOT NULL,
    CONSTRAINT pk_restaurant_location PRIMARY KEY (restaurant_id, location_id),
    CONSTRAINT fk_restaurant_location_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id),
    CONSTRAINT fk_restaurant_location_location FOREIGN KEY (location_id)
        REFERENCES location_tag(id)
);

-- Restaurant-Category Relationship
CREATE TABLE restaurant_category (
    restaurant_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT pk_restaurant_category PRIMARY KEY (restaurant_id, category_id),
    CONSTRAINT fk_restaurant_category_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id),
    CONSTRAINT fk_restaurant_category_category FOREIGN KEY (category_id)
        REFERENCES category_tag(id)
);

-- Food Table
CREATE TABLE food (
    id INT,
    name VARCHAR2(50 CHAR) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    restaurant_id INT,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_food PRIMARY KEY (id),
    CONSTRAINT fk_food_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id)
);

-- Restaurant-Food Relationship
CREATE TABLE restaurant_food (
    restaurant_id INT NOT NULL,
    food_id INT NOT NULL,
    CONSTRAINT pk_restaurant_food PRIMARY KEY (restaurant_id, food_id),
    CONSTRAINT fk_restaurant_food_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id),
    CONSTRAINT fk_restaurant_food_food FOREIGN KEY (food_id)
        REFERENCES food(id)
);

-- Order Unit Table
CREATE TABLE order_unit (
    id INT,
    food_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_order_unit PRIMARY KEY (id),
    CONSTRAINT fk_order_unit_food FOREIGN KEY (food_id)
        REFERENCES food(id)
);

-- Order Table
CREATE TABLE order_table (
    id INT,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_date DATE NOT NULL,
    order_placement_time TIMESTAMP,
    order_acceptance_time TIMESTAMP,
    rider_assignment_time TIMESTAMP,
    delivery_time TIMESTAMP,
    rider_id INT,
    delivery_proof VARCHAR2(255 CHAR),
    status VARCHAR2(20 CHAR) NOT NULL,
    version NUMBER(19),
    delivery_address VARCHAR2(255 CHAR),
    delivery_location_id INT,
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_order PRIMARY KEY (id),
    CONSTRAINT fk_order_user FOREIGN KEY (user_id)
        REFERENCES user_table(id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id),
    CONSTRAINT fk_order_rider FOREIGN KEY (rider_id)
        REFERENCES user_table(id),
    CONSTRAINT fk_order_delivery_location FOREIGN KEY (delivery_location_id)
        REFERENCES location_tag(id)
);

-- Payment Table
CREATE TABLE payment (
    id INT,
    order_id INT NOT NULL,
    method VARCHAR2(10 CHAR) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR2(20 CHAR) NOT NULL,
    version NUMBER(19),
    createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastupdateddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_payment PRIMARY KEY (id),
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id)
        REFERENCES order_table(id)
);
