package com.calculator.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for calculator operations using Java RMI
 */
public interface CalculatorService extends Remote {
    
    /**
     * Add two numbers and then multiply by 2 via chain call
     */
    double add(double a, double b) throws RemoteException;
    
    /**
     * Subtract two numbers and then divide by 9 via chain call
     */
    double subtract(double a, double b) throws RemoteException;
    
    /**
     * Multiply two numbers and then subtract 3 via chain call
     */
    double multiply(double a, double b) throws RemoteException;
    
    /**
     * Divide two numbers (final operation in chain)
     */
    double divide(double a, double b) throws RemoteException;
}