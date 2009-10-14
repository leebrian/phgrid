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
