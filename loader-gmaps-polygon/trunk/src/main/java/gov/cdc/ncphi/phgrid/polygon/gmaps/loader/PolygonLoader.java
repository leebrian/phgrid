package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;

import java.sql.SQLException;
import java.util.List;

public interface PolygonLoader {
	public void loadData(List<DatabasePolygon> polygons) throws SQLException;
	public void createConnection();
}
