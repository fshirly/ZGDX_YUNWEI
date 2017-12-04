/**
 * IMsInterfaceServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fable.insightview.monitor.messagesender.service;

public class IMsInterfaceServiceServiceLocator extends org.apache.axis.client.Service implements com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceService {

    public IMsInterfaceServiceServiceLocator() {
    }


    public IMsInterfaceServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IMsInterfaceServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IMsInterfaceServicePort
    private java.lang.String IMsInterfaceServicePort_address = "http://dx.sc//services/MsInterfaceService";

    public java.lang.String getIMsInterfaceServicePortAddress() {
        return IMsInterfaceServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IMsInterfaceServicePortWSDDServiceName = "IMsInterfaceServicePort";

    public java.lang.String getIMsInterfaceServicePortWSDDServiceName() {
        return IMsInterfaceServicePortWSDDServiceName;
    }

    public void setIMsInterfaceServicePortWSDDServiceName(java.lang.String name) {
        IMsInterfaceServicePortWSDDServiceName = name;
    }

    public com.fable.insightview.monitor.messagesender.service.IMsInterfaceService getIMsInterfaceServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IMsInterfaceServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIMsInterfaceServicePort(endpoint);
    }

    public com.fable.insightview.monitor.messagesender.service.IMsInterfaceService getIMsInterfaceServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceSoapBindingStub _stub = new com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getIMsInterfaceServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIMsInterfaceServicePortEndpointAddress(java.lang.String address) {
        IMsInterfaceServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.fable.insightview.monitor.messagesender.service.IMsInterfaceService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceSoapBindingStub _stub = new com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceSoapBindingStub(new java.net.URL(IMsInterfaceServicePort_address), this);
                _stub.setPortName(getIMsInterfaceServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IMsInterfaceServicePort".equals(inputPortName)) {
            return getIMsInterfaceServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://api.webService.business.sys.scga.cn", "IMsInterfaceServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://api.webService.business.sys.scga.cn", "IMsInterfaceServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IMsInterfaceServicePort".equals(portName)) {
            setIMsInterfaceServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
