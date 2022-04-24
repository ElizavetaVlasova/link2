package course.link;

import course.link.db.dao.Link;
import course.link.db.dao.LinkRepository;
import course.link.model.LinkStatisticResponse;
import course.link.service.LinkStatisticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkStatisticServiceTest {
    @Mock
    LinkRepository repository;

    @InjectMocks
    LinkStatisticService statisticService;

    @Test
    void testStatisticWithNoValidLink() {
        LinkStatisticResponse result = statisticService.getStatisticForOneLink("");
        commonEmptyExpecations(result);
    }

    @Test
    void testGetStatisticWithNoSuchLink() {
        List<Link> listOfSortedUrls = generateMockLinks();
        when(repository.getListOfSortedUrls()).thenReturn(listOfSortedUrls);
        LinkStatisticResponse result = statisticService.getStatisticForOneLink("ShortUrl");

        commonEmptyExpecations(result);
    }

    @Test
    void testGetStatisticForOneLInk() {
        List<Link> listOfSortedUrls = generateMockLinks();
        when(repository.getListOfSortedUrls()).thenReturn(listOfSortedUrls);
        LinkStatisticResponse result = statisticService.getStatisticForOneLink("shortUrl1");

        assertEquals(4, result.getCount());
        assertEquals("/l/shortUrl1", result.getShortLink());
        assertEquals("url1", result.getOriginalUrl());
        assertEquals(1, result.getRank());
    }

    private void commonEmptyExpecations(LinkStatisticResponse result) {
        assertNull(result.getOriginalUrl());
        assertNull(result.getShortLink());
        assertEquals(0, result.getCount());
        assertEquals(0, result.getRank());
    }

    private List<Link> generateMockLinks() {
        Link firstLink = new Link("url1", 4, "/l/shortUrl1");
        Link secondLink = new Link("url2", 15, "/l/shortUrl2");
        Link thirdLink = new Link("url3", 25, "/l/shortUrl3");
        Link fourthLink = new Link("url4", 8, "/l/shortUrl4");
        Link fifthLink = new Link("url5", 98, "/l/shortUrl5");
        Link sixthLink = new Link("url6", 22, "/l/shortUrl6");

        return List.of(firstLink, secondLink, thirdLink, fourthLink, fifthLink, sixthLink);
    }
}
