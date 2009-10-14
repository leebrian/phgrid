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
