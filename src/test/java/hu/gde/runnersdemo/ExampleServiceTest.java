package hu.gde.runnersdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExampleServiceTest {

    private ExampleService exampleService;

    @BeforeEach
    public void init() {
        exampleService = new ExampleService();
    }

    @Test
    void sumNumbers() {
        Long result = exampleService.sumNumbers(1L,2L,3L);
        assertEquals(6, result);
        result = exampleService.sumNumbers(1L,2L,3L);
        assertEquals(6, result);

    }
}