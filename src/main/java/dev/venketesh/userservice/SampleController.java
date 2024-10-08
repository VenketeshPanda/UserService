package dev.venketesh.userservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//localhost:8080/sample
//All the API's for /sample endpoint should come to this controller.
@RequestMapping("/sample")
public class SampleController {

    //localhost:8080/sample/sayhello/Deepak
    @GetMapping("/sayhello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "Hello from User service" + name;
    }

    @GetMapping("/saybye")
    public String sayBye() {
        return "Bye Everyone from User service";
    }
}