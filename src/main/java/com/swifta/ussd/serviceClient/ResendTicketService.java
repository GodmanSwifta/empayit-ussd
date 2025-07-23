package com.swifta.ussd.serviceClient;


import com.swifta.ussd.dto.CustomerData;
import com.swifta.ussd.dto.ResendTicketResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResendTicketService {

   List<ResendTicketResponse> resendTicket(String phoneNumber);

}
