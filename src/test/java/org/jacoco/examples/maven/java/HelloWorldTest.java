package org.jacoco.examples.maven.java;

import static org.junit.Assert.*;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testGetMessage() {
        HelloWorld hello = new HelloWorld();
        assertEquals("Hello World!", hello.getMessage(false));
        assertEquals("Hello Universe!", hello.getMessage(true));
    }
}

