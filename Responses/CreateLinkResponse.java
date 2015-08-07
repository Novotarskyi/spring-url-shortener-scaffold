package Responses;


public class CreateLinkResponse extends Response {

    private String originalLink;

    private String shortenedLink;

    public CreateLinkResponse(String status, String message, String originalLink, String shortenedLink){
        super(status, message);
        this.originalLink = originalLink;
        this.shortenedLink = shortenedLink;
    }

    public String getOriginalLink(){
        return this.originalLink;
    }

    public String getShortenedLink(){
        return "http://localhost:8080/r/"+this.shortenedLink;
    }


}
