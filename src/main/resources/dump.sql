create table disease
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table doctor
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table patient
(
    id                bigserial primary key,
    name              varchar(255) not null,
    birth_date        timestamp    not null,
    disease_id        bigserial    not null,
    current_status_id bigserial,
    doctor_id         bigserial    not null,

    constraint patient_disease_id_fkey foreign key (disease_id)
        references disease (id)
        on update no action on delete cascade,

    constraint patient_doctor_id_fkey foreign key (doctor_id)
        references doctor (id)
        on update no action on delete cascade
);

create table state
(
    id          bigserial primary key,
    name        varchar(255) not null,
    description text         not null,
    disease_id  bigserial    not null,

    constraint state_disease_id_fkey foreign key (disease_id)
        references disease (id)
        on update no action on delete cascade
);

create table status
(
    id          bigserial primary key,
    patient_id  bigserial not null,
    submit_date timestamp not null,
    state_id    bigserial not null,
    is_draft    boolean   not null,
    constraint status_patient_id_fkey foreign key (patient_id)
        references patient (id)
        on update no action on delete cascade,

    constraint status_state_id_fkey foreign key (state_id)
        references state (id)
        on update no action on delete cascade
);

alter table patient
    add constraint patient_current_status_id_fkey foreign key (current_status_id)
        references status (id)
        on update no action on delete cascade;
alter table patient
    alter column current_status_id drop not null;

create table transition
(
    id            bigserial primary key,
    state_from_id bigserial not null,
    state_to_id   bigserial not null,
    predicate     text      not null,

    constraint transition_state_from_id_fkey foreign key (state_from_id)
        references state (id)
        on update no action on delete cascade,

    constraint transition_state_to_id_fkey foreign key (state_to_id)
        references state (id)
        on update no action on delete cascade
);

create table medicine
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table medicine_for_diseases
(
    id          bigserial primary key,
    medicine_id bigserial not null,
    disease_id  bigserial not null,

    constraint medicine_for_diseases_medicine_id_fkey foreign key (medicine_id)
        references medicine (id)
        on update no action on delete cascade,

    constraint medicine_for_diseases_disease_id_fkey foreign key (disease_id)
        references disease (id)
        on update no action on delete cascade
);

create table prescription
(
    id          bigserial primary key,
    status_id   bigserial not null,
    medicine_id bigserial not null,

    constraint prescription_status_id_fkey foreign key (status_id)
        references status (id)
        on update no action on delete cascade,

    constraint prescription_medicine_id_fkey foreign key (medicine_id)
        references medicine (id)
        on update no action on delete cascade
);

create table association
(
    id                  bigserial primary key,
    created_date        timestamp not null,
    predicate           text      not null,
    text                text      not null,
    association_type_id integer   not null,
    doctor_id           bigserial not null,

    constraint association_doctor_id_fkey foreign key (doctor_id)
        references doctor (id)
        on update no action on delete cascade
);

create table contradictions
(
    id           bigserial primary key,
    medicine_id  bigserial not null,
    created_date timestamp not null,
    predicate    text      not null,
    source       text      not null,

    constraint contradictions_medicine_id_fkey foreign key (medicine_id)
        references medicine (id)
        on update no action on delete cascade
);

create table active_substance
(
    id   bigserial primary key,
    name varchar(512) not null
);

create table active_substance_in_medicine
(
    id                  bigserial primary key,
    active_substance_id bigserial not null,
    medicine_id         bigserial not null,
    constraint active_substances_in_medicine_medicine_id_fkey foreign key (medicine_id)
        references medicine (id)
        on update no action on delete cascade,

    constraint active_substances_in_medicine_substance_id_fkey foreign key (active_substance_id)
        references active_substance (id)
        on update no action on delete cascade
);


create table attributes
(
    id   bigserial primary key,
    name varchar(255) not null,
    type varchar(255) not null
);

create table attribute_values
(
    id           bigserial primary key,
    value        varchar(255) not null,
    attribute_id bigserial    not null,

    constraint attribute_id_id_fkey foreign key (attribute_id)
        references attributes (id)
        on update no action on delete cascade
);

create table disease_attributes
(
    id                  bigserial primary key,
    requirement_type_id integer   not null,
    requirement_id      bigint    not null,
    is_required         boolean   not null,
    attribute_id        bigserial not null,

    constraint attribute_id_id_fkey foreign key (attribute_id)
        references attributes (id)
        on update no action on delete cascade
);

create table disease_attribute_values
(
    id                   bigserial primary key,
    status_id            bigserial not null,
    disease_attribute_id bigserial not null,
    value                text      not null,

    constraint diseases_attribute_value_disease_attribute_id_fkey foreign key (disease_attribute_id)
        references disease_attributes (id)
        on update no action on delete cascade,

    constraint diseases_attribute_value_status_id_fkey foreign key (status_id)
        references status (id)
        on update no action on delete cascade
);

create table state_image
(
    id                  bigserial    primary key,
    machine_state       varchar(10)  not null,
    algorithm_position  varchar(128) not null,
    picture             varchar(256) not null
);

insert into state_image(id, machine_state, algorithm_position, picture) values
(1, 'HS 1', '1', '/src/main/resources/images/Target_v3_2/HS_1.png'),
(2, 'HS 2', '2', '/src/main/resources/images/Target_v3_2/HS_2.png'),
(3, 'HS 3', '3', '/src/main/resources/images/Target_v3_2/HS_3.png'),
(4, 'HS 3.1', '3B*', '/src/main/resources/images/Target_v3_2/HS_3_1.png'),
(5, 'HS 4', '4', '/src/main/resources/images/Target_v3_2/HS_4.png'),
(6, 'HS 5', '4A', '/src/main/resources/images/Target_v3_2/HS_5.png'),
(7, 'HS 6', '6', '/src/main/resources/images/Target_v3_2/HS_6.png'),
(8, 'HS 1', 'Exercise stress echocardiography test', '/src/main/resources/images/Target_v3_2/HS_8.png'),
(9, 'HS 9', 'Psychodiagnostics', '/src/main/resources/images/Target_v3_2/HS_10.png'),
(10, 'HS 10', 'Psychodiagnostics', '/src/main/resources/images/Target_v3_2/HS_10.png'),
(11, 'HS 11', 'Psychotherapy, psychotropic drugs ', '/src/main/resources/images/Target_v3_2/HS_11.png'),
(12, 'HSt 1', '1', '/src/main/resources/images/Target_v3_2/HSt_1.png'),
(13, 'HSt 2', '2B', '/src/main/resources/images/Target_v3_2/HSt_2.png'),
(14, 'HSt 3', 'Optimal Medical Therapy CAD, AAT, CA, ICD', '/src/main/resources/images/Target_v3_2/HS_3.png'),
(15, 'HSt 4', 'Optimal Medical Therapy CAD, AAT, CA, ICD', '/src/main/resources/images/Target_v3_2/HSt_4.png'),
(17, 'HSt 5', 'Beta-blockers', '/src/main/resources/images/Target_v3_2/HSt_5.png'),
(15, 'HSt 6', 'Optimal Medical Therapy CAD, AAT, CA, ICD', '/src/main/resources/images/Target_v3_2/HSt_6.png');
