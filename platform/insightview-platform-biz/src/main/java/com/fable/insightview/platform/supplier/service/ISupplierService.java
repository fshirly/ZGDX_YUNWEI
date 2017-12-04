package com.fable.insightview.platform.supplier.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.supplier.enitity.ProviderAccessoryInfo;
import com.fable.insightview.platform.supplier.enitity.ProviderServiceCertificate;
import com.fable.insightview.platform.supplier.enitity.ProviderSoftHardwareProxy;
import com.fable.insightview.platform.supplier.enitity.Supplier;



public interface ISupplierService {
	public List<Supplier> getSupplier(Page<Supplier> page);
	public List<ProviderAccessoryInfo> getAttachment(Page<ProviderAccessoryInfo> page);
	public List<ProviderSoftHardwareProxy> getSoftHardware(Page<ProviderSoftHardwareProxy> page);
	public List<ProviderServiceCertificate> getServiceInfo(Page<ProviderServiceCertificate> page);
	public void insertSupplierInfo(Supplier vo);
	public void editSupplierInfo(Supplier vo);
	public Supplier queryBaseS(Integer id);
	public void deletePai(Integer id);
	public void deletePshp(Integer id);
	public void deletePsc(Integer id);
	public boolean deleteSupplier(Integer id);
	public String deleteSuppliers(String id);
	public ProviderServiceCertificate queryProviderServiceCertificate(Integer id);
	public ProviderSoftHardwareProxy queryProviderSoftHardwareProxy(Integer id);
}
