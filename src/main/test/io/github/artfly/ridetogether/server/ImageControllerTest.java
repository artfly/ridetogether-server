package io.github.artfly.ridetogether.server;

import io.github.artfly.ridetogether.server.repository.ImageRepository;
import io.github.artfly.ridetogether.server.repository.entities.Image;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.nullValue;


public class ImageControllerTest extends BaseControllerTest {
    private static final String PICS_PATH = "/pics";
    private static final String IMAGE_NAME = "12345";
    private MockMultipartFile file = new MockMultipartFile("image", "image.png", "text/plain", "some image".getBytes());
    private BigDecimal longitude = new BigDecimal(12.42324723, new MathContext(11));
    private BigDecimal latitude = new BigDecimal(43.42992853, new MathContext(10));
    private Image image = new Image(IMAGE_NAME, latitude, longitude);
    @Autowired
    private ImageRepository imageRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        image.setImagePath(imageRepository.save(image).getImagePath());
        latitude.setScale(8, RoundingMode.HALF_UP);
        longitude.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    @Test
    public void loadImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.fileUpload(PICS_PATH)
                .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creator_id", is(nullValue())))
                .andExpect(jsonPath("$.longitude", is(nullValue())))
                .andExpect(jsonPath("$.latitude", is(nullValue())));
    }

//    @Test
//    public void getImageCoordinates() throws Exception {
//        mockMvc.perform(get(PICS_PATH + "/" + image.getImagePath() + "/coordinates"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$.image_path", is(image.getImagePath())))
//                .andExpect(jsonPath("$.creator_id", is(nullValue())))
//                .andExpect(jsonPath("$.longitude", is(longitude.toString())))
//                .andExpect(jsonPath("$.latitude", is(latitude)));
//    }

}
