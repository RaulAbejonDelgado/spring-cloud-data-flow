#Download base image ubuntu 18.04
FROM ubuntu:18.04

# Update Software repository
RUN apt-get update && apt-get upgrade -y && apt-get install -y openssh-server
RUN apt-get install nano -y
RUN apt-get install inetutils-ping -y
RUN apt-get install apt-transport-https ca-certificates curl software-properties-common -y
RUN apt-get install net-tools
RUN apt-get upgrade --fix-missing
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 7EA0A9C3F273FCD8
RUN apt-get update
RUN gpg --keyserver pgpkeys.mit.edu --recv-key 7EA0A9C3F273FCD8
RUN gpg -a --export 7EA0A9C3F273FCD8 | apt-key add -
RUN apt-get update
RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable" -y
RUN apt-get --allow-releaseinfo-change update -y
RUN apt-get update -y && apt-get upgrade -y
RUN apt-cache policy docker-ce 
RUN apt-get install docker-ce -y
RUN /etc/init.d/docker start
RUN curl -L "https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m)"  -o /usr/local/bin/docker-compose
RUN mv /usr/local/bin/docker-compose /usr/bin/docker-compose
RUN chmod +x /usr/bin/docker-compose
RUN apt install containerd -y
RUN apt install docker.io -y
RUN mkdir /tmp/rabiitmq


# Install "software-properties-common" (for the "add-apt-repository")
RUN apt-get update && apt-get install -y \
    software-properties-common

# Add the "JAVA" ppa
RUN add-apt-repository -y \
    ppa:webupd8team/java

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -

RUN mkdir /var/run/sshd 
RUN useradd remote_user && echo "remote_user:1234" | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN	mkdir -p /home/remote_user/.ssh 
RUN	chmod 700 /home/remote_user/.ssh

COPY remote-key.pub /home/remote_user/.ssh/authorized_keys

RUN chown remote_user:remote_user -R /home/remote_user && \
    chmod 600 /home/remote_user/.ssh/authorized_keys
    
RUN apt-get update && apt-get install -y mysql-client && rm -rf /var/lib/apt

RUN usermod -aG sudo remote_user
RUN echo 'remote_user ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers

RUN apt-get update && apt-get install -y python-pip && pip install awscli
RUN apt-get update && apt-get install -y git-core

COPY ./sources /home/remote_user/
RUN chmod 700 /home/remote_user/docker-compose.yml
RUN chmod 700 /home/remote_user/docker-compose-rabbitmq.yml
ENV DATAFLOW_VERSION=2.1.0.RELEASE 
ENV SKIPPER_VERSION=2.0.2.RELEASE

VOLUME /tmp
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]

