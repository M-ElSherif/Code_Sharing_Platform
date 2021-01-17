package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    private CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public List<Code> findAll() {
        List<Code> codeList = new ArrayList<>();
        var temp = this.codeRepository.findAll();

        temp.forEach(code -> codeList.add(code));
        return codeList;
    }

    public Optional<Code> getCodeById(UUID id) {
        return this.codeRepository.findByUuid(id);
    }

    public List<Code> getLatestCode() {
        List<Code> codeListTemp = new ArrayList<>();
        List<Code> codeListFinal = new ArrayList<>();

        var temp = this.codeRepository.findTop10LatestCode();
        temp.forEach(code -> codeListTemp.add(code));

        codeListTemp.forEach(code -> {
            if (code.getTime() == 0 && code.getViews() == 0) {
                codeListFinal.add(code);
            }
        });

        return codeListFinal;
    }

    public void addCode(Code code) {
        UUID uuid = UUID.randomUUID();
        code.setUuid(uuid);
        this.codeRepository.save(code);
    }

    public void deleteCodeById(int id) {
        this.codeRepository.deleteById(id);
    }

    public String getCodeId(Code code) {
        Code c = this.codeRepository.findCodeByCode(code).get();
        return c.getUuid().toString();
    }

    public void decrementCodeView(Code code) {
        if (code.getViews() > 0) {
            code.setViews(code.getViews() - 1);
            this.codeRepository.save(code);
        }
    }

    public void decrementCodeTime(Code code, long remaining) {
        if (code.getTime() > 0) {
            code.setTime(remaining);
            this.codeRepository.save(code);
        }
    }

    // Support method
    public boolean isCodeUnRestricted(Code code) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime timeCreated = LocalDateTime.parse(code.getDate(), FORMATTER);
        Duration duration = Duration.between(timeCreated, LocalDateTime.now());
        long seconds = duration.getSeconds();

        this.decrementCodeTime(code, code.getTime() - seconds);

        return seconds < code.getTime() && code.getViews() > 0;
    }

}
