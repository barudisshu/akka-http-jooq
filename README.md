Akka Http Jooq
==========

## 基础建设

1. SBT 1.3.x
2. scala 2.13.x


## generate scala model

```shell script
sbt generateJOOQ
```

check `src/main/generated` dir.

## build image

```shell script
# publish image
sbt docker:publishLocal
# compose
docker-compose up

# check cluster status
docker-compose stop seed
```
