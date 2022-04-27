drop table if exists game CASCADE;
drop table if exists review CASCADE;

create table game (id integer not null auto_increment, genre varchar(64) not null, age_rating integer not null , release_year integer not null, title varchar(64) not null, primary key (id));

create table review (id integer not null auto_increment, review varchar(500), rating integer not null check (rating>=1 AND rating<=10), movie_id integer, primary key (id));

alter table review add constraint FK8378dhlpvq0e9tnkn9mx0r64i foreign key (game_id) references game (id);