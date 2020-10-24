# Exam for Automation Engineer position

## Part one:
Write an automation test for this process:
1. Go to https://vpos.sandbox.splitit.com/
2. Press on ‘New Payment’ button
3. Sign In with user:
    - Username: test_vpos
    - Password: Test123!
4. Create an In-store order with these details:
    * Amount: 100
    * Currency: GBP
    * Order ID: Your Name
    * Installment Options: All Default Options
    * Number of Installments: 6
    * Card number: 4111-1111-1111-1111
    * Expiration Date: 02/22
5. Other details (such email/phone) - you can enter whatever details you like.
6. The test should run 20 times. The variables that should be changed automatically between the runs are Amount and Email.
## Part two:
Change the Card Number to 4222-2222-2222-2220 and the Expiration Date to 10/20 and run the test once again.
