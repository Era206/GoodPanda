package goodpanda.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

/**
 * @author sanjidaera
 * @since 27/11/24
 */
@MappedSuperclass
public abstract class Persistent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(name="createdDate",updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedDate")
    private LocalDateTime lastUpdatedTime;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public abstract Integer getId();

    public boolean isNew() {
        if(isNull(this.getId())) {
            return false;
        }

        return true;
    }
}
