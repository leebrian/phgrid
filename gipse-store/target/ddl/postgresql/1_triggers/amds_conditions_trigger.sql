/*
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.10 1626-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */

--Scripts for installing plsgsql are below, if necessary
/*
CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE FUNCTION plpgsql_validator(oid) RETURNS void AS
    '$libdir/plpgsql' LANGUAGE C;
CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql
    HANDLER plpgsql_call_handler
    VALIDATOR plpgsql_validator;
*/

-- Function: amds_conditions_default_date_modified_function()

-- DROP FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function();

CREATE OR REPLACE FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE SAMPLESCHEMA.amds_condition
    SET date_modified = now()
    WHERE condition_id = new.condition_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION SAMPLESCHEMA.amds_conditions_default_date_modified_function() OWNER TO SAMPLEOWNER;

-- Trigger: amds_conditions_default_date_modified on amds_conditions

-- DROP TRIGGER amds_conditions_default_date_modified ON amds_conditions;

CREATE TRIGGER amds_conditions_default_date_modified
  AFTER UPDATE
  ON SAMPLESCHEMA.amds_conditions
  FOR EACH ROW
  EXECUTE PROCEDURE SAMPLESCHEMA.amds_conditions_default_date_modified_function();

