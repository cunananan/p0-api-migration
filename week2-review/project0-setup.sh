#!/bin/bash
### The following just does a (mock) setup for deploying project0 on EC2 ###

# setup environment variables
export DB_URL=jdbc:postgresql://database-1.vdQw4w9WgXcQ.us-west-0.rds.amazonaws.com:5432/postgres
export DB_USER=postgres
export DB_PASS=secret

# install relevant applications
sudo yum install java
sudo yum install mvn
sudo yum install git

# clone repository
cd ~
git clone https://github.com/030722-VA-SRE/aaron-cunanan.git
cd aaron-cunanan/project0

# Once in here, we can build and run our java application using maven,
# the code/setup of which is outside of the scope of this particular script...
