package com.spring.car.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.recording.RecordSpec;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import com.spring.car.model.proxy.UsersResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class RecordPlaybackStubTest {
    @RegisterExtension
    WireMockExtension wireMockExtension = WireMockExtension
            .newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8084)
                    .extensions(new ResponseTemplateTransformer(false)))
            .build();

    @Test
    void recording_builder_test(){
        RecordSpec build = new RecordSpecBuilder()
                .forTarget("https://jsonplaceholder.typicode.com")
                .extractTextBodiesOver(6000)
                .makeStubsPersistent(true)
                .build();

        wireMockExtension.startRecording(build);

        String response = RestAssured.given().log().all()
                .baseUri("http://localhost:8084")
                .when()
                .get("/posts")
                .then()
                .extract()
                .response()
                .asString();

        System.out.println(response);
        wireMockExtension.stopRecording();

    }

    @Test
    void recording_followup_test(){
        //commented stub required if mapping is not available or persisted
//        wireMockExtension.stubFor(WireMock.get("/posts")
//                .willReturn(aResponse().withBodyFile("posts-f0fcfe2d-5974-4c41-9933-42d6d4079a0f.json")));

        String response = RestAssured.given().log().all()
                .baseUri("http://localhost:8084")
                .when()
                .get("/posts")
                .then().extract()
                .response().asString();
        System.out.println(response);
    }

    @Test
    void proxy_transformer_test(){
        wireMockExtension.stubFor(get("/api/users")
                .willReturn(aResponse().withStatus(200)
                        .proxiedFrom("{{request.headers.url}}")
                        .withTransformers("response-template")));
               // .persistent(true));


        UsersResponse usersResponse = RestAssured.given().log().all()
                .baseUri("http://localhost:8084")
                .header("url","https://reqres.in")
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(UsersResponse.class);

        Assertions.assertEquals(1,usersResponse.getPage());
    }
}
