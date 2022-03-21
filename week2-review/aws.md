### Cloud / AWS Overview
* How would you describe AWS? What is "the cloud" or "cloud computing" and why is it so popular now?
    - Amazon Web Services consists of a number of cloud-based computing solutions that Amazon provides to its clients
    - "The cloud" refers to the infrastucture/platform/services hosted by Amazon that clients can access remotely
    - Cloud computing allows developers to take advantage of a large scale computing infrastructure without having to deal with the cost and maintenance of keeping their own servers on premises, i.e. it is a more cost effective way of deploying an application

* Define Infrastructure, Platform, and Software as a Service
    - IaaS means the service manages the network, storage, server, and virtualization layers of deployment, leaving the client to setup/manage the OS, middleware, runtime, data, and application layers
    - PaaS means the service manages the infrastructure, along with the OS, middleware, and runtime layers, leaving the client to only manage the data and application
    - Saas means all layers are abstracted away from the client, so they only need to interact with the software's interface

* What's the difference between a Region and an Availability Zone (AZ)?
    - Availability zones describe the individual data centers where the cloud infrastructure is hosted
    - A region is a grouping of a number of local availability zones that defines certain capabilities/limits of a service within that region

* How are you charged for using AWS services? Does it vary by service?
    - AWS uses a pay-as-you-go business model, meaning that the amount charged depends on usage, which includes the number and kinds of services being used, the length of time for which the services are running, and the amount of storage used by each service

* Different ways to interact with AWS services?
    - RDS provides a relational database as a service
    - EC2 provides virtual machines to be used on cloud servers
        - AMI provides a configuration for launching an instance of EC2
        - EBS provides block storage that can be shared across EC2 instances
        - Security groups allows the client to control the traffic to their EC2 instances


### EC2

* What are the configuration options for EC2?
    - EC2 configuration is defined by the AMI, which defines the instance's OS, vCPU cores, vCPU performance, amount of memory, block store volume, and more...

* What are the different EC2 instance sizes/types?
    - To name a few:
        - t4g.nano, t4g.micro, t4g.small, t4g.medium, t4g.large, t4g.xlarge, t4g.2xlarge
        - t3.nano, ... t3.2xlarge
        - m6g.large, ... m6g.48xlarge
        - a1.medium, ... a1.4xlarge, a1.metal

* Once you create an EC2, how to connect to it?
    -  The EC2 instance can be accessed via ssh using the public DNS and the private key (.pem file) given upon creation

* What are Security Groups? When defining a rule for a security group, what 3 things do you need to specify?
    - Security Groups acts as a firewall controlling the incoming and outgoing traffic to each EC2 instance
    - When defining a security group, you must provide source IP addresses, protocol, and port range

* What's the difference between scalability, elasticity, and resiliency?
    - Elasticity describes a system's ability to dynamically allocate and deallocate to resources to match the demand for its service
    - Scalability describes a system's capacity for increasing workload to meet peak demands without decreasing performance under its current infrastructure
    - Resiliency describes a system's ability to recover from failures, be it from increased workload or malicious attacks

* Ways of paying for EC2?
    - The client's account will automatically be charged for EC2 usage based on uptime, number of instances, EBS volume, and configuration of each instance


### RDS

* What's an RDS?
    - Relation Database Service provides a way for clients to run and scale a relational database on the cloud
    - It also provides database management services such as automatic backups, failure detection and recovery, and methods for controlling user permissions

* Which vendors are supported?
    - Amaxon RDS supports Amazon Aurora, PostgreSQL, MySQL, MariaSQLm Oracle Database, and SQL Server
