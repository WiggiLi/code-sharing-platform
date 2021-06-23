package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.model.CodeElement;
import platform.service.CodeService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CodeSharingPlatformApi {
    @Autowired
    CodeService codeService;

    @GetMapping(path ="/api/code/{someID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetApiCode(@PathVariable(value="someID") UUID id) {
        CodeElement code = codeService.getCodeById(id);
        if (code != null) {
            return new ResponseEntity<>(code, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path ="/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> PostApiCode(@RequestBody CodeElement code) {
        code.setDate();
        code.setCreateDate();
        code.setViewLimit(code.getViews() > 0);
        UUID id = codeService.save(code);
        return Map.of("id", id.toString());
    }

    @GetMapping(path ="/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeElement> GetApiCodeLatest() {
        return codeService.getLast10Code();
    }
}
