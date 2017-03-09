Load Test
===================

JVM Parameters for load testing :

```bat
	-server -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSParallelRemarkEnabled
	-Xms512m -Xmx768m -XX:NewRatio=2 -XX:SurvivorRatio=5 -XX:MaxTenuringThreshold=25 -XX:PermSize=90m -XX:MaxPermSize=110m -XX:ReservedCodeCacheSize=32m
```