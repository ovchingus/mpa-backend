start transaction;

INSERT INTO disease (id, name) VALUES (1, 'Аритмия');

insert into attributes(id, name, type) values (1, 'VA registered', 'bool');
insert into attributes(id, name, type) values (2, 'CAD', 'bool');
insert into attributes(id, name, type) values (3, 'VA', 'bool');
insert into attributes(id, name, type) values (4, 'LVEF', 'double');
insert into attributes(id, name, type) values (5, 'ECG exercise stress testing: TT, Bicycle testing', 'enum');
insert into attributes(id, name, type) values (6, 'Exercise stress test with Ng. exercise stress ECG test', 'enum');
insert into attributes(id, name, type) values (7, 'Coronary Angiography', 'enum');
insert into attributes(id, name, type) values (8, 'Nonischemic VA', 'bool');
insert into attributes(id, name, type) values (9, 'Ischemic VA', 'bool');
insert into attributes(id, name, type) values (10, 'MR', 'enum');
insert into attributes(id, name, type) values (11, 'AAE', 'enum');
insert into attributes(id, name, type) values (12, 'MR Repeat', 'enum');
insert into attributes(id, name, type) values (13, 'Psychodiagnostics', 'enum');
insert into attributes(id, name, type) values (14, 'AD', 'enum');
insert into attributes(id, name, type) values (15, 'AAE', 'enum');

insert into attribute_values(id, attribute_id, value) values (1, 5, 'positive');
insert into attribute_values(id, attribute_id, value) values (2, 5, 'negative');
insert into attribute_values(id, attribute_id, value) values (3, 5, 'uncertain');
insert into attribute_values(id, attribute_id, value) values (4, 6, 'positive');
insert into attribute_values(id, attribute_id, value) values (5, 6, 'negative');
insert into attribute_values(id, attribute_id, value) values (6, 7, 'positive');
insert into attribute_values(id, attribute_id, value) values (7, 7, 'negative');
insert into attribute_values(id, attribute_id, value) values (8, 10, 'positive');
insert into attribute_values(id, attribute_id, value) values (9, 10, 'negative');
insert into attribute_values(id, attribute_id, value) values (10, 11, 'positive');
insert into attribute_values(id, attribute_id, value) values (11, 11, 'negative');
insert into attribute_values(id, attribute_id, value) values (12, 12, 'indicated');
insert into attribute_values(id, attribute_id, value) values (13, 12, 'not indicated');
insert into attribute_values(id, attribute_id, value) values (14, 13, 'indicated');
insert into attribute_values(id, attribute_id, value) values (15, 13, 'not indicated');
insert into attribute_values(id, attribute_id, value) values (16, 14, 'positive');
insert into attribute_values(id, attribute_id, value) values (17, 14, 'negative');
insert into attribute_values(id, attribute_id, value) values (18, 15, 'positive');
insert into attribute_values(id, attribute_id, value) values (19, 15, 'negative');

insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (1, 1, 1, false, 1);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (2, 1, 1, false, 2);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (3, 1, 1, false, 3);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (4, 1, 1, false, 4);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (5, 1, 1, false, 5);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (6, 1, 1, false, 6);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (7, 1, 1, false, 7);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (8, 1, 1, false, 8);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (9, 1, 1, false, 9);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (10, 1, 1, false, 10);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (11, 1, 1, false, 11);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (12, 1, 1, false, 12);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (13, 1, 1, false, 13);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (14, 1, 1, false, 14);
insert into disease_attributes(id, requirement_id, requirement_type_id, is_required, attribute_id) values (15, 1, 1, false, 15);

insert into state(id, name, description, disease_id) values (1, 'HSt 1', 'VA Not registered', 1);
insert into state(id, name, description, disease_id) values (2, 'HSt 2', 'Algorithm for the management of patients with VA', 1);
insert into state(id, name, description, disease_id) values (3, 'HSt 3', 'Medical therapy', 1);
insert into state(id, name, description, disease_id) values (4, 'HSt 4', 'Optimal Medical Therapy CAD, AAT, CA, ICD', 1);
insert into state(id, name, description, disease_id) values (5, 'HSt 5', 'Beta-blockers', 1);
insert into state(id, name, description, disease_id) values (6, 'HSt 6', 'Амбулаторное лечение', 1);

