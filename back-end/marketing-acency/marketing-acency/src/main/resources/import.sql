INSERT INTO public.role( id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role( id, name) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO public.role( id, name) VALUES (3, 'ROLE_CLIENT');

--permisije za admina
INSERT INTO permission(id, name) VALUES (1, 'GET_ALL_ROLES');
INSERT INTO permission(id, name) VALUES (2, 'GET_ALL_PERMISSIONS');
INSERT INTO permission(id, name) VALUES (3, 'UPDATE_ROLE_PERMISSION');
INSERT INTO permission(id, name) VALUES (4, 'GET_PERMISSIONS_FOR_ROLE');
INSERT INTO permission(id, name) VALUES (5, 'GET_BYUSERID');
INSERT INTO permission(id, name) VALUES (25, 'VIEW_ALL_INDIVIDUALS');
INSERT INTO permission(id, name) VALUES (26, 'VIEW_ALL_LEGAL_ENTITIES');
INSERT INTO permission(id, name) VALUES (6, 'UPDATE_ADMIN');
INSERT INTO permission(id, name) VALUES (7, 'CREATE_ADMIN');
INSERT INTO permission(id, name) VALUES (8, 'GET_ALL_EMPLOYEES');
INSERT INTO permission(id, name) VALUES (9, 'CREATE_EMPLOYEE');
INSERT INTO permission(id, name) VALUES (10, 'SAVE_EMPLOYEE_USER');
INSERT INTO permission(id, name) VALUES (11, 'SAVE_ADMIN_USER');
INSERT INTO permission(id, name) VALUES (12, 'GET_ALL_CLIENTS');
INSERT INTO permission(id, name) VALUES (23, 'APROVE_REGISTRATION_REQUEST');
INSERT INTO permission(id, name) VALUES (24, 'REJECT_REGISTRATION_REQUEST');
INSERT INTO permission(id, name) VALUES (30, 'LOG_MESSAGES');
INSERT INTO permission(id, name) VALUES (31, 'DELETE_USER');
INSERT INTO permission(id, name) VALUES (32, 'BLOCK_USER');



INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 1);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 2);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 3);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 4);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 5);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 6);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 7);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 8);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 9);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 10);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 11);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 12);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 23);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 24);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 25);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 26);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 30);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 31);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 32);



--permisije za klijenta i employee
INSERT INTO permission(id, name) VALUES (13, 'GET_PENDING_ADVERTISMENTS');
INSERT INTO permission(id, name) VALUES (14, 'GET_ACCEPTED_ADVERTISMENTS');
INSERT INTO permission(id, name) VALUES (15, 'UPDATE_ADVERTISMENT');
INSERT INTO permission(id, name) VALUES (16, 'GET__ADVERTISMENTS_BY_CLIENT');
INSERT INTO permission(id, name) VALUES (17, 'CREATE_ADVERTISMENT');
INSERT INTO permission(id, name) VALUES (18, 'UPDATE_CLIENT');
INSERT INTO permission(id, name) VALUES (19, 'UPDATE_EMPLOYEE');
INSERT INTO permission(id, name) VALUES (20, 'GET_CLIENT_BYUSERID');
INSERT INTO permission(id, name) VALUES (21, 'GET_EMPLOYEE_BYUSERID');
INSERT INTO permission(id, name) VALUES (22, 'GET_REQUEST_ID');

INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 13);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 14);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 15);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 15);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 16);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 17);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 18);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 19);
INSERT INTO roles_perms(role_id, permission_id) VALUES (3, 20);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 21);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 22);


INSERT INTO public."package"(id, name, price, visits_number) VALUES (1, 'GOLD', 300.00, 10000);
INSERT INTO public."package"(id, name, price, visits_number) VALUES (2, 'STANDARD', 100.00, 100);
INSERT INTO public."package"(id, name, price, visits_number) VALUES (3, 'BASIC', 50.00, 10);

INSERT INTO public."users" (is_activated, is_blocked, mail, password,mfa) VALUES (true, false, 'djordjevic.ra37.2020@uns.ac.rs', '$2a$10$PXKK744BnNriXiJB/9Y3h.AtYpMcGDo6fWls/mw.JtwVHs0/jS4vy', false);

INSERT INTO public."user_role"(user_id, role_id) VALUES (1, 1);
INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('EmI0VX/zjgRvhHaX8Ids8W2E/lw+gqNjntjlGmDdSq0kEacr99JTVpB4IqgZTGIG', 'Novi Sad', 'Srbija', 'Ana', 'Miljevic', 'mtAcjgCaLYaiW/flDAyyuz+B9C2Y70HyPGAL5+9rFTo=', 1);
