# mw1129

This Project has two parts 
1. Rest Api developed using Spring boot - refer to ToolsPointOfSaleApplication.java
    . swagger url : http://localhost:8080/swagger-ui/index.html#
    . I used H2DB to store the data
2. Standalone Application - refer to PointOfSaleMainApp.java

I have used common util class for both parts as much as possible 
to minimize having redundant codes - ToolsRentalUtil.java


Testing guide: 
1. API testing 
    a. load the tools data into the DB using this api 
    b. http://localhost:8080/swagger-ui/index.html#/point-of-sale-controller/tools 
    c. use this sample data to populate into the Tool table
[
   {
   "toolCode": "LADW",
   "toolType": "Ladder",
   "brand": "Werner",
   "dailyCharge": 1.99,
   "weekdayCharge": "Yes",
   "weekendCharge": "Yes",
   "holidayCharge": "No"
   },
   {
   "toolCode": "CHNS",
   "toolType": "Chainsaw",
   "brand": "Stihl",
   "dailyCharge": 1.49,
   "weekdayCharge": "Yes",
   "weekendCharge": "No",
   "holidayCharge": "Yes"
   },
   {
   "toolCode": "JAKD",
   "toolType": "Jackhammer",
   "brand": "DeWalt",
   "dailyCharge": 2.99,
   "weekdayCharge": "Yes",
   "weekendCharge": "No",
   "holidayCharge": "No"
   },
   {
   "toolCode": "JAKR",
   "toolType": "Jackhammer",
   "brand": "Ridgid",
   "dailyCharge": 2.99,
   "weekdayCharge": "Yes",
   "weekendCharge": "No",
   "holidayCharge": "No"
   }
] 
d. you can hit the /checkout endpoint from swagger to start doing a checkout 
   sample request Json:
    **{
   "toolCode": "LADW",
   "rentalDays": 10,
   "discountPercent": 25,
   "checkoutDate": "09/05/2024"
   }**
========================
2. For Standalone Application, everything related to Tools has been loaded into the ToolsStaticDb class, which is used as a static DB. 
and checkout related are specified in the main method

**Test cases.**
all the scenarios given have been covered in the CheckoutServiceImplTest class 
tase cases have been written to other classes as well 

**NOTE**
regarding the **formatting**, everything is coming as expected in the standalone application

sample response: 
================== JAKR checkout start =====================
   **RentalAgreementDto{
   Tool code:JAKR
   Tool type:Jackhammer
   Tool brand:Ridgid
   Rental days:3
   Check out date:09/04/2024
   Due date:09/07/2024
   Daily rental charge:$2.99
   Charge days:2
   Pre-discount charge:$5.98
   Discount percent:25.0%
   Discount amount:$1.50
   Final charge:$4.48
   processResult:ProcessResult(status=Ok, message=Checkout Successful)}**

Rest-API response :
**{
"toolCode": "LADW",
"toolType": "Ladder",
"toolBrand": "Werner",
"rentalDays": 10,
"checkOutDate": "09/04/2024",
"dueDate": "09/14/2024",
"dailyRentalCharge": "$1.99",
"chargeDays": 10,
"preDiscountCharge": "$19.90",
"discountPercent": 25,
"discountAmount": "$4.97",
"finalCharge": "$14.93",
"processResult": {
  "status": "Ok",
  "msg": "Checkout Successful"
 }
}**

for invalid request data, here is the request/response using the API

**request**:
**{
"toolCode": "LADW",
"rentalDays": 10,
"discountPercent": 250,
"checkoutDate": "2024-09-04"
}**

**Response**: 

**{
"processResult": {
"status": "Error",
"message": "Percentage should be between 0 to 100"
}
}**
