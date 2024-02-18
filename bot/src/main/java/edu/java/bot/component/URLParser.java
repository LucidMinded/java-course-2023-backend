package edu.java.bot.component;

import java.net.URI;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
public class URLParser {
    public ParsedURL parse(String stringUrl) {
        try {
            URL url = new URI(stringUrl).toURL();
            return new ParsedURL(url.getHost(), url.getPath());
        } catch (Exception e) {
            return null;
        }
    }

    public record ParsedURL(String host, String path) {
    }
}
