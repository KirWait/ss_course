-- liquibase formatted sql
create sequence "Project_ProjectID_seq"
    as integer;

alter sequence "Project_ProjectID_seq" owner to postgres;

create sequence "Users_UserID_seq"
    as integer;

alter sequence "Users_UserID_seq" owner to postgres;

create sequence "Tasks_TaskID_seq"
    as integer;

alter sequence "Tasks_TaskID_seq" owner to postgres;

create sequence versions_task_version_id_seq
    as integer;

alter sequence versions_task_version_id_seq owner to postgres;

create table users
(
    user_id   integer      default nextval('"Users_UserID_seq"'::regclass) not null
        constraint "Users_pk"
            primary key,
    user_name varchar(255)                                                 not null,
    password  varchar(255)                                                 not null,
    roles     varchar(255)                                                 not null,
    status    varchar(255) default 'ACTIVE'::character varying
);

alter table users
    owner to postgres;

alter sequence "Users_UserID_seq" owned by users.user_id;

create table project
(
    project_id   integer     default nextval('"Project_ProjectID_seq"'::regclass) not null
        constraint "Project_pk"
            primary key,
    project_name varchar(255)                                                     not null,
    customer_id  integer                                                          not null
        constraint "Project_fk0"
            references users
            on update cascade on delete cascade,
    status       varchar(25) default 'BACKLOG'::character varying
);

alter table project
    owner to postgres;

alter sequence "Project_ProjectID_seq" owned by project.project_id;

create table release
(
    version       varchar(255) default '1.0'::character varying                          not null,
    creation_time varchar(255)                                                           not null,
    end_time      varchar(255),
    release_id    integer      default nextval('versions_task_version_id_seq'::regclass) not null
        constraint versions_pk
            primary key,
    project_id    integer                                                                not null
        constraint versions_project_project_id_fk
            references project
            on update cascade on delete cascade
);

alter table release
    owner to postgres;

alter sequence versions_task_version_id_seq owned by release.project_id;

create table tasks
(
    task_id        integer      default nextval('"Tasks_TaskID_seq"'::regclass) not null
        constraint "Tasks_pk"
            primary key,
    project_id     integer                                                      not null
        constraint "tasks___fk "
            references project
            on update cascade on delete cascade,
    status         varchar(255) default 'BACKLOG'::character varying            not null,
    name           varchar(255)                                                 not null,
    description    varchar(255),
    author_id      integer                                                      not null
        constraint "Tasks_fk1"
            references users
            on update cascade on delete cascade,
    responsible_id integer
        constraint "Tasks_fk2"
            references users
            on update cascade on delete cascade,
    type           varchar(255)                                                 not null,
    release_id     integer                                                      not null
        constraint tasks___fk3
            references release
            on update cascade on delete cascade,
    creation_time  varchar(255),
    start_time     varchar(255),
    end_time       varchar(255)
);

alter table tasks
    owner to postgres;

alter sequence "Tasks_TaskID_seq" owned by tasks.task_id;

create unique index versions_task_version_id_uindex
    on release (release_id);

