package Services;


import com.jolbox.bonecp.BoneCP;
import com.mysql.jdbc.MysqlErrorNumbers;
import Models.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DatabaseService {

    @Autowired
    private BoneCP dbPool;

    public boolean storeLinkToDb(Link link){
        return this.performInsertQuery("INSERT INTO links (original, shortened) values ('" + link.getOriginalLink() + "', '" + link.getShortenedLink() +"');");
    }

    public String getLinkFromDb(String link){
        List links = this.performQuery("SELECT * from links WHERE `shortened` = '" + link + "';");
        Map linkFromDb = (TreeMap)links.get(0);

        return (String)linkFromDb.get("original");
    }

    public List getLinkListFromDb(){
        List links = this.performQuery("SELECT * from links");

        return links;
    }

    public boolean performInsertQuery(String query) {
        Connection connection = null;
        PreparedStatement queryStatement = null;

        Boolean retryTransaction = Boolean.FALSE;
        Integer retryCount = 1;

        do {
            try {
                connection           = this.dbPool.getConnection();
                queryStatement       = connection.prepareStatement(query);
                Integer rowsAffected = queryStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                switch (e.getErrorCode()) {
                    case MysqlErrorNumbers.ER_LOCK_DEADLOCK:
                    case MysqlErrorNumbers.ER_LOCK_WAIT_TIMEOUT:
                        System.err.println("MySQL :Could not get a lock, trying to restart transaction. Attempt " + retryCount + " of " + 3);
                        retryTransaction = Boolean.TRUE;
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {

                        }

                        break;
                    default:

                        return false;
                }

            } catch (Exception e) {

                return false;
            } finally {
                this.closeStatement(queryStatement);
                this.closeConnection(connection);
            }

            retryCount++;
        } while (retryTransaction && (retryCount < 3 + 1));

        System.err.println("Could not complete SQL transaction after " + 3 + " retries. Aborting.");
        return false;
    }


    public List performQuery(String query) {
        Connection connection = null;
        PreparedStatement queryStatement = null;
        ResultSet result = null;

        try {
            connection = this.dbPool.getConnection();
            queryStatement = connection.prepareStatement(query);

            result = queryStatement.executeQuery();

            List results = this.toList(result);
            if (results == null) {
                results = new ArrayList();
            }
            return results;
        } catch (Exception e) {
            return null;
        } finally {
            this.closeResultSet(result);
            this.closeStatement(queryStatement);
            this.closeConnection(connection);
        }
    }

    /**
     * Helper method that converts a ResultSet into a list of maps, one per row
     *
     * @return list of maps, one per row, with column name as the key
     * @throws java.sql.SQLException if the connection fails
     */
    public List toList(ResultSet rs) throws SQLException {
        if (!(rs == null)) {
            List wantedColumnNames = this.getColumnNames(rs);

            return this.toList(rs, wantedColumnNames);
        } else {
            return new ArrayList();
        }
    }

    /**
     * Helper method that maps a ResultSet into a list of maps, one per row
     *
     * @return list of maps, one per column row, with column names as keys
     * @throws SQLException if the connection fails
     */
    public List toList(ResultSet rs, List wantedColumnNames) throws SQLException {
        List rows = new ArrayList();

        int numWantedColumns = wantedColumnNames.size();
        while (rs.next()) {
            Map row = new TreeMap();

            for (int i = 0; i < numWantedColumns; ++i) {
                String columnName = (String) wantedColumnNames.get(i);
                Object value = rs.getObject(columnName);
                row.put(columnName, value);
            }

            rows.add(row);
        }

        return rows;
    }


    /**
     * Return all column names as a list of strings
     *
     * @return list of column name strings
     * @throws SQLException if the query fails
     */
    public List getColumnNames(ResultSet rs) throws SQLException {
        List columnNames = new ArrayList();

        ResultSetMetaData meta = rs.getMetaData();

        int numColumns = meta.getColumnCount();
        for (int i = 1; i <= numColumns; ++i) {
            columnNames.add(meta.getColumnName(i));
        }

        return columnNames;
    }

    /**
     * Closing methods are to close Statements, ResulSet and Connections in this
     * order please call these methods inside a catch finally block
     *
     * @param cnn
     */
    public void closeConnection(final Connection cnn) {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (Exception ex) {

                //log errors
            }
        }
    }

    /**
     * Closing a Statement
     *
     * @param stmt
     */
    public void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception ex) {

                //log errors
            }
        }

    }

    /**
     * Closing a ResultSet
     *
     * @param rs
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {

                // log errors
            }
        }
    }
}
