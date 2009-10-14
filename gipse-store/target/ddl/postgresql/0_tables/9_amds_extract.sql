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
COMMENT ON COLUMN SAMPLESCHEMA.amds_extract.source_oid IS 'Tag identifying source of record';