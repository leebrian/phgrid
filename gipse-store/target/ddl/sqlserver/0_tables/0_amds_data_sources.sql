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
