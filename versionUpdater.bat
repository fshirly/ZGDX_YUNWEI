@echo off

@SET version=3.2.0-SNAPSHOT  


call mvn -f ./platform/pom.xml  versions:set -DnewVersion=%version%
call mvn -f ./monitor/pom.xml  versions:set -DnewVersion=%version%
call mvn -f ./resource/pom.xml  versions:set -DnewVersion=%version%
call mvn -f ./itsm/pom.xml  versions:set -DnewVersion=%version%
call mvn -f ./pom.xml  versions:set -DnewVersion=%version%

echo 版本更新结束！

pause