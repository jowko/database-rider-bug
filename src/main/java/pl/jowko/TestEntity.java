package pl.jowko;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@Getter
@Setter
@Entity
@Table( name = "TEST_ENTITY" )
public class TestEntity implements Serializable
{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column( name = "ID" )
    @EqualsAndHashCode.Include
    private String id;

    @Column( name = "NAME", nullable = false, length = 128 )
    private String name = "";

    public TestEntity()
    {
        this.id = java.util.UUID.randomUUID()
            .toString();
    }

}
