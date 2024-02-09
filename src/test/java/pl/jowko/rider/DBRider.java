package pl.jowko.rider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Created by rafael-pestano on 26/10/2016. Shortcut to enable database rider in junit5 tests.
 * Replaces @ExtendWith(DBUnitExtension.class) and can also be used at method level.
 */
@Target(
{ ElementType.METHOD, ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
@ExtendWith( DBUnitExtension.class )
@Test
public @interface DBRider
{

    /**
     * @return name of the DataSource bean in Spring Context. If empty then default dataSource will be used.
     */
    String dataSourceBeanName() default "";

}
