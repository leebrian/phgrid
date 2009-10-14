USE [SAMPLEDB]
GO
/****** Object:  Table [SAMPLESCHEMA].[amds_data_sources]    Script Date: 05/08/2009 09:52:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SAMPLESCHEMA].[amds_data_sources](
        [data_source_id] [int] NOT NULL,
        [data_source_name] [varchar](50) NOT NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_amds_data_sources_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_amds_data_sources_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_amds_data_sources] PRIMARY KEY CLUSTERED 
(
        [data_source_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Lookup Table for Data Sources' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_data_sources'
USE [SAMPLEDB]
GO
/****** Object:  Table [SAMPLESCHEMA].[amds_import_jobs]    Script Date: 05/08/2009 09:52:38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SAMPLESCHEMA].[amds_import_jobs](
        [job_id] [int] IDENTITY(1,1) NOT NULL,
        [job_description] [varchar](255) NOT NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_amds_import_jobs_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_amds_import_jobs_date_modified]  DEFAULT (getdate()),
        [records_inserted] [bigint] NULL,
        [records_updated] [bigint] NULL,
        [records_deleted] [bigint] NULL,
        [status] [varchar](50) NULL,
 CONSTRAINT [PK_amds_import_jobs] PRIMARY KEY CLUSTERED 
(
        [job_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Table for storing job running data.' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_import_jobs'
USE [SAMPLEDB]
GO
/****** Object:  Table [SAMPLESCHEMA].[amds_condition_classifiers]    Script Date: 05/08/2009 09:50:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SAMPLESCHEMA].[amds_condition_classifiers](
        [classifier_id] [int] NOT NULL,
        [classifier_name] [varchar](255) NOT NULL,
        [classifier_description] [varchar](1000) NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_condition_classifiers_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_condition_classifiers_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_condition_classifiers] PRIMARY KEY CLUSTERED 
(
        [classifier_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
USE [SAMPLEDB]
GO
/****** Object:  Table [SAMPLESCHEMA].[amds_conditions]    Script Date: 05/08/2009 09:51:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SAMPLESCHEMA].[amds_conditions](
        [condition_id] [int] NOT NULL,
        [classifier_id] [int] NOT NULL,
        [condition_name] [varchar](255) NOT NULL,
        [condition_description] [varchar](1000) NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_conditions_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_conditions_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_conditions_condition_id] PRIMARY KEY CLUSTERED 
(
        [condition_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Lookup table for AMDS-known conditions' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_conditions'
GO
ALTER TABLE [SAMPLESCHEMA].[amds_conditions]  WITH CHECK ADD  CONSTRAINT [FK_conditions_condition_classifiers] FOREIGN KEY([classifier_id])
REFERENCES [SAMPLESCHEMA].[amds_condition_classifiers] ([classifier_id])
GO
ALTER TABLE [SAMPLESCHEMA].[amds_conditions] CHECK CONSTRAINT [FK_conditions_condition_classifiers]
USE [SAMPLEDB]
GO
/****** Object:  Table [SAMPLESCHEMA].[amds_extract]    Script Date: 05/08/2009 09:52:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [SAMPLESCHEMA].[amds_extract](
        [date] [smalldatetime] NOT NULL,
        [zip5] [char](5) NOT NULL,
        [state] [char](2) NOT NULL,
        [condition] [int] NOT NULL,
        [count] [int] NOT NULL,
        [date_created] [datetime] NOT NULL CONSTRAINT [DF_amds_extract_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NOT NULL CONSTRAINT [DF_amds_extract_date_modified]  DEFAULT (getdate()),
        [source_oid] [int] NULL,
 CONSTRAINT [PK_amds_extract] PRIMARY KEY CLUSTERED 
(
        [date] ASC,
        [zip5] ASC,
        [state] ASC,
        [condition] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'name of the condition' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_extract', @level2type=N'COLUMN',@level2name=N'condition'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tag identifying source of record' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_extract', @level2type=N'COLUMN',@level2name=N'source_oid'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'AMDS Data Store' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_extract'
GO
ALTER TABLE [SAMPLESCHEMA].[amds_extract]  WITH CHECK ADD  CONSTRAINT [FK_amds_extract_amds_conditions] FOREIGN KEY([condition])
REFERENCES [SAMPLESCHEMA].[amds_conditions] ([condition_id])
GO
ALTER TABLE [SAMPLESCHEMA].[amds_extract] CHECK CONSTRAINT [FK_amds_extract_amds_conditions]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Condition FK' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_extract', @level2type=N'CONSTRAINT',@level2name=N'FK_amds_extract_amds_conditions'
GO
ALTER TABLE [SAMPLESCHEMA].[amds_extract]  WITH CHECK ADD  CONSTRAINT [FK_amds_extract_amds_data_sources] FOREIGN KEY([source_oid])
REFERENCES [SAMPLESCHEMA].[amds_data_sources] ([data_source_id])
GO
ALTER TABLE [SAMPLESCHEMA].[amds_extract] CHECK CONSTRAINT [FK_amds_extract_amds_data_sources]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Data Sources FK' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'TABLE',@level1name=N'amds_extract', @level2type=N'CONSTRAINT',@level2name=N'FK_amds_extract_amds_data_sources'
USE [SAMPLEDB]
GO
/****** Object:  Trigger [SAMPLESCHEMA].[amds_condition_classifiers_default_date_modified]    Script Date: 05/08/2009 09:59:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [SAMPLESCHEMA].[amds_condition_classifiers_default_date_modified]
ON [SAMPLESCHEMA].[amds_condition_classifiers]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM SAMPLESCHEMA.amds_condition_classifiers t
                JOIN INSERTED i
                ON i.classifier_id =t.classifier_id
        END
END
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
USE [SAMPLEDB]
GO
/****** Object:  Trigger [SAMPLESCHEMA].[amds_import_jobs_default_date_modified]    Script Date: 05/08/2009 10:02:29 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [SAMPLESCHEMA].[amds_import_jobs_default_date_modified]
ON [SAMPLESCHEMA].[amds_import_jobs]
AFTER UPDATE
AS
BEGIN
        IF NOT UPDATE(date_modified)
        BEGIN
                UPDATE t
                SET t.date_modified=GETDATE()
                FROM SAMPLESCHEMA.amds_import_jobs t
                JOIN INSERTED i
                ON i.job_id =t.job_id
        END
END
USE [SAMPLEDB]
GO
/****** Object:  View [SAMPLESCHEMA].[amds_view]    Script Date: 05/08/2009 09:57:46 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [SAMPLESCHEMA].[amds_view]
AS
SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, SUBSTRING(SAMPLESCHEMA.amds_extract.zip5, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, 
               SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
FROM  SAMPLESCHEMA.amds_extract INNER JOIN
			   SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id INNER JOIN
               SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id
               

GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "amds_extract"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 135
               Right = 240
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_condition_classifiers"
            Begin Extent = 
               Top = 37
               Left = 482
               Bottom = 165
               Right = 685
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_conditions"
            Begin Extent = 
               Top = 225
               Left = 504
               Bottom = 353
               Right = 708
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 3768
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view'
USE [SAMPLEDB]
GO
/****** Object:  View [SAMPLESCHEMA].[amds_view_dod]    Script Date: 05/08/2009 09:57:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [SAMPLESCHEMA].[amds_view_dod]
AS
SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, SUBSTRING(SAMPLESCHEMA.amds_extract.zip5, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, 
               SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
FROM  SAMPLESCHEMA.amds_extract INNER JOIN
			   SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id INNER JOIN
               SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id
WHERE SAMPLESCHEMA.amds_extract.source_oid = 3 --DoD in data source table

GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "amds_extract"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 135
               Right = 240
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_condition_classifiers"
            Begin Extent = 
               Top = 140
               Left = 48
               Bottom = 268
               Right = 251
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_conditions"
            Begin Extent = 
               Top = 273
               Left = 48
               Bottom = 401
               Right = 252
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_dod'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_dod'
USE [SAMPLEDB]
GO
/****** Object:  View [SAMPLESCHEMA].[amds_view_rt]    Script Date: 05/08/2009 09:58:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [SAMPLESCHEMA].[amds_view_rt]
AS
SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, SUBSTRING(SAMPLESCHEMA.amds_extract.zip5, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, 
               SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
FROM  SAMPLESCHEMA.amds_extract INNER JOIN
			   SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id INNER JOIN
               SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id
WHERE SAMPLESCHEMA.amds_extract.source_oid = 4 --Real-Time in data source table

GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "amds_extract"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 135
               Right = 240
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_condition_classifiers"
            Begin Extent = 
               Top = 140
               Left = 48
               Bottom = 268
               Right = 251
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_conditions"
            Begin Extent = 
               Top = 273
               Left = 48
               Bottom = 401
               Right = 252
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_rt'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_rt'
USE [SAMPLEDB]
GO
/****** Object:  View [SAMPLESCHEMA].[amds_view_va]    Script Date: 05/08/2009 09:58:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [SAMPLESCHEMA].[amds_view_va]
AS
SELECT SAMPLESCHEMA.amds_extract.date, SAMPLESCHEMA.amds_extract.zip5, SUBSTRING(SAMPLESCHEMA.amds_extract.zip5, 1, 3) AS zip3, SAMPLESCHEMA.amds_extract.state, SAMPLESCHEMA.amds_extract.count, 
               SAMPLESCHEMA.amds_condition_classifiers.classifier_name AS classifier, SAMPLESCHEMA.amds_conditions.condition_name AS condition
FROM  SAMPLESCHEMA.amds_extract INNER JOIN
			   SAMPLESCHEMA.amds_conditions ON SAMPLESCHEMA.amds_extract.condition = SAMPLESCHEMA.amds_conditions.condition_id INNER JOIN
               SAMPLESCHEMA.amds_condition_classifiers ON SAMPLESCHEMA.amds_conditions.classifier_id = SAMPLESCHEMA.amds_condition_classifiers.classifier_id
WHERE SAMPLESCHEMA.amds_extract.source_oid = 2 --VA in data source table

GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "amds_extract"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 135
               Right = 240
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_condition_classifiers"
            Begin Extent = 
               Top = 140
               Left = 48
               Bottom = 268
               Right = 251
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "amds_conditions"
            Begin Extent = 
               Top = 273
               Left = 48
               Bottom = 401
               Right = 252
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_va'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'SAMPLESCHEMA', @level1type=N'VIEW',@level1name=N'amds_view_va'
