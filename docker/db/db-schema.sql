create table public.location
(
    location_id   varchar(255) not null
        primary key,
    location_name varchar(255) not null
        unique
);

alter table public.location
    owner to quickbill;

create table public.provider
(
    provider_id   varchar(255) not null
        primary key,
    provider_name varchar(255) not null
        unique
);

alter table public.provider
    owner to quickbill;

create table public.ticket_type
(
    ticket_type_expiry_number integer,
    ticket_type_expiry_unit   varchar(255),
    ticket_type_id            varchar(255) not null
        primary key,
    ticket_type_name          varchar(255) not null
        unique
);

alter table public.ticket_type
    owner to quickbill;

create table public.transport_type
(
    transport_type_id   varchar(255) not null
        primary key,
    transport_type_name varchar(255) not null
        unique
);

alter table public.transport_type
    owner to quickbill;

create table public.ticket_to_buy
(
    location_id       varchar(255) not null
        constraint fkc8w7bxupg9rmqibek0wjonjes
            references public.location,
    provider_id       varchar(255) not null
        constraint fkrcqycklr7bj3f5t6rr20pji9e
            references public.provider,
    ticket_to_buy_id  varchar(255) not null
        primary key,
    ticket_type_id    varchar(255) not null
        constraint fk38j514ih1liw0hu265vehai6c
            references public.ticket_type,
    transport_type_id varchar(255) not null
        constraint fkol77rlywpkbu9a1sfvxbf7fmx
            references public.transport_type,
    constraint ticket_to_buy_provider_id_location_id_transport_type_id_tic_key
        unique (provider_id, location_id, transport_type_id, ticket_type_id)
);

alter table public.ticket_to_buy
    owner to quickbill;

create table public.users
(
    role          varchar(255),
    user_email    varchar(255) not null
        primary key,
    user_name     varchar(255),
    user_password varchar(255)
);

alter table public.users
    owner to quickbill;

