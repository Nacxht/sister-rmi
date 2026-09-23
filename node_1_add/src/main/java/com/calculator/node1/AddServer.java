package com.calculator.node1;

import com.calculator.common.CalculatorService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Node 1 - ADD Server Implementation
 * Adds two numbers and chains to multiply operation
 */
public class AddServer extends UnicastRemoteObject implements CalculatorService {
    
    private final String multiplyServerUrl;
    
    public AddServer(String multiplyServerUrl) throws RemoteException {
        super();
        this.multiplyServerUrl = multiplyServerUrl;
    }
    
    @Override
    public double add(double a, double b) throws RemoteException {
        System.out.println("[ADD] " + a + " + " + b);
        
        double result = a + b;
        
        try {
            // Chain call to multiply server
            Registry registry = LocateRegistry.getRegistry(
                multiplyServerUrl.split(":")[0], 
                Integer.parseInt(multiplyServerUrl.split(":")[1])
            );
            CalculatorService multiplyService = (CalculatorService) registry.lookup("CalculatorService");
            double nextResult = multiplyService.multiply(result, 2);
            return nextResult;
        } catch (Exception e) {
            System.err.println("Error calling multiply service: " + e.getMessage());
            throw new RemoteException("Failed to call multiply service", e);
        }
    }
    
    @Override
    public double subtract(double a, double b) throws RemoteException {
        throw new RemoteException("Subtract operation not available on ADD server");
    }
    
    @Override
    public double multiply(double a, double b) throws RemoteException {
        throw new RemoteException("Multiply operation not available on ADD server");
    }
    
    @Override
    public double divide(double a, double b) throws RemoteException {
        throw new RemoteException("Divide operation not available on ADD server");
    }
    
    public static void main(String[] args) {
        String host = System.getProperty("rmi.server.host", "0.0.0.0");
        int port = Integer.parseInt(System.getProperty("rmi.server.port", "5001"));
        String multiplyUrl = System.getProperty("multiply.server.url", "localhost:5003");
        
        try {
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            
            // Create and export the server object
            AddServer server = new AddServer(multiplyUrl);
            
            // Bind the server to the registry
            registry.rebind("CalculatorService", server);
            
            System.out.println("Node 1 - ADD server running on " + host + ":" + port);
            System.out.println("Chaining to multiply server at: " + multiplyUrl);
            
        } catch (Exception e) {
            System.err.println("ADD Server exception:");
            e.printStackTrace();
        }
    }
}