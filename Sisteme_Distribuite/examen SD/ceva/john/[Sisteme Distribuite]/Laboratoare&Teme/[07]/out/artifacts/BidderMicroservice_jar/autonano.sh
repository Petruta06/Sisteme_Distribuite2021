#!/bin/bash

processes=1

while [ $processes -le 100 ]
do
	echo "Started $processes Bidder process"
	java -jar BidderMicroservice.jar &
	processes=$(( $processes + 1 ))
done
