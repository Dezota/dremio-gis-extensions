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
FROM dezota/dremio-oss-with-ext-thin:22.1.1-1

# who's your boss?
MAINTAINER "Brian Holman" <bholman@dezota.com>

USER root
COPY ./dremio-gis-extensions-22.1.1-202208230402290397-a7010f28.jar /opt/dremio/jars
RUN chown -R dremio:dremio /opt/dremio
USER dremio
