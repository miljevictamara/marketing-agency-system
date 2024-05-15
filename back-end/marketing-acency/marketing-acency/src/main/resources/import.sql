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

INSERT INTO public."users" (is_activated, is_blocked, mail, password) VALUES (true, false, 'employee.1@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'employee.2@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'admin.1@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'admin.2@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'client.1@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'client.2@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'admin@gmail.com', '$2a$10$keprWSXdp5Qh0XYrpJyAaeocBVcfHQMzN18dtmPlSmRawjptCFaf.');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'client@gmail.com', '$2a$10$keprWSXdp5Qh0XYrpJyAaeocBVcfHQMzN18dtmPlSmRawjptCFaf.');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'employee@gmail.com', '$2a$10$keprWSXdp5Qh0XYrpJyAaeocBVcfHQMzN18dtmPlSmRawjptCFaf.');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (false, false, 'miljevictamara+123@gmail.com', '$2a$10$keprWSXdp5Qh0XYrpJyAaeocBVcfHQMzN18dtmPlSmRawjptCFaf.');
INSERT INTO public.client(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Futoska 2', 'Novi Sad', 'Company', 'Srbija', 'Sara', 0, 'Saric', '+381', 123, 'LEGAL_ENTITY', 1, 10);


INSERT INTO public.client(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Futoska 11', 'Novi Sad', null, 'Srbija', 'Marija', 1, 'Savic', '+381', null, 'INDIVIDUAL', 1, 1);
INSERT INTO public.client(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Jevrejska 1', 'Novi Sad', null, 'Srbija', 'Vanja', 1, 'Kostic', '+381', null, 'INDIVIDUAL', 1, 2);

INSERT INTO public."user_role"(user_id, role_id) VALUES (1, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (2, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (3, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (4, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (5, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (6, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (7, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (8, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (9, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (10, 3);


INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Bulevar', 'Novi Sad', null, 'Srbija', 'Marko', 1, 'Markovic', '+381', null, 'INDIVIDUAL', 1, 5);
INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Takovska', 'Beograd', null, 'Srbija', 'Nikola', 1, 'Djordjevic', '+381', null, 'INDIVIDUAL', 1, 6);
INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('Cetinjska', 'Beograd', null, 'Srbija', 'Dara', 1, 'Krstic', '+381', null, 'INDIVIDUAL', 3, 8);

INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Zeleznicka', 'Novi Sad', 'Srbija', 'Mina', 'Peric', '+381631234567', 1);
INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Slovacka', 'Beograd', 'Srbija', 'Jelena', 'Jovanovic', '+381637654321', 2);
INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Janka Veselinovica ', 'Nis ', 'Srbija ', 'Zoran ', 'Krstic ', '+381637654321', 9);

INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Futoska 22', 'Lazarevac', 'Srbija', 'Ana', 'Miljevic', '+381632345678', 3);
INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Laze Kostica', 'Beograd', 'Srbija', 'Filip', 'Filipovic', '+381638765432', 4);
INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('Novosadskog sajma', 'Novi Sad', 'Srbija', 'Jovana', 'Savkovic', '+381638765432', 7);


INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-06-01 12:00:00', '2024-07-01 12:00:00', 3, '2024-05-31 12:00:00', 'Moja prva reklama', 30, 'opis', 'Vaša želja, naša misija!', 1);
INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-07-01 12:00:00', '2024-08-01 12:00:00', 1, '2024-06-30 12:00:00', 'Nova reklama', 30, 'opis zahteva', 'Snaga promene', 1);

INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-08-01 12:00:00', '2024-09-01 12:00:00', 1, '2024-07-31 12:00:00', 'opis trece reklame', 30, 'opis prvog zahteva', 'treci slogan', 0);
INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-09-01 12:00:00', '2024-10-01 12:00:00', 1, '2024-08-31 12:00:00', 'opis cetvrte reklame', 30, 'opis drugog zahteva', 'cetvrti slogan', 0);