CREATE TABLE employee (
    name character varying NOT NULL,
    salary integer NOT NULL,
    id serial PRIMARY KEY,
    organization_id integer
);
CREATE TABLE organization (
    name character varying NOT NULL,
    id serial PRIMARY KEY
);