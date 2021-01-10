package platform;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class WebController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime date = LocalDateTime.now();

    private static String codeSnippet= "placeholder";

    public static void setCodeSnippet(String newCodeSnippet) {
        codeSnippet = newCodeSnippet;
    }

    @GetMapping(value="/code")
    public ResponseEntity<String> getWebCode() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);
        String htmlFileContent = "";

        this.date = LocalDateTime.now();

        try {
            File resource = new ClassPathResource("codeDisplay.html").getFile();
            htmlFileContent = new String(Files.readAllBytes(resource.toPath()));
            htmlFileContent = htmlFileContent.replace("{date}", this.date.format(FORMATTER));
            htmlFileContent = htmlFileContent.replace("{code_snippet}", codeSnippet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(htmlFileContent);
    }

    @GetMapping("/code/new")
    public ResponseEntity<String> submitNewCode() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);
        String htmlFileContent = "";
        try {
            File resource = new ClassPathResource("codeEdit.html").getFile();
            htmlFileContent = new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(htmlFileContent);
    }

}
