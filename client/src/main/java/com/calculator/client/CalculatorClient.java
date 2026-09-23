package com.calculator.client;

import com.calculator.common.CalculatorService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client for testing the Calculator RMI Service
 */
public class CalculatorClient {
    
    public static void main(String[] args) {
        String serverUrl = System.getProperty("server.url", "localhost:5001");
        
        try {
            // Get the RMI registry
            Registry registry = LocateRegistry.getRegistry(
                serverUrl.split(":")[0], 
                Integer.parseInt(serverUrl.split(":")[1])
            );
            
            // Look up the calculator service
            CalculatorService calculator = (CalculatorService) registry.lookup("CalculatorService");
            
            // Test the add operation (which chains through multiply -> subtract -> divide)
            double a = 10.0;
            double b = 5.0;
            
            System.out.println("Testing calculator service...");
            System.out.println("Calling add(" + a + ", " + b + ")");
            
            double result = calculator.add(a, b);
            
            System.out.println("Final result: " + result);
            System.out.println("Expected: ((10 + 5) * 2 - 3) / 9 = 3.0");
            
        } catch (Exception e) {
            System.err.println("Client exception:");
            e.printStackTrace();
        }
    }
}