package com.fable.insightview.platform.listview.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fable.insightview.platform.listview.entity.FileBean;
import com.fable.insightview.platform.util.exception.BusinessException;

public interface FileManagerService {

    /**
     * 根据ID取文件对象
     * @param id
     * @return
     * @throws BusinessException
     */
    FileBean getFileInfo(String id) throws BusinessException;

    /**
     * 根据logo取文件对象集合
     * @param logo
     * @return
     * @throws BusinessException
     */
    List<FileBean> getFilesByLogo(String logo) throws BusinessException;

    /**
     * 根据ID删除文件
     * @param id
     * @return
     * @throws BusinessException
     */
    boolean deleteFileBean(String id) throws BusinessException;


    /**
     * 根据ids删除文件
     * @param ids
     * @throws BusinessException
     */
    void deleteFileInfos(String[] ids) throws BusinessException;


    /**
     * 插入文件对象
     * @param fileBean
     * @return
     * @throws BusinessException
     */
    boolean insertFileBean(FileBean fileBean) throws BusinessException;

    /**
     * 更新文件对象
     * @param fileBean
     * @return
     * @throws BusinessException
     */
    boolean updateFileBean(FileBean fileBean) throws BusinessException;

    /**
     * 根据文件对象上传文件
     * @param fileBean
     * @param file
     * @throws BusinessException
     */
    void uploadFile(FileBean fileBean, MultipartFile file) throws BusinessException;

    /**
     * 分块上传文件
     * @param fileBean
     * @param file
     * @param chunk
     */
    void uploadFileChunk(FileBean fileBean, MultipartFile file, Integer chunk) throws BusinessException;

    /**
     * 上传文件至临时目录，
     * @param file，通过file.getName()，保存至指定的目录中
     * @return 文件相对路径
     * @throws BusinessException
     */
    String uploadTempFile(File file) throws BusinessException;


    /**
     * 根据相对路径取得文件的绝对路径
     * @param relFilePath
     * @return
     */
    String getAbstFilePath(String relFilePath);


    /**
     * 根据相对路径获取文件对象
     * @param relFilePath
     * @return
     */
    File getFileByRelPath(String relFilePath);

    /**
     * 根据路径集合删除文件
     * @param toDeleteFilePaths
     */
    void deleteFileByPaths(List<String> toDeleteFilePaths);

    /**
     * 根据路径删除文件
     * @param relPath
     */
    void deleteFileByPath(String relPath);

    /**
     * 得到文件上传的根目录
     * @return
     */
    String getBaseFileUploadFolder();


}
