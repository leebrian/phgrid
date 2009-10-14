USE [SAMPLEDB]
GO
/****** Object:  Trigger [SAMPLESCHEMA].[amds_data_sources_default_date_modified]    Script Date: 05/08/2009 10:00:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [SAMPLESCHEMA].[amds_data_sources_default_date_modified]
ON [SAMPLESCHEMA].[amds_data_sources]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM SAMPLESCHEMA.amds_data_sources t
                JOIN INSERTED i
                ON i.data_source_id =t.data_source_id
        END
END
