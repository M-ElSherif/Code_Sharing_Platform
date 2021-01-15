package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="CODEID")
    @JsonIgnore
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime date;

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

    public int getId() {
        return id;
    }

    public void setId(int codeID) {
        this.id = codeID;
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
