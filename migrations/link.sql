--liquibase formatted sql
--changeset author:LucidMinded

create table link
(
    id            bigint generated always as identity primary key,
    url           text                     not null unique,
    updated_at    timestamp with time zone not null,
    last_activity timestamp with time zone not null
);

create index link_url_index on link using hash (url);
