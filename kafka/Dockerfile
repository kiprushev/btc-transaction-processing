FROM java:8

RUN wget http://www.scala-lang.org/files/archive/scala-2.10.1.tgz && \
	tar xvf scala-2.10.1.tgz && \
	mv scala-2.10.1 /usr/lib && \
	ln -s /usr/lib/scala-2.10.1 /usr/lib/scala && \
	rm scala-2.10.1.tgz
ENV	PATH=$PATH:/usr/lib/scala/bin

RUN wget http://apache.ip-connect.vn.ua/kafka/0.11.0.2/kafka_2.11-0.11.0.2.tgz && \
	tar -zxvf kafka_2.11-0.11.0.2.tgz && \
	mv kafka_2.11-0.11.0.2 /usr/lib && \
	ln -s /usr/lib/kafka_2.11-0.11.0.2 /usr/lib/kafka && \
	rm kafka_2.11-0.11.0.2.tgz
ENV PATH=$PATH:/usr/lib/kafka/bin

ENV KAFKA_ZOOKEEPER_CONNECT="zookeeper:2181"

RUN sed -i "/zookeeper.connect=localhost:2181/c\zookeeper.connect=${KAFKA_ZOOKEEPER_CONNECT}" /usr/lib/kafka/config/server.properties

WORKDIR /usr/lib/kafka

EXPOSE 9092

ENTRYPOINT ["bin/kafka-server-start.sh","config/server.properties"]