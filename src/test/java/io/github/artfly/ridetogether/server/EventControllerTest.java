package io.github.artfly.ridetogether.server;

import io.github.artfly.ridetogether.server.entities.Event;
import io.github.artfly.ridetogether.server.entities.Image;
import io.github.artfly.ridetogether.server.entities.User;
import io.github.artfly.ridetogether.server.repositories.EventRepository;
import io.github.artfly.ridetogether.server.repositories.ImageRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class EventControllerTest {
    private MockMvc mockMvc;
    private static final String EVENT_PATH = "/events";
    private User user = new User("Arty", "12345", "42", "RockyRoad", "tricycle");
    private Event event = new Event("Hello", "World", 2435345L);
    private Image image = new Image("123", BigDecimal.ONE, BigDecimal.ONE);
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(ctx).build();
//        event.setImage(image);
//        event.
//        eventRepository.save(event);
//        user.setImage(image);
//        dbUser.setImage(image);
//        dbUser.setId(userRepository.save(dbUser).getId());
    }
}
