package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService{

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

    public Optional<Code> getCodeById(int id) {
        return this.codeRepository.findById(id);
    }

    public List<Code> getLatestCode() {
        List<Code> codeList = new ArrayList<>();
        var temp = this.codeRepository.findTop10LatestCode();

        temp.forEach(code -> codeList.add(code));
        return codeList;
    }

    public void addCode(Code code) {
        this.codeRepository.save(code);
    }

    public void deleteCodeById(int id) {
        this.codeRepository.deleteById(id);
    }

    public int getCodeId(Code code) {
        Code c = this.codeRepository.findCodeByCode(code);
        return c.getId();
    }
}
