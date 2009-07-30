package gov.cdc.ncphi.phgrid.services.gipse.common.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Manages DAO for the gipse service
 * @project PHGrid (Apache v2.0 license)
 * @author Brian Alexander Lee (brianalee@gmail.com)
 * @created May 29, 2009 11:36:33 AM
 * 
 *
 */
public class DatabaseManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class);

    private static SqlMapClient sqlMap = null;

    private static String resource = "sqlMap-config.xml";

    public static SqlMapClient getSqlMap() throws IOException {
        if (sqlMap == null) {
            Reader reader = Resources.getResourceAsReader(resource);
            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
            LOGGER.debug("Ibatis initialized");
        }

        return sqlMap;

    }



}
