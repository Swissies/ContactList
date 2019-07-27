delete from contact_emails;
delete from contact_numbers;
delete from contact;

INSERT INTO contact (id, name, user_id) values (1, 'Jhone', 1);
INSERT INTO contact (id, name, user_id) values (2, 'Dave', 1);
INSERT INTO contact (id, name, user_id) values (3, 'Danil', 2);
INSERT INTO contact (id, name, user_id) values (4, 'Nikolai', 2);

INSERT INTO contact_emails (id, contact_id, email) values (1, 1, 'jhoneEmail1@gmail.com');
INSERT INTO contact_emails (id, contact_id, email) values (2, 1, 'jhoneEmail2@gmail.com');
INSERT INTO contact_emails (id, contact_id, email) values (3, 2, 'DaveEmail1@gmail.com');
INSERT INTO contact_emails (id, contact_id, email) values (4, 3, 'DanilEmail1@gmail.com');
INSERT INTO contact_emails (id, contact_id, email) values (5, 4, 'NikolaiEmail1@gmail.com');
INSERT INTO contact_emails (id, contact_id, email) values (6, 4, 'NikolaiEmail2@gmail.com');

INSERT INTO contact_numbers (id, contact_id, number) values (1, 1, '380683423014');
INSERT INTO contact_numbers (id, contact_id, number) values (2, 1, '380437823764');
INSERT INTO contact_numbers (id, contact_id, number) values (3, 2, '380472834987');
INSERT INTO contact_numbers (id, contact_id, number) values (4, 2, '380467823746');
INSERT INTO contact_numbers (id, contact_id, number) values (5, 3, '380234657832');
INSERT INTO contact_numbers (id, contact_id, number) values (6, 4, '380532467382');

alter sequence hibernate_sequence restart with 7;