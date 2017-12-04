package com.fable.insightview.platform.listview.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fable.insightview.platform.common.util.Constants;
import com.fable.insightview.platform.common.util.FileUtil;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.listview.entity.FileBean;
import com.fable.insightview.platform.listview.service.FileManagerService;
import com.fable.insightview.platform.util.exception.BusinessException;

@Service
public class FileManagerServiceImpl implements FileManagerService {

    private static final Logger logger = LoggerFactory.getLogger(FileManagerServiceImpl.class);
//    @Autowired
//    FileBeanMapper fileBeanMapper;

    //chunk目录下所有文件
    private static TreeMap<Long, Long> getAllChunkMap(File dir) throws IOException {
        TreeMap<Long, Long> map = new TreeMap<Long, Long>();
        for (File f : dir.listFiles()) {
            map.put(Long.valueOf(f.getName()), f.length());
        }
        return map;
    }

    //获取总长度
    private static long getTotalSize(TreeMap<Long, Long> chunkStartsToLengths) {
        long ret = 0;
        for (long len : chunkStartsToLengths.values())
            ret += len;
        return ret;
    }

    @Override
    public FileBean getFileInfo(String id) throws BusinessException {
		return null;
        //return fileBeanMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FileBean> getFilesByLogo(String logo) throws BusinessException {
		return null;
        //return fileBeanMapper.selectFilesByLogo(logo);
    }

    @Override
    public boolean deleteFileBean(String id) throws BusinessException {
		return false;
        //return fileBeanMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public boolean insertFileBean(FileBean fileBean) throws BusinessException {
		return false;
        //return fileBeanMapper.insert(fileBean) == 1;
    }

    @Override
    public boolean updateFileBean(FileBean fileBean) throws BusinessException {
		return false;
        //return fileBeanMapper.updateByPrimaryKeySelective(fileBean) == 1;
    }

    @Override
    public void uploadFile(FileBean fileBean, MultipartFile file) throws BusinessException {

        boolean instRet = insertFileBean(fileBean);
        if (instRet) {
            try {
                doUploadFile(fileBean, file);

                fileBean.setFileFn(Constants.STR_TRUE);
                updateFileBean(fileBean);
            } catch (Exception e) {
                //如果上传文件出现异常后，则清除之前插入的文件记录。
                deleteFileBean(fileBean.getId());
                throw new BusinessException("00400001", e, null);
            }
        } else {
            throw new BusinessException("00000011");
        }
    }

    @Override
    public void uploadFileChunk(FileBean fileBean, MultipartFile file, Integer chunk) throws BusinessException {
        if (chunk == 0) {//1.检查，先插入数据库
            setFileBeanPath(fileBean);
            try {
                boolean instRet = insertFileBean(fileBean);
                if (!instRet) {
                    throw new BusinessException("00000009");
                }
            } catch (Exception e) {
                logger.error("", e);
                throw new BusinessException("00000009");
            }
        }
        try {
            //2.写文件
            doUploadFileChunk(fileBean, file, chunk);
            //3.检查trunk的文件是否已经完整
            boolean assembled = checkAssembledFile(fileBean, getChunkFolder(fileBean));
            if (assembled) {
                fileBean.setFileFn(Constants.STR_TRUE);
                updateFileBean(fileBean);
            }
        } catch (Exception e) {
            throw new BusinessException("00400001", e, null);
        }
    }

    @Override
    public String uploadTempFile(File file) throws BusinessException {
        return uploadTempFile(file, "temp");
    }

    @Override
    public void deleteFileInfos(String[] ids) throws BusinessException {
        if (ids == null) return;
        for (int i = 0; i < ids.length; i++) {
            FileBean fileBean = getFileInfo(ids[i]);
            // to delete file.
            if (fileBean != null) {//找不到fileBean，则忽略，进行下一条记录的处理。
                deleteFileBean(ids[i]);
                deleteFileByPath(fileBean.getFilePath());
            }
        }
    }

    @Override
    public void deleteFileByPaths(List<String> toDeleteFileRelPaths) {
        if (toDeleteFileRelPaths != null && !toDeleteFileRelPaths.isEmpty()) {
            for (String path : toDeleteFileRelPaths) {
                deleteFileByPath(path);
            }
        }
    }

    public void deleteFileByPath(String fileRelPath) {
        String fileStr = getAbstFilePath(fileRelPath);
        try {
            Files.deleteIfExists(Paths.get(fileStr));
        } catch (IOException e) {
            logger.error("delete file :" + fileStr + "  ex:" + e);
        }
    }

    @Override
    public String getBaseFileUploadFolder() {
        return getBaseFilePath();
    }


    //*************************private methods******************

    public String getAbstFilePath(String relFilePath) {
        return getBaseFilePath() + File.separator + relFilePath;
    }

    @Override
    public File getFileByRelPath(String relFilePath) {
        File file = new File(getAbstFilePath(relFilePath));
        if (file.exists())
            return file;
        else
            return null;
    }

