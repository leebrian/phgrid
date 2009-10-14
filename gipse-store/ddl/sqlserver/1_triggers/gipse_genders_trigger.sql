USE [${db.name}]
GO
/****** Object:  Trigger [${db.schema}].[gipse_genders_default_date_modified]    Script Date: 05/08/2009 10:00:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [${db.schema}].[gipse_genders_default_date_modified]
ON [${db.schema}].[gipse_genders]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM ${db.schema}.gipse_genders t
                JOIN INSERTED i
                ON i.gender_id =t.gender_id
        END
END
