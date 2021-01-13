package platform;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
        this.codeSnippets.add(code);
    }

    public Code getCode(int n) {
        return this.codeSnippets.get(n - 1);
    }

    public List<Code> getCodeLatest() {
        List<Code> latestCodes = new ArrayList<>();
        int i = 0;
        while (i < 10 && i < this.codeSnippets.size()) {
            latestCodes.add(this.codeSnippets.get(this.codeSnippets.size() - 1 - i));
            i++;
        }
        return latestCodes;
    }

    public int getSize() {
        return this.codeSnippets.size();
    }

}
