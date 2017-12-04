package com.fable.insightview.platform.aaServiceForKscc.mapper;

import com.fable.insightview.platform.aaServiceForKscc.entity.FbsInterface;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :Hairui
 * Date :2017/11/27
 * Time :13:33
 * </p>
 * <p>
 * Department :
 * </p>
 * <p> Copyright : 江苏飞博软件股份有限公司 </p>
 */
public interface KsccSchemeMapper {

    String getFileSize();

    List<Map<String, String>> findAllLiveCodecForYw(Map<String, String> param);

    List<FbsInterface> selectFbsInterfaceList();

    List<Map<String, Object>> getHospital();
}
