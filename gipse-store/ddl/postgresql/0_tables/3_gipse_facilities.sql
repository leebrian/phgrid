/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_facilities

-- DROP TABLE ${db.schema}.gipse_facilities;

CREATE TABLE ${db.schema}.gipse_facilities
(
  facility_id integer NOT NULL,
  classifier_id integer NOT NULL,
  facility_name character varying(255) NOT NULL,
  facility_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_facilities_facility_id" PRIMARY KEY (facility_id),
  CONSTRAINT "FK_facilities_facility_classifiers" FOREIGN KEY (classifier_id)
      REFERENCES ${db.schema}.gipse_facility_classifiers (classifier_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_facilities OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_facilities IS 'Lookup table for GIPSE-known facilities';
