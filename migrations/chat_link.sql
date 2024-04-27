--liquibase formatted sql
--changeset author:LucidMinded

create table chat_link
(
    chat_id BIGINT references chat (id) not null,
    link_id BIGINT references link (id) not null,
    primary key (chat_id, link_id)
);

create index chat_link_link_id_index on chat_link (link_id);
