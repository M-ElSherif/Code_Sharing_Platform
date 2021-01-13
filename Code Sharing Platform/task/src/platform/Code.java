package platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code {

    private String code;
    private LocalDateTime date;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//    private int id;

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
        return date.format(FORMATTER);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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
