Profile class has abstract method create factory that servers for the simple purpose: we add new profile, we will not have a change to
 forget to add new factory. That also supports open-close principle, that after adding 
 new profile we add new factory as a new class, rather than modifying existing factory. 