package nl.novi.jnoldenfacturatie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class JNoldenFacturatieIntegratieTesten {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCorrectlyCreateFactuur() throws Exception {
        String jsonBody = """
                {
                    "factuurDatum": "2024-01-01",
                        "betaalDatum": "2024-01-01",
                        "klantId": -1,
                        "kortingPercentage": 15,
                        "orderRegels": [
                            {
                                "artikelId": -1,
                                "aantal": 1
                            },
                            {
                                "artikelId": -1,
                                "aantal": 2
                            }
                        ]
                }
                """;

        MvcResult respons = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/facturen")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String factuurnummer = respons.getResponse().getContentAsString().substring(15);
        assertThat(respons.getResponse().getContentAsString(), matchesPattern("Factuurnummer: [012]"));

    }

    @Test
    void shouldReturnCorrectFactuur() throws Exception {
        String jsonBody = """
                {
                    "factuurDatum": "2024-01-01",
                        "betaalDatum": "2024-01-01",
                        "klantId": -1,
                        "kortingPercentage": 15,
                        "orderRegels": [
                            {
                                "artikelId": -1,
                                "aantal": 1
                            }
                        ]
                }
                """;

        MvcResult respons = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/facturen")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String factuurnummer = respons.getResponse().getContentAsString().substring(15);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/facturen/" + factuurnummer))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.factuurKlant.voornaam", is("Tester")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subTotaal", is(10.85)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totaalPrijs", is(11.16)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.factuurNummer", is(Integer.parseInt(factuurnummer))));
    }

}
