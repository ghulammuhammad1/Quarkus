package org.acme.getting.started;

import java.util.ArrayList;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class Controller {
    @Inject
    Repo repo;
    public static ArrayList<Student> students=new ArrayList<Student>();
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World";
    }
    
    @GET
    @Path("/getPersonById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id){
        
        return Response.ok(repo.getStudent(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void setStudent(Student student){
        repo.setStudent(student);
    }

    @GET
    @Path("/AllStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Student> getAllStudents(){
        students = repo.getAllStudents();
        
        return students;
    }

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public void update(Student std){
        repo.update(std);
    }

    @DELETE
    @Path("/Delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id){
        repo.delete(id);
        return Response.ok(repo.getAllStudents()).build();
    }
}