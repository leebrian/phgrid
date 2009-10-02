package gov.cdc.ncphi.phgrid.gridviewer;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class Cacher {

	
	private static GeneralCacheAdministrator cacheAdmin = null;
	
	public static GeneralCacheAdministrator getCache()
	{
		if (cacheAdmin == null)
		{
			cacheAdmin = new GeneralCacheAdministrator();
		}
		return cacheAdmin;
	}
}
