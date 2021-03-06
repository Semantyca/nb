package com.semantyca.skyra;

import com.semantyca.nb.core.rest.outgoing.Outcome;
import com.semantyca.nb.util.StringUtil;
import com.semantyca.skyra.modules.operator.model.Exploration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class KMLReaderTest {
    private static String BASE_SERVICE_URL = "http://localhost:8080/nb4/AviaTraker/explorations";
    private static List<Object> providers = new ArrayList<Object>();



    @Test
    public void addTestRoutes() throws Exception {
        List<String> data = new ArrayList();

        for (int i = 0; i < 100; i++) {
            data.add(StringUtil.getRndText());
        }

        for (String u: data) {
            boolean isNew = false;
            Response resp = WebClient.create(BASE_SERVICE_URL + "/" + u, providers)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            Map entity = null;
            int status = resp.getStatus();
            if (status == 200) {
                Outcome outcome = resp.readEntity(Outcome.class);
                try {
                    entity = (Map) outcome.getPayload().get(Outcome.ENTITY_PAYLOAD);
                } catch (Exception e) {

                }
            }

            Exploration dto = new Exploration();
            if (entity == null) {
                isNew = true;
            }
          //  ExplorationStatusDAO dao = new ExplorationStatusDAO();

            //  dto.setStatus(ListUtil.getRndElement(ExplorationStatus.values()));
            dto.setTitle(u);
            Response generalResp = null;
            if (isNew) {
                generalResp = WebClient.create(BASE_SERVICE_URL, providers)
                        .accept(MediaType.APPLICATION_JSON)
                        .type(MediaType.APPLICATION_JSON)
                        .post(dto, Response.class);
            } else {
                generalResp = WebClient.create(BASE_SERVICE_URL, providers)
                        .accept(MediaType.APPLICATION_JSON)
                        .put(dto, Response.class);
            }

            int generalRespStatus = generalResp.getStatus();
            assertEquals(200, generalRespStatus);
            if (generalRespStatus == 200) {
                Outcome generalOutcome = generalResp.readEntity(Outcome.class);
                assertNotNull(generalOutcome);
            }
        }
    }
}
