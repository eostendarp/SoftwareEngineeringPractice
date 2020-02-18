ATM UI Test Script

1. Start main in ATM_UI.java
2. Attempt to log in with ID: "a@b.com" and password: "1234", you should successfully log in
3. You should now be at a screen with a balance of $100 displayed, attempt to withdraw $120, -$50, and $40.123, these should all fail and print messages telling you why they failed
4. Attempt to withdraw $25.50, you should succeed, and now be back at the main logged in screen with your balance displayed as $74.50
5. Attempt to deposit -$100 and $25.505, these should both fail, and print messages informing you why they failed
6. Attempt to deposit $100, you should succeed and now see your balance as $174.50
7. Attempt to transfer $10 to b@b.com, it should fail and give you a message saying that the target account is frozen.
8. Attempt to transfer -$20 and 15.608 to c@b.com, these should both fail
9. Attempt to transfer $20 to b@b.com, this should succeed, and you should see that your balance is now 154.40
10. Logout