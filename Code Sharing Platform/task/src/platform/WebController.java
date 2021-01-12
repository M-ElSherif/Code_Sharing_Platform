package platform;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.management.MXBean;

@RestController
public class WebController {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime localDateTime = LocalDateTime.now();
    private List<Code> codeSnippets = new ArrayList<Code>();
    private static String codeSnippet= "placeholder";

    @Bean
    public FreeMarkerConfigurer freeMarkerConfig() throws IOException {
        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        try {
            cfg.setDirectoryForTemplateLoading(new File("/resources"));
            // Recommended settings for new projects:
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
        } catch (IOException e) {
            System.out.println("Error finding Freemarker templates directory");
        }
        return null;
    }



    public static void setCodeSnippet(String newCodeSnippet) {
        codeSnippet = newCodeSnippet;
    }

//    @GetMapping(value="/code")
//    public ResponseEntity<String> getWebCode() {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.TEXT_HTML);
//        String htmlFileContent = "";
//
//        this.date = LocalDateTime.now();
//
//        try {
//            File resource = new ClassPathResource("codeDisplay.ftlh").getFile();
//            htmlFileContent = new String(Files.readAllBytes(resource.toPath()));
//            htmlFileContent = htmlFileContent.replace("{date}", this.date.format(FORMATTER));
//            htmlFileContent = htmlFileContent.replace("{code_snippet}", codeSnippet);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body(htmlFileContent);
//    }

    @GetMapping(value="/code/{N}")
    public ResponseEntity<String> getWebCode(@PathVariable int N) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);
        String htmlFileContent = "";

        this.localDateTime = LocalDateTime.now();


        /* Create a data-model */
        Map root = new HashMap();
        Code code = this.codeSnippets.get(N-1);
        root.put("code", code);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test.ftlh");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);

        try {
            File resource = new ClassPathResource("codeDisplay.ftlh").getFile();
            htmlFileContent = new String(Files.readAllBytes(resource.toPath()));
            htmlFileContent = htmlFileContent.replace("{date}", this.localDateTime.format(FORMATTER));
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
            File resource = new ClassPathResource("codeEdit.ftlh").getFile();
            htmlFileContent = new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(htmlFileContent);
    }

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

//    @PostMapping(value="/api/code/new", consumes = "application/json")
//    public ResponseEntity<Code> setCode(@RequestBody Code code) {
//        codeSnippet = code.getCode();
//        WebController.setCodeSnippet(codeSnippet);
//        date = LocalDateTime.now();
//
//        return ResponseEntity.ok()
//                .body(new Code());
//    }

    @GetMapping(value="/api/code/{N}", produces ="application/json")
    public ResponseEntity<Code> getCodeSnippet(@PathVariable int N) {
        Code code = this.codeSnippets.get(N-1);

        return ResponseEntity.ok()
                .body(code);
    }

    @PostMapping(value = "/api/code/new", consumes = "application/json")
    public ResponseEntity<JSONObject> addCodeSnippet(@RequestBody Code code) {
        code.setDate(LocalDateTime.now());
        this.codeSnippets.add(code);
        int index = this.codeSnippets.indexOf(code);

        JSONObject id = new JSONObject();
        id.put("id",index+1);

        return ResponseEntity.ok()
                .body(id);
    }

    public Code getNthCode(int N) {
        Code nthCode = null;
        for (Code code : this.codeSnippets) {
            if (code.getId() == N) {
                nthCode = code;
            }
        }
        return nthCode;
    }

}
