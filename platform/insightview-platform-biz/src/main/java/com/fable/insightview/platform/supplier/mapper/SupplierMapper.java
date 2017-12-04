package com.fable.insightview.platform.supplier.mapper;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.supplier.enitity.ProviderAccessoryInfo;
import com.fable.insightview.platform.supplier.enitity.ProviderServiceCertificate;
import com.fable.insightview.platform.supplier.enitity.ProviderSoftHardwareProxy;
import com.fable.insightview.platform.supplier.enitity.Supplier;

public interface SupplierMapper {
	
    List<Supplier> getSupplier(Page<Supplier> page);
    
    public List<ProviderAccessoryInfo> getAttachment(Page<ProviderAccessoryInfo> page);
    
    public List<ProviderSoftHardwareProxy> getSoftHardware(Page<ProviderSoftHardwareProxy> page) ;
    
    public void insertSupplierInfo(Supplier vo);
    
    public void insertPAInfo(ProviderAccessoryInfo vo);
    
    public void insertSHPInfo(ProviderSoftHardwareProxy vo);
    
    public void insertPSCInfo(ProviderServiceCertificate vo);
    
    public Supplier queryBaseS(Integer id);
    
    public List<ProviderServiceCertificate> getServiceInfo(
			Page<ProviderServiceCertificate> page);
    
    public void editSupplierInfo(Supplier vo);
    
    public void editPAInfo(ProviderAccessoryInfo vo);
    
    public void editSHPInfo(ProviderSoftHardwareProxy vo);
    
    public void editPSCInfo(ProviderServiceCertificate vo);
    
    public void deletePai(Integer id);
    
    public void deletePshp(Integer id);
    
	public void deletePsc(Integer id);
	
	public void deleteSupplier(Integer id);
	
	public void deleteProviderAccessoryInfo(Integer id);
	
	public void deleteProviderSoftHardwareProxy(Integer id);
	
	public void deleteProviderServiceCertificate(Integer id);
	
	public int haveUser(Integer id);
	
	public String usingSupplierName(Integer id);
	
	public ProviderServiceCertificate queryProviderServiceCertificate(Integer id);
	
	public ProviderSoftHardwareProxy queryProviderSoftHardwareProxy(Integer id);
}