package com.fable.insightview.monitor.messagesender.service;

public class IMsInterfaceServiceProxy implements com.fable.insightview.monitor.messagesender.service.IMsInterfaceService {
  private String _endpoint = null;
  private com.fable.insightview.monitor.messagesender.service.IMsInterfaceService iMsInterfaceService = null;
  
  public IMsInterfaceServiceProxy() {
    _initIMsInterfaceServiceProxy();
  }
  
  public IMsInterfaceServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIMsInterfaceServiceProxy();
  }
  
  private void _initIMsInterfaceServiceProxy() {
    try {
      iMsInterfaceService = (new com.fable.insightview.monitor.messagesender.service.IMsInterfaceServiceServiceLocator()).getIMsInterfaceServicePort();
      if (iMsInterfaceService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iMsInterfaceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iMsInterfaceService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iMsInterfaceService != null)
      ((javax.xml.rpc.Stub)iMsInterfaceService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.fable.insightview.monitor.messagesender.service.IMsInterfaceService getIMsInterfaceService() {
    if (iMsInterfaceService == null)
      _initIMsInterfaceServiceProxy();
    return iMsInterfaceService;
  }
  
  public java.lang.String sendMessage(java.lang.String in0) throws java.rmi.RemoteException{
    if (iMsInterfaceService == null)
      _initIMsInterfaceServiceProxy();
    return iMsInterfaceService.sendMessage(in0);
  }
  
  public java.lang.String report(java.lang.String in0) throws java.rmi.RemoteException{
    if (iMsInterfaceService == null)
      _initIMsInterfaceServiceProxy();
    return iMsInterfaceService.report(in0);
  }
  
  public java.lang.String reply(java.lang.String in0) throws java.rmi.RemoteException{
    if (iMsInterfaceService == null)
      _initIMsInterfaceServiceProxy();
    return iMsInterfaceService.reply(in0);
  }
  
  
}