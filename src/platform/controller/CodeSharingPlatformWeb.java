package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import platform.model.CodeElement;
import platform.service.CodeService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class CodeSharingPlatformWeb {
    @Autowired
    CodeService codeService;

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")  // 404
    public class OrderNotFoundException extends RuntimeException {}

    @GetMapping(path ="/code/{someID}")
    public ModelAndView code(@PathVariable(value="someID") UUID id) {
        CodeElement code = codeService.getCodeById(id);
        ModelAndView model;
        if (code != null) {
            model = new ModelAndView("code.html", HttpStatus.OK);
            model.addObject("title", "Code");
            model.addObject("codes", Arrays.asList(code));
        } else {
            System.out.println("model NULL");
            throw new OrderNotFoundException();
        }
        return model;
    }

    @GetMapping(path ="/code/new")
    @ResponseBody
    public ModelAndView codeNewWeb() {
        ModelAndView model = new ModelAndView("codeNew.html");
        return model;
    }

    @GetMapping(path ="/code/latest")
    public ModelAndView codeLatest() {
        List<CodeElement> codes = codeService.getLast10Code();
        ModelAndView model = new ModelAndView("code.html");
        model.addObject("title", "Latest");
        model.addObject("codes", codes);
        return model;
    }
}
