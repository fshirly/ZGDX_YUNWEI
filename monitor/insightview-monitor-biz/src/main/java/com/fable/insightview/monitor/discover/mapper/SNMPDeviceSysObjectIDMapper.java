package com.fable.insightview.monitor.discover.mapper;

import com.fable.insightview.monitor.discover.entity.SNMPDeviceSysObjectID;
import com.fable.insightview.monitor.discover.entity.SNMPDeviceSysObjectIDKey;

public interface SNMPDeviceSysObjectIDMapper {
    int deleteByPrimaryKey(SNMPDeviceSysObjectIDKey key);

    int insert(SNMPDeviceSysObjectID record);

    int insertSelective(SNMPDeviceSysObjectID record);

    SNMPDeviceSysObjectID selectByPrimaryKey(SNMPDeviceSysObjectIDKey key);

    int updateByPrimaryKeySelective(SNMPDeviceSysObjectID record);

    int updateByPrimaryKey(SNMPDeviceSysObjectID record);
}