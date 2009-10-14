USE [${db.name}]
GO
/****** Object:  Table [${db.schema}].[gipse_store]    Script Date: 07/08/2009 18:27:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [${db.schema}].[gipse_store](
	[gipse_id] [bigint] IDENTITY(1,1) NOT NULL,
	[date] [smalldatetime] NOT NULL,
	[indicator_id] [int] NOT NULL,
	[value] [float](24) NOT NULL,
	[data_source_id] [int] NULL,
	[zip5] [char](5) NULL,
	[state] [char](2) NULL,
	[age_id] [int] NULL,
	[facility_id] [int] NULL,
	[gender_id] [int] NULL,
	[service_area_id] [int] NULL,
	[date_created] [datetime] NOT NULL CONSTRAINT [DF_gipse_store_date_created]  DEFAULT (getdate()),
	[date_modified] [datetime] NOT NULL CONSTRAINT [DF_gipse_store_date_modified]  DEFAULT (getdate()),
 CONSTRAINT [PK_gipse_store] PRIMARY KEY CLUSTERED 
(
	[gipse_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_gipse_store_unique] UNIQUE NONCLUSTERED 
(
	[state] ASC,
	[zip5] ASC,
	[age_id] ASC,
	[date] ASC,
	[facility_id] ASC,
	[gender_id] ASC,
	[indicator_id] ASC,
	[service_area_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tag identifying source of record.' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_store', @level2type=N'COLUMN',@level2name=N'data_source_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'GIPSE Data Store - Aggregate/Summary data' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_store'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Unique key for stratifying variables' , @level0type=N'SCHEMA',@level0name=N'${db.schema}', @level1type=N'TABLE',@level1name=N'gipse_store', @level2type=N'CONSTRAINT',@level2name=N'IX_gipse_store_unique'
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_ages] FOREIGN KEY([age_id])
REFERENCES [${db.schema}].[gipse_ages] ([age_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_ages]
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_data_sources] FOREIGN KEY([data_source_id])
REFERENCES [${db.schema}].[gipse_data_sources] ([data_source_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_data_sources]
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_facilities] FOREIGN KEY([facility_id])
REFERENCES [${db.schema}].[gipse_facilities] ([facility_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_facilities]
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_genders] FOREIGN KEY([gender_id])
REFERENCES [${db.schema}].[gipse_genders] ([gender_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_genders]
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_indicators] FOREIGN KEY([indicator_id])
REFERENCES [${db.schema}].[gipse_indicators] ([indicator_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_indicators]
GO
ALTER TABLE [${db.schema}].[gipse_store]  WITH CHECK ADD  CONSTRAINT [FK_gipse_store_gipse_service_areas] FOREIGN KEY([service_area_id])
REFERENCES [${db.schema}].[gipse_service_areas] ([service_area_id])
GO
ALTER TABLE [${db.schema}].[gipse_store] CHECK CONSTRAINT [FK_gipse_store_gipse_service_areas]