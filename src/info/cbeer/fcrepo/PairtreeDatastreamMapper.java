package info.cbeer.fcrepo;

import java.net.URI;
import java.io.File;

import gov.loc.repository.pairtree.*;

import org.akubraproject.map.IdMapper;

public class PairtreeDatastreamMapper 
  implements IdMapper {

    private static final String internalScheme = "file";
    
    private final Pairtree pt;
    
    /**
     * Creates an instance that will use the given pattern.
     *
     * @param pathPattern the pattern to use, possibly <code>null</code> or "".
     * @throws IllegalArgumentException if the pattern is invalid.
     */
    public PairtreeDatastreamMapper() {
        this.pt = new Pairtree();
    }
    
    //@Override
    public URI getExternalId(URI internalId) throws NullPointerException {
        String fullPath = internalId.toString().substring(
                internalScheme.length() + 1);
        return URI.create(decode(fullPath));
    }

    //@Override
    public URI getInternalId(URI externalId) throws NullPointerException {
        if (externalId == null) {
            throw new NullPointerException();
        }
        String uri = externalId.toString();
        String[] uriParts = uri.split("\\" + File.separatorChar); 
        String dsVersionId = uriParts[uriParts.length - 1];
        String dsId = dsVersionId.substring(0, dsVersionId.lastIndexOf('.'));
        String versionId = dsVersionId.substring(dsVersionId.lastIndexOf('.') + 1);
        String objId = "info:fedora/" + uriParts[uriParts.length - 3];
        
        return URI.create(internalScheme + ":" + getPath(objId) + "/" + encode(objId) + "/data/" + encode(dsId) + "." + versionId);
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
      String[] ppathParts = uri.split("\\"+ File.separatorChar);
      String pid = ppathParts[ppathParts.length - 3];
      String dsidPath = ppathParts[ppathParts.length - 1];
      int i = dsidPath.lastIndexOf('.');
      String dsid;
      String dsversion;
      if (i == -1) {
          dsid = dsidPath;
          dsversion = "0";
     } else {
          dsid = dsidPath.substring(0, i);
          dsversion = dsidPath.substring(i + 1);
      }
      return pt.uncleanId(pid) + "/" + pt.uncleanId(dsid) + "/" + pt.uncleanId(dsid) + "." + dsversion;	
    }
    
}
