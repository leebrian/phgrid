/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
-- View: gipse_view_bs_dod

-- DROP VIEW ${db.schema}.gipse_view_bs_dod;

CREATE OR REPLACE VIEW ${db.schema}.gipse_view_bs_dod AS 
 SELECT ${db.schema}.gipse_store.date, ${db.schema}.gipse_store.zip5, "substring"(${db.schema}.gipse_store.zip5::text, 1, 3) AS zip3, ${db.schema}.gipse_store.state, ${db.schema}.gipse_store.value, ${db.schema}.gipse_indicator_classifiers.classifier_name AS classifier, ${db.schema}.gipse_indicators.indicator_name AS indicator
   FROM ${db.schema}.gipse_store
   JOIN ${db.schema}.gipse_indicators ON ${db.schema}.gipse_store.indicator_id = ${db.schema}.gipse_indicators.indicator_id
   JOIN ${db.schema}.gipse_indicator_classifiers ON ${db.schema}.gipse_indicators.classifier_id = ${db.schema}.gipse_indicator_classifiers.classifier_id
WHERE ${db.schema}.gipse_store.data_source_id =3; --DoD in data source table    

ALTER TABLE ${db.schema}.gipse_view_bs_dod OWNER TO ${db.owner};

