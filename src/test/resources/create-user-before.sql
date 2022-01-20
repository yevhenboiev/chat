delete from CLIENT_ROOMS;
delete from ROOM;
delete from MESSAGE;
delete from CLIENT;

insert into CLIENT(end_ban, is_active, is_block, login, password, role, start_ban)
values (null, true, false, 'Bot', 'passwordForBot', 'USER', null);

insert into CLIENT(end_ban, is_active, is_block, login, password, role, start_ban)
values (null, true, false, 'Admin', '$2a$12$EMJ4o4fBJOBGYyR9E6BxV.MG24f3IQB340z/ng38Ul23cCusS4iMi', 'ADMIN', null);

insert into CLIENT(end_ban, is_active, is_block, login, password, role, start_ban)
values (null, true, false, 'Moderator', '$2a$12$EMJ4o4fBJOBGYyR9E6BxV.MG24f3IQB340z/ng38Ul23cCusS4iMi', 'MODERATOR', null);

insert into CLIENT(end_ban, is_active, is_block, login, password, role, start_ban)
values (null, true, false, 'User_1', '$2a$12$EMJ4o4fBJOBGYyR9E6BxV.MG24f3IQB340z/ng38Ul23cCusS4iMi', 'USER', null);

insert into CLIENT(end_ban, is_active, is_block, login, password, role, start_ban)
values (null, true, false, 'User_2', '$2a$12$EMJ4o4fBJOBGYyR9E6BxV.MG24f3IQB340z/ng38Ul23cCusS4iMi', 'USER', null);