使用Akka作为管家服务
==========

## 基础建设

1. SBT 1.3.x
2. scala 2.13.x

## 如何build image

在SBT命令中，执行`docker:publishLocal`创建一个本地容器。

要运行集群信息，执行`docker-compose up`。将会创建3个节点，1个seed和两个members。对应`seed`、`c1`和`c2`。

在运行状态，可以试试使用 `docker-compose stop seed`查看集群状态。

docker集群方式部署(不推荐)：

```shell script
docker stack deploy --compose-file docker-compose.yml cplier-hourse
```

检查服务状态：

```shell script
docker stack services cplier-hourse
```

多个docker机器部署：

该方式需要使用本机网络，以至于publish port将不生效。

```shell script
# node1
docker run -it --name cplier-hourse -p 1600:1600 -e CLUSTER_IP=seed -e CLUSTER_PORT=1600 -e SEED_PORT_1600_TCP_ADDR=seed --network=host -d cplier/cplier-hourse
# node2
docker run -it --name cplier-hourse -p 1601:1601 -e CLUSTER_IP=c1 -e CLUSTER_PORT=1601 -e SEED_PORT_1600_TCP_ADDR=seed --network=host -d cplier/cplier-hourse
# node3
docker run -it --name cplier-hourse -p 1602:1602 -e CLUSTER_IP=c2 -e CLUSTER_PORT=1602 -e SEED_PORT_1600_TCP_ADDR=seed --network=host -d cplier/cplier-hourse
```

其中seed 为种子节点，c1、c2为后面添加的节点IP.

## 静默的后台服务

1. 不提供接口
2. 设计定时cron规则
3. 实现数据分析(比Spark更小巧、存储方式更灵活、自动恢复、坚如磐石)


## akka-quartz-scheduler

## 高度分片集群

## 持久化状态

1. 某些计算场合下，希望跟踪到上一次的结果。譬如：小明上一学期的学习表现

3个节点的cassandra集群。仅用于记录状态，进行多个节点的任务计算

```cql
CREATE KEYSPACE IF NOT EXISTS akka WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1' : 3 }; 

CREATE TABLE IF NOT EXISTS akka.messages (
  persistence_id text,
  partition_nr bigint,
  sequence_nr bigint,
  timestamp timeuuid,
  timebucket text,
  writer_uuid text,
  ser_id int,
  ser_manifest text,
  event_manifest text,
  event blob,
  meta_ser_id int,
  meta_ser_manifest text,
  meta blob,
  tags set<text>,
  PRIMARY KEY ((persistence_id, partition_nr), sequence_nr, timestamp))
  WITH gc_grace_seconds = 864000
  AND compaction = {
    'class' : 'SizeTieredCompactionStrategy',
    'enabled' : true,
    'tombstone_compaction_interval' : 86400,
    'tombstone_threshold' : 0.2,
    'unchecked_tombstone_compaction' : false,
    'bucket_high' : 1.5,
    'bucket_low' : 0.5,
    'max_threshold' : 32,
    'min_threshold' : 4,
    'min_sstable_size' : 50
    };

CREATE TABLE IF NOT EXISTS akka.tag_views (
  tag_name text,
  persistence_id text,
  sequence_nr bigint,
  timebucket bigint,
  timestamp timeuuid,
  tag_pid_sequence_nr bigint,
  writer_uuid text,
  ser_id int,
  ser_manifest text,
  event_manifest text,
  event blob,
  meta_ser_id int,
  meta_ser_manifest text,
  meta blob,
  PRIMARY KEY ((tag_name, timebucket), timestamp, persistence_id, tag_pid_sequence_nr))
  WITH gc_grace_seconds = 864000
  AND compaction = {
    'class' : 'SizeTieredCompactionStrategy',
    'enabled' : true,
    'tombstone_compaction_interval' : 86400,
    'tombstone_threshold' : 0.2,
    'unchecked_tombstone_compaction' : false,
    'bucket_high' : 1.5,
    'bucket_low' : 0.5,
    'max_threshold' : 32,
    'min_threshold' : 4,
    'min_sstable_size' : 50
    };

CREATE TABLE IF NOT EXISTS akka.tag_write_progress(
  persistence_id text,
  tag text,
  sequence_nr bigint,
  tag_pid_sequence_nr bigint,
  offset timeuuid,
  PRIMARY KEY (persistence_id, tag));

CREATE TABLE IF NOT EXISTS akka.tag_scanning(
  persistence_id text,
  sequence_nr bigint,
  PRIMARY KEY (persistence_id));

CREATE TABLE IF NOT EXISTS akka.metadata(
  persistence_id text PRIMARY KEY,
  deleted_to bigint,
  properties map<text,text>);

CREATE TABLE IF NOT EXISTS akka.all_persistence_ids(
  persistence_id text PRIMARY KEY);

CREATE TABLE akka.tag_views_progress (                              
    peristence_id text,           
    tag text,                     
    sequence_nr bigint,           
    PRIMARY KEY (peristence_id, tag)                                
);

CREATE KEYSPACE IF NOT EXISTS akka_snapshot WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1' : 3 }; 

CREATE TABLE IF NOT EXISTS akka_snapshot.snapshots (
  persistence_id text,
  sequence_nr bigint,
  timestamp bigint,
  ser_id int,
  ser_manifest text,
  snapshot_data blob,
  snapshot blob,
  meta_ser_id int,
  meta_ser_manifest text,
  meta blob,
  PRIMARY KEY (persistence_id, sequence_nr))
  WITH CLUSTERING ORDER BY (sequence_nr DESC) AND gc_grace_seconds = 864000
  AND compaction = {
    'class' : 'SizeTieredCompactionStrategy',
    'enabled' : true,
    'tombstone_compaction_interval' : 86400,
    'tombstone_threshold' : 0.2,
    'unchecked_tombstone_compaction' : false,
    'bucket_high' : 1.5,
    'bucket_low' : 0.5,
    'max_threshold' : 32,
    'min_threshold' : 4,
    'min_sstable_size' : 50
    };

```