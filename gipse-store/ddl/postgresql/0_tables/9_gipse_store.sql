/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- Sequence: gipse_store_sequence

-- DROP SEQUENCE ${db.schema}.gipse_store_sequence;

CREATE SEQUENCE ${db.schema}.gipse_store_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE ${db.schema}.gipse_store_sequence OWNER TO ${db.owner};

-- Table: gipse_store

-- DROP TABLE ${db.schema}.gipse_store;

CREATE TABLE ${db.schema}.gipse_store
(
  gipse_id bigint NOT NULL DEFAULT nextval('${db.schema}.gipse_store_sequence'::regclass),
  date date NOT NULL,
  indicator_id integer NOT NULL,
  value float(24) NOT NULL,
  data_source_id integer,
  zip5 character(5),
  state character(2),
  age_id integer,
  facility_id integer,
  gender_id integer,
  service_area_id integer,
  date_created timestamp with time zone DEFAULT now(),
  date_modified timestamp with time zone DEFAULT now(),
  CONSTRAINT "PK_gipse_store" PRIMARY KEY (gipse_id),
  CONSTRAINT "FK_gipse_store_gipse_indicator" FOREIGN KEY (indicator_id)
      REFERENCES ${db.schema}.gipse_indicators (indicator_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_gipse_store_gipse_data_sources" FOREIGN KEY (data_source_id)
      REFERENCES ${db.schema}.gipse_data_sources (data_source_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_gipse_store_gipse_ages" FOREIGN KEY (age_id)
      REFERENCES ${db.schema}.gipse_ages (age_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_gipse_store_gipse_facilities" FOREIGN KEY (facility_id)
      REFERENCES ${db.schema}.gipse_facilities (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_gipse_store_gipse_genders" FOREIGN KEY (gender_id)
      REFERENCES ${db.schema}.gipse_genders (gender_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FK_gipse_store_gipse_service_areas" FOREIGN KEY (service_area_id)
      REFERENCES ${db.schema}.gipse_service_areas (service_area_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_store OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_store IS 'GIPSE Data Store';
COMMENT ON COLUMN ${db.schema}.gipse_store.indicator_id IS 'id of indicator-required stratifier';
COMMENT ON COLUMN ${db.schema}.gipse_store.value IS 'value of observation- could be int, ratio, code, etc.';
COMMENT ON COLUMN ${db.schema}.gipse_store.data_source_id IS 'Tag identifying source of record';