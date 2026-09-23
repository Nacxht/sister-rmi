package com.calculator.node3;

import com.calculator.common.CalculatorService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Node 3 - MULTIPLY Server Implementation
 * Multiplies two numbers and chains to subtract operation
 */
public class MultiplyServer extends UnicastRemoteObject implements CalculatorService {
    
    private final String subtractServerUrl;
    
    public MultiplyServer(String subtractServerUrl) throws RemoteException {
        super();
        this.subtractServerUrl = subtractServerUrl;
    }
    
    @Override
    public double add(double a, double b) throws RemoteException {
        throw new RemoteException("Add operation not available on MULTIPLY server");
    }
    
    @Override
    public double subtract(double a, double b) throws RemoteException {
        throw new RemoteException("Subtract operation not available on MULTIPLY server");
    }
    
    @Override
    public double multiply(double a, double b) throws RemoteException {
        System.out.println("[MULTIPLY] " + a + " * " + b);
        
        double result = a * b;
        
        try {
            // Chain call to subtract server
            Registry registry = LocateRegistry.getRegistry(
                subtractServerUrl.split(":")[0], 
                Integer.parseInt(subtractServerUrl.split(":")[1])
            );
            CalculatorService subtractService = (CalculatorService) registry.lookup("CalculatorService");
            double nextResult = subtractService.subtract(result, 3);
            return nextResult;
        } catch (Exception e) {
            System.err.println("Error calling subtract service: " + e.getMessage());
            throw new RemoteException("Failed to call subtract service", e);
        }
    }
    
    @Override
    public double divide(double a, double b) throws RemoteException {
        throw new RemoteException("Divide operation not available on MULTIPLY server");
    }
    
    public static void main(String[] args) {
        String host = System.getProperty("rmi.server.host", "0.0.0.0");
        int port = Integer.parseInt(System.getProperty("rmi.server.port", "5003"));
        String subtractUrl = System.getProperty("subtract.server.url", "localhost:5002");
        
        try {
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            
            // Create and export the server object
            MultiplyServer server = new MultiplyServer(subtractUrl);
            
            // Bind the server to the registry
            registry.rebind("CalculatorService", server);
            
            System.out.println("Node 3 - MULTIPLY server running on " + host + ":" + port);
            System.out.println("Chaining to subtract server at: " + subtractUrl);
            
        } catch (Exception e) {
            System.err.println("MULTIPLY Server exception:");
            e.printStackTrace();
        }
    }
}