# GWT Storage

A simple GWT Client-Side HTML5 [Web Storage](http://www.w3.org/TR/webstorage/) API.

This project aims to extend the [GWT Client-side Storage API](http://www.gwtproject.org/doc/latest/DevGuideHtml5Storage.html), by adding **Object Value** support.



## Key features
  * Storing **Java Object** in HTML5 [Web Storage](http://www.w3.org/TR/webstorage/) _(localStorage or sessionStorage)_
  * Support **all java types** that meet the requirements of [GWT RPC serialization](http://www.gwtproject.org/doc/latest/tutorial/RPC.html#serialize) (implements java.io.Serializable, etc...)
  * Client side Java Object serialization. By reusing the **GWT RPC object serialization framework**. Means, all objects used in GWT RPC service will be automatically persistable in localStorage or sessionStorage. No more code/serializer generation.
  * Customizing the list of web storage persistable type with XML file.
  * **Extensible caching** possiblility to avoid repeating serialization/deserialization

## Stable version
  * [gwt-storage releases](https://github.com/seanchenxi/gwt-storage/releases/) 
  * [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.seanchenxi.gwt)

## Maven Configuration
Find the the available jars in [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.seanchenxi.gwt)

### Release:
```xml
<dependency>
    <groupId>com.seanchenxi.gwt</groupId>
    <artifactId>gwt-storage</artifactId>
    <version>1.2.0</version>
    <scope>provided</scope>
</dependency>
```

### Snapshot:
```xml
<repositories>
    <repository>
        <id>sonatype.snapshots</id>
        <name>Sonatype snapshot repository</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <layout>default</layout>
    </repository>
</repositories>

<dependency>
    <groupId>com.seanchenxi.gwt</groupId>
    <artifactId>gwt-storage</artifactId>
    <version>1.2.1-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

## Previous Versions
  [gwt-storage v1.1](https://github.com/seanchenxi/gwt-storage/releases/tag/v1.1)


## Use gwt-storage in your project
  * GettingStarted: <a target="_blank" href="https://code.google.com/p/gwt-storage/wiki/GettingStarted">GettingStarted</a>
  * Javadoc: <br/>
      <a target="_blank" href="http://seanchenxi.github.io/gwt-storage/javadoc/1.2.0/">version 1.2.0</a><br/>
      <a target="_blank" href="http://seanchenxi.github.io/gwt-storage/javadoc/1.1/">version 1.1</a>


## License
  [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)



## Feedback
  If you're using gwt-storage in your project, please let me know how useful (or not) this library is to you and what you think.
  Suggestions are always welcome. Send me an email at [xi@seanchenxi.com](mailto:xi@seanchenxi.com)
