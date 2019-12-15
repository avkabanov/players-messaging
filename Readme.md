# Players messaging

## Overview 
In this solution communication between two players is implemented. Messages can be 
transferred using inMemory message queue or using socket.   

## Structure overview



Profile class has abstract method create factory that servers for the simple purpose: we add new profile, we will not have a change to
 forget to add new factory. That also supports open-close principle, that after adding 
 new profile we add new factory as a new class, rather than modifying existing factory. 