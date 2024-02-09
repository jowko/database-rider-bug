package pl.jowko;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.dsl.RiderDSL;

import lombok.SneakyThrows;
import pl.jowko.rider.DBUnitExtension;
import pl.jowko.rider.EntityManagerProvider;

@ExtendWith( DBUnitExtension.class )
@DBUnit( caseInsensitiveStrategy = Orthography.UPPERCASE, qualifiedTableNames = true )
public abstract class AbstractDatabaseTest
{

    protected static final EntityManagerProvider INSTANCE = EntityManagerProvider.instance( "test" );

    @SuppressWarnings( "unused" )
    private final ConnectionHolder connectionHolder = INSTANCE::connection;

    @AfterEach
    protected void cleanUpDatabase()
    {
        try
        {
            DBUtil.cleanUpDb( INSTANCE.getEm() );
        }
        catch( PersistenceException | IllegalStateException exception )
        {
            if( getTransaction().isActive() )
            {
                // rollback transaction in case of database error. If transaction will not be rollbacked, then
                // all db tests executed after this exception will fail
                rollbackTransaction();
                DBUtil.cleanUpDb( INSTANCE.getEm() );
                throw exception;
            }
        }
    }

    protected void initializeDataSet( String prefix, List< String > fileNames )
    {
        var dataSets = fileNames.stream()
            .map( name -> prefix + name )
            .toList();
        initializeDataSet( dataSets );
    }

    @SneakyThrows
    protected void initializeDataSet( List< String > dataSets )
    {
        RiderDSL.withConnection( INSTANCE.connection() )
            .withDataSetConfig(
                new DataSetConfig( dataSets.toArray( String[]::new ) ).useSequenceFiltering( false ) )
            .createDataSet();
    }

    protected void beginTransaction()
    {
        getTransaction().begin();
    }

    protected void commitTransaction()
    {
        getTransaction().commit();
    }

    protected void rollbackTransaction()
    {
        getTransaction().rollback();
    }

    protected EntityManager getEm()
    {
        return INSTANCE.getEm();
    }

    protected EntityTransaction getTransaction()
    {
        return getEm().getTransaction();
    }

}
