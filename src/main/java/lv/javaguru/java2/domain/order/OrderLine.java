package lv.javaguru.java2.domain.order;

import lv.javaguru.java2.domain.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "order_lines")
public class OrderLine implements BaseEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "order_id")
    private long orderId;
    @Column(name = "product_id")
    private long productId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private long price;
    @Column(name = "quantity")
    private long quantity;
    @Column(name = "expire_date")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        return new EqualsBuilder()
                .append(id, orderLine.id)
                .append(orderId, orderLine.orderId)
                .append(productId, orderLine.productId)
                .append(price, orderLine.price)
                .append(quantity, orderLine.quantity)
                .append(name, orderLine.name)
                .append(description, orderLine.description)
                .append(expireDate, orderLine.expireDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(orderId)
                .append(productId)
                .append(name)
                .append(description)
                .append(price)
                .append(quantity)
                .append(expireDate)
                .toHashCode();
    }
}
