USE [${db.name}]
GO
/****** Object:  Trigger [${db.schema}].[gipse_store_default_date_modified]    Script Date: 05/08/2009 10:01:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [${db.schema}].[gipse_store_default_date_modified]
ON [${db.schema}].[gipse_store]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM ${db.schema}.gipse_store t
                JOIN INSERTED i
                ON i.gipse_id =t.gipse_id
        END
END
