#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
FROM registry.access.redhat.com/ubi8/ubi

# who's your boss?
MAINTAINER "Brian Holman" <bholman@dezota.com>

ENV JAVA_HOME /usr/lib/jvm/zulu8
RUN \
    set -xeu && \
    yum update && \
    yum -y -q install https://cdn.azul.com/zulu/bin/zulu-repo-1.0.0-1.noarch.rpm && \
    yum -y -q install python3 zulu8-jdk less git maven cmake gcc && \
    alternatives --set python /usr/bin/python3

RUN \
    mkdir /build && \
    cd /build && \
    git clone https://github.com/uber/h3-java.git && \
    cd h3-java && \
    git checkout v3.7.2 && \
    mvn install

COPY ./startup.sh /startup.sh

ENV LANG en_US.UTF-8
ENTRYPOINT ["/startup.sh"]
