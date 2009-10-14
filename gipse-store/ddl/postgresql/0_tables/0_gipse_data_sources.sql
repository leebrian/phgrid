/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

-- Table: gipse_data_sources

-- DROP TABLE ${db.schema}.gipse_data_sources;

CREATE TABLE ${db.schema}.gipse_data_sources
(
   data_source_id integer NOT NULL, 
   data_source_name character varying(50) NOT NULL, 
   date_created timestamp with time zone DEFAULT current_timestamp, 
   date_modified timestamp with time zone DEFAULT current_timestamp, 
   CONSTRAINT "PK_gipse_data_sources" PRIMARY KEY (data_source_id)
) 
WITHOUT OIDS;

ALTER TABLE ${db.schema}.gipse_data_sources OWNER TO ${db.owner};
COMMENT ON TABLE ${db.schema}.gipse_data_sources IS 'Lookup Table for Data Sources';
