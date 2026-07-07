FROM payara/server-web:7.2026.5
COPY target/master-library.war $DEPLOY_DIR
