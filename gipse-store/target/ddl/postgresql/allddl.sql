/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: amds_data_sources

-- DROP TABLE SAMPLESCHEMA.amds_data_sources;

CREATE TABLE SAMPLESCHEMA.amds_data_sources
(
   data_source_id integer NOT NULL, 
   data_source_name character varying(50) NOT NULL, 
   date_created timestamp with time zone DEFAULT current_timestamp, 
   date_modified timestamp with time zone DEFAULT current_timestamp, 
   CONSTRAINT "PK_amds_data_sources" PRIMARY KEY (data_source_id)
) 
WITHOUT OIDS;

ALTER TABLE SAMPLESCHEMA.amds_data_sources OWNER TO SAMPLEOWNER;
COMMENT ON TABLE SAMPLESCHEMA.amds_data_sources IS 'Lookup Table for Data Sources';
/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Sequence: amds_import_jobs_sequence

-- DROP SEQUENCE SAMPLESCHEMA.amds_import_jobs_sequence;

CREATE SEQUENCE SAMPLESCHEMA.amds_import_jobs_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE SAMPLESCHEMA.amds_import_jobs_sequence OWNER TO SAMPLEOWNER;


-- Table: amds_import_jobs

-- DROP TABLE SAMPLESCHEMA.amds_import_jobs;

CREATE TABLE SAMPLESCHEMA.amds_import_jobs
(
  job_id integer NOT NULL DEFAULT nextval('SAMPLESCHEMA.amds_import_jobs_sequence'::regclass),
  job_description character varying(255) NOT NULL,
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  records_inserted bigint,
  records_updated bigint,
  records_deleted bigint,
  status character varying(50),
  CONSTRAINT "PK_amds_import_jobs" PRIMARY KEY (job_id)
)
WITHOUT OIDS;
ALTER TABLE SAMPLESCHEMA.amds_import_jobs OWNER TO SAMPLEOWNER;
COMMENT ON TABLE SAMPLESCHEMA.amds_import_jobs IS 'Table for storing job running data.';
/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: amds_condition_classifiers

-- DROP TABLE SAMPLESCHEMA.amds_condition_classifiers;

CREATE TABLE SAMPLESCHEMA.amds_condition_classifiers
(
  classifier_id integer NOT NULL,
  classifier_name character varying(255) NOT NULL,
  classifier_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_condition_classifiers" PRIMARY KEY (classifier_id)
)
WITHOUT OIDS;

ALTER TABLE SAMPLESCHEMA.amds_condition_classifiers OWNER TO SAMPLEOWNER;
/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: amds_conditions

-- DROP TABLE SAMPLESCHEMA.amds_conditions;

