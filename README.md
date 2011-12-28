# Fedora Pairtree Akubra Mapper


## Setup
Copy pairtree-*.jar and fedora_pairtree_mapper-*.jar to
CATALINA_HOME/webapps/fedora/WEB-INF/lib (or some other path in Fedora's
class path). 

Update your akubra-llstore.xml to use the
info.cbeer.fedora.PairtreeMapper class (see akubra-llstore.example.xml)

??

## Usage
Fedora Pairtree Akubra Mapper will store objects ( and datastreams) in a
Pairtree-like tree with a BagIt-like object, e.g.:

- pairtree_root
   * ab
     * cd
       * abcd
         * object.xml (FOXML-serialized object representation)
         * data
           * MyDatastream.0 (Content for managed datastream
             `MyDatastream` version 0) 
           * MyDatastream.1 (Content for managed datastream
             `MyDatastream` version 1) 

## TODO
Because the FOXML-serialized data is stored in `object.xml`, when an
object is deleted, the directory structure is preserved. Ideally, Fedora
would clean up empty directory trees.

