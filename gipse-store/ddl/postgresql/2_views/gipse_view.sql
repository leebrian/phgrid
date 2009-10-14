/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: gipse_view

-- DROP VIEW ${db.schema}.gipse_view;

CREATE OR REPLACE VIEW ${db.schema}.gipse_view AS 
 SELECT ${db.schema}.gipse_store.date, 
	${db.schema}.gipse_store.zip5, 
	"substring"(${db.schema}.gipse_store.zip5::text, 1, 3) AS zip3, 
	${db.schema}.gipse_store.state, 
	${db.schema}.gipse_store.value, 
	${db.schema}.gipse_indicator_classifiers.classifier_name AS classifier, 
	${db.schema}.gipse_indicators.indicator_name AS indicator,
	${db.schema}.gipse_data_sources.data_source_name AS data_source,
	${db.schema}.gipse_service_areas.service_area_name AS service_area,
	${db.schema}.gipse_ages.age_name AS age
   FROM ${db.schema}.gipse_store
   JOIN ${db.schema}.gipse_indicators ON ${db.schema}.gipse_store.indicator_id = ${db.schema}.gipse_indicators.indicator_id
   JOIN ${db.schema}.gipse_indicator_classifiers ON ${db.schema}.gipse_indicators.classifier_id = ${db.schema}.gipse_indicator_classifiers.classifier_id
   JOIN ${db.schema}.gipse_data_sources ON ${db.schema}.gipse_store.data_source_id = ${db.schema}.gipse_data_sources.data_source_id
   LEFT OUTER JOIN ${db.schema}.gipse_service_areas ON ${db.schema}.gipse_store.service_area_id = ${db.schema}.gipse_service_areas.service_area_id
   LEFT OUTER JOIN ${db.schema}.gipse_ages ON ${db.schema}.gipse_store.age_id = ${db.schema}.gipse_ages.age_id;

ALTER TABLE ${db.schema}.gipse_view OWNER TO ${db.owner};

