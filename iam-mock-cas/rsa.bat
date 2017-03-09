cd "C:\Program Files\Java\jdk1.7.0_17\bin"

keytool -genkey -keystore serverks -keyalg rsa -keysize 512 -alias tomcat7 -storepass changeit -keypass changeit -validity 3650