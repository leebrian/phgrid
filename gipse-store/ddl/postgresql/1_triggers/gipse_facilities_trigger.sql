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

-- Function: gipse_facilities_default_date_modified_function()

-- DROP FUNCTION ${db.schema}.gipse_facilities_default_date_modified_function();

CREATE OR REPLACE FUNCTION ${db.schema}.gipse_facilities_default_date_modified_function()
  RETURNS trigger AS
$BODY$
BEGIN
  IF new.date_modified = old.date_modified THEN
   UPDATE ${db.schema}.gipse_facilities
    SET date_modified = now()
    WHERE facility_id = new.facility_id;
 END IF;
 RETURN NEW;
END
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
  
ALTER FUNCTION ${db.schema}.gipse_facilities_default_date_modified_function() OWNER TO ${db.owner};

-- Trigger: gipse_facilities_default_date_modified on gipse_facilities

-- DROP TRIGGER gipse_facilities_default_date_modified ON gipse_facilities;

CREATE TRIGGER gipse_facilities_default_date_modified
  AFTER UPDATE
  ON ${db.schema}.gipse_facilities
  FOR EACH ROW
  EXECUTE PROCEDURE ${db.schema}.gipse_facilities_default_date_modified_function();

