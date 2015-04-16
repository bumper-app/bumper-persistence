DROP database bumper;

\i create_user.sql

CREATE DATABASE bumper
	WITH OWNER = bumper
	ENCODING = 'UTF8';

\connect bumper

CREATE TABLE people(
	id serial NOT NULL,
	name varchar(50) NOT NULL,
	pseudo varchar(50) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE people OWNER TO bumper;



CREATE TABLE dataset(
	id serial NOT NULL,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE dataset OWNER TO bumper;

CREATE TABLE project(
	id serial NOT NULL,
	name varchar(50) NOT NULL,
	dataset_id integer references dataset(id),
	parent_id integer references project(id),
	PRIMARY KEY (id)
);
ALTER TABLE project OWNER TO bumper;

CREATE TABLE file(
	id serial NOT NULL,
	name varchar(500) NOT NULL,
	project_id integer references project(id) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE file OWNER TO bumper;

CREATE TABLE issue(
	
	id serial NOT NULL,
	exteral_id varchar(50) NOT NULL,
	status_id integer,
	severity_id integer,
	resolution_id integer,
	version varchar(50),
	project_id integer references project(id) NOT NULL,
	dataset_id integer references dataset(id) NOT NULL,
	short_description varchar(200) NOT NULL,
	long_description text,
	reporter_id integer references people(id) NOT NULL,
	type_id integer,
	PRIMARY KEY (id)
);
ALTER TABLE issue OWNER TO bumper;

CREATE TABLE comment(
	id serial NOT NULL,
	comment text NOT NULL,
	people_id integer references people(id) NOT NULL,
	date_event timestamp with time zone default current_timestamp NOT NULL,
	issue_id integer references issue(id) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE comment OWNER TO bumper;

CREATE TABLE changeset(
	id serial NOT NULL,
	date_commit timestamp with time zone default current_timestamp NOT NULL,
	commiter_id integer references people(id) NOT NULL,
	issue_id integer references issue(id) NOT NULL,
	commit_message text NOT NULL,
	commit_revision text NOT NULL,
	changeset_type_id integer NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE changeset OWNER TO bumper;

CREATE TABLE issue_event(
	id serial NOT NULL,
	issue_id integer references issue(id) NOT NULL,
	date_event timestamp with time zone default current_timestamp NOT NULL,
	type_event integer  NOT NULL,
	people_id integer references people(id) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE issue_event OWNER TO bumper;

CREATE TABLE change(
	id serial NOT NULL,
	changeset_id integer references changeset(id) NOT NULL,
	file_id integer references file(id) NOT NULL,
	position varchar(200) NOT NULL,
	insertion integer NOT NULL,
	deletion integer NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE change OWNER TO bumper;