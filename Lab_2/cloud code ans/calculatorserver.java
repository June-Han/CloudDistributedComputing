/*
	Code: calculator server		CalculatorServer.java

	Server code for hosting the CalculatorImpl object
*/

import java.rmi.Naming;	//Import naming classes to bind to rmiregistry

public class calculatorserver {
	static int reg_port = 1099;
	String reg_host = "192.168.1.28";
   //calculatorserver constructor
   public calculatorserver() {
       try {
		     System.setProperty("java.rmi.server.hostname", "192.168.1.24"); //*******
		     calculator cImpl = new calculatorimpl();
		     Naming.rebind("rmi://localhost/CalculatorService", cImpl);
		     //Construct a new CalculatorImpl object and bind it to the local rmiregistry
     		//N.b. it is possible to host multiple objects on a server
       }
       catch (Exception e) {
            System.out.println("Server Error: " + e);
       }
   }

   public static void main(String args[]) {
     	//Create the new Calculator server
        if (args.length == 1)
            reg_port = Integer.parseInt(args[0]);
            new calculatorserver();
   }
}
