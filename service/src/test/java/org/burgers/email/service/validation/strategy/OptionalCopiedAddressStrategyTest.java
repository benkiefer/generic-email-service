package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.burgers.email.service.validation.ValidationContext;
import org.burgers.email.service.validation.rule.EmailAddressFormatRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(PowerMockRunner.class)
public class OptionalCopiedAddressStrategyTest {
    public static final String FIELD = "MY_FIELD";
    public static final String VALUE = "VALUE";

    @Mock
    private EmailTemplateRequest request;
    @Mock
    private ValidationContext context;
    @Mock
    private EmailAddressFormatRule rule;
    private SimpleOptionalStrategy strategy;

    @Before
    public void setup(){
        strategy = new SimpleOptionalStrategy();
        strategy.setEmailAddressFormatRule(rule);
    }

    @Test
    public void validate(){
        strategy.validate(request, context);
        Mockito.verify(rule).validate(VALUE, FIELD, context);
        Mockito.verifyNoMoreInteractions(rule);
    }

    protected class SimpleOptionalStrategy extends OptionalCopiedAddressStrategy {
        @Override
        protected List<String> getOptionalAddresses(EmailTemplateRequest templateRequest) {
            return asList(VALUE);
        }

        @Override
        protected String getField() {
            return FIELD;
        }
    }


}
