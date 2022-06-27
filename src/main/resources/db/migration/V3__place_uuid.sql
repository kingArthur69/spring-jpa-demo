drop table if exists places;

create table places (
       id binary(16) not null,
        name varchar(255),
        address varchar(255),
        primary key (id)
    ) engine=InnoDB;