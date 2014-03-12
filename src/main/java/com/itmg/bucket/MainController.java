package com.itmg.bucket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by User on 12.03.14.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("/main")
    public String printWelcome() {
        return "hello";
    }
}
