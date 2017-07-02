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



    config.vm.provision "shell", inline: "sudo apt-get install zookeeper -y"
    config.vm.provision "shell", inline: "sudo apt-get install zookeeperd -y"



    config.vm.define "zoo1" do |zoo1|
        zoo1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/hosts.1 >> /etc/hosts"
        zoo1.vm.provision "shell", inline: "echo 1 > /etc/zookeeper/conf/myid"
        zoo1.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zk1.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo1.vm.provision "shell", inline: "sudo service zookeeper restart"
    end

    config.vm.define "zoo2" do |zoo2|
        zoo2.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/hosts.2 >> /etc/hosts"
        zoo2.vm.provision "shell", inline: "echo 2 > /etc/zookeeper/conf/myid"
        zoo2.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zk2.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo2.vm.provision "shell", inline: "sudo service zookeeper restart"
    end

    config.vm.define "zoo3" do |zoo3|
        zoo3.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/hosts.3 >> /etc/hosts"
        zoo3.vm.provision "shell", inline: "echo 3 > /etc/zookeeper/conf/myid"
        zoo3.vm.provision "shell", inline: "sudo cat /home/vagrant/devops/zk3.cfg >> /etc/zookeeper/conf/zoo.cfg"
        zoo3.vm.provision "shell", inline: "sudo service zookeeper restart"
    end




end
