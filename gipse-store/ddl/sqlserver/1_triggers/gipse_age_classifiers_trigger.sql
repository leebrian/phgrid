USE [${db.name}]
GO
/****** Object:  Trigger [${db.schema}].[gipse_age_classifiers_default_date_modified]    Script Date: 05/08/2009 09:59:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [${db.schema}].[gipse_age_classifiers_default_date_modified]
ON [${db.schema}].[gipse_age_classifiers]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM ${db.schema}.gipse_age_classifiers t
                JOIN INSERTED i
                ON i.classifier_id =t.classifier_id
        END
END
