package edu.java.bot.component;

import java.net.URI;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component @Slf4j public class URLParser {
    public ParsedURL parse(String stringUrl) {
        try {
            URL url = new URI(stringUrl).toURL();
            log.info("Successfully parsed URL: {}", url);
            return new ParsedURL(url.getHost(), url.getPath());
        } catch (Exception e) {
            log.info("Failed to parse URL: {}", stringUrl, e);
            return null;
        }
    }

    public record ParsedURL(String host, String path) {
    }
}
