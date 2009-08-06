package gov.cdc.ncphi.phgrid.services.gipse.service.dao;

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

    private static final String SQL_MAP_FILE = "sqlMap-config.xml";

    public static SqlMapClient getSqlMap() throws IOException {
        if (sqlMap == null) {
            Reader reader = Resources.getResourceAsReader(SQL_MAP_FILE);
            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
            LOGGER.debug("Ibatis initialized");
        }

        return sqlMap;

    }



}
