package io.github.artfly.ridetogether.server;

import io.github.artfly.ridetogether.server.exceptions.NotFoundException;
import io.github.artfly.ridetogether.server.repositories.ImageRepository;
import io.github.artfly.ridetogether.server.entities.Image;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RidetogetherServerApplication.class)
@WebAppConfiguration
public class ImageControllerTest {
    private MockMvc mockMvc;
    private static final String IMAGE_PATH = "/images";
    private static final String IMAGE_NAME = "test";
    private BigDecimal longitude = new BigDecimal(12.42324723, new MathContext(11));
    private BigDecimal latitude = new BigDecimal(43.42992853, new MathContext(10));
    private Image image = new Image("other", longitude, latitude);
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
    Charset.forName("utf8"));
    private MockMultipartFile file = new MockMultipartFile("image", "image.png", "text/plain", "some image".getBytes());

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
        image.setImagePath(imageRepository.save(new Image(IMAGE_NAME)).getImagePath());
        latitude.setScale(8, RoundingMode.HALF_UP);
        longitude.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    @Test
    public void loadImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.fileUpload(IMAGE_PATH)
                .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.longitude", is(nullValue())))
                .andExpect(jsonPath("$.latitude", is(nullValue())));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Test
    public void readImageCoordinates() throws Exception {
        mockMvc.perform(get(IMAGE_PATH + "/" + IMAGE_NAME + "/coordinates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.image_path", is(IMAGE_NAME)))
                .andExpect(jsonPath("$.longitude", is(nullValue())))
                .andExpect(jsonPath("$.latitude", is(nullValue())));
    }

    @Test
    public void updateImage() throws Exception {
        latitude.negate();
        image.setLatitude(latitude);
        mockMvc.perform(put(IMAGE_PATH + "/" + image.getImagePath())
                .content(this.json(image))
                .contentType(contentType))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(IMAGE_PATH + "/" + IMAGE_NAME + "/coordinates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.longitude", is(image.getLongitude().doubleValue())))
                .andExpect(jsonPath("$.latitude", is(image.getLatitude().doubleValue())));
    }
}