CREATE TABLE SAMPLESCHEMA.amds_conditions
(
  condition_id integer NOT NULL,
  classifier_id integer NOT NULL,
  condition_name character varying(255) NOT NULL,
  condition_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_conditions_condition_id" PRIMARY KEY (condition_id),
  CONSTRAINT "FK_conditions_condition_classifiers" FOREIGN KEY (classifier_id)
      REFERENCES SAMPLESCHEMA.amds_condition_classifiers (classifier_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE SAMPLESCHEMA.amds_conditions OWNER TO SAMPLEOWNER;
COMMENT ON TABLE SAMPLESCHEMA.amds_conditions IS 'Lookup table for AMDS-known conditions';
/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: amds_extract

-- DROP TABLE SAMPLESCHEMA.amds_extract;

CREATE TABLE SAMPLESCHEMA.amds_extract
(
  date date NOT NULL,
  zip5 character(5) NOT NULL,
  state character(2) NOT NULL,
  condition integer NOT NULL,
  count integer NOT NULL,
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  source_oid integer,
  CONSTRAINT "PK_amds_extract" PRIMARY KEY (date, zip5, state, condition),
  CONSTRAINT "FK_amds_extract_amds_conditions" FOREIGN KEY (condition)
      REFERENCES SAMPLESCHEMA.amds_conditions (condition_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_amds_extract_amds_data_sources" FOREIGN KEY (source_oid)
      REFERENCES SAMPLESCHEMA.amds_data_sources (data_source_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE SAMPLESCHEMA.amds_extract OWNER TO SAMPLEOWNER;
COMMENT ON TABLE SAMPLESCHEMA.amds_extract IS 'AMDS Data Store';
COMMENT ON COLUMN SAMPLESCHEMA.amds_extract.condition IS 'name of the condition';
COMMENT ON COLUMN SAMPLESCHEMA.amds_extract.source_oid IS 'Tag identifying source of record';/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_condition_classifiers_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_condition_classifiers_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_condition_classifiers_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_condition_classifiers
    SET date_modified = now()
    WHERE classifier_id = new.classifier_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_condition_classifiers_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_condition_classifiers_default_date_modified on amds_condition_classifiers

-- DROP TRIGGER amds_condition_classifiers_default_date_modified ON amds_condition_classifiers;

CREATE TRIGGER amds_condition_classifiers_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_condition_classifiers
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_condition_classifiers_default_date_modified_function();

/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_conditions_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_condition
    SET date_modified = now()
    WHERE condition_id = new.condition_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_conditions_default_date_modified on amds_conditions

-- DROP TRIGGER amds_conditions_default_date_modified ON amds_conditions;

CREATE TRIGGER amds_conditions_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_conditions
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_conditions_default_date_modified_function();

/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_data_sources_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_data_sources_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_data_sources_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_data_sources
    SET date_modified = now()
    WHERE condition_id = new.condition_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_data_sources_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_data_sources_default_date_modified on amds_data_sources

-- DROP TRIGGER amds_data_sources_default_date_modified ON amds_data_sources;

CREATE TRIGGER amds_data_sources_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_data_sources
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_data_sources_default_date_modified_function();

/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_extract_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_extract_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_extract_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_extract
    SET date_modified = now()
    WHERE condition = new.condition;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_extract_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_extract_default_date_modified on amds_extract

-- DROP TRIGGER amds_extract_default_date_modified ON amds_extract;

CREATE TRIGGER amds_extract_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_extract
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_extract_default_date_modified_function();


/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_import_jobs_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_import_jobs_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_import_jobs_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_import_jobs
    SET date_modified = now()
    WHERE job_id = new.job_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_import_jobs_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_import_jobs_default_date_modified on amds_import_jobs

-- DROP TRIGGER amds_import_jobs_default_date_modified ON amds_import_jobs;

CREATE TRIGGER amds_import_jobs_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_import_jobs
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_import_jobs_default_date_modified_function();



/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: amds_view

-- DROP VIEW SAMPLESCHEMA.amds_view;

CREATE OR REPLACE VIEW SAMPLESCHEMA.amds_view AS 
 SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, "substring"(SAMPLESCHEMA.amds_extract.zip5::text, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
   FROM SAMPLESCHEMA.amds_extract
   JOIN SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id
   JOIN SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id;

ALTER TABLE SAMPLESCHEMA.amds_view OWNER TO SAMPLEOWNER;

/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: amds_view_dod

-- DROP VIEW SAMPLESCHEMA.amds_view_dod;

CREATE OR REPLACE VIEW SAMPLESCHEMA.amds_view_dod AS 
 SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, "substring"(SAMPLESCHEMA.amds_extract.zip5::text, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
   FROM SAMPLESCHEMA.amds_extract
   JOIN SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id
   JOIN SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id;

ALTER TABLE SAMPLESCHEMA.amds_view_dod OWNER TO SAMPLEOWNER;/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: amds_view_rt

-- DROP VIEW SAMPLESCHEMA.amds_view_rt;

CREATE OR REPLACE VIEW SAMPLESCHEMA.amds_view_rt AS 
 SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, "substring"(SAMPLESCHEMA.amds_extract.zip5::text, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
   FROM SAMPLESCHEMA.amds_extract
   JOIN SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id
   JOIN SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id;

ALTER TABLE SAMPLESCHEMA.amds_view_rt OWNER TO SAMPLEOWNER;/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: amds_view_va

-- DROP VIEW SAMPLESCHEMA.amds_view_va;

CREATE OR REPLACE VIEW SAMPLESCHEMA.amds_view_va AS 
 SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, "substring"(SAMPLESCHEMA.amds_extract.zip5::text, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
   FROM SAMPLESCHEMA.amds_extract
   JOIN SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id
   JOIN SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id;

ALTER TABLE SAMPLESCHEMA.amds_view_va OWNER TO SAMPLEOWNER;