package com.fable.insightview.platform.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.supplier.enitity.ProviderAccessoryInfo;
import com.fable.insightview.platform.supplier.enitity.ProviderServiceCertificate;
import com.fable.insightview.platform.supplier.enitity.ProviderSoftHardwareProxy;
import com.fable.insightview.platform.supplier.enitity.Supplier;
import com.fable.insightview.platform.supplier.mapper.SupplierMapper;
import com.fable.insightview.platform.supplier.service.ISupplierService;

@Service
public class SupplierServiceImpl implements ISupplierService {
	@Autowired
	SupplierMapper supplierMapper;

	@Override
	public List<Supplier> getSupplier(Page<Supplier> page) {
		List<Supplier> list = supplierMapper.getSupplier(page);
		return list;
	}

	@Override
	public List<ProviderAccessoryInfo> getAttachment(Page<ProviderAccessoryInfo> page) {
		return supplierMapper.getAttachment(page);
	}

	@Override
	public List<ProviderSoftHardwareProxy> getSoftHardware(Page<ProviderSoftHardwareProxy> page) {
		return supplierMapper.getSoftHardware(page);
	}

	@Override
	public void insertSupplierInfo(Supplier vo) {
		List<ProviderAccessoryInfo> list1 = JsonUtil.toList(vo.getAttachmentArray(),ProviderAccessoryInfo.class);
		List<ProviderSoftHardwareProxy> list2 = JsonUtil.toList(vo.getG_attachmentArrays(),ProviderSoftHardwareProxy.class);
		List<ProviderServiceCertificate> list3 = JsonUtil.toList(vo.getGs_attachmentArrays(),ProviderServiceCertificate.class);
		
		supplierMapper.insertSupplierInfo(vo);
		
		if(list1 != null) {
			for(ProviderAccessoryInfo a:list1) {
				a.setProviderId(vo.getProviderId());
				supplierMapper.insertPAInfo(a);
			}
		}
		if(list2 != null) {
			for(ProviderSoftHardwareProxy b:list2) {
				b.setProviderId(vo.getProviderId());
				supplierMapper.insertSHPInfo(b);
			}
		}
		if(list3 != null) {
			for(ProviderServiceCertificate c:list3) {
				c.setProviderId(vo.getProviderId());
				supplierMapper.insertPSCInfo(c);
			}
		}
	}

	@Override
	public Supplier queryBaseS(Integer id) {
		return supplierMapper.queryBaseS(id);
	}

	@Override
	public List<ProviderServiceCertificate> getServiceInfo(
			Page<ProviderServiceCertificate> page) {
		return supplierMapper.getServiceInfo(page);
	}

	@Override
	public void editSupplierInfo(Supplier vo) {
		List<ProviderAccessoryInfo> list1 = JsonUtil.toList(vo.getAttachmentArray(),ProviderAccessoryInfo.class);
		List<ProviderSoftHardwareProxy> list2 = JsonUtil.toList(vo.getG_attachmentArrays(),ProviderSoftHardwareProxy.class);
		List<ProviderServiceCertificate> list3 = JsonUtil.toList(vo.getGs_attachmentArrays(),ProviderServiceCertificate.class);
		
		supplierMapper.editSupplierInfo(vo);
		
		if(list1 != null) {
			for(ProviderAccessoryInfo a:list1) {
				a.setProviderId(vo.getProviderId());
				if(null == a.getProviderAccessoryId()) {
					supplierMapper.insertPAInfo(a);
				} else {
					supplierMapper.editPAInfo(a);
				}
			}
		}
		if(list2 != null) {
			for(ProviderSoftHardwareProxy b:list2) {
				b.setProviderId(vo.getProviderId());
				if(null ==  b.getProxyId()) {//判断id是否存在，0表示不存在
					supplierMapper.insertSHPInfo(b);//添加
				} else {
					supplierMapper.editSHPInfo(b);//修改
				}
			}
		}
		if(list3 != null) {
			for(ProviderServiceCertificate c:list3) {
				c.setProviderId(vo.getProviderId());
				if(null == c.getServiceId()) {
					supplierMapper.insertPSCInfo(c);
				}else {
					supplierMapper.editPSCInfo(c);
				}
			}
		}
		System.out.println("***********"+vo.getAttachmentArray());
		System.out.println("***********"+vo.getG_attachmentArrays());
		System.out.println("***********"+vo.getGs_attachmentArrays());
		System.out.println("***********"+list1);
		System.out.println("***********"+list2);
		System.out.println("***********"+list3);
		
	}

	@Override
	public void deletePai(Integer id) {
		supplierMapper.deletePai(id);
		
	}

	@Override
	public void deletePsc(Integer id) {
		supplierMapper.deletePsc(id);
		
	}

	@Override
	public void deletePshp(Integer id) {
		supplierMapper.deletePshp(id);
		
	}

	@Override
	public boolean deleteSupplier(Integer id) {
		int i = supplierMapper.haveUser(id);
		if(i == 0 ) {
			supplierMapper.deleteProviderAccessoryInfo(id);
			supplierMapper.deleteProviderSoftHardwareProxy(id);
			supplierMapper.deleteProviderServiceCertificate(id);
			supplierMapper.deleteSupplier(id);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String deleteSuppliers(String id) {
		String[] ids = id.split(",");
		String haveUsingProvider = "";
		for(String pid:ids) {
			int i = supplierMapper.haveUser(Integer.parseInt(pid));
			int nid = Integer.parseInt(pid);
			if(i == 0) {
				supplierMapper.deleteProviderAccessoryInfo(nid);
				supplierMapper.deleteProviderSoftHardwareProxy(nid);
				supplierMapper.deleteProviderServiceCertificate(nid);
				supplierMapper.deleteSupplier(nid);
				//return false;
			} else {
				String sname = supplierMapper.usingSupplierName(nid);
				if(haveUsingProvider == "") {
					haveUsingProvider = sname;
				} else {
					haveUsingProvider += "," + sname;
				}
			}
		}
		/*for(String pid:ids) {
			int nid = Integer.parseInt(pid);
			supplierMapper.deleteProviderAccessoryInfo(nid);
			supplierMapper.deleteProviderSoftHardwareProxy(nid);
			supplierMapper.deleteProviderServiceCertificate(nid);
			supplierMapper.deleteSupplier(nid);
		}*/
		return haveUsingProvider;
	}

	@Override
	public ProviderServiceCertificate queryProviderServiceCertificate(Integer id) {
		return supplierMapper.queryProviderServiceCertificate(id);
	}
	
	@Override
	public ProviderSoftHardwareProxy queryProviderSoftHardwareProxy(Integer id) {
		return supplierMapper.queryProviderSoftHardwareProxy(id);
	}
}
