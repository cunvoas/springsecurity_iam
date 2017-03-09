d:
cd D:\_SOCLE\appserver\apache-tomcat-7.0.50\bin\
shutdown.bat
del /S /Q D:\_SOCLE\appserver\apache-tomcat-7.0.50\webapps\iam-webapp\*.*
rmdir /S /Q D:\_SOCLE\appserver\apache-tomcat-7.0.50\webapps\iam-webapp
copy /Y D:\_SOCLE\workspaces\kepler\cas-iam_parent\iam-webapp\target\*.war D:\_SOCLE\appserver\apache-tomcat-7.0.50\webapps\
rem startup