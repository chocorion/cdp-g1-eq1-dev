package routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("hello")
public class Hello {
    @GET
    @Path("/world")
    @Produces("text/plain")
    public String helloWorld() {
        return "Hello world !";
    }
}
