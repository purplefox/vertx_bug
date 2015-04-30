# vertx_bug
## vertx_bug_kill.mov
Explanation:

This can be reproduced using the ManyHandlers.java verticle.  It's very simple and merely registers 50 handlers (ping-address#).

1. (Top left window) Start a ManyHandlers.java on cluster port 6666.
2. (Middle left window) Start a ManyHandlers.java on cluster port 6667.
3. (Bottom left window) Start a ManyHandlers.java on cluster port 6668.
4. (Top right window) Check the Hazelcast map. This shows that we have 3 entires for each address and the total is 150 ( 3 x 50 ) as expected.
5. (Bottom right window) Kill vertx instances running on cluster port 6667 and 6668 at the same time.  I ps to find their pids and kill them using the 'kill command'.  They both die correctly on the left windows.
6. (Top right window) Check the Hazelcast map again.  We should only have the addresses registered no 6666.  However, notice that we've got 6667 in there as well.  But we still have 50 handlers total as expected - just some of them point to the wrong port.  In this case, if I had clients sending to those addresses, I'd get timeouts once in a while since nothing is running on those ports.
7. (Middle left window) Restart the vertx instance on port 6667.
8. (Bottom left window) Restart the vertx instance on port 6668.
9. (Top right window) This is where things get totally FUBAR.  Checking the Hazelcast map again, we've got mix of stuff on 6666,6667,6668.  So we won't get timeouts, however we seem to have last 23 handlers (the ones that were on 6666 that got replaced with 6667 during step 6).  Now if something were to call those, we'd get the NO_HANDLERS error.

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
