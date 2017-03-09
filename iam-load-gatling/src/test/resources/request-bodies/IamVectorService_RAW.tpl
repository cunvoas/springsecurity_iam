<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.iam.cunvoas.github.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:findIamRawVector>
         <userId>${username}</userId>
         <applicationId>${application}</applicationId>
      </ser:findIamRawVector>
   </soapenv:Body>
</soapenv:Envelope>