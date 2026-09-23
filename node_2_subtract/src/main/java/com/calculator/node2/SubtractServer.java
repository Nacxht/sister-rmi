package com.calculator.node2;

import com.calculator.common.CalculatorService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Node 2 - SUBTRACT Server Implementation
 * Subtracts two numbers and chains to divide operation
 */
public class SubtractServer extends UnicastRemoteObject implements CalculatorService {
    
    private final String divideServerUrl;
    
    public SubtractServer(String divideServerUrl) throws RemoteException {
        super();
        this.divideServerUrl = divideServerUrl;
    }
    
    @Override
    public double add(double a, double b) throws RemoteException {
        throw new RemoteException("Add operation not available on SUBTRACT server");
    }
    
    @Override
    public double subtract(double a, double b) throws RemoteException {
        System.out.println("[SUBTRACT] " + a + " - " + b);
        
        double result = a - b;
        
        try {
            // Chain call to divide server
            Registry registry = LocateRegistry.getRegistry(
                divideServerUrl.split(":")[0], 
                Integer.parseInt(divideServerUrl.split(":")[1])
            );
            CalculatorService divideService = (CalculatorService) registry.lookup("CalculatorService");
            double nextResult = divideService.divide(result, 9);
            return nextResult;
        } catch (Exception e) {
            System.err.println("Error calling divide service: " + e.getMessage());
            throw new RemoteException("Failed to call divide service", e);
        }
    }
    
    @Override
    public double multiply(double a, double b) throws RemoteException {
        throw new RemoteException("Multiply operation not available on SUBTRACT server");
    }
    
    @Override
    public double divide(double a, double b) throws RemoteException {
        throw new RemoteException("Divide operation not available on SUBTRACT server");
    }
    
    public static void main(String[] args) {
        String host = System.getProperty("rmi.server.host", "0.0.0.0");
        int port = Integer.parseInt(System.getProperty("rmi.server.port", "5002"));
        String divideUrl = System.getProperty("divide.server.url", "localhost:5004");
        
        try {
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            
            // Create and export the server object
            SubtractServer server = new SubtractServer(divideUrl);
            
            // Bind the server to the registry
            registry.rebind("CalculatorService", server);
            
            System.out.println("Node 2 - SUBTRACT server running on " + host + ":" + port);
            System.out.println("Chaining to divide server at: " + divideUrl);
            
        } catch (Exception e) {
            System.err.println("SUBTRACT Server exception:");
            e.printStackTrace();
        }
    }
}