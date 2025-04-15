package com.swifta.ussd.constant;

public class AppMessages {

    public static final String INVALID_INPUT = "The value entered is invalid";
    public static final String INCORRECT_SHORTCODE_PAGE = "Service does not exist";
    public static final String NAVIGATION_MESSAGE = "\n0. Back";
    public static final String MID_NAVIGATION_MESSAGE = "\n0. Back\n99. Next";
    public static final String FIRST_NAVIGATION_MESSAGE = "\n99. Next";

    public static final String ACKNOWLEDGMENT_MESSAGE = "The service is provided by EmpayIT to enable customer purchase event tickets. \nWould you like to continue?" +
            "\n1. Proceed \n2. Cancel";
    public static final String CANCEL_MESSAGE = "Thanks for your interest in EmpayIT";
    public static final String T_AND_C_MESSAGE = "I identify MTN and EmpayIT. I accept the T&C in accordance to EmpayIT policy\n1. Yes\n2. No";
    public static final String T_AND_C_DECLINE_MESSAGE = "Dear Customer\nYou need to accept T&C to use this service\nThank you!";
    public static final String DOB_MESSAGE = "Please enter your date of birth in the format DDMMYYYY";
    public static final String DOB_RETRY_MESSAGE = "Dear Customer,\n\nPlease enter valid Date of Birth";
    public static final String KYC_CONFIRMATION_MESSAGE = "Please confirm KYC details\nName: {0}\nDOB: {1}\n1. Correct\n0. Back";
    public static final String KYC_VALID_MESSAGE = "Dear {0}.\nYour data has been registered successfully. Thank you for your interest in EmpayIT.";
    public static final String MAIN_MENU_MESSAGE = "Welcome to EmpayIT\n1. Purchase Ticket\n2. Resend Ticket\n3. Refund Ticket\n4. Ticket Validation\n5. Contact Us";
    public static final String PURCHASE_OPTION_MESSAGE = "Select an option\n1. Self Purchase\n2. Agent Purchase";
    public static final String EVENT_TYPE_MESSAGE = "Select Event type";
    public static final String EVENT_OPTION_MESSAGE = "Select Event";
    public static final String MOVIE_TICKET_OPTION_MESSAGE = "Select Movie Ticket\n1. Olubirin\n2. Django\n3. The secret lives of others";
    public static final String SPORT_EVENT_MESSAGE = "Select Event\n1. National Cup Qualifier\n2. ------";
    public static final String SPORT_CATEGORY_MESSAGE = "Select Category\n1. Section A\n2. Section B\n3. Section C";
    public static final String TICKET_BOUQUET_MESSAGE = "Select Ticket Bouquet";
    public static final String PHONE_MESSAGE = "Please enter Phone number";
    public static final String NUMBER_OF_TICKET_MESSAGE = "Enter number of tickets";
    public static final String TICKET_CONFIRMATION_MESSAGE = "Please confirm ticket details\nEvent: %s\nTitle: %s\nDate: %s\nNumber of ticket: %s\nAmount: N%s\n1. Confirm\n2. Decline";
    public static final String PAYMENT_OPTION_MESSAGE = "Select payment option\n1. MTN MoMo\n2. Promo Code";
    public static final String MOMO_PIN_MESSAGE = "Enter MoMo Pin";
    public static final String PROMO_CODE_MESSAGE = "Enter Event promo code";
    public static final String PAYMENT_CONFIRMATION_MESSAGE = "You have approved a debit of N%s initiated by transaction ID: %s";
    public static final String SMS_TICKET_CONFIRMATION_MESSAGE = "SMS Ticket Confirmation";
    public static final String PHONE_RETRY_MESSAGE = "Dear Customer\n\nPlease enter a valid Phone number";
    public static final String TICKET_LIST_MESSAGE = "Event Ticket ID\n1. Ticket ID/9897302/Oloture Dro";
    public static final String TICKET_RESEND_CONFIRMATION_MESSAGE = "Please confirm you want to resend to %s\n1. Yes\n2. No";
    public static final String TICKET_SENT_MESSAGE = "Dear Customer,\nYour ticket details has been sent. Thank you for your interest in EmpayIT.";
    public static final String SUPPORT_MESSAGE = "Contact Support on 08073763722";
}
