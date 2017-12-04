package com.fable.insightview.platform.common.excel.util;

/**
 * @author wul
 * 
 */
import java.util.List;
import java.util.Map;

public class ValidateColumn {
	/**
	 * �����ļ���֤ʱ���档������һ��ͬ��ı��⣬ȡ������_1�������ڡ�����_1,ȡ����_2
	 * 
	 * @param map
	 *            �������ļ���˳���ȡʱ����ǰ����ǰ��������ֵmap,���ǰ������ �� 5 ����map size��Ϊ4
	 * @param excelTitle
	 * @return 
	 *         ���괫�����excelTitle��map���ڣ����ء�excelTitle_1.���excelTitle_1Ҳ���ڣ�����excelTitle_2
	 */
	public static String configValidate(Map map, String excelTitle) {
		String temp = excelTitle;
		for (int i = 0; i < map.size(); i++) {
			if (i > 0) {
				temp = excelTitle + "_" + i;
			}
			if (map.containsKey(temp)) {
				continue;
			} else {
				break;
			}
		}
		return temp;
	}

	/**
	 * ��֤excel��ͷ��������һ��ͬ��ı��⣬ȡ������_1�������ڡ�����_1,ȡ����_2,������԰�excel�������Ƴ�ͬһ�����Ҳ���Զ�ȡ��
	 * 
	 * @param list
	 *            ��excel���ⰴ˳���ȡʱ����ǰ����ǰ����ֵlist,���ǰ������ �� 5 ����list size��Ϊ4
	 * @param excelTitle
	 * @return ���괫�����excelTitle��list���ڣ����ء�excelTitle_1.���excelTitle_1Ҳ���ڣ�
	 *         ����excelTitle_2
	 */
	public static String columnValidate(List list, String excelTitle) {
		String temp = excelTitle;
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				temp = excelTitle + "_" + i;
			}
			if (list.contains(temp)) {
				continue;
			} else {
				break;
			}
		}
		return temp;

	}
}
