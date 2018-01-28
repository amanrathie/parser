# Test project for WalletHub

## Description

The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration.

## Notes
  
1. The program requires MySQL but the tests uses H2, so you can run *mvn test* without having to configure MySQL
2. MySQL Schema doesn't need to be executed in advance, since the application will create it automatically
   * Mixed
   * Mixed  
3. The input for the generated .jar was changed slightly from the original problem statement (see Instructions section)
4. Item
   * Load the log file once instead of every execution
   * Validation on input data
   * Logging

## Instructions

### Configurations before running app
- Update file /src/main/resources/application.properties with correct configuration for the MySQL database and log file path (*parser.filename*)

- Sample log file can be downloaded from here: https://www.dropbox.com/s/j493jzz27g5xvwo/access.log?dl=0

### Generate .jar
```
mvn package
```

### Run the .jar generated in target folder. Example:
```
java -jar parser-0.0.1-SNAPSHOT.jar 2017-01-01.13:00:00 hourly 100
```

## MYSQL Schema (not needed as the app will create the schema)

```sql
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `http_code` varchar(255) DEFAULT NULL,
  `http_from` varchar(255) DEFAULT NULL,
  `http_method` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `blocked_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```

## SQL Examples

- Find IPs that made more than a cetain number of requests for a given time period:

```sql
SELECT ip
FROM log
WHERE start_date >= '2017-01-01 13:00:00' AND start_date < '2017-01-01 14:00:00'
GROUP BY ip
HAVING COUNT(ip) > 100
```

- Find requests made by a given IP:

```sql
SELECT http_from
FROM log
WHERE ip = '192.168.228.188'
```
