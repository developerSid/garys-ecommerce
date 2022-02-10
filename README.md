# Gary's eCommerce system

## Micronaut 3.3.0 Documentation

- [User Guide](https://docs.micronaut.io/3.3.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.3.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.3.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

### Feature data-jdbc documentation

- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)

### Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

### Feature cache-caffeine documentation

- [Micronaut Caffeine Cache documentation](https://micronaut-projects.github.io/micronaut-cache/latest/guide/index.html)

- [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine)

### Feature security-jwt documentation

- [Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

### Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

### Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)

## Supporting tools

All 3rd development party tools are manged using Docker and housed under the __support/development__ directory.

### Use the development database

To start:
```shell
docker-compose build ecombasedb
docker-compose build ecomdevdb
docker-compose up -d ecomdevdb
docker-compose build ecomdevdbmigrate
docker-compose run ecomdevdbmigrate
```

### Use the test database
To start:
```shell
docker-compose build ecombasedb
docker-compose build ecomtestdb
docker-compose up -d ecomtestdb
```

### Useful docker/docker-compose commands

To stop it all
```shell
docker-compose down
```

To tail the logs
```shell
docker-compose logs -f --tail 100
```
