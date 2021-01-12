package platform;

import org.springframework.stereotype.Component;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConfigManager {

    private Configuration cfg;

    public ConfigManager() {
        this.cfg = new Configuration(Configuration.VERSION_2_3_29);
        try {
            cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\thesh\\Documents\\Repositories\\Code Sharing Platform\\Code Sharing Platform\\task\\src\\resources"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Recommended settings for new projects:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    public Template getTemplate(String template) {
        try {
            return cfg.getTemplate(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
