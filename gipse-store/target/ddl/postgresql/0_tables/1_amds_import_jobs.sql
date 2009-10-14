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
