# Fedora Pairtree Akubra Mapper
Fedora Pairtree Akubra Mapper will store objects (and datastreams) in a unified
Pairtree-like tree with a BagIt-like object.

## Setup
1. Copy `lib/pairtree-1.1.1.jar` and `fedora_pairtree_mapper-0.0.1.jar` to
CATALINA_HOME/webapps/fedora/WEB-INF/lib (or some other path in Fedora's
class path). The other JAR files in `./lib` are only for testing purposes.

1. Update your akubra-llstore.xml to use the
info.cbeer.fcrepo.PairtreeMapper class (see [akubra-llstore.example.xml](https://github.com/cbeer/fedora_pairtree_mapper/blob/master/akubra-llstore.example.xml))

1. Load objects into Fedora

## Usage
Fedora Pairtree Akubra Mapper will store objects ( and datastreams) in a
Pairtree-like tree with a BagIt-like object, e.g.:

- pairtree_root
   * ab
     * cd
       * abcd
         * object.xml (FOXML-serialized object representation)
         * data
             * MyDatastream.0 (Content for managed datastream `MyDatastream` version 0) 
             * MyDatastream.1 (Content for managed datastream `MyDatastream` version 1) 

## TODO
Because the FOXML-serialized data is stored in `object.xml`, when an
object is deleted, the directory structure is preserved. Ideally, Fedora
could clean up empty directory trees.

Object and Datastream stores are configured independently. It might be possible to unify the pairtree configuration.

