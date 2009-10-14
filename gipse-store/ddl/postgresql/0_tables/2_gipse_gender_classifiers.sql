/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_gender_classifiers

-- DROP TABLE ${db.schema}.gipse_gender_classifiers;

CREATE TABLE ${db.schema}.gipse_gender_classifiers
(
  classifier_id integer NOT NULL,
  classifier_name character varying(255) NOT NULL,
  classifier_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_gender_classifiers" PRIMARY KEY (classifier_id)
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_gender_classifiers OWNER TO ${db.owner};
