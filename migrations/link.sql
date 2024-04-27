--liquibase formatted sql
--changeset author:LucidMinded

create table link
(
    id         bigint generated always as identity primary key,
    url        text      not null unique,
    updated_at timestamp not null
);
