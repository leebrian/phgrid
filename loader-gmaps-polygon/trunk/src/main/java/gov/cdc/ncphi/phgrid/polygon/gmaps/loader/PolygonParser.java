package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;



import java.util.List;

public interface PolygonParser {

	List <DatabasePolygon> parseReturn(String filename);
}
