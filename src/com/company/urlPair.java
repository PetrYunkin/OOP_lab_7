package com.company;

import java.net.*;
import java.util.regex.*;

public class urlPair {
    private URL url;
    private int depth;

    public static final String LINK_REGEX = "href\\s*=\\s*\"([^$^\"]*)\"";
    public static final Pattern LINK_PATTERN = Pattern.compile(LINK_REGEX, Pattern.CASE_INSENSITIVE);

    public urlPair(URL url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public static boolean isValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static boolean isAbsolute(String url) {
        return LINK_PATTERN.matcher(url).find() ? true : false;
    }
}