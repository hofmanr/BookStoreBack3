mvn -DskipTests -ff -pl bsb-shared,bsb-ejb,bsb-web clean install && \
mvn  -pl bsb-ear clean package liberty:run