package org.burgers.email.service.validation.strategy;

import org.burgers.email.client.EmailTemplateRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CcAddressStrategyTest {
    @Mock
    private EmailTemplateRequest request;
    @InjectMocks
    private CcAddressStrategy ccAddressStrategy;

    @Test
    public void getField(){
        assertEquals("CC", ccAddressStrategy.getField());
    }

    @Test
    public void getOptionalAddresses(){
        List<String> ccs = asList("ccaddress");
        Mockito.when(request.getCc()).thenReturn(ccs);
        assertEquals(ccs, ccAddressStrategy.getOptionalAddresses(request));
    }


}
