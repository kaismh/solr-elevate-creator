Solr Elevate Creator
====================

Elevate Creator is a [Solr]( http://lucene.apache.org/solr/) Plugin that automates the generation of elevate.xml (elevated query file) from any JDBC supported database, such as MySQL and PostgreSQL.

* The plugin is intended to be used with the [QueryElevationComponent](https://cwiki.apache.org/confluence/display/solr/The+Query+Elevation+Component), and to be invoked from the [DataImportHandler (DIH)]( https://cwiki.apache.org/confluence/display/solr/Uploading+Structured+Data+Store+Data+with+the+Data+Import+Handler) [Event Listeners](https://wiki.apache.org/solr/DataImportHandler#EventListeners), onImportStart or onImportEnd.

### Blog Article
A blog article introducing this plugin can be found [here](https://medium.com/@kaismh/automated-solution-for-query-elevation-using-solr-a15ce88b2762).

### Solr Distribution
A minimal Solr distribution that contains the plugin along with a sample configuration can be found [here](https://github.com/kaismh/solr-elevate-example).        

## Building

### Requirements
* Java 7 or higher
* Maven 3  

### Build Steps
```
git clone https://github.com/kaismh/solr-elevate-creator.git
cd solr-elevate-creator 
mvn clean package
```  

### Compatibility
The plugin is compatible with Solr versions 4 to 6 and was tested using Solr 4.10, 5.5 and 6.1 

# Usage Guide

## Deployment
* Copy solr-elevate-creator-1.0.0.jar to your [solr-home]/lib folder.
* Create elevate-creator.properties file, see dbTestMysql.properties as an example, and put it inside your core conf folder.
* Add onImportStart="org.kaismh.solr.ElevateFileCreatorPlugin" to your document section in your DIH configuration file.  

```
<?xml version="1.0" encoding="UTF-8"?>
<dataConfig>
 <dataSource autoCommit="true" convertType="true" driver="com.mysql.jdbc.Driver" 
 user="root" password="root"
 url="jdbc:mysql://localhost:3306/products?characterEncoding=UTF-8"  />
  <document onImportStart="org.kaismh.solr.ElevateFileCreatorPlugin">
   <entity name="products" pk="id" 
       query="SELECT id, title, description, manufacturer, price FROM amazon"/>
  </document>
</dataConfig>

```  

* If you have not yet configured Solr [QueryElevationComponent](https://cwiki.apache.org/confluence/display/solr/The+Query+Elevation+Component), do so before continuing.
* restart solr  


### Configuration
The following table contains description of **elevate-creator.properties** options:
 
| Option Name       	| Description                                                                                                                                                                                                                                                                        	| Default Value 	|
|-------------------	|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|---------------	|
| enabled           	| Enable or disable plugin                                                                                                                                                                                                                                                           	| true          	|
| overwrite         	| If the elevateOutputFile (elevate.xml) should be overwritten if it exists                                                                                                                                                                                                          	| true          	|
| dataDir           	| If true the elevateOutputFile will be stored in data folder, false will store it in the core conf folder. If you are using SolrCloud set it to false, if running Solr in standalone mode it is better to keep it true since the generated file will get refreshed after each commit. 	| true          	|
| elevateOutputFile 	| Name of the output file, should match \<config-file\> in QueryElevationComponent                                                                                                                                                                                                       	| elevate.xml   	|
| splitRegex        	| You might want to elevate a single document by several queries, to accomplish this store queries inside elevation\_query\_text column comma separated or using other splitters, and set this option to the split value.                                                                     	|               	|
| driverClassName   	| JDBC driver class name.                                                                                                                                                                                                                                                            	|               	|
| username          	| DB User Name.                                                                                                                                                                                                                                                                       	|               	|
| password          	| DB User Password.                                                                                                                                                                                                                                                                   	|               	|
| url               	| JDBC URL (Uniform Resource Locator).                                                                                                                                                                                                                                                	|               	|
| sql               	| The SQL statement that will retrieve both the document id and elevation\_query\_text fields, both columns must exists in the query, you can use 'select myID as id' if you have different names in your DB.                                                                          	|               	|

### Notes

* The plugin implements DIH EventListener, and it is assumed that DIH JARs are present in Solr lib folder. 
* You will need to have your DB JDBC driver present in Solr lib folder.
* The QueryElevationComponent \<config-file\> (ex. elevate.xml) can be present in either data or conf folder but not both. 
* Invoke the plugin from either onImportStart or onImportEnd (depending on your needs), but not both.


## Issue tracking

The issues for this add-on are tracked on the [issues](https://github.com/kaismh/solr-elevate-creator/issues) page. All bug reports and feature requests are appreciated. 

## Roadmap
- [ ] Create a standalone JAR to be used from the command line. 


## License & Author
 
Solr Elevate Creator is licensed under [Apache License 2.0] (http://www.apache.org/licenses/LICENSE-2.0.html). For license terms, see LICENSE.txt.

#### Created by Kais Hassan
