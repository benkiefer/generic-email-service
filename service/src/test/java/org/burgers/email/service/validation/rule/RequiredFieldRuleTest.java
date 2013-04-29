package org.burgers.email.service.validation.rule;

import org.burgers.email.service.validation.ValidationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RequiredFieldRuleTest {
    public static final String FIELD = "myField";
    @InjectMocks
    private RequiredFieldRule rule;
    @Mock
    private ValidationContext context;

    @Test
    public void validate_collection_valid(){
        assertTrue(rule.validateAtLeastOne(asList("hi"), FIELD, context));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void collection_empty_list(){
        assertFalse(rule.validateAtLeastOne(new ArrayList(), FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void collection_empty_set(){
        assertFalse(rule.validateAtLeastOne(new HashSet(), FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void collection_null(){
        assertFalse(rule.validateAtLeastOne(null, FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void collection_valid_set(){
        Set<String> set = new HashSet<String>();
        set.add("hi");
        assertTrue(rule.validateAtLeastOne(set, FIELD, context));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_valid_string(){
        assertTrue(rule.validate("hi", FIELD, context));
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_blank_string_value(){
        assertFalse(rule.validate("", FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_spaces(){
        assertFalse(rule.validate("    ", FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void validate_null(){
        assertFalse(rule.validate(null, FIELD, context));
        verify(context).error(FIELD, RequiredFieldRule.MESSAGE);
        verifyNoMoreInteractions(context);
    }





}
