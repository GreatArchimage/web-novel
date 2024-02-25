package cn.edu.seig.novel.service.impl;

import cn.edu.seig.novel.common.http.Result;
import cn.edu.seig.novel.service.ResourceService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;
    @Override
    public Result getImgVerifyCode() throws IOException {
        return null;
    }

    @Override
    public Result uploadImage(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        String savePath =
                "/image/"
                        + now.format(DateTimeFormatter.ofPattern("yyyy")) + File.separator
                        + now.format(DateTimeFormatter.ofPattern("MM")) + File.separator
                        + now.format(DateTimeFormatter.ofPattern("dd"));
        String oriName = file.getOriginalFilename();
        assert oriName != null;
        String saveFileName = IdWorker.get32UUID() + oriName.substring(oriName.lastIndexOf("."));
        File saveFile = new File(fileUploadPath + savePath, saveFileName);
        if (!saveFile.getParentFile().exists()) {
            boolean isSuccess = saveFile.getParentFile().mkdirs();
            if (!isSuccess) {
                throw new RuntimeException("文件夹创建失败");
            }
        }
        try {
            file.transferTo(saveFile);
            if (Objects.isNull(ImageIO.read(saveFile))) {
                // 上传的文件不是图片
                Files.delete(saveFile.toPath());
                throw new RuntimeException("上传的文件不是图片");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(savePath + File.separator + saveFileName);
    }
}
