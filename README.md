# zookeper-curator-barrier-snippet
Simple working example of barrier in zookeeper with apache curator


# Instructions to run
- Build the docker container:
    ```docker-compose build```
- Run
    ```docker-compose up```

# Notes
This example depends on my own zookeeper-3.5.3-beta docker image available in dockerhub (danielporto/zookeeper). 
The current zookeeper-3.5.3.beta official image is broken and is not compatible with
Apache Curator 4.0.0. 

However, nothing was changed in the codebase of zookeeper. I simply built the image downloading
the zookeeper tarball from apache. (It also works with the image parrotstream/zookeeper-3.5.3-beta but that
is far larger than mine).

Code is built with gradle inside the container. 

Enjoy.


