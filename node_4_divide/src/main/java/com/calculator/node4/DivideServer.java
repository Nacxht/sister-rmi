package com.calculator.node4;

import com.calculator.common.CalculatorService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Node 4 - DIVIDE Server Implementation
 * Divides two numbers (final operation in chain)
 */
public class DivideServer extends UnicastRemoteObject implements CalculatorService {
    
    public DivideServer() throws RemoteException {
        super();
    }
    
    @Override
    public double add(double a, double b) throws RemoteException {
        throw new RemoteException("Add operation not available on DIVIDE server");
    }
    
    @Override
    public double subtract(double a, double b) throws RemoteException {
        throw new RemoteException("Subtract operation not available on DIVIDE server");
    }
    
    @Override
    public double multiply(double a, double b) throws RemoteException {
        throw new RemoteException("Multiply operation not available on DIVIDE server");
    }
    
    @Override
    public double divide(double a, double b) throws RemoteException {
        System.out.println("[DIVIDE] " + a + " / " + b);
        
        if (b == 0) {
            throw new RemoteException("Cannot divide by zero");
        }
        
        return a / b;
    }
    
    public static void main(String[] args) {
        String host = System.getProperty("rmi.server.host", "0.0.0.0");
        int port = Integer.parseInt(System.getProperty("rmi.server.port", "5004"));
        
        try {
            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(port);
            
            // Create and export the server object
            DivideServer server = new DivideServer();
            
            // Bind the server to the registry
            registry.rebind("CalculatorService", server);
            
            System.out.println("Node 4 - DIVIDE server running on " + host + ":" + port);
            
        } catch (Exception e) {
            System.err.println("DIVIDE Server exception:");
            e.printStackTrace();
        }
    }
}