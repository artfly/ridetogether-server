package io.github.artfly.ridetogether.server;

import io.github.artfly.ridetogether.server.entities.Image;
import io.github.artfly.ridetogether.server.entities.User;
import io.github.artfly.ridetogether.server.repositories.ImageRepository;
import io.github.artfly.ridetogether.server.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RidetogetherServerApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;
    private static final String USER_PATH = "/users";
    private User user = new User("Arty", "12345", "42", "RockyRoad", "tricycle");
    private User dbUser = new User("Eu", "54321", "24", "SandyRoad", "forward");
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
    WebApplicationContext ctx;

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
        imageRepository.save(image);
        user.setImage(image);
        dbUser.setImage(image);
        dbUser.setId(userRepository.save(dbUser).getId());
    }

    @Test
    public void addUser() throws Exception {
        mockMvc.perform(post(USER_PATH)
                .content(json(user))
                .contentType(contentType))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.bike_model", is(user.getBikeModel())))
                .andExpect(jsonPath("$.route_type", is(user.getRouteType())))
                .andExpect(jsonPath("$.place_id", is(user.getPlaceId())));
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get(USER_PATH + "/" + dbUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username", is(dbUser.getUsername())))
                .andExpect(jsonPath("$.password", is(dbUser.getPassword())))
                .andExpect(jsonPath("$.bike_model", is(dbUser.getBikeModel())))
                .andExpect(jsonPath("$.route_type", is(dbUser.getRouteType())))
                .andExpect(jsonPath("$.place_id", is(dbUser.getPlaceId())));
    }

    @Test
    public void updateUser() throws Exception {
        dbUser.setRouteType("RockyRoad");
        mockMvc.perform(put(USER_PATH + "/" + dbUser.getId())
                        .content(json(dbUser))
                        .contentType(contentType))
                        .andExpect(status().isNoContent());

        mockMvc.perform(get(USER_PATH + "/" + dbUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.route_type", is(dbUser.getRouteType())));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
