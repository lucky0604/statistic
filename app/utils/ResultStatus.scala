package utils

object ResultStatus extends Enumeration {
  val status_0 = Value("Unknow Error/Data NotFound")

  val status_1 = Value("Success")

  val status_2 = Value("InternalServerError")

  val status_3 = Value("UnAuthorized")

  val status_4 = Value("Not Found")

  val status_5 = Value("Bad Request")

  val status_6 = Value("Verification Code Error")

  val status_7 = Value("Verification Code Invalid")

  val status_8 = Value("Name or Password Error")

  val status_9 = Value("User Does Not Exist")

  val status_10 = Value("User already exist")

  val status_11 = Value("Name already exist")

  val status_12 = Value("Email already exist")
}
