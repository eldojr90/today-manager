CREATE DATABASE "app-today"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;

CREATE TABLE usuarios
(
  us_id numeric NOT NULL DEFAULT nextval('sq_pq_usuario'::regclass),
  us_nome character varying(10),
  us_tipo_usuario numeric,
  us_password character varying(30),
  CONSTRAINT pk_us_id PRIMARY KEY (us_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuarios
  OWNER TO postgres;

CREATE TABLE clientes
(
  cli_id numeric NOT NULL DEFAULT nextval('sq_pk_cli'::regclass),
  cli_cnpj character varying(14),
  cli_rsocial character varying(70),
  cli_nfant character varying(70),
  cli_resp character varying(70),
  cli_ddd_tel character varying(10),
  cli_tel character varying(60),
  cli_ddd_cel character varying(20),
  cli_cel character varying(60),
  CONSTRAINT pk_cli_id PRIMARY KEY (cli_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE clientes
  OWNER TO postgres;

CREATE TABLE atendimentos
(
  atd_id numeric NOT NULL DEFAULT nextval('sq_pk_atd'::regclass),
  cli_id numeric,
  atd_desc character varying(2000),
  atd_status numeric,
  atd_data date,
  CONSTRAINT atendimentos_cli_id_fkey FOREIGN KEY (cli_id)
      REFERENCES clientes (cli_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE atendimentos
  OWNER TO postgres;

