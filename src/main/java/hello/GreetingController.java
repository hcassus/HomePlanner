package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Eae, %s!";
    private static final String formalTemplate = "Grato por estar em sua presença, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "*")
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/formalgreeting")
    public Greeting formalGreeting(@RequestParam(value="name", defaultValue="Lord or Madam") String name){
        return new Greeting(counter.incrementAndGet(), String.format(formalTemplate, name));
    }
}