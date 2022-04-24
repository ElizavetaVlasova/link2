package course.link;

import course.link.db.dao.Link;
import course.link.db.dao.LinkRepository;
import course.link.model.LinkRequest;
import course.link.model.LinkResponse;
import course.link.service.LinkGenerator;
import course.link.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

    public static final String TEST_URL = "https://yandex.ru";
    @Mock
    LinkRepository repository;
    @Mock
    LinkGenerator linkGenerator;

    @InjectMocks
    LinkService linkService;

    @Test
    void testWithEmptyRequest() {
        LinkResponse result = linkService.generateShortLink(new LinkRequest());
        assertNull(result.getLink());
    }

    @Test
    void testWithEmptyResponseFromRepo() {
        LinkRequest request  = new LinkRequest(TEST_URL);
        when(repository.findByOriginalUrl(TEST_URL)).thenReturn(Optional.of(new Link()));

        LinkResponse result = linkService.generateShortLink(request);

        assertNull(result.getLink());
    }

    @Test
    void testGetExistingLink() {
        LinkRequest request  = new LinkRequest(TEST_URL);
        Link link = prepareMockLink();
        when(repository.findByOriginalUrl(TEST_URL)).thenReturn(Optional.of(link));

        LinkResponse result = linkService.generateShortLink(request);

        assertEquals("JKJsJ", result.getLink());
    }

    @Test
    void testGenerateShortLink() {
        LinkRequest request  = new LinkRequest(TEST_URL);
        when(repository.findByOriginalUrl(TEST_URL)).thenReturn(Optional.empty());
        when(linkGenerator.getRandomValue()).thenReturn("JABCD");

        LinkResponse result = linkService.generateShortLink(request);

        assertEquals("JABCD", result.getLink());
    }

    @Test
    void testGetOriginalEmptyUrl() {
        when(repository.findByShortURL("shortLink")).thenReturn(Optional.empty());
        assertNull(linkService.getOriginalUrl("shortLink"));
    }

    @Test
    void testGetOriginalUrl() {
        Link link = prepareMockLink();
        when(repository.findByShortURL("shortLink")).thenReturn(Optional.of(link));
        String result = linkService.getOriginalUrl("shortLink");

        assertEquals(TEST_URL, result);
    }

    private Link prepareMockLink() {
        var link = new Link();
        link.setCounter(2);
        link.setOriginalUrl(TEST_URL);
        link.setShortUrl("JKJsJ");
        return link;
    }
}
