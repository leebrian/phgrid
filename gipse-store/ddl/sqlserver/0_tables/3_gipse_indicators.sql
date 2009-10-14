USE [${db.name}]
GO
/****** Object:  Table [${db.schema}].[gipse_indicators]    Script Date: 05/08/2009 09:51:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [${db.schema}].[gipse_indicators](
        [indicator_id] [int] NOT NULL,
        [classifier_id] [int] NOT NULL,
        [indicator_name] [varchar](255) NOT NULL,
        [indicator_description] [varchar](1000) NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_indicators_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_indicators_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_indicators_indicator_id] PRIMARY KEY CLUSTERED 
(
        [indicator_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Lookup table for gipse-known indicators' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_indicators'
GO
ALTER TABLE [${db.schema}].[gipse_indicators]  WITH CHECK ADD  CONSTRAINT [FK_indicators_indicator_classifiers] FOREIGN KEY([classifier_id])
REFERENCES [${db.schema}].[gipse_indicator_classifiers] ([classifier_id])
GO
ALTER TABLE [${db.schema}].[gipse_indicators] CHECK CONSTRAINT [FK_indicators_indicator_classifiers]
