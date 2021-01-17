package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code {

    @Id
    @Column(name="CODEID")
    @JsonIgnore
    private UUID uuid;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private long time;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Code() {
    }

    public Code(String code, LocalDateTime date) {
        this.code = code;
        this.date = LocalDateTime.now().withNano(0);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return this.date.format(FORMATTER);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code1 = (Code) o;
        return code.equals(code1.code) && date.equals(code1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, date);
    }

}
