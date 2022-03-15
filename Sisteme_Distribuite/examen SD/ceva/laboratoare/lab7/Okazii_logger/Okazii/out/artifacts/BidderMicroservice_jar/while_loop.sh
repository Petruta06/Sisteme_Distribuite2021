
    #!/bin/bash
    # Basic while loop
    for i in {1..100}
	do
	java -jar BidderMicroservice.jar  &

	echo $i
	done
