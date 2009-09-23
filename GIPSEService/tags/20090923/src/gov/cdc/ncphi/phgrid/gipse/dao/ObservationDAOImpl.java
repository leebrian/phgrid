package gov.cdc.ncphi.phgrid.gipse.dao;

import java.util.List;

import gov.cdc.ncphi.phgrid.services.gipse.service.dao.Observation;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.QueryParameters;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;

public class ObservationDAOImpl extends SqlMapDaoTemplate implements ObservationDAO  {
	public static final String IBATIS_BASIC_QUERY = "mscBasicSelect";
	
	public ObservationDAOImpl(DaoManager daoManager) {
		super(daoManager);
	}

	/* (non-Javadoc)
	 * @see gov.cdc.ncphi.phgrid.gipse.dao.ObservationDAO#runAggregateQuery(gov.cdc.ncphi.phgrid.services.gipse.service.dao.QueryParameters)
	 */
	public List<Observation> runAggregateQuery(QueryParameters parameters) {
		return  queryForList(IBATIS_BASIC_QUERY,  parameters);
	}
	
}
