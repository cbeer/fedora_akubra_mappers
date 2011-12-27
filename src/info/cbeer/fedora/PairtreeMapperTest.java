package info.cbeer.fedora;

import java.net.URI;
import org.akubraproject.map.IdMapper;
import info.cbeer.fedora.PairtreeObjectMapper;
import info.cbeer.fedora.PairtreeDatastreamMapper;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class PairtreeMapperTest {

    private static final URI URI1 = URI.create("urn:example1");
    private static final URI URI1_ENCODED = URI.create("file:ur/n+/ex/am/pl/e1/urn+example1/object.xml");
    		
    private static final URI URI2 = URI.create("http://tinyurl.com/cxzzf");
    private static final URI URI2_ENCODED = URI.create("file:ht/tp/+=/=t/in/yu/rl/,c/om/=c/xz/zf/http+==tinyurl,com=cxzzf/object.xml");

    private static final URI URI3 = URI.create("info:foo/bar.baz.");
    private static final URI URI3_ENCODED = URI.create("file:in/fo/+f/oo/=b/ar/,b/az/,/info+foo=bar,baz,/object.xml");
    
	@Test
	public void testGetExternalId()  throws Exception {
		IdMapper mapper = new PairtreeObjectMapper();
		assertEquals(URI1, mapper.getExternalId(URI1_ENCODED));
		assertEquals(URI2, mapper.getExternalId(URI2_ENCODED));
		assertEquals(URI3, mapper.getExternalId(URI3_ENCODED));
	    assertEquals(URI.create("info:fedora/ns:id"), mapper.getExternalId(URI.create("file:in/fo/+f/ed/or/a=/ns/+i/d/info+fedora=ns+id/object.xml")));

	}
	
	@Test
	public void testGetInternalId() {
	    IdMapper mapper = new PairtreeObjectMapper();
        assertEquals(URI1_ENCODED, mapper.getInternalId(URI1));
	    assertEquals(URI2_ENCODED, mapper.getInternalId(URI2));
	    assertEquals(URI3_ENCODED, mapper.getInternalId(URI3));

	    assertEquals(URI.create("file:in/fo/+f/ed/or/a=/ns/+i/d/info+fedora=ns+id/object.xml"), mapper.getInternalId(URI.create("info:fedora/ns:id")));
	}
	
	@Test
	public void testGetObjectExternalId() throws Exception {
	    IdMapper mapper = new PairtreeDatastreamMapper();
	    assertEquals(URI.create("info:fedora/ns:id/dsId/dsId.dsVersionId"), mapper.getExternalId(URI.create("file:in/fo/+f/ed/or/a=/ns/+i/d/info+fedora=ns+id/data/dsId.dsVersionId")));
		
	}
}
