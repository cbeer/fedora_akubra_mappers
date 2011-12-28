package info.cbeer.fcrepo;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import org.akubraproject.map.IdMapper;

public class TimestampFormatterMapper implements IdMapper {

    private static final String internalScheme = "file";
    private final String pattern;

    /**
     * Creates an instance that will use the given pattern.
     *
     * @param pathPattern the pattern to use, possibly <code>null</code> or "".
     * @throws IllegalArgumentException if the pattern is invalid.
     */
    public TimestampFormatterMapper(String pattern) {
        this.pattern = pattern;
    }
    
	@Override
	public URI getExternalId(URI internalId) throws NullPointerException {
        String fullPath = internalId.toString().substring(
                internalScheme.length() + 1);
        int i = fullPath.lastIndexOf('/');
        String encodedURI;
        if (i == -1)
            encodedURI = fullPath;
        else
            encodedURI = fullPath.substring(i + 1);
        return URI.create(decode(encodedURI));
	}

	@Override
	public URI getInternalId(URI externalId) throws NullPointerException {
        if (externalId == null) {
            throw new NullPointerException();
        }
        String uri = externalId.toString();
        return URI.create(internalScheme + ":" + getPath(uri) + encode(uri));
	}

	@Override
	public String getInternalPrefix(String externalPrefix) throws NullPointerException {
        if (externalPrefix == null) {
            throw new NullPointerException();
        }
        // we can only do this if pattern is ""
        if (pattern.length() == 0) {
            return internalScheme + ":" + encode(externalPrefix);
        } else {
            return null;
        }
	}
	
	 // gets the path based on the hash of the uri, or "" if the pattern is empty
    private String getPath(String uri) {
    	   
        if (pattern.length() == 0) {
            return "";
        }
        
    	StringBuilder sb = new StringBuilder();
    	// Send all output to the Appendable object sb
    	Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format(pattern, Calendar.getInstance(), uri);
        sb.append('/');
        return sb.toString();
    }
	
    private static String encode(String uri) {
        // encode char-by-char because we only want to borrow
        // URLEncoder.encode's behavior for some characters
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < uri.length(); i++) {
            char c = uri.charAt(i);
            if (c >= 'a' && c <= 'z') {
                out.append(c);
            } else if (c >= '0' && c <= '9') {
                out.append(c);
            } else if (c >= 'A' && c <= 'Z') {
                out.append(c);
            } else if (c == '-' || c == '=' || c == '(' || c == ')'
                    || c == '[' || c == ']' || c == ';') {
                out.append(c);
            } else if (c == ':') {
                out.append("%3A");
            } else if (c == ' ') {
                out.append("%20");
            } else if (c == '+') {
                out.append("%2B");
            } else if (c == '_') {
                out.append("%5F");
            } else if (c == '*') {
                out.append("%2A");
            } else if (c == '.') {
                if (i == uri.length() - 1) {
                    out.append("%2E");
                } else {
                    out.append(".");
                }
            } else {
                try {
                    out.append(URLEncoder.encode("" + c, "UTF-8"));
                } catch (UnsupportedEncodingException wontHappen) {
                    throw new RuntimeException(wontHappen);
                }
            }
        }
        return out.toString();
    }

    private static String decode(String encodedURI) {
        if (encodedURI.endsWith("%2E")) {
            encodedURI = encodedURI.substring(0, encodedURI.length() - 3) + ".";
        }
        try {
            return URLDecoder.decode(encodedURI, "UTF-8");
        } catch (UnsupportedEncodingException wontHappen) {
            throw new RuntimeException(wontHappen);
        }
    }

}
