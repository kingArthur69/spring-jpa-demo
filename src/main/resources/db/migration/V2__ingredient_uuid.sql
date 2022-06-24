drop table if exists ingredients;

create table ingredients (
       id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;