    //保存独立的文件
    private String uploadTempFile(File file, String fileLogo) {
        String fileRelPath = getFileRelPath(fileLogo, file.getName(), UUID.randomUUID().toString().replace("-", ""));
        try {
            File saveFile = Paths.get(getBaseFilePath(), fileRelPath).toFile();
            FileUtils.copyFile(file, saveFile);
            return fileRelPath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("00400001", e, null);
        }
    }

    private void setFileBeanPath(FileBean fileBean) {
        fileBean.setFilePath(getChunkRelativePath(fileBean));
    }

    // 保存文件至指定目录，返回保存结果
    private void doUploadFileChunk(FileBean fileBean, MultipartFile file, Integer chunk) throws IOException {
        //1.获取临时目录
        String chunkFolder = getChunkFolder(fileBean);
        //2.保存chunk文件
        File chunkFile = Paths.get(chunkFolder + File.separator + chunk).toFile();
        try {
            file.transferTo(chunkFile);
        } catch (IOException e) {
            logger.error("save file to path exception." + e);
            throw e;
        }
    }

    //分块文件目录
    private String getChunkFolder(FileBean fileBean) {
        String chunkFolder = getBaseFilePath() + File.separator + getChunkRelativePath(fileBean);
        FileUtil.createDirs(chunkFolder); //创建目录
        return chunkFolder;
    }

    //分块文件的相对路径
    private String getChunkRelativePath(FileBean fileBean) {
        String relPath = getRelativePath(fileBean.getFileLogo());
        relPath += File.separator + fileBean.getId() + "_chunk";
        return relPath;
    }

    private boolean checkAssembledFile(FileBean fileBean, String chunkFolder) throws IOException {
        File chunkFolderFile = Paths.get(chunkFolder).toFile();
        TreeMap<Long, Long> chunksMap = getAllChunkMap(chunkFolderFile);
        long lengthSoFar = getTotalSize(chunksMap);
        if (lengthSoFar == fileBean.getFileSize().longValue()) {
            File assembledFile = assembleAndDeleteChunks(fileBean, chunkFolder, new ArrayList<Long>(chunksMap.keySet()));
            logger.debug("file:" + assembledFile.getPath());
            return true;
        }
        return false;
    }

    //整合文件
    private File assembleAndDeleteChunks(FileBean fileBean, String chunkFolder, ArrayList<Long> longs) throws IOException {
        String fileRelPath = getFileRelPath(fileBean.getFileLogo(), fileBean.getFileName(), fileBean.getId());
        fileBean.setFilePath(fileRelPath);//更新文件路径
        File saveFile = Paths.get(getBaseFilePath(), fileRelPath).toFile();
        FileOutputStream assembFOS = new FileOutputStream(saveFile);
        try {
            byte[] buf = new byte[100000];
            for (long key : longs) {
                File chunkFile = new File(chunkFolder, key + "");
                InputStream is = new FileInputStream(chunkFile);
                try {
                    while (true) {
                        int r = is.read(buf);
                        if (r == -1)
                            break;
                        if (r > 0)
                            assembFOS.write(buf, 0, r);
                    }
                } finally {
                    is.close();
                }
                chunkFile.delete();
            }
            Paths.get(chunkFolder).toFile().delete();//to delete chunkFolder.
        } finally {
            assembFOS.close();
        }
        return saveFile;
    }


    // 保存文件至指定目录，返回保存结果
    private void doUploadFile(FileBean fileBean, MultipartFile file) {
        // 获取文件保存的目录，规则：根目录【配置】+LOGO+文件
        String fileRelPath = getFileRelPath(fileBean);
        fileBean.setFilePath(fileRelPath);
        File saveFile = Paths.get(getBaseFilePath(), fileRelPath).toFile();
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            logger.error("save file to path exception." + e);
            throw new BusinessException("00400001", e, null);
        }
    }

    //目录规则,并创建目录
    private String getFileRelPath(FileBean fileBean) {
        return getFileRelPath(fileBean.getFileLogo(), fileBean.getFileName(), fileBean.getId());
    }

    private String getFileRelPath(String fileLogo, String fileName, String id) {
        String relPath = getRelativePath(fileLogo);
        //创建目录
        FileUtil.createDirs(getBaseFilePath() + File.separator + relPath);
        return relPath + File.separator + fileName + "_" + id;
    }


    //取得基础路径
    private String getBaseFilePath() {
        String configFolder = "../uploadfile";//SystemPropertyHandler.getProperty("fbs.upload.folder");
        if (configFolder.endsWith("/") && configFolder.endsWith("\\")) {
            configFolder = configFolder.substring(0, configFolder.length() - 1);
        }
        return FileUtil.getBaseFileFolder(configFolder);
    }


    // 目录规则 ，暂时以logo区分，
    private String getRelativePath(String fileLog) {
        if (StringUtil.isEmpty(fileLog))
            return "temp";
        return fileLog;
    }


}
