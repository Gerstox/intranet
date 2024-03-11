package business.intranet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
public class ProductJsonTest {

    @Autowired
    private JacksonTester<Product> json;

    @Test
    void productSerializationTest() throws IOException {
        Product product = new Product(1L, "Chilena de jamón y queso", "Chilenas", 1.7);

        assertThat(json.write(product)).isStrictlyEqualToJson("expected.json");

        assertThat(json.write(product)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(product)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);

        assertThat(json.write(product)).hasJsonPathStringValue("@.name");
        assertThat(json.write(product)).extractingJsonPathStringValue("@.name")
                .isEqualTo("Chilena de jamón y queso");

        assertThat(json.write(product)).hasJsonPathStringValue("@.category");
        assertThat(json.write(product)).extractingJsonPathStringValue("@.category")
                .isEqualTo("Chilenas");

        assertThat(json.write(product)).hasJsonPathNumberValue("@.price");
        assertThat(json.write(product)).extractingJsonPathNumberValue("@.price")
                .isEqualTo(1.7);
    }

    @Test
    void productDeserializationtest() throws IOException {
        String expected = """
                {
                    "id": 1,
                    "name": "Chilena de jamón y queso",
                    "category": "Chilenas",
                    "price": 1.7
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new Product(1L, "Chilena de jamón y queso", "Chilenas", 1.7));
        assertThat(json.parseObject(expected).id()).isEqualTo(1);
        assertThat(json.parseObject(expected).name()).isEqualTo("Chilena de jamón y queso");
        assertThat(json.parseObject(expected).category()).isEqualTo("Chilenas");
        assertThat(json.parseObject(expected).price()).isEqualTo(1.7);
    }

}
