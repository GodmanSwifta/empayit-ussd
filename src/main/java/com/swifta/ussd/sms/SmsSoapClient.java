package com.swifta.ussd.sms;

import com.swifta.ussd.ussdapi.ObjectFactory;
import com.swifta.ussd.ussdapi.RequestSOAPHeader;
import com.swifta.ussd.ussdapi.SendSms;
import com.swifta.ussd.ussdapi.SendSmsResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.xml.bind.DatatypeConverter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.swifta.ussd.constant.SoapConstant.*;

public class SmsSoapClient extends WebServiceGatewaySupport {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private String smsUrl;

    public SmsSoapClient(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    @Timed
    @SuppressWarnings("unchecked")
    public void sendSms(String msisdn, String message, String shortcode) {

        //SPID: 2340110011648
        //SERVICE ID: 234012000024261

        SendSms sendSms = new SendSms();
        sendSms.setSenderName(shortcode);
        sendSms.setMessage(message);
        sendSms.getAddresses().add("tel:" + msisdn);
        //simple reference can be used

        ObjectFactory objectFactory = new ObjectFactory();
        JAXBElement<SendSms> request = objectFactory.createSendSms(sendSms);
        JAXBElement<SendSmsResponse> responseJAXBElement =
                (JAXBElement<SendSmsResponse>) getWebServiceTemplate().marshalSendAndReceive(
                        smsUrl,
                        request, new WebServiceCallBack()
                );
        logger.info("startUssd respond - {}", responseJAXBElement.getValue());

    }

    private RequestSOAPHeader createRequestSOAPHeader() throws NoSuchAlgorithmException {
        ObjectFactory factory = new ObjectFactory();
        String spId = SPID;
        String serviceId = SERVICE_ID;
        String password = PASSWORD;
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((spId + password + timestamp).getBytes());
        byte[] digest = md.digest();
        String spPassword = DatatypeConverter
                .printHexBinary(digest);

        RequestSOAPHeader requestSOAPHeader =
                factory.createRequestSOAPHeader();
        requestSOAPHeader.setSpId(spId);
        requestSOAPHeader.setServiceId(serviceId);
        requestSOAPHeader.setSpPassword(spPassword);
        requestSOAPHeader.setTimeStamp(timestamp);
        return requestSOAPHeader;
    }

    private class WebServiceCallBack implements WebServiceMessageCallback {

        @Override
        public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
            ObjectFactory objectFactory = new ObjectFactory();
            try {
                // get the header from the SOAP message
                SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();

                // create the header element
                RequestSOAPHeader requestSOAPHeader = createRequestSOAPHeader();

                JAXBElement<RequestSOAPHeader> headers =
                        objectFactory.createRequestSOAPHeader(requestSOAPHeader);

                // create a marshaller
                JAXBContext context = JAXBContext.newInstance(RequestSOAPHeader.class);
                Marshaller marshaller = context.createMarshaller();

                // marshal the headers into the specified result
                marshaller.marshal(headers, soapHeader.getResult());
            } catch (Exception e) {
                logger.error("error during marshalling of the SOAP headers", e);
            }
        }
    }


}
