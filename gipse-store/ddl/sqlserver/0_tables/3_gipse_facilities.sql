USE [${db.name}]
GO
/****** Object:  Table [${db.schema}].[gipse_facilities]    Script Date: 05/08/2009 09:51:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [${db.schema}].[gipse_facilities](
        [facility_id] [int] NOT NULL,
        [classifier_id] [int] NOT NULL,
        [facility_name] [varchar](255) NOT NULL,
        [facility_description] [varchar](1000) NULL,
        [date_created] [datetime] NULL CONSTRAINT [DF_facilities_date_created]  DEFAULT (getdate()),
        [date_modified] [datetime] NULL CONSTRAINT [DF_facilities_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_facilities_facility_id] PRIMARY KEY CLUSTERED 
(
        [facility_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Lookup table for gipse-known facilities' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_facilities'
GO
ALTER TABLE [${db.schema}].[gipse_facilities]  WITH CHECK ADD  CONSTRAINT [FK_facilities_facility_classifiers] FOREIGN KEY([classifier_id])
REFERENCES [${db.schema}].[gipse_facility_classifiers] ([classifier_id])
GO
ALTER TABLE [${db.schema}].[gipse_facilities] CHECK CONSTRAINT [FK_facilities_facility_classifiers]
