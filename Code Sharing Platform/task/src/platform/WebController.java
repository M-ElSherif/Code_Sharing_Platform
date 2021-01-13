package platform;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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


    private LocalDateTime localDateTime = LocalDateTime.now();

    private static String codeSnippet = "placeholder";

    private ConfigManager cfg;
    private CodeStorageConfig storageConfig;

    @Autowired
    public WebController(ConfigManager cfg, CodeStorageConfig storageConfig) {
        this.cfg = cfg;
        this.storageConfig = storageConfig;
    }

    public static void setCodeSnippet(String newCodeSnippet) {
        codeSnippet = newCodeSnippet;
    }

    @GetMapping(value = "/code/{N}")
    public ResponseEntity<String> getWebCode(@PathVariable int N) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        Code code = this.storageConfig.getCode(N);

        Map root = new HashMap();
        root.put("code", code);
        Template template = cfg.getTemplate("codeDisplay.ftlh");
        StringWriter out = new StringWriter();

        try {
            template.process(root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(out.getBuffer().toString());
    }

    @GetMapping("/code/latest")
    public ResponseEntity<String> getWebCodeLatest() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        List<Code> codeList = this.storageConfig.getCodeLatest();

        Map root = new HashMap();
        root.put("codeList", codeList);
        Template template = cfg.getTemplate("codeDisplayLatest.ftlh");
        StringWriter out = new StringWriter();

        try {
            template.process(root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(out.getBuffer().toString());
    }

    @GetMapping("/code/new")
    public ResponseEntity<String> submitNewCode() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        Map root= new HashMap();
        Template template = cfg.getTemplate("codeEdit.ftlh");
        StringWriter out = new StringWriter();
        try {
            template.process(root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(out.getBuffer().toString());
    }


    @GetMapping("/api/code/latest")
    public ResponseEntity<List<Code>> getCodeLatest() {
        List<Code> codeSnippets = this.storageConfig.getCodeLatest();

        return ResponseEntity.ok()
                .body(codeSnippets);
    }

    @GetMapping(value = "/api/code/{N}", produces = "application/json")
    public ResponseEntity<Code> getCodeSnippet(@PathVariable int N) {
        Code code = this.storageConfig.getCode(N);

        return ResponseEntity.ok()
                .body(code);
    }

    @PostMapping(value = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> addCodeSnippet(@RequestBody Code code) {
        code.setDate(LocalDateTime.now());
        this.storageConfig.addCode(code);

        return ResponseEntity.ok()
                .body("{ \"id\": \"" + this.storageConfig.getSize() + "\"}");

    }

}
