package platform.repository;

import org.springframework.stereotype.Component;
import platform.model.CodeElement;

import java.util.LinkedList;
import java.util.List;

@Component
public class CodeCollection {
    private List<CodeElement> repo = new LinkedList<>();

    public CodeCollection() {
        CodeElement snippet = new CodeElement();
    }

    public int addSnippet(CodeElement snippet) {
        repo.add(snippet);
        return repo.size()-1;
    }

    public List<CodeElement> getSnippets() {
        return repo;
    }

    public List<CodeElement> getLast10Snippets() {
        List<CodeElement> result = new LinkedList<>();
        for (int i = repo.size()-1, j=0; j < 10 && i >= 0; i--, j++) {
            result.add(repo.get(i));
        }
        return result;
    }
}
