package wang.huaichao.data.entity.crm;

import org.springframework.format.annotation.DateTimeFormat;
import wang.huaichao.Global;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 11/8/2016.
 */
@Entity
@Table(name = "customer_to_ignore")
public class CustomerToIgnore {
    @Id
    private String customerId;

    @DateTimeFormat(pattern = Global.DB_DATETIME_FMT_STR)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, updatable = false)
    private Date createdAt;

    @Enumerated(value = EnumType.STRING)
    private CustomerToIgnoreType type;


    public enum CustomerToIgnoreType {
        EMAIL_PDF, GENERATE_PDF
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CustomerToIgnoreType getType() {
        return type;
    }

    public void setType(CustomerToIgnoreType type) {
        this.type = type;
    }
}
