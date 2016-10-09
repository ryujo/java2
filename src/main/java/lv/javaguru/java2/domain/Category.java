
package lv.javaguru.java2.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category implements BaseEntity {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "father_id")
    @Getter
    @Setter
    private long fatherId;

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + "',fid= " + fatherId + '}';
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if ((object == null) || !(object instanceof Category)) {
            return false;
        }

        Category that = (Category) object;
        return new EqualsBuilder()
                .append(id, that.getId())
                .append(name, that.getName())
                .append(fatherId, that.getFatherId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .toHashCode();
    }
}