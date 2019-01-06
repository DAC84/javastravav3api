package javastrava.service;

import javastrava.api.API;
import javastrava.auth.model.Token;
import javastrava.service.ActivityService;
import javastrava.service.impl.ActivityServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ActivityServiceTest {

    private API api = mock(API.class);
    private ActivityService activityService = new ActivityServiceImpl(api);
    private Token token = mock(Token.class);

    @Before
    public void init(){
        when(api.getToken()).thenReturn(token);
    }

    @Test
    public void testGetActivity(){
        Long id = 1L;
        activityService.getActivity(id);
        verify(api.getActivity(eq(id), any()));
    }



}
