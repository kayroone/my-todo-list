package de.jwiegmann.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TodosApiControllerTest {

    /*@LocalServerPort
    private int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void createTodo() throws JSONException, JsonProcessingException {

        // 1. Arrange:
        JSONObject requestBody = new JSONObject();

        requestBody.put("description", "foo");
        requestBody.put("done", false);
        requestBody.put("dueDate", new Date());
        requestBody.put("title", new Date());

        // 2. Action
        JSONObject responseBody = given().body(requestBody).when().post("/todos").then().statusCode(HttpStatus.SC_CREATED)
            .extract().body().as(JSONObject.class);

        String responseTodoId = responseBody.getString("id");

        // 3. Assert
        TodoFull responseTodo = given().when().get("/todos/" + responseTodoId).then().statusCode(HttpStatus.SC_OK)
            .assertThat().extract().as(TodoFull.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(responseTodo);
    }*/
}