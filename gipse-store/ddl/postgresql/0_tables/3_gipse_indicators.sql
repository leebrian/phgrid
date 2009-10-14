/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_indicators

-- DROP TABLE ${db.schema}.gipse_indicators;

CREATE TABLE ${db.schema}.gipse_indicators
(
  indicator_id integer NOT NULL,
  classifier_id integer NOT NULL,
  indicator_name character varying(255) NOT NULL,
  indicator_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_indicators_indicator_id" PRIMARY KEY (indicator_id),
  CONSTRAINT "FK_indicators_indicator_classifiers" FOREIGN KEY (classifier_id)
      REFERENCES ${db.schema}.gipse_indicator_classifiers (classifier_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_indicators OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_indicators IS 'Lookup table for GIPSE-known indicators';
