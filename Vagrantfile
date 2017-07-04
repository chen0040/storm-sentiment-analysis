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

    config.vm.synced_folder "src/", "/home/vagrant/src", owner:"vagrant", group: "vagrant"
    config.vm.synced_folder "devops/", "/home/vagrant/devops", owner:"vagrant", group: "vagrant"

    # VirtualBox specific settings
    config.vm.provider "virtualbox" do |vb|
    vb.gui = false
    vb.memory = "1024"
    vb.cpus = 1
    end

    config.vm.provision "shell", inline: "sudo apt-get update -y"
    config.vm.provision "shell", inline: "sudo apt-get upgrade -y"
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
        kafka1.vm.provision "shell", inline: "tar -xvzf kafka_2.11-0.11.0.0.tgz --strip 1"
        kafka1.vm.provision "shell", inline: "cp -R /home/vagrant/kafka_2.11-0.11.0.0 /opt"
        kafka1.vm.provision "shell", inline: "ln -s /opt/kafka_2.11-0.10.0.1 /opt/kafka"
        kafka1.vm.provision "shell", inline: "rm -f /opt/kafka/config/server.properties"
        kafka1.vm.provision "shell", inline: "cp /home/vagrant/devops/kafka/server.properties /opt/kafka/config"
        kafka1.vm.provision "shell", inline: "cp /home/vagrant/devops/kafka/init.d /etc/init.d/kafka"
        kafka1.vm.provision "shell", inline: "chmod 755 /etc/init.d/kafka"
        kafka1.vm.provision "shell", inline: "sudo update-rc.d kafka defaults"
        kafka1.vm.provision "shell", inline: "sudo service kafka start"
    end




end
