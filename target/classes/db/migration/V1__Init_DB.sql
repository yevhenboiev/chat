create sequence client_seq start 3 increment 1;

create sequence hibernate_sequence start 1 increment 1;

create sequence room_seq start 2 increment 1;

create table client
(
    id        int8 not null,
    login     varchar(255)          not null,
    password  varchar(255)          not null,
    role      varchar(32),
    is_active boolean default true  not null,
    is_block  boolean default false not null,
    start_ban timestamp,
    end_ban   timestamp,
    primary key (id)
);

create table client_rooms
(
    client_id int8 not null,
    room_id   int8 not null,
    primary key (client_id, room_id)
);

create table message
(
    id            int8 not null,
    creator_id     int8        not null,
    room_id       int8,
    creation_time timestamp,
    content       varchar(1024) not null,
    primary key (id)
);

create table room
(
    id         int8 not null,
    room_name  varchar(255) not null,
    is_private boolean      not null,
    creator_id  int8       not null,
    primary key (id)
);

alter table client
    add constraint client_login_unique
    unique (login);

alter table room
    add constraint room_name_unique
    unique (room_name);

alter table client_rooms
    add constraint client_rooms_room_fk
    foreign key (room_id) references room;

alter table client_rooms
    add constraint client_rooms_client_fk
    foreign key (client_id) references client;

alter table message
    add constraint message_client_fk
    foreign key (creator_id) references client;

alter table message
    add constraint message_room_fk
    foreign key (room_id) references room;

alter table room
    add constraint room_client_fk
    foreign key (creator_id) references client;