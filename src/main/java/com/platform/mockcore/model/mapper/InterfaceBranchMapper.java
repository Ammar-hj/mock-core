package com.platform.mockcore.model.mapper;

import com.platform.mockcore.model.entity.InterfaceBranch;
import com.platform.mockcore.model.entity.InterfaceBranchExample;
import com.platform.mockcore.model.entity.InterfaceBranchWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceBranchMapper {
    long countByExample(InterfaceBranchExample example);

    int deleteByExample(InterfaceBranchExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InterfaceBranchWithBLOBs record);

    int insertSelective(InterfaceBranchWithBLOBs record);

    List<InterfaceBranchWithBLOBs> selectByExampleWithBLOBs(InterfaceBranchExample example);

    List<InterfaceBranch> selectByExample(InterfaceBranchExample example);

    InterfaceBranchWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InterfaceBranchWithBLOBs record, @Param("example") InterfaceBranchExample example);

    int updateByExampleWithBLOBs(@Param("record") InterfaceBranchWithBLOBs record, @Param("example") InterfaceBranchExample example);

    int updateByExample(@Param("record") InterfaceBranch record, @Param("example") InterfaceBranchExample example);

    int updateByPrimaryKeySelective(InterfaceBranchWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(InterfaceBranchWithBLOBs record);

    int updateByPrimaryKey(InterfaceBranch record);
}