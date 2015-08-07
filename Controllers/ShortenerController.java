package Controllers;


import Managers.LinkManager;
import Models.Link;
import Requests.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import Responses.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ShortenerController {

    @Autowired
    LinkManager manager;

    @RequestMapping(value = "/createLink", method = RequestMethod.GET)
    public CreateLinkResponse createLink(@RequestParam Map<String,String> allRequestParams) {

            CreateLinkRequest request = new CreateLinkRequest(allRequestParams);
            Link link = this.manager.createLink(request);

			return new CreateLinkResponse("OK", "Done", link.getOriginalLink(), link.getShortenedLink());
        }

    @RequestMapping(value = "/r/{link}", method = RequestMethod.GET)
    public void redirect(@PathVariable String link, HttpServletResponse resp) throws Exception {
        String url = this.manager.getUrl(link);
        if (url != null)
            resp.sendRedirect(url);
        else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public LinkListResponse getLinks() {


        List linkList = this.manager.getLinkList();

        return new LinkListResponse("OK", "Done", linkList);
    }

}
