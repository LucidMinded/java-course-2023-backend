--liquibase formatted sql
--changeset author:LucidMinded

create table chat_link
(
    chat_id BIGINT references chat (id),
    link_id BIGINT references link (id)
);
