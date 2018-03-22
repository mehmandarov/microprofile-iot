package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionWrapperTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void wrapsExceptionInRuntimeExceptionAndKeepsTheOriginalExceptionAsCause() {
        IOException ioException = new IOException();

        expectedException.expect(RuntimeException.class);
        expectedException.expectCause(is(ioException));
        ExceptionWrapper.wrapExceptions(() -> { throw ioException; });
    }

    @Test
    public void returnsValueAsNormalIfNoException() {
        assertThat(ExceptionWrapper.wrapExceptions(() -> "a"), is("a"));
    }
}