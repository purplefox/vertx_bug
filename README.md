# vertx_bug
## vertx_bug_215.mov
Explanation:

1. (Top left window) Start a Receiver.java running on cluster port 6666
2. (Right most window) Use the Hazelcast tool to print the contents which is as expected
3. (Bottom left window) Start a Receiver.java running on cluster port 6667
4. (Right most window) Use the Hazelcast tool to print the contents which is as expected
5. (Left bottom window) Stop the Receiver.java which is running cluster port 6667
6. (Right most window) Use the Hazelcast tool to print the contents ON NOES!  Why is 6667 still there and 6666 has been removed

On top of all this (and which I should have captured), if you subsequently start 6667 again, and stop it, then 6667 is removed and there are no handler DESPITE 6666 still being up and running.

## vertx_bug_1080.mov
Explanation:
The video is an example of me running through a demonstration of the problem:

1. (Middle window) Start EventBusBridge.java
2. (Right most window) Use the Hazelcast tool to print the contents which is as expected
3. (Top left window) Start a Receiver.java on cluster port 6666
4. (Right most window) Use the Hazelcast tool to print the contents which is as expected
5. (Bottom left window) Start a Receiver.java on cluster port 6667
6. (Right most window) Use the Hazelcast tool to print the contents which is as expected
7. (Bottom left window) Ctrl+C the Receiver.java running
8. (Right most window) Use the Hazelcast tool to print the contents.  At this point you see that
   the handler that was just Ctrl+C (port 6667) remains in the list.

Appologies for the crassness of the Hz.java but it was just for deubgging purposes :)
