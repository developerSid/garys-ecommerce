FROM ecombasedb

COPY ecomdevdb-initdb.sh /docker-entrypoint-initdb.d/ecomdevdb-initdb.sh

RUN dos2unix /docker-entrypoint-initdb.d/ecomdevdb-initdb.sh
