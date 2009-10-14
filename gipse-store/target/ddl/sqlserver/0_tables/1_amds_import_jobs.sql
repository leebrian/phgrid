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
