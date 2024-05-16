create table bill
(
    billId          int auto_increment
        primary key,
    orderId         int            null,
    clientId        int            null,
    productId       int            null,
    productQuantity int            null,
    totalPrice      decimal(10, 2) not null
);

create table client
(
    clientId int auto_increment
        primary key,
    name     varchar(50)  not null,
    email    varchar(100) not null,
    constraint email
        unique (email)
);

create table product
(
    productId       int auto_increment
        primary key,
    productName     varchar(100)   not null,
    price           decimal(10, 2) not null,
    productQuantity int            null
);

create table orders
(
    orderId   int auto_increment
        primary key,
    clientId  int null,
    productId int null,
    quantity  int not null,
    constraint orders_ibfk_1
        foreign key (clientId) references client (clientId),
    constraint orders_ibfk_2
        foreign key (productId) references product (productId)
);

create index clientId
    on orders (clientId);

create index productId
    on orders (productId);

create table staff
(
    id         int auto_increment
        primary key,
    staffName  varchar(100) not null,
    staffEmail varchar(100) not null,
    constraint staffEmail
        unique (staffEmail)
);


