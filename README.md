# storm-sentiment-analysis

Sentiment analysis with Spring and Storm

# Usage

Git clone this project and cd to its root directory

First start the cluster by running the following command:
 
```bash
vagrant up
```

This will start:
 
* a zookeeper cluster at the following hostname:ips:

    * zoo1:192.168.10.12
    * zoo2:192.168.10.13
    * zoo3:192.168.10.14

* a kafka server at the following hostname:ip (which use the zookeeper cluster above):

    * kafka1:192.168.10.15
    
* a storm cluster at the following hostname:ips (which uses the zookeeper cluster above):

    * stormnumbus1:192.168.10.17
    * stormslave1:192.168.10.18
    * stormslave2:192.168.10.19
    * stormslave3:192.168.10.20
    
Once the vagrant VMs are up and running, you can go to your host computer and enter 
the url http://192.168.10.17:8080 to your browse on the host computer. This will show the storm UI.






