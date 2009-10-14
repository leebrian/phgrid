USE [${db.name}]
GO
/****** Object:  Table [${db.schema}].[gipse_genders]    Script Date: 05/08/2009 09:51:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [${db.schema}].[gipse_genders](
        [gender_id] [int] NOT NULL,
        [classifier_id] [int] NOT NULL,
        [gender_name] [varchar](255) NOT NULL,
        [gender_description] [varchar](1000) NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_genders_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_genders_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_genders_gender_id] PRIMARY KEY CLUSTERED 
(
        [gender_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Lookup table for gipse-known genders' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_genders'
GO
ALTER TABLE [${db.schema}].[gipse_genders]  WITH CHECK ADD  CONSTRAINT [FK_genders_gender_classifiers] FOREIGN KEY([classifier_id])
REFERENCES [${db.schema}].[gipse_gender_classifiers] ([classifier_id])
GO
ALTER TABLE [${db.schema}].[gipse_genders] CHECK CONSTRAINT [FK_genders_gender_classifiers]
