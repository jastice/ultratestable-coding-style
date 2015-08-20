# Ultratestable Coding Style

A simple way to make your logic code easier to test.

Inspired by [Blog post by Jessitron](http://blog.jessitron.com/2015/06/ultratestable-coding-style.html).

This repository contains a Java 8 application that reads a file, asynchronously saves the rate at which words occur, 
and censors words which occur too often per minute.


## Run the app

    mvn exec:java
    
This will run the SuperAsyncCensorMachine on "War and Peace".


## The task

Refactor the censoring mechanism so that you can test the logic without dealing with the asynchronous bits.

