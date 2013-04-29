package org.burgers.email.service.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SimpleValidationContextTest {
    public static final String FIELD = "field";
    @InjectMocks
    private SimpleValidationContext context;

    @Test
    public void hasErrors_false(){
        assertFalse(context.hasErrors());
    }

    @Test
    public void hasErrors_true(){
        context.error(FIELD, "boom1");
        context.error(FIELD, "boom2");

        assertTrue(context.hasErrors());

        assertTrue(context.getFieldsInError().contains(FIELD));
        assertEquals(2, context.getErrorsForField(FIELD).size());
        assertTrue(context.getErrorsForField(FIELD).contains("boom1"));
        assertTrue(context.getErrorsForField(FIELD).contains("boom2"));
        assertTrue(context.fieldHasErrors(FIELD));
    }

}
