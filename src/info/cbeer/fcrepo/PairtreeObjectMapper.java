package info.cbeer.fcrepo;

import java.net.URI;
import java.io.File;

import gov.loc.repository.pairtree.*;

import org.akubraproject.map.IdMapper;

public class PairtreeObjectMapper 
  implements IdMapper {

    private static final String internalScheme = "file";
    
    private final Pairtree pt;
    
    /**
     * Creates an instance that will use the given pattern.
     *
     * @param pathPattern the pattern to use, possibly <code>null</code> or "".
     * @throws IllegalArgumentException if the pattern is invalid.
     */
    public PairtreeObjectMapper() {
        this.pt = new Pairtree();
    }
    
    //@Override
    public URI getExternalId(URI internalId) throws NullPointerException {
        String fullPath = internalId.toString().substring(
                internalScheme.length() + 1);
        return URI.create("info:fedora/" + decode(fullPath));
    }

    //@Override
    public URI getInternalId(URI externalId) throws NullPointerException {
        if (externalId == null) {
            throw new NullPointerException();
        }
        String uri = externalId.toString().replace("info:fedora/", "");
        return URI.create(internalScheme + ":" + getPath(uri) + "/" + encode(uri) + "/object.xml");
    }

    //@Override
    public String getInternalPrefix(String externalPrefix)
            throws NullPointerException {
        if (externalPrefix == null) {
            throw new NullPointerException();
        }

        return null;
    }    
    
    public String getPath(String uri) {
	  return pt.mapToPPath(uri);
    }
    
    public String encode(String uri) {
      return pt.cleanId(uri);	
    }
    
    public String decode(String uri) {
      String[] ppathParts = uri.split("\\" + File.separatorChar);
      String id = ppathParts[ppathParts.length - 2];
      return pt.uncleanId(id);	
    }
    
}
