//package platform;
//
//import org.apache.tomcat.jni.Local;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.swing.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@RestController
//public class ApiController {
//
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private LocalDateTime date = LocalDateTime.now();
//
//    private static String codeSnippet = "placeholder";
//
//    @GetMapping("/api/code")
//    public ResponseEntity<Code> getContent() {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        Code code = new Code();
//        code.setCode(codeSnippet);
//        code.setDate(this.date);
//
//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body(code);
//    }
//
//    @PostMapping(value="/api/code/new", consumes = "application/json")
//    public ResponseEntity<Code> setCode(@RequestBody Code code) {
//        codeSnippet = code.getCode();
//        WebController.setCodeSnippet(codeSnippet);
//        date = LocalDateTime.now();
//
//        return ResponseEntity.ok()
//                .body(new Code());
//    }
//}
