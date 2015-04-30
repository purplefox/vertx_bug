#!/bin/bash

echo "Starting first process... "

vertx run ManyHandlers.java -cluster -cluster-port 6666 &
first=$!

sleep 10;

echo "Starting second and third process... "
vertx run ManyHandlers.java -cluster -cluster-port 6667 &
second=$!

vertx run ManyHandlers.java -cluster -cluster-port 6668 &
third=$!

sleep 10

echo "Checking map... "
./check_hz

echo "Notice the map size is 150 and there are 3 ip:port for every registered address"

read -p "Kill second and third.  Hit enter to continue... "

kill $second $third

sleep 5

./check_hz

echo "Notice that the map has varying ports when they should all be 6666"

read -p "Cleaning up... Hit enter to continue..."

kill $first