insert into state(id, name, description, disease_id) values (8, 'HS1', 'ECG, ECG Monitoring', 1);
insert into state(id, name, description, disease_id) values (9, 'HS2', 'Search for Trigger VA', 1);
insert into state(id, name, description, disease_id) values (10, 'HS3', 'ECG exercise stress testing: TT, Bicycle testing', 1);
insert into state(id, name, description, disease_id) values (11, 'HS3.1', 'Exercise stress test with Ng. exercise stress ECG test', 1);
insert into state(id, name, description, disease_id) values (12, 'HS4', 'Coronary angiography', 1);
insert into state(id, name, description, disease_id) values (13, 'HS5', 'Определение показаний к MR', 1);
insert into state(id, name, description, disease_id) values (14, 'HS6', 'MR done; Recover after MR (6-9 months)', 1);
insert into state(id, name, description, disease_id) values (15, 'HS7', 'MR efficiency evaluation', 1);
insert into state(id, name, description, disease_id) values (16, 'HS8', 'Exercise stress ECG test, PET/SPECT, NIFCGM; MR Repeat decision', 1);
insert into state(id, name, description, disease_id) values (17, 'HS9', 'Psychodiagnostics requirement evaluation', 1);
insert into state(id, name, description, disease_id) values (18, 'HS10', 'Psychodiagnostics', 1);
insert into state(id, name, description, disease_id) values (19, 'HS11', 'Psychotherapy, psychotropic drugs', 1);

insert into transition(id, state_from_id, state_to_id, predicate) values (1, 8, 9, 'eq ({status.1}, true)');
insert into transition(id, state_from_id, state_to_id, predicate) values (2, 9, 10, 'and (eq ({status.2}, true), and (eq ({status.3}, true), gte ({status.4}, 0.4)))');
insert into transition(id, state_from_id, state_to_id, predicate) values (3, 10, 12, 'eq ({status.5}, 1)');
insert into transition(id, state_from_id, state_to_id, predicate) values (4, 10, 11, 'eq ({status.5}, 3)');
insert into transition(id, state_from_id, state_to_id, predicate) values (5, 11, 12, 'eq ({status.6}, 4)');
insert into transition(id, state_from_id, state_to_id, predicate) values (6, 12, 13, 'eq ({status.7}, 6)');
insert into transition(id, state_from_id, state_to_id, predicate) values (7, 12, 17, 'and (eq ({status.7}, 7), eq ({status.8}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (8, 12, 17, 'and (eq ({status.7}, 7), eq ({status.9}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (10, 13, 14, 'eq ({status.10}, 8)');
insert into transition(id, state_from_id, state_to_id, predicate) values (11, 13, 17, 'and (eq ({status.10}, 9), eq ({status.8}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (12, 13, 17, 'and (eq ({status.10}, 9), eq ({status.9}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (13, 15, 3, 'eq ({status.11}, 10)');
insert into transition(id, state_from_id, state_to_id, predicate) values (14, 15, 16, 'and (eq ({status.11}, 11), eq ({status.9}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (15, 15, 17, 'and (eq ({status.11}, 11), eq ({status.8}, true))');
insert into transition(id, state_from_id, state_to_id, predicate) values (16, 16, 13, 'eq ({status.12}, 12)');
insert into transition(id, state_from_id, state_to_id, predicate) values (17, 16, 4, 'eq ({status.12}, 13)');
insert into transition(id, state_from_id, state_to_id, predicate) values (18, 17, 4, 'eq ({status.13}, 15)');
insert into transition(id, state_from_id, state_to_id, predicate) values (19, 17, 18, 'eq ({status.13}, 14)');
insert into transition(id, state_from_id, state_to_id, predicate) values (22, 18, 5, 'eq ({status.14}, 17)');
insert into transition(id, state_from_id, state_to_id, predicate) values (23, 18, 19, 'eq ({status.14}, 16)');
insert into transition(id, state_from_id, state_to_id, predicate) values (24, 19, 5, 'eq ({status.15}, 19)');
insert into transition(id, state_from_id, state_to_id, predicate) values (25, 19, 6, 'eq ({status.15}, 18)');

insert into transition(id, state_from_id, state_to_id, predicate) values (26, 8, 1, 'eq ({status.1}, false)');
insert into transition(id, state_from_id, state_to_id, predicate) values (27, 9, 2, 'not(and (eq ({status.2}, true), and (eq ({status.3}, true), gte ({status.4}, 0.4))))');
insert into transition(id, state_from_id, state_to_id, predicate) values (28, 10, 2, 'eq ({status.5}, 2)');
insert into transition(id, state_from_id, state_to_id, predicate) values (29, 11, 2, 'eq ({status.6}, 5)');
insert into transition(id, state_from_id, state_to_id, predicate) values (30, 14, 15, 'eq (1, 1)');


commit;