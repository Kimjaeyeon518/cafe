drop table if exists order_line cascade;
drop table if exists orders cascade;
drop table if exists product cascade;
drop table if exists `users` cascade;

create table `order_line` (
        amount bigint,
        order_id bigint not null,
        order_line_id bigint not null auto_increment,
        product_id bigint not null,
        primary key (order_line_id)
);

create table `orders` (
                        created_at timestamp(6) not null,
                        order_id bigint not null auto_increment,
                        total_price bigint,
                        updated_at timestamp(6) not null,
                        primary key (order_id)
);
    
create table `product` (
                         amount bigint,
                         price bigint,
                         product_id bigint not null auto_increment,
                         sale_count bigint,
                         category varchar(255) check (category in ('CAFFEINE','DECAFFEINE')),
                         description varchar(255),
                         name varchar(255),
                         primary key (product_id)
);
    
create table `users` (
                       point bigint,
                       user_id bigint not null auto_increment,
                       name varchar(255),
                       password varchar(255),
                       role varchar(255) check (role in ('ROLE_USER','ROLE_ADMIN')),
                       username varchar(255) unique,
                       primary key (user_id)
);
