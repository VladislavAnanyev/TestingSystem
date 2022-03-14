/*
create table quizzes (
    id integer not null,
    text varchar(255),
    title varchar(255),
    user_username varchar(255),
    primary key (id)
);

create table quiz_answer (
    quiz_id integer not null,
    answer integer
);

create table quiz_options (
    quiz_id integer not null,
    options varchar(255)
);

create table user (
    username varchar(255) not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    primary key (username)
);

create table user_roles (
    user_username varchar(255) not null,
    roles varchar(255)
);

create table user_answer (
    answer_id integer not null,
    completed_at timestamp,
    status boolean,
    quiz_id integer,
    user_username varchar(255),
    primary key (answer_id)
);

create table user_answer_answer (
    user_answer_answer_id integer not null,
    answer integer
);

create sequence hibernate_sequence start with 1 increment by 1;

alter table quiz add constraint FKemrrkpa0pdoq6l4i6rop84djn foreign key (user_username) references user;
alter table quiz_answer add constraint FKoxi2td1x8cc3y4a0vlsg2hfnc foreign key (quiz_id) references quiz;
alter table quiz_options add constraint FKsx28j7orq6asg17veq9nblhw8 foreign key (quiz_id) references quiz;
alter table user_roles add constraint FK1misndtpfm9hx3ttvixdus8d1 foreign key (user_username) references user;
alter table user_answer add constraint FKbmbh6qr5gwlgwh4ae4ddg4b1c foreign key (quiz_id) references quiz;
alter table user_answer add constraint FKqi862rhltk0ufk964wjm7sam4 foreign key (user_username) references user;
alter table user_answer_answer add constraint FKffc3dp19gknu785dp0oum7yys foreign key (user_answer_answer_id) references user_answer;*/