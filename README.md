# vertx_bug
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
