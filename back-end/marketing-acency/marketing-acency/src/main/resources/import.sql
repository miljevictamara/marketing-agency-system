INSERT INTO public.role( id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role( id, name) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO public.role( id, name) VALUES (3, 'ROLE_CLIENT');

--permisije za admina
INSERT INTO permission(id, name) VALUES (1, 'GET_ALL_ROLES');
INSERT INTO permission(id, name) VALUES (2, 'GET_ALL_PERMISSIONS');
INSERT INTO permission(id, name) VALUES (3, 'UPDATE_ROLE_PERMISSION');
INSERT INTO permission(id, name) VALUES (4, 'GET_PERMISSIONS_FOR_ROLE');
INSERT INTO permission(id, name) VALUES (5, 'GET_BYUSERID');
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


INSERT INTO public."user_role"(user_id, role_id) VALUES (1, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (2, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (3, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (4, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (5, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (6, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (7, 1);
INSERT INTO public."user_role"(user_id, role_id) VALUES (8, 3);
INSERT INTO public."user_role"(user_id, role_id) VALUES (9, 2);

INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('adresa prvog klijenta', 'grad prvog klijenta', 'kompanija prvog klijenta', 'drzava prvog klijenta', 'ime prvog klijenta', 1, 'prezime prvog klijenta', '+381', '123', 'INDIVIDUAL', 1, 5);
INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('adresa drugog klijenta', 'grad drugog klijenta', 'kompanija drugog klijenta', 'drzava drugog klijenta', 'ime drugog klijenta', 1, 'prezime drugog klijenta', '+381', '123', 'INDIVIDUAL', 1, 6);
INSERT INTO public."client"(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('adresa  klijenta', 'grad  klijenta', 'kompanija  klijenta', 'drzava  klijenta', 'ime  klijenta', 1, 'prezime  klijenta', '+381', '123', 'INDIVIDUAL', 1, 8);

INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa prvog zaposlenog', 'grad prvog zaposlenog', 'drzava prvog zaposlenog', 'ime prvog zaposlenog', 'prezime prvog zaposlenog', '+381631234567', 1);
INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa drugog zaposlenog', 'grad drugog zaposlenog', 'drzava drugog zaposlenog', 'ime drugog zaposlenog', 'prezime drugog zaposlenog', '+381637654321', 2);
INSERT INTO public."employee"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa ', 'grad ', 'drzava ', 'ime ', 'prezime ', '+381637654321', 9);

INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa prvog admina', 'grad prvog admina', 'drzava prvog admina', 'ime prvog admina', 'prezime prvog admina', '+381632345678', 3);
INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa drugog admina', 'grad drugog admina', 'drzava drugog admina', 'ime drugog admina', 'prezime drugog admina', '+381638765432', 4);
INSERT INTO public."administrator"(address, city, country, first_name, last_name, phone_number, user_id) VALUES ('adresa nina admina', 'grad nina admina', 'drzava drugog admina', 'ime drugog admina', 'prezime drugog admina', '+381638765432', 7);


INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-06-01 12:00:00', '2024-07-01 12:00:00', 3, '2024-05-31 12:00:00', 'opis prve reklame', 30, 'opis zahteva', 'prvi slogan', 1);
INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-07-01 12:00:00', '2024-08-01 12:00:00', 1, '2024-06-30 12:00:00', 'opis druge reklame', 30, 'opis zahteva', 'drugi slogan', 1);

INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-08-01 12:00:00', '2024-09-01 12:00:00', 1, '2024-07-31 12:00:00', 'opis trece reklame', 30, 'opis prvog zahteva', 'treci slogan', 0);
INSERT INTO public."advertisement"(active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES ('2024-09-01 12:00:00', '2024-10-01 12:00:00', 1, '2024-08-31 12:00:00', 'opis cetvrte reklame', 30, 'opis drugog zahteva', 'cetvrti slogan', 0);