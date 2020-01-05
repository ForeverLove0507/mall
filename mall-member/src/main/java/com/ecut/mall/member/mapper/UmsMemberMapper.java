package com.ecut.mall.member.mapper;

import com.ecut.mall.member.beans.UmsMember;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;
public interface UmsMemberMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllMembers();
}
