# -*- mode: ruby -*# vi: set ft=ruby :
require 'yaml'
vagrantConfig = YAML.load_file 'Vagrantfile.config.yml'
Vagrant.configure("2") do |config|
    config.vm.box = "bento/ubuntu-16.04"

    config.vm.define "zoo1" do |zoo1|
        zoo1.vm.network "private_network", ip: vagrantConfig['ip1']
        zoo1.vm.hostname = "zoo1"
    end

    config.vm.define "zoo2" do |zoo2|
        zoo2.vm.network "private_network", ip: vagrantConfig['ip2']
        zoo2.vm.hostname = "zoo2"
    end

    config.vm.define "zoo3" do |zoo3|
        zoo3.vm.network "private_network", ip: vagrantConfig['ip3']
        zoo3.vm.hostname = "zoo3"
    end

    config.vm.define "kafka1" do |kafka1|
        kafka1.vm.network "private_network", ip: vagrantConfig['ip4']
        kafka1.vm.hostname = "kafka1"
    end

    config.vm.define "stormnimbus1" do |stormnimbus1|
        stormnimbus1.vm.network "private_network", ip: vagrantConfig['ip5']
        stormnimbus1.vm.hostname = "stormnimbus1"
    end

    config.vm.define "stormslave1" do |stormslave1|
        stormslave1.vm.network "private_network", ip: vagrantConfig['ip6']
        stormslave1.vm.hostname = "stormslave1"
    end

    config.vm.define "stormslave2" do |stormslave2|
        stormslave2.vm.network "private_network", ip: vagrantConfig['ip7']
        stormslave2.vm.hostname = "stormslave2"
    end

    config.vm.define "stormslave3" do |stormslave3|
        stormslave3.vm.network "private_network", ip: vagrantConfig['ip8']
        stormslave3.vm.hostname = "stormslave3"
    end

    config.vm.synced_folder "src/", "/home/vagrant/src", owner:"vagrant", group: "vagrant"
    config.vm.synced_folder "devops/", "/home/vagrant/devops", owner:"vagrant", group: "vagrant"

    # VirtualBox specific settings
    config.vm.provider "virtualbox" do |vb|
        vb.gui = false
        vb.memory = "1024"
        vb.cpus = 1
    end

    config.vm.provision "shell", inline: "sudo apt-get update -y"
    config.vm.provision "shell", inline: 'DEBIAN_FRONTEND=noninteractive apt-get -y -o Dpkg::Options::="--force-confdef" -o Dpkg::Options::="--force-confold" upgrade'
    config.vm.provision "shell", inline: "sudo add-apt-repository -y ppa:webupd8team/java"
    config.vm.provision "shell", inline: "sudo apt-get update -y"
    config.vm.provision "shell", inline: "echo 'oracle-java8-installer shared/accepted-oracle-license-v1-1 select true' | sudo debconf-set-selections"
    config.vm.provision "shell", inline: "sudo apt-get install oracle-java8-installer -y"


    config.vm.define "zoo1" do |zoo1|
        zoo1.vm.provision "shell", inline: "sudo apt-get install zookeeper -y"
        zoo1.vm.provision "shell", inline: "sudo apt-get install zookeeperd -y"
        zoo1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/hosts.1 >> /etc/hosts"
        zoo1.vm.provision "shell", inline: "echo 1 > /etc/zookeeper/conf/myid"
        zoo1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/zk1.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo1.vm.provision "shell", inline: "sudo service zookeeper restart"
    end

    config.vm.define "zoo2" do |zoo2|
        zoo2.vm.provision "shell", inline: "sudo apt-get install zookeeper -y"
        zoo2.vm.provision "shell", inline: "sudo apt-get install zookeeperd -y"
        zoo2.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/hosts.2 >> /etc/hosts"
        zoo2.vm.provision "shell", inline: "echo 2 > /etc/zookeeper/conf/myid"
        zoo2.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/zk2.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo2.vm.provision "shell", inline: "sudo service zookeeper restart"
    end

    config.vm.define "zoo3" do |zoo3|
        zoo3.vm.provision "shell", inline: "sudo apt-get install zookeeper -y"
        zoo3.vm.provision "shell", inline: "sudo apt-get install zookeeperd -y"
        zoo3.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/hosts.3 >> /etc/hosts"
        zoo3.vm.provision "shell", inline: "echo 3 > /etc/zookeeper/conf/myid"
        zoo3.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zookeeper/zk3.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo3.vm.provision "shell", inline: "sudo service zookeeper restart"
    end

    config.vm.define "kafka1" do |kafka1|
        kafka1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/kafka/hosts.1 >> /etc/hosts"
        kafka1.vm.provision "shell", inline: "wget http://www-eu.apache.org/dist/kafka/0.11.0.0/kafka_2.11-0.11.0.0.tgz"
        kafka1.vm.provision "shell", inline: "tar -xvzf kafka_2.11-0.11.0.0.tgz"
        kafka1.vm.provision "shell", inline: "cp -R /home/vagrant/kafka_2.11-0.11.0.0 /opt"
        kafka1.vm.provision "shell", inline: "chmod -R 777 /opt/kafka_2.11-0.11.0.0"
        kafka1.vm.provision "shell", inline: "ln -sf /opt/kafka_2.11-0.11.0.0 /opt/kafka"
        kafka1.vm.provision "shell", inline: "rm -f /opt/kafka/config/server.properties"
        kafka1.vm.provision "shell", inline: "cp /home/vagrant/devops/kafka/server.properties /opt/kafka/config/server.properties"
        kafka1.vm.provision "shell", inline: "cp /home/vagrant/devops/kafka/init.d /etc/init.d/kafka"
        kafka1.vm.provision "shell", inline: "chmod 755 /etc/init.d/kafka"
        kafka1.vm.provision "shell", inline: "sudo update-rc.d kafka defaults"
        kafka1.vm.provision "shell", inline: "sudo service kafka start"
    end

    config.vm.define "stormnimbus1" do |stormnimbus1|
        stormnimbus1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/storm/hosts.nimbus1 >> /etc/hosts"
        stormnimbus1.vm.provision "shell", inline: "wget http://www-eu.apache.org/dist/storm/apache-storm-0.9.1-incubating/apache-storm-0.9.1-incubating.tar.gz"
        stormnimbus1.vm.provision "shell", inline: "tar -xvzf apache-storm-0.9.1-incubating.tar.gz"
        stormnimbus1.vm.provision "shell", inline: "cp -R /home/vagrant/apache-storm-0.9.1-incubating /opt"
        stormnimbus1.vm.provision "shell", inline: "chmod -R 777 /opt/apache-storm-0.9.1-incubating"
        stormnimbus1.vm.provision "shell", inline: "ln -sf /opt/apache-storm-0.9.1-incubating /opt/storm"
        stormnimbus1.vm.provision "shell", inline: "rm -f /opt/storm/conf/storm.yaml"
        stormnimbus1.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/storm.yaml /opt/storm/conf/storm.yaml"
        stormnimbus1.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/init.d-nimbus /etc/init.d/storm-nimbus"
        stormnimbus1.vm.provision "shell", inline: "chmod 755 /etc/init.d/storm-nimbus"
        stormnimbus1.vm.provision "shell", inline: "sudo update-rc.d storm-nimbus defaults"
        stormnimbus1.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/init.d-ui /etc/init.d/storm-ui"
        stormnimbus1.vm.provision "shell", inline: "chmod 755 /etc/init.d/storm-ui"
        stormnimbus1.vm.provision "shell", inline: "sudo update-rc.d storm-ui defaults"
        stormnimbus1.vm.provision "shell", inline: "sudo service storm-nimbus start"
        stormnimbus1.vm.provision "shell", inline: "sudo service storm-ui start"
    end
    
    config.vm.define "stormslave1" do |stormslave1|
        stormslave1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/storm/hosts.slave1 >> /etc/hosts"
        stormslave1.vm.provision "shell", inline: "wget http://www-eu.apache.org/dist/storm/apache-storm-0.9.1-incubating/apache-storm-0.9.1-incubating.tar.gz"
        stormslave1.vm.provision "shell", inline: "tar -xvzf apache-storm-0.9.1-incubating.tar.gz"
        stormslave1.vm.provision "shell", inline: "cp -R /home/vagrant/apache-storm-0.9.1-incubating /opt"
        stormslave1.vm.provision "shell", inline: "chmod -R 777 /opt/apache-storm-0.9.1-incubating"
        stormslave1.vm.provision "shell", inline: "ln -sf /opt/apache-storm-0.9.1-incubating /opt/storm"
        stormslave1.vm.provision "shell", inline: "rm -f /opt/storm/conf/storm.yaml"
        stormslave1.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/storm.yaml /opt/storm/conf/storm.yaml"
        stormslave1.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/init.d-supervisor /etc/init.d/storm-supervisor"
        stormslave1.vm.provision "shell", inline: "chmod 755 /etc/init.d/storm-supervisor"
        stormslave1.vm.provision "shell", inline: "sudo update-rc.d storm-supervisor defaults"
        stormslave1.vm.provision "shell", inline: "sudo service storm-supervisor start"
    end

    config.vm.define "stormslave2" do |stormslave2|
        stormslave2.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/storm/hosts.slave2 >> /etc/hosts"
        stormslave2.vm.provision "shell", inline: "wget http://www-eu.apache.org/dist/storm/apache-storm-0.9.1-incubating/apache-storm-0.9.1-incubating.tar.gz"
        stormslave2.vm.provision "shell", inline: "tar -xvzf apache-storm-0.9.1-incubating.tar.gz"
        stormslave2.vm.provision "shell", inline: "cp -R /home/vagrant/apache-storm-0.9.1-incubating /opt"
        stormslave2.vm.provision "shell", inline: "chmod -R 777 /opt/apache-storm-0.9.1-incubating"
        stormslave2.vm.provision "shell", inline: "ln -sf /opt/apache-storm-0.9.1-incubating /opt/storm"
        stormslave2.vm.provision "shell", inline: "rm -f /opt/storm/conf/storm.yaml"
        stormslave2.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/storm.yaml /opt/storm/conf/storm.yaml"
        stormslave2.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/init.d-supervisor /etc/init.d/storm-supervisor"
        stormslave2.vm.provision "shell", inline: "chmod 755 /etc/init.d/storm-supervisor"
        stormslave2.vm.provision "shell", inline: "sudo update-rc.d storm-supervisor defaults"
        stormslave2.vm.provision "shell", inline: "sudo service storm-supervisor start"
    end
    
    config.vm.define "stormslave3" do |stormslave3|
        stormslave3.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/storm/hosts.slave3 >> /etc/hosts"
        stormslave3.vm.provision "shell", inline: "wget http://www-eu.apache.org/dist/storm/apache-storm-0.9.1-incubating/apache-storm-0.9.1-incubating.tar.gz"
        stormslave3.vm.provision "shell", inline: "tar -xvzf apache-storm-0.9.1-incubating.tar.gz"
        stormslave3.vm.provision "shell", inline: "cp -R /home/vagrant/apache-storm-0.9.1-incubating /opt"
        stormslave3.vm.provision "shell", inline: "chmod -R 777 /opt/apache-storm-0.9.1-incubating"
        stormslave3.vm.provision "shell", inline: "ln -sf /opt/apache-storm-0.9.1-incubating /opt/storm"
        stormslave3.vm.provision "shell", inline: "rm -f /opt/storm/conf/storm.yaml"
        stormslave3.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/storm.yaml /opt/storm/conf/storm.yaml"
        stormslave3.vm.provision "shell", inline: "cp /home/vagrant/devops/storm/init.d-supervisor /etc/init.d/storm-supervisor"
        stormslave3.vm.provision "shell", inline: "chmod 755 /etc/init.d/storm-supervisor"
        stormslave3.vm.provision "shell", inline: "sudo update-rc.d storm-supervisor defaults"
        stormslave3.vm.provision "shell", inline: "sudo service storm-supervisor start"
    end

end
