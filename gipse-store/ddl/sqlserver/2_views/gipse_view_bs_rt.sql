USE [${db.name}]
GO
/****** Object:  View [${db.schema}].[gipse_view]    Script Date: 05/08/2009 09:57:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [${db.schema}].[gipse_view_bs_rt]
AS
SELECT ${db.schema}.gipse_store.date, ${db.schema}.gipse_store.zip5, SUBSTRING(${db.schema}.gipse_store.zip5, 1, 3) AS zip3, ${db.schema}.gipse_store.state, ${db.schema}.gipse_store.value, 
               ${db.schema}.gipse_indicator_classifiers.classifier_name AS classifier, ${db.schema}.gipse_indicators.indicator_name AS indicator
FROM  ${db.schema}.gipse_store INNER JOIN
			   ${db.schema}.gipse_indicators ON ${db.schema}.gipse_store.indicator_id = ${db.schema}.gipse_indicators.indicator_id INNER JOIN
               ${db.schema}.gipse_indicator_classifiers ON ${db.schema}.gipse_indicators.classifier_id = ${db.schema}.gipse_indicator_classifiers.classifier_id
WHERE ${db.schema}.gipse_store.data_source_id = 4 --RT in data source table               

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'View that limits observations to only those loaded from the BioSense RT extract.' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'VIEW',@level1name=N'gipse_view_bs_rt'