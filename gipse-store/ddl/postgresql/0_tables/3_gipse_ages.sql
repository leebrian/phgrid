/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_ages

-- DROP TABLE ${db.schema}.gipse_ages;

CREATE TABLE ${db.schema}.gipse_ages
(
  age_id integer NOT NULL,
  classifier_id integer NOT NULL,
  age_name character varying(255) NOT NULL,
  age_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_ages_age_id" PRIMARY KEY (age_id),
  CONSTRAINT "FK_ages_age_classifiers" FOREIGN KEY (classifier_id)
      REFERENCES ${db.schema}.gipse_age_classifiers (classifier_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_ages OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_ages IS 'Lookup table for GIPSE-known ages';
