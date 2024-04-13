package com.eric.springSecurityJwtDemo.service;

import com.eric.springSecurityJwtDemo.dto.EmailDetails;

public interface EmailSenderService {
  void SendEmailAlert(EmailDetails emailDetails);
}
