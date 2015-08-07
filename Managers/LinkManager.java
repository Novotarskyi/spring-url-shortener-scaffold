package Managers;

import Models.Link;
import Requests.CreateLinkRequest;
import Services.DatabaseService;
import Services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkManager {

    @Autowired
    ShortenerService shortenerService;

    @Autowired
    DatabaseService dbService;

    public Link createLink(CreateLinkRequest request){

        Link link = new Link();

        link.setOriginalLink(request.getLink());
        link.setShortenedLink(shortenerService.shortenLink(request.getLink()));
        dbService.storeLinkToDb(link);

        return link;
    }

    public String getUrl(String link){
        return this.dbService.getLinkFromDb(link);
    }

    public List getLinkList(){
        return this.dbService.getLinkListFromDb();
    }

}
