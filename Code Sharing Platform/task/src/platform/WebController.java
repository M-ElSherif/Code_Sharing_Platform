package platform;

import freemarker.template.Template;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class WebController {


    private LocalDateTime localDateTime = LocalDateTime.now();

    private static String codeSnippet = "placeholder";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private ConfigManager cfg;
    private CodeStorageConfig storageConfig;
    private CodeService codeService;

    @Autowired
    public WebController(ConfigManager cfg, CodeStorageConfig storageConfig, CodeService codeService) {
        this.cfg = cfg;
        this.storageConfig = storageConfig;
        this.codeService = codeService;
    }

    public static void setCodeSnippet(String newCodeSnippet) {
        codeSnippet = newCodeSnippet;
    }

    @GetMapping(value = "/code/{N}")
    public ResponseEntity<String> getWebCode(@PathVariable int N) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

//        Code code = this.storageConfig.getCode(N);
        Optional<Code> code = this.codeService.getCodeById(N);

        Map root = new HashMap();
        root.put("code", code.get());
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

//        List<Code> codeList = this.storageConfig.getCodeLatest();
        List<Code> codeList = this.codeService.getLatestCode();

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


    @GetMapping(value="/api/code/latest", produces = "application/json")
    public ResponseEntity<List<Code>> getCodeLatest() {
//        List<Code> codeSnippets = this.storageConfig.getCodeLatest();
        List<Code> codeList = this.codeService.getLatestCode();

        return ResponseEntity.ok()
                .body(codeList);
    }

    @GetMapping(value = "/api/code/{N}", produces = "application/json")
    public ResponseEntity<Code> getCodeSnippet(@PathVariable int N) {
//        Code code = this.storageConfig.getCode(N);
        Optional<Code> code = this.codeService.getCodeById(N);

        return ResponseEntity.ok()
                .body(code.get());
    }

    @PostMapping(value = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> addCodeSnippet(@RequestBody Code code) {
        code.setDate(LocalDateTime.now());
//        this.storageConfig.addCode(code);
        this.codeService.addCode(code);

        return ResponseEntity.ok()
                .body("{ \"id\": \"" + code.getId() + "\"}");

    }

}
