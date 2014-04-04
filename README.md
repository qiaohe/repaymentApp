repaymentApp
============

This is a startup app for CMB

REST API
BASE_URL:http://{appConext}:8080/repayment/

MEMBER
1. GET  /{id}/testCreditLimit.html 
2. POST /{id}/idCardFront.html  
        Parameter @PathVariable Long id, @RequestParam("idCardFrontFile") MultipartFile idCardFrontFile
3.POST  /{id}/idCardBack.html
        @PathVariable Long id, @RequestParam("idCardBackFile") MultipartFile idCardBackFile

4.POST /{id}/creditCard.html
  
