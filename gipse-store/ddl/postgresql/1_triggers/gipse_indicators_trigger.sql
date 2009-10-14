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

-- Function: gipse_indicators_default_date_modified_function()

-- DROP FUNCTION ${db.schema}.gipse_indicators_default_date_modified_function();

CREATE OR REPLACE FUNCTION ${db.schema}.gipse_indicators_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE ${db.schema}.gipse_indicators
    SET date_modified = now()
    WHERE indicator_id = new.indicator_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION ${db.schema}.gipse_indicators_default_date_modified_function() OWNER TO ${db.owner};

-- Trigger: gipse_indicators_default_date_modified on gipse_indicators

-- DROP TRIGGER gipse_indicators_default_date_modified ON gipse_indicators;

CREATE TRIGGER gipse_indicators_default_date_modified
  AFTER UPDATE
  ON ${db.schema}.gipse_indicators
  FOR EACH ROW
  EXECUTE PROCEDURE ${db.schema}.gipse_indicators_default_date_modified_function();

