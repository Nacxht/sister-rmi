# Sister RMI - Java RMI Calculator Service Setup Guide

This guide provides step-by-step instructions for setting up and running the Java RMI Calculator Service on both Windows and Linux (MX-Linux) operating systems.

## Project Overview

This project is a Java RMI (Remote Method Invocation) implementation that mirrors the functionality of the JSON-RPC sister project. It consists of 4 distributed calculator nodes that chain operations together:

- **Node 1 (ADD)**: Adds two numbers, then chains to multiply operation
- **Node 2 (SUBTRACT)**: Subtracts two numbers, then chains to divide operation  
- **Node 3 (MULTIPLY)**: Multiplies two numbers, then chains to subtract operation
- **Node 4 (DIVIDE)**: Divides two numbers (final operation in chain)

The operation chain: `add(10, 5) → multiply(result, 2) → subtract(result, 3) → divide(result, 9)`

## Prerequisites

### For Both Windows and Linux

1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify installation: `java -version` and `javac -version`

2. **Gradle** (Optional - for advanced build management)
   - Download from [Gradle官网](https://gradle.org/install/)
   - Note: Project includes manual compilation scripts (compile.sh/compile.bat) that work without Gradle

### Windows-Specific

- Command Prompt or PowerShell
- Text editor (Notepad, VS Code, etc.)

### Linux (MX-Linux)-Specific

- Terminal
- Text editor (nano, vim, or VS Code)
- Build tools: `sudo apt install build-essential`

## Phase 1: Project Setup

### Step 1: Clone or Extract the Project

#### Windows
```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn
cd sister-rmi
```

#### Linux (MX-Linux)
```bash
cd /home/yourusername/Documents/dev-stuff/learn
cd sister-rmi
```

### Step 2: Verify Project Structure

The project should have the following structure:
```
sister-rmi/
├── common/
│   └── src/main/java/com/calculator/common/
│       └── CalculatorService.java
├── node_1_add/
│   └── src/main/java/com/calculator/node1/
│       └── AddServer.java
├── node_2_subtract/
│   └── src/main/java/com/calculator/node2/
│       └── SubtractServer.java
├── node_3_multiply/
│   └── src/main/java/com/calculator/node3/
│       └── MultiplyServer.java
├── node_4_divide/
│   └── src/main/java/com/calculator/node4/
│       └── DivideServer.java
├── client/
│   └── src/main/java/com/calculator/client/
│       └── CalculatorClient.java
├── build.gradle.kts
├── settings.gradle.kts
├── start_server1.sh/bat
├── start_server2.sh/bat
├── start_server3.sh/bat
├── start_server4.sh/bat
├── run_client.sh/bat
├── compile.sh/bat
└── SETUP.md
```

## Phase 2: Build the Project

You have two options for building the project:

### Option 1: Manual Compilation (Recommended - No Gradle Required)

#### Windows

```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
compile.bat
```

#### Linux (MX-Linux)

```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./compile.sh
```

### Option 2: Using Gradle

#### Windows

```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
gradlew build
```

#### Linux (MX-Linux)

```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./gradlew build
```

**Note**: If you don't have execute permissions on Linux:
```bash
chmod +x gradlew
./gradlew build
```

## Phase 3: Network Configuration

### Important: Java RMI Network Requirements

Java RMI requires proper network configuration for distributed deployment:

1. **Firewall Configuration**: Ensure ports 5001-5004 are open on all machines
2. **Host Configuration**: Each server needs to know its actual IP address
3. **Registry Access**: Clients need to reach the RMI registry on each server

### Windows Firewall Configuration

```cmd
# Allow RMI ports through Windows Firewall
netsh advfirewall firewall add rule name="RMI Port 5001" dir=in action=allow protocol=TCP localport=5001
netsh advfirewall firewall add rule name="RMI Port 5002" dir=in action=allow protocol=TCP localport=5002
netsh advfirewall firewall add rule name="RMI Port 5003" dir=in action=allow protocol=TCP localport=5003
netsh advfirewall firewall add rule name="RMI Port 5004" dir=in action=allow protocol=TCP localport=5004
```

### Linux (MX-Linux) Firewall Configuration

```bash
# If using UFW (Ubuntu-based)
sudo ufw allow 5001/tcp
sudo ufw allow 5002/tcp
sudo ufw allow 5003/tcp
sudo ufw allow 5004/tcp

# If using firewalld (Fedora-based)
sudo firewall-cmd --permanent --add-port=5001/tcp
sudo firewall-cmd --permanent --add-port=5002/tcp
sudo firewall-cmd --permanent --add-port=5003/tcp
sudo firewall-cmd --permanent --add-port=5004/tcp
sudo firewall-cmd --reload
```

## Phase 4: Server Configuration

### Step 1: Determine Network Configuration

For distributed deployment, you need to configure each server with:

1. **Server IP addresses** (actual network IPs, not localhost)
2. **Port assignments** (default: 5001-5004)
3. **Chain dependencies** (which server calls which)

### Step 2: Configure Network Settings

#### Windows - Modify Batch Files

Edit each `.bat` file to use actual IP addresses:

**start_server1.bat:**
```cmd
set RMI_SERVER_HOST=192.168.1.100  # Change to actual IP
set RMI_SERVER_PORT=5001
set MULTIPLY_SERVER_URL=192.168.1.102:5003  # Change to actual IP
```

**start_server2.bat:**
```cmd
set RMI_SERVER_HOST=192.168.1.101  # Change to actual IP
set RMI_SERVER_PORT=5002
set DIVIDE_SERVER_URL=192.168.1.103:5004  # Change to actual IP
```

**start_server3.bat:**
```cmd
set RMI_SERVER_HOST=192.168.1.102  # Change to actual IP
set RMI_SERVER_PORT=5003
set SUBTRACT_SERVER_URL=192.168.1.101:5002  # Change to actual IP
```

**start_server4.bat:**
```cmd
set RMI_SERVER_HOST=192.168.1.103  # Change to actual IP
set RMI_SERVER_PORT=5004
```

#### Linux (MX-Linux) - Modify Shell Scripts

Edit each `.sh` file to use actual IP addresses:

**start_server1.sh:**
```bash
export RMI_SERVER_HOST="192.168.1.100"  # Change to actual IP
export RMI_SERVER_PORT="5001"
export MULTIPLY_SERVER_URL="192.168.1.102:5003"  # Change to actual IP
```

**start_server2.sh:**
```bash
export RMI_SERVER_HOST="192.168.1.101"  # Change to actual IP
export RMI_SERVER_PORT="5002"
export DIVIDE_SERVER_URL="192.168.1.103:5004"  # Change to actual IP
```

**start_server3.sh:**
```bash
export RMI_SERVER_HOST="192.168.1.102"  # Change to actual IP
export RMI_SERVER_PORT="5003"
export SUBTRACT_SERVER_URL="192.168.1.101:5002"  # Change to actual IP
```

**start_server4.sh:**
```bash
export RMI_SERVER_HOST="192.168.1.103"  # Change to actual IP
export RMI_SERVER_PORT="5004"
```

## Phase 5: Start the Servers

### Important: Start Order

Start the servers in this order:
1. **Node 4 (DIVIDE)** - First (no dependencies)
2. **Node 2 (SUBTRACT)** - Second (depends on Node 4)
3. **Node 3 (MULTIPLY)** - Third (depends on Node 2)
4. **Node 1 (ADD)** - Fourth (depends on Node 3)

### Windows - Starting Servers

Open separate Command Prompt windows for each server:

**Window 1 - Node 4:**
```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
start_server4.bat
```

**Window 2 - Node 2:**
```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
start_server2.bat
```

**Window 3 - Node 3:**
```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
start_server3.bat
```

**Window 4 - Node 1:**
```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
start_server1.bat
```

### Linux (MX-Linux) - Starting Servers

Open separate terminal windows for each server:

**Terminal 1 - Node 4:**
```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./start_server4.sh
```

**Terminal 2 - Node 2:**
```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./start_server2.sh
```

**Terminal 3 - Node 3:**
```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./start_server3.sh
```

**Terminal 4 - Node 1:**
```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./start_server1.sh
```

### Expected Server Output

Each server should display:
```
Node 4 - DIVIDE server running on 0.0.0.0:5004
Node 2 - SUBTRACT server running on 0.0.0.0:5002
Chaining to divide server at: 192.168.1.103:5004
Node 3 - MULTIPLY server running on 0.0.0.0:5003
Chaining to subtract server at: 192.168.1.101:5002
Node 1 - ADD server running on 0.0.0.0:5001
Chaining to multiply server at: 192.168.1.102:5003
```

## Phase 6: Run the Client

### Windows

```cmd
cd C:\Users\YourUsername\Documents\dev-stuff\learn\sister-rmi
run_client.bat
```

### Linux (MX-Linux)

```bash
cd /home/yourusername/Documents/dev-stuff/learn/sister-rmi
./run_client.sh
```

### Expected Client Output

```
Testing calculator service...
Calling add(10.0, 5.0)
[ADD] 10.0 + 5.0
[MULTIPLY] 15.0 * 2.0
[SUBTRACT] 30.0 - 3.0
[DIVIDE] 27.0 / 9.0
Final result: 3.0
Expected: ((10 + 5) * 2 - 3) / 9 = 3.0
```

## Phase 7: Single Machine Testing (Localhost)

For testing on a single machine, use localhost configuration:

### Windows - Localhost Configuration

Edit batch files to use localhost:
```cmd
set RMI_SERVER_HOST=localhost
set MULTIPLY_SERVER_URL=localhost:5003
set DIVIDE_SERVER_URL=localhost:5004
set SUBTRACT_SERVER_URL=localhost:5002
```

### Linux (MX-Linux) - Localhost Configuration

Edit shell scripts to use localhost:
```bash
export RMI_SERVER_HOST="localhost"
export MULTIPLY_SERVER_URL="localhost:5003"
export DIVIDE_SERVER_URL="localhost:5004"
export SUBTRACT_SERVER_URL="localhost:5002"
```

Then follow the same startup procedure as above.

## Troubleshooting

### Common Issues and Solutions

#### 1. Connection Refused

**Problem**: `java.rmi.ConnectException: Connection refused to host`

**Solution**: 
- Ensure all servers are running
- Check firewall settings
- Verify IP addresses are correct
- Ensure ports 5001-5004 are not blocked

#### 2. Class Not Found

**Problem**: `java.lang.ClassNotFoundException`

**Solution**:
- Ensure the project is built with `./gradlew build`
- Check classpath in startup scripts
- Verify common module is included in classpath

#### 3. RMI Registry Issues

**Problem**: `java.rmi.server.ExportException: Registry already exists`

**Solution**:
- Stop existing RMI registry processes
- Use different ports if needed
- Kill existing Java processes: `taskkill /F /IM java.exe` (Windows) or `killall java` (Linux)

#### 4. Network Timeout

**Problem**: `java.rmi.ConnectException: Timeout`

**Solution**:
- Check network connectivity between machines
- Verify firewall rules
- Ensure correct IP addresses are used
- Test with `ping` command between machines

#### 5. Permission Denied (Linux)

**Problem**: `Permission denied` when running scripts

**Solution**:
```bash
chmod +x *.sh
chmod +x gradlew
```

### Debug Mode

Enable RMI debugging for troubleshooting:

Add this Java option to startup scripts:
```
-Djava.rmi.server.logCalls=true
-Djava.security.debug=all
```

## Advanced Configuration

### Custom Ports

To use different ports, modify the scripts:

**Windows:**
```cmd
set RMI_SERVER_PORT=6001
```

**Linux:**
```bash
export RMI_SERVER_PORT="6001"
```

### Security Manager

For production environments, consider adding a security manager:

```java
if (System.getSecurityManager() == null) {
    System.setSecurityManager(new SecurityManager());
}
```

Create a security policy file (`java.policy`):
```java
grant {
    permission java.net.SocketPermission "*:1024-65535", "connect,accept";
    permission java.net.SocketPermission "*:80", "connect";
};
```

Run with security policy:
```
-Djava.security.policy=java.policy
```

## Project Comparison: RMI vs JSON-RPC

| Feature | Java RMI | JSON-RPC |
|---------|----------|----------|
| Protocol | Java-specific RMI protocol | HTTP/JSON |
| Language | Java only | Language-agnostic |
| Type Safety | Strong typing | Loose typing |
| Performance | Binary protocol (faster) | Text-based (slower) |
| Complexity | More complex setup | Simpler setup |
| Network | Requires RMI registry | Standard HTTP |
| Firewall | Custom ports needed | Standard HTTP ports |

## Clean Up

### Stop Servers

**Windows:**
```cmd
taskkill /F /IM java.exe
```

**Linux:**
```bash
killall java
```

### Clean Build Files

**Both Windows and Linux:**
```cmd
# Using Gradle (if installed)
gradlew clean

# Manual cleanup
rm -rf build/ (Linux/Mac)
rmdir /s /q build (Windows)
```

## Summary

This Java RMI implementation provides a distributed calculator service with method chaining similar to the JSON-RPC version. The key differences are:

1. **Protocol**: Uses Java RMI instead of HTTP/JSON
2. **Type Safety**: Strongly typed Java interfaces
3. **Performance**: Binary protocol for better performance
4. **Complexity**: Requires RMI registry and network configuration

The setup process involves building the project, configuring network settings, starting servers in dependency order, and running the client to test the distributed operation chain.