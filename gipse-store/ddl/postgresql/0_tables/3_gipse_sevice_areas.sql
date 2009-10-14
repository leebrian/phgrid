/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_service_areas

-- DROP TABLE ${db.schema}.gipse_service_areas;

CREATE TABLE ${db.schema}.gipse_service_areas
(
  service_area_id integer NOT NULL,
  classifier_id integer NOT NULL,
  service_area_name character varying(255) NOT NULL,
  service_area_description character varying(1000),
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_service_areas_service_area_id" PRIMARY KEY (service_area_id),
  CONSTRAINT "FK_service_areas_service_area_classifiers" FOREIGN KEY (classifier_id)
      REFERENCES ${db.schema}.gipse_service_area_classifiers (classifier_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_service_areas OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_service_areas IS 'Lookup table for GIPSE-known service_areas';
