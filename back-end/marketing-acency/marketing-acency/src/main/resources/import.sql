INSERT INTO public.role( id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role( id, name) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO public.role( id, name) VALUES (3, 'ROLE_CLIENT');

INSERT INTO public.users(id, is_activated, is_blocked, mail, password) VALUES (1, true, false, 'employee.1@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public.users(id, is_activated, is_blocked, mail, password) VALUES (2, true, false, 'employee.2@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');

INSERT INTO public."package"(id, name, price, visits_number) VALUES (1, 'GOLD', 300.00, 10000);

INSERT INTO public."employee"(id, address, city, country, first_name, last_name, phone_number, user_id) VALUES (1, 'adresa prvog zaposlenog', 'grad prvog zaposlenog', 'drzava prvog zaposlenog', 'ime prvog zaposlenog', 'prezime prvog zaposlenog', '+381631234567', 1);
INSERT INTO public."employee"(id, address, city, country, first_name, last_name, phone_number, user_id) VALUES (2, 'adresa drugog zaposlenog', 'grad drugog zaposlenog', 'drzava drugog zaposlenog', 'ime drugog zaposlenog', 'prezime drugog zaposlenog', '+381637654321', 2);

INSERT INTO public."advertisement"(id, active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES (1, '2024-06-01 12:00:00', '2024-07-01 12:00:00', 1, '2024-05-31 12:00:00', 'opis prve reklame', 30, 'opis zahteva', 'prvi slogan', 1);
INSERT INTO public."advertisement"(id, active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES (2, '2024-07-01 12:00:00', '2024-08-01 12:00:00', 1, '2024-06-30 12:00:00', 'opis druge reklame', 30, 'opis zahteva', 'drugi slogan', 1);

INSERT INTO public."advertisement"(id, active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES (3, '2024-08-01 12:00:00', '2024-09-01 12:00:00', 1, '2024-07-31 12:00:00', 'opis trece reklame', 30, 'opis prvog zahteva', 'treci slogan', 0);
INSERT INTO public."advertisement"(id, active_from, active_to, client_id, deadline, description, duration, request_description, slogan, status) VALUES (4, '2024-09-01 12:00:00', '2024-10-01 12:00:00', 1, '2024-08-31 12:00:00', 'opis cetvrte reklame', 30, 'opis drugog zahteva', 'cetvrti slogan', 0);

