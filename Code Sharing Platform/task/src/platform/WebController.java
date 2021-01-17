package platform;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public WebController(ConfigManager cfg, CodeService codeService) {
        this.cfg = cfg;
        this.codeService = codeService;
    }

    public static void setCodeSnippet(String newCodeSnippet) {
        codeSnippet = newCodeSnippet;
    }

    @GetMapping(value = "/code/{UUID}")
    public ResponseEntity<String> getWebCode(@PathVariable UUID UUID) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        Optional<Code> code = this.codeService.getCodeById(UUID);

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

        Map root = new HashMap();
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

    @GetMapping(value = "/api/code/latest", produces = "application/json")
    public ResponseEntity<List<Code>> getCodeLatest() {
        List<Code> codeList = new ArrayList<>();
        codeList = this.codeService.getLatestCode();

        return ResponseEntity.ok()
                .body(codeList);
    }

    @GetMapping(value = "/api/code/{UUID}", produces = "application/json")
    public ResponseEntity<Code> getCodeSnippet(@PathVariable UUID UUID) {
        Code code = this.codeService.getCodeById(UUID).get();

        this.codeService.decrementCodeView(code);

        if (this.codeService.isCodeUnRestricted(code)) {
            return ResponseEntity.ok()
                    .body(code);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> addCodeSnippet(@RequestBody Code code) {
        code.setDate(LocalDateTime.now());
        if (code.getTime() == 0 || code.getTime() < 0) {
            code.setTime(0);
        }
        if (code.getViews() == 0 || code.getViews() < 0) {
            code.setViews(0);
        }
        this.codeService.addCode(code);

        return ResponseEntity.ok()
                .body("{ \"id\": \"" + code.getUuid().toString() + "\"}");

    }

}
