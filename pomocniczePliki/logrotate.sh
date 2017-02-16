#!/bin/bash
path=/home/cartman/smieci/logs/
filename=console.log
yesterday=$(date -d "today" +%Y-%m-%d)
grep -a $yesterday $path$filename > $path$filename.$yesterday
