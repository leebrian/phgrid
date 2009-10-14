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
