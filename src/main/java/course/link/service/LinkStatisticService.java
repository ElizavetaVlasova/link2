package course.link.service;

import course.link.db.dao.Link;
import course.link.db.dao.LinkRepository;
import course.link.model.LinkStatisticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkStatisticService {
    Logger logger = LoggerFactory.getLogger(LinkService.class);

    @Autowired
    LinkRepository repository;

    public LinkStatisticResponse getStatisticForOneLink(String shortLink) {
        if (shortLink.isBlank()) {
            logger.error("The link is not valid.");
            return new LinkStatisticResponse();
        }

        List<Link> listOfSortedUrls = repository.getListOfSortedUrls();
        String shortId = String.join("", "/l/", shortLink);

        int rank = 0;
        Link link = null;
        for (int i = 0; i < listOfSortedUrls.size(); i++) {
             Link repositoryLink = listOfSortedUrls.get(i);
             if (shortId.equals(repositoryLink.getShortUrl())) {
                 rank = i;
                 link = repositoryLink;
                 break;
             }
        }
        if (link == null) {
            logger.error("There is no such link in repository");
            return new LinkStatisticResponse();
        }
        return new LinkStatisticResponse(rank + 1, link);
    }

    public List<LinkStatisticResponse> getStatisticForAllLinks(int page, int limit) {
        List<Link> listOfSortedUrls = repository.getListOfSortedUrls();
        List<LinkStatisticResponse> result = new ArrayList<>();
        int skip = (page -1) * limit;
        int limitCounter = 0;
            for (int i = skip; i < listOfSortedUrls.size(); i++) {
                if (limitCounter >= limit) {
                    break;
                }
                result.add(new LinkStatisticResponse(i + 1, listOfSortedUrls.get(i)));
                limitCounter++;
        }
        return result;
    }
}
