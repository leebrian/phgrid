/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: amds_view_va

-- DROP VIEW SAMPLESCHEMA.amds_view_va;

CREATE OR REPLACE VIEW SAMPLESCHEMA.amds_view_va AS 
 SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, "substring"(SAMPLESCHEMA.amds_extract.zip5::text, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
   FROM SAMPLESCHEMA.amds_extract
   JOIN SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id
   JOIN SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id;

ALTER TABLE SAMPLESCHEMA.amds_view_va OWNER TO SAMPLEOWNER;