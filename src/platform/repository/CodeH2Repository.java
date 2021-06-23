package platform.repository;

import org.springframework.data.repository.CrudRepository;
import platform.model.CodeElement;
import java.util.List;
import java.util.UUID;

public interface CodeH2Repository extends CrudRepository<CodeElement, String> {
    CodeElement findById(UUID id);
    void deleteById(UUID id);
    CodeElement save(CodeElement code);
    List<CodeElement> findFirst10ByLifeTimeLessThanEqualAndViewsLessThanEqualOrderByCreateDateDesc(Integer  time, Integer  views);
}
