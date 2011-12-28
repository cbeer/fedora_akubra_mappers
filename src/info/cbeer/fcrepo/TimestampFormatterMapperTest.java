package info.cbeer.fcrepo;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Calendar;

import org.akubraproject.map.IdMapper;
import org.junit.Test;

public class TimestampFormatterMapperTest {
	@Test
	public void testGetExternalId()  throws Exception {
		IdMapper mapper = new TimestampFormatterMapper("%1$tY");
		assertEquals(URI.create("info:fedora/ns:id"), mapper.getExternalId(URI.create("file:2011/info%3Afedora%2Fns%3Aid")));
	}
	
	@Test
	public void testGetInternalId() {
		IdMapper mapper = new TimestampFormatterMapper("%1$tY/%1$td");
		String cal = String.format("%1$tY/%1$td", Calendar.getInstance());
		 
        assertEquals(URI.create("file:" + cal + "/info%3Afedora%2Fns%3Aid"), mapper.getInternalId(URI.create("info:fedora/ns:id")));
	}
	
}
