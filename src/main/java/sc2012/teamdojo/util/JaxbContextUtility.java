/*
 * JaxbContextUtility.java created on 7 Mar 2010 09:36:29 by suggitpe for project sandbox-jaxb-test-client
 * 
 */
package sc2012.teamdojo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that encapsulates JAXB XML utilities, such as marshalling and unmarshalling. This class is
 * thread safe to its internal state.
 * 
 * @author suggitpe
 * @version 1.0 7 Mar 2010
 */
public final class JaxbContextUtility {

    private static final Logger LOG = LoggerFactory.getLogger( JaxbContextUtility.class );

    private static final JaxbContextUtility INSTANCE = new JaxbContextUtility();

    private Map<Class<?>, JAXBContext> jaxbContextCache = new HashMap<Class<?>, JAXBContext>();
    private final Object jaxCacheLock = new Object();
    private Map<String, Schema> schemaCache = new HashMap<String, Schema>();
    private final Object schemaCacheLock = new Object();

    /**
     * Private to ensure singleton.
     */
    private JaxbContextUtility() {}

    /**
     * Singleton method.
     * 
     * @return the singleton instance of the JaxbContextUtility.
     */
    public static JaxbContextUtility instance() {
        return INSTANCE;
    }

    /**
     * Unmarshals an XML string into an object of a given type.
     * 
     * @param aXmlString
     *            the XML string from which to create the object
     * @param aClazz
     *            the class type to return
     * @return an object of the same type as the passed in Class
     */
    @SuppressWarnings("unchecked")
    public <T> T unmarshalObject( String aXmlString, Class<?> aClazz ) throws JAXBException {
        return (T) unmarshalObject( aXmlString, aClazz, null );
    }

    /**
     * Unmarshals an XML string into an object of a given type.
     * 
     * @param aXmlString
     *            the XML string from which to create the object
     * @param aClazz
     *            the class type to return
     * @param aSchemaLocation
     *            the location of the schema to validate the XML against (classpath location)
     * @return an object of the same type as the passed in Class
     */
    @SuppressWarnings("unchecked")
    public <T> T unmarshalObject( String aXmlString, Class<?> aClazz, String aSchemaLocation )
                    throws JAXBException {
        if ( aXmlString == null ) {
            throw new IllegalArgumentException( "Cannot pass in a null xml string to unmarshaller" );
        }
        if ( aClazz == null ) {
            throw new IllegalArgumentException( "Cannot pass in a null class reference to unmarshaller" );
        }

        Unmarshaller unmarshaller = createUnmarshaller( aClazz );
        if ( aSchemaLocation != null ) {
            Schema schema = createSchemafromClasspathReference( aSchemaLocation );
            unmarshaller.setSchema( schema );
        }

        return (T) unmarshaller.unmarshal( new StringReader( aXmlString ) );
    }

    /**
     * @param aSchemaLocation
     * @return
     */
    private Schema createSchemafromClasspathReference( String aSchemaLocation ) {
        synchronized ( schemaCacheLock ) {
            if ( !schemaCache.containsKey( aSchemaLocation ) ) {
                SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
                URL url = getClass().getClassLoader().getResource( aSchemaLocation );
                if ( url == null ) {
                    throw new IllegalArgumentException( "Schema location [" + aSchemaLocation
                                                        + "] cannot be found on classpath" );
                }
                try {
                    Schema schema = sf.newSchema( url );
                    schemaCache.put( aSchemaLocation, schema );
                }
                catch ( SAXException saxe ) {
                    throw new IllegalArgumentException( "Failed to parse XML schema at [" + aSchemaLocation
                                                        + "]", saxe );
                }
            }
            return schemaCache.get( aSchemaLocation );
        }
    }

    private Unmarshaller createUnmarshaller( Class<?> aClazz ) throws JAXBException {
        JAXBContext ctx = createJaxbContext( aClazz );
        return ctx.createUnmarshaller();
    }

    /**
     * Marshals object into an XML string.
     * 
     * @param aObject
     *            the object to marshal
     * @return XML representation of the marshalled object
     * @throws javax.xml.bind.JAXBException
     *             if the object is not a JAXB object
     */
    public String marshalObject( Object aObject ) throws JAXBException {
        if ( aObject == null ) {
            throw new IllegalArgumentException( "Null object passed into marshaller, not allowed." );
        }

        StringWriter writer = new StringWriter();
        Marshaller marshaller = createMarshaller( aObject.getClass() );
        marshaller.marshal( aObject, writer );
        return writer.toString();
    }

    private Marshaller createMarshaller( Class<?> aClazz ) throws JAXBException {
        JAXBContext ctx = createJaxbContext( aClazz );
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        marshaller.setProperty( Marshaller.JAXB_ENCODING, "UTF-8" );
        return marshaller;
    }

    private JAXBContext createJaxbContext( Class<?> aClazz ) throws JAXBException {
        synchronized ( jaxCacheLock ) {
            if ( !jaxbContextCache.containsKey( aClazz ) ) {
                if ( LOG.isDebugEnabled() ) {
                    LOG.debug( "Added JAXBContext for class [" + aClazz.getName() + "] to the internal cache" );
                }
                jaxbContextCache.put( aClazz, JAXBContext.newInstance( aClazz ) );
            }
            return jaxbContextCache.get( aClazz );
        }
    }

}
