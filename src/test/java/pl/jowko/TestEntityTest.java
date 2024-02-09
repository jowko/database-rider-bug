package pl.jowko;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.database.rider.core.api.dataset.DataSet;

class TestEntityTest extends AbstractDatabaseTest
{

    private EntityManager em;

    @BeforeEach
    void setUp()
    {
        em = getEm();
    }

    @Test
    @DataSet( value = "pl/jowko/test.yaml" )
    void shouldGetData()
    {
        TestEntity entity = em.find( TestEntity.class, "test-id" );
        // this does not work on Jakarta, null is returned on Jakarta variant
        assertThat( entity ).isNotNull();
    }

    @Test
    void shouldGetDataFromInitializedDataSet()
    {
        beginTransaction();
        initializeDataSet( "pl/jowko/", List.of( "test2.yaml" ) );
        commitTransaction();
        List< TestEntity > entity = em.createQuery( "Select e From TestEntity e", TestEntity.class )
            .getResultList();
        // this does not work on Jakarta, empty list is returned on Jakarta variant
        assertThat( entity ).isNotEmpty();
    }

    @Test
    void shouldSaveData()
    {
        beginTransaction();
        TestEntity entity = new TestEntity();
        entity.setName( "abc" );
        em.persist( entity );
        commitTransaction();
        assertThat( em.createQuery( "Select e FROM TestEntity e", TestEntity.class )
            .getResultList() ).hasSize( 1 )
            .extracting( TestEntity::getName )
            .containsOnly( "abc" );
    }

}
