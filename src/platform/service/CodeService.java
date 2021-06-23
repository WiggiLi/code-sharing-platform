package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.model.CodeElement;
import platform.repository.CodeH2Repository;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class CodeService {
    @Autowired
    CodeH2Repository studentRepository;

    //getting last 10 student records
    public List<CodeElement> getLast10Code()
    {
        List<CodeElement> allowedCodes = new ArrayList();
        studentRepository.findFirst10ByLifeTimeLessThanEqualAndViewsLessThanEqualOrderByCreateDateDesc(0,0).forEach(student -> {
            if (student.getLifeTime() <= 0 && student.getViews() <= 0) allowedCodes.add(student);
        });
        return allowedCodes;
    }

    //getting a specific record
    public CodeElement getCodeById(UUID id)
    {
        CodeElement code = studentRepository.findById(id);
        if (code == null){
            System.out.println("CODE NULL");
            return null;
        }
        //check of restrictions
        int currentLifeTime = code.getLifeTime();
        int currentViews = code.getViews();

        boolean isDeleate = false;

        if (currentLifeTime > 0){
            LocalDateTime end = LocalDateTime.now();
            String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            Duration timeElapsed = Duration.between(LocalDateTime.parse(code.getDate(), formatter), end);
            boolean checkTime =  timeElapsed.getSeconds() < currentLifeTime;
            if (checkTime)
                code.setLifeTime(currentLifeTime - (int)timeElapsed.getSeconds());
            else
                isDeleate = true;
        }
        if (code.isViewLimit()) {
            if (currentViews > 0)
                code.setViews(currentViews - 1);
            else
                isDeleate = true;
        }
        if (isDeleate){
            studentRepository.deleteById(code.getID());
            return null;
        } else{
            studentRepository.save(code);
            return code;
        }
    }

    public UUID save(CodeElement student)
    {
        CodeElement code = studentRepository.save(student);
        return code.getID();
    }
}
