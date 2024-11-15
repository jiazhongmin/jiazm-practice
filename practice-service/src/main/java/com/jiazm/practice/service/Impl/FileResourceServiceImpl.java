package com.jiazm.practice.service.Impl;

import com.github.pagehelper.PageInfo;
import com.jiazm.practice.commons.SetValueUtils;
import com.jiazm.practice.entity.FileResource;
import com.jiazm.practice.exception.BaseException;
import com.jiazm.practice.mapper.FileResourceMapper;
import com.jiazm.practice.req.fileresource.DeleteReq;
import com.jiazm.practice.req.fileresource.ListReq;
import com.jiazm.practice.req.fileresource.ModifyReq;
import com.jiazm.practice.response.GeneralResponse;
import com.jiazm.practice.service.FileResourceService;
import com.jiazm.practice.utils.CopyClass;
import com.jiazm.practice.utils.FileUtils;
import com.jiazm.practice.vo.UploadImageVo;
import com.jiazm.practice.vo.fileresource.FileResourceListVo;
import com.jiazm.practice.vo.fileresource.FileResourceVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.pagehelper.page.PageMethod.offsetPage;

/**
 * fileResource ServiceImpl
 *
 * @author jiazm3
 * @date 2024-11-15 18:05:29
 */
@Service("fileResourceService")
@Slf4j
public class FileResourceServiceImpl implements FileResourceService {

    @Resource
    private FileResourceMapper fileResourceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResponse<String> modify(ModifyReq req) {
        //获取用户itCode
        String itCode = "jiazm3";
        Date date = new Date();
        //验证是否存在条件匹配的数据
        verifyDataExist(req);
        if (Objects.nonNull(req.getId())) {
            FileResource fileResource = fileResourceMapper.findById(req.getId());
            FileResource fileResourceUpdate = new CopyClass<ModifyReq, FileResource>().copyBean(req, FileResource.class);
            fileResourceUpdate.setId(fileResource.getId());
            SetValueUtils.setMethodUpdateValVoid(fileResourceUpdate, itCode, date);
            fileResourceMapper.update(fileResourceUpdate);
        } else {
            FileResource fileResourceAdd = new CopyClass<ModifyReq, FileResource>().copyBean(req, FileResource.class);
            SetValueUtils.setMethodCreateValVoid(fileResourceAdd, itCode, date);
            fileResourceMapper.add(fileResourceAdd);
        }
        return GeneralResponse.success("modify success");
    }

    @Override
    public GeneralResponse<FileResourceListVo> list(ListReq req) {
        FileResourceListVo vo = new FileResourceListVo();
        if (Objects.nonNull(req.getPageNum()) && Objects.nonNull(req.getPageSize())) {
            req.setPageNum((req.getPageNum() - 1) * req.getPageSize());
        }
        offsetPage(req.getPageNum(), req.getPageSize());
        List<FileResource> vos = fileResourceMapper.selectByCondition(req);
        PageInfo<FileResource> pageInfo = new PageInfo<>(vos);
        List<FileResourceVo> fileResourceVos = new CopyClass<FileResource, FileResourceVo>().copyListNull(pageInfo.getList(), FileResourceVo.class);
        vo.setList(fileResourceVos);
        vo.setTotal((int) pageInfo.getTotal());
        return GeneralResponse.success(vo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResponse<String> del(DeleteReq req) {
        String itCode = "jiazm3";
        Date date = new Date();
        FileResource fileResource = fileResourceMapper.findById(req.getId());
        SetValueUtils.setMethodDelValVoid(fileResource, itCode, date);
        fileResourceMapper.update(fileResource);
        return GeneralResponse.success("delete success");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GeneralResponse<UploadImageVo> uploadFile(MultipartFile file) {
        UploadImageVo vo = new UploadImageVo();
        String uploadPath = "/images/";
        String itCode = "jiazm3";
        Date date = new Date();
        try {
            String fileType = FileUtils.getFileType(file);
            String fileName = file.getOriginalFilename();
            FileResource fileResource = new FileResource();
            fileResource.setFilePath(uploadPath);
            fileResource.setSourceFileName(fileName);
            SetValueUtils.setMethodCreateValVoid(fileResource, itCode, date);
            fileResourceMapper.add(fileResource);
            String resultFileName = Objects.requireNonNull(fileName).substring(0, fileName.lastIndexOf(".")) + "_" + fileResource.getId() + "." + fileType;
            fileResource.setFileName(resultFileName);
            fileResourceMapper.update(fileResource);
            String filePath = "/www/pictures/" + fileResource.getFileName();
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // 保存文件
            //file.transferTo(Paths.get(filePath));
            Files.copy(file.getInputStream(), Paths.get(filePath));
            vo.setFilePath(uploadPath);
            vo.setFileName(fileName);
        } catch (Exception e) {
            throw new BaseException("-999", e.getMessage());
        }
        return GeneralResponse.success(vo);
    }

    private void verifyDataExist(ModifyReq req) {
        FileResource findFileResource = new FileResource();
        //todo 添加条件
        findFileResource.setIsDeleted(0);
        List<FileResource> fileResourceList = fileResourceMapper.findList(findFileResource);
        if (Objects.isNull(req.getId())) {
            if (CollectionUtils.isNotEmpty(fileResourceList)) {
                throw new BaseException("-999", "这条记录已经存在!");
            }
        } else {
            List<FileResource> resultList = fileResourceList.stream().filter(fileResource -> !fileResource.getId().equals(req.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(resultList)) {
                throw new BaseException("-999", "这条记录已经存在!");
            }
        }
    }
}
