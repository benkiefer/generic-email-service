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
public class BccAddressStrategyTest {
    @Mock
    private EmailTemplateRequest request;
    @InjectMocks
    private BccAddressStrategy bccAddressStrategy;

    @Test
    public void getField(){
        assertEquals("BCC", bccAddressStrategy.getField());
    }
    
    @Test
    public void getOptionalAddresses(){
        List<String> bccs = asList("bccaddress");
        Mockito.when(request.getBcc()).thenReturn(bccs);
        assertEquals(bccs, bccAddressStrategy.getOptionalAddresses(request));
    }


}
