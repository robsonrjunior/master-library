package com.github.robsonrjunior.master.library.configs;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
    name = "java:app/MasterLibrary/MasterLibraryDataSource",
    className = "com.mysql.cj.jdbc.MysqlDataSource",
    user = "${ENV=MYSQL_USER}",
    password = "${ENV=MYSQL_PASSWORD}",
    url = "jdbc:mysql://${ENV=MYSQL_HOST}:${ENV=MYSQL_PORT}/${ENV=MYSQL_DATABASE}?useSSL=false&serverTimezone=UTC"
)
@ApplicationScoped
@Startup
public class DatabaseConfiguration {}
