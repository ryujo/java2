package lv.javaguru.java2.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "visitors_counter")
@Getter
@Setter
@ToString
public class CountVisitor implements BaseEntity, CountEntity {

    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="product_id")
    private long productId;

    @Column(name="counter")
    private int counter;

    @Column(name = "ip")
    private String ip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CountVisitor that = (CountVisitor) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(productId, that.productId)
                .append(counter, that.counter)
                .append(ip, that.ip)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(productId)
                .append(counter)
                .append(ip)
                .toHashCode();
    }
}
