FROM ecombasedb

COPY ecomtestdb-initdb.sh /docker-entrypoint-initdb.d/ecomtestdb-initdb.sh

RUN dos2unix /docker-entrypoint-initdb.d/ecomtestdb-initdb.sh