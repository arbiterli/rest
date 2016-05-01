cd F:\apache-tomcat-6.0.36\webapps
rm -rf rest
del rest.war

cd F:\eclipseEE\projects\rest\auto
call ant

cd F:\apache-tomcat-6.0.36\bin
call catalina.bat start

cd F:\eclipseEE\projects\rest\auto