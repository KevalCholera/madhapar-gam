package com.madhapar.Util;

/**
 * Created by smartsense on 05/10/16.
 */

public class Constants {
    public static class DifferentData{
        public static final int GoingPersonName = 1;
        public static final int InterestedPersonName = 2;
        public static final int NotGoingPersonName = 3;
        public static String Check = "check";
    }
    public static class RequestConstants {
        public static String BaseUrl = "http://139.59.29.185/";
        public static String LoginUrl = BaseUrl + "login/";
        public static String EventListUrl = BaseUrl + "events/";
        public static String OtpUrl = BaseUrl + "sendOTP/";
        public static String VerifyOtpUrl = BaseUrl + "verifyOTP/";
        public static String ChangePasswordUrl = BaseUrl + "resetPassword/";
        public static String SignupUrl = BaseUrl + "register/";
        public static String HeaderPostfix = "JWT ";
        public static String DefaultToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjkxOTQwODU4MDgwMSIsInVzZXJNb2JpbGVObyI6IjkxOTQwODU4MDgwMSIsInVzZXJfaWQiOjEsImVtYWlsIjoia2FyYW5AZ21haWwuY29tIiwiZXhwIjoxNDg1NjUzMTIxfQ.nPGJW1rtAl0r9wE31mjjraFj8QHkCZiralX-1jaQs2g";
    }

    public static class UserData {
        public static String UserId = "userId";
        public static String UserMobileNo = "userMobileNo";
        public static String UserFirstName = "userFirstName";
        public static String UserLastName = "userLastName";
        public static String UserLocationId = "locationId";
        public static String UserLocationName = "locationName";
        public static String UserProfilePic = "userProfilePic";
        public static String UserProfession = "userProfession";
        public static String UserEmail = "email";
        public static String UserDOB = "userDOB";
        public static String UserBloodGroup = "userBloodGroup";
        public static String UserFamilyMemberCount = "userFamilyMemberCount";
        public static String UserRegistrationId = "userRegistrationId";
        public static String isVerified = "isVerified";
        public static String UserFBProfileName = "userFBProfileName";
        public static String token = "token";
    }

    public static class ResponseCode {
        public static int LoginSuccessCode = 200;
        public static int SignUpSuccessCode = 201;
        public static int SuccessCode = 200;
        public static int OtpVerificationSuccess = 204;
        //public static int SignUpSuccessCode1 = 406;
    }


}
