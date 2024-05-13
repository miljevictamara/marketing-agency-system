INSERT INTO public.role( id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role( id, name) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO public.role( id, name) VALUES (3, 'ROLE_CLIENT');

INSERT INTO public."package"(id, name, price, visits_number) VALUES (1, 'GOLD', 300.00, 10000);
INSERT INTO public."package"(id, name, price, visits_number) VALUES (2, 'STANDARD', 100.00, 100);
INSERT INTO public."package"(id, name, price, visits_number) VALUES (3, 'BASIC', 50.00, 10);

INSERT INTO public."users" (is_activated, is_blocked, mail, password) VALUES (true, false, 'andjela1108@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');
INSERT INTO public."users"(is_activated, is_blocked, mail, password) VALUES (true, false, 'miljevictamara@gmail.com', '$2a$10$lhYgvOwC1Q.fxzQBkwVNI.xqwKaoQiY6Gum5fzeN9jsuYStzORNGi');

INSERT INTO public."user_role"(user_id, role_id) VALUES (1, 2);
INSERT INTO public."user_role"(user_id, role_id) VALUES (2, 2);

INSERT INTO public.client(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('adresa prvog klijenta', 'grad prvog klijenta', 'kompanija prvog klijenta', 'drzava prvog klijenta', 'ime prvog klijenta', 0, 'prezime prvog klijenta', '+381', '123', 'INDIVIDUAL', 1, 1);
INSERT INTO public.client(address, city, company_name, country, first_name, is_approved, last_name, phone_number, pib, role, package_id, user_id) VALUES ('adresa drugog klijenta', 'grad drugog klijenta', 'kompanija drugog klijenta', 'drzava drugog klijenta', 'ime drugog klijenta', 0, 'prezime drugog klijenta', '+381', '123', 'INDIVIDUAL', 1, 2);

