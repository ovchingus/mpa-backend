create table disease (
  id   bigserial primary key,
  name varchar(255)
);

create table doctor (
  id   bigserial primary key,
  name varchar(255)
);

create table patient (
  id         bigserial primary key,
  name       varchar(255),
  birth_date timestamp,
  disease_id bigserial,
  doctor_id  bigserial,
  constraint patient_disease_id_fkey foreign key (disease_id)
  references disease (id)
  on update no action on delete cascade,

  constraint patient_doctor_id_fkey foreign key (doctor_id)
  references doctor (id)
  on update no action on delete cascade
);

create table state (
  id          bigserial primary key,
  name        varchar(255),
  description text,
  disease_id  bigserial,

  constraint state_disease_id_fkey foreign key (disease_id)
  references disease (id)
  on update no action on delete cascade
);

create table status (
  id          bigserial primary key,
  patient_id  bigserial,
  submit_date timestamp,
  state_id    bigserial,
  is_draft    boolean,
  constraint status_patient_id_fkey foreign key (patient_id)
  references patient (id)
  on update no action on delete cascade,

  constraint status_state_id_fkey foreign key (state_id)
  references state (id)
  on update no action on delete cascade
);

create table transition (
  id            bigserial primary key,
  state_from_id bigserial not null,
  state_to_id   bigserial not null,

  constraint transition_state_from_id_fkey foreign key (state_from_id)
  references state (id)
  on update no action on delete cascade,

  constraint transition_state_to_id_fkey foreign key (state_to_id)
  references state (id)
  on update no action on delete cascade
);

create table medicine (
  id   bigserial primary key,
  name varchar(255)
);

create table prescription (
  id          bigserial primary key,
  status_id   bigserial,
  medicine_id bigserial,

  constraint prescription_status_id_fkey foreign key (status_id)
  references status (id)
  on update no action on delete cascade,

  constraint prescription_medicine_id_fkey foreign key (medicine_id)
  references medicine (id)
  on update no action on delete cascade
);

create table association (
  id           bigserial primary key,
  created_date timestamp,
  predicate    text,
  text         text,
  doctor_id    bigserial,

  constraint association_doctor_id_fkey foreign key (doctor_id)
  references doctor (id)
  on update no action on delete cascade
);