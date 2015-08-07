package Models;

public class Link {

    private String originalLink;

    private String shortenedLink;


    public String getOriginalLink(){
        return this.originalLink;
    }

    public String getShortenedLink(){
        return this.shortenedLink;
    }


    public void setOriginalLink(String originalLink){
        this.originalLink = originalLink;
    }

    public void setShortenedLink(String shortenedLink){
        this.shortenedLink = shortenedLink;
    }

}
