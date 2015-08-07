package Requests;


import java.util.Map;

public class CreateLinkRequest {

    private String link;

    public CreateLinkRequest(Map<String,String> allRequestParams){
        this.link = allRequestParams.get("link");
    }

    public String getLink(){
        return this.link;
    }

}
