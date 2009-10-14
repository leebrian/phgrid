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
