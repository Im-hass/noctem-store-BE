package noctem.storeService.global.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private Timestamp updatedAt;

    @JsonIgnore
    private Boolean isDeleted = false;
}
