package info.cbeer.fcrepo;

import java.net.URI;
import org.akubraproject.map.IdMapper;

import info.cbeer.fcrepo.PairtreeDatastreamMapper;
import info.cbeer.fcrepo.PairtreeObjectMapper;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairtreeMapperTest {

	@Test
	public void testGetExternalId()  throws Exception {
		IdMapper mapper = new PairtreeObjectMapper();
	    assertEquals(URI.create("info:fedora/ns:id"), mapper.getExternalId(URI.create("file:ns/+i/d/ns+id/object.xml")));

	}
	
	@Test
	public void testGetInternalId() {
	    IdMapper mapper = new PairtreeObjectMapper();
	    assertEquals(URI.create("file:ns/+i/d/ns+id/object.xml"), mapper.getInternalId(URI.create("info:fedora/ns:id")));
	}
	
	@Test
	public void testGetObjectExternalId() throws Exception {
	    IdMapper mapper = new PairtreeDatastreamMapper();
	    assertEquals(URI.create("info:fedora/ns:id/dsId/dsId.dsVersionId"), mapper.getExternalId(URI.create("file:ns/+i/d/ns+id/data/dsId.dsVersionId")));
		
	}
}
