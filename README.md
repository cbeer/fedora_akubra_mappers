# Fedora Akubra Mappers
A set of additional Akubra mappers


## Fedora Pairtree Akubra Mapper
Fedora Pairtree Akubra Mapper will store objects (and datastreams) in a unified
Pairtree-like tree with a BagIt-like object.

### Setup
1. Copy `lib/pairtree-1.1.1.jar` and `fedora_akubra_mappers-0.0.1.jar` to
CATALINA_HOME/webapps/fedora/WEB-INF/lib (or some other path in Fedora's
class path). The other JAR files in `./lib` are only for testing purposes.

1. Update your akubra-llstore.xml to use the
`info.cbeer.fcrepo.PairtreeObjectMapper` and `info.cbeer.fcrepo.PairtreeDatastreamMapper`  classes (e.g. [akubra-llstore.example.xml](https://github.com/cbeer/fedora_akubra_mappers/blob/master/akubra-llstore.example.xml))

1. Load objects into Fedora

### Usage
Fedora Pairtree Akubra Mapper will store objects ( and datastreams) in a
Pairtree-like tree with a BagIt-like object, e.g.:

```
- pairtree_root/
   * ab/
     * cd/
       * abcd/
         * object.xml (FOXML-serialized object representation)
         * data/
             * MyDatastream.0 (Content for managed datastream `MyDatastream` version 0) 
             * MyDatastream.1 (Content for managed datastream `MyDatastream` version 1) 
```

### TODO
Because the FOXML-serialized data is stored in `object.xml`, when an
object is deleted, the directory structure is preserved. Ideally, Fedora
could clean up empty directory trees.

Object and Datastream stores are configured independently. It might be possible to unify the pairtree configuration.

## Fedora Timestamp Formatter Mapper

### Usage
Fedora Timestamp Formatter Mapper will store objects using a Formatted
timestamp. The timestamp pattern is defined in your akubra-llstore.xml
configuration.

For example, if your `akubra-llstore.xml` was configured with:

```xml
   <bean name="fsObjectStoreMapper"
        class="info.cbeer.fcrepo.TimestampFormatterMapper"
         singleton="true">
    <constructor-arg value="%1$tY/%1$tm/%1$td"/>
   </bean>
```

Your objectStore would look like:

```
- objectStore/
  * 2010/
  * 2011/
    * 11/
    * 12/
      * 01/
        * info%3Afedora%2Ens%3Aid (FOXML-serialized object representation)
      * 15/
```

The constructor-arg is a [format string](http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html#syntax), with the first argument as a calendar instance and the second the object identifier.
