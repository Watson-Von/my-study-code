package com.watson.demo.excel;

import com.watson.demo.utils.EasyExcelUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author : fengHangWen
 * @version : 1.0
 * @description : 生成excel文件服务
 * @company : 深圳一点盐光科技有限公司
 * @date : 2020/12/1 17:41
 */
@Service
public class GeneratorExcelFileService {

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 相对路径, 文件会生成在与项目平级的目录
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 17:12
     */
    private final String FILE_PATH = "file/excel/";

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 文件后缀
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 18:13
     */
    private final String EXCEL_SUFFIX = ".xlsx";

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 生成excel文件
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 17:42
     */
    public <T> File generatorExcel(String fileName, Class<T> clazz, List<T> list) {
        if (!generateFilePath()) {
            return null;
        }
        String fileFullName = FILE_PATH + fileName + EXCEL_SUFFIX;
        EasyExcelUtil.writeToExcel(fileFullName, clazz, list);
        return new File(fileFullName);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 删除文件
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 17:42
     */
    public boolean deleteFile(File file) {
        if (file != null) {
            return file.delete();
        }
        return true;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description :
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 17:42
     */
    private boolean generateFilePath() {
        File path = new File(FILE_PATH);
        if (path.exists()) {
            return true;
        } else {
            return path.mkdirs();
        }
    }

}
