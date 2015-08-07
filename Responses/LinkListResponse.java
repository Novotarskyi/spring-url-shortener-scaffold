package Responses;


import java.util.List;

public class LinkListResponse extends Response {

    private List linkList;


    public LinkListResponse(String status, String message, List linkList){
        super(status, message);
        this.linkList = linkList;
    }

    public List getLinkList(){
        return this.linkList;
    }
}
