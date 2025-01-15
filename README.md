
# CraftPlugin
[![Maven Central Version](https://img.shields.io/maven-central/v/be.machigan/craftplugin?style=for-the-badge&logo=apachemaven&label=Latest%20version)](https://central.sonatype.com/artifact/be.machigan/craftplugin)
[![GitHub Release](https://img.shields.io/github/v/release/MachiganMC/CraftPlugin?style=for-the-badge&logo=github&label=Last%20stable%20release)](https://github.com/MachiganMC/CraftPlugin/releases)

CraftPlugin is a Minecraft library to make the development of Spigot/Paper easier and quicker.
It supports Spigot <ins>and</ins> Paper. The library is designed so that you don't have to make
the difference between Spigot or Paper while developing.

The library is developed for minimum Minecraft ``1.19`` and supports latests versions.

## 📜 Table of contents :
- [🛠 To include in your project :](#-to-include-in-your-project-)
  - [Maven](#maven-) 
  - [Gradle](#gradle-)
- [🖋 Use it in your plugin](#-use-it-in-your-plugin-)


## 🔨 To include in your project :
###  Maven :
````xml
<dependencies>
    <dependency>
        <groupId>be.machigan</groupId>
        <artifactId>craftplugin</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
````
> [!WARNING]  
> Do not forget the Apache Shade Plugin to include the library in your final jar.
````xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.5.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <artifactSet>
                        <includes>
                            <include>be.machigan:craftplugin</include>
                        </includes>
                    </artifactSet>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
````

### Gradle :
````java
repositories {
    mavenCentral()
}

dependencies {
    implementation "be.machigan:craftplugin:VERSION"
}
````
> [!WARNING]  
> Do not forget the include the library in the final jar.
````java
tasks.jar {
    from configurations.runtimeClasspath.collect {
        it.isDirectory() && it.name == 'craftplugin-VERSION' ? it : zipTree(it)
    }
}
````

## 🖋 Use it in your plugin :
Once you've included the library in your project, just register your plugin to the library.
````java
public class CraftPluginExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        CraftPlugin.registerPlugin(this);
    }
}
```` 
 