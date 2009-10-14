USE [SAMPLEDB]
GO
/****** Object:  Trigger [SAMPLESCHEMA].[amds_extract_default_date_modified]    Script Date: 05/08/2009 10:01:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [SAMPLESCHEMA].[amds_extract_default_date_modified]
ON [SAMPLESCHEMA].[amds_extract]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM SAMPLESCHEMA.amds_extract t
                JOIN INSERTED i
                ON i.date =t.date
                AND i.zip5 = t.zip5
                AND i.state = t.state
                AND i.condition = t.condition
        END
END
