package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table(name="code")
public class CodeElement {
    //mark id as primary key
    @Id
    //defining id as column name
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", nullable = false, updatable = false)
    @ColumnDefault("random_uuid()")
    private UUID id;
    @Column(name="code_element")
    private String code;
    @Column(name="date_time")
    private String date;
    @Column(name="life_time")
    private int lifeTime;
    @Column(name="views")
    private int views;
    private LocalDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    boolean viewLimit;

    private static final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    @JsonIgnore
    public UUID getID() {
        return id;
    }

    @JsonProperty("time")
    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    @JsonProperty("views")
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate() {
        LocalDateTime localDateTime = LocalDateTime.now(); //get current date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

        this.date = localDateTime.format(formatter);
    }

    @JsonIgnore
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    public boolean isViewLimit() {
        return viewLimit;
    }

    public void setViewLimit(boolean viewlimit) {
        this.viewLimit = viewlimit;
    }
}
