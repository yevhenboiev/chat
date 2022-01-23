insert into client (id, login, password, role, is_active, is_block, start_ban, end_ban)
values (1, 'Bot', 'Bot', null, true, false, null, null);

insert into client (id, login, password, role, is_active, is_block, start_ban, end_ban)
values (2, 'Admin', '$2a$12$jQADut2UBqFEZqrhgjajpehc8xeXdSBGhyd3G0ju5.1xFvYu1kX5m', 'ADMIN', true, false, null, null);

insert into room (id, room_name, is_private, creator_id)
values (1, 'Chat Bot by Admin', true, 1);

insert into client_rooms (client_id, room_id)
values (2, 1);