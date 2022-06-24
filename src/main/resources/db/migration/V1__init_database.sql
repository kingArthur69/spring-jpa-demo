drop table if exists customers;
drop table if exists employees;
drop table if exists ingredients;
drop table if exists meals;
drop table if exists orders;
drop table if exists orders_meals;

create table customers (
       id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        address varchar(255),
        phone varchar(255),
        primary key (id)
    ) engine=InnoDB;

create table employees (
       id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        position varchar(255),
        salary double precision,
        primary key (id)
    ) engine=InnoDB;

create table ingredients (
       id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

create table meals (
       id bigint not null auto_increment,
        name varchar(255),
        price double precision,
        primary key (id)
    ) engine=InnoDB;

create table orders (
       id bigint not null auto_increment,
        date datetime(6),
        price double precision,
        customer_id bigint,
        employee_id bigint,
        primary key (id)
    ) engine=InnoDB;

create table orders_meals (
       order_id bigint not null,
        meals_id bigint not null
    ) engine=InnoDB;

alter table orders_meals 
       add constraint UK_ax799fa3rrnxj8w2rgipxxw0h unique (meals_id);

alter table orders 
       add constraint FKpxtb8awmi0dk6smoh2vp1litg 
       foreign key (customer_id) 
       references customers (id);

alter table orders 
       add constraint FKfhl8bv0xn3sj33q2f3scf1bq6 
       foreign key (employee_id) 
       references employees (id);

alter table orders_meals 
       add constraint FKetdaqpg0mk119sm5cf1t3xm5g 
       foreign key (meals_id) 
       references meals (id);

alter table orders_meals 
       add constraint FK23en38xrrkiiv78l1tlx7fu1l 
       foreign key (order_id) 
       references orders (id);

