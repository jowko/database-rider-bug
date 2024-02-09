package pl.jowko;

import java.util.Arrays;

import javax.persistence.EntityManager;

class DBUtil
{

    // @formatter:off
    static final String[] ORDERED_DROP_TABLES = new String[] {
            "public.TEST_ENTITY"
    };
    // @formatter:on

    static void cleanUpDb( EntityManager em )
    {
        var tx = em.getTransaction();
        tx.begin();
        Arrays.stream( ORDERED_DROP_TABLES )
            .forEach( tableName -> {
                em.createNativeQuery( "DELETE FROM " + tableName )
                    .executeUpdate();
            } );
        tx.commit();
        em.clear();
    }

}
