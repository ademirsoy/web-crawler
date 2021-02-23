# Simple Web Crawler

- Web Crawler is a tool to crawl all the links in a website and prints the site map to the console.
- The default root website is https://www.afiniti.com. However, a different website can be passed as an argument to the program.
- An example output is placed under resources/sitemap_final.txt for this website.

## Usage

### Build (Skip to `Run With Docker` if JDK 11 not exists)
- It's a Java project that requires Jdk 11 or higher
  
- Run the following script in root directory in order to build with Maven:

  **`mvn clean install`**
- Run the following if Maven is not installed

  **`./mvnw clean install`**

  or in Windows

  **`./mvnw.cmd clean install`**

### Run
- Once the build is completed, you can run with one of the following commands:

  **`java -jar target/web-crawler-0.0.1-jar-with-dependencies.jar`**

- In order to print site map of a different website, run as follows:

  **`java -jar target/web-crawler-0.0.1-jar-with-dependencies.jar https://www.different-website.com`**

### RUN WITH DOCKER
**`docker run -a stdout alidemirsoy/web-crawler`**


