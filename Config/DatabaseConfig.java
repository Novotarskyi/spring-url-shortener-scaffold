package Config;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DatabaseConfig {

    @Bean
    @Qualifier("dbPool")
    public BoneCP dbPool() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance(); 	// load the DB driver
        BoneCPConfig config = new BoneCPConfig();	// create a new configuration object
        config.setJdbcUrl("jdbc:mysql://localhost:3306/the_db_name");	// set the JDBC url
        config.setUsername("root");			// set the username
        config.setPassword("");				// set the password

        config.setMinConnectionsPerPartition(6);
        config.setMaxConnectionsPerPartition(12);
        config.setPartitionCount(3);
        config.setAcquireIncrement(6);

        BoneCP connectionPool = new BoneCP(config); 	// setup the connection pool

        return connectionPool;
    }
}
