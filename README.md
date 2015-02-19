# GWT Storage

A simple GWT Client-Side HTML5 [Web Storage](http://www.w3.org/TR/webstorage/) API.

This project aims to extend the [GWT Client-side Storage API](http://www.gwtproject.org/doc/latest/DevGuideHtml5Storage.html), by adding **Object Value** support.



## Key features
  * Storing **Java Object** in HTML5 [Web Storage](http://www.w3.org/TR/webstorage/) _(localStorage or sessionStorage)_
  * Support **all java types** that meet the requirements of [GWT RPC serialization](http://www.gwtproject.org/doc/latest/tutorial/RPC.html#serialize) (implements java.io.Serializable, etc...)
  * Client side Java Object serialization. By reusing the **GWT RPC object serialization framework**. Means, all objects used in GWT RPC service will be automatically persistable in localStorage or sessionStorage. No more code/serializer generation.
  * Customizing the list of web storage persistable type with XML file.
  * **Extensible caching** possiblility to avoid repeating serialization/deserialization
  
#### Coming new features: 
  * Server side data serialization [#8] (https://github.com/seanchenxi/gwt-storage/issues/8), thanks to [Freddy Boucher] (https://github.com/freddyboucher) and [Richard Wallis](https://github.com/rdwallis)
  * [Value record priority](https://github.com/seanchenxi/gwt-storage/pull/3), thanks to [Richard Wallis](https://github.com/rdwallis)
  * Data encryption
  * Data compression
  * Data recovering tools

## Stable version, v1.3.0
  * [gwt-storage releases](https://github.com/seanchenxi/gwt-storage/releases/) 
  * [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.seanchenxi.gwt)

## Previous Versions
  * [gwt-storage v1.3.0](https://github.com/seanchenxi/gwt-storage/releases/tag/v1.3.0)
  * [gwt-storage v1.2.1](https://github.com/seanchenxi/gwt-storage/releases/tag/v1.2.1)
  * [gwt-storage v1.2.0](https://github.com/seanchenxi/gwt-storage/releases/tag/v1.2.0)
  * [gwt-storage v1.1](https://github.com/seanchenxi/gwt-storage/releases/tag/v1.1)

## Use gwt-storage in your project
  * Getting Started: <a target="_blank" href="https://github.com/seanchenxi/gwt-storage/wiki/Getting-Started">Getting Started</a>
  * Javadoc: <a target="_blank" href="http://seanchenxi.github.io/gwt-storage/javadoc/1.3.0/">v1.3.0</a><br/>
  * Javadoc SNAPSHOT <a target="_blank" href="http://seanchenxi.github.io/gwt-storage/javadoc/1.3.1-SNAPSHOT/">v1.3.1-SNAPSHOT</a><br/>

### Maven Configuration
Find the the available jars in [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.seanchenxi.gwt)

#### Release:
```xml
<dependency>
    <groupId>com.seanchenxi.gwt</groupId>
    <artifactId>gwt-storage</artifactId>
    <version>1.3.0</version>
    <scope>provided</scope>
</dependency>
```

#### Snapshot:
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
    <version>1.3.1-SNAPSHOT</version>
    <scope>provided</scope>
    <!-- 
        If you want to do sever side object2string serialization, 
        you should change the scope to compile
    -->
</dependency>


```


## License
  [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)


## Feedback
  If you're using gwt-storage in your project, please let me know how useful (or not) this library is to you and what you think.
  Suggestions are always welcome. Send me an email at [xi@seanchenxi.com](mailto:xi@seanchenxi.com)
