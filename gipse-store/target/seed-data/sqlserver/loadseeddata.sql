USE [SAMPLEDB]
GO
INSERT INTO amds_data_sources (data_source_id,data_source_name) VALUES(1,'BioSense-Unclassified');
INSERT INTO amds_data_sources (data_source_id,data_source_name) VALUES(2,'BioSense-VA');
INSERT INTO amds_data_sources (data_source_id,data_source_name) VALUES(3,'BioSense-DoD');
INSERT INTO amds_data_sources (data_source_id,data_source_name) VALUES(4,'BioSense-RT');
USE [SAMPLEDB]
GO
INSERT INTO amds_condition_classifiers (classifier_id, classifier_name, classifier_description) VALUES(1,'BioSense,'BioSense Classifier');
INSERT INTO amds_condition_classifiers (classifier_id, classifier_name, classifier_description) VALUES(2,'RODS','Default RODS Classifier');
INSERT INTO amds_condition_classifiers (classifier_id, classifier_name, classifier_description) VALUES(3,'ESSENCE-I',''');
INSERT INTO amds_condition_classifiers (classifier_id, classifier_name, classifier_description) VALUES(4,'ESSENCE-II',''');
INSERT INTO amds_condition_classifiers (classifier_id, classifier_name, classifier_description) VALUES(5,'EARS',''');
USE [SAMPLEDB]
GO
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(80,1,'Fever','BioSense-Fever Syndrome (80)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(81,1,'Rash','BioSense-Rash Syndrome (81)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(82,1,'Botulism-like','BioSense-Botulism-like Syndrome (82)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(83,1,'Gastrointestinal','BioSense-Gastrointestinal (83)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(84,1,'Hemorrhagic','BioSense-Hemorrhagic Syndrome (84)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(85,1,'Localized Cutaneous Lesion','BioSense-Localized Cutaneous Lesion Syndrome (85)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(86,1,'Lymphadenitis','BioSense-Lymphadenitis Syndrome (86)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(87,1,'Neurological','BioSense-Neurological Syndrome (87)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(88,1,'Respiratory','BioSense-Respiratory Syndrome (88)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(89,1,'Specific Infection','BioSense-Specific Infection Syndrome (89)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(90,1,'Severe Illness or Death','BioSense-Severe Illness or Death (90)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(91,1,'Injury','BioSense-Injury Syndrome (91)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(92,1,'Chronic Disease','BioSense-Chronic Disease Syndrome (92)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(6,1,'Asthma','BioSense-Asthma Sub-Syndrome (6)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(7,1,'Bites, animal','BioSense-Bites, animal Sub-Syndrome (7)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(9,1,'Burns','BioSense-Burns Sub-Syndrome (9)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(10,1,'Carbon monoxide poisoning','BioSense-Carbon monoxide poisoning Sub-Syndrome (10)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(11,1,'Cardiac dysrhythmias','BioSense-Cardiac dysrhythmias Sub-Syndrome (11)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(13,1,'Chest pain','BioSense-Chest pain Sub-Syndrome (13)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(14,1,'CNS, inflammatory disease','BioSense-CNS, inflammatory disease Sub-Syndrome (14)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(17,1,'Convulsions','BioSense-Convulsions Sub-Syndrome (17)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(18,1,'COPD','BioSense-COPD Sub-Syndrome (18)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(19,1,'Cough','BioSense-Cough Sub-Syndrome (19)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(23,1,'Diabetes mellitus','BioSense-Diabetes mellitus Sub-Syndrome (23)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(24,1,'Diarrhea','BioSense-Diarrhea Sub-Syndrome (24)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(27,1,'Dyspnea','BioSense-Dyspnea Sub-Syndrome (27)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(29,1,'Falls','BioSense-Chronic Disease Sub-Syndrome (29)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(30,1,'Fever(Subyndrome)','BioSense-Fever(Subyndrome) Sub-Syndrome (30)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(32,1,'Fractures and dislocation','BioSense-Fractures and dislocation Sub-Syndrome (32)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(36,1,'Heart disease, ischemic','BioSense-Heart disease, ischemic Sub-Syndrome (36)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(37,1,'Heat, excessive','BioSense-Heat, excessive Sub-Syndrome (37)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(43,1,'Injury, NOS','BioSense-Injury, NOS Sub-Syndrome (43)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(44,1,'Insect bites','BioSense-Insect bites Sub-Syndrome (44)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(46,1,'Jaundice','BioSense-Jaundice Sub-Syndrome (46)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(49,1,'Meningismus','BioSense-Meningismus Sub-Syndrome (49)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(50,1,'Mental disorders','BioSense-Mental disorders Sub-Syndrome (50)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(52,1,'Motor vehicle traffic accidents','BioSense-Motor vehicle traffic accidents Sub-Syndrome (52)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(54,1,'Nausea and vomiting','BioSense-Nausea and vomiting Sub-Syndrome (54)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(62,1,'Pneumonia and lung abscess','BioSense-Pneumonia and lung abscess Sub-Syndrome (62)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(64,1,'Pregnancy, childbirth, puerperium complications','BioSense-Pregnancy, childbirth, puerperium complications Sub-Syndrome (64)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(66,1,'Rash(Subsyndrome)','BioSense-Rash(Subsyndrome) Sub-Syndrome (66)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(71,1,'Skin infection','BioSense-Skin infection Sub-Syndrome (92)');
INSERT INTO amds_conditions (condition_id,classifier_id,condition_name,condition_description) VALUES(100,1,'ILI2','BioSense-ILI2 Sub-Syndrome (100)');

