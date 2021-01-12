package platform;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CodeStorageConfig {

    private List<Code> codeSnippets;
    private int codeIDCounter;

    public CodeStorageConfig() {
        this.codeSnippets = new ArrayList<>();
        this.codeIDCounter = 0;
    }

    public void addCode(Code code) {
        this.codeIDCounter++;
        code.setId(this.codeIDCounter);
        this.codeSnippets.add(code);
    }

    public Code getCode(int n) {
        return this.codeSnippets.get(n-1);
    }

    public List<Code> getCodeLatest() {
        return this.codeSnippets.subList(0,this.codeSnippets.size());
    }

}
