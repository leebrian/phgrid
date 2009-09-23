package gov.cdc.ncphi.phgrid.gipse.dao;

import gov.cdc.ncphi.phgrid.services.gipse.service.dao.Observation;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.QueryParameters;

import java.util.List;

public interface ObservationDAO {

	/**
	 * @param parameters
	 * @return
	 */
	public abstract List<Observation> runAggregateQuery(
			QueryParameters parameters);

}