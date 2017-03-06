package net.filecode.angular2.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Trade.
 */

@Document(collection = "trade")
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("product_type")
    private String productType;

    @Field("product_family")
    private String productFamily;

    @Field("price")
    private BigDecimal price;

    @Field("currency")
    private String currency;

    @Field("creation_date")
    private LocalDate creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public Trade productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public Trade productFamily(String productFamily) {
        this.productFamily = productFamily;
        return this;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Trade price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public Trade currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Trade creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trade trade = (Trade) o;
        if (trade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trade{" +
            "id=" + id +
            ", productType='" + productType + "'" +
            ", productFamily='" + productFamily + "'" +
            ", price='" + price + "'" +
            ", currency='" + currency + "'" +
            ", creationDate='" + creationDate + "'" +
            '}';
    }
}
