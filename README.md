24.07.2012 - yves hwang

zeromq-sink-example
===================

mucking about with 0mq in Java.

A simple work flow as such.
	1. Sink creates a Job and sends it to Repo via a PUSH/PULL socket combo
	2. Repo gets the job, pushes the job to workers via a PUB/SUB socket combo
	3. Workers gets the published job, does the job, then sends result to Sink via PUSH/PULL socket combo

Setup
=====
* ZeroMq 2.2.0
* using Java binding
* Ensure that zeromq.jar is linked in classpath
* Ensure -Djava.library.path=/usr/local/lib is used when running the sink, repo and worker
* all 3 nodes can be started in any order

