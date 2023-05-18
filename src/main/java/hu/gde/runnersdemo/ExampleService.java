package hu.gde.runnersdemo;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public Long sumNumbers(Long a, Long b, Long c) {
        return a+b+c;
    }
}
