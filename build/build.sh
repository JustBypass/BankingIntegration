#!/usr/bin/env sh


#get project name
web_server_path='var/lib/tomcat'
#up postgresql server

docker pull tomcat


docker pull postgresql

#up cassandra server

docker pull cassandra


#up tomcat(jetty) server




#build mvn project(war)


mvn package .



#move war to tomcat


mv ./target/*.war ${web_server_path}



# enable monitoring


docker run -d \
--name=prometheus \
-p 9090:9090 \
-v /etc/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
prom/prometheus \
--config.file=/etc/prometheus/prometheus.yml