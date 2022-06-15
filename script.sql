create table orders (
    id serial not null primary key,
    user_id integer not null,
    total_value integer not null,
    products_description varchar(250) not null,
    created_at timestamp not null,
    status varchar(12) not null
);