USE [SAMPLEDB]
GO
/****** Object:  Trigger [SAMPLESCHEMA].[amds_conditions_default_date_modified]    Script Date: 05/08/2009 10:00:03 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [SAMPLESCHEMA].[amds_conditions_default_date_modified]
ON [SAMPLESCHEMA].[amds_conditions]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM SAMPLESCHEMA.amds_conditions t
                JOIN INSERTED i
                ON i.condition_id =t.condition_id
        END
END
