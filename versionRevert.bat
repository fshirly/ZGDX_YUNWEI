@echo off

call mvn -f ./platform/pom.xml versions:revert
call mvn -f ./monitor/pom.xml versions:revert
call mvn -f ./resource/pom.xml  versions:revert
call mvn -f ./itsm/pom.xml versions:revert

echo °æ±¾»Ö¸´½áÊø£¡

pause