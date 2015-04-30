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

read -p "Kill second and third "

kill $second $third

sleep 5

./check_hz

read -p "Done ... "

kill $first