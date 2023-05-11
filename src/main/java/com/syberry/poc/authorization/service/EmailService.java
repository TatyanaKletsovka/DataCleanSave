package com.syberry.poc.authorization.service;

/**
 * This interface is responsible for constructing messages and sending emails.
 */
public interface EmailService {

  /**
   * Sends html email.
   *
   * @param token password reset token
   * @param userEmail email of the recipient
   * */
  void sendEmail(String token, String userEmail);
}
