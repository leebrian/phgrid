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
