package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Code getCodeById(UUID id) {
        Optional<Code> code = this.codeRepository.findByUuid(id);
        if (code.isPresent()) {
            if (code.get().isViewRestricted()) {
                this.decrementCodeView(code.get());
            }
            return code.get();
        }
        return null;
    }


    public List<Code> getLatestCode() {
        List<Code> codeListFinal = new ArrayList<>();

        var temp = this.codeRepository.findTop10LatestCode();
        temp.forEach(code -> codeListFinal.add(code));

        return codeListFinal;
    }

    public void addCode(Code code) {
        UUID uuid = UUID.randomUUID();
        code.setUuid(uuid);
        if (code.getTime() == 0 || code.getTime() < 0) {
            code.setTime(0);
        }
        if (code.getViews() == 0 || code.getViews() < 0) {
            code.setViews(0);
        }
        if (code.getViews() > 0) {
            code.setViewRestricted(true);
        }
        if (code.getTime() > 0) {
            code.setTimeRestricted(true);
        }
        this.codeRepository.save(code);
    }

    @Transactional
    public void deleteCodeById(UUID uuid) {
        this.codeRepository.deleteByUuid(uuid);
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

    // Support method
    public boolean isCodeRestricted(Code code) {
        Optional<Code> optCode = Optional.ofNullable(code);
        if (optCode.isPresent()) {
            if (code.isTimeRestricted() && code.isViewRestricted()) {
                if (code.getTime() > 0 && code.getViews() > 0) {
                    return false;
                } else {
                    this.deleteCodeById(code.getUuid());
                    return true;
                }
            } else if (code.isTimeRestricted() && !code.isViewRestricted()) {
                if (code.getTime() > 0) {
                    return false;
                } else {
                    this.deleteCodeById(code.getUuid());
                    return true;
                }
            } else if (code.isViewRestricted() && !code.isTimeRestricted()) {
                if (code.getViews() > 0) {
                    return false;
                } else {
                    this.deleteCodeById(code.getUuid());
                    return true;
                }
            } else if (!code.isTimeRestricted() && !code.isViewRestricted()){
                return false;
            }
        }
        return false;
    }

}
