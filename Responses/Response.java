package Responses;

public abstract class Response {

    private final String status;
    private final String message;


    public Response(String status, String message){
        this.status = status;
        this.message = message;
    }

    public String getStatus(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }
}
