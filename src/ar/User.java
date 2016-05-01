package ar;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
/**
* 
* @author ar
* 
*/

@Path("tt")
public class User {

	@GET
	@Path("/name/{i}")
	@Produces(MediaType.TEXT_XML)
	
	public String userName(@PathParam("i") String i) {
	 
		String name = i;
		return "<User>" + "<Name>" + name+ "arar" + "</Name>" + "</User>";
	}
	 
	@GET
	@Path("/age/{j}") 
	@Produces(MediaType.TEXT_XML)
	public String userAge(@PathParam("j") int j) {
	 
		int age = j;
		return "<User>" + "<Age>" + age + "</Age>" + "</User>";
	}
}
