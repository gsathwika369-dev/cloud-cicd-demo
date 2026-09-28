package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreetingServiceTest {

    @Test
    void shouldReturnGreetingMessage() {
        GreetingService service = new GreetingService();

        String result = service.greet("Ammu");

        assertEquals("Hello, Ammu!", result);
    }
}
